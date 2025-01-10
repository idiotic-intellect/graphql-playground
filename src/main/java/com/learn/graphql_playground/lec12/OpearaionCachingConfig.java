package com.learn.graphql_playground.lec12;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;

@Configuration
public class OpearaionCachingConfig {
	
	@Bean
	GraphQlSourceBuilderCustomizer sourceBuilderCustomizer(PreparsedDocumentProvider provider) {
		return customizer -> customizer.configureGraphQl(builder -> builder.preparsedDocumentProvider(provider));
	}

	@Bean
	PreparsedDocumentProvider provider() {
		
		Map<String, PreparsedDocumentEntry> map = new ConcurrentHashMap<>();
		return new PreparsedDocumentProvider() {
			
			@Override
			public CompletableFuture<PreparsedDocumentEntry> getDocumentAsync(ExecutionInput executionInput,
					Function<ExecutionInput, PreparsedDocumentEntry> parseAndValidateFunction) {
				
				var documentEntry = map.computeIfAbsent(executionInput.getQuery(), val -> {
						System.out.println("Not Found : " + val);
						var r = parseAndValidateFunction.apply(executionInput);
						System.out.println("Caching : " + r);
						return r;
					});
				return CompletableFuture.completedFuture(documentEntry);
			}
		};
	}
}
