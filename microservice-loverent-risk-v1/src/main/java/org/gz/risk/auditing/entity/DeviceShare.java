package org.gz.risk.auditing.entity;

/**
 * Created by dell on 2017/1/4.
 */
public class DeviceShare implements java.io.Serializable{

    private long historyId;
    private long userId;
    private long id2;
    private String customerName2;
    private long id3;
    private String customerName3;

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }

    public String getCustomerName2() {
        return customerName2;
    }

    public void setCustomerName2(String customerName2) {
        this.customerName2 = customerName2;
    }

    public long getId3() {
        return id3;
    }

    public void setId3(long id3) {
        this.id3 = id3;
    }

    public String getCustomerName3() {
        return customerName3;
    }

    public void setCustomerName3(String customerName3) {
        this.customerName3 = customerName3;
    }
}
