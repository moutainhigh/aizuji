/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.liquidation.common;

import java.io.Serializable;

public class OrderBy implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * 排序字段名
	 */
	private String cloumnName;
	
	/**
	 * 顺序
	 */
	private String order;

	/**
	 * @return the cloumnName
	 */
	public String getCloumnName() {
		return cloumnName;
	}

	/**
	 * @param cloumnName the cloumnName to set
	 */
	public void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
}
