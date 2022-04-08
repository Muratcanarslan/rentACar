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

import com.rentACar.rentACar.business.abstracts.BrandService;
import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.business.requests.brandRequests.DeleteBrandRequest;
import com.rentACar.rentACar.business.requests.brandRequests.UpdateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandNotFoundException;
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
	public DataResult<List<BrandListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws BusinessException {
		return this.brandService.getAll(pageNo, pageSize);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException {
		return this.brandService.add(createBrandRequest);
	}

	@GetMapping("/getById/{brandId}")
	public DataResult<GetBrandDto> getById(@RequestParam int id) throws BusinessException {
		return this.brandService.getById(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest)
			throws BrandNotFoundException, BrandAlreadyExistsException {
		return this.brandService.update(updateBrandRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) throws BrandNotFoundException {
		return this.brandService.delete(deleteBrandRequest);

	}
}
