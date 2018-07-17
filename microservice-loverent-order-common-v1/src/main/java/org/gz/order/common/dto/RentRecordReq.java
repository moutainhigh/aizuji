package org.gz.order.common.dto;

import java.io.Serializable;

import org.gz.order.common.entity.RentRecord;

public class RentRecordReq extends RentRecord implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 创建人ID
     */
    private Long              createBy;
    /**
     * 创建人用户名
     */
    private String            createMan;

  /**
   * 商户号（月结卡号）
   */
  private String businessNo;

  /**
   * 物流类型0我司发货 1用户归还 2用户维修
   */
  private int Sftype;

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

  public String getBusinessNo() {
    return businessNo;
  }

  public void setBusinessNo(String businessNo) {
    this.businessNo = businessNo;
  }

  public int getSftype() {
    return Sftype;
  }

  public void setSftype(int sftype) {
    Sftype = sftype;
  }

}
