package com.rentACar.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.dtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.GetColorDto;
import com.rentACar.rentACar.business.requests.CreateColorRequest;
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
	public DataResult<List<ColorListDto>> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		return this.colorService.add(createColorRequest);
	}
	

	@GetMapping("/getById")
	public DataResult<GetColorDto> getById(@RequestParam int colorId){
		return this.colorService.getById(colorId);
	}

}
