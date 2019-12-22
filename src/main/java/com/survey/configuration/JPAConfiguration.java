package com.survey.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.survey.dao","com.survey.repository"})
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource((javax.sql.DataSource) dataSource());
		em.setPersistenceUnitName("persistencia");
		em.setPackagesToScan("com.survey.domain");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		Properties jpaProperties = hibernateProperties();
		em.setJpaProperties(jpaProperties);
		return em;
	}
	
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://z1gvtk2rh5sxr5be:fbd42vtkn0c9o59p@ysp9sse09kl0tzxj.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306"
                        +"/xvu58m1axlz9rxs1");
		dataSource.setUsername("z1gvtk2rh5sxr5be");
		dataSource.setPassword("fbd42vtkn0c9o59p");
		return (DataSource) dataSource;
	}
	
	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql","true");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}
}
