package org.gz.oss.common.service.impl;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.StringUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.Enum.CommodityType;
import org.gz.oss.common.dao.AdvertisingDao;
import org.gz.oss.common.entity.Advertising;
import org.gz.oss.common.entity.AdvertisingVO;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.AdvertisingService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private AdvertisingDao advertisingDao;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMaterielService materielService;

    @Override
    public ResponseResult<List<Advertising>> queryAdverList() {
        List<Advertising> list = this.advertisingDao.queryAdverList();
        List<Advertising> resultList = new ArrayList<>();
        for (int i = 0;i < list.size(); i++){
            Advertising a = list.get(i);
            //获取产品信息
            Advertising result = setProductInfo(a);
            resultList.add(result);
        }
        return ResponseResult.buildSuccessResponse(resultList);
    }

    /**
     * 查询产品信息
     * @param a
     * @return
     */
    private Advertising setProductInfo(Advertising a){
        if(a.getType().intValue() == CommodityType.SALE.getCode()){
            // 如果是关联售卖商品 根据产品id获取产品信息
            ProductInfo pInfo = new ProductInfo();
            pInfo.setId(a.getProductId());
            ResponseResult<ProductInfo> result = productService.getByIdOrPdtNo(pInfo);
            if(result.isSuccess() && null != result.getData()){
                ProductInfo info = result.getData();
                a.setModelName(info.getModelName());
                a.setConfigValue(info.getConfigValue());
                a.setSpecBatchNoValues(info.getSpecBatchNoValues());
            }
        }else if(a.getType().intValue() == CommodityType.LEASE.getCode()){
            // 如果关联租赁商品 根据型号id获取型号名称
            if(a.getModelId() == null){
                return a;
            }
            ResponseResult<?> res = materielService.getMaterielModelDetail(a.getModelId().intValue());
            if (res.isSuccess() && null != res.getData()) {
                Map<String, Object> map = (HashMap) res.getData();
                a.setModelName(map.get("materielModelName").toString());
                Object materielClassIdObj = map.get("materielClassId");
                Object materielBrandIdObj = map.get("materielBrandId");
                a.setMaterielClassId(materielClassIdObj == null ? null : (Integer) (materielClassIdObj));
                a.setMaterielBrandId(materielBrandIdObj == null ? null : (Integer) (materielBrandIdObj));
            }
        }
        return a;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> insert(AdvertisingVO vo) {
        AdvertisingVO adv = setOperatorInfo(vo);
        if(this.advertisingDao.insert(adv) <= 0){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
        return ResponseResult.buildSuccessResponse();
    }

    /**
     * 设置操作人ID和创建时间
     * @return AdvertisingVO
     */
    private AdvertisingVO setOperatorInfo(AdvertisingVO vo) {
        BaseController bc = new BaseController();
        AuthUser user = bc.getAuthUser();
        vo.setCreateTime(new Date());
        vo.setOperatingId(user.getId());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> update(AdvertisingVO vo) {
        try {
            AdvertisingVO adv = setOperatorInfo(vo);
            if(this.advertisingDao.update(adv) <= 0){
                return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
            }
        }catch (Exception e){
            log.info("更新广告失败：{}" ,e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> delete(int id) {
        if(this.advertisingDao.delete(id) <= 0){
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
        }
        return ResponseResult.buildSuccessResponse();
    }


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> move(AdvertisingVO vo) {
        List<AdvertisingVO> list = vo.getAttaList();
        for (AdvertisingVO a : list){
            this.advertisingDao.move(a);
        }
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    public ResponseResult<Advertising> getAdvertisingById(int id) {
        Advertising adv = this.advertisingDao.getAdvertisingById(id);
        //获取产品信息
        setProductInfo(adv);
        return ResponseResult.buildSuccessResponse(adv);
    }
}
