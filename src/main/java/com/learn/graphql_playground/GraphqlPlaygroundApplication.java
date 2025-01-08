package com.learn.graphql_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.learn.graphql_playground.sec01.lec05")
public class GraphqlPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlPlaygroundApplication.class, args);
	}

}