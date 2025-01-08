package com.learn.graphql_playground.sec01.lec02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.sec01.lec02.dto.AgeRangeFilter;
import com.learn.graphql_playground.sec01.lec02.dto.Customer;
import com.learn.graphql_playground.sec01.lec02.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@QueryMapping("customers")
	public Flux<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@QueryMapping("customerById")
	public Mono<Customer> getCustomerById(@Argument("id") Integer customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	@QueryMapping("customerNameContains")
	public Flux<Customer> getCustomersMatchesName(@Argument("name") String customerName) {
		return customerService.getCustomersMatchesName(customerName);
	}
	
	@QueryMapping("customerByAgeRange")
	public Flux<Customer> getCustomersByAgeRange(@Argument("filter") AgeRangeFilter ageRange) {
		return customerService.getCustomersByAgeRange(ageRange);
	}
}
