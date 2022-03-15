package com.rentACar.rentACar.business.dtos.corporateCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCorporateCustomerDto {

	private int corporateCustomerId;

	private String email;

	private String password;

	private String companyName;

	private String taxNumber;

}
