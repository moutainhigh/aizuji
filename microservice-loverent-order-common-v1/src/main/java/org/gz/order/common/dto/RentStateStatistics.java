package org.gz.order.common.dto;




import org.gz.common.entity.BaseEntity;

/**
 * 订单状态统计
 * @author phd
 */
public class RentStateStatistics extends BaseEntity{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer countApplyPending;//待提交
	private Integer countApprovalPending; //待审批
	private Integer countRefuse;//已拒绝
	private Integer countWaitPayment;//待支付
	private Integer countWaitSignup;//待签约
	private Integer countCancel;//已取消
	private Integer countWaitAssembly;//待配货
	private Integer countWaitPick;//待拣货
	private Integer countWaitSend;//待发货
	private Integer countSendOut;//发货中
	private Integer countNormalPerformance;//正常履约
	private Integer countPrematureTerminating;//提前解约中
	private Integer countPrematureTermination;//提前解约
	private Integer countChange;//换机订单状态
	private Integer countRepair;//维修订单状态
	private Integer countOverdue;//已逾期
	private Integer countRecycling;//回收中
	private Integer countEarlyBuyout;//提前买断
	private Integer countEarlyRecycle;//提前回收
	private Integer countNormalBuyout;//正常买断
	private Integer countRecycle;//已回收
	public Integer getCountApplyPending() {
		return countApplyPending;
	}
	public void setCountApplyPending(Integer countApplyPending) {
		this.countApplyPending = countApplyPending;
	}
	public Integer getCountApprovalPending() {
		return countApprovalPending;
	}
	public void setCountApprovalPending(Integer countApprovalPending) {
		this.countApprovalPending = countApprovalPending;
	}
	public Integer getCountRefuse() {
		return countRefuse;
	}
	public void setCountRefuse(Integer countRefuse) {
		this.countRefuse = countRefuse;
	}
	public Integer getCountWaitPayment() {
		return countWaitPayment;
	}
	public void setCountWaitPayment(Integer countWaitPayment) {
		this.countWaitPayment = countWaitPayment;
	}
	public Integer getCountWaitSignup() {
		return countWaitSignup;
	}
	public void setCountWaitSignup(Integer countWaitSignup) {
		this.countWaitSignup = countWaitSignup;
	}
	public Integer getCountCancel() {
		return countCancel;
	}
	public void setCountCancel(Integer countCancel) {
		this.countCancel = countCancel;
	}
	public Integer getCountWaitAssembly() {
		return countWaitAssembly;
	}
	public void setCountWaitAssembly(Integer countWaitAssembly) {
		this.countWaitAssembly = countWaitAssembly;
	}
	public Integer getCountWaitPick() {
		return countWaitPick;
	}
	public void setCountWaitPick(Integer countWaitPick) {
		this.countWaitPick = countWaitPick;
	}
	public Integer getCountWaitSend() {
		return countWaitSend;
	}
	public void setCountWaitSend(Integer countWaitSend) {
		this.countWaitSend = countWaitSend;
	}
	public Integer getCountSendOut() {
		return countSendOut;
	}
	public void setCountSendOut(Integer countSendOut) {
		this.countSendOut = countSendOut;
	}
	public Integer getCountNormalPerformance() {
		return countNormalPerformance;
	}
	public void setCountNormalPerformance(Integer countNormalPerformance) {
		this.countNormalPerformance = countNormalPerformance;
	}
	public Integer getCountPrematureTerminating() {
		return countPrematureTerminating;
	}
	public void setCountPrematureTerminating(Integer countPrematureTerminating) {
		this.countPrematureTerminating = countPrematureTerminating;
	}
	public Integer getCountPrematureTermination() {
		return countPrematureTermination;
	}
	public void setCountPrematureTermination(Integer countPrematureTermination) {
		this.countPrematureTermination = countPrematureTermination;
	}
	public Integer getCountChange() {
		return countChange;
	}
	public void setCountChange(Integer countChange) {
		this.countChange = countChange;
	}
	public Integer getCountRepair() {
		return countRepair;
	}
	public void setCountRepair(Integer countRepair) {
		this.countRepair = countRepair;
	}
	public Integer getCountOverdue() {
		return countOverdue;
	}
	public void setCountOverdue(Integer countOverdue) {
		this.countOverdue = countOverdue;
	}
	public Integer getCountRecycling() {
		return countRecycling;
	}
	public void setCountRecycling(Integer countRecycling) {
		this.countRecycling = countRecycling;
	}
	public Integer getCountEarlyBuyout() {
		return countEarlyBuyout;
	}
	public void setCountEarlyBuyout(Integer countEarlyBuyout) {
		this.countEarlyBuyout = countEarlyBuyout;
	}
	public Integer getCountEarlyRecycle() {
		return countEarlyRecycle;
	}
	public void setCountEarlyRecycle(Integer countEarlyRecycle) {
		this.countEarlyRecycle = countEarlyRecycle;
	}
	public Integer getCountNormalBuyout() {
		return countNormalBuyout;
	}
	public void setCountNormalBuyout(Integer countNormalBuyout) {
		this.countNormalBuyout = countNormalBuyout;
	}
	public Integer getCountRecycle() {
		return countRecycle;
	}
	public void setCountRecycle(Integer countRecycle) {
		this.countRecycle = countRecycle;
	}
	
    
}