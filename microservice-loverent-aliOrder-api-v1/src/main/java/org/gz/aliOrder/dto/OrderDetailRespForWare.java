package org.gz.aliOrder.dto;

/**
 * 提供给仓管系统查询列表的返回对象信息 Author Version Date Changes 临时工 1.0 2017年12月27日 Created
 */
public class OrderDetailRespForWare extends OrderDetailResp {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * 拣货人 8 待发货
   */
  private String checkMan;
  /**
   * 拣货日期
   */
  private String checkDate;

  /**
   * 填单人 9 发货中
   */
  private String sendMan;
  /**
   * 填单日期
   */
  private String sendDate;

  /**
   * 申请时间 字符串格式
   */
  private String applyTimes;


  public String getCheckMan()
    {
      return checkMan;
    }

  public void setCheckMan(String checkMan)
    {
      this.checkMan = checkMan;
    }

  public String getCheckDate()
    {
      return checkDate;
    }

  public void setCheckDate(String checkDate)
    {
      this.checkDate = checkDate;
    }


  public String getSendMan()
    {
      return sendMan;
    }


  public void setSendMan(String sendMan)
    {
      this.sendMan = sendMan;
    }

  public String getSendDate()
    {
      return sendDate;
    }

  public void setSendDate(String sendDate)
    {
      this.sendDate = sendDate;
    }

  public String getApplyTimes()
    {
      return applyTimes;
    }

  public void setApplyTimes(String applyTimes)
    {
      this.applyTimes = applyTimes;
    }

}
