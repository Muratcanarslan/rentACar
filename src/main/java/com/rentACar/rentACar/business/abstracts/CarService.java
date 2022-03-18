package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.rentACar.rentACar.business.dtos.carDtos.CarListDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListLessThanDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListSortByDailyPrice;
import com.rentACar.rentACar.business.dtos.carDtos.GetCarDto;
import com.rentACar.rentACar.business.requests.carRequests.CreateCarRequest;
import com.rentACar.rentACar.business.requests.carRequests.UpdateCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll(int pageNo, int pageSize);

	Result add(CreateCarRequest createCarRequest) throws BusinessException;

	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

	Result delete(int carId) throws BusinessException;

	DataResult<GetCarDto> getById(int carId) throws BusinessException;

	DataResult<List<CarListLessThanDto>> findByDailyPriceLessThanEqual(double dailyPrice) throws BusinessException;

	DataResult<List<CarListSortByDailyPrice>> getCarListSortByDailyPrice(Sort.Direction sortDirection)
			throws BusinessException;

	void checkIfExistByCarId(int id) throws BusinessException;
	
	void updateKilometreInformation(int carId,double kilometreInformation) throws BusinessException;

	double calculateRentPriceByCarIdAndRentDateValue(int carId, int rentDateValue) throws BusinessException;
}
