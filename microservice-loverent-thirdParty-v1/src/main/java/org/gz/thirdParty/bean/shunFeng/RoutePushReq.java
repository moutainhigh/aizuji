package org.gz.thirdParty.bean.shunFeng;

import java.util.List;

public class RoutePushReq {
	private ShunFengHead head;
	private List<RoutePushBodyReq> body;
	
	public ShunFengHead getHead() {
		return head;
	}
	
	public void setHead(ShunFengHead head) {
		this.head = head;
	}
	
	public List<RoutePushBodyReq> getBody() {
		return body;
	}
	
	public void setBody(List<RoutePushBodyReq> body) {
		this.body = body;
	}
	
}
