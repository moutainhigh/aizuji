package org.gz.order.common.Enum;

/**
 * 前端租赁状态
 * @author phd
 */
public enum FrontRentState {
	        
 ApplyPending(0, "申请中", "您的租赁订单还未完成提交，请尽快确认下单哦"),
 ApprovalPending(1, "审批中", "您的租赁订单已预约成功，可能会有审核人员与您电话联系，请保持电话畅通，耐心等待。"),
 Refuse(2, "审批不通过", "很抱歉，您申请的租赁订单审批未通过，过几天再来试试吧~"),
 WaitPayment(3, "待支付", "您的租赁订单已审批通过，请尽快完成支付！"),
 WaitSignup(4, "待签约", "支付成功，请及时完成签约操作，订单将在签约成功后1-3个工作日发货。"),
 Cancel(5, "订单已取消", "您的租赁订单已成功取消，你心仪的商品很抢手哦，下次别再错过~"),
 WaitSend(6, "待发货", "您的租赁订单已支付成功，商品将以顺丰快递发出，请留意短信通知"),
 SendOut(7, "已发货", "您的商品已通过顺丰快递发出，签收前请记得验货哦，愿爱租机给您的生活带来更美妙体验~"),
 Renting(8, "租赁中", "您心仪的宝贝已经到手，尽情享受新机带给你的乐趣吧，愿爱租机给您的生活带来更美妙体验~。"),
 EndPerformance(9, "履约完成", "您的租赁订单已结清，租约将于%s到期,到期后请及时归还手机。如果您对宝贝依依不舍，您可以买断手机归属权。"),
 Overdue(10, "已逾期", "您租赁订单已逾期，为了不影响您的正常使用，请按时缴纳租金。"),
 Returning(11, "归还中", "您的租赁订单已申请归还，爱租机收到货后会有专业人员对手机进行测验，请留意确认结果通知哦~"),
 Return(12, "已归还", "您申请归还的商品已确认验收，欢迎继续使用爱租机预约新机哦~"),
 BuyOut(13, "已买断", "您已买断该商品，拥有商品归属权，无需归还。欢迎继续使用爱租机预约新机哦~"),
 // RentException(14, "租赁异常（强制归还/买断/交租）",
 // "您的租赁订单未正常履约，请在规定时间内按照人工客服指示进行租约处理，违约相关法律责任请查看租赁服务协议。"),
 BuyIng(15, "待支付（买断中，2小时内有效）", "您已申请买断，买断后将拥有产品归属权，无需归还，请立即支付，若支付超时，买断需要重新申请。"),
 Expires(16, "租约到期", "您的租赁订单已结清，租约期满，请立即处理归还手机。如果您对宝贝依依不舍，您可以买断手机归属权，或选择续租。"),
 BuyOutNotice(17, "待买断", "您的租赁订单未正常履约，为了不影响您的信用记录，请在规定时间内按照人工客服指示进行买断支付，违约相关法律责任请查看租赁服务协议。"),
 ReturnNotice(18, "待归还", "。"),
 WAITSETTLE(19, "待结清", "您的租赁订单未正常履约，为了不影响您的信用记录，请在规定时间内进行结清支付，违约相关法律责任请查看租赁服务协议。"),
 DamageCheck(20, "定损完成", "。");

  private int code;
    private String message;
    private String cueWords;
    
    FrontRentState(int code, String message,String cueWords) {
        this.code = code;
        this.message = message;
        this.cueWords=cueWords;
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
	
	public String getCueWords() {
		return cueWords;
	}
	public void setCueWords(String cueWords) {
		this.cueWords = cueWords;
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
		
}
