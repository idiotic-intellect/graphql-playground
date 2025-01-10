package com.learn.graphql_playground.lec11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.learn.graphql_playground.lec11.dto.Book;
import com.learn.graphql_playground.lec11.dto.Electronics;
import com.learn.graphql_playground.lec11.dto.FruitDto;
import com.learn.graphql_playground.lec11.dto.Location;

import graphql.schema.TypeResolver;

@Configuration
public class TypeResolverConfig {

	@Bean
	RuntimeWiringConfigurer configurer(TypeResolver resolver) {
		return config -> config.type("Result", builder -> builder.typeResolver(resolver));
	}
	
	@Bean
	TypeResolver typeResolver() {
		ClassNameTypeResolver resolver = new ClassNameTypeResolver();
		resolver.addMapping(FruitDto.class, "Fruit");
		resolver.addMapping(Book.class, "Book");
		resolver.addMapping(Electronics.class, "Electronics");
		resolver.addMapping(Location.class, "Location");
		return resolver;
	}
}
