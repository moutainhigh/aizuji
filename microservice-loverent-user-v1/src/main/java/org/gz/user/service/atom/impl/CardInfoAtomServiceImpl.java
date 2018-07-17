package org.gz.user.service.atom.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.utils.StringUtils;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.CardInfo;
import org.gz.user.mapper.AppUserMapper;
import org.gz.user.mapper.CardInfoMapper;
import org.gz.user.service.atom.CardInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Service
@Slf4j
public class CardInfoAtomServiceImpl implements CardInfoAtomService {

	@Autowired
	private CardInfoMapper cardInfoMapper;

	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addCard(CardInfo cardInfo) {
		cardInfoMapper.insert(cardInfo);
	}

	@Override
	public List<CardInfo> loadCard(Long userId) {
		return cardInfoMapper.selectByUserId(userId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateCard(CardInfo cardInfo) {
		cardInfoMapper.updateByPrimaryKeySelective(cardInfo);
	}

	@Override
	public CardInfo selectByCardNo(JSONObject body) {
		Long userId = body.getLong("userId");
		String cardNo = body.getString("cardNo");
		return cardInfoMapper.selectByCardNo(userId, cardNo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateCardByUserId(CardInfo cardInfo) {
		CardInfo dbRecord = cardInfoMapper.selectByCardNo(cardInfo.getUserId(), cardInfo.getCardNo());
		if (dbRecord != null) {
			cardInfo.setCardStatus(1);
			cardInfoMapper.updateByUserId(cardInfo);
			
			try {
				//同步身份证、账户姓名至用户表
				AppUser appUser = appUserMapper.selectByPrimaryKey(cardInfo.getUserId());
				if (appUser != null) {
					boolean canUpdate = false; 
					if (StringUtils.isEmpty(appUser.getIdNo())) {
						canUpdate = true;
					}
					if (StringUtils.isEmpty(appUser.getRealName())) {
						canUpdate = true;
					}
					
					if (canUpdate) {
						AppUser modifyUser = new AppUser();
						modifyUser.setUserId(dbRecord.getUserId());
						modifyUser.setIdNo(dbRecord.getIdNo());	//身份证
						modifyUser.setRealName(dbRecord.getAccountName());	//户名
						
						appUserMapper.updateByPrimaryKeySelective(modifyUser);
					}
				}
			} catch (Exception e) {
				log.error("--->updateCardByUserId, update idNo, accountName to app_user failed, e: {}", e);
			}
		}
	}

	@Override
	public void addCardIfNotExist(CardInfo cardInfo) {
		List<CardInfo> dbRecords = cardInfoMapper.selectByCondition(cardInfo);
		if (dbRecords == null || dbRecords.isEmpty()) {
			cardInfoMapper.insertSelective(cardInfo);
		}
	}
	
}
