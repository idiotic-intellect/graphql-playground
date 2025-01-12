package com.learn.graphql_playground.lec15.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec15.dto.CustomerDto;
import com.learn.graphql_playground.lec15.dto.CustomerNotFound;
import com.learn.graphql_playground.lec15.dto.DeleteResponseDto;
import com.learn.graphql_playground.lec15.service.CustomerService;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@QueryMapping("customers")
	public Flux<CustomerDto> getAllCustomers(DataFetchingEnvironment environment) {
		var callerId = environment.getGraphQlContext().getOrDefault("caller-id", "");
		System.out.println("CALLERID: " + callerId);
		return this.customerService.getAllCustomers();
	}
	
	@QueryMapping("customerById")
	public Mono<Object> getCustomerById(@Argument("id") Integer customerId) {
//		throw new RuntimeException("Some issue on server");
		return this.customerService.getCustomerById(customerId)
				.cast(Object.class)
				.switchIfEmpty(Mono.just(CustomerNotFound.create(customerId, "Customer not present")));
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
 