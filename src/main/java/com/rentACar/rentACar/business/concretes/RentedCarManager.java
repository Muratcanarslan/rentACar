package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.CorporateCustomerService;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.IndividualCustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForIndividualCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarForDelayedReturnRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.RentedCarDao;
import com.rentACar.rentACar.entities.concretes.Customer;
import com.rentACar.rentACar.entities.concretes.RentedCar;

@Service
@Transactional
public class RentedCarManager implements RentedCarService {

	private RentedCarDao rentedCarDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private CarMaintenanceService carMaintenanceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private InvoiceService invoiceService;
	private IndividualCustomerService individualCustomerService;
	private CustomerService customerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public RentedCarManager(RentedCarDao rentedCarDao, ModelMapperService modelMapperService, CarService carService,
			CarMaintenanceService carMaintenanceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService, InvoiceService invoiceService,
			IndividualCustomerService individualCustomerService, CustomerService customerService,
			CorporateCustomerService corporateCustomerService

	) {
		this.rentedCarDao = rentedCarDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.invoiceService = invoiceService;
		this.individualCustomerService = individualCustomerService;
		this.customerService = customerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public Result addForIndividualCustomer(CreateRentedCarRequestForIndividualCustomer createRentedCarRequest) throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException, IndividualCustomerNotFoundException, CustomerNotFoundException
			 {

		this.carService.checkIfExistByCarId(createRentedCarRequest.getCarId());
		this.carMaintenanceService.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequest.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequest.getCarId());
		this.individualCustomerService.checkIfIndividualCustomerExistsById(createRentedCarRequest.getCustomerId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequest, RentedCar.class);

		rentedCar.setCustomer(getCustomerForMapping(createRentedCarRequest.getCustomerId()));

		rentedCar.setRentKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		this.rentedCarDao.save(rentedCar);

		return new SuccessResult("rented car added for individual customer");
	}

	@Override
	public Result addForCorporateCustomer(
			CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer) throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException, CarAlreadyInRentException
			 {

		this.carService.checkIfExistByCarId(createRentedCarRequestForCorporateCustomer.getCarId());
		this.corporateCustomerService
				.checkIfCorporateCustomerExists(createRentedCarRequestForCorporateCustomer.getCustomerId());
		this.carMaintenanceService
				.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequestForCorporateCustomer.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequestForCorporateCustomer.getCarId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequestForCorporateCustomer,
				RentedCar.class);

		rentedCar.setRentKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		this.rentedCarDao.save(rentedCar);

		return new SuccessResult("rented car added for corporate customer");
	}

	@Override
	public Result update(UpdateRentedCarRequest updateRentedCarRequest) throws CarNotFoundException, CustomerNotFoundException, RentedCarNotFoundException  {

		this.carService.checkIfExistByCarId(updateRentedCarRequest.getCarId());
		checkIfRentedCarIsExistsByRentedCarId(updateRentedCarRequest.getRentedCarId());
		this.customerService.checkIfCustomerExists(updateRentedCarRequest.getCustomerId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(updateRentedCarRequest, RentedCar.class);

		rentedCar.setCustomer(getCustomerForMapping(updateRentedCarRequest.getCustomerId()));

		rentedCar.setRentKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		this.carService.updateKilometreInformation(updateRentedCarRequest.getCarId(),
				updateRentedCarRequest.getReturnKilometre());

		this.rentedCarDao.save(rentedCar);

		return new SuccessResult("Rented Car Updated");

	}
	
	@Override
	public Result updateRentedCarForDelayedReturn(
			UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest) throws CarNotFoundException, RentedCarNotFoundException, CustomerNotFoundException  {
		
		this.carService.checkIfExistByCarId(updateRentedCarForDelayedReturnRequest.getCarId());
		checkIfRentedCarIsExistsByRentedCarId(updateRentedCarForDelayedReturnRequest.getRentedCarId());
		this.customerService.checkIfCustomerExists(updateRentedCarForDelayedReturnRequest.getCustomerId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(updateRentedCarForDelayedReturnRequest, RentedCar.class);

		rentedCar.setCustomer(getCustomerForMapping(updateRentedCarForDelayedReturnRequest.getCustomerId()));

		this.carService.updateKilometreInformation(updateRentedCarForDelayedReturnRequest.getCarId(),
				updateRentedCarForDelayedReturnRequest.getReturnKilometre());
		
		rentedCar.setReturnKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		this.rentedCarDao.save(rentedCar);

		return new SuccessResult("Rented Car Updated");
	}

	@Override
	public Result delete(int rentedCarId) throws RentedCarNotFoundException  {

		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		this.orderedAdditionalServiceService.deleteOrderedAdditionalServicesByRentedCarId(rentedCarId);

		this.invoiceService.deleteAllByRentedCarId(rentedCarId);

		this.rentedCarDao.deleteById(rentedCarId);

		return new SuccessResult("Rented Car Deleted");
	}

	@Override
	public DataResult<GetRentedCarDto> getById(int rentedCarId) throws RentedCarNotFoundException  {

		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		RentedCar rentedCar = this.rentedCarDao.getById(rentedCarId);

		GetRentedCarDto getRentedCarDto = this.modelMapperService.forDto().map(rentedCar, GetRentedCarDto.class);

		return new SuccessDataResult<GetRentedCarDto>(getRentedCarDto, "Get Rented Car Dto");
	}

	@Override
	public DataResult<List<RentedCarListDto>> getAll() {

		List<RentedCar> rentedCars = this.rentedCarDao.findAll();

		List<RentedCarListDto> rentedCarListDtos = rentedCars.stream()
				.map(rentedCar -> this.modelMapperService.forDto().map(rentedCar, RentedCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentedCarListDto>>(rentedCarListDtos, "rented cars");
	}

	@Override
	public RentedCar getRentedCarForBusiness(int rentedCarId) throws RentedCarNotFoundException  {
		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		RentedCar rentedCar = this.rentedCarDao.getById(rentedCarId);

		return rentedCar;
	}

	public void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws RentedCarNotFoundException  {
		if (!this.rentedCarDao.existsById(rentedCarId)) {
			throw new RentedCarNotFoundException(BusinessMessages.CAR_RENT_NOT_FOUND + rentedCarId);
		}
	}

	public void checkIfCarIsAlreadyRentedByCarId(int carId) throws CarAlreadyInRentException  {
		if (this.rentedCarDao.getByCar_CarIdAndReturnDateIsNull(carId) != null) {
			throw new CarAlreadyInRentException(BusinessMessages.CAR_ALREADY_IN_RENT);
		}
	}

	private Customer getCustomerForMapping(int customerId) throws CustomerNotFoundException {
		return this.customerService.getCustomerById(customerId);
	}

	

}
