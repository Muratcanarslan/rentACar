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

import com.rentACar.rentACar.business.abstracts.CorporateCustomerService;
import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.GetCorporateCustomerDto;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerAlreadyExistsByTaxNumberException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

	private CorporateCustomerService corporateCustomerService;

	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		super();
		this.corporateCustomerService = corporateCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)
			throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam int corporateCustomerId) throws CorporateCustomerNotFoundException {
		return this.corporateCustomerService.delete(corporateCustomerId);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
			throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException,
			CorporateCustomerNotFoundException {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@GetMapping("/getById")
	public DataResult<GetCorporateCustomerDto> getById(@RequestParam int corporateCustomerId)
			throws CorporateCustomerNotFoundException {
		return this.corporateCustomerService.getById(corporateCustomerId);
	}

	@GetMapping("/getAll")
	public DataResult<List<CorporateCustomerListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.corporateCustomerService.getAll(pageNo,pageSize);
	}
}
