package com.rentACar.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.RentDetailsService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.RentedCarDao;
import com.rentACar.rentACar.entities.concretes.RentedCar;

@Service
@Transactional
public class RentedCarManager implements RentedCarService {

	private RentedCarDao rentedCarDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private CarMaintenanceService carMaintenanceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;
	private RentDetailsService rentDetailsService;
	
	@Autowired
	public RentedCarManager(RentedCarDao rentedCarDao, ModelMapperService modelMapperService, CarService carService,
			CarMaintenanceService carMaintenanceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			AdditionalServiceService additionalServiceService,
			RentDetailsService rentDetailsService
			) {
		this.rentedCarDao = rentedCarDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
		this.rentDetailsService = rentDetailsService;
	}

	@Override
	public Result add(CreateRentedCarRequest createRentedCarRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(createRentedCarRequest.getCarId());
		this.carMaintenanceService.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequest.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequest.getCarId());
		
		double totalPrice = calculateTotalPrice(createRentedCarRequest.getRentDate(),
				createRentedCarRequest.getConfirmedPaidedDate(),
				createRentedCarRequest.getAdditionalServiceIds(),
				createRentedCarRequest.getCarId(),
				createRentedCarRequest.getHireCityId(),
				createRentedCarRequest.getReturnCityId()
				);
		
		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequest, RentedCar.class);
		
		rentedCar.setTotalPrice(totalPrice);
		
		RentedCar savedRentedCar = this.rentedCarDao.save(rentedCar);
		
		this.orderedAdditionalServiceService.addOrderedAdditionalServicesByAdditionalIdListAndRentedCarId(createRentedCarRequest.getAdditionalServiceIds(), savedRentedCar.getRentedCarId());
		
		return new SuccessResult("rented car added");
	}

	@Override
	public Result update(UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(updateRentedCarRequest.getCarId());
		
		checkIfRentedCarIsExistsByRentedCarId(updateRentedCarRequest.getRentedCarId());
		
		this.orderedAdditionalServiceService.deleteOrderedAdditionalServicesByRentedCarId(updateRentedCarRequest.getRentedCarId());
	
		this.orderedAdditionalServiceService.addOrderedAdditionalServicesByAdditionalIdListAndRentedCarId(updateRentedCarRequest.getAdditionalServiceIds(), updateRentedCarRequest.getRentedCarId());
		
		RentedCar rentedCar = this.modelMapperService.forRequest().map(updateRentedCarRequest, RentedCar.class);
		
		double totalPrice = calculateTotalPrice(updateRentedCarRequest.getRentDate(),
				updateRentedCarRequest.getConfirmedPaidedDate()
				,updateRentedCarRequest.getAdditionalServiceIds()
				,updateRentedCarRequest.getCarId(),
				updateRentedCarRequest.getHireCityId(),
				updateRentedCarRequest.getReturnCityId()
				);
		
		rentedCar.setTotalPrice(totalPrice);
		
		this.rentedCarDao.save(rentedCar);
		
		return new SuccessResult("Rented Car Updated");
		
	}

	@Override
	public Result delete(int rentedCarId) throws BusinessException {
		
		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		
		this.orderedAdditionalServiceService.deleteOrderedAdditionalServicesByRentedCarId(rentedCarId);
		
		this.rentedCarDao.deleteById(rentedCarId);
		
		return new SuccessResult("Rented Car Deleted");
	}

	@Override
	public DataResult<GetRentedCarDto> getById(int rentedCarId) throws BusinessException {
		
		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		
		RentedCar rentedCar = this.rentedCarDao.getById(rentedCarId);
		
		GetRentedCarDto getRentedCarDto = this.modelMapperService.forDto().map(rentedCar, GetRentedCarDto.class);
		
		return new SuccessDataResult<GetRentedCarDto>(getRentedCarDto,"Get Rented Car Dto");
	}

	@Override
	public DataResult<List<RentedCarListDto>> getAll(){
        
		List<RentedCar> rentedCars = this.rentedCarDao.findAll();
		
		List<RentedCarListDto> rentedCarListDtos = rentedCars.stream().map(rentedCar -> this.modelMapperService.forDto().map(rentedCar, RentedCarListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentedCarListDto>>(rentedCarListDtos,"rented cars");
	}
	
	public void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws BusinessException {
		if(!this.rentedCarDao.existsById(rentedCarId)) {
			throw new BusinessException("Rent information is not founded");
		}
	}
	
	public void checkIfCarIsAlreadyRentedByCarId(int carId) throws BusinessException {
		if(this.rentedCarDao.getByCar_CarIdAndReturnDateIsNull(carId)!=null) {
			throw new BusinessException("Car is Already Rented");
		}
	}
	
	
	private double calculateTotalPrice(LocalDate rentDate,LocalDate confirmedPaidDate,List<Integer> additionalServiceIds,int carId,int hireCityId,int returnCityId) throws BusinessException {
		
		int rentedDayValue = (int) ChronoUnit.DAYS.between(rentDate,confirmedPaidDate);
		
		double carRentedPrice = this.carService.calculateRentPriceByCarIdAndRentDateValue(carId, rentedDayValue);
		
		double additionalServicePrice = this.additionalServiceService.calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(additionalServiceIds, rentedDayValue);
		
		double deliveryPrice = calculateDeliveryPrice(hireCityId, returnCityId);
		
		return carRentedPrice + additionalServicePrice + deliveryPrice;
		
	}
	
	private double calculateDeliveryPrice(int hireCityId,int returnCityId) throws BusinessException {
		
		double price = 0;
		
		if(hireCityId != returnCityId) {
			price = this.rentDetailsService.getDifferentCityDeliveryPrice();
		}
		
		return price;
	}
	

}
