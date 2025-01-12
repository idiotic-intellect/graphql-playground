package com.learn.graphql_playground.lec15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.learn.graphql_playground.lec15.dto.CustomerDto;

import graphql.schema.TypeResolver;

@Configuration
public class TypeResolverConfig {

	@Bean
	RuntimeWiringConfigurer configurer(TypeResolver resolver) {
		return config -> config.type("CustomerResponse", builder -> builder.typeResolver(resolver));
	}
	
	@Bean
	TypeResolver typeResolver() {
		ClassNameTypeResolver resolver = new ClassNameTypeResolver();
		resolver.addMapping(CustomerDto.class, "Customer");
		return resolver;
	}
}
