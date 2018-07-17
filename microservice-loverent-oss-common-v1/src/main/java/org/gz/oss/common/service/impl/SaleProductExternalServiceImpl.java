/**
 * 
 */
package org.gz.oss.common.service.impl;

import java.util.List;

import org.gz.oss.common.dao.SaleProductExternalDao;
import org.gz.oss.common.dto.SaleProductCountReq;
import org.gz.oss.common.dto.SaleProductCountResp;
import org.gz.oss.common.service.SaleProductExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年4月2日 上午11:08:04
 */
@Service
public class SaleProductExternalServiceImpl implements SaleProductExternalService {

	@Autowired
	private SaleProductExternalDao saleProductExternalDao;

	@Override
	public List<SaleProductCountResp> querySaleProductCountList(SaleProductCountReq req) {
		return saleProductExternalDao.querySaleProductCountList(req);
	}

}
