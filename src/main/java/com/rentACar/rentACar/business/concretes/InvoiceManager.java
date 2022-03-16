package com.rentACar.rentACar.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.InvoiceDao;
import com.rentACar.rentACar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentedCarService rentedCarService;
	private CustomerService customerService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService,
			@Lazy RentedCarService rentedCarService, CustomerService customerService) {
		super();
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentedCarService = rentedCarService;
		this.customerService = customerService;
	}

	@Override
	public void addInvoice(int rentedCarId, double totalPrice, int totalRentDays) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest(totalRentDays, totalPrice, rentedCarId);

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		this.invoiceDao.save(invoice);

	}

	@Override
	public void updateInvoice(int rentedCarId, double totalPrice, int totalRentDays) throws BusinessException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		checkIfInvoiceExistsByRentedCarId(rentedCarId);

		Invoice invoice = this.invoiceDao.getByRentedCar_RentedCarId(rentedCarId);

		invoice.setTotalPrice(totalPrice);
		invoice.setTotalRentDays(totalRentDays);

		this.invoiceDao.save(invoice);
	}

	@Override
	public DataResult<GetInvoiceDto> getById(int invoiceId) throws InvoiceNotFoundException {

		checkIfInvoiceExists(invoiceId);

		Invoice invoice = this.invoiceDao.getById(invoiceId);

		GetInvoiceDto getInvoiceDto = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<GetInvoiceDto>(getInvoiceDto, "invoice");
	}

	@Override
	public DataResult<List<InvoiceCustomerListDto>> getInvoicesByCustomerId(int customerId) throws CustomerNotFoundException {

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
		
		List<InvoiceListDto> invoiceListDtos = invoices.stream().map(invoice->this.modelMapperService.forDto().map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
		

		return new SuccessDataResult<List<InvoiceListDto>>(invoiceListDtos,"get all");
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
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		
		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(createInvoiceRequest.getRentedCar_RentedCarId());
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		this.invoiceDao.save(invoice);

		return new SuccessResult("invoice added by controller");
	}

	@Override
	public Result delete(int invoiceId) throws InvoiceNotFoundException {
		checkIfInvoiceExists(invoiceId);

		this.invoiceDao.deleteById(invoiceId);

		return new SuccessResult("invoice deleted");
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



}
