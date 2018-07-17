package org.gz.liquidation.common.dto.invoice;



import org.gz.common.entity.QueryPager;

/**
 * 
 * @Description:订单发票查询请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
public class InvoiceReq extends QueryPager {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
  /**
   * 订单编号
   */
  private String rentRecordNo;
  /**
   * 手机号码
   */
  private String phoneNum;
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 是否已经开票 0：否 1：是
   */
  private Integer invoiceEnd;

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getInvoiceEnd() {
    return invoiceEnd;
  }

  public void setInvoiceEnd(Integer invoiceEnd) {
    this.invoiceEnd = invoiceEnd;
  }
    
}