package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CreditCardInformationService;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.creditCardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.dtos.creditCardInformationDtos.CreditCardInformationByCustomerDto;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.CreateCreditCardInformationRequest;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.DeleteCreditCardInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.creditCardException.CreditCardNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CreditCardInformationDao;
import com.rentACar.rentACar.entities.concretes.CreditCardInformation;

@Service
public class CreditCardInformationManager implements CreditCardInformationService {

	private CreditCardInformationDao cardInformationDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;

	@Autowired
	public CreditCardInformationManager(CreditCardInformationDao cardInformationDao, CustomerService customerService,
			ModelMapperService modelMapperService) {
		super();
		this.cardInformationDao = cardInformationDao;
		this.customerService = customerService;
		this.modelMapperService = modelMapperService;

	}

	@Override
	public Result add(CreateCreditCardInformationRequest createCardInformationRequest)
			throws CustomerNotFoundException {

		this.customerService.checkIfCustomerExists(createCardInformationRequest.getCustomer_CustomerId());

		CreditCardInformation cardInformation = this.modelMapperService.forRequest().map(createCardInformationRequest,
				CreditCardInformation.class);
		
		cardInformation.setCustomer(this.customerService.getCustomerById(createCardInformationRequest.getCustomer_CustomerId()));

		this.cardInformationDao.save(cardInformation);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result delete(DeleteCreditCardInformationRequest deleteCreditCardInformationRequest)
			throws CreditCardNotFoundException {

		checkIfCreditCardInformationExists(deleteCreditCardInformationRequest.getCreditCardInformationId());

		this.cardInformationDao.deleteById(deleteCreditCardInformationRequest.getCreditCardInformationId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAll() {

		List<CreditCardInformation> cardInformations = this.cardInformationDao.findAll();

		List<CardInformationListDto> cardInformationListDtos = cardInformations.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CardInformationListDto>>(cardInformationListDtos);
	}

	@Override
	public DataResult<List<CreditCardInformationByCustomerDto>> getByCustomerId(int customerId)
			throws CustomerNotFoundException {

		this.customerService.checkIfCustomerExists(customerId);

		List<CreditCardInformation> creditCardInformations = this.cardInformationDao
				.getByCustomer_CustomerId(customerId);

		List<CreditCardInformationByCustomerDto> creditCardInformationByCustomerDtos = creditCardInformations.stream()
				.map(creditCardInformation -> this.modelMapperService.forDto().map(creditCardInformation,
						CreditCardInformationByCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CreditCardInformationByCustomerDto>>(creditCardInformationByCustomerDtos,BusinessMessages.GET_SUCCESSFUL);

	}

	private void checkIfCreditCardInformationExists(int creditCarId) throws CreditCardNotFoundException {
		if (!this.cardInformationDao.existsById(creditCarId)) {
			throw new CreditCardNotFoundException(BusinessMessages.CREDIT_CARD_NOT_FOUND + creditCarId);
		}
	}

}
