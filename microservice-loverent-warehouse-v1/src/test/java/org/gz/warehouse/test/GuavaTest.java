/**
 * 
 */
package org.gz.warehouse.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月19日 下午2:59:29
 */
public class GuavaTest {

	private static final File BASE_DIR = new File(".");

	@Test
	public void testJoiner1() throws IOException {
		Joiner joiner = Joiner.on(",");
		StringBuilder builder = new StringBuilder();
		String[] arr = new String[] { "1", "2", "3" };
		joiner.appendTo(builder, Arrays.asList(arr));
		assertEquals(builder.toString(), "1,2,3");
		builder.append(",");
		joiner.appendTo(builder, new String[] { "4", "5", "6" });
		assertEquals(builder.toString(), "1,2,3,4,5,6");
	}

	@Test
	public void testJoiner2() throws IOException {
		Joiner joiner = Joiner.on(",");
		Iterator<String> it = Arrays.asList(new String[] { "1", "2", "3" }).iterator();
		File destFile = new File(BASE_DIR.getCanonicalPath(), "testJoiner2.txt");
		FileWriter fileWriter = new FileWriter(destFile);
		joiner.appendTo(fileWriter, it);
		fileWriter.flush();
		fileWriter.close();
		FileReader fileReader = new FileReader(destFile);
		String s = IOUtils.readLines(fileReader).get(0);
		fileReader.close();
		assertEquals(s, "1,2,3");
	}

	@Test
	public void testMapJoiner() {
		Joiner joiner = Joiner.on(",");
		String keyValueSeparator = "=>";
		MapJoiner mapJoiner = joiner.withKeyValueSeparator(keyValueSeparator);
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("userName", "张三");
		map.put("age", 30);
		String s = mapJoiner.join(map);
		assertEquals(s, "userName=>张三,age=>30");
	}

	@Test
	public void testPadStart() {
		System.err.println("P" + Strings.padStart("1", 8, '0'));
		System.err.println("P" + Strings.padStart("111", 8, '0'));
		System.err.println("P" + Strings.padStart("122323", 8, '0'));
	}

	@Test
	public void testOrding() {
		List<Integer> intList = Lists.newArrayList(2, 3, 1);
		Ordering<Integer> ordering = Ordering.from(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Ints.compare(o1, o2);
			}
		});
		Collections.sort(intList, ordering);
		for(Integer i :intList) {
			System.err.println(i);
		}
		assertEquals(Lists.newArrayList(1,2,3),intList);
	}
	
	@Test
	public void testUpload() {
		String s ="https://gzhlw-files.oss-cn-shanghai-internal.aliyuncs.com/materiel/pic/M2018011510525996400018.jpg";
		
		System.err.println(s.length());
	}
	
	@Test
	public void test() {
		List<SaleProductResp> list1 = Lists.newArrayList(new SaleProductResp(1L),new SaleProductResp(2L));
		List<ProductInfo> list2 = Lists.newArrayList(new ProductInfo(1L));
		List<Long> productIds1 = Lists.transform(list1, new Function<SaleProductResp,Long>() {
			@Override
			public Long apply(SaleProductResp input) {
				if(input!=null) {
					return input.getProductId();
				}
				return null;
			}});
		
		List<Long> productIds2 = Lists.transform(list2, new Function<ProductInfo,Long>() {
			@Override
			public Long apply(ProductInfo input) {
				if(input!=null) {
					return input.getId();
				}
				return null;
			}});
		Set<Long> set1 = Sets.newLinkedHashSet(productIds1);
		Set<Long> set2 = Sets.newLinkedHashSet(productIds2);
		Set<Long> result = Sets.difference(set1, set2);
		for(Long r : result) {
			System.out.println("表中要删除的productID:"+r);//表做删除
		}
		result = Sets.difference(set2, set1);
		for(Long r : result) {
				System.out.println("表中要新增的productID:"+r);//表做新增
		}
		
	}
}
