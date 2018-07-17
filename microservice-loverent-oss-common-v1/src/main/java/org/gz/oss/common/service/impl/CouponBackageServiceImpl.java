package org.gz.oss.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.dao.CouponBackageDao;
import org.gz.oss.common.dao.CouponDao;
import org.gz.oss.common.dao.CouponTypeDao;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponBackage;
import org.gz.oss.common.entity.CouponBackageQuery;
import org.gz.oss.common.entity.CouponType;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.CouponBackageService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优惠券礼包业务逻辑实现
 *
 */
@Service
public class CouponBackageServiceImpl implements CouponBackageService {

	@Autowired
	private CouponBackageDao couponBackageDao;
	
	@Autowired
	private CouponDao couponDao;
	
	@Autowired
	private CouponTypeDao couponTypeDao;
	
	@Resource
    private IProductService productService;
	
	@Resource
    private IMaterielService materielService;
	
	@Override
	public ResponseResult<ResultPager<CouponBackage>> getCouponBackageList(CouponBackageQuery c) {
		int totalNum = couponBackageDao.queryPageCount(c);
		List<CouponBackage> list = new ArrayList<CouponBackage>();
		if(totalNum>0) {
		    list = couponBackageDao.queryPageList(c);
		    for(int i=0;i<list.size();i++){
		    	CouponBackage couponBackage = list.get(i);
		    	Long backageId = couponBackage.getId();
		    	List<Coupon> couponList = couponDao.getCouponList(backageId);
		    	couponBackage.setCouponList(couponList);
		    }
		}
		ResultPager<CouponBackage> data = new ResultPager<CouponBackage>(totalNum, c.getCurrPage(), c.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<?> addCouponBackage(CouponBackage c) {
		try {
        	c.setUpdateNo(new Date());
        	c.setCreateNo(new Date());
			if (couponBackageDao.insertSelective(c) > 0) { 
				if(c.getCouponList().size()>0){
					List<Coupon> couponList = c.getCouponList();
					for(int i=0;i<couponList.size();i++){
						Coupon coupon = couponList.get(i);
						coupon.setBackageId(c.getId());
						coupon.setIsBackage((byte)1);
						coupon.setQualifications(c.getQualifications());
						coupon.setValidityStartTime(c.getValidityStartTime());
						coupon.setValidityEndTime(c.getValidityEndTime());
						coupon.setCreateNo(new Date());
						coupon.setUpdateNo(new Date());
						couponDao.insertSelective(coupon);
						int totalNum = coupon.getCouponTypeList().size();
						if(totalNum > 0 ){
							for(int j=0;j<totalNum;j++){
								CouponType ct = coupon.getCouponTypeList().get(j);
								ct.setCouponId(coupon.getId());
								ct.setCreateNo(new Date());
								couponTypeDao.insertSelective(ct);
							}
						}
					}
				}
				return ResponseResult.buildSuccessResponse(c);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
        return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
	}

	@Override
	public ResponseResult<?> updateCouponBackage(CouponBackage c) {
		try {
        	c.setUpdateNo(new Date());
        	Long backageId = c.getId();
			if (backageId != null) {
				if (this.couponBackageDao.updateByPrimaryKeySelective(c) > 0) {
					if(c.getCouponList().size()>0){
						List<Coupon> couponList = c.getCouponList();
						for(int i=0;i<couponList.size();i++){
							Coupon coupon = couponList.get(i);
							coupon.setBackageId(backageId);
							coupon.setIsBackage((byte)1);
							coupon.setQualifications(c.getQualifications());
							coupon.setValidityStartTime(c.getValidityStartTime());
							coupon.setValidityEndTime(c.getValidityEndTime());
							coupon.setCreateNo(new Date());
							coupon.setUpdateNo(new Date());
							couponDao.insertSelective(coupon);
							int totalNum = coupon.getCouponTypeList().size();
							if(totalNum > 0 ){
								for(int j=0;j<totalNum;j++){
									CouponType ct = coupon.getCouponTypeList().get(j);
									ct.setCouponId(coupon.getId());
									couponTypeDao.updateByPrimaryKeySelective(ct);
								}
							}
						}
					}
					return ResponseResult.buildSuccessResponse(c);
	            }
			} 
        } catch (Exception e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
        return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
	}

	@Override
	public ResponseResult<CouponBackage> toUpdateCouponBackage(Long backageId) {
		CouponBackage cb = couponBackageDao.selectByPrimaryKey(backageId);
		List<Coupon> cList = couponDao.getCouponList(backageId);
		if(cList.size() > 0){
			for(int i=0;i<cList.size();i++){
				Coupon coupon = cList.get(i);
				List<CouponType> ct =  couponTypeDao.queryDataList(coupon.getId());
				if(ct.size() > 0){
					for(int j=0;j<ct.size();j++){
						CouponType cType = ct.get(j);
						if(coupon.getCouponType().equals("20")){
							if(cType.getProductId() != null){
								ProductInfo pInfo = new ProductInfo();
				                pInfo.setId(cType.getProductId());
								ResponseResult<ProductInfo> result = productService.getByIdOrPdtNo(pInfo);
				                if (result.isSuccess()) {
				                    ProductInfo info = result.getData();
				                    cType.setModelName(info.getModelName());
				                    cType.setConfigValue(info.getConfigValue());
				                    cType.setSpecBatchNoValue(info.getSpecBatchNoValues());
				                    cType.setSignContractAmount(info.getSignContractAmount());
				                }
							}
						} else if (coupon.getCouponType().equals("30")){
							if(cType.getModelId() != null){
								ResponseResult<?> res = materielService.getMaterielModelDetail(Integer.valueOf(cType.getModelId().toString()));
				                if (res.isSuccess()) {
									Map<String, Object> map = (HashMap) res.getData();
									cType.setModelName(map.get("materielModelName").toString());
				                }
							}
						} else {
							if(cType.getProductId()!= null){
								ProductInfo pInfo = new ProductInfo();
				                pInfo.setId(cType.getProductId());
								ResponseResult<ProductInfo> result = productService.getByIdOrPdtNo(pInfo);
				                if (result.isSuccess()) {
				                    ProductInfo info = result.getData();
				                    cType.setModelName(info.getModelName());
				                    cType.setConfigValue(info.getConfigValue());
				                    cType.setSpecBatchNoValue(info.getSpecBatchNoValues());
				                    cType.setSignContractAmount(info.getSignContractAmount());
				                }
							}
							if(cType.getModelId() != null){
								ResponseResult<?> res = materielService.getMaterielModelDetail(Integer.valueOf(cType.getModelId().toString()));
				                if (res.isSuccess()) {
									Map<String, Object> map = (HashMap) res.getData();
									cType.setModelName(map.get("materielModelName").toString());
				                }
							}
						}
					}
				}
				coupon.setCouponTypeList(ct);
			}
		}
		cb.setCouponList(cList);
		return ResponseResult.buildSuccessResponse(cb);
	}
}
