package com.learn.graphql_playground.lec15.exceptions;

import java.util.Map;

import org.springframework.graphql.execution.ErrorType;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ErrorType errorType;
	private final String message;
	private final Map<String, Object> extensions;
	
	
	public ApplicationException(ErrorType errorType, String message, Map<String, Object> extensions) {
		super(message);
		this.errorType = errorType;
		this.message = message;
		this.extensions = extensions;
	}


	public ErrorType getErrorType() {
		return errorType;
	}


	public String getMessage() {
		return message;
	}


	public Map<String, Object> getExtensions() {
		return extensions;
	}
	
}
