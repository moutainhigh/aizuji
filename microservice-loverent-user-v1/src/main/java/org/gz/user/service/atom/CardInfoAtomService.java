package org.gz.user.service.atom;

import java.util.List;

import org.gz.user.entity.CardInfo;

import com.alibaba.fastjson.JSONObject;

public interface CardInfoAtomService {

	void addCard(CardInfo cardInfo);

	List<CardInfo> loadCard(Long userId);

	void updateCard(CardInfo cardInfo);

	CardInfo selectByCardNo(JSONObject body);

	void updateCardByUserId(CardInfo cardInfo);

	void addCardIfNotExist(CardInfo cardInfo);

}
