/**
 * 
 */
package org.gz.common.constants;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: ZTree简单树节点
 * @author hxj
 * @Description:
 * @date 2017年12月21日 下午7:03:27
 */
public class ZTreeSimpleNode extends BaseEntity {

	private static final long serialVersionUID = -2151720093176831060L;

	private Integer id;

	private Integer pId;

	private String name;

	private String open = "true";

	private String isParent = "true";

	private List<ZTreeSimpleNode> children = new ArrayList<ZTreeSimpleNode>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public List<ZTreeSimpleNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeSimpleNode> children) {
		this.children = children;
	}
}
