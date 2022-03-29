package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.GetCarMaintenanceDto;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarMaintenanceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.rentACar.rentACar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentedCarService rentedCarService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			CarService carService,@Lazy RentedCarService rentedCarService) {
		super();
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentedCarService = rentedCarService;
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws CarNotFoundException, CarAlreadyInMaintenanceException, CarAlreadyInRentException  {
		
		this.carService.checkIfExistByCarId(createCarMaintenanceRequest.getCarId());
		this.rentedCarService.checkIfCarIsAlreadyRentedByCarId(createCarMaintenanceRequest.getCarId());
		checkIfCarMaintenanceIsExistsByCarId(createCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result delete(int carMaintenanceId) throws CarMaintenanceNotFoundException  {
		checkIfExistsByCarMaintenanceId(carMaintenanceId);

		this.carMaintenanceDao.deleteById(carMaintenanceId);

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws CarMaintenanceNotFoundException, CarNotFoundException  {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		checkIfExistsByCarMaintenanceId(carMaintenance.getCarMaintenanceId());
		this.carService.checkIfExistByCarId(carMaintenance.getCar().getCarId());
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws CarMaintenanceNotFoundException  {
		checkIfExistsByCarMaintenanceId(carMaintenanceId);
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
		GetCarMaintenanceDto carMaintenanceDto = this.modelMapperService.forDto().map(carMaintenance,
				GetCarMaintenanceDto.class);
		carMaintenanceDto.setCarId(carMaintenance.getCar().getCarId());
		return new SuccessDataResult<GetCarMaintenanceDto>(carMaintenanceDto, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll(int pageNo,int pageSize){

		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll(pageable).getContent();

		List<CarMaintenanceListDto> carMaintenanceListDtos = carMaintenances.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(carMaintenanceListDtos, BusinessMessages.GET_SUCCESSFUL);

	}

	private void checkIfExistsByCarMaintenanceId(int id) throws CarMaintenanceNotFoundException  {
		if (!this.carMaintenanceDao.existsByCarMaintenanceId(id)) {
			throw new CarMaintenanceNotFoundException(BusinessMessages.CAR_MAINTENANCE_NOT_FOUND + id);
		}
	}


	@Override
	public void checkIfCarMaintenanceIsExistsByCarId(int carId) throws CarAlreadyInMaintenanceException  {
		if (this.carMaintenanceDao.getByCar_CarIdAndCarMaintenanceReturnDateIsNull(carId) != null) {
			throw new CarAlreadyInMaintenanceException(BusinessMessages.CAR_ALREADY_IN_MAINTENANCE);
		}
	}

}
