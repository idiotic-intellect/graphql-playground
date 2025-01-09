package com.learn.graphql_playground.lec09.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AllTypes(UUID id, Integer height, Float temperature, String city, Boolean isValid, Long distance, Byte ageInYears,
		Short ageInMonths, BigDecimal bigDecimal, BigInteger bigInteger, LocalDate date, LocalTime time, OffsetDateTime dateTime,
		Car car) {
}



/*
type AllTypes {
id: ID
height: Int
temperature: Float
city: String
isValid: Boolean
distance: Long
ageInYears: Byte
ageInMonths: Short
bigDecimal: BigDecimal
bigInteger: BigInteger
date: Date
time: LocalTime
dateTime: DateTime
car: Car
}
*/