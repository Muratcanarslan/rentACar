package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.dtos.CarListDto;
import com.rentACar.rentACar.business.dtos.CarListLessThanDto;
import com.rentACar.rentACar.business.dtos.CarListSortByDailyPrice;
import com.rentACar.rentACar.business.dtos.GetCarDto;
import com.rentACar.rentACar.business.requests.CreateCarRequest;
import com.rentACar.rentACar.business.requests.UpdateCarRequest;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorDataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CarDao;
import com.rentACar.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll(int pageNo,int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<Car> cars = carDao.findAll(pageable).getContent();
		
		if(cars.isEmpty()) {
			return new ErrorDataResult<List<CarListDto>>("No cars found");
		}

		List<CarListDto> carListDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());

		return  new SuccessDataResult<List<CarListDto>>(carListDto,"car list");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		this.carDao.save(car);
		
		return new SuccessResult("cars added");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
         
		if(!isExistByCarId(updateCarRequest.getCarId())) {
			return new ErrorResult("No cars found for this id");
		}
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
			
		this.carDao.save(car);
			
		return new SuccessResult("car updated");


	}

	@Override
	public Result delete(int carId) {
		
		if(isExistByCarId(carId)) {
			this.carDao.deleteById(carId);
			return new SuccessResult("car deleted");
		}

		return new ErrorResult("No cars found for this id");

	}

	@Override
	public DataResult<GetCarDto> getById(int carId) {
			
			if(!isExistByCarId(carId)) {
				return new ErrorDataResult<GetCarDto>("No car found");
			}
			
			Car car = this.carDao.getById(carId);

			GetCarDto getCarDto = this.modelMapperService.forDto().map(car, GetCarDto.class);

			return new SuccessDataResult<GetCarDto>(getCarDto,"car get by id");
	}
	
	@Override
	public DataResult<List<CarListLessThanDto>> findByDailyPriceLessThanEqual(double dailyPrice) {
		
		List<Car> cars = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		
		if(cars.isEmpty()) {
			return new ErrorDataResult<List<CarListLessThanDto>>("No car found");
		}
		
		List<CarListLessThanDto> carListLessThanDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car,CarListLessThanDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListLessThanDto>>(carListLessThanDto,"Car List");
		
	}
	
	@Override
	public DataResult<List<CarListSortByDailyPrice>> getCarListSortByDailyPrice(Direction sortDirection) {
		Sort sort = Sort.by(sortDirection,"dailyPrice");
		
		List<Car> cars = this.carDao.findAll(sort);
		
		if(cars.isEmpty()) {
			return new ErrorDataResult<List<CarListSortByDailyPrice>>("no cars found");
		}
		
		List<CarListSortByDailyPrice> carListSortByDailyPrice = cars.stream()
				.map(car-> this.modelMapperService.forDto().map(car,CarListSortByDailyPrice.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListSortByDailyPrice>>(carListSortByDailyPrice,"sorted car list");
	}

	

	public boolean isExistByCarId(int id)  {
		if (this.carDao.existsById(id)) {
			return true;
		}
		return false;
	}

	
	

}
