package org.gz.overdue.job;

import javax.annotation.Resource;

import org.gz.overdue.service.overdueOrder.OverdueOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PullDataJob {
	
	@Resource
    private OverdueOrderService overdueOrderService;

	@Scheduled(cron="5 0 0 * * ?")
	public void execute(){
		log.info("syncOrderData execute is begin.");
		boolean flag = overdueOrderService.syncOrderData();
		log.info("syncOrderData execute is begin.flag:{}",flag);
	}
}
