package org.gz.aliOrder.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class AddOrderReq implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -5379707825654379516L;

  /**
   * 产品编号
   */
  @NotNull(message = "产品编号不能为null")
  @NotBlank(message = "产品编号不能为空")
  private String productNo;
  /**
   * 芝麻用户Id
   */
  @NotNull(message = "芝麻用户Id不能为null")
  @NotBlank(message = "芝麻用户Id不能为空")
  private String zmUserId;

  /**
   * 用户id
   */
  private Long userId;

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getZmUserId() {
    return zmUserId;
  }

  public void setZmUserId(String zmUserId) {
    this.zmUserId = zmUserId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
