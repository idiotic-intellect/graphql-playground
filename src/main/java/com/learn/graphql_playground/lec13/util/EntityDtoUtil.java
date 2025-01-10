package com.learn.graphql_playground.lec13.util;

import org.springframework.beans.BeanUtils;

import com.learn.graphql_playground.lec13.dto.CustomerDto;
import com.learn.graphql_playground.lec13.entity.Customer;

public class EntityDtoUtil {

	public static Customer toEntity(CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}
	
	public static Customer toEntity(Integer id, CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		customer.setId(id);
		return customer;
	}
	
	public static CustomerDto toDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}
}
