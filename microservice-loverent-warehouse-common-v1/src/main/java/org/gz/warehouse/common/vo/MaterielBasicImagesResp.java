package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * 物料基础信息表
 * 
 * @author hxj
 *
 */
public class MaterielBasicImagesResp extends BaseEntity {

	private static final long serialVersionUID = -4377482345197219471L;

	private Long id;// 主键

	private Long materielBasicInfoId;// 物料主键ID

	private String attaUrl;// 附件地址

	public MaterielBasicImagesResp() {
		
	}
	
	public MaterielBasicImagesResp(String attaUrl) {
		this(null,attaUrl);
	}
	
	public MaterielBasicImagesResp(Long materielBasicInfoId,String attaUrl) {
		this.materielBasicInfoId = materielBasicInfoId;
		this.attaUrl=attaUrl;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterielBasicInfoId() {
		return materielBasicInfoId;
	}

	public void setMaterielBasicInfoId(Long materielBasicInfoId) {
		this.materielBasicInfoId = materielBasicInfoId;
	}

	public String getAttaUrl() {
		return attaUrl;
	}

	public void setAttaUrl(String attaUrl) {
		this.attaUrl = attaUrl;
	}

}
