package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	//	Java configuration to add viewResolver
	/*@Bean
	public InternalResourceViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}*/
	
	//Java configuration to add messageSource
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		/*messageSource.setBasenames(new String[] {"classpath:messages/base_en"});*/
		messageSource.setBasename("classpath:messages/base_en");
		messageSource.setDefaultEncoding("UTF-8");
		System.out.println("Message Source "+messageSource);
		
		return messageSource;
	}
}
