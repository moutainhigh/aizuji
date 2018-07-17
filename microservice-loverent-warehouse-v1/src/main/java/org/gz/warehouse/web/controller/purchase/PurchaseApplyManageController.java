/**
 * 
 */
package org.gz.warehouse.web.controller.purchase;

import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.service.purchase.PurchaseApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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
@RequestMapping("/purchaseApply")
@Api(value = "/")
@Slf4j
public class PurchaseApplyManageController extends BaseController{
	
	@Autowired
	private PurchaseApplyService purchaseApplyService;
	
	/**
	 * 
	* @Description: 新增采购申请单
	* @param p
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody PurchaseApplyOrder p, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				if(StringUtils.hasText(p.getExpectedReceiveDate_s())) {
					p.setExpectedReceiveDate(new SimpleDateFormat("yyyy-MM-dd").parse(p.getExpectedReceiveDate_s()));
				}
				p.setCreateBy(1L);
				p.setApplyName("管理员");
				return this.purchaseApplyService.insert(p);
			} catch (Exception e) {
				log.error("新增采购申请单失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 
	* @Description: 更新采购申请单
	* @param p
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> update(@Valid @RequestBody PurchaseApplyOrder p, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				if(!AssertUtils.isPositiveNumber4Long(p.getId())) {
					return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "更新采购单时，必须传采购申请单ID", null);
				}
				if(StringUtils.hasText(p.getExpectedReceiveDate_s())) {
					p.setExpectedReceiveDate(new SimpleDateFormat("yyyy-MM-dd").parse(p.getExpectedReceiveDate_s()));
				}
				p.setCreateBy(1L);
				p.setApplyName("管理员");
				return this.purchaseApplyService.update(p);
			} catch (Exception e) {
				log.error("修改采购申请单失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 获取分页列表
	 * @param m
	 * @return
	 */
	@PostMapping(value = "/queryByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(@RequestBody PurchaseApplyOrderQuery q){
		if(q!=null) {
			try {
				return this.purchaseApplyService.queryByPage(q);
			} catch (Exception e) {
				log.error("获取采购申请分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	
	@GetMapping(value = "/getDetail/{id}")
	public ResponseResult<?> getDetail(@PathVariable Long id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.purchaseApplyService.queryDetail(id);
			} catch (Exception e) {
				log.error("获取采购申请详情失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 将采购申请订单状态修改为提交状态
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws
	 */
	@GetMapping(value = "/updateToSubmitFlag/{id}")
	public ResponseResult<?> updateToSubmitFlag(@PathVariable Long id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.purchaseApplyService.updateToSubmitFlag(id);
			} catch (Exception e) {
				log.error("更新采购申请提交状态失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
}
