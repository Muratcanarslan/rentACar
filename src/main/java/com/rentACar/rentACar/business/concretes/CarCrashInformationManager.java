package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarCrashInformationService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListByCarDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.GetCarCrashInformationDto;
import com.rentACar.rentACar.business.requests.carCrashRequests.CreateCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.UpdateCarCrashInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions.CarCrashInformationNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CarCrashInformationDao;
import com.rentACar.rentACar.entities.concretes.CarCrashInformation;

@Service
public class CarCrashInformationManager implements CarCrashInformationService {

	private CarCrashInformationDao carCrashInformationDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	
	@Autowired
	public CarCrashInformationManager(CarCrashInformationDao carCrashInformationDao,
			ModelMapperService modelMapperService, CarService carService) {
		super();
		this.carCrashInformationDao = carCrashInformationDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}


	@Override
	public Result add(CreateCarCrashInformationRequest createCarCrashInformationRequest) throws BusinessException {

		this.carService.checkIfExistByCarId(createCarCrashInformationRequest.getCarId());

		CarCrashInformation carCrashInformation = this.modelMapperService.forRequest()
				.map(createCarCrashInformationRequest, CarCrashInformation.class);

		this.carCrashInformationDao.save(carCrashInformation);

		return new SuccessResult("car crash information added");
	}

	@Override
	public Result update(UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws BusinessException {

		checkIfCarCrashInformationExists(updateCarCrashInformationRequest.getCarCrashInformationId());
		this.carService.checkIfExistByCarId(updateCarCrashInformationRequest.getCarId());

		CarCrashInformation carCrashInformation = this.modelMapperService.forRequest()
				.map(updateCarCrashInformationRequest, CarCrashInformation.class);

		this.carCrashInformationDao.save(carCrashInformation);

		return new SuccessResult("car crash information updated");
	}

	@Override
	public Result delete(int carCrashInformationId) throws CarCrashInformationNotFoundException {

		checkIfCarCrashInformationExists(carCrashInformationId);

		this.carCrashInformationDao.deleteById(carCrashInformationId);

		return new SuccessResult("car crash information deleted");
	}

	@Override
	public DataResult<GetCarCrashInformationDto> getById(int carCrashInformationId)
			throws CarCrashInformationNotFoundException {

		checkIfCarCrashInformationExists(carCrashInformationId);

		CarCrashInformation carCrashInformation = this.carCrashInformationDao.getById(carCrashInformationId);

		GetCarCrashInformationDto getCarCrashInformationDto = this.modelMapperService.forDto().map(carCrashInformation,
				GetCarCrashInformationDto.class);

		return new SuccessDataResult<GetCarCrashInformationDto>(getCarCrashInformationDto, "get car crash infomation");
	}

	@Override
	public DataResult<List<CarCrashInformationListByCarDto>> getByCarId(int carId) throws BusinessException {

		this.carService.checkIfExistByCarId(carId);

		List<CarCrashInformation> carCrashInformations = this.carCrashInformationDao.getByCar_CarId(carId);

		List<CarCrashInformationListByCarDto> carCrashInformationListByCarDtos = carCrashInformations.stream()
				.map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation,
						CarCrashInformationListByCarDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarCrashInformationListByCarDto>>(carCrashInformationListByCarDtos,
				"get by car id");
	}

	@Override
	public DataResult<List<CarCrashInformationListDto>> getAll() {

		List<CarCrashInformation> carCrashInformations = this.carCrashInformationDao.findAll();

		List<CarCrashInformationListDto> carCrashInformationListDtos = carCrashInformations.stream()
				.map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation,
						CarCrashInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarCrashInformationListDto>>(carCrashInformationListDtos, "get all");
	}


	private void checkIfCarCrashInformationExists(int carCrashInformationId)
			throws CarCrashInformationNotFoundException {
		if (!this.carCrashInformationDao.existsById(carCrashInformationId)) {
			throw new CarCrashInformationNotFoundException(
					"car crash information not found for this id : " + carCrashInformationId);
		}
	}

}
