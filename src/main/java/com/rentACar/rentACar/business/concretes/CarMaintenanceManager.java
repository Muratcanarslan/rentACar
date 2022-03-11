package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.GetCarMaintenanceDto;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
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
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		this.carService.checkIfExistByCarId(createCarMaintenanceRequest.getCarId());
		this.rentedCarService.checkIfCarIsAlreadyRentedByCarId(createCarMaintenanceRequest.getCarId());
		checkIfCarMaintenanceIsExistsByCarId(createCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("Car Maintenance Added");
	}

	@Override
	public Result delete(int carMaintenanceId) throws BusinessException {
		checkIfExistsByCarMaintenanceId(carMaintenanceId);

		this.carMaintenanceDao.deleteById(carMaintenanceId);

		return new SuccessResult("Car Maintenance Deleted");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		checkIfExistsByCarMaintenanceId(carMaintenance.getCarMaintenanceId());
		this.carService.checkIfExistByCarId(carMaintenance.getCar().getCarId());
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("Car Maintenance Updated");
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException {
		checkIfExistsByCarMaintenanceId(carMaintenanceId);
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
		GetCarMaintenanceDto carMaintenanceDto = this.modelMapperService.forDto().map(carMaintenance,
				GetCarMaintenanceDto.class);
		carMaintenanceDto.setCarId(carMaintenance.getCar().getCarId());
		return new SuccessDataResult<GetCarMaintenanceDto>(carMaintenanceDto, "car maintenance");
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll(){

		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll();

		List<CarMaintenanceListDto> carMaintenanceListDtos = carMaintenances.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(carMaintenanceListDtos, "car maintenance list");

	}

	private void checkIfExistsByCarMaintenanceId(int id) throws BusinessException {
		if (!this.carMaintenanceDao.existsByCarMaintenanceId(id)) {
			throw new BusinessException("CarMaintenance Not Found");
		}
	}


	@Override
	public void checkIfCarMaintenanceIsExistsByCarId(int carId) throws BusinessException {
		if (this.carMaintenanceDao.getByCar_CarIdAndCarMaintenanceReturnDateIsNull(carId) != null) {
			throw new BusinessException("Car is already in maintenance");
		}
	}

}
