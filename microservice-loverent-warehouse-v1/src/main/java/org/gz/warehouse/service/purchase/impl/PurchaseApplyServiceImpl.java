/**
 * 
 */
package org.gz.warehouse.service.purchase.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetail;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderDetailMapper;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderMapper;
import org.gz.warehouse.service.purchase.PurchaseApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 下午1:36:28
 */
@Service
@Slf4j
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

	@Autowired
	private PurchaseApplyOrderMapper applyOrderMapper;

	@Autowired
	private PurchaseApplyOrderDetailMapper applyOrderDetailMapper;
	
	/**
	 * 采购申请订单号前辍
	 */
	private static final String PREFIX_PURCHASE_APPLY_ORDER="PO";
	
	
	@Transactional
	@Override
	public ResponseResult<PurchaseApplyOrder> insert(PurchaseApplyOrder p) {
		ResponseResult<PurchaseApplyOrder> vr = this.validatePurchaseApplyOrder(p);
		if (vr.isSuccess()) {
			try {
				//生成采购申请单号：PO+时间戳
				p.setPurchaseNo(UUIDUtils.genDateUUID(PREFIX_PURCHASE_APPLY_ORDER));
				p.setCreateOn(new Date());
				p.setUpdateBy(p.getCreateBy());
				p.setUpdateOn(p.getCreateOn());
				p.setVersion(1L);
				if(this.applyOrderMapper.insert(p)>0) {//插入采购申请订单
					List<PurchaseApplyOrderDetail> detailList = p.getDetailList();
					for(Iterator<PurchaseApplyOrderDetail> it=detailList.iterator();it.hasNext();) {
						it.next().setPurchaseApplyOrderId(p.getId());
					}
					//插入采购申请订单明细-物料信息
					this.applyOrderDetailMapper.batchInsert(detailList);
					//更新采购申请订单总额
					p.setSumAmount(this.calculateSumAmount(detailList));
					this.applyOrderMapper.updateSumAmount(p);
					return ResponseResult.buildSuccessResponse(null);
				}
			} catch (Exception e) {
				log.error("新增物料采购申请发生异常：{}", e.getLocalizedMessage());
				throw new ServiceException();
			}
		}
		return vr;
	}

	@Override
	public ResponseResult<PurchaseApplyOrder> update(PurchaseApplyOrder p) {
		ResponseResult<PurchaseApplyOrder> vr = this.validatePurchaseApplyOrder(p);
		if (vr.isSuccess()) {
			try {
				PurchaseApplyOrderVO  queryOrder = this.applyOrderMapper.selectByPrimaryKey(p.getId());
				if(queryOrder==null) {
					return  ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), "无此采购申请订单", p);
				}
				if(queryOrder.getStatusFlag().intValue()!=10) {
					return  ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(),"仅可修改草稿采购申请订单", p);
				}
				this.applyOrderMapper.deleteApplyOrderDetail(p.getId());
				this.applyOrderMapper.deleteApplyOrder(p.getId());
				p.setId(null);
				return this.insert(p);
			} catch (Exception e) {
				log.error("修改物料采购申请发生异常：{}", e.getLocalizedMessage());
				throw new ServiceException();
			}
		}
		return vr;
	}
	
	/** 
	* @Description: 计算各物料总价
	* @param detailList
	* @return BigDecimal
	*/
	private BigDecimal calculateSumAmount(List<PurchaseApplyOrderDetail> detailList) {
		BigDecimal sumAmount = new BigDecimal(0);
		BigDecimal perSumAmout=null;
		for(PurchaseApplyOrderDetail detail:detailList) {
			if(detail.getUnitPrice().compareTo(new BigDecimal(0))>0) {
				perSumAmout = detail.getUnitPrice().multiply(new BigDecimal(detail.getApplyQuantity()));
				sumAmount = sumAmount.add(perSumAmout);
			}
		}
		return sumAmount;
	}

	/**
	 * @Description: 验证采购单
	 * @param p
	 * @return ResponseResult<PurchaseApplyOrder>
	 */
	private ResponseResult<PurchaseApplyOrder> validatePurchaseApplyOrder(PurchaseApplyOrder p) {
		// 验证物料列表是否重复
		if(p==null||CollectionUtils.isEmpty(p.getDetailList())) {
			return  ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), p);
		}
		List<PurchaseApplyOrderDetail> detailList = p.getDetailList();
		HashSet<Long>  materielBasicIdSet = new HashSet<Long>();
		for(PurchaseApplyOrderDetail detail : detailList) {
			if(detail.getUnitPrice()==null) {
				detail.setUnitPrice(new BigDecimal(0));
			}
			if(AssertUtils.isPositiveNumber4Long(detail.getMaterielBasicId())) {
				materielBasicIdSet.add(detail.getMaterielBasicId());
			}
		}
		if(materielBasicIdSet.size()==0) {
			return  ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), "请先选择采购物料", p);
		}
		//若不等，则说明有重复
		if(detailList.size()!=materielBasicIdSet.size()) {
			return  ResponseResult.build(WarehouseErrorCode.PURCHASE_APPLY_REPEAT_ERROR.getCode(), WarehouseErrorCode.PURCHASE_APPLY_REPEAT_ERROR.getMessage(), p);
		}
		return ResponseResult.buildSuccessResponse(p);
	}

	/**
	 * 获取分页列表
	 */
	@Override
	public ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q) {
		int totalNum = this.applyOrderMapper.queryPageCount(q);
		List<PurchaseApplyOrderVO> list = new ArrayList<PurchaseApplyOrderVO>(0);
		if(totalNum>0) {
			list = this.applyOrderMapper.queryPageList(q);
		}
		ResultPager<PurchaseApplyOrderVO> data = new ResultPager<PurchaseApplyOrderVO>(totalNum, q.getCurrPage(), q.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	/**
	 * 获取详情
	 */
	@Override
	public ResponseResult<PurchaseApplyOrderVO> queryDetail(Long id) {
		PurchaseApplyOrderQuery q =new PurchaseApplyOrderQuery();
		q.setId(id);
		q.setCurrPage(1);
		q.setPageSize(1);
		q.setStatusFlag(null);
		List<PurchaseApplyOrderVO> list =this.applyOrderMapper.queryPageList(q);
		PurchaseApplyOrderVO res=null;
		if(CollectionUtils.isNotEmpty(list)) {
			res = list.get(0);
			//封装物料采购信息
			List<PurchaseApplyOrderDetailVO> detailList = this.applyOrderDetailMapper.queryByPurchaseApplyOrderId(res.getId());
			res.setDetailList(detailList);
		}
		return ResponseResult.buildSuccessResponse(res);
	}

	/**
	 * 此方法只允许将草稿状态的采购申请修改为提交状态
	 */
	@Override
	public ResponseResult<PurchaseApplyOrder> updateToSubmitFlag(Long id) {
		PurchaseApplyOrderVO order = this.applyOrderMapper.selectByPrimaryKey(id);
		if(order!=null&&order.getStatusFlag().intValue()==10) {//只能是草稿状态
			PurchaseApplyOrder newOrder = new PurchaseApplyOrder();
			newOrder.setId(order.getId());
			newOrder.setVersion(order.getVersion());
			newOrder.setUpdateBy(order.getUpdateBy());
			newOrder.setUpdateOn(new Date());
			newOrder.setStatusFlag(20);//修改为已提交状态
			int flag = this.applyOrderMapper.updateStatusFlag(newOrder);
			if(flag>0) {
				return ResponseResult.buildSuccessResponse(null);
			}else {
				log.error("采购申请草稿-提交，并发修改异常：{}",id);
			}
		}
		return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
	}


}
