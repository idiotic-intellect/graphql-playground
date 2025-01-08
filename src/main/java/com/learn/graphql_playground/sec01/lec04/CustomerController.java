package com.learn.graphql_playground.sec01.lec04;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.sec01.lec04.dto.Customer;
import com.learn.graphql_playground.sec01.lec04.dto.CustomerOrderDto;
import com.learn.graphql_playground.sec01.lec04.service.CustomerService;
import com.learn.graphql_playground.sec01.lec04.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	
//	@BatchMapping(typeName = "Customer",field = "orders")
//	public Flux<List<CustomerOrderDto>> getOrdersByCustomerName(List<Customer> customerList) {
//		return this.orderService.getOrdersByCustomerNames(
//				customerList.stream()
//				.map(Customer::name)
//				.collect(Collectors.toList())
//				);
//	}
	
	@BatchMapping(typeName = "Customer",field = "orders")
	public Mono<Map<Customer,List<CustomerOrderDto>>> getOrdersByCustomerName(List<Customer> customerList) {
		return this.orderService.fetchOrdersasMap(customerList);
	}
	
	@BatchMapping(typeName = "Customer", field = "combinedData")
	public Flux<String> getCombinedData(List<Customer> customerList) {
		return this.orderService.getCombinedData(customerList);
	}

}
