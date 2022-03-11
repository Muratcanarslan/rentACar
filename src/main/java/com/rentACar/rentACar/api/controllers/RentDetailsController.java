package com.rentACar.rentACar.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.RentDetailsService;
import com.rentACar.rentACar.business.dtos.rentDetailsDtos.GetRentDetails;
import com.rentACar.rentACar.business.requests.rentDetailsRequests.UpdateRentDetailsRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentDetails")
public class RentDetailsController {
	
	private RentDetailsService rentDetailsService;

	public RentDetailsController(RentDetailsService rentDetailsService) {
		super();
		this.rentDetailsService = rentDetailsService;
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateRentDetailsRequest updateRentDetailsRequest) throws BusinessException {
		return this.rentDetailsService.update(updateRentDetailsRequest);
	}
	
	@GetMapping("/getRentDetails")
	public DataResult<GetRentDetails> getRentDetails() throws BusinessException{
		return this.rentDetailsService.getRentDetails();
	}
	
}
