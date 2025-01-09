package com.learn.graphql_playground.lec09;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec09.dto.AllTypes;
import com.learn.graphql_playground.lec09.dto.Car;
import com.learn.graphql_playground.lec09.dto.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ScalarController {
	
	private AllTypes allTypes = new AllTypes(
			UUID.randomUUID(),
			10,
			10.12f,
			"Trichy",
			false,
			120000000000L,
			Byte.valueOf("12"),
			Short.valueOf("100"),
			BigDecimal.valueOf(123456789.123456789),
			BigInteger.valueOf(123456789),
			LocalDate.now(),
			LocalTime.now(),
			OffsetDateTime.now(),
			Car.BMW
			);

	@QueryMapping("get")
	public Mono<AllTypes> getAllData() {
		return Mono.just(allTypes);
	}
	
	@QueryMapping("products")
	public Flux<Product> getAllProduct() {
		return Flux.just(
				new Product("banana", Map.of(
						"expiry data", "01/01/2025",
						"color", "yellow"
						)),
				new Product("mac", Map.of(
						"cpu", "8 core",
						"RAM", "32g"
						))
				);
	}
}
