/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dto;

/**
 * Recommend  Dto 对象
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public class RecommendDto {

    /**
     * 主键id 
     */
    private Integer id;
    /**
     * 推荐位名称 
     */
    private String name;
    /**
     * 推荐位类型（10：售卖  20：租赁） 
     */
    private Integer type;
    /**
     * 来源（10：首页推荐位  20：租机主推位 30 ：租机栏目） 
     */
    private Integer source;
    /**
     * 租金显示类型（10：月 20:天） 
     */
    private Integer leaseAmountShowTypt;

    /**
     * 排序号
     */
    private Integer position;

    /**
     * 图片路径
     */
    private String  image;

    /**
     * 物料品牌id
     */
    private Integer materielBrandId;

    /**
     * 新旧程度配置
     */
    private Integer materielNewConfigId;

    /**
     * 是否是主物料   主物料标志 0：否 1：是
     */
    private Integer materielFlag;

    public Integer getMaterielFlag() {
        return materielFlag;
    }

    public void setMaterielFlag(Integer materielFlag) {
        this.materielFlag = materielFlag;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
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

    public Integer getMaterielBrandId() {
        return materielBrandId;
    }

    public void setMaterielBrandId(Integer materielBrandId) {
        this.materielBrandId = materielBrandId;
    }

    public Integer getMaterielNewConfigId() {
        return materielNewConfigId;
    }

    public void setMaterielNewConfigId(Integer materielNewConfigId) {
        this.materielNewConfigId = materielNewConfigId;
    }
}

