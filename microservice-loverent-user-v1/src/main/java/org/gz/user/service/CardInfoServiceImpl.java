package org.gz.user.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.entity.CardInfo;
import org.gz.user.exception.UserServiceException;
import org.gz.user.service.atom.CardInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@Slf4j
public class CardInfoServiceImpl implements CardInfoService {
	
	@Autowired
	private CardInfoAtomService cardInfoAtomService;
	
	@Override
	public ResponseResult<String> addCard(@RequestBody CardInfo cardInfo) {
		log.info("start execute addCard");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			cardInfo.setCardCode("");
			cardInfo.setCardIssuing("");
			cardInfo.setReservedPhoneNum("");
			cardInfo.setNoAgree("");
			cardInfo.setCardStatus(1);
			cardInfo.setCreateTime(new Date());
			cardInfoAtomService.addCard(cardInfo);
		} catch (UserServiceException e) {
			 log.error("addCard failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("addCard failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> updateCard(@RequestBody CardInfo cardInfo) {
		log.info("start execute updateCard");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			cardInfoAtomService.updateCard(cardInfo);
		} catch (UserServiceException e) {
			 log.error("updateCard failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("updateCard failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<List<CardInfo>> loadCard(@RequestParam("userId") Long userId) {
		log.info("start execute loadCard");
		ResponseResult<List<CardInfo>> result = new ResponseResult<>();
		try {
			List<CardInfo> cardInfos = cardInfoAtomService.loadCard(userId);
			result.setData(cardInfos);
		} catch (UserServiceException e) {
			 log.error("loadCard failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("loadCard failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<CardInfo> selectByCardNo(@RequestBody JSONObject body) {
		log.info("start execute selectByCardNo, params: {}", body.toJSONString());
		ResponseResult<CardInfo> result = new ResponseResult<>();
		try {
			CardInfo cardInfo = cardInfoAtomService.selectByCardNo(body);
			result.setData(cardInfo);
		} catch (UserServiceException e) {
			 log.error("loadCard failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("loadCard failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> updateCardByUserId(@RequestBody CardInfo cardInfo) {
		log.info("start execute updateCardByUserId");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			cardInfoAtomService.updateCardByUserId(cardInfo);
		} catch (UserServiceException e) {
			 log.error("updateCardByUserId failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("updateCardByUserId failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> addCardIfNotExist(@RequestBody CardInfo cardInfo) {
		log.info("start execute addCardIfNotExist");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			cardInfoAtomService.addCardIfNotExist(cardInfo);
		} catch (UserServiceException e) {
			 log.error("addCardIfNotExist failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("addCardIfNotExist failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

}
