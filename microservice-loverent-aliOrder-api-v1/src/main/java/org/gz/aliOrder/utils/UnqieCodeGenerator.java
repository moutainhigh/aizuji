/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.aliOrder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.gz.common.utils.JsonUtils;

/**
 * @Description:根据业务生成唯一订单号 
 * Author Version Date Changes 
 * zhuangjianfa 1.0 2017年7月13日 Created
 */
public class UnqieCodeGenerator {

    /**
     * @Description: 生成订单号(格式：业务编码+年的后两位+月份两位+日期两位+订单自增ID(不足10位，左边补0))
     * @param fixed,业务编码，如现在的订单用S
     * @param id 先新增再把id查出来
     * @return
     * @throws createBy:zhuangjianfa createDate:2017年7月13日
     */
    public static String getCode(String fixed, Long id) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String curDate = df.format(new Date());
        String code = String.format("%s%s%s", fixed, curDate, getIDToString(id, 10));
        return code;
    }

  /**
   * @Description:yyyyMMddHHmmssSSS 加上13位自增数字
   * @param id 先新增再把id查出来
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  public static String getTransctionId(Long id) {
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String curDate = df.format(new Date());
    String code = String.format("%s%s", curDate, getIDToString(id, 13));
    return code;
  }

    public static String getIDToString(Long id, int len) {
        String ret = getZOString(len);
        if (null != id) {
            String idStr = id.toString();
            int idLen = idStr.length();
            if (idLen < len) {
                ret = getZOString(len - idLen) + idStr;
            } else {
                ret = idStr;
            }
        }
        return ret;
    }

    public static String getZOString(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            ret += "0";
        }
        return ret;
    }

    public static void main(String args[]) {
        System.out.println("Value:" + getIDToString(49l, 10));
        System.out.println("Value:" + getCode("so", 8L));
    System.out.println("Value:" + getTransctionId(8L));

    String materielSpecName = "";
    String[] mStrings = materielSpecName.split(",");
    StringBuffer sBuffer = null;
    Map<String, Object> map = new HashMap<>();
    for (int i = 0; i < mStrings.length; i++) {
      sBuffer = new StringBuffer();
      sBuffer.append("materielSpecName");
      sBuffer.append(i + 1);
      map.put(sBuffer.toString(), mStrings[i]);
    }
    System.out.println(JsonUtils.toJsonString(map));
    }
}
