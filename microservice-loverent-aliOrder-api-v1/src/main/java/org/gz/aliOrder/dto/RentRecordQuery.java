package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.util.List;

import org.gz.common.OrderBy;
import org.gz.common.entity.QueryPager;

public class RentRecordQuery extends QueryPager implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 租机编号
   */
  private String rentRecordNo;
  /**
   * 用户id
   */
  private Long userId;

  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 身份证号
   */
  private String idNo;
  /**
   * 手机号
   */
  private String phoneNum;

  /**
   * 手机sn编码
   */
  private String snCode;
  /**
   * IMEI
   */
  private String imei;
  /**
   * 还机时填写的物流单号
   */
  private String returnLogisticsNo;

  /**
   * 大于申请时间的字段
   */
  private String startApplyTime;
  /**
   * 小于申请时间的字段
   */
  private String endApplyTime;
  /**
   * 签约开始时间
   */
  private String signStartTime;

  /**
   * 签约结束时间
   */
  private String signEndTime;

  /**
   * 订单状态 // WaitPick(7,"待拣货"), // WaitSend(8,"待发货"), // SendOut(9,"发货中"),
   */
  private Integer state;
  /**
   * 产品类型 1:租赁 2:以租代购 3:出售 
   */
  private Integer productType;
  /**
   * 物料分类id 
   */
  private Integer materielClassId;
  
  /**
   * 产品类型list
   */
  private List<Integer> productTypes;

  /**
   * 不传订单状态时候默认设置的订单状态list（后端自己处理，不需要接口传值）
   */
  private List<Integer> stateList;
  
  /**
   * 排序字段集合
   */
  private List<OrderBy> orderBy;

  /**
   * 控制订单状态不查询状态为0的订单信息
   */
  private Integer notZero;

  /**
   * 控制订单状态查询状态大于某个状态的订单信息
   */
  private Integer greateZero;

  public String getStartApplyTime() {
    return startApplyTime;
  }

  public void setStartApplyTime(String startApplyTime) {
    this.startApplyTime = startApplyTime;
  }

public String getSignStartTime()
    {
      return signStartTime;
    }

  public void setSignStartTime(String signStartTime)
    {
      this.signStartTime = signStartTime;
    }

  public String getSignEndTime()
    {
      return signEndTime;
    }

  public void setSignEndTime(String signEndTime)
    {
      this.signEndTime = signEndTime;
    }

  public Integer getState()
    {
      return state;
    }

  public void setState(Integer state)
    {
      this.state = state;
    }

  public List<OrderBy> getOrderBy()
    {
      return orderBy;
    }

  public void setOrderBy(List<OrderBy> orderBy)
    {
      this.orderBy = orderBy;
    }



  public String getRentRecordNo()
    {
      return rentRecordNo;
    }

  public void setRentRecordNo(String rentRecordNo)
    {
      this.rentRecordNo = rentRecordNo;
    }

  public String getSnCode()
    {
      return snCode;
    }

  public void setSnCode(String snCode)
    {
      this.snCode = snCode;
    }

  public String getImei()
    {
      return imei;
    }

  public void setImei(String imei)
    {
      this.imei = imei;
    }

  public List<Integer> getStateList()
    {
      return stateList;
    }

  public void setStateList(List<Integer> stateList)
    {
      this.stateList = stateList;
    }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public Integer getNotZero() {
    return notZero;
  }

  public void setNotZero(Integer notZero) {
    this.notZero = notZero;
  }

  public Integer getGreateZero() {
    return greateZero;
  }

  public void setGreateZero(Integer greateZero) {
    this.greateZero = greateZero;
  }

  public String getEndApplyTime() {
    return endApplyTime;
  }

  public void setEndApplyTime(String endApplyTime) {
    this.endApplyTime = endApplyTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getReturnLogisticsNo() {
    return returnLogisticsNo;
  }

  public void setReturnLogisticsNo(String returnLogisticsNo) {
    this.returnLogisticsNo = returnLogisticsNo;
  }

	public Integer getProductType() {
		return productType;
	}
	
	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	public List<Integer> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<Integer> productTypes) {
		this.productTypes = productTypes;
	}
	
  
}
