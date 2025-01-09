package com.learn.graphql_playground.lec06.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec06.dto.CustomerOrderDto;

import reactor.core.publisher.Flux;

@Service
public class OrderService {

	private Map<String, List<CustomerOrderDto>> orderMap = Map.of(
			"Ravi", List.of(
					new CustomerOrderDto(UUID.randomUUID(), "Ravi-product-1"),
					new CustomerOrderDto(UUID.randomUUID(), "Ravi-product-2")
					)
//			,
//			"Rag", List.of(
//					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-1"),
//					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-2"),
//					new CustomerOrderDto(UUID.randomUUID(), "Rag-product-3")
//					)
			
			);
	
	public Flux<CustomerOrderDto> getOrdersByCustomerName(String customerName) {
		return Flux.fromIterable(orderMap.getOrDefault(customerName, Collections.emptyList()))
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(order -> print("Orders of " + customerName));
	}
	
	private void print(String msg) {
		System.out.println(LocalDateTime.now().toString() +" : "+ msg);
	}
}
