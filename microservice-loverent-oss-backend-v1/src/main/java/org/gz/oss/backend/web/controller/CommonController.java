package org.gz.oss.backend.web.controller;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.backend.helper.FileUploadHelper;
import org.gz.oss.common.constants.OssCommConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Value("${img.rootPath}")
    private String           imgRootPath;

    @Value("${img.storePath}")
    private String           imgStorePath;

    @Resource
    private FileUploadHelper fileUploadHelper;

    /**
     * @Description: 上传图片
     * @param file 图片
     * @param type 图片类型
     * @return ResponseResult
     * @createBy:liuyt
     * @createDate:2017年8月15日
     */
    @RequestMapping("/uploadImg")
    public ResponseResult<?> uploadImg(@RequestBody MultipartFile file, Integer type) {
        String storePath = "";
        switch (type) {
            case OssCommConstants.UPLOAD_IMG_TYPE_BANNER:
                storePath = "banner";
                break;
            case OssCommConstants.UPLOAD_IMG_TYPE_ADVERTISING:
                storePath = "advertising";
                break;
            case OssCommConstants.UPLOAD_IMG_TYPE_SHOPWINDOW:
                storePath = "shopwindow";
                break;
            case OssCommConstants.UPLOAD_IMG_TYPE_RECOMMEND:
                storePath = "recommend";
                break;
            case OssCommConstants.UPLOAD_IMG_TYPE_ALIPAYAPP:
                storePath="alipayapp";
            default:
                break;
        }
        String data = fileUploadHelper.upload(file, imgRootPath, imgStorePath + storePath);
        return ResponseResult.buildSuccessResponse(data);
    }
}
