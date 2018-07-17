/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.entity;

import org.gz.warehouse.common.vo.ProductInfo;

/**
 * RecommendCommodityRelation 实体类
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public class RecommendCommodityRelation {


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

    /**
     * 推荐位名称
     */
    private String name;

    private String modelName; //型号名称

    private ProductInfo productInfo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
