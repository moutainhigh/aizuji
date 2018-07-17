package org.gz.risk.intf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.gz.risk.auditing.entity.User;
import org.gz.risk.bean.Result;
import org.gz.risk.bean.ViolationReq;

/**
 * @Description:第三方服务违规数据接口
 * Author   Version     Date        Changes
 * zhangguoliang    1.0         2017年7月15日  Created
 */
public interface ViolationService {
    
    /**
     * @Description: 通过用户信息查询第三方违规数据
     * @param req
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    Result getViolationListByUser(User req)  throws UnsupportedEncodingException;
    
    
    Result update(ViolationReq req);
    
    Result queryListByUserId(ViolationReq req);
    
    Result selectByMeal(ViolationReq req);
    
    Result selectByMealNum(ViolationReq req);
    
    Result add(ViolationReq req);
    
    Result addAll(User user) throws IOException;
    
    Result selectVio(ViolationReq req);
}
