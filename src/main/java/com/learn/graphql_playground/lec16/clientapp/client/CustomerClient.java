package com.learn.graphql_playground.lec16.clientapp.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec16.dto.CustomerDto;
import com.learn.graphql_playground.lec16.dto.CustomerNotFound;
import com.learn.graphql_playground.lec16.dto.CustomerResponse;
import com.learn.graphql_playground.lec16.dto.DeleteResponseDto;
import com.learn.graphql_playground.lec16.dto.GenericResponse;

import reactor.core.publisher.Mono;

@Service
public class CustomerClient {

	private final HttpGraphQlClient client;

	public CustomerClient(@Value("${customer.service.url}") String baseUrl) {
		super();
		this.client = HttpGraphQlClient.builder()
						.webClient(cli -> cli.baseUrl(baseUrl))
						.build();
	}
	
	public Mono<ClientGraphQlResponse> rawQuery(String query) {
		return this.client.document(query).execute();
	}
	
	@SuppressWarnings("rawtypes")
	public Mono<GenericResponse> getCustomerById1(Integer id) {
		return this.client.documentName("customer-by-id")
				.variable("id", id)
				.execute()
				.map(customer -> {
					var field = customer.field("customerById");
					return (Objects.nonNull(field.getValue())) ? new GenericResponse<>(field.toEntity(CustomerDto.class)) :
						new GenericResponse<>(customer.getErrors());
				});
//				.retrieve("customerById")
//				.toEntity(CustomerDto.class);
	}
	
	public Mono<CustomerResponse> getCustomerByIdWithUnion(Integer id) {
		return this.client
//				.mutate().header("caller-id", "GT").build()
				.mutate().webClient(b -> b.defaultHeader("caller-id", "GIT")).build()
				.documentName("customer-by-id")
				.variable("id", id)
				.execute()
				.map(customer -> {
					var field = customer.field("customerById");
					var isCustomer = "Customer".equalsIgnoreCase(customer.field("customerById.type").getValue().toString());
					return (isCustomer) ? field.toEntity(CustomerDto.class) : field.toEntity(CustomerNotFound.class);
						
				});
//				.retrieve("customerById")
//				.toEntity(CustomerDto.class);
	}
	
	public Mono<List<CustomerDto>> getAllCustomers() {
		return this.crud("GetAll", Collections.emptyMap(), ParameterizedTypeReference.forType(List.class));
	}
	
	public Mono<CustomerDto> getCustomerById(Integer id) {
		return this.crud("GetCustomerById", Map.of("id", id), ParameterizedTypeReference.forType(CustomerDto.class));
	}
	
	public Mono<CustomerDto> createNewCustomer(CustomerDto customer) {
		return this.crud("CreateCustomer", Map.of("customer", customer), ParameterizedTypeReference.forType(CustomerDto.class));
	}
	
	public Mono<CustomerDto> updateExistingCustomer(Integer id, CustomerDto customer) {
		return this.crud("UpdateCustomer", Map.of("id", id,"customer", customer), ParameterizedTypeReference.forType(CustomerDto.class));
	}
	
	public Mono<CustomerDto> deleteCustomerById(Integer id) {
		return this.crud("DeleteCustomer", Map.of("id", id), ParameterizedTypeReference.forType(DeleteResponseDto.class));
	}
	
	private <T> Mono<T> crud(String operationName, Map<String, Object> variables, ParameterizedTypeReference<T> type) {
		return this.client.documentName("crud-operations").operationName(operationName)
				.variables(variables)
				.retrieve("response").toEntity(type);
		
	}
	
}
