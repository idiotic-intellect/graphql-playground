package com.learn.graphql_playground.lec09.dto;

import java.util.Map;

public record Product(String name, Map<String, String> attributes) {

}
