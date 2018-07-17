package org.gz.liquidation.feign;

import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "MICROSERVICE-LOVERENT-UPLOAD-V1",fallback=IUploadAliServiceFallBack.class)
public interface IUploadAliService {


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
   @PostMapping(value="/uploadAli/uploadToOSSFileInputStrem")
   public ResponseResult<String>  uploadToOSSFileInputStrem(@RequestBody UploadBody uploadBody) ;
}


@Component
class IUploadAliServiceFallBack implements IUploadAliService {
	@Override
	public ResponseResult<String> uploadToOSSFileInputStrem(UploadBody uploadBody) {
		return defaultBkMethod();
	}

	private ResponseResult<String> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-upload-v1系统异常，已回退", null);
    }


}