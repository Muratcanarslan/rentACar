package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.cardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.requests.cardInformationRequests.CreateCardInformationRequest;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CardInformationService {
	
	Result add(CreateCardInformationRequest createCardInformationRequest);
	
	DataResult<List<CardInformationListDto>> getAll();
}
