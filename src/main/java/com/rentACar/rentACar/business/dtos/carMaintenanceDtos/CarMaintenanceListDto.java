package com.rentACar.rentACar.business.dtos.carMaintenanceDtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	
    private int maintenanceId;
	
	private String maintenanceDescription;
	
	private Date maintenanceReturnDate;
	
	private int carId;
}
