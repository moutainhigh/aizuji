package org.gz.user.dto;

import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.user.entity.ContactInfo;

public class ContactInfoQueryDto extends QueryPager{
	
	private static final long serialVersionUID = 1L;
	
	/**用户ID*/
	private Long userId;

	/**总记录数*/
    private Long              totalNum;
	
	private List<ContactInfo> data;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<ContactInfo> getData() {
		return data;
	}

	public void setData(List<ContactInfo> data) {
		this.data = data;
	}

    public Long getTotalNum() {
        return totalNum;
	}

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
	}

}
