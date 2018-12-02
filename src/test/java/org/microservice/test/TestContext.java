package org.microservice.test;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.microservice.dao.HibernateService;
import org.microservice.dao.OperationFactory;
import org.microservice.model.Cached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "org.microservice.test")
@EnableTransactionManagement
//@EntityScan(basePackages = { "org.microservice" })
public class TestContext {
	
	
	@Bean
	HibernateService hibernateService() {
		OperationFactory operationFactory = new OperationFactory();
		HibernateService hibernateService = new HibernateService(45000L,operationFactory);
		return hibernateService;
	}
	
	   @Bean
	    public DataSource dataSource(){

	        DriverManagerDataSource ds = new DriverManagerDataSource();

	        ds.setDriverClassName("org.h2.Driver");
	        ds.setUrl("jdbc:h2:mem");
	        ds.setUsername("sa");
	        ds.setPassword("");

	        return ds;

	    } 
	   
		@Autowired
		@Bean(name = "sessionFactory")
		public SessionFactory getSessionFactory() {
			LocalSessionFactoryBuilder sessionBuilder = 
					new LocalSessionFactoryBuilder(dataSource());
			sessionBuilder.addAnnotatedClasses(Cached.class);
			sessionBuilder.addProperties(getHibernateProperties());
			return sessionBuilder.buildSessionFactory();
			    
		}
		private Properties getHibernateProperties() {
			Properties properties = new Properties();
			properties.put("hibernate.show_sql", "true");
			properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.put("hibernate.hbm2ddl.auto", "create");
			return properties;
		}

		@Autowired
		@Bean(name = "hibernateTemplate")
		public HibernateTemplate hibernateTemplate() {
			return new HibernateTemplate(getSessionFactory());
		}

		
		@Autowired
		@Bean(name = "transactionManager")
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
			HibernateTransactionManager transactionManager = 
				new HibernateTransactionManager(sessionFactory);
			
			transactionManager.setRollbackOnCommitFailure(true);

			return transactionManager;
		}



}
