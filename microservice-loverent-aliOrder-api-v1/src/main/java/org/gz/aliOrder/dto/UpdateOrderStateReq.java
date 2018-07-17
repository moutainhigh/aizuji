package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 修改订单状态请求对象 Author Version Date Changes 临时工 1.0 2017年12月14日 Created
 */
public class UpdateOrderStateReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * 租机编号
     */
    private String  rentRecordNo;
    /**
   * 0提交 1 待审批 2 已拒绝 3 待支付 4 待签约 5 已取消 6 待配货 7 待拣货 8 待发货 9 发货中 10 正常履约（已经签收） 11
   * 提前解约中 12 提前解约 13 换机订单状态 14 维修订单状态 15 已逾期 16 归还中 17 提前买断 18 提前归还（废弃） 19 正常买断
   * 20 已归还 21提前买断中 22正常买断中 23提前归还中（废弃） 24履约完成 25 强制买断中 26 强制买断 27定损完成 28 强制履约中
   * 29 强制履约完成 30 强制归还中 31 强制定损完成 32 强制归还完成 33退货中 34已退货
   */
    private Integer state;

    /**
     * 创建人ID
     */
    private Long    createBy;
    /**
     * 创建人用户名
     */
    private String            createMan;
    /**
   * 原因 1, "想要重新下单"), (2, "商品价格较贵"), (3, "等待时间较长"), (4, "是想了解流程"), (5, "不想要了");
   */
    private String            remark;


  /**
   * 还机时填写的物流单号
   */
  private String returnLogisticsNo;

  /**
   * 设备imei号
   */
  private String imei;
  /**
   * snCode
   */
  private String snCode;

  /**
   * 顺丰商户号
   */
  private String businessNo;

  /**
   * 折损费
   */
  private BigDecimal discountFee;
  /**
   * 调用方式 ： 0 app调用 1.清算调用
   */
  private int callType;

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Long getCreateBy() {
    return createBy;
  }

  public void setCreateBy(Long createBy) {
    this.createBy = createBy;
  }

  public String getCreateMan() {
    return createMan;
  }

  public void setCreateMan(String createMan) {
    this.createMan = createMan;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getReturnLogisticsNo() {
    return returnLogisticsNo;
  }


  public void setReturnLogisticsNo(String returnLogisticsNo) {
    this.returnLogisticsNo = returnLogisticsNo;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public String getSnCode() {
    return snCode;
  }

  public void setSnCode(String snCode) {
    this.snCode = snCode;
  }

  public String getBusinessNo() {
    return businessNo;
  }

  public void setBusinessNo(String businessNo) {
    this.businessNo = businessNo;
  }


  public BigDecimal getDiscountFee() {
    return discountFee;
  }


  public void setDiscountFee(BigDecimal discountFee) {
    this.discountFee = discountFee;
  }

  public int getCallType() {
    return callType;
  }

  public void setCallType(int callType) {
    this.callType = callType;
  }

}
