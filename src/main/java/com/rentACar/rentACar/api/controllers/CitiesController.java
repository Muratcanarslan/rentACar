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

import com.rentACar.rentACar.business.abstracts.CityService;
import com.rentACar.rentACar.business.dtos.cityDtos.CityListDto;
import com.rentACar.rentACar.business.dtos.cityDtos.GetCityDto;
import com.rentACar.rentACar.business.requests.cityRequest.CreateCityRequest;
import com.rentACar.rentACar.business.requests.cityRequest.DeleteCityRequest;
import com.rentACar.rentACar.business.requests.cityRequest.UpdateCityRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

	private CityService cityService;

	public CitiesController(CityService cityService) {
		super();
		this.cityService = cityService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CityListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.cityService.getAll(pageNo, pageSize);
	}

	@GetMapping("/getById")
	public DataResult<GetCityDto> getById(@RequestParam int cityId) throws BusinessException {
		return this.cityService.getById(cityId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
		return this.cityService.add(createCityRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException {
		return this.cityService.update(updateCityRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest) throws BusinessException {
		return this.cityService.delete(deleteCityRequest);
	}
}
