package org.gz.user.hystrix;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.entity.CardInfo;
import org.gz.user.service.CardInfoService;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * hystrix impl
 * 
 * @author yangdx
 *
 */
@Component
public class CardInfoServiceHystrixImpl implements CardInfoService {

	@Override
	public ResponseResult<String> addCard(CardInfo cardInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateCard(CardInfo cardInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<List<CardInfo>> loadCard(Long userId) {
		ResponseResult<List<CardInfo>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<CardInfo> selectByCardNo(JSONObject body) {
		ResponseResult<CardInfo> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateCardByUserId(CardInfo cardInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> addCardIfNotExist(CardInfo cardInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
