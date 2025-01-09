package com.learn.graphql_playground.lec07.service;

import java.util.Collections;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec07.dto.CustomerWithOrder;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerOrderDataFetcher implements DataFetcher<Flux<CustomerWithOrder>>{

	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderService orderService;
	
	@Override
	public Flux<CustomerWithOrder> get(DataFetchingEnvironment environment) throws Exception {
		var includeOrders = environment.getSelectionSet().contains("orders");
		return this.customerService.getAllCustomers()
				.map(customer -> CustomerWithOrder.create(customer.id(), customer.name(), customer.age(), customer.city(),
						Collections.emptyList()))
				.transform(this.updateOrdersTransformer(includeOrders));
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
