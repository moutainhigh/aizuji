package org.gz.user.service.atom.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.entity.ContactInfo;
import org.gz.user.mapper.ContactInfoMapper;
import org.gz.user.service.atom.ContactInfoAtomService;
import org.gz.user.supports.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoAtomServiceImpl implements ContactInfoAtomService {

	@Autowired
	private ContactInfoMapper contactInfoMapper;
	
	@Override
	public ContactInfoQueryDto queryContactInfoByPage(
			ContactInfoQueryDto queryDto) {
		Pagination<ContactInfo> pagination = new Pagination<>();
		pagination.setPage(queryDto.getCurrPage());
		pagination.setLimit(queryDto.getPageSize());
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", queryDto.getUserId());
		pagination.setParams(condition);
		List<ContactInfo> contactInfos = contactInfoMapper.queryContactInfoByPage(pagination);
        queryDto.setTotalNum(pagination.getTotal());
		queryDto.setData(contactInfos);
		return queryDto;
	}

	
}
