package org.gz.order.backend.service;

import org.gz.common.resp.ResponseResult;
import org.gz.user.dto.ContactInfoQueryDto;

/**
 * 联系人
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月29日 	Created
 */
public interface ContactInfoProxy {
    /**
     * 查询通讯录列表
     * @param queryDto
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月29日
     */
    ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(ContactInfoQueryDto queryDto);
}
