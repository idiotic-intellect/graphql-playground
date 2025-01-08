package com.learn.graphql_playground.sec01.lec04.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.sec01.lec04.dto.Customer;
import com.learn.graphql_playground.sec01.lec04.dto.CustomerOrderDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
public class OrderService {

	private Map<String, List<CustomerOrderDto>> orderMap = Map.of(
			"Ravi", List.of(
					new CustomerOrderDto(UUID.randomUUID(), "Ravi-product-1"),
					new CustomerOrderDto(UUID.randomUUID(), "Ravi-product-2")
					),
			"Rag", List.of(
					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-1"),
					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-2"),
					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-3")
					)
			
			);
	
	public Flux<CustomerOrderDto> getOrdersByCustomerName(String customerName) {
		return Flux.fromIterable(orderMap.getOrDefault(customerName, Collections.emptyList()));
	}
	
	public Flux<List<CustomerOrderDto>> getOrdersByCustomerNames(List<String> customerNames) {
		return Flux.fromIterable(customerNames)
				.flatMapSequential(customerName -> fetchOrders(customerName).defaultIfEmpty(Collections.emptyList()));
//				.map(customerName -> orderMap.getOrDefault(customerName, Collections.emptyList()));
	}
	
	private Mono<List<CustomerOrderDto>> fetchOrders(String name) {
		return Mono.justOrEmpty(orderMap.get(name))
				.delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(0, 500)));
	}
	
	public Mono<Map<Customer,List<CustomerOrderDto>>> fetchOrdersasMap(List<Customer> customers) {
		return Flux.fromIterable(customers)
				.map(customer -> Tuples.of(customer, orderMap.getOrDefault(customer.name(), Collections.emptyList())))
				.collectMap(Tuple2::getT1, Tuple2::getT2);
	}
	
	public Flux<String> getCombinedData(List<Customer> customerList) {
		return Flux.fromIterable(customerList)
				.map(customer -> customer.id()+"-"+customer.name()
				+"-"+customer.age()+"-"+customer.city());
		
//		return Mono.just(customer.id()+"-"+customer.name()
//				+"-"+customer.age()+"-"+customer.city());
	}
}
