package com.learn.graphql_playground.sec01.lec01;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class PlaygroundController {

	@QueryMapping("sayHello")
	public Mono<String> helloWorld() {
		return Mono.just("Hello World").delayElement(Duration.ofMillis(800));
	}
	
	@QueryMapping("sayHelloTo")
	public Mono<String> welcomeMessage(@Argument("name") String personName) {
//		return Mono.just("Hello " + personName + "!!!");
		return Mono.fromSupplier(() -> "Hello " + personName + "!!!").delayElement(Duration.ofMillis(900));
	}
	
	@QueryMapping("random")
	public Mono<Integer> returnNum() {
		return Mono.just(ThreadLocalRandom.current().nextInt(1, 100)).delayElement(Duration.ofMillis(1000));
	}
}
