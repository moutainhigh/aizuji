/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.bean;

import java.util.List;

import org.gz.common.OrderBy;

/**
 * 
 * 查询对象，用于动态排序与分页的传参
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class QueryDto{
	
	private Object queryConditions;
	
	private Page page;
	
	private List<OrderBy> orderBy;

	/**
	 * @return the queryConditions
	 */
	public Object getQueryConditions() {
		return queryConditions;
	}
	
    /**
     * Sets this queryConditions.
     * @param queryConditions this queryConditions to set
     */
    public void setQueryConditions(Object queryConditions) {
        this.queryConditions = queryConditions;
    }

    /**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the orderBy
	 */
	public List<OrderBy> getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(List<OrderBy> orderBy) {
		this.orderBy = orderBy;
	}
	
}
