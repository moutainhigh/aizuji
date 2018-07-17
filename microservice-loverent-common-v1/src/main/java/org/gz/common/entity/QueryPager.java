package org.gz.common.entity;

/**
 * 分页查询类,带分页查询的实体可继承此类
 * @author hxj
 *
 */
public class QueryPager extends BaseEntity {

	private static final long serialVersionUID = 66536703880324492L;

	/**
	 * 当前页，默认为1
	 */
	protected int currPage = 1;

	/**
	 * 每页大小，默认为10
	 */
	protected int pageSize = 10;

	protected int startIndex;

	protected int endIndex;

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

	public int getStartIndex() {
		return (currPage - 1) * pageSize;
	}

	public int getEndIndex() {
		return pageSize;
	}
}
