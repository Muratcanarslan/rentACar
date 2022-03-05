package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
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
public class RentedCarManager implements RentedCarService {

	private RentedCarDao rentedCarDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public RentedCarManager(RentedCarDao rentedCarDao, ModelMapperService modelMapperService, CarService carService,
			CarMaintenanceService carMaintenanceService) {
		this.rentedCarDao = rentedCarDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.carMaintenanceService = carMaintenanceService;
	}

	@Override
	public Result add(CreateRentedCarRequest createRentedCarRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(createRentedCarRequest.getCarId());
		this.carMaintenanceService.checkIfCarMaintenanceIsExistsByCarId(createRentedCarRequest.getCarId());
		checkIfCarIsAlreadyRentedByCarId(createRentedCarRequest.getCarId());
		
		RentedCar rentedCar = this.modelMapperService.forRequest().map(createRentedCarRequest, RentedCar.class);
		
		this.rentedCarDao.save(rentedCar);
		
		return new SuccessResult("rented car added");
	}

	@Override
	public Result update(UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException {
		this.carService.checkIfExistByCarId(updateRentedCarRequest.getCarId());
		checkIfRentedCarIsExistsByRentedCarId(updateRentedCarRequest.getRentedCarId());
		
		RentedCar rentedCar = this.modelMapperService.forRequest().map(updateRentedCarRequest, RentedCar.class);
		
		this.rentedCarDao.save(rentedCar);
		
		return new SuccessResult("Rented Car Updated");
		
	}

	@Override
	public Result delete(int rentedCarId) throws BusinessException {
		checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		
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
	public DataResult<List<RentedCarListDto>> getAll() throws BusinessException {
        
		List<RentedCar> rentedCars = this.rentedCarDao.findAll();
		
		checkIfRentedCarListIsEmpty(rentedCars);
		
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
	
	public void checkIfRentedCarListIsEmpty(List<RentedCar> rentedCars) throws BusinessException {
		if(rentedCars.isEmpty()) {
			throw new BusinessException("No Rented Car Found");
		}
	}

}
