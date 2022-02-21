package com.rentACar.rentACar.business.dtos;

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
