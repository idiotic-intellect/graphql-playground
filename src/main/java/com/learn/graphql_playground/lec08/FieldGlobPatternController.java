package com.learn.graphql_playground.lec08;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import graphql.schema.DataFetchingFieldSelectionSet;

@Controller
public class FieldGlobPatternController {

	@QueryMapping
	public Object level1(DataFetchingFieldSelectionSet selectionSet) {
		
		System.out.println( selectionSet.contains("level2"));
		System.out.println( selectionSet.contains("level2/level3"));
		System.out.println( selectionSet.contains("**/level4"));
		System.out.println( selectionSet.contains("level2/**/level5"));
		
		return null;
	}
}
