package com.rentACar.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rentACar.rentACar.api.models.MakePaymentForCorporateCustomerModel;
import com.rentACar.rentACar.api.models.MakePaymentForDelayedReturnModel;
import com.rentACar.rentACar.api.models.MakePaymentForIndividualCustomerModel;
import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.CityService;
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
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
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
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.core.utilities.timeUtilities.TimeUtility;
import com.rentACar.rentACar.dataAccess.abstracts.PaymentDao;
import com.rentACar.rentACar.entities.concretes.Payment;
import com.rentACar.rentACar.entities.concretes.RentedCar;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentedCarService rentedCarService;
	private InvoiceService invoiceService;
	private BankService bankService;
	private CustomerService customerService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private CarService carService;
	private CityService cityService;
	private AdditionalServiceService additionalServiceService;

	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			RentedCarService rentedCarService, InvoiceService invoiceService, BankService bankService,
			OrderedAdditionalServiceService orderedAdditionalServiceService, CustomerService customerService,
			CarService carService, CityService cityService, AdditionalServiceService additionalServiceService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentedCarService = rentedCarService;
		this.invoiceService = invoiceService;
		this.bankService = bankService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
		this.carService = carService;
		this.cityService = cityService;
		this.additionalServiceService = additionalServiceService;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result makePaymentForIndividualCustomer(
			MakePaymentForIndividualCustomerModel makePaymentForIndividualCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CarAlreadyInRentException,
			CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CityNotFoundException,
			PaymentValueIsNotCorrectException {

		this.checkIfPaymentValueIsValid(
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer().getCarId(),
				TimeUtility.calculateTotalRentDays(
						makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer()
								.getRentDate(),
						makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer()
								.getConfirmedPaidedDate()),
				makePaymentForIndividualCustomerModel.getCreateOrderedAdditionalServiceListRequests()
						.getAdditionalServiceIds(),
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer().getHireCityId(),
				makePaymentForIndividualCustomerModel.getCreateRentedCarRequestForIndividualCustomer()
						.getReturnCityId(),
				makePaymentForIndividualCustomerModel.getCreateBankServiceRequest().getTotalPrice());

		this.checkIfPaymentIsSuccessfull(makePaymentForIndividualCustomerModel.getCreateBankServiceRequest());

		runPaymentSuccessorForIndividualCustomer(makePaymentForIndividualCustomerModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void runPaymentSuccessorForIndividualCustomer(
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result makePaymentForCorporateCustomer(
			MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws PaymentNotSuccessfullException, CarNotFoundException, CorporateCustomerNotFoundException,
			CarAlreadyInMaintenanceException, CarAlreadyInRentException, CityNotFoundException,
			AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException, RentDetailsNotFoundException, CustomerNotFoundException,
			InvoiceNotFoundException, PaymentValueIsNotCorrectException {

		this.checkIfPaymentValueIsValid(
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer().getCarId(),
				TimeUtility.calculateTotalRentDays(
						makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer()
								.getRentDate(),
						makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer()
								.getConfirmedPaidedDate()),
				makePaymentForCorporateCustomerModel.getCreateOrderedAdditionalServiceListRequests()
						.getAdditionalServiceIds(),
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer().getHireCityId(),
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer().getReturnCityId(),
				makePaymentForCorporateCustomerModel.getBankServiceRequest().getTotalPrice());

		this.checkIfPaymentIsSuccessfull(makePaymentForCorporateCustomerModel.getBankServiceRequest());

		runPaymentSuccessorForCorporateCustomer(makePaymentForCorporateCustomerModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void runPaymentSuccessorForCorporateCustomer(
			MakePaymentForCorporateCustomerModel makePaymentForCorporateCustomerModel)
			throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException,
			CarAlreadyInRentException, CityNotFoundException, AdditionalServiceNotFoundException,
			OrderedAdditionalServiceAlreadyExistsException, RentedCarNotFoundException, RentDetailsNotFoundException,
			CustomerNotFoundException, InvoiceNotFoundException {

		int rentedCarId = this.rentedCarService.addForCorporateCustomer(
				makePaymentForCorporateCustomerModel.getCreateRentedCarRequestForCorporateCustomer());

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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result makePaymentForDelayedReturn(MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws PaymentNotSuccessfullException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException,
			RentUpdateNotRequiresPaymentException, RentedCarAlreadyReturnException, ReturnKilometreNotValidException,
			PaymentValueIsNotCorrectException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());
		this.checkIfPaymentIsSuccessfull(makePaymentForDelayedReturnModel.getCreateBankServiceRequest());
		this.rentedCarService.checkIfRentedCarAlreadyReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());

		this.checkIfPaymentValueIsValidForDelayedReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId(),
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getReturnDate(),
				makePaymentForDelayedReturnModel.getCreateBankServiceRequest().getTotalPrice());

		this.checkIfPaymentIsSuccessfull(makePaymentForDelayedReturnModel.getCreateBankServiceRequest());

		this.runPaymentSuccessorForDelayedReturn(makePaymentForDelayedReturnModel);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void runPaymentSuccessorForDelayedReturn(MakePaymentForDelayedReturnModel makePaymentForDelayedReturnModel)
			throws RentedCarNotFoundException, AdditionalServiceNotFoundException, CarNotFoundException,
			RentDetailsNotFoundException, InvoiceNotFoundException, CustomerNotFoundException,
			RentUpdateNotRequiresPaymentException, ReturnKilometreNotValidException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());

		RentedCar rentedCar = this.rentedCarService.getRentedCarForBusiness(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());

		this.rentedCarService.updateRentedCarForDelayedReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest());

		int invoiceId = this.invoiceService.addForDelayedReturn(
				makePaymentForDelayedReturnModel.getUpdateRentedCarForDelayedReturnRequest().getRentedCarId());

		CreatePaymentRequest createPaymentRequest = this.getPaymentRequestForMapping(
				this.invoiceService.getById(invoiceId).getData().getTotalPrice(),
				rentedCar.getCustomer().getCustomerId(),
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
	public DataResult<List<PaymentListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Payment> payments = this.paymentDao.findAll(pageable).getContent();

		List<PaymentListDto> paymentListDtos = payments.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(paymentListDtos, BusinessMessages.GET_SUCCESSFUL);

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

	private void checkIfPaymentValueIsValid(int carId, int rentDayValue, List<Integer> additionalServiceIds,
			int hireCityId, int returnCityId, double paymentValue)
			throws CarNotFoundException, RentDetailsNotFoundException, CityNotFoundException,
			AdditionalServiceNotFoundException, PaymentValueIsNotCorrectException {

		double calculatedPrice = this.paymentValueCalculator(carId, rentDayValue, additionalServiceIds, hireCityId,
				returnCityId);

		if (calculatedPrice != paymentValue) {
			throw new PaymentValueIsNotCorrectException(BusinessMessages.PAYMENT_VALUE_IS_NOT_CORRECT);
		}

	}

	private double paymentValueCalculator(int carId, int rentDayValue, List<Integer> additionalServiceIds,
			int hireCityId, int returnCityId) throws AdditionalServiceNotFoundException, RentDetailsNotFoundException,
			CarNotFoundException, CityNotFoundException {

		this.carService.checkIfExistByCarId(carId);
		this.cityService.checkIfCityExists(returnCityId);
		this.cityService.checkIfCityExists(hireCityId);

		double carDailyPrice = this.carService.getById(carId).getData().getDailyPrice();

		double carRentPrice = carDailyPrice * rentDayValue;

		double deliveryPrice = this.invoiceService.calculateDeliveryPrice(hireCityId, returnCityId);

		double additionalServicePrice = this.additionalServiceService
				.calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(additionalServiceIds,
						rentDayValue);

		return carRentPrice + deliveryPrice + additionalServicePrice;
	}

	private void checkIfPaymentValueIsValidForDelayedReturn(int rentedCarId, LocalDate returnDate, double paymentValue)
			throws RentedCarNotFoundException, AdditionalServiceNotFoundException, CarNotFoundException,
			RentDetailsNotFoundException, PaymentValueIsNotCorrectException {

		double calculatedPrice = paymentValueCalculatorForDelayedReturn(rentedCarId, returnDate);

		if (calculatedPrice != paymentValue) {
			throw new PaymentValueIsNotCorrectException(BusinessMessages.PAYMENT_VALUE_IS_NOT_CORRECT);
		}

	}

	private double paymentValueCalculatorForDelayedReturn(int rentedCarId, LocalDate returnDate)
			throws RentedCarNotFoundException, AdditionalServiceNotFoundException, CarNotFoundException,
			RentDetailsNotFoundException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		RentedCar rentedCar = this.rentedCarService.getRentedCarForBusiness(rentedCarId);

		double calculatedPrice = this.invoiceService.calculateTotalPriceForDelayedReturn(rentedCarId,
				rentedCar.getCar().getCarId(),
				TimeUtility.calculateTotalRentDays(rentedCar.getConfirmedPaidedDate(), returnDate));

		return calculatedPrice;

	}

	public void checkIfPaymentIsSuccessfull(CreateBankServiceRequest createBankServiceRequest)
			throws PaymentNotSuccessfullException {

		this.bankService.addPayment(createBankServiceRequest.getCardNumber(), createBankServiceRequest.getCvv(),
				createBankServiceRequest.getName(), createBankServiceRequest.getTotalPrice());

	}

}
