package org.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//@SpringBootApplication(exclude = { JndiConnectionFactoryAutoConfiguration.class, DataSourceAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,
//		DataSourceTransactionManagerAutoConfiguration.class })
@SpringBootApplication

public class SpringBoot2JPAWithHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot2JPAWithHibernateApplication.class, args);
	}
}