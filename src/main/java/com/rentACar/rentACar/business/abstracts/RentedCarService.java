package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForIndividualCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarForDelayedReturnRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.entities.concretes.RentedCar;

public interface RentedCarService {

	int addForIndividualCustomer(CreateRentedCarRequestForIndividualCustomer createRentedCarRequest) throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException;

	int addForCorporateCustomer(
			CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer)
			throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException,
			CarAlreadyInRentException, CityNotFoundException, CustomerNotFoundException;

	Result update(UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException;

	Result updateRentedCarForDelayedReturn(
			UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest) throws CarNotFoundException, RentedCarNotFoundException, CustomerNotFoundException;

	Result delete(int rentedCarId) throws RentedCarNotFoundException;

	DataResult<GetRentedCarDto> getById(int rentedCarId) throws RentedCarNotFoundException;

	RentedCar getRentedCarForBusiness(int rentedCarId) throws RentedCarNotFoundException;

	DataResult<List<RentedCarListDto>> getAll();

	void checkIfCarIsAlreadyRentedByCarId(int carId) throws CarAlreadyInRentException;

	void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws RentedCarNotFoundException;
}
