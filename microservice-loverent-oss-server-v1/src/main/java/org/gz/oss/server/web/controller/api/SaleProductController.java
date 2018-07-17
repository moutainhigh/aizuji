/**
 * 
 */
package org.gz.oss.server.web.controller.api;

import java.util.List;

import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.dto.SaleProductCountReq;
import org.gz.oss.common.dto.SaleProductCountResp;
import org.gz.oss.common.service.SaleProductExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title:售卖产品查询控制器
 * @author hxj
 * @date 2018年4月2日 上午10:56:34
 */
@RestController
public class SaleProductController extends BaseController {

	@Autowired
	private Validator validator;
	
	@Autowired
	private SaleProductExternalService saleProductExternalService;
	
	@PostMapping("/api/querySaleProductCount")
	public ResponseResult<List<SaleProductCountResp>> querySaleProductCountList(@RequestBody SaleProductCountReq req) {
		ResponseResult<String> vr = super.getValidatedResult(validator, req,Default.class);
		if(vr==null) {
			return ResponseResult.buildSuccessResponse(this.saleProductExternalService.querySaleProductCountList(req));
		}
		return ResponseResult.build(1000, vr.getErrMsg(), null);
	}
}
