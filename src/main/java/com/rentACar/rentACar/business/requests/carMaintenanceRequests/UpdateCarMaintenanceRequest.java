package com.rentACar.rentACar.business.requests.carMaintenanceRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	
	@NotNull
	@Positive
	private int carMaintenanceId;
	
	@NotNull
	@Size(min=2,max=255)
	private String carMaintenanceDescription;
	
	private LocalDate carMaintenanceReturnDate;
	
	@NotNull
	@Positive
	@JsonProperty(value = "carId")
	private int Car_CarId;
}
