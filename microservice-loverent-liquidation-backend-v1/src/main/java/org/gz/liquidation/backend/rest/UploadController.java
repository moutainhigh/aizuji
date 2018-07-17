/**
 * 
 */
package org.gz.liquidation.backend.rest;

import org.apache.commons.io.IOUtils;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.backend.feign.IUploadAliService;
import org.gz.liquidation.common.entity.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月27日 下午3:38:11
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

	@Value("${oss.agreement.path}")
	private String osspath;
	  
	@Autowired
	private IUploadAliService uploadAliService;
	
	@PostMapping(value="/uploadImage")
    public ResponseResult<Attachment> uploadImage(@RequestParam("attaFile") MultipartFile file) {
        try {
        	Attachment data = new Attachment();
    		UploadBody dataMap = new UploadBody();
    		String originalFilename = file.getOriginalFilename();// 原文件名
			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));   
			
			dataMap.setFile(IOUtils.toByteArray(file.getInputStream()));
			dataMap.setBucketName(osspath);
			dataMap.setReturnPathType(1);
			dataMap.setFileType(fileType);
			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
    		if(0 == d.getErrCode()){ //上传失败直接提示
    			data.setAttaUrl(d.getData());
    		}
        	//返回json
            return ResponseResult.buildSuccessResponse(data);
        } catch (Exception e) {
        	log.error("上传文件失败：{}",e.getLocalizedMessage());
        	return ResponseResult.build(2000, "附件上传失败", null);
        }
    }
}
