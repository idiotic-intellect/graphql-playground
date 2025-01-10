package com.learn.graphql_playground.lec11;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.learn.graphql_playground.lec11.dto.Book;
import com.learn.graphql_playground.lec11.dto.Electronics;
import com.learn.graphql_playground.lec11.dto.FruitDto;
import com.learn.graphql_playground.lec11.dto.Location;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class SearchEngineController {

	@QueryMapping("search")
	public Flux<Object> getSearchResults() {
		
		List<Object> list = List.of(
				Electronics.create("Mac book", 600, "APPLE"),
				FruitDto.create("banana", LocalDate.now().plusDays(3)),
				Electronics.create("Galaxy F23 5G", 400, "SAMSUNG"),
				Book.create("Java", "James"),
				FruitDto.create("apple", LocalDate.now().plusDays(5)),
				Location.create("1st street", "Trichy")
				);
		
		return Mono.fromSupplier(() -> new ArrayList<>(list))
				.doOnNext(Collections::shuffle)
				.flatMapIterable(Function.identity())
				.take(ThreadLocalRandom.current().nextInt(0, list.size()));
	}
}
