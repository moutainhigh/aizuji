package org.gz.warehouse.entity.warehouse;

import java.util.Date;

import org.gz.common.entity.QueryPager;

/**
 * @Title: 还机记录传入参数实体
 * @author daiqingwen
 * @date 2018年3月7日 下午 16:56
 */
public class WarehouseReturnRecordVO extends QueryPager {

	private static final long serialVersionUID = -5484219229993770822L;
	
	private Long id;

    private String sourceOrderNo;       //销售单号


    private String snNo;                //商品SN号


    private String imieNo;              //商品IMIE号


    private String operatorName;        //操作人名称


    private Date operateOn;             //操作时间（入库时间）

    private Date operateOnEndTime;      //操作结束时间
    
    private String transitlnStartTime;//入库开始时间
    
    private String transitlnEndTime;//入库结束时间

    private String applyStartTime;       //还机申请时间

    private String applyEndTime;    //换机申请结束时间

    private Integer transitlnStatus;

    private Long productType;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getImieNo() {
        return imieNo;
    }

    public void setImieNo(String imieNo) {
        this.imieNo = imieNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperateOn() {
        return operateOn;
    }

    public void setOperateOn(Date operateOn) {
        this.operateOn = operateOn;
    }

    public Date getOperateOnEndTime() {
        return operateOnEndTime;
    }

    public void setOperateOnEndTime(Date operateOnEndTime) {
        this.operateOnEndTime = operateOnEndTime;
    }

    public String getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public String getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Integer getTransitlnStatus() {
        return transitlnStatus;
    }

    public void setTransitlnStatus(Integer transitlnStatus) {
        this.transitlnStatus = transitlnStatus;
    }

	public String getTransitlnStartTime() {
		return transitlnStartTime;
	}

	public void setTransitlnStartTime(String transitlnStartTime) {
		this.transitlnStartTime = transitlnStartTime;
	}

	public String getTransitlnEndTime() {
		return transitlnEndTime;
	}

	public void setTransitlnEndTime(String transitlnEndTime) {
		this.transitlnEndTime = transitlnEndTime;
	}

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
	}
    
    
}
