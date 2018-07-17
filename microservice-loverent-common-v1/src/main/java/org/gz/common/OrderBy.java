package org.gz.common;

import java.io.Serializable;

/**
 * 
 * @author phd
 */
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
	private String sequence;

	public String getCloumnName() {
		return cloumnName;
	}

	public void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
}
