package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface PaymentService {
	
	Result makePaymentForIndividualCustomer(MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel) throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException, AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException;
	
	DataResult<GetPaymentDto> getById(int paymentId);
	
	DataResult<List<PaymentListDto>> getAll();
	
	//DataResult<List<PaymentListForRentedCarIdDto>> getByRentedCarId(); 

}
