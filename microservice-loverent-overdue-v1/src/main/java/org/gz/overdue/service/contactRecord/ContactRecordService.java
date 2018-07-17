package org.gz.overdue.service.contactRecord;


import java.util.List;

import org.gz.overdue.entity.ContactRecord;
import org.gz.overdue.entity.ContactRecordPage;

/**
 * 联系记录相关接口
 * @Description:TODO
 * Author	Version		Date		Changes
 * hening 	1.0  		2017年2月1日 	Created
 */
public interface ContactRecordService {

    /**
     * 添加记录
     * @param SettleOrderVo
     * @throws
     * createBy:hening            
     * createDate:2018年02月01日
     */
    void addContactRecord(ContactRecord contactRecord);

    /**
     * 删除记录
     * @param id
     */
    void deleteSettle(String id);
    
    /**
     * 根据ID修改记录
     * @param settleOrder
     */
    void updateContactRecord(ContactRecord contactRecord);
    
    /**
     * 根据条件查询总数
     * @param settleOrder
     * @return
     */
    Integer queryCount(ContactRecordPage contactRecord);
    
    /**
     * 根据条件查询记录(分页)
     * @param settleOrderPage
     * @return
     */
    List<ContactRecord> queryList(ContactRecordPage contactRecordPage);
}
