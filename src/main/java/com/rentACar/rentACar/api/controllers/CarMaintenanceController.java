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

import com.rentACar.rentACar.business.abstracts.CarMaintenanceService;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.GetCarMaintenanceDto;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenance")
public class CarMaintenanceController {
	
	private CarMaintenanceService carMaintenanceService;
	
	public CarMaintenanceController(CarMaintenanceService carMaintenanceService) {
		super();
		this.carMaintenanceService = carMaintenanceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam int carMaintenanceId) throws BusinessException {
		return this.carMaintenanceService.delete(carMaintenanceId);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCarMaintenanceDto> getCarMaintenanceById(@RequestParam int carMaintenanceId) throws BusinessException{
		return this.carMaintenanceService.getById(carMaintenanceId);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CarMaintenanceListDto>> getCarMaintenanceListDto() throws BusinessException{
		return this.carMaintenanceService.getAll();
	}
}
