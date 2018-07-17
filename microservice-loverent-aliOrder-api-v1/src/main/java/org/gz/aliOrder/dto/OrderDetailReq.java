package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;




/**
 * 订单详情请求对象 Author Version Date Changes 临时工 1.0 2017年12月19日 Created
 */
public class OrderDetailReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String            userId;
    /**
   * 芝麻用户id
   */
  @NotNull(message = "芝麻用户id不能为null")
  @NotBlank(message = "芝麻用户id不能为空")
  private String zmUserId;
  /**
   * 前端返回状态编码
   */
    private Integer           state;

  /**
   * 产品类型 1租赁和以租代售 3.售卖
   */
  private Integer productTypes;

    /**
     * 后端对应的状态码list用于到数据库查询
     */
    private List<Integer>     backState;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Integer> getBackState() {
        return backState;
    }

    public void setBackState(List<Integer> backState) {
        this.backState = backState;
    }

  public Integer getProductTypes() {
    return productTypes;
  }

  public void setProductTypes(Integer productTypes) {
    this.productTypes = productTypes;
  }

  public String getZmUserId() {
    return zmUserId;
  }

  public void setZmUserId(String zmUserId) {
    this.zmUserId = zmUserId;
  }

}
