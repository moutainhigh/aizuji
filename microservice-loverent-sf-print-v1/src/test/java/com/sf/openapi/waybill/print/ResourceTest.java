/**
 * 
 */
package com.sf.openapi.waybill.print;

import java.io.File;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class ResourceTest extends BaseTest{

	@Test
	public void test() throws Exception {
//		HystrixCommandAspect a;
		File file1 = ResourceUtils.getFile("classpath:template/poster_100mm150mm.jasper");
		System.err.println(file1.getAbsolutePath());
		File file2 = ResourceUtils.getFile("classpath:template/v3_poster_100mm210mm.jasper");
		System.err.println(file2.getAbsolutePath());
	}
}
