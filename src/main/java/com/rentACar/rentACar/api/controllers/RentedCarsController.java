package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListForCustomerDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.DeleteRentedCarRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentedCars")
public class RentedCarsController {

	private RentedCarService rentedCarService;

	public RentedCarsController(RentedCarService rentedCarService) {
		super();
		this.rentedCarService = rentedCarService;
	}

	@PutMapping("/updateForValidReturn")
	public Result update(@RequestBody @Valid UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException {
		return this.rentedCarService.updateForValidReturn(updateRentedCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentedCarRequest deleteRentedCarRequest) throws BusinessException {
		return this.rentedCarService.delete(deleteRentedCarRequest);
	}

	@GetMapping("/getById")
	public DataResult<GetRentedCarDto> getById(@RequestParam int rentedCarId) throws BusinessException {
		return this.rentedCarService.getById(rentedCarId);
	}

	@GetMapping("/getAll")
	public DataResult<List<RentedCarListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws BusinessException {

		return this.rentedCarService.getAll(pageNo, pageSize);
	}

	@GetMapping("/getByCustomerId")
	public DataResult<List<RentedCarListForCustomerDto>> getByCustomerId(@RequestParam int customerId)
			throws CustomerNotFoundException {
		return this.rentedCarService.getByCustomerId(customerId);
	}
}
