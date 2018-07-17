/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dto;

/**
 * RecommendCommodityRelation  Dto 对象
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public class RecommendCommodityRelationDto {

    /**
     * 主键id 
     */
    private Long id;
    /**
     * 推荐位id 
     */
    private Long recommendId;
    /**
     * 产品id 
     */
    private Long productId;

    /**
     * 型号id
     */
    private Long   modelId;
    /**
     * 商品imei 
     */
    private String imeiNo;
    /**
     * 商品SN编码 
     */
    private String snNo;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public Long getRecommendId() {
        return this.recommendId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return this.productId;
    }
    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getImeiNo() {
        return this.imeiNo;
    }
    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getSnNo() {
        return this.snNo;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}

