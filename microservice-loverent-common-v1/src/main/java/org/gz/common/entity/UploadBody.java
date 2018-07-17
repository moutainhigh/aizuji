package org.gz.common.entity;

import java.io.Serializable;
/**
 * 文件上传对象
 */
public class UploadBody implements Serializable {


	private static final long serialVersionUID = 5549893924341143881L;

	/**
	 * 必传字段，文件流
	 */
	private byte[] file;
	/**
	 * 可选字段，文件名称，如果传了代表上传到指定路径，优先级最高
	 */
	private String fileName;
	/**
	 * 分区路径名称，文件名称，如果传了代表随机生成文件名称
	 */
	private String bucketName;
	/**
	 * 可选字段，返回路径类型，和bucketName组合使用，如果为1代表返回图片完成路径，其他代表返回文件名称
	 */
	private Integer returnPathType;
	/**
	 * 可选字段，文件类型，和bucketName组合使用，例如jpg
	 */
	private String fileType;

	public String getFileName() {
		return fileName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public Integer getReturnPathType() {
		return returnPathType;
	}
	public void setReturnPathType(Integer returnPathType) {
		this.returnPathType = returnPathType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
}
