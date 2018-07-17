package org.gz.webank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test3 {
	public static void main(String[] args) {
		
		List<String> eles = new ArrayList<>();
		eles.add("appId001");
		eles.add("userID19959248596551");
		eles.add("kHoSxvLZGxSoFsjxlbzEoUzh5PAnTU7T");
		eles.add("1.0.0");
		eles.add("aabc1457895464");
		eles.add("zxc9Qfxlti9iTVgHAjwvJdAZKN3nMuUhrsPdPlPVKlcyS50N6tlLnfuFBPIucaMS");
		
		String sign = sign(eles);
		System.out.println(sign);
	}
	
	private static String sign(List<String> eles) {
		eles.removeAll(Collections.singleton(null));
		Collections.sort(eles);
		
		StringBuffer sb = new StringBuffer();
		for (String el : eles) {
			sb.append(el);
		}
		
		System.out.println(sb);
	//	return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
		return null;
	}

}
