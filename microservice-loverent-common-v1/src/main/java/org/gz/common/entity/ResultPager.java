package org.gz.common.entity;

import java.util.List;

/**
 * 分页结果类
 * @author hxj
 * @param <T>
 *
 */
public class ResultPager<T> extends BaseEntity {

	private static final long serialVersionUID = 8358782764010739217L;

	protected int totalNum;

	protected int currPage;

	protected int pageSize;

	protected List<T> data;
	
	protected int totalPages=0;//总页数

	public ResultPager() {
		
	}
	
	public ResultPager(int totalNum,int currPage,int pageSize,List<T> data) {
		this.totalNum=totalNum;
		this.currPage=currPage;
		this.pageSize=pageSize;
		if(pageSize>0&&totalNum>0) {
			this.totalPages=(totalNum/pageSize)+(totalNum%pageSize>0?1:0);
		}
		this.data=data;
	}
	
	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
