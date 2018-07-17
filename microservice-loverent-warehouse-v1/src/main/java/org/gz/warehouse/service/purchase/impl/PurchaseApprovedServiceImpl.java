/**
 * 
 */
package org.gz.warehouse.service.purchase.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetail;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderVO;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderDetailMapper;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderMapper;
import org.gz.warehouse.service.purchase.PurchaseApprovedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月19日 上午10:24:04
 */
@Service
@Slf4j
public class PurchaseApprovedServiceImpl implements PurchaseApprovedService {

	@Autowired
	private PurchaseApplyOrderMapper applyOrderMapper;
	
	@Autowired
	private PurchaseApplyOrderDetailMapper applyOrderDetailMapper;
	
	@Override
	public ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q) {
		List<PurchaseApplyOrderVO> list = new ArrayList<PurchaseApplyOrderVO>(0);
		int totalNum = 0;
		if(q!=null) {
			q.setStatusFlag(20);//查询已提交的采购申请单
			totalNum = this.applyOrderMapper.queryPageCount(q);
			if(totalNum>0) {
				list = this.applyOrderMapper.queryPageList(q);
			}
			ResultPager<PurchaseApplyOrderVO> data = new ResultPager<PurchaseApplyOrderVO>(totalNum, q.getCurrPage(), q.getPageSize(), list);
			return ResponseResult.buildSuccessResponse(data);
		}
		return  ResponseResult.buildSuccessResponse(new ResultPager<PurchaseApplyOrderVO>(totalNum, 1, 10, list));
	}

	@Override
	public ResponseResult<PurchaseApplyOrderVO> queryDetail(Long id) {
		PurchaseApplyOrderQuery q =new PurchaseApplyOrderQuery();
		q.setId(id);
		q.setCurrPage(1);
		q.setPageSize(1);
		q.setStatusFlag(20);
		List<PurchaseApplyOrderVO> list =this.applyOrderMapper.queryPageList(q);
		PurchaseApplyOrderVO res=null;
		if(CollectionUtils.isNotEmpty(list)) {
			res = list.get(0);
			//封装物料采购信息
			List<PurchaseApplyOrderDetailVO> detailList = this.applyOrderDetailMapper.queryByPurchaseApplyOrderId(res.getId());
			if(CollectionUtils.isNotEmpty(detailList)) {
				for(PurchaseApplyOrderDetailVO detail : detailList) {//将批准数量修改成申请数量
					detail.setApprovedQuantity(detail.getApplyQuantity());
				}
			}
			res.setDetailList(detailList);
		}
		return ResponseResult.buildSuccessResponse(res);
	}

	@Transactional
	@Override
	public ResponseResult<PurchaseApprovedOrderVO> approved(PurchaseApprovedOrderVO p) {
		//验证数据
		if(p==null) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		PurchaseApplyOrderVO order = applyOrderMapper.selectByPrimaryKey(p.getId());
		if(order==null) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		if(CollectionUtils.isEmpty(p.getDetailList())) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		if(order.getStatusFlag().intValue()!=20) {//判断当前状态是否处于已提交状态
			return ResponseResult.build(WarehouseErrorCode.PURCHASE_APPROVED_STATUS_ERROR.getCode(), WarehouseErrorCode.PURCHASE_APPROVED_STATUS_ERROR.getMessage(), null);
		}
		//先更新采购物料明细
		BigDecimal sumAmount = new BigDecimal(0);
		try {
			for(PurchaseApprovedOrderDetailVO v:p.getDetailList()) {
				PurchaseApplyOrderDetail orderDetail = this.applyOrderDetailMapper.selectByPrimaryKey(v.getId());
				if(orderDetail==null) {
					log.error("采购申请物料不存在：{}"+v.getId());
					throw new ServiceException(WarehouseErrorCode.PURCHASE_APPLY_NOTEXIST_ERROR.getCode(),WarehouseErrorCode.PURCHASE_APPLY_NOTEXIST_ERROR.getMessage());
				}
				this.applyOrderDetailMapper.updateApprovedQuantity(v);
				BigDecimal perSumAmout = orderDetail.getUnitPrice().multiply(new BigDecimal(v.getApprovedQuantity()));
				sumAmount = sumAmount.add(perSumAmout);
			}
		} catch(ServiceException e) {
			throw e;
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		
		//再更新采购单信息
		PurchaseApplyOrder newOrder = new PurchaseApplyOrder();
		newOrder.setId(order.getId());
		newOrder.setVersion(order.getVersion());
		newOrder.setApprovedId(p.getApprovedId());
		newOrder.setApprovedName(p.getApprovedName());
		newOrder.setApprovedDateTime(new Date());
		newOrder.setSumAmount(sumAmount);
		int flag = this.applyOrderMapper.approved(newOrder);
		if(flag>0) {
			return ResponseResult.buildSuccessResponse(null);
		}else {
			log.error("采购申请并发审批失败:{}",p.getId());
			throw new ServiceException("采购申请并发审批失败:"+p.getId());
		}
	}

	@Override
	public ResponseResult<PurchaseApprovedOrderVO> reject(PurchaseApprovedOrderVO p) {
		//验证数据
		if(p==null) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		PurchaseApplyOrderVO order = applyOrderMapper.selectByPrimaryKey(p.getId());
		if(order==null) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		if(order.getStatusFlag().intValue()!=20) {//判断当前状态是否处于已提交状态
			return ResponseResult.build(WarehouseErrorCode.PURCHASE_APPROVED_STATUS_ERROR.getCode(), WarehouseErrorCode.PURCHASE_APPROVED_STATUS_ERROR.getMessage(), null);
		}
		//直接更新
		PurchaseApplyOrder newOrder = new PurchaseApplyOrder();
		newOrder.setId(order.getId());
		newOrder.setVersion(order.getVersion());
		newOrder.setApprovedId(p.getApprovedId());
		newOrder.setApprovedName(p.getApprovedName());
		newOrder.setApprovedDateTime(new Date());
		int flag = this.applyOrderMapper.reject(newOrder);
		if(flag>0) {
			return ResponseResult.buildSuccessResponse(null);
		}else {
			log.error("采购申请并发拒绝失败:{}",p.getId());
			throw new ServiceException("采购申请并发拒绝失败:"+p.getId());
		}
	}
	
}
