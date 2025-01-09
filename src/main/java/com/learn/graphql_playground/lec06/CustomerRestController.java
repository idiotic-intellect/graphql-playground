package com.learn.graphql_playground.lec06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.graphql_playground.lec06.dto.CustomerWithOrder;
import com.learn.graphql_playground.lec06.service.CustomerService;
import com.learn.graphql_playground.lec06.service.OrderService;

import reactor.core.publisher.Flux;

@RestController
public class CustomerRestController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("customers")
	public Flux<CustomerWithOrder> customerOrders() {
		return this.customerService.getAllCustomers()
				.flatMap(customer -> this.orderService.getOrdersByCustomerName(customer.name())
						.collectList()
						.map(orders -> 
						CustomerWithOrder.create(customer.id(), customer.name(), customer.age(), customer.city(), orders)
						));
	}
}
