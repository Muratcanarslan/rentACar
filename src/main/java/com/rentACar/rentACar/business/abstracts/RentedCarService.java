package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListForCustomerDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForIndividualCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.DeleteRentedCarRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarForDelayedReturnRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentUpdateNotRequiresPaymentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentUpdateRequiresPaymentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarAlreadyReturnException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.entities.concretes.RentedCar;

public interface RentedCarService {

	int addForIndividualCustomer(CreateRentedCarRequestForIndividualCustomer createRentedCarRequest)
			throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException,
			IndividualCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException;

	int addForCorporateCustomer(CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer)
			throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException,
			CarAlreadyInRentException, CityNotFoundException, CustomerNotFoundException;

	Result updateForValidReturn(UpdateRentedCarRequest updateRentedCarRequest) throws RentUpdateRequiresPaymentException, RentedCarNotFoundException, CarNotFoundException;

	Result updateRentedCarForDelayedReturn(
			UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest) throws RentedCarNotFoundException, CarNotFoundException, RentUpdateNotRequiresPaymentException;

	Result delete(DeleteRentedCarRequest deleteRentedCarRequest) throws RentedCarNotFoundException;

	DataResult<GetRentedCarDto> getById(int rentedCarId) throws RentedCarNotFoundException;
	
	DataResult<List<RentedCarListForCustomerDto>> getByCustomerId(int customerId) throws CustomerNotFoundException;
	
	DataResult<List<RentedCarListDto>> getAll(int pageNo,int pageSize);
	
	RentedCar getRentedCarForBusiness(int rentedCarId) throws RentedCarNotFoundException;

	void checkIfCarIsAlreadyRentedByCarId(int carId) throws CarAlreadyInRentException;

	void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws RentedCarNotFoundException;
	
	void checkIfRentedCarAlreadyReturn(int rentedCarId) throws RentedCarNotFoundException, RentedCarAlreadyReturnException;
}
