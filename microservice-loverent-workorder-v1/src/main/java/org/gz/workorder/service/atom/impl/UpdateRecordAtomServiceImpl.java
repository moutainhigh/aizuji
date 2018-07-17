package org.gz.workorder.service.atom.impl;


import org.gz.workorder.dao.UpdateRecordDao;
import org.gz.workorder.entity.UpdateRecord;
import org.gz.workorder.service.atom.UpdateRecordAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateRecordAtomServiceImpl implements UpdateRecordAtomService {

	@Autowired
	private UpdateRecordDao updateRecordDao;
	
	@Override
	public void add(UpdateRecord record) {
		updateRecordDao.add(record);
	}

}
