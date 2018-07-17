package org.gz.order.common.Enum;


public enum FrontSaleState
{

 WaitPayment(3, "待支付", "建议您尽快支付订单，因商品很抢手，如未支付我们将在 hh-mm-ss 后自动取消订单"),
 Cancel(5, "已取消", "商品数量有限，且很抢手，目前价格最优，早买早用哦"),
 WaitSend(6, "待发货", "您的订单已支付成功，商品将以顺丰快递发出，请留意短信通知"),
 SendOut(7, "已发货", "您的商品已由顺丰发出，请留意签收。愿爱租机给您的生活带来更美妙体验~"),
 BuyOut(13, "已完成", "您的订单已确认收货，如您有任何问题，可在我的--帮助中心-进行咨询，届时有专人为您解答。");

  private int code;
  private String message;
  private String cueWords;

  FrontSaleState(int code, String message, String cueWords){
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
