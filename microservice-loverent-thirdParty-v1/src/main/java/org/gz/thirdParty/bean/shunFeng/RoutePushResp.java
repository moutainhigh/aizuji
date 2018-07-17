package org.gz.thirdParty.bean.shunFeng;

public class RoutePushResp {

	private headMessageResp head;
	private bodyMessageResp body;

	public headMessageResp getHead() {
		return head;
	}

	public void setHead(headMessageResp head) {
		this.head = head;
	}

	public bodyMessageResp getBody() {
		return body;
	}

	public void setBody(bodyMessageResp body) {
		this.body = body;
	}

	public static class headMessageResp {
		private int transType;
		private String transMessageId;
		private String code;
		private String message;

		public int getTransType() {
			return transType;
		}

		public void setTransType(int transType) {
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

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	public static class bodyMessageResp {
		private String id;
		private String idError;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIdError() {
			return idError;
		}

		public void setIdError(String idError) {
			this.idError = idError;
		}
	}
}
