package org.gz.risk.constant;

/**
 * @author CaoBang
 */
public enum ViolationType {

    HONOR_BLACKLlST(101, "百荣黑名单", "SpecialList_c"),
    HONOR_CELLPHONE(102, "百荣手机三要数", "TelCheck"),
    HONOR_ID_CARD(103, "百荣身份二要数", "IdTwo_z"),
    HONOR_BANK_FOUR(104, "百荣银行四要数", "BankFour"),
    HONOR_LOAN_EQUIPMENT(105, "百荣借款设备", "LoanEquipment"),
    HONOR_REGISTER_EQUIPMENT(106, "百荣注册设备", "RegisterEquipment"),
    HONOR_SIGN_EQUIPMENT(107, "百荣登录设备", "SignEquipment"),
    AFU_BLACKLIST(202, "阿福黑名单", "queryBlackList"),
    AFU_QUERY_LOAN(201, "阿福借款情况", "queryLoan"),
    AFU_QUERY_CREDIT_SCORE(203, "阿福致诚分", "queryCreditScore"),
    AFU_QUERIEDHISTORY(204, "被查询情况", "getQueriedHistory"),
    XUE_XIN(301, "学信数据", "XUEXIN"),
    OPEN(302, "运营商数据", "OPEN"),
    TONGDUN_PRELOAN(401, "同盾贷前", "preloan");

    ViolationType(int code, String text, String meal) {
        this.code = code;
        this.text = text;
        this.meal = meal;
    }

    private int code;

    private String text;

    private String meal;

    public static ViolationType getViolationType(int code) {
        ViolationType[] violationTypes = ViolationType.values();
        for (ViolationType violationType : violationTypes) {
            if (violationType.getCode() == code) {
                return violationType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}
