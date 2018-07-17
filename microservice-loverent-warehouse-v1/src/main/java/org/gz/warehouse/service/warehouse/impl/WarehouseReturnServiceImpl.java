package org.gz.warehouse.service.warehouse.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.UUIDUtils;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.constants.MaterielStatusEnum;
import org.gz.warehouse.constants.ProductTypeEnum;
import org.gz.warehouse.constants.WarehouseReturnEnum;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfo;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrack;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseReturnImages;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecord;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecordVO;
import org.gz.warehouse.feign.OrderService;
import org.gz.warehouse.mapper.warehouse.StorageLocationMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityTrackMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseReturnMapper;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.gz.warehouse.service.warehouse.WarehouseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class WarehouseReturnServiceImpl implements WarehouseReturnService {

	@Autowired
	private WarehouseReturnMapper returnMapper;

	@Autowired
	private WarehouseInfoMapper warehouseInfoMapper;

	@Autowired
	private StorageLocationMapper locationMapper;

	@Autowired
	private WarehouseCommodityInfoMapper commodityInfoMapper;

	@Autowired
	private WarehouseChangeRecordService recordService;

	@Autowired
	private WarehouseCommodityTrackMapper trackMapper;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MaterielBasicInfoService basicInfoService;

	@Override
	public ResponseResult<ResultPager<WarehouseReturnRecord>> queryPageList(WarehouseReturnRecordVO returnRecord) {
		int totalCount = returnMapper.queryPageCount(returnRecord);
		List<WarehouseReturnRecord> list = new ArrayList<>();
		if (totalCount > 0) {
			list = this.returnMapper.queryPageList(returnRecord);
			 list = Lists.transform(list, new Function<WarehouseReturnRecord, WarehouseReturnRecord>() {
				@Override
				public WarehouseReturnRecord apply(WarehouseReturnRecord w) {
					w.setAttaList(new ArrayList<>());
					if (w.getTransitlnStatus() == 10) {
						w.setTransitlnName(WarehouseReturnEnum.TRANSITL_MIDWAY.getName());
					} else if (w.getTransitlnStatus() == 20) {
						w.setTransitlnName(WarehouseReturnEnum.TRANSITL_ALREADY.getName());
					}
					return w;
				}
			});
		}
		ResultPager<WarehouseReturnRecord> data = new ResultPager<WarehouseReturnRecord>(totalCount,
				returnRecord.getCurrPage(), returnRecord.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<WarehouseReturnRecord> transitln(WarehouseReturnRecord returnRecord) {
		ResponseResult<WarehouseReturnRecord> vr = this.checkTransitln(returnRecord);
		if (vr.isSuccess()) {
			returnRecord.setTransitlnStatus(WarehouseReturnEnum.TRANSITL_ALREADY.getCode());
			returnRecord.setOperateOn(new Date());
			// 更新入库记录：还机中->已入库
			this.returnMapper.updateRecord(returnRecord);
			// 附件列表
			List<WarehouseReturnImages> imgList = returnRecord.getAttaList();
			if(CollectionUtils.isNotEmpty(imgList)){
				List<WarehouseReturnImages> newImgList = new ArrayList<WarehouseReturnImages>();
				for(WarehouseReturnImages img : imgList) {
					if(StringUtils.hasText(img.getAttaUrl())) {
						img.setReturnRecordId(returnRecord.getId());
						newImgList.add(img);
					}
				}
				if(CollectionUtils.isNotEmpty(newImgList)) {
					// 批量插入新附件
					returnMapper.insertRecordImages(newImgList);
				}
			}
			// 按订单状态调整库位
			this.adjustWarehouseLocationForIn(returnRecord);
			// 向订单系统发送已入库通知
			return sureReturned(returnRecord);
		}
		return vr;
	}

	/**
	 * @Description: @param @param returnRecord @param @return @return
	 *               ResponseResult<WarehouseReturnRecord> @throws
	 */
	private ResponseResult<WarehouseReturnRecord> sureReturned(WarehouseReturnRecord returnRecord) {
		UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
		updateOrderState.setRentRecordNo(returnRecord.getSourceOrderNo());
		updateOrderState.setState(returnRecord.getSourceOrderStatus());
		updateOrderState.setCreateBy(returnRecord.getOperatorId());
		updateOrderState.setCreateMan(returnRecord.getOperatorName());
		updateOrderState.setDiscountFee(new BigDecimal(returnRecord.getDamagePrice()));
		ResponseResult<String> result = this.orderService.sureReturned(updateOrderState);
		if (result.isSuccess()) {
			return ResponseResult.buildSuccessResponse(returnRecord);
		} else {
			throw new ServiceException("调用订单系统修改入库状态失败！");
		}
	}

	/**
	 * @Description: 入库验证 @param returnRecord @return
	 *               ResponseResult<WarehouseReturnRecord> @throws
	 */
	private ResponseResult<WarehouseReturnRecord> checkTransitln(WarehouseReturnRecord returnRecord) {
		WarehouseReturnRecord queryRecord = this.returnMapper.selectByPrimaryKey(returnRecord.getId());
		if (queryRecord == null) {
			return ResponseResult.build(1000, "无此还机记录", null);
		}
		if (queryRecord.getTransitlnStatus().intValue() != WarehouseReturnEnum.TRANSITL_MIDWAY.getCode()) {
			return ResponseResult.build(1000, "入库时，还机状态错误", null);
		}
		if (!queryRecord.getSnNo().equals(returnRecord.getSnNo())
				|| !queryRecord.getImieNo().equals(returnRecord.getImieNo())) {
			return ResponseResult.build(1000, "入库时，所传SN,IMEI与还机记录不匹配", null);
		}
		returnRecord.setSourceOrderStatus(queryRecord.getSourceOrderStatus());
		returnRecord.setMaterielBasicId(queryRecord.getMaterielBasicId());
		returnRecord.setSourceOrderNo(queryRecord.getSourceOrderNo());
		return ResponseResult.buildSuccessResponse(returnRecord);
	}

	/**
	 * 
	 * @Description:
	 * @param r
	 */
	private void adjustWarehouseLocationForIn(WarehouseReturnRecord r) {
		WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode("S011");// 新机库
		StorageLocation transitInLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "TRANSIT_IN");// 入库在途仓位
		/**
		 * a) 对于归还中、提前解约中、强制归还中的订单， 在完成入库后，入库在途-1， 如果入库物料状态选择“新机”，则可售库存+1；
		 * 如果入库物料状态选择“好机”，则好机库存+1； 如果入库物料状态选择“坏机”，则坏机库存+1；
		 * 
		 * b)对于“退货中”的订单，在完成入库后，入库在途-1，可售库存 +1；
		 */
		int sourceOrderStatus = r.getSourceOrderStatus().intValue();
		// 归还中、提前解约中、强制归还中、退货中
		if (sourceOrderStatus == BackRentState.Recycling.getCode()
				|| sourceOrderStatus == BackRentState.PrematureTerminating.getCode()
				|| sourceOrderStatus == BackRentState.ForceRecycleIng.getCode()
				|| sourceOrderStatus == BackRentState.ReturnGoodIng.getCode()) {
			List<WarehouseCommodityInfo> commodityList = this.queryCommodityList(r, newWarehouse.getId(),
					transitInLocation.getId());
			if (CollectionUtils.isEmpty(commodityList)) {
				throw new ServiceException(1000, "入库失败，入库在途仓位中无此商品");
			}
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);
			int materielStatus = r.getMaterielStatus().intValue();
			Integer destLocationId = null;// 目标仓位ID
			if (materielStatus == MaterielStatusEnum.NEW.getCode()
					|| sourceOrderStatus == BackRentState.ReturnGoodIng.getCode()) {
				StorageLocation availableLocation = this.locationMapper.queryLocation(newWarehouse.getId(),
						"AVAILABLE");// 可售仓位
				// 新机时仓位变更：入库在途->可售,记录库存变化：入库在途-1，可售+1
				destLocationId = availableLocation.getId();// 可售
			} else if (materielStatus == MaterielStatusEnum.GOOD.getCode()) {
				StorageLocation goodLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "GOOD");// 好机仓位
				// 好机时仓位变更：入库在途->好机,记录库存变化：入库在途-1，好机+1
				destLocationId = goodLocation.getId();
			} else if (materielStatus == MaterielStatusEnum.BAD.getCode()) {
				StorageLocation badLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "FAULTY");// 坏机仓位
				// 坏机时仓位变更：入库在途->坏机,记录库存变化：入库在途-1，坏机+1
				destLocationId = badLocation.getId();
			} else {
				throw new ServiceException(1000, "入库失败，非法物料状态");
			}
			adjustReturnLocation(r, newWarehouse, destLocationId, now, adjustBatchNo, commodity);
		}
	}

	/**
	 * @Description: @param @param r @param @param newWarehouse @param @param
	 * destLocationId @param @param now @param @param adjustBatchNo @param @param
	 * commodity @return void @throws
	 */
	private void adjustReturnLocation(WarehouseReturnRecord r, WarehouseInfo newWarehouse, Integer destLocationId,
			Date now, String adjustBatchNo, WarehouseCommodityInfo commodity) {
		// 更新库位
		this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), destLocationId);
		// 记录库存变化
		this.recordService.insertChangeRecord(r.getMaterielBasicId(), r.getSourceOrderNo(), commodity.getWarehouseId(),
				commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo,
				AdjustReasonEnum.REASON_IN, commodity.getStorageBatchNo(), -1, r.getOperatorId(), r.getOperatorName());
		this.recordService.insertChangeRecord(r.getMaterielBasicId(), r.getSourceOrderNo(), newWarehouse.getId(),
				destLocationId, now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, AdjustReasonEnum.REASON_IN,
				commodity.getStorageBatchNo(), 1, r.getOperatorId(), r.getOperatorName());
		// 记录库存跟踪
		this.insertNewTrack(r.getMaterielBasicId(), r.getSourceOrderNo(), now, adjustBatchNo,
				AdjustReasonEnum.REASON_IN.getReason(), commodity, newWarehouse.getId(), destLocationId,
				r.getOperatorId(), r.getOperatorName());
	}

	private int insertNewTrack(Long materielBasicId, String sourceOrderNo, Date now, String adjustBatchNo,
			String adjustReason, WarehouseCommodityInfo commodity, Integer newWarehouseId, Integer newLocationId,
			Long operatorId, String operatorName) {
		WarehouseCommodityTrack newTrack = new WarehouseCommodityTrack();
		newTrack.setAdjustBatchNo(adjustBatchNo);
		newTrack.setAdjustType(2);
		newTrack.setAdjustReason(adjustReason);
		newTrack.setBatchNo(commodity.getBatchNo());
		newTrack.setImieNo(commodity.getImieNo());
		newTrack.setMaterielBasicId(materielBasicId);
		newTrack.setOperateOn(now);
		newTrack.setOperatorId(operatorId);
		newTrack.setOperatorName(operatorName);
		newTrack.setSnNo(commodity.getSnNo());
		newTrack.setSourceOrderNo(sourceOrderNo);
		newTrack.setStorageBatchNo(commodity.getStorageBatchNo());
		newTrack.setWarehouseId(newWarehouseId);
		newTrack.setWarehouseLocationId(newLocationId);
		return this.trackMapper.insert(newTrack);
	}

	/**
	 * @Description: 将commodityId记录修改为新仓库，新仓位
	 * @param now
	 *            更新时间
	 * @param commodityId
	 *            源商品库存记录ID
	 * @param newWarehouseId
	 *            新仓库ID
	 * @param newLocationId
	 *            新库位ID
	 * @return int
	 */
	private int updateWarehouseLocation(Date now, Long commodityId, Integer newWarehouseId, Integer newLocationId) {
		WarehouseCommodityInfo updateCommodity = new WarehouseCommodityInfo();
		updateCommodity.setId(commodityId);
		updateCommodity.setWarehouseId(newWarehouseId);
		updateCommodity.setWarehouseLocationId(newLocationId);
		updateCommodity.setOperateOn(now);
		return this.commodityInfoMapper.updateWarehouseLocation(updateCommodity);
	}

	/**
	 * @Description:
	 * @param
	 * @param
	 * @return WarehouseCommodityInfoQuery
	 */
	private List<WarehouseCommodityInfo> queryCommodityList(WarehouseReturnRecord returnRecord, Integer warehouseId,
			Integer locationId) {
		WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
		commodityQuery.setCurrPage(1);
		commodityQuery.setPageSize(1);
		commodityQuery.setMaterielBasicId(returnRecord.getMaterielBasicId());
		commodityQuery.setWarehouseId(warehouseId);
		commodityQuery.setWarehouseLocationId(locationId);
		commodityQuery.setSnNo(returnRecord.getSnNo());
		commodityQuery.setBatchNo(returnRecord.getBatchNo());
		commodityQuery.setImieNo(returnRecord.getImieNo());
		return this.commodityInfoMapper.queryPageList(commodityQuery);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<WarehouseReturnRecord> updateRecord(WarehouseReturnRecord returnRecord) {
		if (returnMapper.updateRecord(returnRecord) <= 0) {
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),
					ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
		}
		// 附件列表
		List<WarehouseReturnImages> list = returnRecord.getAttaList();
		if (list.size() > 0) {
			Long id = returnRecord.getId(); // 还机记录ID
			// 删除原始附件
			returnMapper.deleteOriginalImages(id);
			// 批量插入新附件
			returnMapper.insertRecordImages(list);
		}
		return ResponseResult.buildSuccessResponse(returnRecord);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<?> deleteRecord(String id) {
		String[] ids = id.split(",");
		Long[] idArr = new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			idArr[i] = Long.valueOf(ids[i]);
		}
		// 删除还机记录附件表数据
		returnMapper.deleteRecordImages(idArr);
		// 删除还机记录表数据
		if (returnMapper.deleteRecord(idArr) <= 0) {
			return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(),ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<WarehouseReturnRecord> queryRecordDetail(Long id) {
		WarehouseReturnRecord detail = returnMapper.selectByPrimaryKey(id);
		List<WarehouseReturnImages> attaList = null;
		if(detail!=null) {
			attaList = returnMapper.queryRecordDetail(id);
		}
		detail.setAttaList(attaList);
		return ResponseResult.buildSuccessResponse(detail);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<ApplyReturnReq> applyReturn(ApplyReturnReq req) {
		// 判断此订单是否已经入表，若是则不予处理
		WarehouseReturnRecordVO countQuery = new WarehouseReturnRecordVO();
		countQuery.setSourceOrderNo(req.getSourceOrderNo());
		int count = this.returnMapper.queryPageCount(countQuery);
		if (count > 0) {
			return ResponseResult.build(1000, "该订单已申请还库", req);
		}
		// 查询物料基础信息
		ResponseResult<MaterielBasicInfo> materielBasicInfoQueryResult = this.basicInfoService.getDetail(req.getMaterielBasicId());
		if (materielBasicInfoQueryResult.getData() == null || materielBasicInfoQueryResult.isSuccess() == false) {
			return ResponseResult.build(1000, "无此物料", req);
		}
		//还需要判断库存商品映射表是否存在此商品(物料ID+SN+IMEI)
		WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
		commodityQuery.setCurrPage(1);
		commodityQuery.setPageSize(1);
		commodityQuery.setMaterielBasicId(req.getMaterielBasicId());
		commodityQuery.setSnNo(req.getSnNo());
		commodityQuery.setBatchNo(req.getBatchNo());
		commodityQuery.setImieNo(req.getImieNo());
		List<WarehouseCommodityInfo> commodityList = this.commodityInfoMapper.queryPageList(commodityQuery);
		if(CollectionUtils.isEmpty(commodityList)) {
			return ResponseResult.build(1000, "无此商品", req);
		}
		WarehouseCommodityInfo commodityInfo = commodityList.get(0);
		MaterielBasicInfo materielBasicInfo = materielBasicInfoQueryResult.getData();
		WarehouseReturnRecord r = new WarehouseReturnRecord();
		r.setBatchNo(commodityInfo.getBatchNo());
		r.setImieNo(req.getImieNo());
		r.setMaterielBasicId(req.getMaterielBasicId());
		r.setMaterielCode(materielBasicInfo.getMaterielCode());
		r.setMaterielModelId(materielBasicInfo.getMaterielModelId());
		r.setMaterielModelName(materielBasicInfo.getMaterielModelName());
		r.setMaterielName(materielBasicInfo.getMaterielName());
		r.setMaterielSpecValue(materielBasicInfo.getSpecValues());
		r.setMaterielUnitId(materielBasicInfo.getMaterielUnitId());
		r.setUnitName(materielBasicInfo.getUnitName());
		r.setOperateOn(new Date());
		r.setOperatorId(1L);
		r.setOperatorName("管理员");
		r.setSourceOrderNo(req.getSourceOrderNo());
		r.setReturnApplyTime(req.getReturnApplyTime());
		r.setSnNo(req.getSnNo());
		r.setSourceOrderStatus(req.getSourceOrderStatus());
		r.setLogisticsNo(req.getLogisticsNo());
		r.setTransitlnStatus(WarehouseReturnEnum.TRANSITL_MIDWAY.getCode());
		r.setTransitInType(req.getProductType().intValue()==ProductTypeEnum.TYPE_SALE.getCode()? 20 : 10);
		r.setProductId(req.getProductId());
		r.setProductType(req.getProductType());
		ResponseResult<ApplyReturnReq> result = this.adjustWarehouseLocationForApply(r);
		if (result.isSuccess()) {
			return this.returnMapper.inserRecord(r) > 0 ? ResponseResult.buildSuccessResponse(req): ResponseResult.build(1000, "数据库操作失败", null);
		}
		return result;
	}

	/**
	 * @Description: 按订单状态调整库位
	 * @param r
	 */
	private ResponseResult<ApplyReturnReq> adjustWarehouseLocationForApply(WarehouseReturnRecord r) {
		ResponseResult<ApplyReturnReq> result = ResponseResult.build(1000, "参数错误", null);
		int orderStatus = r.getSourceOrderStatus().intValue();
		if (orderStatus != BackRentState.Recycling.getCode()
				&&orderStatus != BackRentState.ForceRecycleIng.getCode()
				&&orderStatus != BackRentState.PrematureTerminating.getCode()
				&& orderStatus != BackRentState.ReturnGoodIng.getCode()) {
			return result;
		}
		WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode("S011");// 新机库
		StorageLocation transitInLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "TRANSIT_IN");// 入库在途仓位
		Integer warehouseId = newWarehouse.getId();
		Date now = new Date();
		String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
		List<WarehouseCommodityInfo> commodityList = null;
		WarehouseCommodityInfo commodity = null;
		Integer destLocationId = null;
		// a)对于变为“归还中”和“强制归还中”的订单，对应的库存调整为：在租-1，入库在途+1，若在租库位无此订单占用的库存，则调整失败；
		if (orderStatus == BackRentState.Recycling.getCode()
				|| orderStatus == BackRentState.ForceRecycleIng.getCode()) {
			StorageLocation rentingLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "RENTING");// 在租仓位
			commodityList = this.queryCommodityList(r, warehouseId, rentingLocation.getId());
		}
		// b)对于变为提前解约中和退货中的订单，对应的库存调整为：出库在途-1，入库在途+1，如出库在途无此订单占用的库存，则调整失败；
		if (orderStatus == BackRentState.PrematureTerminating.getCode()
				|| orderStatus == BackRentState.ReturnGoodIng.getCode()) {
			StorageLocation transitOutLocation = this.locationMapper.queryLocation(newWarehouse.getId(), "TRANSIT_OUT");// 出库在途仓位
			commodityList = this.queryCommodityList(r, warehouseId, transitOutLocation.getId());
		}
		if (CollectionUtils.isNotEmpty(commodityList)) {
			commodity = commodityList.get(0);
			destLocationId = transitInLocation.getId();
			this.adjustReturnLocation(r, newWarehouse, destLocationId, now, adjustBatchNo, commodity);
			result = ResponseResult.buildSuccessResponse(null);
		}
		return result;
	}
}
