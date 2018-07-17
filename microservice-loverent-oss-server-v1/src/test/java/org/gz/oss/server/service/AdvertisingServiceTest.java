package org.gz.oss.server.service;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Advertising;
import org.gz.oss.common.entity.AdvertisingVO;
import org.gz.oss.common.service.AdvertisingService;
import org.gz.oss.server.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * 广告位单元测试
 * 
 * @author daiqingwen
 * @Date 2018-03-13 下午17:02
 */
@Slf4j
public class AdvertisingServiceTest extends BaseTest {

	@Autowired
	private AdvertisingService advertisingService;

	/**
	 * 查询
	 */
	@Test
	public void queryAdverList() {
		ResponseResult<List<Advertising>> result = this.advertisingService.queryAdverList();
		log.info("查询结果为 :" + result);
		assertTrue(result.isSuccess());
	}

	/**
	 * 新增
	 */
	@Test
	public void insertTest() {
		AdvertisingVO vo = new AdvertisingVO();
		vo.setImage("www.baidu.com");
		vo.setImeiNo("P0003243432");
		vo.setLinkUrl("www.tmall.com");
		vo.setSnNo("serwefsdfwerqwe32423");
		vo.setType(30);
		vo.setPosition(3);
		ResponseResult<?> result = this.advertisingService.insert(vo);
		log.info("结果为 :" + result);
		assertTrue(result.isSuccess());
	}

	/**
	 * 修改
	 */
	@Test
	public void updateTest() {
		AdvertisingVO vo = new AdvertisingVO();
		vo.setImage("www.163.com");
		vo.setImeiNo("P0000002224565");
		vo.setLinkUrl("");
		vo.setSnNo("P0034034023432sdafasdfsda");
		vo.setType(30);
		vo.setId(3);
		vo.setPosition(3);
		ResponseResult<?> result = this.advertisingService.update(vo);
		log.info("修改结果为" + result);
		assertTrue(result.isSuccess());
	}

	/**
	 * 删除
	 */
	@Test
	public void deleteTest() {
		int id = 9;
		ResponseResult<?> result = this.advertisingService.delete(id);
		log.info("删除结果为" + result);
		assertTrue(result.isSuccess());
	}

	/**
	 * 上移/下移
	 */
	@Test
	public void move() {
		AdvertisingVO vo = new AdvertisingVO();
		vo.setId(12);
		vo.setPosition(2);
		AdvertisingVO vo2 = new AdvertisingVO();
		vo2.setId(13);
		vo2.setPosition(1);
		List<AdvertisingVO> list = new ArrayList<>();
		list.add(vo);
		list.add(vo2);
		AdvertisingVO vo3 = new AdvertisingVO();
		vo3.setAttaList(list);
		ResponseResult<?> result = this.advertisingService.move(vo3);
		log.info("上移/下移结果为：" + result);
		assertTrue(result.isSuccess());
	}

	@Test
	public void ss() {
		Map<String,Integer> map = new HashMap<>();
		map.put("a",1);
		map.put("b",2);
		map.put("c",3);
		map.put("d",4);
		map.put("e",5);
//		Set<Map.Entry<String, Integer>> set = map.entrySet();
//		for (Map.Entry<String, Integer> m : set){
//			System.out.println(">>>>>>key is ：" + m.getKey() + "-" +"value is :" + m.getValue());
//		}
		Iterator<Map.Entry<String,Integer>> ite = map.entrySet().iterator();
		while (ite.hasNext()){
			Map.Entry<String,Integer> m = ite.next();
			System.out.println(">>>>>>key is ：" + m.getKey() + "  " +"value is :" + m.getValue());
		}

		List<String> list = new ArrayList();

	}
}