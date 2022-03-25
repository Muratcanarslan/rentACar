package com.rentACar.rentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.RentDetailsService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.rentDetailsDtos.GetRentDetails;
import com.rentACar.rentACar.business.requests.rentDetailsRequests.UpdateRentDetailsRequest;
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.RentDetailsDao;
import com.rentACar.rentACar.entities.concretes.RentDetails;

@Service
public class RentDetailsManager implements RentDetailsService {

	private RentDetailsDao rentDetailsDao;
	private ModelMapperService modelMapperService;

	public RentDetailsManager(RentDetailsDao rentDetailsDao, ModelMapperService modelMapperService) {
		super();
		this.rentDetailsDao = rentDetailsDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result update(UpdateRentDetailsRequest updateRentDetailsService) throws RentDetailsNotFoundException {

		checkIfRentDetailsExists();
		RentDetails rentDetails = this.modelMapperService.forRequest().map(updateRentDetailsService, RentDetails.class);
		rentDetails.setRentDetailsId(1);

		this.rentDetailsDao.save(rentDetails);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);

	}

	@Override
	public DataResult<GetRentDetails> getRentDetails() throws RentDetailsNotFoundException {

		checkIfRentDetailsExists();

		RentDetails rentDetails = this.rentDetailsDao.getById(1);

		GetRentDetails getRentDetails = this.modelMapperService.forDto().map(rentDetails, GetRentDetails.class);

		return new SuccessDataResult<GetRentDetails>(getRentDetails, BusinessMessages.GET_SUCCESSFUL);

	}

	public void checkIfRentDetailsExists() throws RentDetailsNotFoundException {
		if (!this.rentDetailsDao.existsById(1)) {
			throw new RentDetailsNotFoundException(BusinessMessages.RENT_DETAILS_NOT_FOUND);
		}
	}

	public double getDifferentCityDeliveryPrice() throws RentDetailsNotFoundException {
		checkIfRentDetailsExists();
		return this.rentDetailsDao.getById(1).getDifferentCityDeliveryPrice();
	}

}
