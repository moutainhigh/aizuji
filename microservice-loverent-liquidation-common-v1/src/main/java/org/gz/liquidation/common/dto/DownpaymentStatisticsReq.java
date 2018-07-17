package org.gz.liquidation.common.dto;

import java.util.Date;
import java.util.List;
/**
 * 
 * @Description:TODO	首期款统计请求
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月4日 	Created
 */
public class DownpaymentStatisticsReq {
	private Date dateStart;
	private Date dateEnd;
	private List<String> orderSnList; 
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public List<String> getOrderSnList() {
		return orderSnList;
	}
	public void setOrderSnList(List<String> orderSnList) {
		this.orderSnList = orderSnList;
	}
	
}
