package com.rentACar.rentACar.business.requests.colorRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {
	
	@NotNull
	@Positive
	private int colorId;
	
	@NotNull
	@Size(min = 2, max = 255)
	private String colorName;
}
