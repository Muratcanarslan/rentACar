package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface PaymentService {
	
	Result add(CreatePaymentRequest createPaymentRequest) throws CustomerNotFoundException, BusinessException;
	
	DataResult<GetPaymentDto> getById(int paymentId);
	
	DataResult<List<PaymentListDto>> getAll();
	
	//DataResult<List<PaymentListForRentedCarIdDto>> getByRentedCarId(); 

}
