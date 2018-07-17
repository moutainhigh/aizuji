package org.gz.aliOrder.Enum;


public enum FrontALiState
{
 ApplyPending(0, "申请中", "您的租赁订单还未完成提交，请尽快确认下单哦"),
 WaitPayment(3, "待发货", "您的租赁订单已申请，请在48小时内完成租金支付，订单超时将被取消 "),
 Cancel(5, "已取消", "您的租赁订单已成功取消，你心仪的商品很抢手哦，下次别再错过~"),
 WaitSend(6, "待发货", "您的租赁合约已生效，商品将以顺丰快递发出，请留意短信通知"),
 SendOut(7, "已发货", "您的商品已通过顺丰快递发出，签收前请记得验货哦，愿爱租机给您的生活带来更美妙体验~"),
 BuyOut(13, "已完成", "您已买断该商品，拥有商品归属权，欢迎继续使用爱租机预约新机哦"),
 Renting(8, "租赁中", "商品租用到期后买断或归还，冻结预授权金额将会释放"),
 OrderClose(21, "交易关闭", "");

  private int code;
  private String message;
  private String cueWords;

  FrontALiState(int code, String message, String cueWords){
    this.code = code;
    this.message = message;
    this.cueWords = cueWords;
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

}
