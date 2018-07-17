/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.liquidation.common;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Page implements Serializable{
		
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 起始位置
     */
    @NotNull(message="start起始页不能为空")
    private Integer start;
    
    /**
     * 每页显示条数
     */
    @NotNull(message="pageSize页大小不能为空")
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
