/**
 * 
 */
package org.gz.warehouse.web.controller.purchase;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderVO;
import org.gz.warehouse.service.purchase.PurchaseApprovedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 采购申请管理控制器
 * @author hxj
 * @date 2017年12月18日 上午11:30:46
 */
@RestController
@RequestMapping("/purchaseApproved")
@Api(value = "/")
@Slf4j
public class PurchaseApprovedManageController extends BaseController{
	
	@Autowired
	private PurchaseApprovedService purchaseApprovedService;
	
	
	/**
	 * 获取分页列表
	 * @param m
	 * @return
	 */
	@PostMapping(value = "/queryByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(@RequestBody PurchaseApplyOrderQuery q){
		if(q!=null) {
			try {
				return this.purchaseApprovedService.queryByPage(q);
			} catch (Exception e) {
				log.error("获取已提交采购申请分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取详情
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws
	 */
	@GetMapping(value = "/getDetail/{id}")
	public ResponseResult<?> getDetail(@PathVariable Long id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.purchaseApprovedService.queryDetail(id);
			} catch (Exception e) {
				log.error("获取已提交采购申请详情失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 
	* @Description: 采购申请单审批
	* @param p
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/approved", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> approved(@Valid @RequestBody PurchaseApprovedOrderVO p, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				p.setApprovedId(1L);
				p.setApprovedName("管理员");
				return this.purchaseApprovedService.approved(p);
			} catch (Exception e) {
				log.error("采购申请单审批失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 
	* @Description: 采购申请单审批
	* @param p
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> reject(@Valid @RequestBody PurchaseApprovedOrderVO p, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				p.setApprovedId(1L);
				p.setApprovedName("管理员");
				return this.purchaseApprovedService.reject(p);
			} catch (Exception e) {
				log.error("采购申请单审批失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
}
