package com.config;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages= {"com.repository"})
public class RepositoryConfiguration {
	
	@Autowired
	DatabaseConfiguration databaseConfiguration;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(databaseConfiguration.url);
		dataSource.setUsername(databaseConfiguration.username);
		dataSource.setPassword(databaseConfiguration.password);
		
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.model");
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setJpaProperties(additionalProperties());
		//For enabling L2 cache
		entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ALL);
		return entityManagerFactoryBean;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.hbm2ddl.auto", databaseConfiguration.HIBERNATE_HBM2DDl_AUTO);
		properties.setProperty("hibernate.dialect", databaseConfiguration.HIBERNATE_DIALECT);
		
		return properties;
	}
}
