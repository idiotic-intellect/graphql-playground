package com.learn.graphql_playground.lec05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec05.dto.Customer;
import com.learn.graphql_playground.lec05.service.CustomerService;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@QueryMapping("customers")
	public Flux<Customer> getAllCustomers(DataFetchingEnvironment env) {
		System.out.println("Customer - " + env.getDocument());
		System.out.println("Customer - " + env.getOperationDefinition());
		return customerService.getAllCustomers();
	}
	
}
