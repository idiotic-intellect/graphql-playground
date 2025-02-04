package com.learn.graphql_playground.lec09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class ScalarConfig {

	@Bean
	RuntimeWiringConfigurer configurer() {
		return config -> config
				.scalar(ExtendedScalars.GraphQLLong)
				.scalar(ExtendedScalars.GraphQLByte)
				.scalar(ExtendedScalars.GraphQLShort)
				.scalar(ExtendedScalars.GraphQLBigDecimal)
				.scalar(ExtendedScalars.GraphQLBigInteger)
				.scalar(ExtendedScalars.Date)
				.scalar(ExtendedScalars.LocalTime)
				.scalar(ExtendedScalars.DateTime)
				.scalar(ExtendedScalars.Object);
	}
	
	/*
	 scalar Long
	scalar Byte
	scalar Short
	scalar BigDecimal
	scalar BigInteger
	scalar Date
	scalar LocalTime
	scalar DateTime
	scalar Object
	 */
}
