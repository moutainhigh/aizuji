package org.gz.oss.server.web.controller;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.dao.ProductColumnRelationDao;
import org.gz.oss.common.entity.ProductColumn;
import org.gz.oss.common.entity.ProductColumnRelation;
import org.gz.oss.common.service.ProductColumnRelationService;
import org.gz.oss.common.service.ProductColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/productColumn")
@Slf4j
public class ProductColumnServerController {
	
	@Autowired
	private ProductColumnService productColumnService;
	
	@Autowired
	private ProductColumnRelationService columnRelationService;
	
	@Autowired
	private ProductColumnRelationDao productColumnRelationDao;
    
   /**
    * 查询栏目配置列表
    * @return
    * @throws
    * createBy:zhangguoliang            
    * createDate:2017年12月20日
    */
    @PostMapping(value = "/queryColumnList")
    public ResponseResult<List<ProductColumn>> queryColumnList() {
        try {
        	List<ProductColumn> columnList = productColumnService.findAll();
        	if (columnList != null && !columnList.isEmpty()) {
        		for (ProductColumn column : columnList) {
        			List<ProductColumnRelation> relations = productColumnRelationDao.queryAvalableListByColumnId(column.getId());
        			/*if (relations != null && !relations.isEmpty()) {
        				String ids = "";
        				StringBuffer sb = new StringBuffer();
        				for (ProductColumnRelation relation: relations) {
        					sb.append(relation.getMaterielModelId()).append(",");
        				}
        				if (sb.length() > 0) {
        					ids = sb.substring(0, sb.length() - 1);
        					column.setMaterielModelIds(ids);
        				}
        			}*/
        			column.setMaterielData(relations);
        		}
        	}
            return ResponseResult.buildSuccessResponse(columnList);
        } catch (Exception e) {
            log.error("queryColumnList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
   /**
    * id查询栏目配置详情
    * @param id
    * @return
    * @throws
    * createBy:zhangguoliang            
    * createDate:2017年12月20日
    */
    @PostMapping(value="/getColumnById")
    public ResponseResult<?> getColumnById(Integer id) {
        try {
        	ProductColumn column = productColumnService.getById(id);
            return ResponseResult.buildSuccessResponse(column);
        } catch (Exception e) {
            log.error("getColumnById失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
  
    /**
     * 查询绑定关系
     * @param id
     * @return
     */
    @PostMapping(value="/getRelation")
    public ResponseResult<?> getRelation(@RequestParam("id") Integer id) {
        try {
        	ProductColumnRelation columnRelation =columnRelationService.getRelation(id);
            return ResponseResult.buildSuccessResponse(columnRelation);
        } catch (Exception e) {
            log.error("batchUpdateRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
}
