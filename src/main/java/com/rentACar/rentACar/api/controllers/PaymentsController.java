package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.abstracts.PaymentService;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
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


@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	private PaymentService paymentService;

	public PaymentsController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}
	
	@PostMapping("/makePaymentForIndividualCustomer")
	public Result makePaymentForIndividualCustomer(@RequestBody @Valid MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel) throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException, AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException {
		return this.paymentService.makePaymentForIndividualCustomer(makePaymentForIndividualCustomerModel);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<PaymentListDto>> getAll(){
		return this.paymentService.getAll();
	}
}
