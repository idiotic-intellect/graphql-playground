package com.learn.graphql_playground.lec06.service;

import java.util.Collections;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec06.dto.CustomerWithOrder;

import graphql.schema.DataFetchingFieldSelectionSet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerOrderDataFetcher {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderService orderService;
	
	public Flux<CustomerWithOrder> customerOrders(DataFetchingFieldSelectionSet selectionSet) {
		var includeOrders = selectionSet.contains("orders");
		return this.customerService.getAllCustomers()
				.map(customer -> CustomerWithOrder.create(customer.id(), customer.name(), customer.age(), customer.city(),
						Collections.emptyList()))
				.transform(this.updateOrdersTransformer(includeOrders))
				;
	}
	
	private UnaryOperator<Flux<CustomerWithOrder>> updateOrdersTransformer(boolean includeOrders) {
		return includeOrders ? f -> f.flatMapSequential(this::fetchOrders) : f -> f;
	}
	
	private Mono<CustomerWithOrder> fetchOrders(CustomerWithOrder customerWithOrder) {
		return this.orderService.getOrdersByCustomerName(customerWithOrder.getName())
				.collectList()
				.doOnNext(customerWithOrder::setOrders)
				.thenReturn(customerWithOrder);
	}
}
