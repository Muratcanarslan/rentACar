package com.rentACar.rentACar.api.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	public InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getById")
	public DataResult<GetInvoiceDto> getById(@RequestParam int invoiceId) throws InvoiceNotFoundException {
		return this.invoiceService.getById(invoiceId);
	}

	@GetMapping("/getByCustomerId")
	public DataResult<List<InvoiceCustomerListDto>> getByCustomerId(@RequestParam int customerId)
			throws CustomerNotFoundException {
		return this.invoiceService.getInvoicesByCustomerId(customerId);
	}

	@GetMapping("/getInvoiceByRentedCarId")
	public DataResult<GetInvoiceDto> getByRentedCarId(@RequestParam int rentedCarId) throws BusinessException {
		return this.invoiceService.getInvoiceByRentedCarId(rentedCarId);
	}

	@GetMapping("/getDateBetween")
	public DataResult<List<InvoiceDateBetweenDto>> getByDateBetween(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		return this.invoiceService.getInvoicesDateBetween(startDate, endDate);
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceListDto>> getAll() {

		return this.invoiceService.getAll();

	}

	@DeleteMapping("/deleteByInvoiceId")
	public Result deleteById(@RequestParam int invoiceId) throws InvoiceNotFoundException {
		return this.invoiceService.delete(invoiceId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoiceService.add(createInvoiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest invoiceRequest) throws InvoiceNotFoundException, BusinessException {
		return this.invoiceService.update(invoiceRequest);
	}

}
