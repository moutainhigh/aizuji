package org.gz.risk.auditing.service;

import org.gz.risk.auditing.entity.User;

/**
 * @author CaoBang
 */
public interface IThirdPartyDataService {

    void addViolation(String phase,String loanRecordId, User loanUser);
    
    /**
     * 
     * @Description: 自动分单 
     * @throws
     */
    void autoCreditCompensation();
}
