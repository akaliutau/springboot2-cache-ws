package org.microservice.test;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.microservice.dao.DMLType;
import org.microservice.dao.OperationFactory;
import org.microservice.model.Cached;
import org.microservice.model.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class})
public class PersistentLevelTests {

	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	
    String key = "key1";
    String value = "val1";
    Pair pair = new Pair(key,value);


	@Test
	public void integration1Test() {
		assertTrue(entityManagerFactory != null);
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		assertTrue(session != null);
		
		OperationFactory operationFactory = new OperationFactory();
		DMLType result = (DMLType) operationFactory.upsert(session, pair);

		assertTrue(result != null);
		
		Cached found = (Cached) operationFactory.searchByKey(session, key);
		

		assertTrue(found != null);
		assertTrue(found.getKey() != null);
		assertTrue(found.getKey().equals(key));
		assertTrue(found.getValue() != null);
		assertTrue(found.getValue().equals(value));

	}

}
