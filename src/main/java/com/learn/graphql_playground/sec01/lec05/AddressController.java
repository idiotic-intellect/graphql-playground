package com.learn.graphql_playground.sec01.lec05;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.sec01.lec05.dto.Address;
import com.learn.graphql_playground.sec01.lec05.dto.Customer;

import reactor.core.publisher.Mono;

@Controller
public class AddressController {

	@SchemaMapping(typeName = "Customer", field="address")
	public Mono<Address> getAddress(Customer customer) {
		return Mono.just(
				new Address(customer.name() + " Street", customer.name() + " city")
				);
	}
}
