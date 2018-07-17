package org.gz.risk.auditing.entity;

/**
 * @author CaoBang
 */
public class UserHistoryWrapper extends UserEntity {
    private String afSwiftNumber;

    private Long history_id;

    public String getAfSwiftNumber() {
        return afSwiftNumber;
    }

    public void setAfSwiftNumber(String afSwiftNumber) {
        this.afSwiftNumber = afSwiftNumber;
    }

    public Long getHistory_id() {
        return history_id;
    }

    public void setHistory_id(Long history_id) {
        this.history_id = history_id;
    }
}
