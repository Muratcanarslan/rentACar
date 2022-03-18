package com.rentACar.rentACar.business.abstracts;

import java.util.Date;
import java.util.List;

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

public interface InvoiceService {

	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

	Result delete(int invoiceId) throws InvoiceNotFoundException;
	
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws InvoiceNotFoundException, BusinessException;

	DataResult<GetInvoiceDto> getById(int invoiceId) throws InvoiceNotFoundException;

	DataResult<GetInvoiceDto> getInvoiceByRentedCarId(int rentedCarId) throws BusinessException;

	DataResult<List<InvoiceCustomerListDto>> getInvoicesByCustomerId(int customerId) throws CustomerNotFoundException;

	DataResult<List<InvoiceDateBetweenDto>> getInvoicesDateBetween(Date startDate, Date endDate);

	DataResult<List<InvoiceListDto>> getAll();

	//TODO:methot ismi yanlış.
	void deleteAllByRentedCarId(int rentedCarId) throws BusinessException;

}
