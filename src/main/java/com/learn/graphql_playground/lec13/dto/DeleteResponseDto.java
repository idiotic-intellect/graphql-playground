package com.learn.graphql_playground.lec13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class DeleteResponseDto {
	
	private Integer id;
	
	private Status status;

}
