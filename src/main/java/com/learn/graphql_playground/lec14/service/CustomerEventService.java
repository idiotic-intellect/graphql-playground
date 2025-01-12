package com.learn.graphql_playground.lec14.service;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec14.dto.CustomerEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class CustomerEventService {

	private final Sinks.Many<CustomerEvent> customerEventSource = Sinks.many().multicast().onBackpressureBuffer();
	private final Flux<CustomerEvent> customerSubscribeSource = customerEventSource.asFlux().cache(0);
	
	public Mono<Void> emitCustomerEvents(CustomerEvent event) {
		this.customerEventSource.tryEmitNext(event);
		return Mono.empty();
	}
	
	public Flux<CustomerEvent> subscribeCustomerEvents() {
		return this.customerSubscribeSource;
	}
}
