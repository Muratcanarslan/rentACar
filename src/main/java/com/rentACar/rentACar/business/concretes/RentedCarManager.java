package com.rentACar.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.CityService;
import com.rentACar.rentACar.business.abstracts.CorporateCustomerService;
import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.IndividualCustomerService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
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
	private IndividualCustomerService individualCustomerService;
	private CustomerService customerService;
	private CorporateCustomerService corporateCustomerService;
	private CityService cityService;

	@Autowired
	public RentedCarManager(RentedCarDao rentedCarDao, ModelMapperService modelMapperService, CarService carService,
			CarMaintenanceService carMaintenanceService, IndividualCustomerService individualCustomerService,
			CustomerService customerService, CorporateCustomerService corporateCustomerService, CityService cityService

	) {
		this.rentedCarDao = rentedCarDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.carMaintenanceService = carMaintenanceService;
		this.individualCustomerService = individualCustomerService;
		this.customerService = customerService;
		this.corporateCustomerService = corporateCustomerService;
		this.cityService = cityService;
	}

	@Override
	public int addForIndividualCustomer(CreateRentedCarRequestForIndividualCustomer createRentedCarRequest)
			throws CarNotFoundException, CarAlreadyInRentException, CarAlreadyInMaintenanceException,
			IndividualCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException {

		this.carService.checkIfExistByCarId(createRentedCarRequest.getCarId());
		this.carMaintenanceService.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequest.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequest.getCarId());
		this.individualCustomerService.checkIfIndividualCustomerExistsById(createRentedCarRequest.getCustomerId());
		this.cityService.checkIfCityExists(createRentedCarRequest.getHireCityId());
		this.cityService.checkIfCityExists(createRentedCarRequest.getReturnCityId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequest, RentedCar.class);

		rentedCar.setCustomer(getCustomerForMapping(createRentedCarRequest.getCustomerId()));

		rentedCar.setRentKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		rentedCar.setRentedCarId(0);

		RentedCar savedRentedCar = this.rentedCarDao.save(rentedCar);

		return savedRentedCar.getRentedCarId();
	}

	@Override
	public int addForCorporateCustomer(
			CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer)
			throws CarNotFoundException, CorporateCustomerNotFoundException, CarAlreadyInMaintenanceException,
			CarAlreadyInRentException, CityNotFoundException, CustomerNotFoundException {

		this.carService.checkIfExistByCarId(createRentedCarRequestForCorporateCustomer.getCarId());
		this.corporateCustomerService
				.checkIfCorporateCustomerExists(createRentedCarRequestForCorporateCustomer.getCustomerId());
		this.carMaintenanceService
				.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequestForCorporateCustomer.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequestForCorporateCustomer.getCarId());
		this.cityService.checkIfCityExists(createRentedCarRequestForCorporateCustomer.getHireCityId());
		this.cityService.checkIfCityExists(createRentedCarRequestForCorporateCustomer.getReturnCityId());

		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequestForCorporateCustomer,
				RentedCar.class);

		rentedCar.setRentKilometre(
				this.carService.getById(rentedCar.getCar().getCarId()).getData().getKilometreInformation());

		rentedCar.setCustomer(getCustomerForMapping(createRentedCarRequestForCorporateCustomer.getCustomerId()));

		rentedCar.setRentedCarId(0);

		RentedCar savedRentedCar = this.rentedCarDao.save(rentedCar);

		return savedRentedCar.getRentedCarId();

	}

	@Override
	public Result updateForValidReturn(UpdateRentedCarRequest updateRentedCarRequest)
			throws RentUpdateRequiresPaymentException, RentedCarNotFoundException, CarNotFoundException {

		this.checkIfRentedCarIsExistsByRentedCarId(updateRentedCarRequest.getRentedCarId());

		RentedCar rentedCar = this.rentedCarDao.getById(updateRentedCarRequest.getRentedCarId());

		checkIfReturnDateIsValidReturn(rentedCar.getConfirmedPaidedDate(), updateRentedCarRequest.getReturnDate());

		rentedCar.setReturnDate(updateRentedCarRequest.getReturnDate());
		rentedCar.setReturnKilometre(updateRentedCarRequest.getReturnKilometre());

		this.carService.updateKilometreInformation(updateRentedCarRequest.getCar_CarId(),
				updateRentedCarRequest.getReturnKilometre());

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);

	}

	@Override
	public Result updateRentedCarForDelayedReturn(
			UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest)
			throws RentedCarNotFoundException, CarNotFoundException, RentUpdateNotRequiresPaymentException {

		this.checkIfRentedCarIsExistsByRentedCarId(updateRentedCarForDelayedReturnRequest.getRentedCarId());

		RentedCar rentedCar = this.rentedCarDao.getById(updateRentedCarForDelayedReturnRequest.getRentedCarId());

		checkIfReturnDateIsDelayedReturn(rentedCar.getConfirmedPaidedDate(),
				updateRentedCarForDelayedReturnRequest.getReturnDate());

		rentedCar.setReturnDate(updateRentedCarForDelayedReturnRequest.getReturnDate());
		rentedCar.setReturnKilometre(updateRentedCarForDelayedReturnRequest.getReturnKilometre());

		this.carService.updateKilometreInformation(updateRentedCarForDelayedReturnRequest.getCar_CarId(),
				updateRentedCarForDelayedReturnRequest.getReturnKilometre());

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public Result delete(DeleteRentedCarRequest deleteRentedCarRequest) throws RentedCarNotFoundException {

		checkIfRentedCarIsExistsByRentedCarId(deleteRentedCarRequest.getRentedCarId());

		this.rentedCarDao.deleteById(deleteRentedCarRequest.getRentedCarId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public DataResult<GetRentedCarDto> getById(int rentedCarId) throws RentedCarNotFoundException {

		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		RentedCar rentedCar = this.rentedCarDao.getById(rentedCarId);

		GetRentedCarDto getRentedCarDto = this.modelMapperService.forDto().map(rentedCar, GetRentedCarDto.class);

		return new SuccessDataResult<GetRentedCarDto>(getRentedCarDto, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<RentedCarListForCustomerDto>> getByCustomerId(int customerId)
			throws CustomerNotFoundException {

		this.customerService.checkIfCustomerExists(customerId);

		List<RentedCar> rentedCars = this.rentedCarDao.getByCustomer_CustomerId(customerId);

		List<RentedCarListForCustomerDto> rentedCarListForCustomerDtos = rentedCars.stream()
				.map(rentedCar -> this.modelMapperService.forDto().map(rentedCar, RentedCarListForCustomerDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentedCarListForCustomerDto>>(rentedCarListForCustomerDtos,
				BusinessMessages.GET_SUCCESSFUL);

	}

	@Override
	public DataResult<List<RentedCarListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<RentedCar> rentedCars = this.rentedCarDao.findAll(pageable).getContent();

		List<RentedCarListDto> rentedCarListDtos = rentedCars.stream()
				.map(rentedCar -> this.modelMapperService.forDto().map(rentedCar, RentedCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentedCarListDto>>(rentedCarListDtos, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public RentedCar getRentedCarForBusiness(int rentedCarId) throws RentedCarNotFoundException {
		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		RentedCar rentedCar = this.rentedCarDao.getById(rentedCarId);

		return rentedCar;
	}

	public void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws RentedCarNotFoundException {
		if (!this.rentedCarDao.existsById(rentedCarId)) {
			throw new RentedCarNotFoundException(BusinessMessages.CAR_RENT_NOT_FOUND + rentedCarId);
		}
	}

	public void checkIfCarIsAlreadyRentedByCarId(int carId) throws CarAlreadyInRentException {
		if (this.rentedCarDao.getByCar_CarIdAndReturnDateIsNull(carId) != null) {
			throw new CarAlreadyInRentException(BusinessMessages.CAR_ALREADY_IN_RENT);
		}
	}

	private Customer getCustomerForMapping(int customerId) throws CustomerNotFoundException {
		return this.customerService.getCustomerById(customerId);
	}

	private void checkIfReturnDateIsValidReturn(LocalDate paidDate, LocalDate returnDate)
			throws RentUpdateRequiresPaymentException {
		if (ChronoUnit.DAYS.between(paidDate, returnDate) > 0) {
			throw new RentUpdateRequiresPaymentException(BusinessMessages.UPDATE_RENT_REQUIRES_PAYMENT);
		}

	}

	private void checkIfReturnDateIsDelayedReturn(LocalDate paidDate, LocalDate returnDate)
			throws RentUpdateNotRequiresPaymentException {
		if (ChronoUnit.DAYS.between(paidDate, returnDate) <= 0) {
			throw new RentUpdateNotRequiresPaymentException(BusinessMessages.UPDATE_RENT_NOT_REQUIRES_PAYMENT);
		}
	}

	@Override
	public void checkIfRentedCarAlreadyReturn(int rentedCarId)
			throws RentedCarNotFoundException, RentedCarAlreadyReturnException {

		this.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		if (this.rentedCarDao.getById(rentedCarId).getReturnDate() != null) {
			throw new RentedCarAlreadyReturnException(BusinessMessages.RENTED_CAR_ALREADY_RETURN);
		}
	}

}
