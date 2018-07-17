package org.gz.risk.auditing.entity;

/**
 * 规则映射 
 * @author phd
 * @date 2017年11月9日
 * @version 1.0
 */
public enum RuleCode {
	Score("s1", "score"),
    CheckIsExistsRecord("a1", "CheckIsExistsRecord"),
    QQ_ID_PHONE_NO("a2", "QQ_ID_PHONE_NO"),
    BlackCompanyKilled("a3", "BlackCompanyKilled"),
    RepeatLoan("a4", "RepeatLoan"),
    KeyWord("a6", "KeyWord"),
    ViolateRate("a7", "ViolateRate"),
    ViolateOverdue("a8", "ViolateOverdue"),
    IpCheck("a9", "IpCheck"),
    CompanyPhoneAndName("a10", "CompanyPhoneAndName"),
    ContactPhoneAndName("a11", "ContactPhoneAndName"),
    AddressAndPhone("a12", "AddressAndPhone"),
    CheckLastApply("a13", "CheckLastApply"),
    CurrentAddCheck("a14", "CurrentAddCheck"),
    CompanyCellNumCheck("a15", "CompanyCellNumCheck"),
    RelationApplyCheck("a16", "RelationApplyCheck"),
    SpouseCheck("a17", "SpouseCheck"),
    ApplyRelationCheck("a18", "ApplyRelationCheck"),
    SameCompanyCheck("a19", "SameCompanyCheck"),
    DifferentCompanyCheck("a20", "DifferentCompanyCheck"),
    BairongKilled("a21", "BairongKilled"),
    BairongAfublackKill("a22", "BairongAfublackKill"),
    BairongBaifuKill("a23", "BairongBaifuKill"),
    BairongImcellKill("a24", "BairongImcellKill"),
    callDetails1Kill("a25", "callDetails1Kill"),
    callDetails2Kill("a26", "callDetails2Kill"),
    callDetails3Kill("a27", "callDetails3Kill"),
    callDetails4Kill("a28", "callDetails4Kill"),
    callDetails5Kill("a29", "callDetails5Kill"),
    callDetails6Kill("a30", "callDetails6Kill"),
    deviceSharingRefuse("a31", "deviceSharingRefuse"),
    isCallInformation1Kill("a32", "isCallInformation1Kill"),
    isCallInformation2Kill("a33", "isCallInformation2Kill"),
    isCallSms1Kill("a34", "isCallSms1Kill"),
    isCallSms2Kill("a35", "isCallSms2Kill"),
    isApplyKill("a36", "isApplyKill"),
    isDeviceShareKill("a37", "isDeviceShareKill"),
    isIndustryKill("a38", "isIndustryKill"),
    isIdKill("a39", "isIdKill"),
    isChannelKill("a40", "isChannelKill"),
    isCoupleKill("a41", "isCoupleKill"),
    isFraudKill("a42", "isFraudKill"),
    isSameqqKill("a43", "isSameqqKill"),
    isRejectKill("a44", "isRejectKill"),
    isDueContactKill("a45", "isDueContactKill"),
    isEmergencKill("a46", "isEmergencKill"),
    isPasswordKill("a47", "isPasswordKill"),
    isOutlookKill("a48", "isOutlookKill"),
    isDenullKill("a49", "isDenullKill"),
    isXuewrongKill("a50", "isXuewrongKill"),
    isCertlengKill("a51", "isCertlengKill"),
    isIpKill("a52", "isIpKill"),
    isAbnormalInfoKill("a53", "isAbnormalInfoKill"),
    isCompanyCellNumkill("a54", "isCompanyCellNumkill"),
    isSameIpKill("a55", "isSameIpKill"),
    isAbnormalContactsKill("a56", "isAbnormalContactsKill"),
    isSameCurrentAddrKill("a57", "isSameCurrentAddrKill"),
    isDiffSpouseKill("a58", "isDiffSpouseKill"),
    isSameCompanyIncomeKill("a59", "isSameCompanyIncomeKill"),
    isSameContactsNameKill("a60", "isSameContactsNameKill"),
    isSameCompanyAddrKill("a61", "isSameCompanyAddrKill"),
    isSameContactsTelKill("a62", "isSameContactsTelKill"),
    isSameContactsIdKill("a63", "isSameContactsIdKill"),
    isSameCompanyPassword("a64", "isSameCompanyPassword"),
    isDiffMaterial("a65", "isDiffMaterial"),
    isContactsKill("a66", "isContactsKill"),
    isLoanContactsKill("a67", "isLoanContactsKill"),
    isBlackTelKill("a68", "isBlackTelKill"),
    isDiffMaterialKill2("a69", "isDiffMaterialKill2")
    ;

    RuleCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String value;
    private String code;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
   
}
