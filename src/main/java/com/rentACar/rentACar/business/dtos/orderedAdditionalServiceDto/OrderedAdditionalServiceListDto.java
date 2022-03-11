package com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {
	
	private int orderedAdditionalServiceId;
	
	private int rentedCar_RentedCarId;
	
	private int additionalServiceId;
}
