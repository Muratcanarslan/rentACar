package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.dtos.carDtos.CarListDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListLessThanDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListSortByDailyPrice;
import com.rentACar.rentACar.business.dtos.carDtos.GetCarDto;
import com.rentACar.rentACar.business.requests.carRequests.CreateCarRequest;
import com.rentACar.rentACar.business.requests.carRequests.UpdateCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws BusinessException {
		return this.carService.getAll(pageNo, pageSize);
	}

	@GetMapping("/getById")
	public DataResult<GetCarDto> getById(@RequestParam int carId) throws BusinessException {
		return this.carService.getById(carId);
	}

	@GetMapping("/getByDailyPrice")
	public DataResult<List<CarListLessThanDto>> findByDailyPriceLessThanEqual(@RequestParam double dailyPrice)
			throws BusinessException {
		return this.carService.findByDailyPriceLessThanEqual(dailyPrice);
	}

	@GetMapping("/getBySortedDailyPrice")
	public DataResult<List<CarListSortByDailyPrice>> getCarListSortByDailyPrice(Sort.Direction sortDirection)
			throws BusinessException {
		return this.carService.getCarListSortByDailyPrice(sortDirection);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException {
		return this.carService.update(updateCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam int carId) throws BusinessException {
		return this.carService.delete(carId);
	}

}
