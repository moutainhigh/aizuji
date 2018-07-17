package org.gz.oss.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.constants.OssCommConstants;
import org.gz.oss.common.dao.RecommendCommodityRelationDao;
import org.gz.oss.common.dao.RecommendDao;
import org.gz.oss.common.dto.RecommendCommodityRelationDto;
import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.entity.RecommendCommodityRelation;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.request.RecommendReq;
import org.gz.oss.common.service.RecommendService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecomendServiceImpl implements RecommendService {

    @Resource
    private RecommendDao recommendDao;

    @Resource
    private RecommendCommodityRelationDao recommendCommodityRelationDao;

    @Resource
    private IProductService               productService;

    @Override
    public List<Recommend> queryRecommendList(RecommendDto dto) {
        return recommendDao.queryList(dto);
    }

    @Override
    public void addRecommend(Recommend recommend) {
        recommendDao.add(recommend);
    }

    @Override
    public void updateRecommendInfo(Recommend recommend) {
        UpdateDto<Recommend> updateDto = new UpdateDto<Recommend>();
        Recommend whereDto = new Recommend();
        whereDto.setId(recommend.getId());
        updateDto.setUpdateWhere(whereDto);
        updateDto.setUpdateCloumn(recommend);
        recommendDao.update(updateDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void delRecommendInfo(Integer id) {
        //先根据推荐位ID，删除推荐位下的商品
        this.recommendCommodityRelationDao.deleteByRecommendId(id);
        //删除推荐位
        this.recommendDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void batchUpdate(List<Recommend> recommendReqList) {
        if (recommendReqList != null && recommendReqList.size() > 0) {
            for (Recommend recomend : recommendReqList) {
                UpdateDto<Recommend> updateDto = new UpdateDto<>();
                Recommend updateWhere = new Recommend();
                updateWhere.setId(recomend.getId());
                updateDto.setUpdateCloumn(recomend);
                updateDto.setUpdateWhere(updateWhere);
                recommendDao.update(updateDto);
            }
        }
    }

    @Override
    public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(RecommendDto qvo) {
        // 先通过推荐位id或来源查询出推荐位信息 取出第一条（根据类型也只取第一条）
        List<Recommend> list = this.queryRecommendList(qvo); //此处sql的修改
        if (CollectionUtils.isEmpty(list)) {
            return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage()+",推荐位的ID不存在", null);
        }
        Recommend recommend = list.get(0);

        // 查询推荐位对应的商品信息
        RecommendCommodityRelationDto dto = new RecommendCommodityRelationDto();
        dto.setRecommendId(recommend.getId().longValue());
        List<RecommendCommodityRelation> relationList = recommendCommodityRelationDao.queryList(dto);

        List<ProductInfo> resultList = null;
        List<Long> modelIdList = new ArrayList<>();
        List<Long> productIdList = new ArrayList<>();

        if (recommend.getType() == OssCommConstants.RECOMMEND_TYPE_SALE) {
            // 售卖
            for (RecommendCommodityRelation rcr : relationList) {
                productIdList.add(rcr.getProductId());
            }
            ProductInfoQvo productInfoQvo = new ProductInfoQvo();
            productInfoQvo.setIdList(productIdList);
            ResponseResult<List<ProductInfo>> result = productService.getSaleCommodityInfo(productInfoQvo);
            if (result.isSuccess() && null != result.getData()) {
                resultList = result.getData();
                Map<Long, ProductInfo> pdtIdMap = new HashMap<>();
                for (ProductInfo pi : resultList) {
                    pdtIdMap.put(pi.getId(), pi);
                    if(null != pi.getShowAmount() && null != pi.getSignContractAmount()){
                        pi.setDiscount(this.calcDiscount(pi.getShowAmount(),pi.getSignContractAmount()));
                    }else{
                        pi.setDiscount(new BigDecimal(0));
                    }
                }
                for (RecommendCommodityRelation rcr : relationList) {
                    rcr.setProductInfo(pdtIdMap.get(rcr.getProductId()));
                }
            }
        } else if (recommend.getType() == OssCommConstants.RECOMMEND_TYPE_LEASE) {
            // 租赁
            for (RecommendCommodityRelation rcr : relationList) {
                modelIdList.add(rcr.getModelId());
                ProductInfoQvo productInfoQvo = new ProductInfoQvo();
                productInfoQvo.setMaterielModelId(rcr.getModelId().intValue());
                productInfoQvo.setMaterielNewConfigId(qvo.getMaterielNewConfigId());
                productInfoQvo.setMaterielBrandId(qvo.getMaterielBrandId());
//                productInfoQvo.setIsDeleted((byte)0); //此处查没有下架的商品 ，为最低价的商品   -----------目前需求暂时不启用这个条件
                productInfoQvo.setMaterielFlag(qvo.getMaterielFlag());
                ResponseResult<ProductInfo> result = productService.queryProductInfoWithMaterieImageByModelId(productInfoQvo);
                if (result.isSuccess() && null != result.getData()){
                    ProductInfo data = result.getData();
                    rcr.setProductInfo(data);
                    rcr.setModelName(data.getModelName());
                }
            }
        }

        return ResponseResult.buildSuccessResponse(relationList);
    }


    /**
     * 计算折扣率=签约价值/显示价值*10
     * @param showAmount 显示价值
     * @param signContractAmount 签约价值
     * @return BigDecimal
     */
    private BigDecimal calcDiscount(BigDecimal showAmount, BigDecimal signContractAmount) {
        BigDecimal result = new BigDecimal(0);
        if (showAmount != null && signContractAmount != null && showAmount.compareTo(result) == 1
                && signContractAmount.compareTo(result) == 1 && showAmount.compareTo(signContractAmount) == 1) {
            result = signContractAmount.divide(showAmount,2, BigDecimal.ROUND_UP).multiply(new BigDecimal(10));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void batchUpdateRecommendCommodityRelation(RecommendReq req) {
        //先根据推荐位id删除对应的关联
        this.recommendCommodityRelationDao.deleteByRecommendId(req.getRecommendId());
        
        //批量新增新的商品管理关联
        if (CollectionUtils.isNotEmpty(req.getRcrList())) {
            this.recommendCommodityRelationDao.addBatch(req.getRcrList());
        }
    }

}
