package com.learn.graphql_playground.lec12;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class PlaygroundController {

	@QueryMapping("sayHello")
	public Mono<String> welcomeMessage(@Argument("name") String personName) {
//		return Mono.just("Hello " + personName + "!!!");
		return Mono.fromSupplier(() -> "Hello " + personName + "!!!");
	}
	
}
