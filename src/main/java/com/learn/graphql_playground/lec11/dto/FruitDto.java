package com.learn.graphql_playground.lec11.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class FruitDto {
	private String description;
	private LocalDate expiryDate;

}
