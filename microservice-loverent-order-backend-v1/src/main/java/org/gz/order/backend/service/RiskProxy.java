package org.gz.order.backend.service;

import org.gz.common.resp.ResponseResult;
import org.gz.risk.common.request.RiskReq;

public interface RiskProxy {

    ResponseResult<String> processAutoCredit(RiskReq riskReq);
}
