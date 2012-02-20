package org.xsalefter.springredis.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"classpath*:/META-INF/spring-redis-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTemplateTest {
	
	@Inject
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testInjectionWorks() {
		Assert.assertNotNull(this.redisTemplate);
	}
}
