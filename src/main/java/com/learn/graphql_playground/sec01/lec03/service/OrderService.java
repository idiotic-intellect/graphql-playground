package com.learn.graphql_playground.sec01.lec03.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.sec01.lec03.dto.CustomerOrderDto;

import reactor.core.publisher.Flux;

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
	
	public Flux<CustomerOrderDto> getOrdersByCustomerName(String customerName, Integer limit) {
		return Flux.fromIterable(orderMap.getOrDefault(customerName, Collections.emptyList())).take(limit);
	}
}
