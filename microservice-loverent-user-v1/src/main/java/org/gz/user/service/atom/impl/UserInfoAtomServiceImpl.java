package org.gz.user.service.atom.impl;

import java.util.Date;

import org.gz.common.utils.BeanConvertUtil;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.LoginLog;
import org.gz.user.entity.UserInfo;
import org.gz.user.mapper.AddressInfoMapper;
import org.gz.user.mapper.AppUserMapper;
import org.gz.user.mapper.LoginLogMapper;
import org.gz.user.mapper.UserInfoMapper;
import org.gz.user.service.atom.UserInfoAtomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoAtomServiceImpl implements UserInfoAtomService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Autowired
	private AddressInfoMapper addressInfoMapper;
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	
	@Override
	public UserInfoDto queryByUserId(Long userId) {
		AppUser appUser = appUserMapper.selectByPrimaryKey(userId);
		if (appUser == null) {
			return null;
		}

		//convert appuser properties
		UserInfoDto dto = BeanConvertUtil.convertBean(appUser, UserInfoDto.class);
		
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		if (userInfo != null) {
			BeanUtils.copyProperties(userInfo, dto);
		}
		
		AddressInfo addressInfo = addressInfoMapper.queryAddressByUserId(userId);
		if (addressInfo != null) {
			dto.setAddrProvince(addressInfo.getAddrProvince());
			dto.setAddrCity(addressInfo.getAddrCity());
			dto.setAddrDetail(addressInfo.getAddrDetail());
		}
		
		LoginLog log = loginLogMapper.selectNewlyRecordByUserId(userId);
		if (log != null) {
			dto.setDeviceId(log.getDeviceId());
			dto.setDeviceType(log.getDeviceType());
			dto.setOsType(log.getOsType());
			dto.setChannelType(log.getChannelType());
			dto.setAppVersion(log.getAppVersion());
		}
		
		return dto;
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		Long userId = userInfo.getUserId();
		
		UserInfo record = userInfoMapper.selectByUserId(userId);
		if (record != null) {
			//update
			userInfo.setUpdateTime(new Date());
			userInfoMapper.updateByUserIdSelective(userInfo);
		} else {
			//add
			userInfo.setCreateTime(new Date());
			userInfoMapper.insertSelective(userInfo);
		}
		
	}

}
