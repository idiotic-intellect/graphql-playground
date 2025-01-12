package com.learn.graphql_playground.lec16.dto;

import java.util.Collections;
import java.util.List;

import org.springframework.graphql.ResponseError;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GenericResponse<T> {

	private final T data;
	private final List<ResponseError> errors;
	private final boolean dataPresent;
	
	public GenericResponse(T data) {
		this.data = data;
		this.errors = Collections.emptyList();
		this.dataPresent = true;
	}
	
	public GenericResponse(List<ResponseError> errors) {
		this.data = null;
		this.errors =  errors;
		this.dataPresent = false;
	}
}
