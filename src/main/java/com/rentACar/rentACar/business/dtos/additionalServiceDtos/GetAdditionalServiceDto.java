package com.rentACar.rentACar.business.dtos.additionalServiceDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionalServiceDto {
	
    private int additionalServiceId;
	
	private String additionalServiceName;
	
	private double additionalServiceDailyPrice;
	

}
