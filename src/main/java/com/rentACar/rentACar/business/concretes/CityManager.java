package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CityService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.cityDtos.CityListDto;
import com.rentACar.rentACar.business.dtos.cityDtos.GetCityDto;
import com.rentACar.rentACar.business.requests.cityRequest.CreateCityRequest;
import com.rentACar.rentACar.business.requests.cityRequest.DeleteCityRequest;
import com.rentACar.rentACar.business.requests.cityRequest.UpdateCityRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.cityExceptions.CityNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CityDao;
import com.rentACar.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService{
	
	private CityDao cityDao;
	private ModelMapperService modelMapperService;

	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		super();
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}
	
	
	@Override
	public Result add(CreateCityRequest createCityRequest) throws CityAlreadyExistsException  {
			
		checkIfCityExistsByCityName(createCityRequest.getCityName());
		
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class); 
				
		city.setCityName(createCityRequest.getCityName());
		
		this.cityDao.save(city);
		
		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}
	@Override
	public Result update(UpdateCityRequest updateCityRequest) throws CityNotFoundException, CityAlreadyExistsException  {
		
		checkIfCityExistsByCityName(updateCityRequest.getCityName());
		checkIfCityExists(updateCityRequest.getCityId());
		
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		
		this.cityDao.save(city);
		
		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}
	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {
		
		checkIfCityExists(deleteCityRequest.getCityId());
		
		this.cityDao.deleteById(deleteCityRequest.getCityId());
		
		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}
	@Override
	public DataResult<GetCityDto> getById(int cityId) throws CityNotFoundException  {
		checkIfCityExists(cityId);

		City city = this.cityDao.getById(cityId);
		
		GetCityDto getCityDto = this.modelMapperService.forDto().map(city, GetCityDto.class);
		
		return new SuccessDataResult<GetCityDto>(getCityDto,BusinessMessages.GET_SUCCESSFUL);
	}
	@Override
	public DataResult<List<CityListDto>> getAll(int pageNo,int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<City> cities = this.cityDao.findAll(pageable).getContent();
		
		List<CityListDto> cityListDtos = cities.stream().map(city -> this.modelMapperService.forDto().map(city, CityListDto.class)).collect(Collectors.toList());
		
	    return new SuccessDataResult<List<CityListDto>>(cityListDtos,BusinessMessages.GET_SUCCESSFUL);
	}
	
	private void checkIfCityExistsByCityName(String cityName) throws CityAlreadyExistsException  {
		if(this.cityDao.existsByCityName(cityName)) {
			throw new CityAlreadyExistsException(BusinessMessages.CITY_ALREADY_EXISTS);
		}
	}
	
	public void checkIfCityExists(int cityId) throws CityNotFoundException  {
		if(!this.cityDao.existsById(cityId)) {
			throw new CityNotFoundException(BusinessMessages.CITY_NOT_FOUND + cityId);
		}
	}

}
