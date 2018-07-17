package org.gz.app.vo;

/**
 * 芝麻订单详情
 * 
 * @author yangdx
 *
 */
public class ZhimaOrderDetailVo {

	/**姓名*/
	private String name;
	
	/**证件号*/
	private String certNo;
	
	/**手机号*/
	private String mobile;
	
	/**住宅地址，如：西湖区文三路168号*/
	private String house;
	
	/**较差、中等、良好、优秀、极好5个级别*/
	private String zmGrade;

	/**本次订单信用权益金额（如免押、极速付） 1000.00*/
	private String creditAmout;
	
	/**支付宝userid*/
	private String userId;
	
	/**渠道来源  rent*/
	private String channelId;
	
	/**芝麻分*/
	private String zmScore;
	
	/**人脸核身结果 Y:通过 */
	private String zmFace;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getZmGrade() {
		return zmGrade;
	}

	public void setZmGrade(String zmGrade) {
		this.zmGrade = zmGrade;
	}

	public String getCreditAmout() {
		return creditAmout;
	}

	public void setCreditAmout(String creditAmout) {
		this.creditAmout = creditAmout;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getZmScore() {
		return zmScore;
	}

	public void setZmScore(String zmScore) {
		this.zmScore = zmScore;
	}

	public String getZmFace() {
		return zmFace;
	}

	public void setZmFace(String zmFace) {
		this.zmFace = zmFace;
	}
	
}
