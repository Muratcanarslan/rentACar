package com.rentACar.rentACar.business.requests.carRequests;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@NotNull
	private double dailyPrice;

	@NotNull
	@Pattern(regexp = "^[0-9]{4}")
	private String modelYear;

	@NotNull
	@Size(min = 2, max = 255)
	private String description;

	@NotNull
	@Min(0)
	private double kilometreInformation;

	@NotNull
	@Positive
	private int brandId;

	@NotNull
	@Positive
	private int colorId;
}
