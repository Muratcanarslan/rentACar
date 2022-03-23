package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.abstracts.CardInformationService;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.PaymentService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.cardInformationRequests.CreateCardInformationRequest;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.rentACar.rentACar.core.utilities.adapters.abstracts.BankService;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions.PaymentAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.PaymentDao;
import com.rentACar.rentACar.entities.concretes.CardInformation;
import com.rentACar.rentACar.entities.concretes.Customer;
import com.rentACar.rentACar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentedCarService rentedCarService;
	private InvoiceService invoiceService;
	private BankService bankService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, CustomerService customerService,
			RentedCarService rentedCarService, InvoiceService invoiceService, BankService bankService,
			OrderedAdditionalServiceService orderedAdditionalServiceService
			) {
		super();
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentedCarService = rentedCarService;
		this.invoiceService = invoiceService;
		this.bankService = bankService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public Result makePaymentForIndividualCustomer(
			MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException,
			CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException {

		this.bankService.addPayment(makePaymentForIndividualCustomerModel.getCreateBankServiceRequest().getCardNumber(),
				makePaymentForIndividualCustomerModel.getCreateBankServiceRequest().getCvv(),
				makePaymentForIndividualCustomerModel.getCreateBankServiceRequest().getName(),
				makePaymentForIndividualCustomerModel.getCreateBankServiceRequest().getTotalPrice());

		runPaymentSuccessorForIndividualCustomer(makePaymentForIndividualCustomerModel);

		return new SuccessResult(BusinessMessages.ADDED_SUCCESSFULL);

	}

	@Transactional
	public void runPaymentSuccessorForIndividualCustomer(
			MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException,
			IndividualCustomerNotFoundException, CustomerNotFoundException, AdditionalServiceNotFoundException,
			OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException,
			InvoiceNotFoundException {

		int rentedCarId = this.rentedCarService.addForIndividualCustomer(
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer());

		this.orderedAdditionalServiceService.addOrderedAdditionalServiceForPayment(makePaymentForIndividualCustomerModel
				.getCreateOrderedAdditionalServiceListRequests().getAdditionalServiceIds(), rentedCarId);

		// TODO:SIL

		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

		createInvoiceRequest.setRentedCar_RentedCarId(rentedCarId);

		int invoiceId = this.invoiceService.add(createInvoiceRequest);

		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();

		createPaymentRequest.setCustomer_CustomerId(1);
		createPaymentRequest.setInvoice_InvoiceId(invoiceId);
		createPaymentRequest.setRentedCar_RentedCarId(rentedCarId);
		createPaymentRequest.setTotalPrice(this.invoiceService.getById(invoiceId).getData().getTotalPrice());

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.getCustomer().setUserId(payment.getCustomer().getCustomerId());

		this.paymentDao.save(payment);

	}

	@Override
	public DataResult<GetPaymentDto> getById(int paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {

		List<Payment> payments = this.paymentDao.findAll();

		List<PaymentListDto> paymentListDtos = payments.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(paymentListDtos, "payments");

	}

	private void checkIfPaymentAlreadyExistsForInvoiceId(int invoiceId) throws PaymentAlreadyExistsException {
		if (this.paymentDao.existsByInvoice_InvoiceId(invoiceId)) {
			throw new PaymentAlreadyExistsException(BusinessMessages.PAYMENT_ALREADY_EXISTS_BY_INVOICE_ID + invoiceId);
		}
	}

}
