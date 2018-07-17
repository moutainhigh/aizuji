package org.gz.common.oss;

import java.io.InputStream;
import java.util.Arrays;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

public class OSSTest {
	
	private static String bucketName = "osstest-01";
	private static String endPoint = "http://oss-cn-beijing.aliyuncs.com";
	private static String accessKeyId = "LTAI1owWwhSga1II";
	private static String accessKeySecret = "Q8e2DtoFGuOh63IXrIefFEUfGwswRW";
	
	public static void main(String[] args) {
		OSSClient client= new OSSClient(
				endPoint, 
				accessKeyId, 
				accessKeySecret);
		
		OSSObject oob = client.getObject(bucketName, "pdf/agreement/temp/unsign.pdf");
		if (oob != null) {
			InputStream is = oob.getObjectContent();
			if (is != null) {
				try {
					System.out.println(is.available());
					
					byte[] stream = new byte[is.available()];
					System.out.println(Arrays.toString(stream));
					is.read(stream, 0, is.available());
					System.out.println(Arrays.toString(stream));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
