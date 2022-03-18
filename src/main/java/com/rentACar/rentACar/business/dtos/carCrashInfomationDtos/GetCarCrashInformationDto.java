package com.rentACar.rentACar.business.dtos.carCrashInfomationDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarCrashInformationDto {

	private int carCrashInformationId;

	private String carCrashInformationDetails;

	private int carId;

}
