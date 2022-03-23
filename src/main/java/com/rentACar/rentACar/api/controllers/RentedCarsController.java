package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.api.models.CreateRentedCarForCorporateCustomerAndBankServiceModel;
import com.rentACar.rentACar.api.models.CreateRentedCarForIndividualCustomerAndBankServiceModel;
import com.rentACar.rentACar.api.models.UpdateRentedCarForDelayedReturnModel;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.adapters.abstracts.BankService;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentedCars")
public class RentedCarsController {

	private RentedCarService rentedCarService;
	private BankService bankService;

	public RentedCarsController(RentedCarService rentedCarService, BankService bankService) {
		super();
		this.rentedCarService = rentedCarService;
		this.bankService = bankService;
	}

	@PostMapping("/addForIndividualCustomer")
	public Result addForIndividualCustomer(
			@RequestBody @Valid CreateRentedCarForIndividualCustomerAndBankServiceModel createRentedCarRequest)
			throws BusinessException {

		this.bankService.addPayment(createRentedCarRequest.getCreateBankServiceRequest().getCardNumber(),
				createRentedCarRequest.getCreateBankServiceRequest().getCvv(),
				createRentedCarRequest.getCreateBankServiceRequest().getName(),
				createRentedCarRequest.getCreateBankServiceRequest().getTotalPrice());

		return this.rentedCarService
				.addForIndividualCustomer(createRentedCarRequest.getCreateRentedCarRequestForIndividualCustomer());
	}

	@PostMapping("/addForCorporateCustomer")
	public Result addForCorporateCustomer(
			@RequestBody @Valid CreateRentedCarForCorporateCustomerAndBankServiceModel createRentedCarRequestForCorporateCustomer)
			throws BusinessException {

		this.bankService.addPayment(
				createRentedCarRequestForCorporateCustomer.getCreateBankServiceRequest().getCardNumber(),
				createRentedCarRequestForCorporateCustomer.getCreateBankServiceRequest().getCvv(),
				createRentedCarRequestForCorporateCustomer.getCreateBankServiceRequest().getName(),
				createRentedCarRequestForCorporateCustomer.getCreateBankServiceRequest().getTotalPrice());

		return this.rentedCarService.addForCorporateCustomer(
				createRentedCarRequestForCorporateCustomer.getCreateRentedCarRequestForCorporateCustomer());
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException {
		return this.rentedCarService.update(updateRentedCarRequest);
	}

	@PutMapping("/updateRentedCarForDelayedReturn")
	public Result updateRentedCarForDelayedReturn(
			@RequestBody @Valid UpdateRentedCarForDelayedReturnModel updateRentedCarForDelayedReturnModel)
			throws BusinessException {

		this.bankService.addPayment(updateRentedCarForDelayedReturnModel.getCreateBankServiceRequest().getCardNumber(),
				updateRentedCarForDelayedReturnModel.getCreateBankServiceRequest().getCvv(),
				updateRentedCarForDelayedReturnModel.getCreateBankServiceRequest().getName(),
				updateRentedCarForDelayedReturnModel.getCreateBankServiceRequest().getTotalPrice());

		return this.rentedCarService.updateRentedCarForDelayedReturn(
				updateRentedCarForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest());

	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam int rentedCarId) throws BusinessException {
		return this.rentedCarService.delete(rentedCarId);
	}

	@GetMapping("/getById")
	public DataResult<GetRentedCarDto> getById(@RequestParam int rentedCarId) throws BusinessException {
		return this.rentedCarService.getById(rentedCarId);
	}

	@GetMapping("/getAll")
	public DataResult<List<RentedCarListDto>> getAll() throws BusinessException {
		return this.rentedCarService.getAll();
	}
}
