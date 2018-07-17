package org.gz.user.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.service.atom.ContactInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
	
	@Autowired
	private ContactInfoAtomService contactInfoAtomService;

	@Override
	public ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(
			@RequestBody ContactInfoQueryDto queryDto) {
		log.info("start execute queryContactInfoByPage...");
		
		ResponseResult<ContactInfoQueryDto> result = new ResponseResult<>();
		
		ContactInfoQueryDto dto = contactInfoAtomService.queryContactInfoByPage(queryDto);
		
		result.setData(dto);
		
		return result;
	}

}
