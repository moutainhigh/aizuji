/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.bean;

import java.io.Serializable;

/**
 * 
 * <Change to the actual description of this class>
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class Page implements Serializable{
		
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 起始位置
     */
    private Integer start;
    
    /**
     * 每页显示条数
     */
    private Integer pageSize;
    
    /**
     * 总数
     */
    private Integer totalNum;

    
    /**
     * Returns this start object.
     * @return this start
     */
    public Integer getStart() {
        return start;
    }

    
    /**
     * Sets this start.
     * @param start this start to set
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    
    /**
     * Returns this pageSize object.
     * @return this pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    
    /**
     * Sets this pageSize.
     * @param pageSize this pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    
    /**
     * Returns this totalNum object.
     * @return this totalNum
     */
    public Integer getTotalNum() {
        return totalNum;
    }

    
    /**
     * Sets this totalNum.
     * @param totalNum this totalNum to set
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
