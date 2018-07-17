package org.gz.aliOrder.service.outside;

import org.gz.aliOrder.config.FeignFullConfig;

/**
 * 
 */

import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.UndoPickReq;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE", configuration = FeignFullConfig.class, fallbackFactory = IProductServiceFallFactory.class)

public interface IProductService {


    @PostMapping(value = "/api/product/getByIdOrPdtNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ProductInfo> getByIdOrPdtNo(@RequestBody ProductInfo pInfo);

  /**
   * 申请通过物料id查询是否有库存
   * 
   * @param q
   * @return
   * @throws createBy:临时工 createDate:2017年12月27日
   */
  @PostMapping(value = "/api/order/queryWarehouseMaterielCount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(@RequestBody WarehouseMaterielCountQuery q);

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
  
  /**
   * 
  * @Description: 撤销拣货接口
  * @param  q:全传 sourceOrderNo：销售单号，materielBasicId：物料ID，productId：产品ID，orderSource：订单来源 1：APP,2:小程序
  * @return ResponseResult<UndoPickReq> errCode==0 表示撤销拣货成功，否则表示失败
   */
  @PostMapping(value = "/api/order/undoPick")
    public ResponseResult<UndoPickReq> undoPick(@RequestBody UndoPickReq q);
}


@Component
@Slf4j
class IProductServiceFallFactory implements FallbackFactory<IProductService>
 {
  @Override
  public IProductService create(Throwable cause) {
    return new IProductService() {
      
      @Override
      public ResponseResult<ProductInfo> getByIdOrPdtNo(ProductInfo pInfo) {
        log.error("microservice-loverent-warehouse 系统异常getByIdOrPdtNo，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(WarehouseMaterielCountQuery q) {
        log.error("microservice-loverent-warehouse 系统异常queryWarehouseMaterielCount，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
      }

      @Override
      public ResponseResult<SigningResult> signing(SigningQuery q) {
        log.error("microservice-loverent-warehouse 系统异常signing，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
      }

      @Override
      public ResponseResult<SigningResult> signFailed(SigningQuery q) {
        log.error("microservice-loverent-warehouse 系统异常signFailed，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);

      }

      @Override
      public ResponseResult<BuyEndVO> buyEnd(BuyEndVO q) {
        log.error("microservice-loverent-warehouse 系统异常buyEnd，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
      }

      @Override
      public ResponseResult<ApplyReturnReq> applyReturn(ApplyReturnReq req) {
        log.error("microservice-loverent-warehouse 系统异常applyReturn，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
      }

      @Override
      public ResponseResult<RentingReq> renting(RentingReq q) {
        log.error("microservice-loverent-warehouse 系统异常renting，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
      }

      @Override
      public ResponseResult<UndoPickReq> undoPick(UndoPickReq q) {
        log.error("microservice-loverent-warehouse 系统异常undoPick，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-warehouse 系统异常，已回退", null);
      }
      
      
    };
  }

  




}