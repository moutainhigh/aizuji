package org.gz.oss.common.dto;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

/**
 * <Change to the actual description of this class>
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class UpdateDto<T> {
	/**
     * 业务日志标识
     */
    private String  logSign;
	
    /**
     * 更新数据,此处设置的对象与mapper xml文件中的属性要一致
     */
    private T updateCloumn;
    
    /**
     * 更新条件,此处设置的对象与mapper xml文件中的属性要一致
     */
    private T updateWhere;

    
    /**
     * Returns this updateCloumn object.
     * @return this updateCloumn
     */
    public T getUpdateCloumn() {
        return updateCloumn;
    }

    
    /**
     * Sets this updateCloumn.
     * @param updateCloumn this updateCloumn to set
     */
    public void setUpdateCloumn(T updateCloumn) {
        this.updateCloumn = updateCloumn;
    }

    
    /**
     * Returns this updateWhere object.
     * @return this updateWhere
     */
    public T getUpdateWhere() {
        return updateWhere;
    }

    
    /**
     * Sets this updateWhere.
     * @param updateWhere this updateWhere to set
     */
    public void setUpdateWhere(T updateWhere) {
        this.updateWhere = updateWhere;
    }


	public String getLogSign() {
		return logSign;
	}


	public void setLogSign(String logSign) {
		this.logSign = logSign;
	}
}
