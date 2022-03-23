package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CardInformationService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.cardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.requests.cardInformationRequests.CreateCardInformationRequest;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CardInformationDao;
import com.rentACar.rentACar.entities.concretes.CardInformation;

@Service
public class CardInformationManager implements CardInformationService {

	private CardInformationDao cardInformationDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CardInformationManager(CardInformationDao cardInformationDao, ModelMapperService modelMapperService) {
		super();
		this.cardInformationDao = cardInformationDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCardInformationRequest createCardInformationRequest) {
		
		CardInformation cardInformation = this.modelMapperService.forRequest().map(createCardInformationRequest, CardInformation.class);
		
		this.cardInformationDao.save(cardInformation);
		
		return new SuccessResult(BusinessMessages.ADDED_SUCCESSFULL);
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAll() {

		List<CardInformation> cardInformations = this.cardInformationDao.findAll();

		List<CardInformationListDto> cardInformationListDtos = cardInformations.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CardInformationListDto>>(cardInformationListDtos);
	}

}
