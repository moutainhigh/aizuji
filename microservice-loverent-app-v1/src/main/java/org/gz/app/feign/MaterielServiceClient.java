package org.gz.app.feign;

import org.gz.app.hystrix.MaterielServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="microservice-loverent-warehouse", fallback=MaterielServiceHystrixImpl.class)
public interface MaterielServiceClient {
	
	/**
     * 获取所有物料分类列表  树状返回
     * @param m
     * @return
     */
    @GetMapping(value = "/api/queryAllMaterielClassList")
    public ResponseResult<?> queryAllMaterielClassList();
    
    /**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     */
    @GetMapping(value = "/api/queryMaterielBrandListByClassId/{classId}")
    public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId);
    
    /**
     * 根据物料品牌id查询所有型号列表
     * @param m
     * @return
     */
    @GetMapping(value = "/api/queryMaterielModelListByBrandId/{brandId}")
    public ResponseResult<?> queryMaterielModelListByBrandId(@PathVariable("brandId") Integer brandId);
    
    /**
     * 根据物料型号id查询所有规格列表
     * @param m
     * @return
     */
    @GetMapping(value = "/api/queryMaterielSpecListByModelId/{modelId}")
    public ResponseResult<?> queryMaterielSpecListByModelId(@PathVariable("modelId") Integer modelId);
    
    /**
     * 获取所有新旧配置
     */
    @GetMapping(value = "/api/getAllNewConfigs")
    public ResponseResult<?> getAllNewConfigs();
    
    /**
     * 
    * @Description: 根据物料ID查询物料规格配置参数
     */
    @GetMapping(value = "/api/materiel/queryMaterielSpecPara/{materielBasicInfoId}")
    public ResponseResult<?> queryMaterielSpecPara(@PathVariable("materielBasicInfoId") Long materielBasicInfoId);
}
