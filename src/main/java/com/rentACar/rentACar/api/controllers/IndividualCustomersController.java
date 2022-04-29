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

import com.rentACar.rentACar.business.abstracts.IndividualCustomerService;
import com.rentACar.rentACar.business.dtos.individualCustomerDtos.GetIndividualCustomerDto;
import com.rentACar.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerAlreadyExistsByNationalIdentityException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;

	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws UserAlreadyExistsException, IndividualCustomerAlreadyExistsByNationalIdentityException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest)
			throws IndividualCustomerNotFoundException {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws IndividualCustomerNotFoundException, UserAlreadyExistsException,
			IndividualCustomerAlreadyExistsByNationalIdentityException {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@GetMapping("/getById")
	public DataResult<GetIndividualCustomerDto> getById(@RequestParam int individualCustomerId)
			throws IndividualCustomerNotFoundException {
		return this.individualCustomerService.getById(individualCustomerId);
	}

	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.individualCustomerService.getAll(pageNo, pageSize);
	}

}
