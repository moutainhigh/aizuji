package org.gz.order.common.dto;

import java.io.Serializable;
import java.util.Date;

public class WorkOrderRsp implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 租机编号
   */
  private String rentRecordNo;

  /**
   * 申请时间
   */
  private Date applyTime;

  /**
   * 物料品牌名称
   */
  private String materielBrandName;
  /**
   * 物料型号名称
   */
  private String materielModelName;
  /**
   * 物料规格名称
   */
  private String materielSpecName;
  
  /**
   * 物料名称
   */
  private String matreielName;

  /**
   * 产品租期（月）
   */
  private Integer leaseTerm;

  /**
   * 订单状态
   */
  private Integer state;
  /**
   * 状态描述
   */
  private String stateDesc;

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }


  public String getMaterielBrandName() {
    return materielBrandName;
  }

  public void setMaterielBrandName(String materielBrandName) {
    this.materielBrandName = materielBrandName;
  }

  public String getMaterielModelName() {
    return materielModelName;
  }

  public Date getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }

  public void setMaterielModelName(String materielModelName) {
    this.materielModelName = materielModelName;
  }

  public String getMaterielSpecName() {
    return materielSpecName;
  }

  public void setMaterielSpecName(String materielSpecName) {
    this.materielSpecName = materielSpecName;
  }


  public Integer getLeaseTerm() {
    return leaseTerm;
  }


  public void setLeaseTerm(Integer leaseTerm) {
    this.leaseTerm = leaseTerm;
  }


  public Integer getState() {
    return state;
  }


  public void setState(Integer state) {
    this.state = state;
  }


  public String getStateDesc() {
    return stateDesc;
  }


  public void setStateDesc(String stateDesc) {
    this.stateDesc = stateDesc;
  }

	public String getMatreielName() {
		return matreielName;
	}
	
	public void setMatreielName(String matreielName) {
		this.matreielName = matreielName;
	}
  


}
