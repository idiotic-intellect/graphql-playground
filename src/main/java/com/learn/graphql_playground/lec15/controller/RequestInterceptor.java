package com.learn.graphql_playground.lec15.controller;

import java.util.Map;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class RequestInterceptor implements WebGraphQlInterceptor{
//	RSocketGraphQlInterceptor for web socket subscription
	@Override
	public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
		
		var headers = request.getHeaders().getOrEmpty("caller-id");
		var callerId = headers.isEmpty() ? "" : headers.get(0);
		request.configureExecutionInput((ei, b) -> b.graphQLContext(Map.of("caller-id", callerId)).build());
		return chain.next(request);
	}

}
