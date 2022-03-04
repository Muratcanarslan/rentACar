package com.rentACar.rentACar.business.dtos.brandDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandDto {
	
	private int brandId;
	private String brandName;
}
