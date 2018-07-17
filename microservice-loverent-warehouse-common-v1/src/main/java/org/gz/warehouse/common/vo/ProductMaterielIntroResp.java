/**
 * 
 */
package org.gz.warehouse.common.vo;

import java.util.List;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:产品物料介绍响应对象
 * @author hxj
 * @date 2018年4月3日 下午4:01:21
 */
public class ProductMaterielIntroResp extends BaseEntity {

	private static final long serialVersionUID = -4790238242053392678L;

	private String materielIntroduction;

	private List<String> attaUrls;

	public ProductMaterielIntroResp() {

	}

	public ProductMaterielIntroResp(String materielIntroduction,List<String> attaUrls) {
		this.materielIntroduction=materielIntroduction;
		this.attaUrls=attaUrls;
	}

	public String getMaterielIntroduction() {
		return materielIntroduction;
	}

	public void setMaterielIntroduction(String materielIntroduction) {
		this.materielIntroduction = materielIntroduction;
	}

	public List<String> getAttaUrls() {
		return attaUrls;
	}

	public void setAttaUrls(List<String> attaUrls) {
		this.attaUrls = attaUrls;
	}

}
