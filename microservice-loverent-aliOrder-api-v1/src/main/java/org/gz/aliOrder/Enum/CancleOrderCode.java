package org.gz.aliOrder.Enum;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

/**
 * @Description:取消订单返类型枚举类
 * @author pengk 2018年3月28日
 */
public enum CancleOrderCode
{
 AGAIN_ORDER(1, "想要重新下单"),
 HIGH_PRICE(2, "商品价格较贵"),
 LONG_WAITTIME(3, "等待时间较长"),
 WANT_KNOW(4, "是想了解流程"),
 NOT_WANT(5, "不想要了");

  /**
   * 编码
   */
  private int code;

  /**
   * 消息
   */
  private String message;

  private CancleOrderCode(int code, String message){
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public String getMessage(Object... args) {
    return String.format(message, args);
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
