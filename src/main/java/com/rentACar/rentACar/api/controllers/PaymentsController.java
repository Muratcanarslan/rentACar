package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.api.models.MakePaymentForCorporateCustomerModel;
import com.rentACar.rentACar.api.models.MakePaymentForDelayedReturnModel;
import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.abstracts.PaymentService;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListByCustomerDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
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

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	public PaymentsController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@PostMapping("/makePaymentForIndividualCustomer")
	public Result makePaymentForIndividualCustomer(
			@RequestBody @Valid MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws BusinessException {

		return this.paymentService.makePaymentForIndividualCustomer(makePaymentForIndividualCustomerModel);
	}

	@PostMapping("/makePaymentForCorporateCustomer")
	public Result makePaymentForCorporateCustomer(
			@RequestBody @Valid MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CorporateCustomerNotFoundException,
			CarAlreadyInMaintenanceException, CarAlreadyInRentException, CityNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, CustomerNotFoundException,
			InvoiceNotFoundException, PaymentValueIsNotCorrectException {

		return this.paymentService.makePaymentForCorporateCustomer(makePaymentForCorporateCustomerModel);

	}

	@PostMapping("/makePaymentForDelayedReturn")
	public Result makePaymentForDelayedReturn(
			@RequestBody @Valid MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws PaymentNotSuccessfullException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException,
			RentUpdateNotRequiresPaymentException, RentedCarAlreadyReturnException, ReturnKilometreNotValidException,
			PaymentValueIsNotCorrectException {

		return this.paymentService.makePaymentForDelayedReturn(makePaymentForDelayedReturnModel);

	}

	@GetMapping("/getAll")
	public DataResult<List<PaymentListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		return this.paymentService.getAll(pageNo, pageSize);
	}

	@GetMapping("/getById")
	public DataResult<GetPaymentDto> getById(@RequestParam int paymentId) throws PaymentNotFoundException {
		return this.paymentService.getById(paymentId);
	}

	@GetMapping("/getByCustomerId")
	public DataResult<List<PaymentListByCustomerDto>> getByCustomerId(@RequestParam int customerId)
			throws CustomerNotFoundException {
		return this.paymentService.getByCustomerId(customerId);
	}

}
