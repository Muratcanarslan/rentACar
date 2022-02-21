package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.dtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.GetColorDto;
import com.rentACar.rentACar.business.requests.CreateColorRequest;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorDataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.ColorDao;
import com.rentACar.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	@Autowired
	public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		List<Color> colors = this.colorDao.findAll();
		
		if(colors.isEmpty()) {
			return new ErrorDataResult<List<ColorListDto>>("No colors found");
		}
		
		List<ColorListDto> result = colors.stream().map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
		.collect(Collectors.toList());
		
		return  new SuccessDataResult<List<ColorListDto>>(result,"color list");
		
 }

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		if(!existByColorName(color.getColorName())) {
			this.colorDao.save(color);
			return new SuccessResult("color added");
		}
		
		return new ErrorResult("color is exist");
		
	}
	
	@Override
	public DataResult<GetColorDto> getById(int id) {
		
		Color color = this.colorDao.getById(id);
		if(color == null) {
			return new ErrorDataResult<GetColorDto>("No colors found");

		}
		GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);
		
		return  new SuccessDataResult<GetColorDto>(getColorDto,"color get by id");

	}
	
	public boolean existByColorName(String name) {
		return this.colorDao.existsByColorName(name);
	}

	

}
