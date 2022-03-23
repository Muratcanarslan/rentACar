package com.rentACar.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.CardInformationService;
import com.rentACar.rentACar.business.dtos.cardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.requests.cardInformationRequests.CreateCardInformationRequest;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/cardInformation")
public class CardInformationsController {

	private CardInformationService cardInformationService;

	public CardInformationsController(CardInformationService cardInformationService) {
		super();
		this.cardInformationService = cardInformationService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCardInformationRequest createCardInformationRequest) {
		return this.cardInformationService.add(createCardInformationRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<CardInformationListDto>> getAll() {
		return this.cardInformationService.getAll();
	}

}
