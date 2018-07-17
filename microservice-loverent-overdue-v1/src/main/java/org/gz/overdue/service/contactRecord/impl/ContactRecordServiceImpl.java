package org.gz.overdue.service.contactRecord.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.overdue.entity.ContactRecord;
import org.gz.overdue.entity.ContactRecordPage;
import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.mapper.ContactRecordMapper;
import org.gz.overdue.mapper.OverdueOrderMapper;
import org.gz.overdue.service.contactRecord.ContactRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactRecordServiceImpl implements ContactRecordService {

	@Resource
	private ContactRecordMapper contactRecordMapper;
	@Resource
	private OverdueOrderMapper overdueOrderMapper;

	@Override
	@Transactional
	public void addContactRecord(ContactRecord contactRecord) {
		contactRecordMapper.add(contactRecord);
		//修改订单联系结果相关字段
		OverdueOrder query = new OverdueOrder();
		query.setOrderSN(contactRecord.getOrderSN());
		List<OverdueOrder> list = overdueOrderMapper.query(query);
		if(list!=null&&list.size()>0){
			OverdueOrder overdueOrder = new OverdueOrder();
			overdueOrder.setId(list.get(0).getId());
			overdueOrder.setContactTime(new Date());
			overdueOrder.setContactResult(contactRecord.getContactResult());
			overdueOrder.setOrderStatus(1);
			overdueOrderMapper.update(overdueOrder);
		}
	}

	@Override
	public void deleteSettle(String id) {
		contactRecordMapper.deleteById(id);
	}

	@Override
	public void updateContactRecord(ContactRecord contactRecord) {
		contactRecordMapper.update(contactRecord);
	}

	@Override
	public Integer queryCount(ContactRecordPage contactRecordPage) {
		return contactRecordMapper.queryCount(contactRecordPage);
	}

	@Override
	public List<ContactRecord> queryList(ContactRecordPage contactRecordPage) {
		return contactRecordMapper.queryList(contactRecordPage);
	}
	
}
