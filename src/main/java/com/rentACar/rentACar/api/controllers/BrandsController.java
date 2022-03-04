package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.BrandService;
import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService; 
	
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<BrandListDto>> getAll() throws BusinessException{
		return this.brandService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException {
		return this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetBrandDto> getById(@RequestParam int id) throws BusinessException{
		return this.brandService.getById(id);
	}
}
