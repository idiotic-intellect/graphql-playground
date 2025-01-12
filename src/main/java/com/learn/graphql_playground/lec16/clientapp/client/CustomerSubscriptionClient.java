package com.learn.graphql_playground.lec16.clientapp.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import com.learn.graphql_playground.lec16.dto.CustomerEvent;

import reactor.core.publisher.Flux;

@Service
public class CustomerSubscriptionClient {

	private final WebSocketGraphQlClient client;
	
	public CustomerSubscriptionClient(@Value("${customer.events.subscription.url}") String baseUrl) {
		this.client = WebSocketGraphQlClient.builder(baseUrl, new ReactorNettyWebSocketClient()).build();
	}
	
	public Flux<CustomerEvent> customerEvents() {
		var doc = """
					subscription {
						customerEvents {
							id
							action
						}
					}
				""";
		return this.client.document(doc).retrieveSubscription("customerEvents").toEntity(CustomerEvent.class);
	}
}
