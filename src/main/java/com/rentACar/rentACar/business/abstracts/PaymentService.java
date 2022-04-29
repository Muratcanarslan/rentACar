package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.api.models.MakePaymentForCorporateCustomerModel;
import com.rentACar.rentACar.api.models.MakePaymentForDelayedReturnModel;
import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListByCustomerDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions.PaymentNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions.PaymentValueIsNotCorrectException;
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentUpdateNotRequiresPaymentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarAlreadyReturnException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.ReturnKilometreNotValidException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface PaymentService {

	Result makePaymentForIndividualCustomer(MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException,
			CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CityNotFoundException,
			PaymentValueIsNotCorrectException;

	Result makePaymentForCorporateCustomer(MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CorporateCustomerNotFoundException,
			CarAlreadyInMaintenanceException, CarAlreadyInRentException, CityNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, CustomerNotFoundException,
			InvoiceNotFoundException, PaymentValueIsNotCorrectException;

	Result makePaymentForDelayedReturn(MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws PaymentNotSuccessfullException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException,
			RentUpdateNotRequiresPaymentException, RentedCarAlreadyReturnException, ReturnKilometreNotValidException, PaymentValueIsNotCorrectException;

	DataResult<GetPaymentDto> getById(int paymentId) throws PaymentNotFoundException;

	DataResult<List<PaymentListDto>> getAll(int pageNo, int pageSize);

	DataResult<List<PaymentListByCustomerDto>> getByCustomerId(int customerId) throws CustomerNotFoundException;
}
