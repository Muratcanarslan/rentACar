package com.rentACar.rentACar.business.requests.carMaintenanceRequests;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	
	@NotNull
	@Size(min = 2,max = 255)
	private String carMaintenanceDescription;
	
	private Date carMaintenanceReturnDate;
	
	@NotNull
	@Min(0)
	private int carId;
}
