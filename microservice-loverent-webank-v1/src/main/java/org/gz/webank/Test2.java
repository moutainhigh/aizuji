package org.gz.webank;

import java.net.URLEncoder;

public class Test2 {
	public static void main(String[] args) {
		String string = URLEncoder.encode("http://www.shebaodai.cn/resource/configs/versionControl.json");
		System.out.println(string);
	}
}
