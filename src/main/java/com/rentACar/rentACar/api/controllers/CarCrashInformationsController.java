package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.CarCrashInformationService;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListByCarDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.GetCarCrashInformationDto;
import com.rentACar.rentACar.business.requests.carCrashRequests.CreateCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.UpdateCarCrashInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions.CarCrashInformationNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carCrashInformations")
public class CarCrashInformationsController {
	
	private CarCrashInformationService carCrashInformationService;

	public CarCrashInformationsController(CarCrashInformationService carCrashInformationService) {
		super();
		this.carCrashInformationService = carCrashInformationService;
	}
	
	@GetMapping("/getById")
	public DataResult<GetCarCrashInformationDto> getById(@RequestParam int carInformationId) throws CarCrashInformationNotFoundException{
		return this.carCrashInformationService.getById(carInformationId);
	}
	
	@GetMapping("/getByCarId")
	public DataResult<List<CarCrashInformationListByCarDto>> getByCarId(int carId) throws BusinessException{
		return this.carCrashInformationService.getByCarId(carId);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CarCrashInformationListDto>> getAll(){
		return this.carCrashInformationService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarCrashInformationRequest createCarCrashInformationRequest) throws BusinessException {
		return this.carCrashInformationService.add(createCarCrashInformationRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws CarCrashInformationNotFoundException, BusinessException {
		return this.carCrashInformationService.update(updateCarCrashInformationRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam int carCrashInformationId) throws CarCrashInformationNotFoundException {
		return this.carCrashInformationService.delete(carCrashInformationId);
	}
}
