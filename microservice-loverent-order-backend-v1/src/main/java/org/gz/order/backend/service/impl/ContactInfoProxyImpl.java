package org.gz.order.backend.service.impl;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.order.backend.service.ContactInfoProxy;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.service.ContactInfoService;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoProxyImpl implements ContactInfoProxy {

    @Resource
    private ContactInfoService contactInfoService;

    @Override
    public ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(ContactInfoQueryDto queryDto) {
        return contactInfoService.queryContactInfoByPage(queryDto);
    }

}
