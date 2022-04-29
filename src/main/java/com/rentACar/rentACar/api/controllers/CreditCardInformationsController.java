package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.CreditCardInformationService;
import com.rentACar.rentACar.business.dtos.creditCardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.CreateCreditCardInformationRequest;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.DeleteCreditCardInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.creditCardException.CreditCardAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.creditCardException.CreditCardNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cardInformation")
public class CreditCardInformationsController {

	private CreditCardInformationService cardInformationService;

	public CreditCardInformationsController(CreditCardInformationService cardInformationService) {
		super();
		this.cardInformationService = cardInformationService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCreditCardInformationRequest createCardInformationRequest)
			throws CustomerNotFoundException, CreditCardAlreadyExistsException {
		return this.cardInformationService.add(createCardInformationRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCreditCardInformationRequest deleteCreditCardInformationRequest)
			throws CreditCardNotFoundException {
		return this.cardInformationService.delete(deleteCreditCardInformationRequest);
	}

	@GetMapping("/getByCustomerId")
	public Result getByCustomerId(@RequestParam int customerId) throws CustomerNotFoundException {
		return this.cardInformationService.getByCustomerId(customerId);
	}

	@GetMapping("/getAll")
	public DataResult<List<CardInformationListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.cardInformationService.getAll(pageNo, pageSize);
	}

}
