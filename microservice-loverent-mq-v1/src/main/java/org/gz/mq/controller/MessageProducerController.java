package org.gz.mq.controller;

import org.gz.mq.entity.resp.ResponseResult;
import org.gz.mq.service.ProduceMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author hxj
 * @date 2017年11月9日上午11:24:11
 */
@RestController
@RequestMapping(value = "/producer")
public class MessageProducerController {

	private static Logger logger = LoggerFactory.getLogger(MessageProducerController.class);

	@Autowired
	private ProduceMessageService produceMessageService;

	@PostMapping(value = "/sendSingleMsg")
	public ResponseResult sendSingleMsg(String topic, String message) {
		try {
			this.produceMessageService.sendMessage(topic, message);
			return ResponseResult.OPT_SUCCESS;
		} catch (Exception e) {
			logger.error("sendCreditAuditRuleLog failed:" + e.getLocalizedMessage());
			return ResponseResult.OPT_INVALID_PARAM;
		}
	}

}
