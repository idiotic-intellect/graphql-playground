package com.learn.graphql_playground.sec01.lec05;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.sec01.lec05.dto.Account;
import com.learn.graphql_playground.sec01.lec05.dto.Customer;


import reactor.core.publisher.Mono;

@Controller
public class AccountController {

	@SchemaMapping(typeName = "Customer", field = "account")
	public Mono<Account> getAccountData(Customer customer) {
		return Mono.just(
				new Account(
						UUID.randomUUID(),
						ThreadLocalRandom.current().nextInt(6000, 100000),
						ThreadLocalRandom.current().nextBoolean() ? "SAVINGS" : "CURRENT"
				)
				);
	}
}
