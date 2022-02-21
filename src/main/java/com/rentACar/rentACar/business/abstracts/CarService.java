package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.rentACar.rentACar.business.dtos.CarListDto;
import com.rentACar.rentACar.business.dtos.CarListLessThanDto;
import com.rentACar.rentACar.business.dtos.CarListSortByDailyPrice;
import com.rentACar.rentACar.business.dtos.GetCarDto;
import com.rentACar.rentACar.business.requests.CreateCarRequest;
import com.rentACar.rentACar.business.requests.UpdateCarRequest;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarService {
	
	DataResult<List<CarListDto>> getAll(int pageNo,int pageSize);
	Result add(CreateCarRequest createCarRequest);
	Result update(UpdateCarRequest updateCarRequest);
	Result delete(int carId);
	DataResult<GetCarDto> getById(int carId);
	DataResult<List<CarListLessThanDto>> findByDailyPriceLessThanEqual(double dailyPrice);
	DataResult<List<CarListSortByDailyPrice>> getCarListSortByDailyPrice(Sort.Direction sortDirection);
}
