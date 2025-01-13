package com.learn.graphql_playground;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import com.learn.graphql_playground.lec14.dto.CustomerDto;
import com.learn.graphql_playground.lec14.dto.DeleteResponseDto;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "lec=lec14")
@TestMethodOrder(OrderAnnotation.class)
public class GraphqlCrudTest {

	@Autowired
	private HttpGraphQlTester tester;

	@Test
	@Order(1)
	public void getAllCustomers() {
//		var document = """
//				query {
//					customers {
//						id
//						name
//						age
//						city
//					}
//				}
//				""";
		this.tester.documentName("crud-operations").operationName("GetAll").execute()
					.path("response").entityList(Object.class).hasSize(5)
					.path("response.[0].name").entity(String.class).isEqualTo("Ram");
	}
	
	@Test
	@Order(3)
	public void getCustomerById() {
		this.tester.documentName("crud-operations").variable("id", 6).operationName("GetCustomerById").execute()
		.path("response.id").entity(Integer.class).isEqualTo(6)
		.path("response.name").entity(String.class).matches(name -> name.contains("Sura"))
		.path("response.age").entity(Integer.class).isEqualTo(21);
	}
	
	@Test
	@Order(2)
	public void createNewCustomer() {
		this.tester.documentName("crud-operations")
		.variable("customer", CustomerDto.create(null, "Ravi Sura", 21, "Berlin"))
		.operationName("CreateCustomer").execute()
		.path("response.id").entity(Integer.class).isEqualTo(6)
		.path("response.name").entity(String.class).matches(name -> name.contains("Sura"))
		.path("response.age").entity(Integer.class).matches(age -> age > 18)
		.path("response.city").entity(String.class).isEqualTo("Berlin");
		;
	}
	
	@Test
	@Order(4)
	public void updateExistingCustomer() {
		this.tester.documentName("crud-operations")
		.variables(Map.of("id", 6,"customer", CustomerDto.create(null, "Ravi Surav", 25, "Ohio")))
		.operationName("UpdateCustomer").execute()
		.path("response.id").entity(Integer.class).isEqualTo(6)
		.path("response.name").entity(String.class).matches(name -> name.contains("Sura"))
		.path("response.age").entity(Integer.class).matches(age -> age > 18)
		.path("response.city").entity(String.class).isEqualTo("Ohio")
		.path("response").entity(Object.class).satisfies(System.out::println);
	}
	
	@Test
	@Order(5)
	public void deleteExistingCustomer() {
		this.tester.documentName("crud-operations")
		.variable("id", 4)
		.operationName("DeleteCustomer").execute()
		.path("response.id").entity(Integer.class).isEqualTo(4)
		.path("response.status").entity(String.class).isEqualTo("SUCCESS")
		.path("response").entity(DeleteResponseDto.class).satisfies(res -> {
			Assertions.assertThat(res.getId().equals(3));
		});
	}
}
