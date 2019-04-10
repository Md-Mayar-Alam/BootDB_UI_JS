package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/db/database.properties")
public class DatabaseConfiguration {
	
	@Value("${spring.datasource.driver}")
	public String driver;
	
	@Value("${spring.datasource.url}")
	public String url;
	
	@Value("${spring.datasource.username}")
	public String username;
	
	@Value("${spring.datasource.password}")
	public String password;
	
	@Value("${spring.jpa.show-sql}")
	public String HIBERNATE_SHOW_SQL;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	public String HIBERNATE_HBM2DDl_AUTO;
	
	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String HIBERNATE_DIALECT;
}
