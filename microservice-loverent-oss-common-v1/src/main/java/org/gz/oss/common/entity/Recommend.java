/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.entity;

import javax.validation.constraints.NotNull;


/**
 * Recommend 实体类
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public class Recommend {


    /**
     * 主键id 
     */
    private Integer id;
    /**
     * 推荐位名称 
     */
    @NotNull(message = "推荐位名称不能为空")
    private String name;
    /**
     * 推荐位类型（10：售卖  20：租赁） 
     */
    @NotNull(message = "推荐位类型不能为空")
    private Integer type;
    /**
     * 来源（10：首页推荐位  20：租机主推位 30 ：租机栏目） 
     */
    @NotNull(message = "推荐位来源不能为空")
    private Integer source;
    /**
     * 租金显示类型（10：月 20:天） 
     */
    private Integer leaseAmountShowTypt;

    /**
     * 排序
     */
    private Integer position;

    /**
     * 图片路径
     */
    private String  image;

    /**
     * 关联产品数量
     */
    private Integer productNum;

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setSource(Integer source) {
        this.source = source;
    }
    
    public Integer getSource() {
        return this.source;
    }
    
    public void setLeaseAmountShowTypt(Integer leaseAmountShowTypt) {
        this.leaseAmountShowTypt = leaseAmountShowTypt;
    }
    
    public Integer getLeaseAmountShowTypt() {
        return this.leaseAmountShowTypt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
    
}
