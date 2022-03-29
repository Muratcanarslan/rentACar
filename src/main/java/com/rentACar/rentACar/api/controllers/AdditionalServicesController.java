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

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.GetAdditionalServiceDto;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalService")
public class AdditionalServicesController {

	private AdditionalServiceService additionalServiceService;

	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest)
			throws BusinessException {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest)
			throws BusinessException {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<AdditionalServiceListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.additionalServiceService.getAll(pageNo, pageSize);
	}
	
	@GetMapping("/getById/{additionalServiceId}")
	public DataResult<GetAdditionalServiceDto> getById(@RequestParam int additionalServiceId) throws AdditionalServiceNotFoundException{
		return this.additionalServiceService.getById(additionalServiceId);
	}

}
