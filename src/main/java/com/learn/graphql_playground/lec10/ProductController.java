package com.learn.graphql_playground.lec10;

import java.time.LocalDate;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec10.dto.Book;
import com.learn.graphql_playground.lec10.dto.Electronics;
import com.learn.graphql_playground.lec10.dto.FruitDto;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@QueryMapping("products")
	public Flux<Object> getAllProducts() {
		return Flux.just(
				Electronics.create("Mac book", 600, "APPLE"),
				FruitDto.create("banana", 1, LocalDate.now().plusDays(3)),
				Electronics.create("Galaxy F23 5G", 400, "SAMSUNG"),
				Book.create("Java", 40, "James"),
				FruitDto.create("apple", 2, LocalDate.now().plusDays(5))
				);
	}
}
