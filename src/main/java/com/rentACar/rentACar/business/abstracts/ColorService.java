package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.colorDtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.colorDtos.GetColorDto;
import com.rentACar.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface ColorService {

	DataResult<List<ColorListDto>> getAll();

	Result add(CreateColorRequest cleareColorRequest) throws ColorAlreadyExistsException;

	DataResult<GetColorDto> getById(int id) throws ColorNotFoundException;

	void checkIfExistsByColorId(int id) throws ColorNotFoundException;
}
