package org.gz.oss.common.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.config.FeignFullConfig;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.MaterielUnitShortResp;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.omg.CORBA.INTERNAL;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 物料相关接口
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月15日 	Created
 */
@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE", configuration = FeignFullConfig.class, fallback = IMaterielServiceFallBack.class)
public interface IMaterielService {

    @PostMapping(value = "/materielClass/api/queryAllMaterielClassList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryAllMaterielClassList();

    @PostMapping(value = "/materielBrand/api/queryMaterielBrandListByClassId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryMaterielBrandListByClassId(@RequestParam("classId") Integer classId);
    
    @PostMapping(value = "/materielModel/api/queryMaterielModelListByBrandId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryMaterielModelListByBrandId(@RequestParam("brandId") Integer brandId);

    @PostMapping(value = "/materielSpec/api/queryMaterielSpecListByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryMaterielSpecListByModelId(@RequestParam("modelId") Integer modelId);

    @GetMapping(value = "/materielModel/api/getDetail/{id}")
    public ResponseResult<?> getMaterielModelDetail(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/api/getMaterielModelById",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseResult<List<MaterielModelVo>> getMaterielModelById(@RequestBody List<Integer> modelIdList);

    /**
     * 根据物料ID,仓库编码，库位编码获取商品列表接口
     * @param q
     * @return ResponseResult
     */
    @PostMapping(value = "/api/materielCommodity/queryWarehouseLocationCommoditys")
    public ResponseResult<?> queryWarehouseLocationCommoditys(@RequestBody WarehouseCommodityReq q);
    
    /**
     * 查询所有可用单位列表
    * @Description: 
    * @param @return
    * @return List<MaterielUnitShortResp>
    * @throws
     */
    @GetMapping(value = "/api/queryAllUnitList")
	public List<MaterielUnitShortResp> queryAllUnitList();
}

@Component
class IMaterielServiceFallBack implements IMaterielService{

    @Override
    public ResponseResult<?> queryAllMaterielClassList() {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryMaterielBrandListByClassId(Integer classId) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryMaterielModelListByBrandId(Integer brandId) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryMaterielSpecListByModelId(Integer modelId) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryWarehouseLocationCommoditys(WarehouseCommodityReq q) {
        return defaultBkMethod();
    }

    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<?> getMaterielModelDetail(Integer id) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<List<MaterielModelVo>> getMaterielModelById(@RequestBody List modelIdList) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

	
	@Override
	public List<MaterielUnitShortResp> queryAllUnitList() {
		return new ArrayList<MaterielUnitShortResp>(0);
	}
}
