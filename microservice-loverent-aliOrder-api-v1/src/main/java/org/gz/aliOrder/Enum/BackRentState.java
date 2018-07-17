package org.gz.aliOrder.Enum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台租赁状态
 * @author phd
 */
public enum BackRentState {
	    
 ApplyPending(0, "申请中"), // 0,"申请中"
 ApprovalPending(1, "待审批"), // 1,"审批中",
 Refuse(2, "已拒绝"), // 2,"审批不通过"
 WaitPayment(3, "待支付"), // 3,"待支付
 WaitSignup(4, "待签约"), // 4,"待签约"
 Cancel(5, "已取消"), // 5,"订单已取消",
 WaitAssembly(6, "待配货"), // 4,"待签约"
 WaitPick(7, "待拣货"), // 6,"待发货",,
 WaitSend(8, "待发货"), // 6,"待发货",,
 SendOut(9, "发货中"), // 7, "已发货"
 NormalPerformance(10, "正常履约"), // 8, "租赁中"
 PrematureTerminating(11, "提前解约中"),
 PrematureTermination(12, "提前解约"),
 Change(13, "换机订单状态"),
 Repair(14, "维修订单状态"),
 Overdue(15, "已逾期"), // 10, "已逾期",
 Recycling(16, "归还中"), // 11,"归还中"
 EarlyBuyout(17, "提前买断"), // 13,"已买断"
 EarlyRecycle(18, "提前归还"), // 12,"已归还"
 NormalBuyout(19, "正常买断"), // 13,"已买断"
 Recycle(20, "已归还"), // 12,"已归还"
 EarlyBuyIng(21, "提前买断中"), // =====废弃
 NormalBuyIng(22, "正常买断中"), // ===废弃
 EarlyRecycing(23, "提前归还中"), // 11,"归还中"
 EndPerformance(24, "履约完成"), // 9, "履约完成"
 ForceBuyIng(25, "强制买断中"), // 17, "待买断"
 ForceBuyout(26, "强制买断"), // 13,"已买断"
 DamageCheck(27, "定损完成"), // 20, "定损完成"
 ForcePerformanceIng(28, "强制履约中"), // 19, "待结清"
 ForcePerformanceEnd(29, "强制履约完成"), // 16, "租约到期"
 ForceRecycleIng(30, "强制归还中"), // 18, "待归还"
 ForceDamageCheck(31, " 强制定损完成"), // 20, "定损完成"
 ForceRecycleEnd(32, " 强制归还完成"), // 12,"已归还"
 ReturnGoodIng(33, " 退货中"),
 ReturnGoodEnd(34, " 已退货"),
 OrderClose(35, " 交易关闭");

    private int code;
    private String message;
    
    BackRentState(int code, String message) {
        this.code = code;
        this.message = message;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static FrontRentState getByCode(Integer code) {
        if (code != null) {
            for (FrontRentState objEnum : FrontRentState.values()) {
                if (objEnum.getCode()==code.intValue()) {
                    return objEnum;
                }
            }
        }
        return null;
    }

  public static BackRentState getBackByCode(Integer code) {
    if (code != null) {
      for (BackRentState objEnum : BackRentState.values()) {
        if (objEnum.getCode() == code.intValue()) {
          return objEnum;
        }
      }
    }
    return null;
  }
	
	public static List<Map<String, String>> all(){
		List<Map<String, String>> rlist=new ArrayList<>();
    	Map<String, String> rmap = null;
    	for(BackRentState uite : values()){
    		rmap=new HashMap<String, String>();
    		rmap.put("code", uite.getCode()+"");
    		rmap.put("message", uite.getMessage());
    		rlist.add(rmap);
    	}
    	return rlist;
    }
	    
}
