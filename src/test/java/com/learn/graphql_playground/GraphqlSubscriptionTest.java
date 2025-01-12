package com.learn.graphql_playground;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import com.learn.graphql_playground.lec14.dto.Action;
import com.learn.graphql_playground.lec14.dto.CustomerEvent;

import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "lec=lec14")
@TestMethodOrder(OrderAnnotation.class)
public class GraphqlSubscriptionTest {

	private static final String WS_PATH =  "ws://localhost:8080/graphql";
	
	@Autowired
	private HttpGraphQlTester tester;
	
	@Test
	@Order(1)
	public void subscriptionTest() {
		var webSocketClient =  WebSocketGraphQlTester.builder(WS_PATH, new ReactorNettyWebSocketClient()).build();
		
		this.tester.documentName("crud-operations")
		.variable("id", 4)
		.operationName("DeleteCustomer")
		.executeAndVerify();
		
		webSocketClient.documentName("subscription-test")
			.executeSubscription().toFlux("customerEvents", CustomerEvent.class)
			.take(1)
			.as(StepVerifier::create)
			.consumeNextWith(e -> Assertions.assertThat(e.getAction()).isEqualTo(Action.DELETED))
			.verifyComplete();

			
			
	}
}
