package org.gz.order.common.Enum;

/**
 * 租赁记录排序
 * @author phd
 */
public enum RentRecordSort {
	
    APPLY_SORT(1, "申请时间", "applyTime", "desc"),

    APPROVAL_SORT(2,"审核成功时间","approvalTime","desc"),

    PAY_SORT(3,"支付成功时间","payTime","desc"),
    
    SIGN_SORT(4,"签约成功时间","signTime","desc"),
	
    TERMINATE_APPLY_SORT(5,"申请解约时间","terminateApplyTime","asc");
	
    private Integer id;     

    /**
     * 描述
     */
    private String  desc;

    /**
     * 排序别名
     */
    private String  sortAlias;

    /**
     * 排序类型 asc 或者 desc
     */
    private String  orderByType;

    public static RentRecordSort getSortEnumById(Integer id) {
        if (id != null) {
            for (RentRecordSort sortEnum : RentRecordSort.values()) {
                if (sortEnum.getId().equals(id)) {
                    return sortEnum;
                }
            }
        }
        return APPLY_SORT;
    }

    private RentRecordSort(Integer id, String desc, String sortAlias, String orderByType){
        this.id = id;
        this.desc = desc;
        this.sortAlias = sortAlias;
        this.orderByType = orderByType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSortAlias() {
        return sortAlias;
    }

    public void setSortAlias(String sortAlias) {
        this.sortAlias = sortAlias;
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

}