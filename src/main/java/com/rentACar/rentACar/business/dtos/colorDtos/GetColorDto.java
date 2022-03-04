package com.rentACar.rentACar.business.dtos.colorDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetColorDto {
	
    private int colorId;
	
	private String colorName;
}
