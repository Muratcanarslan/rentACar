package com.rentACar.rentACar.business.abstracts;

import com.rentACar.rentACar.business.dtos.rentDetailsDtos.GetRentDetails;
import com.rentACar.rentACar.business.requests.rentDetailsRequests.UpdateRentDetailsRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface RentDetailsService {
	
	Result update(UpdateRentDetailsRequest updateRentDetailsService) throws BusinessException;
	DataResult<GetRentDetails> getRentDetails() throws BusinessException;
	double getDifferentCityDeliveryPrice() throws BusinessException;
}
