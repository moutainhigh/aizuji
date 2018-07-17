package org.gzjf.test;

import org.gz.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "feign.hystrix.enabled=false" })
@DirtiesContext
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void testGetAddListJson(){
		JSONObject body = new JSONObject();
		body.put("name", "xxx");
		body.put("pwd", "asdsa");
		userService.login(body);
	}
	
	

}
