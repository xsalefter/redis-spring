package org.xsalefter.springredis.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"classpath*:/META-INF/spring-redis-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConnectionTest {

	@Inject
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Test
	public void testInjectionWorks() {
		Assert.assertNotNull(this.jedisConnectionFactory);
	}
	
	@Test
	public void connectionTest() {
		JedisConnection connection = this.jedisConnectionFactory.getConnection();
		Assert.assertNotNull(connection);
	}
}
