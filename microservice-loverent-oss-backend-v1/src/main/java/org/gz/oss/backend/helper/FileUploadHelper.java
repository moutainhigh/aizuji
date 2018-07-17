package org.gz.oss.backend.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.Enum.ImageResultCode;
import org.gz.oss.common.exception.BusinessExcepton;
import org.gz.oss.common.feign.OSSUploadAliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传帮助类
 * 
 * @Description:TODO Author Version Date Changes liuyt 1.0 2017年7月31日 Created
 */
@Component
public class FileUploadHelper {

	@Autowired
	private OSSUploadAliService uploadAliService;

    @Value("${ALIYUN_OSS_UPLOAD}")
    private boolean          ALIYUN_OSS_UPLOAD;


    @Value("${ngix.img.viewPath}")
    private String           ngixImgViewPath;

    private Logger           logger = LoggerFactory.getLogger(FileUploadHelper.class);

    /**
     * 上传图片文件
     * 
     * @Description: TODO
     * @return
     * @throws createBy:liuyt createDate:2017年7月31日
     */
    public String upload(MultipartFile file, String rootPath, String storePath) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        String suffix;
        String viewRoot = "";
        try {
            String fileName = file.getOriginalFilename();
            // 生成三位随机数
            int random = new Random().nextInt(900) + 100;
            suffix = storePath + str + random + "_" + fileName;
            if (ALIYUN_OSS_UPLOAD) {
        		//上传图片
        		UploadBody dataMap = new UploadBody();
        		String originalFilename = file.getOriginalFilename();// 原文件名
    			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));   
    			
    			dataMap.setFile(IOUtils.toByteArray(file.getInputStream()));
    			dataMap.setBucketName(storePath);
    			dataMap.setReturnPathType(1);
    			dataMap.setFileType(fileType);
    			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
    			if(0 != d.getErrCode()){ //上传失败直接提示
			       logger.error("upload error", d.getErrMsg());
		            throw new BusinessExcepton(ImageResultCode.IMG_UPLOAD_FAIL.getCode(),
		            		ImageResultCode.IMG_UPLOAD_FAIL.getMessage());
        		}else{
        			return d.getData();
        		}
            } else {
                String filePath = rootPath + storePath;
                File tempfile = new File(filePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String path = rootPath + suffix;
                File root = new File(path);
                file.transferTo(root);
                viewRoot = ngixImgViewPath;
            }
        } catch (Exception e) {
            logger.error("upload error", e);
            throw new BusinessExcepton(ImageResultCode.IMG_UPLOAD_FAIL.getCode(),
            		ImageResultCode.IMG_UPLOAD_FAIL.getMessage());
        }
        return viewRoot + suffix;
    }

    /**
     * 是否图片后缀
     * 
     * @Description: TODO
     * @return
     * @throws createBy:liuyt createDate:2017年7月31日
     */
    public boolean isImgSuffix(String suffix) {
        if (!suffix.equalsIgnoreCase(".png")) {
            throw new BusinessExcepton(ImageResultCode.IMG_SUFFIX_ERROR.getCode(),
            		ImageResultCode.IMG_SUFFIX_ERROR.getMessage());
        }
        return true;
    }

}
