package com.rentACar.rentACar.business.dtos.carMaintenanceDtos;

import java.util.Date;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceDto {
	
	private int carMaintenanceId;
	
	private String carMaintenanceDescription;
	
	private Date carMaintenanceReturnDate;
	
	private int carId;
}
