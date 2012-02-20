package org.xsalefter.springredis.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Example using {@link String} as a key and {@link List} as a value. 
 * @author xsalefter (xsalefter@gmail.com)
 */
@ContextConfiguration({"classpath*:/META-INF/spring-redis-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StringRedisTemplateWithListTest {

	@Inject
	private StringRedisTemplate stringRedisTemplate;
	private ListOperations<String, String> storage;
	
	/**
	 * Populate the sample data before we used it.
	 */
	@Before
	public void before() {
		this.storage = this.stringRedisTemplate.opsForList();
		Assert.assertNotNull(this.stringRedisTemplate);
		Assert.assertNotNull(this.storage);
		
		this.storage.rightPush("person", "Coddy Andersen");
		this.storage.rightPush("person", "Anna Lopez");
		this.storage.rightPush("person", "Michael Justin");
		this.storage.rightPush("person", "Roger Guortanni");
		this.storage.rightPush("person", "Zelda Hamilton");
	}
	
	/**
	 * Remove the sample data after one test executed. So we have consistent data 
	 * set in every test.
	 */
	@After
	public void after() {
		this.storage.getOperations().delete("person");
	}
	
	@Test
	public void countAllPerson() {
		System.out.println(">>>>> countAllPerson() <<<<<");
		Long size = this.storage.size("person");
		Assert.assertEquals(size, Long.valueOf(5L));
	}
	
	@Test
	public void listAllPerson() {
		System.out.println(">>>>> listAllPerson() <<<<<");
		List<String> persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		for (String person : persons) 
			System.out.println(">>> Person : " + person);
	}
	
	@Test
	public void addNewPerson() {
		System.out.println(">>>>> addNewPerson() <<<<<");
		this.storage.rightPush("person", "Jonathan Rose");
		
		List<String> persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		for (String person : persons) 
			System.out.println(">>> Person : " + person);
				
		Long size = this.storage.size("person");
		Assert.assertEquals(size, Long.valueOf(6L));
	}
	
	@Test
	public void updateSpecificsPerson() {
		System.out.println(">>>>> updateSpecificsPerson() <<<<<");
		final String personToUpdate = new String("Anna Lopez");
		final String newPersonName = new String("Anna Lopez Hiquilera");
		
		List<String> persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		int dataIndex = persons.indexOf(personToUpdate);
		this.storage.set("person", dataIndex, newPersonName);
		
		persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		for (String p : persons)
			System.out.println(">>> Person : " + p);
		
	}
	
	@Test
	public void deleteSpecificsPerson() {
		System.out.println(">>>>> deleteSpecificsPerson() <<<<<");
		final String personToRemove = new String("Anna Lopez");
		
		List<String> persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		int dataIndex = persons.indexOf(personToRemove);
		this.storage.remove("person", dataIndex, personToRemove);
		
		persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		for (String p : persons)
			System.out.println(">>> Person : " + p);
		
	}
	
	/**
	 * This is depends on your case. Maybe we could optimize this by using 
	 * binary search, or some 3rd-party lib from apache until guava.
	 */
	@Test
	public void searchPerson() {
		System.out.println(">>>>> searchPerson <<<<<");
		final String searchKey = "an";
		
		List<String> persons = this.storage.range("person", Long.valueOf(0), this.storage.size("person"));
		List<String> searchResult = new ArrayList<String>();
		
		for (String person : persons) {
			if (person.toLowerCase().contains(searchKey.toLowerCase())) {
				searchResult.add(person);
			}
		}
		
		for (String person : searchResult)
			System.out.println(">>> Search Result :" + person);
	}
}
