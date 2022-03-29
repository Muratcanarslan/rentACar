package com.rentACar.rentACar.api.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceRentedCarListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.DeleteInvoiceRequest;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
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
	public DataResult<List<InvoiceRentedCarListDto>> getByRentedCarId(@RequestParam int rentedCarId)
			throws BusinessException {
		return this.invoiceService.getInvoiceByRentedCarId(rentedCarId);
	}

	@GetMapping("/getDateBetween")
	public DataResult<List<InvoiceDateBetweenDto>> getByDateBetween(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		return this.invoiceService.getInvoicesDateBetween(startDate, endDate);
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		return this.invoiceService.getAll(pageNo, pageSize);

	}

	@DeleteMapping("/deleteByInvoiceId")
	public Result deleteById(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest)
			throws InvoiceNotFoundException {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

}
