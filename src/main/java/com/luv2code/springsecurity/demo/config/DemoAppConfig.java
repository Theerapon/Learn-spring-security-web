package com.luv2code.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {

	@Autowired
	private Environment environment;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// define a bean for ViewResolver

	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// define a bean for our security data source
	
	@Bean
	public DataSource secuiryDataSource() {
		
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		
		try {
			comboPooledDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		
		comboPooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
		comboPooledDataSource.setUser(environment.getProperty("jdbc.user"));
		comboPooledDataSource.setPassword(environment.getProperty("jdbc.password"));
		
		// set connection pool props
		comboPooledDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		comboPooledDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		comboPooledDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		comboPooledDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return comboPooledDataSource;
	}
	
	private int getIntProperty(String propName) {
		
		String propValueString = environment.getProperty(propName);
		return Integer.parseInt(propValueString);
		
	}
	
}









