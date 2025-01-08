package com.learn.graphql_playground.sec01.lec05.dto;

import java.util.UUID;

public record Account(UUID id, Integer amount, String accountType) {

}
