package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.cityDtos.CityListDto;
import com.rentACar.rentACar.business.dtos.cityDtos.GetCityDto;
import com.rentACar.rentACar.business.requests.cityRequest.CreateCityRequest;
import com.rentACar.rentACar.business.requests.cityRequest.UpdateCityRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CityService {

	Result add(CreateCityRequest createCityRequest) throws CityAlreadyExistsException;

	Result update(UpdateCityRequest updateCityRequest) throws CityNotFoundException, CityAlreadyExistsException;

	Result delete(int cityId) throws BusinessException;

	DataResult<GetCityDto> getById(int cityId) throws CityNotFoundException;

	DataResult<List<CityListDto>> getAll();

}
