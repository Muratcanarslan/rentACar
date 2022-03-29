package com.rentACar.rentACar.business.requests.carMaintenanceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarMaintenanceRequest {

	@NotNull
	@Positive
	private int carMaintenanceId;
}
