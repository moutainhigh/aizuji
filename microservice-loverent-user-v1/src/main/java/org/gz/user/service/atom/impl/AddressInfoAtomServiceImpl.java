package org.gz.user.service.atom.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.gz.user.entity.AddressInfo;
import org.gz.user.mapper.AddressInfoMapper;
import org.gz.user.service.atom.AddressInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AddressInfoAtomServiceImpl implements AddressInfoAtomService {

	@Autowired
	private AddressInfoMapper addressInfoMapper;
	
	@Override
	public AddressInfo queryAddressByUserId(Long userId) {
		return addressInfoMapper.queryAddressByUserId(userId);
	}

	@Override
	public AddressInfo queryAddressByPrimaryKey(Long addrId) {
		return addressInfoMapper.selectByPrimaryKey(addrId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateAddressByPrimaryKey(AddressInfo addressInfo) {
		addressInfoMapper.updateByPrimaryKeySelective(addressInfo);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateAddressByUserId(AddressInfo addressInfo) {
		Long userId = addressInfo.getUserId();
		AddressInfo dbRecord = addressInfoMapper.queryAddressByUserId(userId);
		if (dbRecord == null) {
			addressInfo.setCreateTime(new Date());
			addressInfo.setAddrArea("");
			addressInfo.setAddrStatus(1);
			
			addressInfoMapper.insertSelective(addressInfo);
		} else {
			dbRecord.setAddrProvince(addressInfo.getAddrProvince());
			dbRecord.setAddrCity(addressInfo.getAddrCity());
			dbRecord.setAddrDetail(addressInfo.getAddrDetail());
			dbRecord.setUpdateTime(new Date());
			
			addressInfoMapper.updateByPrimaryKeySelective(dbRecord);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void removeAddressByPrimaryKey(Long addrId) {
		addressInfoMapper.removeAddressByPrimaryKey(addrId);
	}

	@Override
	public void removeAddressByUserId(Long userId) {
		addressInfoMapper.removeAddressByUserId(userId);
	}

	@Override
	public void insertAddress(AddressInfo addressInfo) {
		addressInfoMapper.insertSelective(addressInfo);
	}
}
