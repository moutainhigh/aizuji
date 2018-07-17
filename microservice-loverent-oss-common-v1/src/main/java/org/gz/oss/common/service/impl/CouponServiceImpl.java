package org.gz.oss.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.oss.common.dao.CouponBackageDao;
import org.gz.oss.common.dao.CouponDao;
import org.gz.oss.common.dao.CouponRelationDao;
import org.gz.oss.common.dao.CouponTypeDao;
import org.gz.oss.common.dao.CouponUseDetailDao;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponRelation;
import org.gz.oss.common.entity.CouponType;
import org.gz.oss.common.entity.CouponUseDetail;
import org.gz.oss.common.entity.CouponUseDetailQuery;
import org.gz.oss.common.entity.CouponUseParam;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IOrderService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.CouponService;
import org.gz.oss.common.utils.DateToolUtils;
import org.gz.warehouse.common.vo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 优惠券业务逻辑实现
 *
 */
@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponDao couponDao;

	@Autowired
	private CouponRelationDao couponRelationDao;
	
	@Autowired
	private CouponUseDetailDao couponUseDetailDao;
	
	@Resource
	private IOrderService orderService;
	
	@Autowired
	private CouponTypeDao couponTypeDao;
	
	@Resource
    private IProductService productService;
	
	@Resource
    private IMaterielService materielService;
	
	@Autowired
	private CouponBackageDao couponBackageDao;

	@Override
	public ResponseResult<ResultPager<Coupon>> getCouponList(CouponQuery c) {
		int totalNum = couponDao.queryPageCount(c);
        List<Coupon> list = new ArrayList<Coupon>();
        if(totalNum>0) {
        	//查询优惠券列表
            list = couponDao.queryPageList(c);
            if(list.size()>0){
            	//根据有效时间设置优惠券状态
            	for(int i=0;i<list.size();i++){
            		Coupon coupon = list.get(i);
            		Byte couponStatus = DateToolUtils.isEffectiveDate(new Date(), coupon.getValidityStartTime(), coupon.getValidityEndTime());
					coupon.setCouponStatus(couponStatus);
            		//查询已领取优惠券数量
            		int hasNum = couponRelationDao.getHasCount(coupon.getId());
            		coupon.setHasNum(hasNum);
                    //查询已使用优惠券数量
            		int useNum = couponRelationDao.getUseCount(coupon.getId());
            		coupon.setUseNum(useNum);
            	}
            }
        }
        ResultPager<Coupon> data = new ResultPager<Coupon>(totalNum, c.getCurrPage(), c.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}
	
	@Transactional
	@Override
	public ResponseResult<?> addCoupon(Coupon c) {
		try {
			c.setUpdateNo(new Date());
			c.setCreateNo(new Date());
			c.setStatus((byte)0);
			c.setFlag((byte)0);
			c.setIsBackage((byte)0);
			if (this.couponDao.insert(c) > 0) {
				int totalNum = c.getCouponTypeList().size();
				if(totalNum > 0 ){
					for(int i=0;i<totalNum;i++){
						CouponType ct = c.getCouponTypeList().get(i);
						ct.setCouponId(c.getId());
						ct.setCreateNo(new Date());
						couponTypeDao.insertSelective(ct);
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
	public ResponseResult<Coupon> toUpdateCoupon(Long couponId) {
		Coupon coupon = couponDao.selectByPrimaryKey(couponId);
		if(coupon != null){
			//根据有效时间设置优惠券状态
			Byte couponStatus = DateToolUtils.isEffectiveDate(new Date(), coupon.getValidityStartTime(), coupon.getValidityEndTime());
			coupon.setCouponStatus(couponStatus);
			//查询已领取优惠券数量
    		int hasNum = couponRelationDao.getHasCount(coupon.getId());
    		coupon.setHasNum(hasNum);
            //查询已使用优惠券数量
    		int useNum = couponRelationDao.getUseCount(coupon.getId());
    		coupon.setUseNum(useNum);
			List<CouponType> ct =  couponTypeDao.queryDataList(coupon.getId());
			if(ct.size() > 0){
				for(int i=0;i<ct.size();i++){
					CouponType cType = ct.get(i);
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
			return ResponseResult.buildSuccessResponse(coupon);
		} 
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	@Transactional
	@Override
	public ResponseResult<?> updateCoupon(Coupon c) {
		try {
        	c.setUpdateNo(new Date());
        	c.setCreateNo(new Date());
			if (this.couponDao.updateByPrimaryKeySelective(c) > 0) {
				int totalNum = c.getCouponTypeList().size();
				if(totalNum > 0 ){
					couponTypeDao.deleteByCouponId(c.getId());
					for(int i=0;i<totalNum;i++){
						CouponType ct = c.getCouponTypeList().get(i);
						ct.setCouponId(c.getId());
						ct.setCreateNo(new Date());
						couponTypeDao.insertSelective(ct);
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
	public ResponseResult<?> selectByPrimaryKey(Long id) {
		try {
			Coupon coupon = couponDao.selectByPrimaryKey(id);
			//根据有效时间设置优惠券状态
			Byte couponStatus = DateToolUtils.isEffectiveDate(new Date(), coupon.getValidityStartTime(), coupon.getValidityEndTime());
			coupon.setCouponStatus(couponStatus);
			//查询已领取优惠券数量
    		int hasNum = couponRelationDao.getHasCount(coupon.getId());
    		coupon.setHasNum(hasNum);
            //查询已使用优惠券数量
    		int useNum = couponRelationDao.getUseCount(coupon.getId());
    		coupon.setUseNum(useNum);
            return ResponseResult.buildSuccessResponse(coupon);
        } catch (Exception e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
	}

	@Override
	public ResponseResult<ResultPager<CouponRelation>> queryHasCouponList(CouponUserQuery crq) {
		List<CouponRelation> list = new ArrayList<CouponRelation>();
		int totalNum = couponRelationDao.queryPageCount(crq);
        if(totalNum>0){
        	list = couponRelationDao.queryCouponRelationList(crq);
        }
		ResultPager<CouponRelation> data = new ResultPager<CouponRelation>(totalNum, crq.getCurrPage(), crq.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<ResultPager<CouponUseDetail>> queryUseCouponList(CouponUseDetailQuery cudq) {
		int totalNum = couponUseDetailDao.queryPageCount(cudq);
        List<CouponUseDetail> list = new ArrayList<CouponUseDetail>();
        if(totalNum>0){
        	list = couponUseDetailDao.queryCouponUseDetailList(cudq);
        }
		ResultPager<CouponUseDetail> data = new ResultPager<CouponUseDetail>(totalNum, cudq.getCurrPage(), cudq.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}

	@Transactional
	@Override
	public ResponseResult<?> useCoupon(CouponUseParam cup) {
		CouponRelation cr = couponRelationDao.selectByParams(cup.getUserId(), cup.getCouponId());
		ResponseResult<OrderDetailResp> responseResult = orderService.queryBackOrderDetail(cup.getOrderNo());
		if(cr != null && responseResult.isSuccess()){
			cr.setCouponStatus((byte)1);
			Date date = new Date();
			cr.setUseTime(date);
			cr.setUserId(cup.getUserId());
			couponRelationDao.updateByPrimaryKeySelective(cr);
			OrderDetailResp or = responseResult.getData();
			CouponUseDetail cud = new CouponUseDetail();

			cud.setCouponId(cup.getCouponId());
			cud.setUserId(cup.getUserId());
			cud.setUserName(cup.getUserName());
			cud.setConsumeType(or.getProductType());
			cud.setGoodsDetail(or.getMaterielModelName() + or.getMaterielSpecName());
			cud.setCreateNo(date);
			cud.setReceiveTime(cr.getReceiveTime());
			cud.setUseTime(date);
			cud.setOrderNo(or.getRentRecordNo());

			couponUseDetailDao.insertSelective(cud);
			return ResponseResult.buildSuccessResponse();
		}
		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
	}

	@Override
	public ResponseResult<?> useRegisterGiveCoupon(CouponUseParam cup) {
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		List<Coupon> cList = couponDao.queryCouponList(nowDate);
		if(cList.size() > 0){
			for(int i=0;i<cList.size();i++){
				Coupon c = cList.get(i);
				CouponRelation couponRe = couponRelationDao.selectByParams(cup.getUserId(), c.getId());
				if(null == couponRe){
					CouponRelation cr = new CouponRelation();
					cr.setCouponId(c.getId());
					cr.setUserName(cup.getUserName());
					cr.setUserId(cup.getUserId());
					cr.setReceiveTime(new Date());
					couponRelationDao.insertSelective(cr);
				}
			}
		}
//		优惠券礼包（2018-4-7 不发版）
//		List<CouponBackage> cbList = couponBackageDao.queryCouponBackList(nowDate);
//		if(cbList.size() > 0){
//			for(int i=0;i<cbList.size();i++){
//				CouponBackage cb = cbList.get(i);
//				CouponRelation cr = new CouponRelation();
//				cr.setBackageId(cb.getId());
//				cr.setUserName(cup.getUserName());
//				cr.setUserId(cup.getUserId());
//				cr.setReceiveTime(new Date());
//				couponRelationDao.insertSelective(cr);
//			}
//		}
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<ResultPager<Coupon>> queryCouponList(CouponUserQuery cuq) {
		int totalNum = couponRelationDao.queryPageCount(cuq);
        List<Coupon> list = new ArrayList<Coupon>();
        if(totalNum>0) {
        	List<CouponRelation> crList = couponRelationDao.queryCouponRelationList(cuq);
            if(crList.size()>0){
            	for(int i=0;i<crList.size();i++){
            		CouponRelation cr = crList.get(i);
            		Coupon c = couponDao.selectByPrimaryKey(cr.getCouponId());
            		c.setStatus(cr.getCouponStatus());
            		c.setCouponStatus(DateToolUtils.isEffectiveDate(new Date(), c.getValidityStartTime(), c.getValidityEndTime()));
            		list.add(c);
            	}
            	Collections.sort(list, new Comparator<Coupon>(){  
    	            public int compare(Coupon c1, Coupon c2) {
    	            	return sortPriority((int)c1.getCouponStatus(), (int)c2.getCouponStatus());
    	            }  
    	        });
            }
        }
        ResultPager<Coupon> data = new ResultPager<Coupon>(totalNum, cuq.getCurrPage(), cuq.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}
	/**
     * 给出排序标准
     * @param i
     * @param j
     * @return
     */
    private int sortPriority(int i, int j) {
        return getPriority(j) - getPriority(i);
    }
    
    /**
     * 给出自定义优先级：返回数字越大优先级越高
     * @param i input
     * @return  priority
     */
    private int getPriority(int i) {
        int a;
        if (i == 20) {
            a = 3;
        } else if (i == 10){
            a = 2;
        } else if (i == 30) {
            a = 1;
        } else {
            a = 0;
        } 
        return a;
    }

	@Override
	public ResponseResult<Coupon> queryCouponDetail(CouponQuery cq) {
		CouponRelation cr = couponRelationDao.selectByParams(cq.getUserId(), cq.getCouponId());
		Coupon c = couponDao.selectByPrimaryKey(cq.getCouponId());
		c.setStatus(cr.getCouponStatus());
		return ResponseResult.buildSuccessResponse(c);
	}
	
	@Override
	public ResponseResult<ResultPager<Coupon>> getUserCouponList(CouponUserQuery cuq) {
		List<CouponRelation> crList = couponRelationDao.getCRListByUserId(cuq.getUserId());
		List<Coupon> list = new ArrayList<Coupon>();
		if(crList.size() > 0){
			for(int i=0;i<crList.size();i++){
				CouponRelation cr = crList.get(i);
        		Coupon c = couponDao.selectByPrimaryKey(cr.getCouponId());
        		c.setStatus(cr.getCouponStatus());
        		List<CouponType> ctList = couponTypeDao.queryDataList(c.getId());
        		//优惠券类型  10 通用  20 售卖 30 租赁-买断 31 租赁-首期款 32 租赁-交租
        		Byte cStatus = DateToolUtils.isEffectiveDate(new Date(), c.getValidityStartTime(), c.getValidityEndTime());
        		if(c.getCouponType() == 10){
            		if(cStatus != 30){
            			if (cStatus == 20){
            				c.setCouponStatus((byte)20);
            			} else {
            				c.setCouponStatus((byte)10);
            			}
            			list.add(c);
            		}
        		} else if (c.getCouponType() == 20){
            		if(cStatus != 30){
            			if (cStatus == 20 && cuq.getCouponType() == 20){
            				boolean isUse = false;
            				if(ctList.size()>0){
            					for(int j=0;j<ctList.size();j++){
            						CouponType ct = ctList.get(j);
            						if(ct.getModelId() == cuq.getModelId() || ct.getProductId()== cuq.getProductId()){
            							isUse = true;
            						}
            					}
            				} 
            				if(ctList.size()>0 && isUse){
            					c.setCouponStatus((byte)20);
            				} else {
            					c.setCouponStatus((byte)10);
            				}
            			} else {
            				c.setCouponStatus((byte)10);
            			}
            			list.add(c);
            		}
        		} else if (c.getCouponType() == 30){
            		if(cStatus != 30){
            			if (cStatus == 20 && cuq.getCouponType() == 30){
            				boolean isUse = false;
            				if(ctList.size()>0){
            					for(int j=0;j<ctList.size();j++){
            						CouponType ct = ctList.get(j);
            						if(ct.getModelId() == cuq.getModelId()){
            							isUse = true;
            						}
            					}
            				} 
            				if(ctList.size()>0 && isUse){
            					c.setCouponStatus((byte)20);
            				} else {
            					c.setCouponStatus((byte)10);
            				}
            			} else {
            				c.setCouponStatus((byte)10);
            			}
            			list.add(c);
            		}
        		} else if (c.getCouponType() == 31){
        			if(cStatus != 30){
            			if (cStatus == 20 && cuq.getCouponType() == 31){
            				boolean isUse = false;
            				if(ctList.size()>0){
            					for(int j=0;j<ctList.size();j++){
            						CouponType ct = ctList.get(j);
            						if(ct.getModelId() == cuq.getModelId()){
            							isUse = true;
            						}
            					}
            				} 
            				if(ctList.size()>0 && isUse){
            					c.setCouponStatus((byte)20);
            				} else {
            					c.setCouponStatus((byte)10);
            				}
            			} else {
            				c.setCouponStatus((byte)10);
            			}
            			list.add(c);
            		}
        		} else {
        			if(cStatus != 30){
            			if (cStatus == 20 && cuq.getCouponType() == 32){
            				boolean isUse = false;
            				if(ctList.size()>0){
            					for(int j=0;j<ctList.size();j++){
            						CouponType ct = ctList.get(j);
            						if(ct.getModelId() == cuq.getModelId()){
            							isUse = true;
            						}
            					}
            				} 
            				if(ctList.size()>0 && isUse){
            					c.setCouponStatus((byte)20);
            				} else {
            					c.setCouponStatus((byte)10);
            				}
            			} else {
            				c.setCouponStatus((byte)10);
            			}
            			list.add(c);
            		}
        		}
			}
			Collections.sort(list, new Comparator<Coupon>(){  
	            public int compare(Coupon c1, Coupon c2) {
	            	if (c2.getCouponStatus().compareTo(c1.getCouponStatus()) == 0){
	            		return c2.getAmount().compareTo(c1.getAmount());
	            	} else {
	            		return c2.getCouponStatus().compareTo(c1.getCouponStatus());
	            	}
	            }  
	        });   
		}
        ResultPager<Coupon> data = new ResultPager<Coupon>(list.size(), cuq.getCurrPage(), cuq.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}
	
	@Transactional
	@Override
	public ResponseResult<?> useReturnCoupon(CouponUseParam cup) {
		CouponRelation cr = couponRelationDao.queryDetail(cup.getCouponId());
		if(cr != null){
			cr.setCouponStatus((byte)0);
			cr.setIsReturn((byte)1);
			Date date = new Date();
			cr.setUseTime(date);
			cr.setUserId(cup.getUserId());
			couponRelationDao.updateByPrimaryKeySelective(cr);
			couponUseDetailDao.updateByParams(cup.getCouponId(), cup.getUserId());
			return ResponseResult.buildSuccessResponse();
		}
		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
	}
	
}
