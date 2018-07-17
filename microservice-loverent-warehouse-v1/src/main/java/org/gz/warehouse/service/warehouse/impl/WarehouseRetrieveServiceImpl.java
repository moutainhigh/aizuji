/**
 * 
 */
package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.constants.ProductTypeEnum;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.WarehouseAggregationVO;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfo;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityShortVO;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrack;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseLocationMaterielDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielAggregationWrapper;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseRetrieveQuery;
import org.gz.warehouse.mapper.warehouse.StorageLocationMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityTrackMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseRetrieveMapper;
import org.gz.warehouse.service.product.ProductService;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.gz.warehouse.service.warehouse.WarehouseRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月22日 上午11:13:38
 */
@Service
@Slf4j
public class WarehouseRetrieveServiceImpl implements WarehouseRetrieveService {

	@Autowired
	private WarehouseRetrieveMapper retrieveMapper;
	
	@Autowired
	private WarehouseInfoMapper warehouseInfoMapper;
	
	@Autowired
	private StorageLocationMapper locationMapper;
	
	@Autowired
	private WarehouseCommodityInfoMapper commodityInfoMapper;
	
	@Autowired
	private WarehouseCommodityTrackMapper trackMapper;
	
	@Autowired
	private WarehouseChangeRecordService recordService;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public ResponseResult<ResultPager<WarehouseAggregationVO>> queryAggregationPage(WarehouseRetrieveQuery q) {
		int totalNum = this.retrieveMapper.queryAggregationPageCount(q);
		List<WarehouseAggregationVO> list =new ArrayList<WarehouseAggregationVO>();
		if(totalNum>0) {
			list = this.retrieveMapper.queryAggregationPageList(q);
		}
		return ResponseResult.buildSuccessResponse(new ResultPager<WarehouseAggregationVO>(totalNum,q.getCurrPage(),q.getPageSize(),list));
	}

	@Override
	public ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>> queryMaterielAggregationPage(WarehouseRetrieveQuery q) {
		int totalNum = 0;
		List<WarehouseMaterielAggregationWrapper> warehouseMaterielList =  this.retrieveMapper.queryWarehouseMaterielList(q);//按物料分类，查询物料总数
		if(CollectionUtils.isNotEmpty(warehouseMaterielList)) {
			totalNum = warehouseMaterielList.size();//分页总数
			for(WarehouseMaterielAggregationWrapper w:warehouseMaterielList) {
				this.aggregationByPerMaterie(w);//聚合每种物料
			}
		}
		return ResponseResult.buildSuccessResponse(new ResultPager<WarehouseMaterielAggregationWrapper>(totalNum,q.getCurrPage(),q.getPageSize(),warehouseMaterielList));
	}

	/** 
	* @Description: 按物料聚合
	* @param @param w
	* @return void
	* @throws 
	*/
	private void aggregationByPerMaterie(WarehouseMaterielAggregationWrapper w) {
		Integer materielBasicId = w.getMaterielBasicId();//物料ID
		w.setWarehouseDetailList(aggregationByPerWarehouse(materielBasicId));
	}

	/** 
	* @Description: 按仓库聚合
	* @param  materielBasicId
	* @return List<WarehouseMaterielDetailVO>
	*/
	private List<WarehouseMaterielDetailVO> aggregationByPerWarehouse(Integer materielBasicId) {
		//查询物料存在的仓库列表，此处可使用一条SQL通过组装数据来完成.为了流程清晰，拆分成了两条SQL.
		List<WarehouseMaterielDetailVO> warehouseMaterielDetailList = this.retrieveMapper.queryWarehouseMaterielAggregationList(materielBasicId);
		if(CollectionUtils.isNotEmpty(warehouseMaterielDetailList)) {
			for(WarehouseMaterielDetailVO v:warehouseMaterielDetailList) {
				v.setLocationDetailList(this.aggregationByPerLocation(materielBasicId,v.getWarehouseId()));//按仓位聚合
			}
		}
		return warehouseMaterielDetailList;
	}

	/** 
	* @Description: 按仓位聚合
	* @param materielBasicId
	* @param warehouseId
	* @return List<WarehouseLocationMaterielDetailVO>
	*/
	private List<WarehouseLocationMaterielDetailVO> aggregationByPerLocation(Integer materielBasicId,Integer warehouseId) {
		List<WarehouseLocationMaterielDetailVO> list =  this.retrieveMapper.queryLocationMaterielAggregationList(materielBasicId,warehouseId);
		//补其它仓位商品数量为0的数据-开始
		if(CollectionUtils.isNotEmpty(list)) {
			List<Integer> warehouseLocationIds = Lists.transform(list, new Function<WarehouseLocationMaterielDetailVO,Integer>() {
				@Override
				public Integer apply(WarehouseLocationMaterielDetailVO input) {
					return input.getWarehouseLocationId();
				}});
			//查询该仓库下商品数量为0的其它仓位
			List<WarehouseLocationMaterielDetailVO> noDataList = this.retrieveMapper.queryExcludeLocationMaterielAggregationList(warehouseId, warehouseLocationIds);
			//组装数据
			list.addAll(noDataList);
			//重排数据,默认按warehouseLocationId升序排序
			Ordering<WarehouseLocationMaterielDetailVO> ordering = Ordering.from(new Comparator<WarehouseLocationMaterielDetailVO>() {
				@Override
				public int compare(WarehouseLocationMaterielDetailVO o1, WarehouseLocationMaterielDetailVO o2) {
					return Ints.compare(o1.getWarehouseLocationId().intValue(), o2.getWarehouseLocationId().intValue());
				}});
			Collections.sort(list, ordering);
		}
		//补其它仓位商品数量为0的数据-结束
		return list;
	}


	@Override
	public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(WarehouseMaterielCountQuery q) {
		if(q.getWarehouseId()==null&&!StringUtils.hasText(q.getWarehouseCode())) {
			q.setWarehouseCode("S011");
		}
		if(q.getWarehouseLocationId()==null&&!StringUtils.hasText(q.getLocationCode())) {
			q.setLocationCode("AVAILABLE");
		}
		WarehouseMaterielCount count =  this.retrieveMapper.queryWarehouseMaterielCount(q);
		return ResponseResult.buildSuccessResponse(count);
	}

	/**
	 * 查询新机库可售仓位，随机取一件商品，再将商品从新机可售库位->新机冻结库位，同时可售库位-，冻结+，记录库存变化与库存跟踪
	 */
	@Transactional
	@Override
	public ResponseResult<SigningResult> signing(SigningQuery q) {
		//冥等操作
		try {
			//检查库存商品跟踪最新一条记录是否有单号新仓库冻结仓位操作记录
			List<WarehouseCommodityTrack> trackList = this.getFreeTrackList(q.getMaterielBasicId(),q.getSourceOrderNo());
			WarehouseInfo warehouseInfo = this.warehouseInfoMapper.queryWarehouseInfoByCode("S011");//新机库
			StorageLocation location = this.locationMapper.queryLocation(warehouseInfo.getId(),"RESERVE");//冻结仓位
			if(CollectionUtils.isEmpty(trackList)) {//若为空，则说明未执行过冻结操作
				return singNewCommodity(q, warehouseInfo, location);
			}else {
				//使用batchNo,snNo,imieNo检查新机库冻结库位，检查是否存在记录，若存在则直接返回
				WarehouseCommodityTrack track = trackList.get(0);
				WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
				commodityQuery.setCurrPage(1);
				commodityQuery.setPageSize(1);
				commodityQuery.setMaterielBasicId(track.getMaterielBasicId());
				commodityQuery.setWarehouseId(track.getWarehouseId());
				commodityQuery.setWarehouseLocationId(track.getWarehouseLocationId());
				commodityQuery.setSnNo(track.getSnNo());
				commodityQuery.setBatchNo(track.getBatchNo());
				commodityQuery.setImieNo(track.getImieNo());
				List<WarehouseCommodityInfo> commodityList = this.commodityInfoMapper.queryPageList(commodityQuery);
				if(CollectionUtils.isNotEmpty(commodityList)) {
					WarehouseCommodityInfo commodity = commodityList.get(0);
					SigningResult result = new SigningResult();
					result.setBatchNo(commodity.getBatchNo());
					result.setImieNo(commodity.getImieNo());
					result.setMaterielBasicId(q.getMaterielBasicId());
					result.setSnNo(commodity.getSnNo());
					result.setSourceOrderNo(q.getSourceOrderNo());
					return ResponseResult.buildSuccessResponse(result);//返回成功
				}else {
					return singNewCommodity(q, warehouseInfo, location);
				}
			}
		}catch(Exception e) {
			log.error("签约失败："+e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage()+"：异常终止");
		}
	}

	/** 
	* @Description: 
	* @param q
	* @param warehouseInfo
	* @param location
	* @return ResponseResult<SigningResult>
	*/
	private ResponseResult<SigningResult> singNewCommodity(SigningQuery q, WarehouseInfo warehouseInfo,StorageLocation location) {
		//查询新机可售库位是否存在可用商品
		WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();
		commodityQuery.setMaterielBasicId(q.getMaterielBasicId());
		commodityQuery.setWarehouseCode("S011");//新机库
		commodityQuery.setLocationCode("AVAILABLE");//可售仓位
		commodityQuery.setSnNo(q.getSnNo());
		commodityQuery.setImieNo(q.getImieNo());
		commodityQuery.setCurrPage(1);
		commodityQuery.setPageSize(1);
		List<WarehouseCommodityShortVO> commodityList = this.retrieveMapper.queryWarehouseCommodityPageList(commodityQuery);
		if(CollectionUtils.isEmpty(commodityList)) {
			return ResponseResult.build(WarehouseErrorCode.EX_NO_COMMODITY_ERROR.getCode(), WarehouseErrorCode.EX_NO_COMMODITY_ERROR.getMessage(), null);
		}
		Date now = new Date();
		String batchNo = UUIDUtils.genDateUUID(null);
		String adjustBatchNo = "IVA"+batchNo;
		
		//直接变更库位
		WarehouseCommodityShortVO commodity = commodityList.get(0);
		
		if(warehouseInfo==null||location==null) {
			return ResponseResult.build(WarehouseErrorCode.EX_NO_WAREHOUSELOCATION_ERROR.getCode(), WarehouseErrorCode.EX_NO_WAREHOUSELOCATION_ERROR.getMessage(), null);
		}
		
		ProductInfo productInfo = this.productService.getProductInfoById(q.getProductId());
		AdjustReasonEnum reason = AdjustReasonEnum.REASON_SIGNING;
		if(productInfo!=null&&AssertUtils.isPositiveNumber4Int(productInfo.getProductType())&&productInfo.getProductType().intValue()==ProductTypeEnum.TYPE_SALE.getCode()) {
			reason=AdjustReasonEnum.REASON_SALE;
		}
		WarehouseCommodityInfo updateCommodity = new WarehouseCommodityInfo();
		updateCommodity.setId(commodity.getId());
		updateCommodity.setWarehouseId(warehouseInfo.getId());
		updateCommodity.setWarehouseLocationId(location.getId());
		updateCommodity.setOperateOn(now);
		this.commodityInfoMapper.updateWarehouseLocation(updateCommodity);
		
		//记录库存变化,新机库可售仓位-，新机库冻结库位+
		this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, reason, commodity.getStorageBatchNo(), -1, 1L,"管理员");
		this.recordService.insertChangeRecord(q.getMaterielBasicId(),q.getSourceOrderNo(), warehouseInfo.getId(), location.getId(), now, AdjustTypeEnum.TYPE_SALE,adjustBatchNo,reason, commodity.getStorageBatchNo(),1,1L,"管理员");

		//记录库存跟踪
		WarehouseCommodityTrack track = new WarehouseCommodityTrack();
		track.setAdjustBatchNo(adjustBatchNo);
		track.setAdjustType(AdjustTypeEnum.TYPE_SALE.getCode());
		track.setAdjustReason(reason.getReason());
		track.setBatchNo(commodity.getBatchNo());
		track.setImieNo(commodity.getImieNo());
		track.setMaterielBasicId(q.getMaterielBasicId());
		track.setOperateOn(now);
		track.setOperatorId(1L);
		track.setOperatorName("管理员");
		track.setSnNo(commodity.getSnNo());
		track.setSourceOrderNo(q.getSourceOrderNo());
		track.setStorageBatchNo(commodity.getStorageBatchNo());
		track.setWarehouseId(warehouseInfo.getId());
		track.setWarehouseLocationId(location.getId());
		this.trackMapper.insert(track);
		SigningResult result = new SigningResult();
		result.setBatchNo(commodity.getBatchNo());
		result.setImieNo(commodity.getImieNo());
		result.setMaterielBasicId(q.getMaterielBasicId());
		result.setSnNo(commodity.getSnNo());
		result.setSourceOrderNo(q.getSourceOrderNo());
		return ResponseResult.buildSuccessResponse(result);//返回成功
	}


	/** 
	* @Description: 根据物料ID,源单号查询商品跟踪记录
	* @param materielBasicId：物料ID
	* @param sourceOrderNo:源单号
	* @return List<WarehouseCommodityTrack>
	*/
	private List<WarehouseCommodityTrack> getFreeTrackList(Long materielBasicId,String sourceOrderNo) {
		WarehouseCommodityTrackQuery trackQuery = new WarehouseCommodityTrackQuery();
		trackQuery.setMaterielBasicId(materielBasicId);
		trackQuery.setWarehouseCode("S011");//新机库
		trackQuery.setLocationCode("RESERVE");//冻结仓位
		trackQuery.setSourceOrderNo(sourceOrderNo);//源单号
		trackQuery.setCurrPage(1);
		trackQuery.setPageSize(1);
		return this.trackMapper.queryCommodityTrackPageList(trackQuery);
	}

	@Override
	public ResponseResult<SigningResult> signFailed(SigningQuery q) {
		//冥等操作
		try {
			List<WarehouseCommodityTrack> trackList = getFreeTrackList(q.getMaterielBasicId(),q.getSourceOrderNo());
			if(CollectionUtils.isEmpty(trackList)) {//如果为空，则说明未走过确认签约流程
				return ResponseResult.build(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage()+"：该订单状态错误", null);
			}
			WarehouseCommodityTrack track = trackList.get(0);
			if(!q.getImieNo().equals(track.getImieNo())||!q.getSnNo().equals(track.getSnNo())) {
				return ResponseResult.build(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), "IMIE,SN号与签约时不一致", null);
			}
			WarehouseCommodityInfoQuery commodityQuery = new WarehouseCommodityInfoQuery();//查询冻结库位
			commodityQuery.setCurrPage(1);
			commodityQuery.setPageSize(1);
			commodityQuery.setMaterielBasicId(track.getMaterielBasicId());
			commodityQuery.setWarehouseId(track.getWarehouseId());
			commodityQuery.setWarehouseLocationId(track.getWarehouseLocationId());
			commodityQuery.setSnNo(track.getSnNo());
			commodityQuery.setBatchNo(track.getBatchNo());
			commodityQuery.setImieNo(track.getImieNo());
			List<WarehouseCommodityInfo> commodityList = this.commodityInfoMapper.queryPageList(commodityQuery);//查询冻结库位是否存在商品
			if(CollectionUtils.isNotEmpty(commodityList)) {//若存在，则可售+1，冻结-1，
				WarehouseInfo warehouseInfo = this.warehouseInfoMapper.queryWarehouseInfoByCode("S011");//新机库
				StorageLocation location = this.locationMapper.queryLocation(warehouseInfo.getId(),"AVAILABLE");//可售仓位
				
				Date now = new Date();
				String batchNo = UUIDUtils.genDateUUID(null);
				String adjustBatchNo = "IVA"+batchNo;
				WarehouseCommodityInfo commodity=commodityList.get(0);
				
				//直接变更库位
				WarehouseCommodityInfo updateCommodity = new WarehouseCommodityInfo();
				updateCommodity.setId(commodity.getId());
				updateCommodity.setWarehouseId(warehouseInfo.getId());//新机库
				updateCommodity.setWarehouseLocationId(location.getId());//可售仓位
				updateCommodity.setOperateOn(now);
				this.commodityInfoMapper.updateWarehouseLocation(updateCommodity);
				
				//记录库存变化,新机库可售仓位+，新机库冻结库位-
				//新机库冻结库位-
				this.recordService.insertChangeRecord(q.getMaterielBasicId(), q.getSourceOrderNo(), commodity.getWarehouseId(), commodity.getWarehouseLocationId(), now, AdjustTypeEnum.TYPE_SALE, adjustBatchNo, AdjustReasonEnum.REASON_CANCEL_ORDER, commodity.getStorageBatchNo(), -1, 1L,"管理员");
				//新机库可售仓位+
				this.recordService.insertChangeRecord(q.getMaterielBasicId(),q.getSourceOrderNo(), warehouseInfo.getId(), location.getId(), now, AdjustTypeEnum.TYPE_SALE,adjustBatchNo,AdjustReasonEnum.REASON_CANCEL_ORDER, commodity.getStorageBatchNo(),1,1L,"管理员");
			
				//记录库存跟踪
				WarehouseCommodityTrack newTrack = new WarehouseCommodityTrack();
				newTrack.setAdjustBatchNo(adjustBatchNo);
				newTrack.setAdjustType(2);
				newTrack.setAdjustReason(AdjustReasonEnum.REASON_CANCEL_ORDER.getReason());
				newTrack.setBatchNo(commodity.getBatchNo());
				newTrack.setImieNo(commodity.getImieNo());
				newTrack.setMaterielBasicId(q.getMaterielBasicId());
				newTrack.setOperateOn(now);
				newTrack.setOperatorId(1L);
				newTrack.setOperatorName("管理员");
				newTrack.setSnNo(commodity.getSnNo());
				newTrack.setSourceOrderNo(q.getSourceOrderNo());
				newTrack.setStorageBatchNo(commodity.getStorageBatchNo());
				newTrack.setWarehouseId(warehouseInfo.getId());
				newTrack.setWarehouseLocationId(location.getId());
				this.trackMapper.insert(newTrack);
				
				//封装结果
				SigningResult result = new SigningResult();
				result.setBatchNo(commodity.getBatchNo());
				result.setImieNo(commodity.getImieNo());
				result.setMaterielBasicId(q.getMaterielBasicId());
				result.setSnNo(commodity.getSnNo());
				result.setSourceOrderNo(q.getSourceOrderNo());
				return ResponseResult.buildSuccessResponse(result);//返回成功
			}else {//不存在，则说明订单状态错误
				return ResponseResult.build(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage()+"：该订单状态错误", null);
			}
		} catch (Exception e) {
			log.error("签约失败调用失败："+e.getLocalizedMessage());
			throw new ServiceException(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage()+"：异常终止");
		}
	}
}
