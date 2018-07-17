package org.gz.thirdParty.bean.shunFeng;

import java.util.List;

public class OrderDownload {
	private head head;
	private body body;
	
	public head getHead() {
		return head;
	}

	public void setHead(head head) {
		this.head = head;
	}

	public body getBody() {
		return body;
	}

	public void setBody(body body) {
		this.body = body;
	}

	public static class head {
		private String transType;
		private String transMessageId;
		private String code;
		private String messsage;
		public String getTransType() {
			return transType;
		}
		public void setTransType(String transType) {
			this.transType = transType;
		}
		public String getTransMessageId() {
			return transMessageId;
		}
		public void setTransMessageId(String transMessageId) {
			this.transMessageId = transMessageId;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMesssage() {
			return messsage;
		}
		public void setMesssage(String messsage) {
			this.messsage = messsage;
		}
	}
	
	public static class body {
		private List<String> images;
		private String pageWidth;
		private String pageHeight;
		private String orientation;
		private String type;
		private String resultMessage;
		private String imagePath;
		
		public List<String> getImages() {
			return images;
		}
		public void setImages(List<String> images) {
			this.images = images;
		}
		public String getPageWidth() {
			return pageWidth;
		}
		public void setPageWidth(String pageWidth) {
			this.pageWidth = pageWidth;
		}
		public String getPageHeight() {
			return pageHeight;
		}
		public void setPageHeight(String pageHeight) {
			this.pageHeight = pageHeight;
		}
		public String getOrientation() {
			return orientation;
		}
		public void setOrientation(String orientation) {
			this.orientation = orientation;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getResultMessage() {
			return resultMessage;
		}
		public void setResultMessage(String resultMessage) {
			this.resultMessage = resultMessage;
		}
		public String getImagePath() {
			return imagePath;
		}
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
	}
}
