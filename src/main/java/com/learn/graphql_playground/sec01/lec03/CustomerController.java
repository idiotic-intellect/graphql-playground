package com.learn.graphql_playground.sec01.lec03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.sec01.lec03.dto.Customer;
import com.learn.graphql_playground.sec01.lec03.dto.CustomerOrderDto;
import com.learn.graphql_playground.sec01.lec03.service.CustomerService;
import com.learn.graphql_playground.sec01.lec03.service.OrderService;

import reactor.core.publisher.Flux;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderService orderService;
	
//	@QueryMapping("customers")
	@SchemaMapping(typeName = "Query", field = "customers")
	public Flux<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@SchemaMapping(typeName = "Customer",field = "orders")
	public Flux<CustomerOrderDto> getOrdersByCustomerName(Customer customer, @Argument("count") Integer limit) {
		return this.orderService.getOrdersByCustomerName(customer.name(), limit);
	}

}
