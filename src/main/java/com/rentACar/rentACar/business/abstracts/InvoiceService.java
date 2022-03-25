package com.rentACar.rentACar.business.abstracts;

import java.util.Date;
import java.util.List;

import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions.InvoiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions.RentDetailsNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface InvoiceService {

	int add(CreateInvoiceRequest createInvoiceRequest) throws RentedCarNotFoundException,
			AdditionalServiceNotFoundException, CarNotFoundException, RentDetailsNotFoundException;

	int addForDelayedReturn(int rentedCarId)
			throws RentedCarNotFoundException, AdditionalServiceNotFoundException, CarNotFoundException,
			RentDetailsNotFoundException;

	Result delete(int invoiceId) throws InvoiceNotFoundException;

	Result update(UpdateInvoiceRequest updateInvoiceRequest)
			throws InvoiceNotFoundException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException;

	DataResult<GetInvoiceDto> getById(int invoiceId) throws InvoiceNotFoundException;

	DataResult<GetInvoiceDto> getInvoiceByRentedCarId(int rentedCarId)
			throws RentedCarNotFoundException, InvoiceNotFoundException;

	DataResult<List<InvoiceCustomerListDto>> getInvoicesByCustomerId(int customerId) throws CustomerNotFoundException;

	DataResult<List<InvoiceDateBetweenDto>> getInvoicesDateBetween(Date startDate, Date endDate);

	DataResult<List<InvoiceListDto>> getAll();
	
	CreateInvoiceRequest getInvoiceRequestForMapping(int rentedCarId) throws RentedCarNotFoundException;

	void checkIfInvoiceExists(int invoiceId) throws InvoiceNotFoundException;

	// TODO:methot ismi yanlış.
	void deleteAllByRentedCarId(int rentedCarId) throws RentedCarNotFoundException;

}
