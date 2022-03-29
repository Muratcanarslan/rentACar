package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarCrashInformationService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListByCarDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.GetCarCrashInformationDto;
import com.rentACar.rentACar.business.requests.carCrashRequests.CreateCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.UpdateCarCrashInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions.CarCrashInformationNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
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
	public Result add(CreateCarCrashInformationRequest createCarCrashInformationRequest) throws CarNotFoundException {

		this.carService.checkIfExistByCarId(createCarCrashInformationRequest.getCarId());

		CarCrashInformation carCrashInformation = this.modelMapperService.forRequest()
				.map(createCarCrashInformationRequest, CarCrashInformation.class);

		this.carCrashInformationDao.save(carCrashInformation);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result update(UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws CarNotFoundException, CarCrashInformationNotFoundException {

		checkIfCarCrashInformationExists(updateCarCrashInformationRequest.getCarCrashInformationId());
		this.carService.checkIfExistByCarId(updateCarCrashInformationRequest.getCarId());

		CarCrashInformation carCrashInformation = this.modelMapperService.forRequest()
				.map(updateCarCrashInformationRequest, CarCrashInformation.class);

		this.carCrashInformationDao.save(carCrashInformation);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public Result delete(int carCrashInformationId) throws CarCrashInformationNotFoundException {

		checkIfCarCrashInformationExists(carCrashInformationId);

		this.carCrashInformationDao.deleteById(carCrashInformationId);

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public DataResult<GetCarCrashInformationDto> getById(int carCrashInformationId) throws CarCrashInformationNotFoundException {

		checkIfCarCrashInformationExists(carCrashInformationId);

		CarCrashInformation carCrashInformation = this.carCrashInformationDao.getById(carCrashInformationId);

		GetCarCrashInformationDto getCarCrashInformationDto = this.modelMapperService.forDto().map(carCrashInformation,
				GetCarCrashInformationDto.class);

		return new SuccessDataResult<GetCarCrashInformationDto>(getCarCrashInformationDto,BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<CarCrashInformationListByCarDto>> getByCarId(int carId) throws CarNotFoundException {

		this.carService.checkIfExistByCarId(carId);

		List<CarCrashInformation> carCrashInformations = this.carCrashInformationDao.getByCar_CarId(carId);

		List<CarCrashInformationListByCarDto> carCrashInformationListByCarDtos = carCrashInformations.stream()
				.map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation,
						CarCrashInformationListByCarDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarCrashInformationListByCarDto>>(carCrashInformationListByCarDtos,
				BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<CarCrashInformationListDto>> getAll(int pageNo,int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);

		List<CarCrashInformation> carCrashInformations = this.carCrashInformationDao.findAll(pageable).getContent();

		List<CarCrashInformationListDto> carCrashInformationListDtos = carCrashInformations.stream()
				.map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation,
						CarCrashInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarCrashInformationListDto>>(carCrashInformationListDtos, BusinessMessages.GET_SUCCESSFUL);
	}

	private void checkIfCarCrashInformationExists(int carCrashInformationId)
			throws CarCrashInformationNotFoundException {
		if (!this.carCrashInformationDao.existsById(carCrashInformationId)) {
			throw new CarCrashInformationNotFoundException(
					BusinessMessages.CAR_CRASH_INFORMATION_NOT_FOUND + carCrashInformationId);
		}
	}

}
