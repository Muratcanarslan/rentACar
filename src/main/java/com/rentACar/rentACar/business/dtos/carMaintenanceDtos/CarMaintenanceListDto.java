package com.rentACar.rentACar.business.dtos.carMaintenanceDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	
    private int maintenanceId;
	
	private String maintenanceDescription;
	
	private LocalDate maintenanceReturnDate;
	
	private LocalDate maintenanceDate;
	
	private int carId;
}
