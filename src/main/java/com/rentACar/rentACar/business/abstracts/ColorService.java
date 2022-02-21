package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.GetColorDto;
import com.rentACar.rentACar.business.requests.CreateColorRequest;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	Result add(CreateColorRequest cleareColorRequest);
	
	DataResult<GetColorDto> getById(int id);
}
