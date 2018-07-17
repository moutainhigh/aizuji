package org.gz.order.backend.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.order.backend.config.FeignFullConfig;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ConfirmSignVO;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE", configuration = FeignFullConfig.class, fallback = IMaterielServiceFallBack.class)
public interface IMaterielService {

	/**
	 * 获取物料分类
	 */
    @PostMapping(value = "/materielClass/api/queryAllMaterielClassList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryAllMaterielClassList();

    /**
     * 申请通过物料id查询是否有库存
     */
    @PostMapping(value = "/api/order/queryWarehouseMaterielCount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(@RequestBody WarehouseMaterielCountQuery q);
    
    /**
     * 
    * @Description: 查询配货状态
    * @param  q ：必传sourceOrderNo-订单号
     */
    @PostMapping(value = "/api/order/pickQuery", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
      public ResponseResult<WarehousePickResult> pickQuery(@RequestBody WarehousePickQuery q);
    
    /**
     * 
    * @Description: 确认收货
    * @param  q ：必传sourceOrderNo-订单号
     */
    @PostMapping(value = "/api/order/confirmSign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
      public ResponseResult<ConfirmSignVO> confirmSign(@RequestBody ConfirmSignVO q);
    
    /**
     * 
    * @Description: 确认签约接口 获取imsi和snCode
    * @param  q:只传sourceOrderNo：原始订单号  materielBasicId：物料ID
    * @return ResponseResult<WarehouseMaterielCount>
     */
    @PostMapping(value = "/api/order/signing", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<SigningResult> signing(@RequestBody SigningQuery q);

    /**
     * @Description: 签约失败调用接口
     * @param q:全传
     * @return ResponseResult<SigningResult>
     */
    @PostMapping(value = "/api/order/signFailed", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<SigningResult> signFailed(@RequestBody SigningQuery q);

    /**
     * 
    * @Description: 买断接口
    * @param  q
     */
    @PostMapping(value = "/api/order/buyEnd")
    public ResponseResult<BuyEndVO> buyEnd(@RequestBody BuyEndVO q);

    /**
     * @Description: 申请还机
     * @param req
     * @return ResponseResult<ApplyReturnReq>
     */
    @PostMapping(value = "/api/order/applyReturn")
    public ResponseResult<ApplyReturnReq> applyReturn(@RequestBody ApplyReturnReq req);
    
    /**
     * 
    * @Description: 在租接口
    * @param  q
     */
    @PostMapping(value = "/api/order/renting")
      public ResponseResult<RentingReq> renting(@RequestBody RentingReq q);

}

@Component
class IMaterielServiceFallBack implements IMaterielService{

    @Override
    public ResponseResult<?> queryAllMaterielClassList() {
        return defaultBkMethod();
    }

    
    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }


	@Override
	public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(WarehouseMaterielCountQuery q) {
		return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
	}


	@Override
	public ResponseResult<WarehousePickResult> pickQuery(WarehousePickQuery q) {
		return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
	}


	@Override
	public ResponseResult<ConfirmSignVO> confirmSign(ConfirmSignVO q) {
		return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
	}
	@Override
    public ResponseResult<SigningResult> signing(SigningQuery q) {
      return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<SigningResult> signFailed(SigningQuery q) {
      return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);

    }

    @Override
    public ResponseResult<BuyEndVO> buyEnd(BuyEndVO q) {
      return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<ApplyReturnReq> applyReturn(ApplyReturnReq req) {
      return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<RentingReq> renting(RentingReq q) {
      return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }
}
