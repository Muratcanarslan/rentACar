package com.rentACar.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.RentDetailsService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.InvoiceDao;
import com.rentACar.rentACar.entities.concretes.Invoice;
import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;
import com.rentACar.rentACar.entities.concretes.RentedCar;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentedCarService rentedCarService;
	private CustomerService customerService;
	private RentDetailsService rentDetailsService;
	private CarService carService;
	private AdditionalServiceService additionalServiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService,
			@Lazy RentedCarService rentedCarService, CustomerService customerService,
			RentDetailsService rentDetailsService, CarService carService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			AdditionalServiceService additionalServiceService) {
		super();
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentedCarService = rentedCarService;
		this.customerService = customerService;
		this.rentDetailsService = rentDetailsService;
		this.carService = carService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(createInvoiceRequest.getRentedCar_RentedCarId());
		checkIfInvoiceAlreadyExistsByRentedCarId(createInvoiceRequest.getRentedCar_RentedCarId());

		RentedCar rentedCar = this.rentedCarService
				.getRentedCarForBusiness(createInvoiceRequest.getRentedCar_RentedCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		invoice.setTotalRentDays(calculateTotalRentDays(rentedCar.getRentDate(), rentedCar.getConfirmedPaidedDate()));

		invoice.setTotalPrice(calculateTotalPrice(rentedCar.getRentDate(), rentedCar.getConfirmedPaidedDate(),
				rentedCar.getRentedCarId(), rentedCar.getCar().getCarId(), rentedCar.getHireCity().getCityId(),
				rentedCar.getReturnCity().getCityId(), invoice.getTotalRentDays()));

		this.invoiceDao.save(invoice);

		return new SuccessResult("invoice added by controller");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(updateInvoiceRequest.getRentedCar_RentedCarId());
		checkIfInvoiceExistsByRentedCarId(updateInvoiceRequest.getRentedCar_RentedCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

		invoice.setInvoiceId(this.invoiceDao.getByRentedCar_RentedCarId(updateInvoiceRequest.getRentedCar_RentedCarId())
				.getInvoiceId());

		RentedCar rentedCar = this.rentedCarService
				.getRentedCarForBusiness(updateInvoiceRequest.getRentedCar_RentedCarId());

		invoice.setTotalRentDays(calculateTotalRentDays(rentedCar.getRentDate(), rentedCar.getConfirmedPaidedDate()));

		invoice.setTotalPrice(calculateTotalPrice(rentedCar.getRentDate(), rentedCar.getConfirmedPaidedDate(),
				rentedCar.getRentedCarId(), rentedCar.getCar().getCarId(), rentedCar.getHireCity().getCityId(),
				rentedCar.getReturnCity().getCityId(), invoice.getTotalRentDays()));

		this.invoiceDao.save(invoice);

		return new SuccessResult("invoice updated");
	}

	@Override
	public DataResult<GetInvoiceDto> getById(int invoiceId) throws InvoiceNotFoundException {

		checkIfInvoiceExists(invoiceId);

		Invoice invoice = this.invoiceDao.getById(invoiceId);

		GetInvoiceDto getInvoiceDto = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<GetInvoiceDto>(getInvoiceDto, "invoice");
	}

	@Override
	public DataResult<List<InvoiceCustomerListDto>> getInvoicesByCustomerId(int customerId)
			throws CustomerNotFoundException {

		this.customerService.checkIfCustomerExists(customerId);

		List<Invoice> invoices = this.invoiceDao.getByRentedCar_Customer_CustomerId(customerId);

		List<InvoiceCustomerListDto> invoiceListDtos = invoices.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceCustomerListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceCustomerListDto>>(invoiceListDtos, "invoice list");

	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() {
		List<Invoice> invoices = this.invoiceDao.findAll();

		List<InvoiceListDto> invoiceListDtos = invoices.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(invoiceListDtos, "get all");
	}

	@Override
	public DataResult<GetInvoiceDto> getInvoiceByRentedCarId(int rentedCarId) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		checkIfInvoiceExistsByRentedCarId(rentedCarId);

		Invoice invoice = this.invoiceDao.getByRentedCar_RentedCarId(rentedCarId);

		GetInvoiceDto getInvoiceDto = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<GetInvoiceDto>(getInvoiceDto, "get invoice by rented car id");

	}

	@Override
	public DataResult<List<InvoiceDateBetweenDto>> getInvoicesDateBetween(Date startDate, Date endDate) {

		List<Invoice> invoices = this.invoiceDao.getByCreationDateBetween(startDate, endDate);

		List<InvoiceDateBetweenDto> dateBetweenDtos = invoices.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceDateBetweenDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDateBetweenDto>>(dateBetweenDtos, "date between invoice list");
	}

	@Override
	public Result delete(int invoiceId) throws InvoiceNotFoundException {
		checkIfInvoiceExists(invoiceId);

		this.invoiceDao.deleteById(invoiceId);

		return new SuccessResult("invoice deleted");
	}

	private double calculateTotalPrice(LocalDate rentDate, LocalDate confirmedPaidDate, int rentedCarId, int carId,
			int hireCityId, int returnCityId, int totalRentDays) throws BusinessException {

		double carDailyPrice = this.carService.getById(carId).getData().getDailyPrice();

		double additionalServicePrice = calculateOrderedAdditionalServicePrice(
				this.orderedAdditionalServiceService.getByRentedCarIdForBusiness(rentedCarId), totalRentDays);

		double carPrice = carDailyPrice * totalRentDays;

		double deliveryPrice = calculateDeliveryPrice(hireCityId, returnCityId);

		return deliveryPrice + carPrice + additionalServicePrice;

	}

	private double calculateOrderedAdditionalServicePrice(List<OrderedAdditionalService> orderedAdditionalServices,
			int totalRentDays) throws BusinessException {

		double price = 0;

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {
			price += this.additionalServiceService
					.getById(orderedAdditionalService.getAdditionalService().getAdditionalServiceId()).getData()
					.getAdditionalServiceDailyPrice() * totalRentDays;
		}
		return price;
	}

	private double calculateDeliveryPrice(int hireCityId, int returnCityId) throws BusinessException {

		double price = 0;

		if (hireCityId != returnCityId) {
			price = this.rentDetailsService.getDifferentCityDeliveryPrice();
		}

		return price;
	}

	private int calculateTotalRentDays(LocalDate rentDate, LocalDate confirmedPaidDate) {
		return (int) ChronoUnit.DAYS.between(rentDate, confirmedPaidDate);
	}

	@Override
	public void deleteAllByRentedCarId(int rentedCarId) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		this.invoiceDao.deleteAllByRentedCar_RentedCarId(rentedCarId);
	}

	private void checkIfInvoiceExists(int invoiceId) throws InvoiceNotFoundException {
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new InvoiceNotFoundException("invoice not found for this id" + invoiceId);
		}
	}

	private void checkIfInvoiceExistsByRentedCarId(int rentedCarId) throws InvoiceNotFoundException {
		if (this.invoiceDao.getByRentedCar_RentedCarId(rentedCarId) == null) {
			throw new InvoiceNotFoundException("invoice not found for this rented car id : " + rentedCarId);
		}
	}

	private void checkIfInvoiceAlreadyExistsByRentedCarId(int rentedCarId) throws InvoiceAlreadyExistsException {
		if (this.invoiceDao.getByRentedCar_RentedCarId(rentedCarId) != null) {
			throw new InvoiceAlreadyExistsException("invoice already exists for this rented car id : " + rentedCarId);
		}
	}

}
