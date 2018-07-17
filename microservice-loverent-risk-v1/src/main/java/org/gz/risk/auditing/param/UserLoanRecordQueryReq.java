package org.gz.risk.auditing.param;

import java.io.Serializable;
import java.util.List;

import org.gz.risk.auditing.entity.BaseReq;
import org.gz.risk.bean.OrderBy;

public class UserLoanRecordQueryReq extends BaseReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String value;
    private List<String>      loanRecordIds;

    /**
     * 申请起始时间
     */
    private String            startTime;
    /**
     * 申请结束时间
     */
    private String            endTime;

    /**
     * 排序名称
     */
    private List<OrderBy>     orderBy;

    /**
     * 起始位置
     */
    private Integer           start;

    /**
     * 每页显示条数
     */
    private Integer           pageSize;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    /**
     * Returns this loanRecordIds object.
     * 
     * @return this loanRecordIds
     */
    public List<String> getLoanRecordIds() {
        return loanRecordIds;
    }

    /**
     * Sets this loanRecordIds.
     * 
     * @param loanRecordIds this loanRecordIds to set
     */
    public void setLoanRecordIds(List<String> loanRecordIds) {
        this.loanRecordIds = loanRecordIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<OrderBy> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<OrderBy> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
