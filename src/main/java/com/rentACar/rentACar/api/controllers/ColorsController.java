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

import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.dtos.colorDtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.colorDtos.GetColorDto;
import com.rentACar.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.rentACar.rentACar.business.requests.colorRequests.DeleteColorRequest;
import com.rentACar.rentACar.business.requests.colorRequests.UpdateColorRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/colors")
public class ColorsController {

	private ColorService colorService;

	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ColorListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws BusinessException {
		return this.colorService.getAll(pageNo, pageSize);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		return this.colorService.add(createColorRequest);
	}

	@GetMapping("/getById")
	public DataResult<GetColorDto> getById(@RequestParam int colorId) throws BusinessException {
		return this.colorService.getById(colorId);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest)
			throws ColorNotFoundException, ColorAlreadyExistsException {
		return this.colorService.update(updateColorRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) throws ColorNotFoundException {
		return this.colorService.delete(deleteColorRequest);
	}
}
