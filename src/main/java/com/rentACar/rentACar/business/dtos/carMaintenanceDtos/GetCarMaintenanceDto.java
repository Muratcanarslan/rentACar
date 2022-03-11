package com.rentACar.rentACar.business.dtos.carMaintenanceDtos;

import java.time.LocalDate;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceDto {
	
	private int carMaintenanceId;
	
	private String carMaintenanceDescription;
	
	private LocalDate carMaintenanceReturnDate;
	
	private int carId;
}
