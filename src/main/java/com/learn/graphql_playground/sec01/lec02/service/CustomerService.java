package com.learn.graphql_playground.sec01.lec02.service;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.learn.graphql_playground.sec01.lec02.dto.AgeRangeFilter;
import com.learn.graphql_playground.sec01.lec02.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
		return customerData;
	}
	
	public Mono<Customer> getCustomerById(Integer customerId) {
		return customerData.filter(customer -> customer.id().equals(customerId)).next();
	}
	
	public Flux<Customer> getCustomersMatchesName(String customerName) {
		return customerData.filter(customer -> customer.name().contains(customerName));
	}
	
	public Flux<Customer> getCustomersByAgeRange(AgeRangeFilter ageRange) {
		return customerData.filter(customer -> customer.age() >= ageRange.minAge() && customer.age() <= ageRange.maxAge())
				.sort(Comparator.comparing(Customer::age).reversed());
//				.sort((c1, c2) -> c2.age() - c1.age());
	}
}
