package org.gz.aliOrder.Enum;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

/**
 * @Description:订单返回编码消息枚举类
 * @author pengk 2017年7月10日
 */
public enum OrderResultCode
{
 /**
  * 请求成功
  */
 DEAL_SUCCESS(0, "操作成功."),
 /**
  * 请求参数错误
  */
 REQ_PARA_ERROR(1, "%s"),
 /**
  * 服务异常
  */
 SERVICE_EXCEPTION(2, "系统异常,请稍候再试或联系客服人员处理,谢谢!"),

 RENT_APPROVAL_PENDING(10001, "很抱歉，您有正在租赁中的订单，暂时无法提交新的订单."),

 /**
  * 订单申请的时候 没有生成订单编号
  */
 FAILD_CREATE_RECORDID(10002, "订单编号生成失败."),

 /**
  * 没有查询到对应的订单信息
  */
 NOT_RENT_RECORD(10003, "没有查询到对应的订单信息."),

 NOT_RENTEXTENDS_RECORD(10004, "没有查询到对应的订单扩展表信息."),

 NOT_PRODUCT(10005, "没有查询到对应的产品信息."),
 PRODUCT_DELETE(10006, "产品已下架."),

 NO_STOCK(10007, "备货中"),
 RENT_AGREEMENT_SAVE_ERROR(10008, "合同保存错误."),
 SIGN_END(10009, "已经签约完成."),
 STATE_FALSE(10010, "状态流转有错."),
 PAY_ING(10011, "当前订单存在交易处理中的付款."),
 PAY_WAIT(10012, "很抱歉，您有待支付的订单，暂时无法提交新的订单"),
 PRODUCT_NOTSUITABLE(10006, "很抱歉，当前申请的产品不能售卖."),

 LOAN_NOT_FOUNT(13002, "提交的申请单号不存在."),
 NOT_LOAN_CONFIG(13003, "没有找到贷款配置."),

 NOT_ONE_TERM(13020, "不是一期还清."),

 NOT_SIGN_AWAIT(12002, "还未到签约流程."),

 UNAUTHORIZED_ACCESS(20001, "未授权访问"),
 FLOW_STATUS_ERROR(20002, "流程状态错误");

  /**
   * 编码
   */
  private int code;

  /**
   * 消息
   */
  private String message;

  private OrderResultCode(int code, String message){
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
