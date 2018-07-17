package org.gz.thirdParty.utils;

import java.util.List;

import org.gz.common.utils.HttpClientUtil;
import org.gz.thirdParty.bean.shunFeng.OrderDownload;

import com.alibaba.fastjson.JSON;

public class DownloadPicUtils {

	
	public static void main(String[] args) {
		String[] orderids = new String[] {"1111234549935","1111234549936","1111234549937","1111234549938"};
		try {
			for(String orderid : orderids) {
				String resp = HttpClientUtil.get("http://192.168.2.115:8009/orderDownload?orderId="+orderid);
				System.out.println(resp);
				DownloadResp download = JSON.parseObject(resp,DownloadResp.class);
				List<String> list = download.getData().getBody().getImages();
				int i=1;
				for(String pic : list){
					Base64Trans.GenerateImage(Base64Trans.GenerateImageByte(pic),"c:/pic/"+orderid+"_"+i+++".jpg");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static class DownloadResp{
		private Integer code;
		private String message;
		private OrderDownload data;
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public OrderDownload getData() {
			return data;
		}
		public void setData(OrderDownload data) {
			this.data = data;
		}
	}
}
