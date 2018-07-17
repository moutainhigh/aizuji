package org.gz.warehouse.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

	protected static final long HUNDRED_MS=100L;
	
	protected static final long ONE_SECOND=1000L;
	
	protected static final long TEN_SECOND=10*ONE_SECOND;
	
}
