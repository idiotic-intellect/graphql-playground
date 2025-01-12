package com.learn.graphql_playground.lec15.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.graphql.execution.ErrorType;

import com.learn.graphql_playground.lec15.dto.CustomerDto;

import reactor.core.publisher.Mono;

public class ApplicationErrors {

	public static <T>Mono<T> noSuchUser(Integer id) {
		return Mono.error(new ApplicationException(
				ErrorType.BAD_REQUEST,
				"Some issue internally id  missing",
				Map.of("customerId", id + " - not present", "TimeStamp", LocalDateTime.now())
				));
	}
	
	public static <T>Mono<T> ageRestriction(CustomerDto customerDto) {
		return Mono.error(new ApplicationException(
				ErrorType.FORBIDDEN,
				"Adult(age 18+) only can create account",
				Map.of("reason","Customer " + customerDto.getName() + " is restricted due to under age", "TimeStamp", LocalDateTime.now())
				));
	}
}
