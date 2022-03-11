package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.dtos.colorDtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.colorDtos.GetColorDto;
import com.rentACar.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
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
				
		List<ColorListDto> result = colors.stream().map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
		.collect(Collectors.toList());
		
		return  new SuccessDataResult<List<ColorListDto>>(result,"color list");
		
 }

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		
		checkIfExistsByColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		return new SuccessResult("color added");
	}
	
	@Override
	public DataResult<GetColorDto> getById(int id) throws BusinessException {
		
		
		checkIfExistsByColorId(id);
		
		Color color = this.colorDao.getById(id);
		
		GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);
		
		return  new SuccessDataResult<GetColorDto>(getColorDto,"color get by id");

	}
	
	public void checkIfExistsByColorName(String name) throws BusinessException {
		if(this.colorDao.existsByColorName(name)) {
			throw new BusinessException("Color already exists");
		}
	}

	
	public void checkIfExistsByColorId(int id) throws BusinessException {
		if(!this.colorDao.existsById(id)) {
			throw new BusinessException("Color Not Found");
		}
	}

	

}
