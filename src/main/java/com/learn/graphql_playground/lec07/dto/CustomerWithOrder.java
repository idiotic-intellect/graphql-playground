package com.learn.graphql_playground.lec07.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerWithOrder {

	private Integer id;
	private String name;
	private Integer age;
	private String city;
	private List<CustomerOrderDto> orders;
}
