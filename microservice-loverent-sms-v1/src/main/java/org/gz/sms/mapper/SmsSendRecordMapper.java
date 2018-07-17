package org.gz.sms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.sms.entity.SmsSendRecord;

@Mapper
public interface SmsSendRecordMapper {
    int deleteByPrimaryKey(Long smsid);

    int insert(SmsSendRecord record);

    int insertSelective(SmsSendRecord record);

    SmsSendRecord selectByPrimaryKey(Long smsid);

    int updateByPrimaryKeySelective(SmsSendRecord record);

    int updateByPrimaryKey(SmsSendRecord record);
}