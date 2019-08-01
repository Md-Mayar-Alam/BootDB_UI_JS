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
		
		
		/*
		 * hibernate.hbm2ddl.auto Automatically validates or exports schema DDL to the database when the SessionFactory is created. 
		 * With create-drop, the database schema will be dropped when the SessionFactory is closed explicitly.
			e.g. validate | update | create | create-drop
			
			So the list of possible options are,
			
			validate: validate the schema, makes no changes to the database.
			update: update the schema.
			create: creates the schema, destroying previous data.
			create-drop: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
		 */
		properties.setProperty("hibernate.hbm2ddl.auto", databaseConfiguration.HIBERNATE_HBM2DDl_AUTO);
		
		/*
		 * The dialect specifies the type of database used in hibernate so that hibernate generate appropriate type of SQL statements. 
		 * For connecting any hibernate application with the database, it is required to provide the configuration of SQL dialect.
		 */
		properties.setProperty("hibernate.dialect", databaseConfiguration.HIBERNATE_DIALECT);
		
		return properties;
	}
}
