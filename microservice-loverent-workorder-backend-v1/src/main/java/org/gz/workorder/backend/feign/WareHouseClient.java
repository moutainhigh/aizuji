package org.gz.workorder.backend.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.vo.ConfirmSignVO;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.gz.workorder.backend.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE", configuration = FeignFullConfig.class, fallback = IMaterielServiceFallBack.class)
public interface WareHouseClient {

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

}

@Component
class IMaterielServiceFallBack implements WareHouseClient{

    @Override
    public ResponseResult<?> queryAllMaterielClassList() {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
    }

	@Override
	public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(WarehouseMaterielCountQuery q) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}


	@Override
	public ResponseResult<WarehousePickResult> pickQuery(WarehousePickQuery q) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}


	@Override
	public ResponseResult<ConfirmSignVO> confirmSign(ConfirmSignVO q) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}
}
