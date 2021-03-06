package com.rentACar.rentACar.business.abstracts;

import java.util.Date;
import java.util.List;

import com.rentACar.rentACar.business.dtos.invoiceDtos.GetInvoiceDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceDateBetweenDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceRentedCarListDto;
import com.rentACar.rentACar.business.dtos.invoiceDtos.InvoiceCustomerListDto;
import com.rentACar.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.rentACar.rentACar.business.requests.invoiceRequests.DeleteInvoiceRequest;
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

	// TODO list dönen end pointlere pageable eklenmeli.

	int add(CreateInvoiceRequest createInvoiceRequest) throws RentedCarNotFoundException,
			AdditionalServiceNotFoundException, CarNotFoundException, RentDetailsNotFoundException;

	int addForDelayedReturn(int rentedCarId) throws RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException;

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws InvoiceNotFoundException;

	Result update(UpdateInvoiceRequest updateInvoiceRequest)
			throws InvoiceNotFoundException, RentedCarNotFoundException, AdditionalServiceNotFoundException,
			CarNotFoundException, RentDetailsNotFoundException;

	DataResult<GetInvoiceDto> getById(int invoiceId) throws InvoiceNotFoundException;

	DataResult<List<InvoiceRentedCarListDto>> getInvoiceByRentedCarId(int rentedCarId)
			throws RentedCarNotFoundException, InvoiceNotFoundException;

	DataResult<List<InvoiceCustomerListDto>> getInvoicesByCustomerId(int customerId) throws CustomerNotFoundException;

	DataResult<List<InvoiceDateBetweenDto>> getInvoicesDateBetween(Date startDate, Date endDate);

	DataResult<List<InvoiceListDto>> getAll(int pageNo, int pageSize);

	CreateInvoiceRequest getInvoiceRequestForMapping(int rentedCarId) throws RentedCarNotFoundException;

	double calculateDeliveryPrice(int hireCityId, int returnCityId) throws RentDetailsNotFoundException;

	double calculateTotalPriceForDelayedReturn(int rentedCarId, int carId, int totalRentDays)
			throws AdditionalServiceNotFoundException, CarNotFoundException, RentDetailsNotFoundException,
			RentedCarNotFoundException;

	void checkIfInvoiceExists(int invoiceId) throws InvoiceNotFoundException;

	// TODO:methot ismi yanlış.
	void deleteAllByRentedCarId(int rentedCarId) throws RentedCarNotFoundException;

}
