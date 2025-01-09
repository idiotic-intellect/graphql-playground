package com.learn.graphql_playground.lec07.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.learn.graphql_playground.lec07.service.CustomerOrderDataFetcher;

@Configuration
public class DataFectcherWiringConfig {

	@Autowired
	CustomerOrderDataFetcher dataFetcher;
	
	@Bean
	public RuntimeWiringConfigurer configurer() {
		return config -> config.type("Query", builder -> builder.dataFetcher("customers", dataFetcher));
	}
}
