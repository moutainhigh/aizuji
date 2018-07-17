package org.gz.order.common.dto;




import org.gz.common.entity.BaseEntity;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;

/**
 * RentRecord RentRecordExtends 数据传输对象
 * @author phd
 */
public class RecordAndExtends extends BaseEntity{


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RentRecord rentRecord;
	private RentRecordExtends rentRecordExtends;
	
    /**
    * 物流 
    */
	private Object logistics;

	 /**
	 * 还机时填写的物流
	 */
	private Object returnLogistics;

    /**
     * 履约次数
     */
    private Integer           performanceCount;
    /**
     * 违约次数
     */
    private Integer           breachCount;

	
	public RentRecord getRentRecord() {
		return rentRecord;
	}

	public void setRentRecord(RentRecord rentRecord) {
		this.rentRecord = rentRecord;
	}

	public RentRecordExtends getRentRecordExtends() {
		return rentRecordExtends;
	}

	public void setRentRecordExtends(RentRecordExtends rentRecordExtends) {
		this.rentRecordExtends = rentRecordExtends;
	}

	public Object getLogistics() {
		return logistics;
	}

	public void setLogistics(Object logistics) {
		this.logistics = logistics;
	}

	public Object getReturnLogistics() {
		return returnLogistics;
	}

	public void setReturnLogistics(Object returnLogistics) {
		this.returnLogistics = returnLogistics;
	}

    public Integer getPerformanceCount() {
        return performanceCount;
    }

    public void setPerformanceCount(Integer performanceCount) {
        this.performanceCount = performanceCount;
    }

    public Integer getBreachCount() {
        return breachCount;
    }

    public void setBreachCount(Integer breachCount) {
        this.breachCount = breachCount;
    }
	
	
	
}