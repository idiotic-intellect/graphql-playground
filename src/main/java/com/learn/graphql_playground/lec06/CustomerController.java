package com.learn.graphql_playground.lec06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec06.dto.CustomerWithOrder;
import com.learn.graphql_playground.lec06.service.CustomerOrderDataFetcher;

import graphql.schema.DataFetchingFieldSelectionSet;
import reactor.core.publisher.Flux;

@Controller
public class CustomerController {

	@Autowired
	CustomerOrderDataFetcher dataFetcher;
	
//	@QueryMapping("customers")
	@SchemaMapping(typeName = "Query", field = "customers")
	public Flux<CustomerWithOrder> getAllCustomers(DataFetchingFieldSelectionSet selectionSet) {
		return this.dataFetcher.customerOrders(selectionSet);
	}
	
//	@SchemaMapping(typeName = "Customer",field = "orders")
//	public Flux<CustomerOrderDto> getOrdersByCustomerName(Customer customer) {
//		return this.orderService.getOrdersByCustomerName(customer.name());
//	}

}
