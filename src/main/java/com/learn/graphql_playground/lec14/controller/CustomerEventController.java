package com.learn.graphql_playground.lec14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec14.dto.CustomerEvent;
import com.learn.graphql_playground.lec14.service.CustomerEventService;

import reactor.core.publisher.Flux;

@Controller
public class CustomerEventController {

	@Autowired
	private CustomerEventService eventService;
	
	@SubscriptionMapping("customerEvents")
	public Flux<CustomerEvent> subscribeCustomerEvents() {
		return this.eventService.subscribeCustomerEvents();
	}
}
