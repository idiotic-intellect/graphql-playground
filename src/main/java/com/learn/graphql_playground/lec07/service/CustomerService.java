package com.learn.graphql_playground.lec07.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec07.dto.Customer;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	private final Flux<Customer> customerData = Flux.just(
			new Customer(1, "Rag", 45, "Trichy"),
			new Customer(2, "Ravi", 35, "Salem"),
			new Customer(3, "Raghu", 25, "Chennai"),
			new Customer(4, "Raghuram", 30, "Tanjore"),
			new Customer(5, "Ravi Shark", 15, "Benagaluru")
			);
	
	public Flux<Customer> getAllCustomers() {
		return customerData.delayElements(Duration.ofSeconds(1))
				.doOnNext(customer -> print("Customer - " + customer.name()));
	}
	
	private void print(String msg) {
		System.out.println(LocalDateTime.now().toString() +" : "+ msg);
	}
}
