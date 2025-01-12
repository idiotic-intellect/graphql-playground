package com.learn.graphql_playground.lec14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec14.dto.CustomerDto;
import com.learn.graphql_playground.lec14.dto.DeleteResponseDto;
import com.learn.graphql_playground.lec14.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@QueryMapping("customers")
	public Flux<CustomerDto> getAllCustomers() {
		return this.customerService.getAllCustomers();
	}
	
	@QueryMapping("customerById")
	public Mono<CustomerDto> getCustomerById(@Argument("id") Integer customerId) {
		return this.customerService.getCustomerById(customerId);
	}
	
	@MutationMapping("createCustomer")
	public Mono<CustomerDto> createNewCustomer(@Argument("customer") CustomerDto  newCustomer) {
		return this.customerService.createNewCustomer(newCustomer);
//				.delayElement(Duration.ofSeconds(2));
	}
	
	@MutationMapping("updateCustomer")
	public Mono<CustomerDto> updateExistingCustomer(@Argument("id") Integer customerId, @Argument("customer") CustomerDto  updateCustomer) {
		return this.customerService.updateExistingCustomer(customerId, updateCustomer);
//				.delayElement(Duration.ofSeconds(2));
	}
	
	@MutationMapping("deleteCustomer")
	public Mono<DeleteResponseDto> deleteExistingCustomer(@Argument("id") Integer customerId) {
		return this.customerService.deleteExistingCustomer(customerId);
	}
	
}
 