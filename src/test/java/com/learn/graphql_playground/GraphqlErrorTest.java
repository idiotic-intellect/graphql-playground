package com.learn.graphql_playground;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import com.learn.graphql_playground.lec14.dto.CustomerDto;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "lec=lec15")
@TestMethodOrder(OrderAnnotation.class)
public class GraphqlErrorTest {

	@Autowired
	private HttpGraphQlTester tester;
	
	@Test
	@Order(1)
	public void createNewCustomer() {
		this.tester
		.mutate().header("caller-id", "Amber").build()
		.documentName("crud-operations")
		.variable("customer", CustomerDto.create(null, "Ravi Sura", 2, "Berlin"))
		.operationName("CreateCustomer").execute()
		.errors().satisfy(list -> {
			Assertions.assertThat(list).hasSize(1);
			Assertions.assertThat(list.get(0).getErrorType()).isEqualTo(ErrorType.FORBIDDEN);
		})
		.path("response.id").pathDoesNotExist()
		.path("response").valueIsNull();
	}
}
