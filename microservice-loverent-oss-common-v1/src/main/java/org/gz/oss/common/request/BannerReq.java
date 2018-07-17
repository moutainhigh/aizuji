package org.gz.oss.common.request;

import java.io.Serializable;
import java.util.List;

import org.gz.oss.common.entity.Banner;

public class BannerReq implements Serializable{

	private static final long serialVersionUID = 1392437086345316163L;
	
	private List<Banner> bannerReqList;

	public List<Banner> getBannerReqList() {
		return bannerReqList;
	}

	public void setBannerReqList(List<Banner> bannerReqList) {
		this.bannerReqList = bannerReqList;
	}
	
}
