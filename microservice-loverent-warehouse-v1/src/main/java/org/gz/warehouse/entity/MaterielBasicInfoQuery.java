package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 物料基础信息表
 * 
 * @author hxj
 *
 */
public class MaterielBasicInfoQuery extends QueryPager {

	private static final long serialVersionUID = -3543263888875148096L;

	protected Long id;// 主键ID

	protected String queryContent;//按物料名称，物料编码模糊查询
	
	protected String materielName;// 物料名称

	protected String materielCode;// 物料编码

	protected Integer materielClassId;// 物料分类

	protected Integer materielBrandId;// 物料品牌

	protected Integer materielModelId;// 品牌对应型号

	protected String specBatchNo;// 规格批次号

	protected Integer materielUnitId;// 物料单位ID

	protected Integer enableFlag;// 启用/停用标志 0:禁用 1:启用
	
	protected Integer materielFlag;//主物料标志

	protected Integer materielNewConfigId;//新旧程序配置ID
	
	protected String materielBarCode;// 物料条形码
	
	public MaterielBasicInfoQuery() {

	}

	public MaterielBasicInfoQuery(MaterielBasicInfo info) {
		this.id = info.getId();
		this.materielName = info.getMaterielName();
		this.materielClassId=info.getMaterielClassId();
		this.materielBrandId=info.getMaterielBrandId();
		this.materielModelId=info.getMaterielModelId();
		this.specBatchNo=info.getSpecBatchNo();
		this.materielFlag=info.getMaterielFlag();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	public Integer getMaterielBrandId() {
		return materielBrandId;
	}

	public void setMaterielBrandId(Integer materielBrandId) {
		this.materielBrandId = materielBrandId;
	}

	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Integer getMaterielFlag() {
		return materielFlag;
	}

	public void setMaterielFlag(Integer materielFlag) {
		this.materielFlag = materielFlag;
	}

	public Integer getMaterielNewConfigId() {
		return materielNewConfigId;
	}

	public void setMaterielNewConfigId(Integer materielNewConfigId) {
		this.materielNewConfigId = materielNewConfigId;
	}

	public String getMaterielBarCode() {
		return materielBarCode;
	}

	public void setMaterielBarCode(String materielBarCode) {
		this.materielBarCode = materielBarCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
