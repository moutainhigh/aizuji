package org.gz.order.common.dto;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gz.common.OrderBy;
import org.gz.common.entity.QueryPager;
import org.gz.order.common.Enum.RentRecordSort;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;

/**
 * RentRecord RentRecordExtends 请求参数
 * @author phd
 */ 
public class RecordAndExtendsQuery extends QueryPager implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RentRecord rentRecord;
	
	private RentRecordExtends rentRecordExtends;
	
	
    /**
     * 
     * 查询参数
     * 
     * */
    

	/**
     * 申请开始时间
     */
    private Date applyStartTime;
    
    /**
     * 申请结束时间
     */
    private Date applyEndTime;
    
    /**
     * 审核通过开始时间
     */
    private Date approvalStartTime;
    
    /**
     * 审核通过结束时间
     */
    private Date approvalEndTime;
    
    /**
     * 支付完成开始时间
     */
    private Date payStartTime;
    
    /**
     * 支付完成结束时间
     */
    private Date payEndTime;
    
    /**
     * 解约申请开始时间
     */
    private Date terminateApplyStartTime;
    
    /**
     * 解约申请结束时间
     */
    private Date terminateApplyEndTime;
    
    /**
     * 订单状态集合
     */
    private List<Integer> recordStates;
    
    /**
     * 排序字段集合
     */
    private List<OrderBy> orderBy;
    
    /**
     * RentRecordSort枚举类的id
     */
    private Integer sortId;
    
    private List<Integer> noRecordStates;
    
    private List<Integer> productTypes;
    

    
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

	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Date getApprovalStartTime() {
		return approvalStartTime;
	}

	public void setApprovalStartTime(Date approvalStartTime) {
		this.approvalStartTime = approvalStartTime;
	}

	public Date getApprovalEndTime() {
		return approvalEndTime;
	}

	public void setApprovalEndTime(Date approvalEndTime) {
		this.approvalEndTime = approvalEndTime;
	}

	public Date getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public Date getTerminateApplyStartTime() {
		return terminateApplyStartTime;
	}

	public void setTerminateApplyStartTime(Date terminateApplyStartTime) {
		this.terminateApplyStartTime = terminateApplyStartTime;
	}

	public Date getTerminateApplyEndTime() {
		return terminateApplyEndTime;
	}

	public void setTerminateApplyEndTime(Date terminateApplyEndTime) {
		this.terminateApplyEndTime = terminateApplyEndTime;
	}

	
	public List<Integer> getRecordStates() {
		return recordStates;
	}

	public void setRecordStates(List<Integer> recordStates) {
		this.recordStates = recordStates;
	}

	public List<OrderBy> getOrderBy() {
		List<OrderBy> orderByList = new ArrayList<>();
        OrderBy ob = new OrderBy();
        orderByList.add(ob);
        RentRecordSort rentRecordSort = RentRecordSort.getSortEnumById(this.sortId);
        ob.setCloumnName(rentRecordSort.getSortAlias());
        ob.setSequence(rentRecordSort.getOrderByType());
		return orderByList;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public List<Integer> getNoRecordStates() {
		return noRecordStates;
	}

	public void setNoRecordStates(List<Integer> noRecordStates) {
		this.noRecordStates = noRecordStates;
	}

	public List<Integer> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<Integer> productTypes) {
		this.productTypes = productTypes;
	}
	
    
}