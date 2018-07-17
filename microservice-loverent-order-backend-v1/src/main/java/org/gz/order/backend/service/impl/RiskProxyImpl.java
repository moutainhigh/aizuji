package org.gz.order.backend.service.impl;

import org.gz.common.resp.ResponseResult;
import org.gz.order.backend.service.RiskProxy;
import org.gz.risk.api.controller.IRiskController;
import org.gz.risk.common.request.RiskReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskProxyImpl implements RiskProxy {

    @Autowired
    private IRiskController riskController;

    @Override
    public ResponseResult<String> processAutoCredit(RiskReq riskReq) {
        return riskController.processAutoCredit(riskReq);
    }

}
