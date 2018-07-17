/**
 * 
 */
package org.gz.oss.common.dto;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年4月2日 上午11:03:27
 */
public class SaleProductCountResp extends BaseEntity {

	private static final long serialVersionUID = 22118765911280109L;

	private String snNo;

	private String imeiNo;

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
}
