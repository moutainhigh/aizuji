/**
 * 
 */
package org.gz.warehouse.service.purchase.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.UUIDUtils;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetail;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecv;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvWrapper;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecord;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfo;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrack;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderDetailMapper;
import org.gz.warehouse.mapper.purchase.PurchaseApplyOrderMapper;
import org.gz.warehouse.mapper.purchase.PurchaseOrderRecvMapper;
import org.gz.warehouse.mapper.warehouse.StorageLocationMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseChangeRecordMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityTrackMapper;
import org.gz.warehouse.service.purchase.PurchaseRecvService;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月20日 下午5:40:05
 */
@Service
@Slf4j
public class PurchaseRecvServiceImpl implements PurchaseRecvService {

	@Autowired
    private Validator validator;
	
	@Autowired
	private PurchaseApplyOrderMapper applyOrderMapper;
	
	@Autowired
	private PurchaseApplyOrderDetailMapper applyOrderDetailMapper;
	
	@Autowired
	private PurchaseOrderRecvMapper orderRecvMapper;
	
	@Autowired
	private WarehouseCommodityInfoMapper commodityInfoMapper;
	
	@Autowired
	private WarehouseChangeRecordMapper changeRecordMapper;
	
	@Autowired
	private WarehouseCommodityTrackMapper trackMapper;
	
	@Autowired
	private WarehouseChangeRecordService recordService;
	
	@Autowired
	private StorageLocationMapper locationMapper;
	
	@Override
	public ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q) {
		List<PurchaseApplyOrderVO> list = new ArrayList<PurchaseApplyOrderVO>(0);
		int totalNum = 0;
		if(q!=null) {
			q.setStatusFlag(30);//查询已批的采购申请单
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
		q.setStatusFlag(30);
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

	@Transactional
	@Override
	public ResponseResult<PurchaseOrderRecvWrapper> recv(PurchaseOrderRecvWrapper p) {
		//数据检验
		ResponseResult<PurchaseOrderRecvWrapper> result = this.validateForRecv(p);
		if(result.isSuccess()==false) {
			return result;
		}
		try {
			p.setOperateOn(new Date());
			final String batchNo = UUIDUtils.genDateUUID(null);//生成统一批次号
			String recvBatchNo = "POR"+batchNo;//收货批次号
			String storageBatchNo="SBN"+batchNo;//入库批次号
			String adjustBatchNo="IVA"+batchNo;//库存调整批次号
			
			long purchaseApplyOrderId = p.getPurchaseApplyOrderId();//采购订单主键
			PurchaseApplyOrderVO queryApplyOrder = this.applyOrderMapper.selectByPrimaryKey(purchaseApplyOrderId);//获取采购订单
			PurchaseApplyOrder applyOrder = new PurchaseApplyOrder();
			applyOrder.setId(queryApplyOrder.getId());
			applyOrder.setVersion(queryApplyOrder.getVersion());
			applyOrder.setUpdateBy(p.getOperatorId());
			applyOrder.setUpdateOn(p.getOperateOn());
			applyOrder.setStatusFlag(50);
			applyOrder.setRealReceiveDate(p.getOperateOn());
			
			StorageLocation location = getWarehouseLocation(p.getPurchaseApplyOrderId());//获取可售库位
			List<PurchaseOrderRecvVO> recvList = p.getRecvList();
			for(PurchaseOrderRecvVO v: recvList) {//循环处理每批物料
				long purchaseApplyOrderDetailId = v.getPurchaseApplyOrderDetailId();
				PurchaseApplyOrderDetail purchaseApplyOrderDetail = applyOrderDetailMapper.selectByPrimaryKey(purchaseApplyOrderDetailId);
				List<PurchaseOrderRecvDetailVO> detailList = v.getDetailList();
				
				//写入采购收货表
				List<PurchaseOrderRecv> orderRecvList =FluentIterable.from(detailList).transform(new Function<PurchaseOrderRecvDetailVO,PurchaseOrderRecv>() {
					@Override
					public PurchaseOrderRecv apply(PurchaseOrderRecvDetailVO input) {
						return new PurchaseOrderRecv(purchaseApplyOrderDetailId, recvBatchNo, input.getBatchNo(),input.getSnNo(),input.getImieNo());
					}
				}).toList();
				orderRecvMapper.batchInsert(orderRecvList);
				
				//处理物流差异
				if(orderRecvList.size()!=purchaseApplyOrderDetail.getApprovedQuantity().intValue()) {
					//实收数量
					purchaseApplyOrderDetail.setRealRecvQuantity(orderRecvList.size());
					//差异数量=收货数量 -批准数量
					purchaseApplyOrderDetail.setDiffQuantity(purchaseApplyOrderDetail.getRealRecvQuantity().intValue()-purchaseApplyOrderDetail.getApprovedQuantity().intValue());
					this.applyOrderDetailMapper.updateRecvQuantity(purchaseApplyOrderDetail);
					//记录物流差异
					StorageLocation diffLocation = this.locationMapper.queryLocation(queryApplyOrder.getWarehouseInfoId(), "DELIVER_GAP");//物流差异仓位
					this.recordService.insertChangeRecord(purchaseApplyOrderDetail.getMaterielBasicId(), queryApplyOrder.getPurchaseNo(), queryApplyOrder.getWarehouseInfoId(), diffLocation.getId(), p.getOperateOn(), AdjustTypeEnum.TYPE_PURCHASE, adjustBatchNo, AdjustReasonEnum.REASON_DIFF, storageBatchNo, purchaseApplyOrderDetail.getDiffQuantity(), p.getOperatorId(),p.getOperatorName());
					//写入库存差异，统一物料库位统计
					List<WarehouseCommodityInfo> diffCommodList = new ArrayList<WarehouseCommodityInfo>();
					if(purchaseApplyOrderDetail.getDiffQuantity().intValue()<0) {//收货少于批准数量时，IMEI,SN号插空值
						int count =Math.abs(purchaseApplyOrderDetail.getDiffQuantity().intValue());
						for(int i=0;i<count;i++) {
							WarehouseCommodityInfo commodityInfo = new WarehouseCommodityInfo();
							commodityInfo.setWarehouseId(queryApplyOrder.getWarehouseInfoId());
							commodityInfo.setWarehouseLocationId(diffLocation.getId());//物流差异库位
							commodityInfo.setMaterielBasicId(purchaseApplyOrderDetail.getMaterielBasicId());
							commodityInfo.setBatchNo("");
							commodityInfo.setSnNo("");
							commodityInfo.setImieNo("");
							commodityInfo.setStorageBatchNo(storageBatchNo);
							commodityInfo.setOperatorId(p.getOperatorId());
							commodityInfo.setOperatorName(p.getOperatorName());
							commodityInfo.setOperateOn(p.getOperateOn());
							diffCommodList.add(commodityInfo);
						}
					}else {
						for(int i=purchaseApplyOrderDetail.getApprovedQuantity();i<purchaseApplyOrderDetail.getRealRecvQuantity();i++) {//收货大于批准数量时，IMEI,SN号插页面输入值
							WarehouseCommodityInfo commodityInfo = new WarehouseCommodityInfo();
							commodityInfo.setWarehouseId(queryApplyOrder.getWarehouseInfoId());
							commodityInfo.setWarehouseLocationId(diffLocation.getId());//物流差异库位
							commodityInfo.setMaterielBasicId(purchaseApplyOrderDetail.getMaterielBasicId());
							commodityInfo.setBatchNo(detailList.get(i).getBatchNo());
							commodityInfo.setSnNo(detailList.get(i).getSnNo());
							commodityInfo.setImieNo(detailList.get(i).getImieNo());
							commodityInfo.setStorageBatchNo(storageBatchNo);
							commodityInfo.setOperatorId(p.getOperatorId());
							commodityInfo.setOperatorName(p.getOperatorName());
							commodityInfo.setOperateOn(p.getOperateOn());
							diffCommodList.add(commodityInfo);
						}
					}
					if(CollectionUtils.isNotEmpty(diffCommodList)) {
						this.commodityInfoMapper.batchInsert(diffCommodList);
					}
				}
			
				
				//写入库存
				List<WarehouseCommodityInfo> commodityList = FluentIterable.from(detailList).transform(new Function<PurchaseOrderRecvDetailVO,WarehouseCommodityInfo>() {
					@Override
					public WarehouseCommodityInfo apply(PurchaseOrderRecvDetailVO input) {
						WarehouseCommodityInfo commodityInfo = new WarehouseCommodityInfo();
						commodityInfo.setWarehouseId(queryApplyOrder.getWarehouseInfoId());
						commodityInfo.setWarehouseLocationId(location.getId());
						commodityInfo.setMaterielBasicId(purchaseApplyOrderDetail.getMaterielBasicId());
						commodityInfo.setBatchNo(input.getBatchNo());
						commodityInfo.setSnNo(input.getSnNo());
						commodityInfo.setImieNo(input.getImieNo());
						commodityInfo.setStorageBatchNo(storageBatchNo);
						commodityInfo.setOperatorId(p.getOperatorId());
						commodityInfo.setOperatorName(p.getOperatorName());
						commodityInfo.setOperateOn(p.getOperateOn());
						return commodityInfo;
					}
				}).toList();
				this.commodityInfoMapper.batchInsert(commodityList);
				
				//写入库存跟踪
				List<WarehouseCommodityTrack> trackList = FluentIterable.from(detailList).transform(new Function<PurchaseOrderRecvDetailVO,WarehouseCommodityTrack>() {
					@Override
					public WarehouseCommodityTrack apply(PurchaseOrderRecvDetailVO input) {
						WarehouseCommodityTrack track = new WarehouseCommodityTrack();
						track.setWarehouseId(queryApplyOrder.getWarehouseInfoId());
						track.setWarehouseLocationId(location.getId());
						track.setMaterielBasicId(purchaseApplyOrderDetail.getMaterielBasicId());
						track.setBatchNo(input.getBatchNo());
						track.setSnNo(input.getSnNo());
						track.setImieNo(input.getImieNo());
						track.setAdjustType(1);
						track.setAdjustBatchNo(adjustBatchNo);
						track.setStorageBatchNo(storageBatchNo);
						track.setSourceOrderNo(queryApplyOrder.getPurchaseNo());
						track.setOperatorId(p.getOperatorId());
						track.setOperatorName(p.getOperatorName());
						track.setAdjustReason("采购入库");
						track.setOperateOn(p.getOperateOn());
						return track;
					}
				}).toList();
				trackMapper.batchInsert(trackList);
				
				//写入库存变化表
				WarehouseChangeRecord changeRecord = new WarehouseChangeRecord();
				changeRecord.setWarehouseId(queryApplyOrder.getWarehouseInfoId());
				changeRecord.setWarehouseLocationId(location.getId());
				changeRecord.setMaterielBasicId(purchaseApplyOrderDetail.getMaterielBasicId());
				changeRecord.setStorageBatchNo(storageBatchNo);
				changeRecord.setAdjustType(1);
				changeRecord.setAdjustBatchNo(adjustBatchNo);
				changeRecord.setAdjustReason("采购入库");
				changeRecord.setSourceOrderNo(queryApplyOrder.getPurchaseNo());
				changeRecord.setChangeQuantity(orderRecvList.size());
				changeRecord.setOperatorId(p.getOperatorId());
				changeRecord.setOperatorName(p.getOperatorName());
				changeRecord.setOperateOn(p.getOperateOn());
				this.changeRecordMapper.insert(changeRecord);
			}
			//修改采购申请单
			if(this.applyOrderMapper.updateStatusFlag(applyOrder)>0) {
				return ResponseResult.buildSuccessResponse();
			}else {
				throw new ServiceException("并发修改异常");
			}
		} catch (Exception e) {
			log.error("采购收货发生异常：{}",e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	/** 
	* @Description: 收货时验证数据
	* @param p
	* @return ResponseResult<PurchaseOrderRecvWrapper>
	*/
	@Transactional(propagation=Propagation.SUPPORTS)
	private ResponseResult<PurchaseOrderRecvWrapper> validateForRecv(PurchaseOrderRecvWrapper p) {
		if(p==null||CollectionUtils.isEmpty(p.getRecvList())) {
			return ResponseResult.build(ResponseStatus.DATA_INPUT_ERROR.getCode(), ResponseStatus.DATA_INPUT_ERROR.getMessage(), null);
		}
		Set<ConstraintViolation<PurchaseOrderRecvWrapper>> vr=this.validator.validate(p, Default.class);
		if(CollectionUtils.isNotEmpty(vr)) {//检查是否存在验证错误
			Iterator<ConstraintViolation<PurchaseOrderRecvWrapper>> it = vr.iterator();
			StringBuffer errors = new StringBuffer();
			String delimter=";";
			while(it.hasNext()) {//此处在快速失败模式下，只会有一条错误信息，普通模式会有多条
				errors.append(it.next().getMessage()).append(delimter);
			}
			String errMsg = errors.substring(0, errors.length()-delimter.length());
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), errMsg, p);
		}
		
		PurchaseApplyOrderVO applyOrder = this.applyOrderMapper.selectByPrimaryKey(p.getPurchaseApplyOrderId());
		if(applyOrder==null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "采购申请单不存在", p);
		}
		if(applyOrder.getStatusFlag().intValue()!=30) {
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "采购申请单状态错误", p);
		}
		//根据采购订单查询仓库是否存在可售库位数量
		StorageLocation location = getWarehouseLocation(p.getPurchaseApplyOrderId());
		if(location==null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "该采购订单目前无可售仓位", p);
		}
		//验证数据存在性，包括：采购申请物料的存在性
		List<PurchaseOrderRecvVO> recvList = p.getRecvList();
		HashSet<String> snSet = new HashSet<String>();
		HashSet<String> imieSet = new HashSet<String>();
		//验证SN，IMIE全局唯一
		for(PurchaseOrderRecvVO recv : recvList) {
			for(PurchaseOrderRecvDetailVO detail :recv.getDetailList()) {
				if(snSet.contains(detail.getSnNo())){
					return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "收货采购订单存在重复sn号", p); 
				}else {
					snSet.add(detail.getSnNo());
				}
				if(imieSet.contains(detail.getImieNo())) {
					return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "收货采购订单存在重复IMEI号", p); 
				}else {
					imieSet.add(detail.getImieNo());
				}
			}
		}
		List<Long> idList =FluentIterable.from(recvList).transform(new Function<PurchaseOrderRecvVO,Long>() {
			@Override
			public Long apply(PurchaseOrderRecvVO input) {
				return input.getPurchaseApplyOrderDetailId();
			}
		}).toList();
		int count = this.applyOrderDetailMapper.queryCountByIDList(idList);
		if(idList.size()!=count) {
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), "某些采购申请物料不存在", p);
		}
		return ResponseResult.buildSuccessResponse(p);
	}

	/** 
	* @Description: 获取仓位
	* @param  p
	* @return StorageLocation
	*/
	private StorageLocation getWarehouseLocation(Long purchaseApplyOrderId) {
		StorageLocationQuery locationQuery = new StorageLocationQuery();
		locationQuery.setLocationName("可售");
		locationQuery.setLocationCode("AVAILABLE");
		locationQuery.setEnableFlag(1);
		locationQuery.setPurchaseApplyOrderId(purchaseApplyOrderId);
		return this.applyOrderMapper.queryWarehouseLocation(locationQuery);
	}

}
