package com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {
	
	private int orderedAdditionalServiceId;
	
	@JsonProperty(value = "rentedCarId")
	private int rentedCar_RentedCarId;
	
	private int additionalServiceId;
}
