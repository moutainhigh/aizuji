/**
 * 
 */
package org.gz.warehouse.service.warehouse.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.hv.group.UpdateRecordGroup;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.RentRecordReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ConfirmSignVO;
import org.gz.warehouse.common.vo.OrderSourceEnum;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.UndoPickReq;
import org.gz.warehouse.common.vo.WarehouseCodeEnum;
import org.gz.warehouse.common.vo.WarehouseLocationCodeEnum;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.gz.warehouse.config.InsuredAmountConfig;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.constants.ProductTypeEnum;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecord;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfo;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrack;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecord;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecordQuery;
import org.gz.warehouse.feign.AliOrderService;
import org.gz.warehouse.feign.ApplyOrderRequest;
import org.gz.warehouse.feign.IThirdPartService;
import org.gz.warehouse.feign.OrderService;
import org.gz.warehouse.feign.ResultCode;
import org.gz.warehouse.mapper.MaterielBasicInfoMapper;
import org.gz.warehouse.mapper.warehouse.StorageLocationMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseChangeRecordMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityTrackMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehousePickingRecordMapper;
import org.gz.warehouse.service.product.ProductService;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.gz.warehouse.service.warehouse.WarehouseOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 商品出库业务类
 * @author hxj
 * @Description:
 * @date 2017年12月29日 上午10:07:52
 */
@Service
@Slf4j
public class WarehouseOutServiceImpl implements WarehouseOutService {

	@Autowired
	private WarehouseInfoMapper warehouseInfoMapper;

	@Autowired
	private StorageLocationMapper locationMapper;

	@Autowired
	private WarehouseCommodityTrackMapper trackMapper;

	@Autowired
	private WarehouseCommodityInfoMapper commodityInfoMapper;

	@Autowired
	private WarehouseChangeRecordMapper changeRecordMapper;

	@Autowired
	private WarehousePickingRecordMapper pickMapper;

	@Autowired
	private MaterielBasicInfoMapper materielBasicInfoMapper;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AliOrderService aliOrderService;

	@Autowired
	private WarehouseChangeRecordService recordService;

	@Autowired
	private IThirdPartService thirdPartService;

	@Autowired
	private InsuredAmountConfig insuredAmountConfig;

	@Autowired
	private ProductService productService;

	@Autowired
	private Validator validator;

	private static final String WAREHOUSE_NEW = WarehouseCodeEnum.NEW.getCode();// 新机库

	private static final String LOCATION_AVAILABLE = WarehouseLocationCodeEnum.AVAILABLE.getCode();// 可售库位

	private static final String LOCATION_SALES = WarehouseLocationCodeEnum.SALES.getCode();// 待售库位

	private static final String LOCATION_RESERVE = WarehouseLocationCodeEnum.RESERVE.getCode();// 冻结库位

	private static final String LOCATION_TRANSIT_OUT = WarehouseLocationCodeEnum.TRANSIT_OUT.getCode();// 出库在途库位

	private static final String LOCATION_RENTING = WarehouseLocationCodeEnum.RENTING.getCode();// 在租库位

	private static final String LOCATION_BUY_END = WarehouseLocationCodeEnum.BUY_END.getCode();// 买断库位

	private static final int ORDERSOURCE_APP = OrderSourceEnum.APP.getCode();// APP订单来源

	private static final int ORDERSOURCE_Applets = OrderSourceEnum.Applets.getCode();// 小程序订单来源

	@Transactional
	@Override
	public ResponseResult<WarehousePickingRecord> pick(WarehousePickingRecord q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		boolean flag = false;
		try {
			/*********************** 验证流程开始 **************************/
			List<WarehouseCommodityInfo> commodityList = null;
			WarehouseCommodityInfo commodity = null;
			if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {// app处理逻辑
				// 判断订单号有无进行过签约冻结操作,若无则提示：拣货失败，提示该订单尚未进行过签约冻结操作
				List<WarehouseCommodityTrack> trackList = this.queryTrackList(q.getMaterielBasicId(), WAREHOUSE_NEW,
						LOCATION_RESERVE, q.getSourceOrderNo());
				if (CollectionUtils.isEmpty(trackList)) {
					return ResponseResult.build(WarehouseErrorCode.PICK_NOT_SIGN_ERROR.getCode(),
							WarehouseErrorCode.PICK_NOT_SIGN_ERROR.getMessage(), null);
				}
				WarehouseCommodityTrack track = trackList.get(0);
				if (!track.getSnNo().equals(q.getSnNo()) || !track.getImieNo().equals(q.getImieNo())) {
					return ResponseResult.build(WarehouseErrorCode.PICK_UNKNOW_COMMODITY_ERROR.getCode(),
							WarehouseErrorCode.PICK_UNKNOW_COMMODITY_ERROR.getMessage(), null);
				}

				// 判断商品是否存在于冻结库位,若无则提示：拣货失败，提示冻结库位暂时无库存
				// 使用batchNo,snNo,imieNo检查新机库冻结库位
				commodityList = this.queryCommodityListByTrack(track);
			}
			if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {
				// 小程序不需要冻结库存，根据前端传的SN,IMEI来拣货，然后转移到待售库位
				WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
				commodityQuery.setCurrPage(1);
				commodityQuery.setPageSize(1);
				commodityQuery.setMaterielBasicId(q.getMaterielBasicId());
				commodityQuery.setWarehouseCode(WAREHOUSE_NEW);
				commodityQuery.setLocationCode(LOCATION_AVAILABLE);
				//指定商品拣货逻辑
				commodityQuery.setImieNo(q.getImieNo());
				commodityQuery.setSnNo(q.getSnNo());
				commodityList = this.commodityInfoMapper.queryPageList(commodityQuery);
			}
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.PICK_LOCATION_ERROR.getCode(),
						WarehouseErrorCode.PICK_LOCATION_ERROR.getMessage(), null);
			}
			commodity = commodityList.get(0);
			/*********************** 验证流程结束 **************************/
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation saleLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_SALES);// 待售仓位
			// 库位变更：冻结/可售->待售
			this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), saleLocation.getId());
			// 记录库存变化：冻结库位/可售-1，待售+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_PICK, commodity.getStorageBatchNo(), -1, q.getOperatorId(),
					q.getOperatorName());
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					saleLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, AdjustReasonEnum.REASON_PICK,
					commodity.getStorageBatchNo(), 1, q.getOperatorId(), q.getOperatorName());

			// 记录库存跟踪
			this.insertNewTrack(q.getMaterielBasicId(), q.getSourceOrderNo(), now, adjustBatchNo,
					AdjustReasonEnum.REASON_PICK.getReason(), commodity, newWarehouse.getId(), saleLocation.getId());

			// 写拣货记录
			q.setStatusFlag(BackRentState.WaitSend.getCode());// 待发货状态
			q.setApplyDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(q.getApplyTimes()));
			MaterielBasicInfoQuery m = new MaterielBasicInfoQuery();// 修正数据
			m.setCurrPage(1);
			m.setPageSize(1);
			m.setId(q.getMaterielBasicId());
			MaterielBasicInfoVO basicInfo = this.materielBasicInfoMapper.queryPageList(m).get(0);
			q.setMaterielName(basicInfo.getMaterielName() + "");
			q.setMaterielModelName(basicInfo.getMaterielModelName());
			q.setMaterielCode(basicInfo.getMaterielCode() + "");
			q.setMaterielUnitName(basicInfo.getUnitName() + "");
			q.setMaterielSpecValue(basicInfo.getSpecValues() + "");
			q.setBatchNo(commodity.getBatchNo());
			q.setImieNo(commodity.getImieNo());
			q.setSnNo(commodity.getSnNo());
			q.setOperateOn(now);
			q.setMaterielSpecValue(basicInfo.getSpecValues());
			q.setMaterielUnitName(basicInfo.getUnitName());
			ProductInfo productInfo = this.productService.getProductInfoById(q.getProductId());
			if(productInfo!=null&&AssertUtils.isPositiveNumber4Int(productInfo.getProductType())) {
				q.setProductType(productInfo.getProductType());
			}
			log.info("拣货时，入库参数：{}",JsonUtils.toJsonString(q));
			this.pickMapper.insert(q);
			flag = true;
		} catch (Exception e) {
			log.error("拣货失败：" + e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.PICK_EXCEPTION_ERROR.getCode(),
					WarehouseErrorCode.PICK_EXCEPTION_ERROR.getMessage());
		}
		if (flag) {
			// 调用订单系统修改订单状态(待拣货-待发货)
			try {
				UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
				updateOrderState.setRentRecordNo(q.getSourceOrderNo());
				updateOrderState.setState(BackRentState.WaitSend.getCode());
				updateOrderState.setCreateBy(1L);
				updateOrderState.setCreateMan("管理员");
				updateOrderState.setImei(q.getImieNo());
				updateOrderState.setSn(q.getSnNo());
				if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {
					ResponseResult<String> res = this.orderService.updateOrderState(updateOrderState);
					log.info("调用订单系统修改订单状态(待拣货-待发货)：{}", res);
				}
				if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {
				  org.gz.aliOrder.dto.UpdateOrderStateReq aliupdateOrderState = new org.gz.aliOrder.dto.UpdateOrderStateReq();
				  aliupdateOrderState = BeanConvertUtil.convertBean(updateOrderState,  org.gz.aliOrder.dto.UpdateOrderStateReq.class);
				  aliupdateOrderState.setSnCode(q.getSnNo());
				  log.info("调用小程序订单系统修改订单状态(待拣货-待发货)参数：{}",JsonUtils.toJsonString(aliupdateOrderState));
				  ResponseResult<String> res = this.aliOrderService.updateOrder(aliupdateOrderState);
				  log.info("调用小程序订单系统修改订单状态(待拣货-待发货)：{}", res);
				}
			} catch (Exception e) {
				log.error("调用订单系统修改订单状态(待拣货-待发货)：{}" + e.getLocalizedMessage());
			}
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	@Override
	public ResponseResult<WarehousePickingRecord> undoPick(WarehousePickingRecord q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		boolean flag = false;
		try {
			/*********************** 验证流程开始 **************************/
			// 判断订单号有无进行过拣货操作(拣货完成后，商品会进入待售仓位),若无则提示：撤销拣货失败，该订单尚未进行过拣货操作
			List<WarehouseCommodityTrack> trackList = this.queryTrackList(q.getMaterielBasicId(), WAREHOUSE_NEW,
					LOCATION_SALES, q.getSourceOrderNo());// 待售仓位
			if (CollectionUtils.isEmpty(trackList)) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_PICK_NOT_ERROR.getCode(),
						WarehouseErrorCode.UNDO_PICK_NOT_ERROR.getMessage(), null);
			}
			WarehouseCommodityTrack track = trackList.get(0);
			if (q.getOrderSource().intValue() == ORDERSOURCE_APP
					&& (!track.getSnNo().equals(q.getSnNo()) || !track.getImieNo().equals(q.getImieNo()))) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_PICK_UNKNOW_COMMODITY_ERROR.getCode(),
						WarehouseErrorCode.UNDO_PICK_UNKNOW_COMMODITY_ERROR.getMessage(), null);
			}

			// 判断商品是否存在于待售库位,若无则提示：撤销拣货失败，待售库位暂时无库存
			// 使用batchNo,snNo,imieNo检查新机库待售库位
			List<WarehouseCommodityInfo> commodityList = this.queryCommodityListByTrack(track);
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_PICK_LOCATION_ERROR.getCode(),
						WarehouseErrorCode.UNDO_PICK_LOCATION_ERROR.getMessage(), null);
			}

			/*********************** 验证流程结束 **************************/
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation revertLocation = null;
			if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {// 对于APP，应返回到冻结库位
				revertLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_RESERVE);
			}
			if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {// 对于小程序，应返回到可售库位
				revertLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_AVAILABLE);
			}
			// 库位变更：待售->冻结
			this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), revertLocation.getId());

			// 记录库存变化：待售库位-1，冻结+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_UNDO_PICK, commodity.getStorageBatchNo(), -1,
					q.getOperatorId(), q.getOperatorName());
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					revertLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo,
					AdjustReasonEnum.REASON_UNDO_PICK, commodity.getStorageBatchNo(), 1, q.getOperatorId(),
					q.getOperatorName());

			// 记录库存跟踪
			this.insertNewTrack(q.getMaterielBasicId(), q.getSourceOrderNo(), now, adjustBatchNo,
					AdjustReasonEnum.REASON_UNDO_PICK.getReason(), commodity, newWarehouse.getId(),
					revertLocation.getId());

			// 修改拣货记录
			WarehousePickingRecordQuery pickQuery = new WarehousePickingRecordQuery();
			pickQuery.setSourceOrderNo(q.getSourceOrderNo());
			pickQuery.setImieNo(q.getImieNo());
			pickQuery.setSnNo(q.getSnNo());
			pickQuery.setMaterielBasicId(q.getMaterielBasicId());
			pickQuery.setStatusFlag(BackRentState.WaitSend.getCode());
			pickQuery.setCurrPage(1);
			pickQuery.setPageSize(1);
			List<WarehousePickingRecord> recordList = this.pickMapper.queryPageList(pickQuery);
			if (CollectionUtils.isNotEmpty(recordList)) {
				WarehousePickingRecord temp = recordList.get(0);
				temp.setStatusFlag(BackRentState.WaitPick.getCode());
				temp.setOperateOn(now);
				temp.setOperatorId(q.getOperatorId());
				temp.setOperatorName(q.getOperatorName());
				this.pickMapper.undoPick(temp);
			}
			flag = true;
		} catch (Exception e) {
			log.error("撤销拣货失败：" + e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.UNDO_PICK_EXCEPTION_ERROR.getCode(),
					WarehouseErrorCode.UNDO_PICK_EXCEPTION_ERROR.getMessage());
		}
		if (flag) {
			// 调用订单系统修改订单状态(待发货-待拣货)
			try {
				UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
				updateOrderState.setRentRecordNo(q.getSourceOrderNo());
				updateOrderState.setState(BackRentState.WaitPick.getCode());
				updateOrderState.setCreateBy(q.getOperatorId());
				updateOrderState.setCreateMan(q.getOperatorName());
				if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {
					ResponseResult<String> res = this.orderService.updateOrderState(updateOrderState);
					log.info("调用订单系统修改订单状态(待发货-待拣货)：{}", res);
				}
				if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {
					org.gz.aliOrder.dto.UpdateOrderStateReq aliupdateOrderState = new org.gz.aliOrder.dto.UpdateOrderStateReq();
					aliupdateOrderState = BeanConvertUtil.convertBean(updateOrderState,  org.gz.aliOrder.dto.UpdateOrderStateReq.class);
					ResponseResult<String> res = this.aliOrderService.canclePick(aliupdateOrderState);
					log.info("调用小程序订单系统修改订单状态(待发货-待拣货)：{}", res);
				}
			} catch (Exception e) {
				log.error("调用订单系统修改订单状态(待发货-待拣货)：{}" + e.getLocalizedMessage());
			}
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	@Transactional
	public ResponseResult<WarehousePickingRecord> out(WarehousePickingRecord q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		Set<ConstraintViolation<WarehousePickingRecord>> errors = validator.validate(q, UpdateRecordGroup.class);
		if (CollectionUtils.isNotEmpty(errors)) {
			StringBuilder res = new StringBuilder();
			for (ConstraintViolation<WarehousePickingRecord> error : errors) {
				res.append(error.getMessage()).append(";");
			}
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					res.substring(0, res.length() - 1), null);
		}
		boolean flag = false;
		try {
			/*********************** 验证流程开始 **************************/
			// 判断是否已拣货
			WarehousePickingRecordQuery pickQuery = new WarehousePickingRecordQuery();
			pickQuery.setSourceOrderNo(q.getSourceOrderNo());
			pickQuery.setMaterielBasicId(q.getMaterielBasicId());
			pickQuery.setCurrPage(1);
			pickQuery.setPageSize(1);
			List<WarehousePickingRecord> pickRecordList = this.pickMapper.queryPageList(pickQuery);
			if (CollectionUtils.isEmpty(pickRecordList)) {// 无拣货记录
				return ResponseResult.build(WarehouseErrorCode.OUT_ORDER_ERROR.getCode(),
						WarehouseErrorCode.OUT_ORDER_ERROR.getMessage(), null);
			}
			WarehousePickingRecord pickRecord = pickRecordList.get(0);
			if (pickRecord.getStatusFlag().intValue() != 8) {// 不处于待发货状态
				return ResponseResult.build(WarehouseErrorCode.OUT_STATUS_ERROR.getCode(),
						WarehouseErrorCode.OUT_STATUS_ERROR.getMessage(), null);
			}
			q.setOrderSource(pickRecord.getOrderSource());
			q.setImieNo(pickRecord.getImieNo());
			q.setSnNo(pickRecord.getSnNo());
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation saleLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_SALES);// 待售仓位
			// 判断商品是否存在于在售库位,若无则提示：出货失败，待售库位暂时无库存
			// 使用batchNo,snNo,imieNo检查新机库待售库位
			WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
			commodityQuery.setCurrPage(1);
			commodityQuery.setPageSize(1);
			commodityQuery.setMaterielBasicId(pickRecord.getMaterielBasicId());
			commodityQuery.setWarehouseId(newWarehouse.getId());
			commodityQuery.setWarehouseLocationId(saleLocation.getId());
			commodityQuery.setSnNo(pickRecord.getSnNo());
			commodityQuery.setBatchNo(pickRecord.getBatchNo());
			commodityQuery.setImieNo(pickRecord.getImieNo());
			List<WarehouseCommodityInfo> commodityList = this.commodityInfoMapper.queryPageList(commodityQuery);
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.OUT_LOCATION_ERROR.getCode(),
						WarehouseErrorCode.OUT_LOCATION_ERROR.getMessage(), null);
			}
			/*********************** 验证流程结束 **************************/

			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);

			StorageLocation outLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_TRANSIT_OUT);// 出库在途仓位

			// 变更仓位：待售->出库在途
			this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), outLocation.getId());

			// 记录库存变化：待售库位-1，出库在途+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_OUT, commodity.getStorageBatchNo(), -1, q.getOperatorId(),
					q.getOperatorName());
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					outLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, AdjustReasonEnum.REASON_OUT,
					commodity.getStorageBatchNo(), 1, q.getOperatorId(), q.getOperatorName());

			// 记录库存跟踪
			this.insertNewTrack(q.getMaterielBasicId(), q.getSourceOrderNo(), now, adjustBatchNo,
					AdjustReasonEnum.REASON_OUT.getReason(), commodity, newWarehouse.getId(), outLocation.getId());

			// 更新拣货记录
			pickRecord.setStatusFlag(BackRentState.SendOut.getCode());// 修改为已发货
			pickRecord.setFillReceiptId(q.getOperatorId());
			pickRecord.setFillReceiptName(q.getOperatorName());
			pickRecord.setFillReceiptOn(now);
			// 调用顺丰下单
			MaterielBasicInfo materielBasicInfo = this.materielBasicInfoMapper
					.selectByPrimaryKey(q.getMaterielBasicId());
			q.setMaterielModelName(materielBasicInfo.getMaterielModelName());
			q.setMaterielSpecValue(materielBasicInfo.getSpecValues());
			if (!StringUtils.hasText(pickRecord.getLogisticsNo())) {
				List<String> orderData = buildLogisticsNo(q);
				log.info("orderData:{}", JsonUtils.toJsonString(orderData));
				if (CollectionUtils.isEmpty(orderData) || !StringUtils.hasText(orderData.get(0))) {
					throw new Exception("调用顺丰下单失败");
				}
				String logisticsNo = orderData.get(0);
				pickRecord.setLogisticsNo(logisticsNo);
				q.setLogisticsNo(logisticsNo);
			}
			pickRecord.setMonthlyCardNum(q.getMonthlyCardNum());
			pickRecord.setInsuredAmount(q.getInsuredAmount());
			pickRecord.setProductId(q.getProductId());
			pickRecord.setProv(q.getProv());
			pickRecord.setCity(q.getCity());
			pickRecord.setArea(q.getArea());
			pickRecord.setAddress(q.getAddress());
			pickRecord.setPhoneNum(q.getPhoneNum());
			pickRecord.setIdNo(q.getIdNo());
			pickRecord.setRealName(q.getRealName());
			log.info("出库更新拣货记录参数：{}", pickRecord.toString());
			this.pickMapper.update(pickRecord);
			flag = true;
		} catch (Exception e) {
			log.error("出货失败：" + e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.OUT_EXCEPTION_ERROR.getCode(),
					WarehouseErrorCode.OUT_EXCEPTION_ERROR.getMessage());
		}
		if (flag) {
			try {
				// 调用订单系统修改订单状态(待发货-已发货)
				RentRecordReq rentRecordReq = new RentRecordReq();
				rentRecordReq.setRentRecordNo(q.getSourceOrderNo());
				rentRecordReq.setState(BackRentState.SendOut.getCode());
				rentRecordReq.setLogisticsNo(q.getLogisticsNo());
				rentRecordReq.setCreateBy(q.getOperatorId());
				rentRecordReq.setCreateMan(q.getOperatorName());
				rentRecordReq.setBusinessNo(q.getMonthlyCardNum());
				if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {
					log.info("调用订单系统修改订单状态(待发货-已发货),参数：{}", JsonUtils.toJsonString(rentRecordReq));
					ResponseResult<String> res = this.orderService.sendOut(rentRecordReq);
					log.info("调用订单系统修改订单状态(待发货-已发货)完毕，回传结果：{}", res);
				}
				if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {
				    org.gz.aliOrder.dto.UpdateOrderStateReq updateOrderStateReq = new org.gz.aliOrder.dto.UpdateOrderStateReq();
					updateOrderStateReq.setRentRecordNo(q.getSourceOrderNo());
					updateOrderStateReq.setState(BackRentState.SendOut.getCode());
					updateOrderStateReq.setReturnLogisticsNo(q.getLogisticsNo());
					updateOrderStateReq.setCreateBy(q.getOperatorId());
					updateOrderStateReq.setCreateMan(q.getOperatorName());
					updateOrderStateReq.setBusinessNo(q.getMonthlyCardNum());
					updateOrderStateReq.setImei(q.getImieNo());
					updateOrderStateReq.setSnCode(q.getSnNo());
					log.info("调用小程序订单系统修改订单状态(待发货-已发货),参数：{}", JsonUtils.toJsonString(updateOrderStateReq));
					ResponseResult<String> res = this.aliOrderService.sendOut(updateOrderStateReq);
					log.info("调用小程序订单系统修改订单状态(待发货-已发货)完毕，回传结果：{}", res);
				}
			} catch (Exception e) {
				if (q.getOrderSource().intValue() == ORDERSOURCE_APP) {
					log.error("调用订单系统修改订单状态(待发货-已发货)发生异常：{}" + e.getLocalizedMessage());
				}
				if (q.getOrderSource().intValue() == ORDERSOURCE_Applets) {
					log.error("调用小程序订单系统修改订单状态(待发货-已发货)发生异常：{}" + e.getLocalizedMessage());
				}
			}
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	@Override
	public ResponseResult<RentingReq> renting(RentingReq q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		try {
			/*********************** 验证流程开始 **************************/
			// 判断该订单号有无进行过出库操作(出库完成后，商品会进入出库在途仓位),若无则提示：变更商品在租库位失败，该订单尚未进行过出库操作
			List<WarehouseCommodityTrack> trackList = this.queryTrackList(q.getMaterielBasicId(), WAREHOUSE_NEW,
					LOCATION_TRANSIT_OUT, q.getSourceOrderNo());// 出库在途仓位
			if (CollectionUtils.isEmpty(trackList)) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_OUT_ERROR.getCode(),
						WarehouseErrorCode.UNDO_OUT_ERROR.getMessage(), null);
			}
			WarehouseCommodityTrack track = trackList.get(0);
			// 判断所传商品是否与出货商品一致
			if (!track.getSnNo().equals(q.getSnNo()) || !track.getImieNo().equals(q.getImieNo())) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_OUT_UNKNOW_COMMODITY_ERROR.getCode(),
						WarehouseErrorCode.UNDO_OUT_UNKNOW_COMMODITY_ERROR.getMessage(), null);
			}

			// 判断商品是否存在于出库在途仓位,若无则提示：变更商品在租库位失败，该订单尚未进行过出库操作
			// 使用batchNo,snNo,imieNo检查出库在途仓位
			List<WarehouseCommodityInfo> commodityList = this.queryCommodityListByTrack(track);
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_OUT_ERROR.getCode(),
						WarehouseErrorCode.UNDO_OUT_ERROR.getMessage(), null);
			}

			/*********************** 验证流程结束 **************************/
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation rentingLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_RENTING);// 在租库位

			// 库位变更：出库在途->在租库位
			this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), rentingLocation.getId());

			// 记录库存变化：出库在途-1，在租库位+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_RENTING, commodity.getStorageBatchNo(), -1, 1L, "管理员");
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					rentingLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo,
					AdjustReasonEnum.REASON_RENTING, commodity.getStorageBatchNo(), 1, 1L, "管理员");

			// 记录库存跟踪
			this.insertNewTrack(q.getMaterielBasicId(), q.getSourceOrderNo(), now, adjustBatchNo,
					AdjustReasonEnum.REASON_RENTING.getReason(), commodity, newWarehouse.getId(),
					rentingLocation.getId());
		} catch (Exception e) {
			log.error("变更商品在租库位失败:{}", e.getLocalizedMessage());
			throw new ServiceException(1000, "变更商品在租库位失败:" + e.getLocalizedMessage());
		}
		return ResponseResult.buildSuccessResponse(q);
	}

	/**
	 * 
	 * @Description: 判读是否是租赁和以租代购产品
	 * @param productId
	 * @return boolean
	 */
	private boolean isRentProduct(Long productId) {
		ProductInfo productInfo = this.productService.getProductInfoById(productId);
		return productInfo != null && (productInfo.getProductType().intValue() == ProductTypeEnum.TYPE_LEASE.getCode()
				|| productInfo.getProductType().intValue() == ProductTypeEnum.TYPE_PURCHASING.getCode());
	}

	// 调用顺丰下单接口
	public List<String> buildLogisticsNo(WarehousePickingRecord q) {
		ApplyOrderRequest req = new ApplyOrderRequest();
		req.setAddress(q.getAddress());// 到件方详细地址
		req.setCargo("手机");// 货物名称
		req.setCity(q.getCity());// 到件方所在城市
		req.setCompany(q.getRealName());// 到件方公司名称
		req.setContact(q.getRealName());// 到件方联系人
		req.setCounty(q.getArea());// 区域
		req.setCustId(insuredAmountConfig.monthlyCardNum(q.getInsuredAmount()));//// 顺丰月结卡号
		q.setMonthlyCardNum(req.getCustId());
		req.setExpressType(new Short("2"));
		req.setIsDoCall(new Short("0"));// 不下 call
		req.setIsGenBillNo(new Short("1"));
		req.setIsGenEletricPic(new Short("1"));// 是否生成电子运单图片 1生成
		req.setMobile(q.getPhoneNum());// 到件方手机
		if(this.isRentProduct(q.getProductId())) {
			req.setNeedReturnTrackingNo(new Short("1"));
		}
		else {
			req.setNeedReturnTrackingNo(new Short("0"));
		}
		req.setOrderId(q.getSourceOrderNo());
		req.setPayArea("755G");
		req.setPayMethod(new Short("3"));
		req.setProvince(q.getProv());
		req.setRemark("需用户提供身份证复印件，随签收确认单一同返还");
		req.setTel(q.getPhoneNum());// 到件方联系电话
		req.setCargo(q.getMaterielModelName() + "" + q.getMaterielSpecValue());
		log.info("调用顺丰下单接口参数：{}", JsonUtils.toJsonString(req));
		ResultCode rc = thirdPartService.order2(req);
		log.info("调用顺丰下单接口结果：{}", JsonUtils.toJsonString(rc));
		if (rc != null && rc.getCode() == 0) {
			return (List<String>) rc.getData();
		}
		return null;
	}

	@Override
	public ResponseResult<WarehousePickResult> pickQuery(WarehousePickQuery q) {
		WarehousePickingRecordQuery pickQuery = new WarehousePickingRecordQuery();
		pickQuery.setSourceOrderNo(q.getSourceOrderNo());
		pickQuery.setCurrPage(1);
		pickQuery.setPageSize(1);
		List<WarehousePickingRecord> list = this.pickMapper.queryPageList(pickQuery);
		WarehousePickResult result = new WarehousePickResult();
		result.setSourceOrderNo(q.getSourceOrderNo());
		boolean flag = false;
		if (CollectionUtils.isNotEmpty(list)) {
			WarehousePickingRecord record = list.get(0);
			result.setStatusFlag(record.getStatusFlag());
			result.setStatusFlag_s(record.getStatusFlag_s());
			flag = true;
		}
		return flag ? ResponseResult.buildSuccessResponse(result) : ResponseResult.build(1000, "该订单尚未进行过拣货", result);
	}

	/**
	 * 确认收货: 库位变化：出库在途-1，在租+1
	 */
	@Override
	public ResponseResult<ConfirmSignVO> confirmSign(ConfirmSignVO q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		try {
			/*********************** 验证流程开始 **************************/
			Long materielBasicId = q.getMaterielBasicId();
			String warehouseCode = WAREHOUSE_NEW;// 新机库
			String locationCode = LOCATION_TRANSIT_OUT;// 出库在途仓位编码
			String sourceOrderNo = q.getSourceOrderNo();// 源订单号
			// 判断订单号有没有进行过出库操作,若无则提示：确认收货失败，该订单尚未进行过出库操作
			List<WarehouseCommodityTrack> trackList = this.queryTrackList(materielBasicId, warehouseCode, locationCode,
					sourceOrderNo);
			if (CollectionUtils.isEmpty(trackList)) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), "确认收货失败，该订单尚未进行过出库操作",
						null);
			}
			WarehouseCommodityTrack track = trackList.get(0);
			if (!track.getSnNo().equals(q.getSnNo()) || !track.getImieNo().equals(q.getImieNo())) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(),
						"确认收货失败，出货SN号，IMIE号与传入参数不一致", null);
			}
			// 判断商品是否存在于出库在途库位,若无则提示：确认收货失败，该订单尚未进行过出库操作
			// 使用batchNo,snNo,imieNo检查新机库出库在途库位
			List<WarehouseCommodityInfo> commodityList = this.queryCommodityListByTrack(track);
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), "确认收货失败，该订单尚未进行过出库操作",
						null);
			}

			/*********************** 验证流程结束 **************************/
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);// 在售仓库商品
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation rentingLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_RENTING);// 在租仓位
			// 变更仓位:出库在途->在租
			this.updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), rentingLocation.getId());
			// 记录库存变化：出库在途-1，在租+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_RECV, commodity.getStorageBatchNo(), -1, 1L, "管理员");

			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					rentingLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, AdjustReasonEnum.REASON_RECV,
					commodity.getStorageBatchNo(), 1, 1L, "管理员");

			// 记录库存跟踪
			this.insertNewTrack(materielBasicId, sourceOrderNo, now, adjustBatchNo,
					AdjustReasonEnum.REASON_RECV.getReason(), commodity, newWarehouse.getId(), rentingLocation.getId());

		} catch (Exception e) {
			log.error("确认收货失败：" + e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), "确认收货异常");
		}
		return ResponseResult.buildSuccessResponse(q);
	}

	/**
	 * 提前买断或正常买断
	 */
	@Override
	public ResponseResult<BuyEndVO> buyEnd(BuyEndVO q) {
		if (q == null) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		try {
			/*********************** 验证流程开始 **************************/
			Long materielBasicId = q.getMaterielBasicId();
			String warehouseCode = WAREHOUSE_NEW;// 新机库
			String locationCode = LOCATION_RENTING;// 在租仓位编码
			if (!isRentProduct(q.getProductId())) {
				locationCode = "TRANSIT_OUT";// 售卖时，前置仓位应该是出库在途
			}
			String sourceOrderNo = q.getSourceOrderNo();// 源订单号

			// 判断订单号有没有进行过出库操作,若无则提示：买断失败，该订单尚未进行过确认收货操作
			List<WarehouseCommodityTrack> trackList = this.queryTrackList(materielBasicId, warehouseCode, locationCode,
					sourceOrderNo);
			if (CollectionUtils.isEmpty(trackList)) {
				return ResponseResult.build(WarehouseErrorCode.EX_BUY_END_ERROR.getCode(), "买断失败，该订单前置操作状态错误", null);
			}
			WarehouseCommodityTrack track = trackList.get(0);
			if (!track.getSnNo().equals(q.getSnNo()) || !track.getImieNo().equals(q.getImieNo())) {
				return ResponseResult.build(WarehouseErrorCode.EX_BUY_END_ERROR.getCode(), "买断失败，确认收货SN号，IMIE号与传入参数不一致",
						null);
			}
			// 判断商品是否存在于出库在途库位,若无则提示：确认收货失败，该订单尚未进行过出库操作
			// 使用batchNo,snNo,imieNo检查新机库出库在途库位
			List<WarehouseCommodityInfo> commodityList = this.queryCommodityListByTrack(track);
			if (CollectionUtils.isEmpty(commodityList)) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(),
						"确认收货失败，该订单尚未进行过出库或售卖操作", null);
			}

			/*********************** 验证流程结束 **************************/
			Date now = new Date();
			String adjustBatchNo = UUIDUtils.genDateUUID("IVA");// 调整批次号
			WarehouseCommodityInfo commodity = commodityList.get(0);// 在售仓库商品
			WarehouseInfo newWarehouse = this.warehouseInfoMapper.queryWarehouseInfoByCode(WAREHOUSE_NEW);// 新机库
			StorageLocation buyEndLocation = this.locationMapper.queryLocation(newWarehouse.getId(), LOCATION_BUY_END);// 买断仓位

			// 变更库位：在租->买断
			updateWarehouseLocation(now, commodity.getId(), newWarehouse.getId(), buyEndLocation.getId());

			// 记录库存变化：在租-1，买断+1
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(),
					commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE,
					adjustBatchNo, AdjustReasonEnum.REASON_BUYEND, commodity.getStorageBatchNo(), -1, 1L, "管理员");
			this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), newWarehouse.getId(),
					buyEndLocation.getId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo,
					AdjustReasonEnum.REASON_BUYEND, commodity.getStorageBatchNo(), 1, 1L, "管理员");

			// 记录库存跟踪
			insertNewTrack(materielBasicId, sourceOrderNo, now, adjustBatchNo,
					AdjustReasonEnum.REASON_BUYEND.getReason(), commodity, newWarehouse.getId(),
					buyEndLocation.getId());

		} catch (Exception e) {
			log.error("买断失败：" + e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), "买断失败异常");
		}
		return ResponseResult.buildSuccessResponse(q);
	}

	/**
	 * 
	 * @Description: 插入库存跟踪
	 * @param materielBasicId
	 * @param sourceOrderNo
	 * @param now
	 * @param adjustBatchNo
	 * @param adjustReason
	 * @param commodity
	 * @param newWarehouseId
	 * @param newLocationId
	 * @return int
	 */
	private int insertNewTrack(Long materielBasicId, String sourceOrderNo, Date now, String adjustBatchNo,
			String adjustReason, WarehouseCommodityInfo commodity, Integer newWarehouseId, Integer newLocationId) {
		WarehouseCommodityTrack newTrack = new WarehouseCommodityTrack();
		newTrack.setAdjustBatchNo(adjustBatchNo);
		newTrack.setAdjustType(2);
		newTrack.setAdjustReason(adjustReason);
		newTrack.setBatchNo(commodity.getBatchNo());
		newTrack.setImieNo(commodity.getImieNo());
		newTrack.setMaterielBasicId(materielBasicId);
		newTrack.setOperateOn(now);
		newTrack.setOperatorId(1L);
		newTrack.setOperatorName("管理员");
		newTrack.setSnNo(commodity.getSnNo());
		newTrack.setSourceOrderNo(sourceOrderNo);
		newTrack.setStorageBatchNo(commodity.getStorageBatchNo());
		newTrack.setWarehouseId(newWarehouseId);
		newTrack.setWarehouseLocationId(newLocationId);
		return this.trackMapper.insert(newTrack);
	}

	/**
	 * 
	 * @Description: 插入库存变化记录
	 * @param materielBasicId
	 * @param sourceOrderNo
	 * @param now
	 * @param adjustBatchNo
	 * @param adjustReason
	 * @param storageBatchNo
	 * @param newWarehouseId
	 * @param newLocationId
	 * @return int
	 */
	@SuppressWarnings("unused")
	private int insertNewChangeRecord(Long materielBasicId, String sourceOrderNo, Date now, String adjustBatchNo,
			String adjustReason, String storageBatchNo, Integer newWarehouseId, Integer newLocationId) {
		WarehouseChangeRecord newRecord = new WarehouseChangeRecord();
		newRecord.setAdjustBatchNo(adjustBatchNo);
		newRecord.setAdjustReason(adjustReason);
		newRecord.setAdjustType(2);// 销售调整类型
		newRecord.setChangeQuantity(1);
		newRecord.setOperatorId(1L);
		newRecord.setOperatorName("管理员");
		newRecord.setOperateOn(now);
		newRecord.setMaterielBasicId(materielBasicId);
		newRecord.setSourceOrderNo(sourceOrderNo);
		newRecord.setStorageBatchNo(storageBatchNo);// 入库批次号保持不变
		newRecord.setWarehouseId(newWarehouseId);
		newRecord.setWarehouseLocationId(newLocationId);
		return this.changeRecordMapper.insert(newRecord);// 新插记录
	}

	/**
	 * @Description: 递减库位数量
	 * @param sourceOrderNo
	 *            源订单号
	 * @param now
	 *            更新时间
	 * @param adjustBatchNo
	 *            调整批次号
	 * @param commodity
	 *            源商品
	 * @return int
	 */
	@SuppressWarnings("unused")
	private int decreaseLocationQuantity(String sourceOrderNo, Date now, String adjustBatchNo, String adjustReason,
			WarehouseCommodityInfo commodity) {
		WarehouseChangeRecord updateRecord = new WarehouseChangeRecord();
		updateRecord.setWarehouseId(commodity.getWarehouseId());
		updateRecord.setWarehouseLocationId(commodity.getWarehouseLocationId());// 在租库位
		updateRecord.setMaterielBasicId(commodity.getMaterielBasicId());
		updateRecord.setAdjustBatchNo(adjustBatchNo);
		updateRecord.setAdjustReason(adjustReason);
		updateRecord.setAdjustType(2);// 销售调整类型
		updateRecord.setSourceOrderNo(sourceOrderNo);// 记录销售单号
		updateRecord.setChangeQuantity(1);
		updateRecord.setOperateOn(now);
		return this.changeRecordMapper.decreaseWarehouseLocationQuantity(updateRecord);// 递减
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
	 * 
	 * @Description: 根据订单号，物料ID，仓库编码，库位编码查询物料跟踪列表
	 * @param materielBasicId
	 *            物料ID
	 * @param warehouseCode
	 *            仓库编码
	 * @param locationCode
	 *            库位编码
	 * @param sourceOrderNo
	 *            源订单号
	 * @return List<WarehouseCommodityTrack>
	 */
	private List<WarehouseCommodityTrack> queryTrackList(Long materielBasicId, String warehouseCode,
			String locationCode, String sourceOrderNo) {
		WarehouseCommodityTrackQuery trackQuery = new WarehouseCommodityTrackQuery();
		trackQuery.setMaterielBasicId(materielBasicId);
		trackQuery.setWarehouseCode(warehouseCode);
		trackQuery.setLocationCode(locationCode);
		trackQuery.setSourceOrderNo(sourceOrderNo);
		trackQuery.setCurrPage(1);
		trackQuery.setPageSize(1);
		return trackMapper.queryCommodityTrackPageList(trackQuery);
	}

	/**
	 * 
	 * @Description: 根据物料跟踪查询商品列表
	 * @param track
	 * @return List<WarehouseCommodityInfo>
	 */
	private List<WarehouseCommodityInfo> queryCommodityListByTrack(WarehouseCommodityTrack track) {
		WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
		commodityQuery.setCurrPage(1);
		commodityQuery.setPageSize(1);
		commodityQuery.setMaterielBasicId(track.getMaterielBasicId());
		commodityQuery.setWarehouseId(track.getWarehouseId());
		commodityQuery.setWarehouseLocationId(track.getWarehouseLocationId());
		commodityQuery.setSnNo(track.getSnNo());
		commodityQuery.setBatchNo(track.getBatchNo());
		commodityQuery.setImieNo(track.getImieNo());
		return this.commodityInfoMapper.queryPageList(commodityQuery);
	}

	@Override
	public ResponseResult<ResultPager<WarehousePickingRecord>> queryByPage(WarehousePickingRecordQuery q) {
		int totalNum = this.pickMapper.queryPageCount(q);
		List<WarehousePickingRecord> list = new ArrayList<WarehousePickingRecord>(0);
		if (totalNum > 0) {
			list = this.pickMapper.queryPageList(q);
		}
		ResultPager<WarehousePickingRecord> data = new ResultPager<WarehousePickingRecord>(totalNum, q.getCurrPage(),
				q.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<UndoPickReq> undoPick(UndoPickReq q) {
		log.info("订单系统撤销拣货参数：{}",JsonUtils.toJsonString(q));
		WarehousePickingRecordQuery pickQuery = new WarehousePickingRecordQuery();
		pickQuery.setSourceOrderNo(q.getSourceOrderNo());
		pickQuery.setProductId(q.getProductId());
		pickQuery.setOrderSource(q.getOrderSource());
		pickQuery.setMaterielBasicId(q.getMaterielBasicId());
		pickQuery.setCurrPage(1);
		pickQuery.setPageSize(1);
		List<WarehousePickingRecord> list = this.pickMapper.queryPageList(pickQuery);
		if (CollectionUtils.isNotEmpty(list)) {
			try {
				ResponseResult<WarehousePickingRecord> resp = this.undoPick(list.get(0));
				if (resp.isSuccess()) {
					return ResponseResult.buildSuccessResponse(q);
				} else {
					return ResponseResult.build(resp.getErrCode(), resp.getErrMsg(), q);
				}
			} catch (ServiceException e) {
				return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), q);
			} catch (Exception e) {
				return ResponseResult.build(1000, "撤销拣货异常", q);
			}
		}
		return ResponseResult.build(1000, "撤销拣货参数异常", q);
	}
}
