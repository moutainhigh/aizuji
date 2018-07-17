package org.gz.upload.web.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.upload.util.AliyunOSSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploadAli")
public class UploadAliController {
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(UploadAliController.class);
	
	@Autowired
	private AliyunOSSService aliyunOSSService;
//    /**
//     * 上传多个文件，同时返回文件名称
//     * @param files 多个文件流
//     * @param bucketName  分区路径名称
//     * @return  
//     */
//    @PostMapping(value="/uploadToOSSFileList")
//    public ResponseResult<?>  uploadToOSSFileList(List<MultipartFile> files, String bucketName) {
//        try {
//        		//上传图片
//        	Map<String, List<Map<String, String>>> uploadRst = 
//        				aliyunOSSService.uploadToOSS(files,bucketName);
//        	return ResponseResult.buildSuccessResponse(uploadRst);	
//        } catch (Exception e) {
//        	logger.error("uploadImg失败：{}", e);
//            return ResponseResult.build(ResponseStatus.FILE_UPLOAD_FAILED.getCode(),
//                ResponseStatus.FILE_UPLOAD_FAILED.getMessage(),
//                e.getMessage());
//        }
//    }
	  /**
     * 获取上传路径前缀
     * @param  fileUrl 文件上传路径
     * @return  文件流数组
     */
    @PostMapping(value="/getByteArray")
    public ResponseResult<byte[]>  getByteArray(@RequestParam String fileUrl) {
    	byte[] b = null;
    	try {
    		InputStream in = aliyunOSSService.getInputStream(fileUrl);
    		
    		if(in != null){
    			 b = IOUtils.toByteArray(in);
    			 return ResponseResult.buildSuccessResponse(b);
    		}
		} catch (Exception e) {
			logger.error("getByteArray失败：{}", e);
			 return ResponseResult.build(ResponseStatus.FILE_FETCH_FAILED.getCode(),
		                ResponseStatus.FILE_FETCH_FAILED.getMessage(),null);
			
		}
    	   return ResponseResult.build(ResponseStatus.FILE_FETCH_FAILED.getCode(),
                   ResponseStatus.FILE_FETCH_FAILED.getMessage(),null);
    }
	  /**
     * 获取上传路径前缀
     * @return  上传路径前缀
     */
    @PostMapping(value="/getAccessUrlPrefix")
    public String  getAccessUrlPrefix() {
    	return aliyunOSSService.getAccessUrlPrefix();
    }
    
    /**
     * 上传单个文件
     * 
     * @param file 必传字段，文件流
     * @param fileName  可选字段，文件名称，如果传了代表上传到指定路径，优先级最高
     * @param bucketName  分区路径名称，文件名称，如果传了代表随机生成文件名称
     * @param returnPathType  可选字段，返回路径类型，和bucketName组合使用，如果为1代表返回图片完成路径，其他代表返回文件名称
     * @param fileType  可选字段，文件类型，和bucketName组合使用，例如jpg
     * @return  
     */
    @PostMapping(value="/uploadToOSSFileInputStrem")
    public ResponseResult<String>  uploadToOSSFileInputStrem(@RequestBody UploadBody uploadBody) {
        try {
        	InputStream sbs = new ByteArrayInputStream(uploadBody.getFile()); 
        	if(uploadBody.getFileName() != null){
            	aliyunOSSService.uploadToOSS(sbs,uploadBody.getFileName());
            	return ResponseResult.buildSuccessResponse("");	
        	}
        	if(uploadBody.getBucketName() != null){
    			String randomFileName = String.valueOf(System.nanoTime()) + "_"+UUID.randomUUID().toString().replaceAll("-", ""); 
    			
    			String ossFullPath = uploadBody.getBucketName() + "/" + randomFileName+uploadBody.getFileType();
    			
            	aliyunOSSService.uploadToOSS(sbs,ossFullPath);
            	if(uploadBody.getReturnPathType() == 1){
            		return ResponseResult.buildSuccessResponse(aliyunOSSService.getAccessUrlPrefix() + ossFullPath);	
            	}else{
            		return ResponseResult.buildSuccessResponse(ossFullPath);	
            	}
            	
        	}
        	
        	
            return ResponseResult.build(ResponseStatus.FILE_UPLOAD_FAILED.getCode(),
                    ResponseStatus.FILE_UPLOAD_FAILED.getMessage(),
                   "上传图片失败");
        } catch (Exception e) {
        	logger.error("uploadImg失败：{}", e);
            return ResponseResult.build(ResponseStatus.FILE_UPLOAD_FAILED.getCode(),
                ResponseStatus.FILE_UPLOAD_FAILED.getMessage(),
                e.getMessage());
        }
    }
}
