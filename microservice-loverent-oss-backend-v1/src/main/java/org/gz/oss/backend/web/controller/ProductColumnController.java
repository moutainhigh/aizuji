package org.gz.oss.backend.web.controller;

import java.util.Date;
import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.ProductColumn;
import org.gz.oss.common.entity.ProductColumnRelation;
import org.gz.oss.common.feign.OSSUploadAliService;
import org.gz.oss.common.request.ProductColumnRelationReq;
import org.gz.oss.common.request.ProductColumnReq;
import org.gz.oss.common.service.ProductColumnRelationService;
import org.gz.oss.common.service.ProductColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/productColumn")
public class ProductColumnController extends BaseController {
	
	@Autowired
	private ProductColumnService iProductColumnService;
	
	@Autowired
	private ProductColumnRelationService productColumnRelationService;

	@Autowired
	private OSSUploadAliService uploadAliService;
	
	
	/**
	 * 添加栏目配置
	 * @param column
	 * @return
	 * @throws
	 * createBy:zhangguoliang            
	 * createDate:2017年12月20日
	 */
    @PostMapping(value = "/addColumn")
    public ResponseResult<?> addColumn(ProductColumn column) {
        try {
        	//初始化默认值
        	column.setCreateTime(new Date());
        	column.setIsDeleted(0);
            iProductColumnService.add(column);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("addColumn失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }
    
    
   /**
    * 查询栏目配置列表
    * @return
    * @throws
    * createBy:zhangguoliang            
    * createDate:2017年12月20日
    */
    @PostMapping(value = "/queryColumnList")
    public ResponseResult<?> queryColumnList() {
        try {
            return ResponseResult.buildSuccessResponse(iProductColumnService.findAll());
        } catch (Exception e) {
            log.error("queryColumnList失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
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
            return ResponseResult.buildSuccessResponse(iProductColumnService.getById(id));
        } catch (Exception e) {
            log.error("getColumnById失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 更新栏目配置信息
     * @param column
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年12月21日
     */
    @PostMapping(value="/updateColumn")
    public ResponseResult<?> updateColumn(ProductColumn column) {
        try {
        	iProductColumnService.update(column);
        	return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("updateColumn失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
   /**
    * 批量更新栏目配置信息
    * @param columnReqList
    * @return
    * @throws
    * createBy:zhangguoliang            
    * createDate:2017年12月20日
    */
    @PostMapping(value="/batchUpdateColumn")
    public ResponseResult<?> batchUpdateColumn(ProductColumnReq req) {
        try {
            iProductColumnService.batchUpdate(req.getColumnReqList());
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("batchUpdateColumn失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 查询栏目对应的产品列表
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年12月20日
     */
    @PostMapping(value = "/queryRelationList")
    public ResponseResult<?> queryRelationList(Integer columnId) {
        try {
            return ResponseResult.buildSuccessResponse(this.productColumnRelationService.queryListByColumnId(columnId));
        } catch (Exception e) {
            log.error("queryRelationList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    
    
    /**
     * 批量添加绑定关系
     * @param req
     * @return
     */
    @PostMapping(value="/batchAddRelation")
    public ResponseResult<?> batchAddRelation(ProductColumnRelationReq req) {
        try {
        	Date now = new Date();
        	List<ProductColumnRelation> relationReqList = req.getRelationReqList();
        	//初始化默认值
        	if(relationReqList!=null && relationReqList.size()>0){
        		for (ProductColumnRelation relation : relationReqList) {
            		relation.setCreateTime(now);//创建时间
            		relation.setIsDeleted(1);//是否删除，0-正常
    			}
        	}
        	//return iProductColumnService.batchAddRelation(req);
        	this.productColumnRelationService.addBatch(relationReqList);
        	return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("batchAddRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    /**
     * 批量更新绑定关系
     * @param req
     * @return
     */
    @PostMapping(value="/batchUpdateRelation")
    public ResponseResult<?> batchUpdateRelation(ProductColumnRelationReq req) {
        try {
            //return iProductColumnService.batchUpdateRelation(req);
        	this.productColumnRelationService.updateBatch(req.getRelationReqList());
        	return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("batchUpdateRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 添加绑定关系
     * @param 绑定关系
     * @return
     * @throws
     * createBy:yangdx            
     */
    @PostMapping(value="/addRelation")
    public ResponseResult<?> addRelation(ProductColumnRelation relation) {
        try {
        	relation.setCreateTime(new Date());//创建时间
    		relation.setIsDeleted(0);//是否删除，0-正常
        	//return iProductColumnService.addRelation(relation);
    		this.productColumnRelationService.add(relation);
    		return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("addRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 上传图片
     */
//    @PostMapping(value="/uploadImg")
//    public ResponseResult<?> uploadImg(MultipartFile file) {
//    	Object data="";
//        try {
//        	if (file != null) {
//
//        		//上传图片
//        		UploadBody dataMap = new UploadBody();
//        		String originalFilename = file.getOriginalFilename();// 原文件名
//    			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
//
//    			dataMap.setFile(IOUtils.toByteArray(file.getInputStream()));
//    			dataMap.setBucketName("image/oss/product_column_relation/");
//    			dataMap.setReturnPathType(1);
//    			dataMap.setFileType(fileType);
//    	        ResponseResult d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
//        		if(0 != d.getErrCode()){ //上传失败直接提示
//        			return d;
//        		}else{
//        			data = d.getData();
//        		}
//        	}
//        	return ResponseResult.buildSuccessResponse(data);
//        } catch (Exception e) {
//            log.error("uploadImg失败：{}", e);
//            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
//                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
//                e.getMessage());
//        }
//    }
    
    /**
     * 修改绑定关系
     * @param 绑定关系
     * @return
     */
    @PostMapping(value="/updateRelation")
    public ResponseResult<?> updateRelation(ProductColumnRelation relation) {
    	try {
        	//return iProductColumnService.updateRelation(relation);
    		this.productColumnRelationService.updateRelation(relation);
        	return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("updateRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 删除绑定关系
     * @param 绑定关系
     * @return
     * @throws
     * createBy:yangdx            
     */
    @PostMapping(value="/deleteRelation")
    public ResponseResult<?> deleteRelation(Integer id) {
        try {
        	//return iProductColumnService.deleteRelation(id);
        	this.productColumnRelationService.deleteRelation(id);
        	return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("deleteRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    
    /**
     * 获取绑定关系
     * @param id
     * @return
     */
    @PostMapping(value="/getRelation")
    public ResponseResult<?> getRelation(Integer id) {
        try {
        	return ResponseResult.buildSuccessResponse(this.productColumnRelationService.getById(id));//iProductColumnService.getRelation(id);
        } catch (Exception e) {
            log.error("getRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                e.getMessage());
        }
    }
    
}
