package org.gz.warehouse.web.controller.api;

import java.util.List;

import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ConfirmSignVO;
import org.gz.warehouse.common.vo.MaterielBasicInfoReq;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.MaterielModelReq;
import org.gz.warehouse.common.vo.MaterielModelResp;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.UndoPickReq;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.gz.warehouse.service.materiel.MaterielModelService;
import org.gz.warehouse.service.warehouse.WarehouseOutService;
import org.gz.warehouse.service.warehouse.WarehouseRetrieveService;
import org.gz.warehouse.service.warehouse.WarehouseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * @Title: 处理订单业务的外部控制器
 * @author hxj
 * @Description: 
 * @date 2017年12月26日 下午4:37:54
 */
@RestController
@RequestMapping("")
@Slf4j
public class OrderController extends BaseController {

	@Autowired
	private Validator validator;
	
	@Autowired
	private WarehouseRetrieveService retrieveService;
	
	@Autowired
	private WarehouseOutService outService;
	
	@Autowired
	private MaterielBasicInfoService materielBasicInfoService;
	
	@Autowired
	private MaterielModelService modelService;
	
	@Autowired
	private WarehouseReturnService returnService;
	
	
	/**
	 * 
	* @Description: 根据物料ID获取新机库可售仓位中的数量
	* @param q 只传： materielBasicId：物料ID
	* @return ResponseResult<WarehouseMaterielCount>
	 */
	@PostMapping(value = "/api/order/queryWarehouseMaterielCount")
    public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(@RequestBody WarehouseMaterielCountQuery q) {
		 ResponseResult<WarehouseMaterielCount> result =  this.retrieveService.queryWarehouseMaterielCount(q);
		 if(result!=null&&result.getData()==null) {
			 WarehouseMaterielCount data= new WarehouseMaterielCount();
			 data.setMaterielBasicId(q.getMaterielBasicId().intValue());
			 data.setMaterielCount(0);
			 result.setData(data);
		 }
		 return result;
    }
	
	/**
	 * 
	* @Description: 签约接口
	* @param  q:只传sourceOrderNo：原始订单号  materielBasicId：物料ID
	* @return ResponseResult<SigningResult>
	 */
	@PostMapping(value = "/api/order/signing")
    public ResponseResult<SigningResult> signing(@RequestBody SigningQuery q) {
			ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
			if(vr==null) {
				try {
					return this.retrieveService.signing(q);
				} catch (ServiceException e) {
					return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), null);
				}catch (Exception e) {
					return ResponseResult.build(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage(), null);
				}
			}
			return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	/**
	 * 
	* @Description: 签约失败调用接口
	* @param  q:全传 
	* @return ResponseResult<SigningResult>
	 */
	@PostMapping(value = "/api/order/signFailed")
    public ResponseResult<SigningResult> signFailed(@RequestBody SigningQuery q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			if(!StringUtils.hasText(q.getImieNo())||!StringUtils.hasText(q.getSnNo())) {
				return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "IMIE号，SN号为必传参数", null);
			}
			try {
				return this.retrieveService.signFailed(q);
			} catch (ServiceException e) {
				return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), null);
			}catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getCode(), WarehouseErrorCode.EX_SIGN_FAILED_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	/**
	 * 
	* @Description: 撤销拣货接口
	* @param  q:全传 sourceOrderNo：销售单号，materielBasicId：物料ID，productId：产品ID，orderSource：订单来源 1：APP,2:小程序
	* @return ResponseResult<UndoPickReq> errCode==0 表示撤销拣货成功，否则表示失败
	 */
	@PostMapping(value = "/api/order/undoPick")
    public ResponseResult<UndoPickReq> undoPick(@RequestBody UndoPickReq q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			try {
				return this.outService.undoPick(q);
			} catch (ServiceException e) {
				return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), null);
			}catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.UNDO_PICK_EXCEPTION_ERROR.getCode(), WarehouseErrorCode.UNDO_PICK_EXCEPTION_ERROR.getMessage(), q);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), q);
    }
	

	/**
	 * 
	* @Description: 查询配货状态
	* @param  q ：必传sourceOrderNo-订单号
	 */
	@PostMapping(value = "/api/order/pickQuery")
    public ResponseResult<WarehousePickResult> pickQuery(@RequestBody WarehousePickQuery q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			try {
				return  this.outService.pickQuery(q);
			} catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.EX_PICK_QUERY_ERROR.getCode(), WarehouseErrorCode.EX_PICK_QUERY_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	/**
	 * 
	* @Description: 确认收货
	* @param  q ：必传sourceOrderNo-订单号
	 */
	@PostMapping(value = "/api/order/confirmSign")
    public ResponseResult<ConfirmSignVO> confirmSign(@RequestBody ConfirmSignVO q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			try {
				return  this.outService.confirmSign(q);
			} catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	/**
	 * 
	* @Description: 买断接口
	* @param  q
	 */
	@PostMapping(value = "/api/order/buyEnd")
    public ResponseResult<BuyEndVO> buyEnd(@RequestBody BuyEndVO q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			try {
				return  this.outService.buyEnd(q);
			} catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getCode(), WarehouseErrorCode.EX_CONFIRM_SIGN_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	
	/**
	 * 
	* @Description: 在租接口
	* @param  q
	 */
	@PostMapping(value = "/api/order/renting")
    public ResponseResult<RentingReq> renting(@RequestBody RentingReq q) {
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			try {
				return  this.outService.renting(q);
			} catch (Exception e) {
				return ResponseResult.build(WarehouseErrorCode.EX_RENTING_ERROR.getCode(), WarehouseErrorCode.EX_RENTING_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
    }
	
	
	/**
	 * 
	* @Description: 获取物料详情
	* @param id
	* @return ResponseResult<MaterielBasicInfo>
	 */
	@GetMapping(value="/api/order/getMaterielDetail/{id}")
	public ResponseResult<MaterielBasicInfo> getDetail(@PathVariable("id")Long id){
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.materielBasicInfoService.getDetail(id);
			} catch (Exception e) {
				log.error("获取物料详情失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 
	* @Description: 查询物料型号列表
	* @param  q
	* @return ResponseResult<List<MaterielModelResp>>
	 */
	@PostMapping(value="/api/order/queryModelList")
	public ResponseResult<List<MaterielModelResp>> queryModelList(@RequestBody MaterielModelReq q){
		ResponseResult<String> vr = super.getValidatedResult(validator, q, Default.class);
		if(vr==null) {
			return this.modelService.queryModelList(q.getIds());
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
	}
	
	/**
	 * 
	* @Description: 根据物料型号ID查询主物料信息
	* @param  req 必传materielModelId:物料型号ID
	* @return ResponseResult<MaterielBasicInfoResp>
	 */
	@PostMapping(value="/api/order/queryMainMaterielInfo")
	public ResponseResult<MaterielBasicInfoResp> queryMainMaterielInfo(@RequestBody MaterielBasicInfoReq req){
		MaterielBasicInfoResp result = null;
		if(req!=null&&AssertUtils.isPositiveNumber4Int(req.getMaterielModelId())) {
			req.setMaterielFlag(1);//设为主物料标志
			result= this.materielBasicInfoService.queryMainMaterielBasicInfo(req);
			return ResponseResult.buildSuccessResponse(result);
		}
		return ResponseResult.build(1000, "必须传递materielModelId参数", null);
	}
	
	/**
	 * 
	* @Description: 根据物料型号ID查询主物料图文介绍
	* @param  req 必传materielModelId:物料型号ID
	* @return ResponseResult<MaterielBasicInfoResp>
	 */
	@PostMapping(value="/api/order/queryMainMaterielBasicIntroductionInfo")
	public ResponseResult<MaterielBasicInfoResp> queryMainMaterielBasicIntroductionInfo(@RequestBody MaterielBasicInfoReq req){
		MaterielBasicInfoResp result = null;
		if(req!=null&&AssertUtils.isPositiveNumber4Int(req.getMaterielModelId())) {
			req.setMaterielFlag(1);//设为主物料标志
			result= this.materielBasicInfoService.queryMainMaterielBasicIntroductionInfo(req);
			return ResponseResult.buildSuccessResponse(result);
		}
		return ResponseResult.build(1000, "必须传递materielModelId参数", null);
	}

	/**
	 * 
	* @Description: 申请还机
	* @param  req 
	* @return ResponseResult<ApplyReturnReq>
	 */
	@PostMapping(value="/api/order/applyReturn")
	public ResponseResult<ApplyReturnReq> applyReturn(@RequestBody ApplyReturnReq req){
		ResponseResult<String> vr = super.getValidatedResult(validator, req, Default.class);
		if(vr==null) {
			try {
				return this.returnService.applyReturn(req);
			} catch (Exception e) {
				log.error("申请还机失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(1000, "申请还机错误", null);
			}
		}
		return ResponseResult.build(1000, "传参错误", null);
	}
}
