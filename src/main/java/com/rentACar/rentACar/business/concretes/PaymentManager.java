package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.api.models.MakePaymentForCorporateCustomerModel;
import com.rentACar.rentACar.api.models.MakePaymentForDelayedReturnModel;
import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.PaymentService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListByCustomerDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.rentACar.rentACar.core.utilities.adapters.abstracts.BankService;
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
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.PaymentDao;
import com.rentACar.rentACar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentedCarService rentedCarService;
	private InvoiceService invoiceService;
	private BankService bankService;
	private CustomerService customerService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			RentedCarService rentedCarService, InvoiceService invoiceService, BankService bankService,
			OrderedAdditionalServiceService orderedAdditionalServiceService, CustomerService customerService) {
		super();
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentedCarService = rentedCarService;
		this.invoiceService = invoiceService;
		this.bankService = bankService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
	}

	@Override
	public Result makePaymentForIndividualCustomer(
			MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException,
			CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CityNotFoundException {

		this.checkIfPaymentIsSuccessfull(makePaymentForIndividualCustomerModel.getCreateBankServiceRequest());

		runPaymentSuccessorForIndividualCustomer(makePaymentForIndividualCustomerModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);

	}

	@Transactional
	private void runPaymentSuccessorForIndividualCustomer(
			MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException,
			IndividualCustomerNotFoundException, CustomerNotFoundException, AdditionalServiceNotFoundException,
			OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException,
			InvoiceNotFoundException, CityNotFoundException {

		int rentedCarId = this.rentedCarService.addForIndividualCustomer(
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer());

		this.orderedAdditionalServiceService.addOrderedAdditionalServiceForPayment(makePaymentForIndividualCustomerModel
				.getCreateOrderedAdditionalServiceListRequests().getAdditionalServiceIds(), rentedCarId);

		int invoiceId = this.invoiceService.add(this.invoiceService.getInvoiceRequestForMapping(rentedCarId));

		CreatePaymentRequest createPaymentRequest = this.getPaymentRequestForMapping(
				this.invoiceService.getById(invoiceId).getData().getTotalPrice(),
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer().getCustomerId(),
				rentedCarId, invoiceId);

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.setCustomer(this.customerService.getCustomerById(createPaymentRequest.getCustomer_CustomerId()));

		this.paymentDao.save(payment);

	}

	@Override
	public Result makePaymentForCorporateCustomer(
			MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CorporateCustomerNotFoundException,
			CarAlreadyInMaintenanceException, CarAlreadyInRentException, CityNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, CustomerNotFoundException,
			InvoiceNotFoundException {

		this.checkIfPaymentIsSuccessfull(makePaymentForCorporateCustomerModel.getBankServiceRequest());

		runPaymentSuccessorForCorporateCustomer(makePaymentForCorporateCustomerModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Transactional
	private void runPaymentSuccessorForCorporateCustomer(
			MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException,
			CarAlreadyInRentException, CityNotFoundException, AdditionalServiceNotFoundException,
			OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException,
			CustomerNotFoundException, InvoiceNotFoundException {

		int rentedCarId = this.rentedCarService.addForCorporateCustomer(
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer());

		System.out.println(rentedCarId);

		this.orderedAdditionalServiceService.addOrderedAdditionalServiceForPayment(makePaymentForCorporateCustomerModel
				.getCreateOrderedAdditionalServiceListRequests().getAdditionalServiceIds(), rentedCarId);

		int invoiceId = this.invoiceService.add(this.invoiceService.getInvoiceRequestForMapping(rentedCarId));

		CreatePaymentRequest createPaymentRequest = this.getPaymentRequestForMapping(
				this.invoiceService.getById(invoiceId).getData().getTotalPrice(),
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer().getCustomerId(),
				rentedCarId, invoiceId);

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.setCustomer(this.customerService.getCustomerById(createPaymentRequest.getCustomer_CustomerId()));

		this.paymentDao.save(payment);

	}

	@Override
	public Result makePaymentForDelayedReturn(MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws PaymentNotSuccessfullException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());
		this.checkIfPaymentIsSuccessfull(makePaymentForDelayedReturnModel.getCreateBankServiceRequest());

		this.runPaymentSuccessorForDelayedReturn(makePaymentForDelayedReturnModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Transactional
	private void runPaymentSuccessorForDelayedReturn(MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws RentedCarNotFoundException, AdditionalServiceNotFoundException, CarNotFoundException,
			RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException {

		this.rentedCarService.updateRentedCarForDelayedReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest());

		int invoiceId = this.invoiceService.addForDelayedReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());

		CreatePaymentRequest createPaymentRequest = this.getPaymentRequestForMapping(
				this.invoiceService.getById(invoiceId).getData().getTotalPrice(),
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getCustomerId(),
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId(),
				invoiceId);

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.setCustomer(this.customerService.getCustomerById(createPaymentRequest.getCustomer_CustomerId()));

		this.paymentDao.save(payment);

	}

	@Override
	public DataResult<GetPaymentDto> getById(int paymentId) throws PaymentNotFoundException {

		checkIfPaymentExists(paymentId);

		Payment payment = this.paymentDao.getById(paymentId);

		GetPaymentDto getPaymentDto = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);

		return new SuccessDataResult<GetPaymentDto>(getPaymentDto);

	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {

		List<Payment> payments = this.paymentDao.findAll();

		List<PaymentListDto> paymentListDtos = payments.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(paymentListDtos, "payments");

	}

	@Override
	public DataResult<List<PaymentListByCustomerDto>> getByCustomerId(int customerId) throws CustomerNotFoundException {

		this.customerService.checkIfCustomerExists(customerId);

		List<Payment> payments = this.paymentDao.getByCustomer_CustomerId(customerId);

		List<PaymentListByCustomerDto> paymentListByCustomerDtos = payments.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListByCustomerDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListByCustomerDto>>(paymentListByCustomerDtos);
	}

	private CreatePaymentRequest getPaymentRequestForMapping(double totalPrice, int customerId, int rentedCarId,
			int invoiceId) {

		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();

		createPaymentRequest.setCustomer_CustomerId(customerId);
		createPaymentRequest.setInvoice_InvoiceId(invoiceId);
		createPaymentRequest.setTotalPrice(totalPrice);
		createPaymentRequest.setRentedCar_RentedCarId(rentedCarId);

		return createPaymentRequest;

	}

	private void checkIfPaymentExists(int paymentId) throws PaymentNotFoundException {
		if (!this.paymentDao.existsById(paymentId)) {
			throw new PaymentNotFoundException(BusinessMessages.PAYMENT_NOT_FOUND + paymentId);
		}
	}

	public void checkIfPaymentIsSuccessfull(CreateBankServiceRequest createBankServiceRequest)
			throws PaymentNotSuccessfullException {

		this.bankService.addPayment(createBankServiceRequest.getCardNumber(), createBankServiceRequest.getCvv(),
				createBankServiceRequest.getName(), createBankServiceRequest.getTotalPrice());

	}

}
