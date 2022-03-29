package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.BrandService;
import com.rentACar.rentACar.business.abstracts.CarService;
import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.carDtos.CarListDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListLessThanDto;
import com.rentACar.rentACar.business.dtos.carDtos.CarListSortByDailyPrice;
import com.rentACar.rentACar.business.dtos.carDtos.GetCarDto;
import com.rentACar.rentACar.business.requests.carRequests.CreateCarRequest;
import com.rentACar.rentACar.business.requests.carRequests.DeleteCarRequest;
import com.rentACar.rentACar.business.requests.carRequests.UpdateCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CarDao;
import com.rentACar.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private ColorService colorService;
	private BrandService brandService;

	public CarManager(CarDao carDao, ModelMapperService modelMapperService, ColorService colorService,
			BrandService brandService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService;
		this.colorService = colorService;
	}

	@Override
	public void updateKilometreInformation(int carId, double kilometreInformation) throws CarNotFoundException {

		checkIfExistByCarId(carId);

		Car car = this.carDao.getById(carId);

		car.setKilometreInformation(kilometreInformation);

		this.carDao.save(car);
	}

	@Override
	public DataResult<List<CarListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Car> cars = this.carDao.findAll(pageable).getContent();

		List<CarListDto> carListDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(carListDto, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BrandNotFoundException, ColorNotFoundException {

		this.brandService.checkIfIsExistsByBrandId(createCarRequest.getBrandId());

		this.colorService.checkIfExistsByColorId(createCarRequest.getColorId());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest)
			throws CarNotFoundException, BrandNotFoundException, ColorNotFoundException {

		checkIfExistByCarId(updateCarRequest.getCarId());

		this.brandService.checkIfIsExistsByBrandId(updateCarRequest.getBrandId());

		this.colorService.checkIfExistsByColorId(updateCarRequest.getColorId());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

		this.carDao.save(car);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws CarNotFoundException {

		this.checkIfExistByCarId(deleteCarRequest.getCarId());

		this.carDao.deleteById(deleteCarRequest.getCarId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);

	}

	@Override
	public DataResult<GetCarDto> getById(int carId) throws CarNotFoundException {

		checkIfExistByCarId(carId);

		Car car = this.carDao.getById(carId);

		GetCarDto getCarDto = this.modelMapperService.forDto().map(car, GetCarDto.class);

		return new SuccessDataResult<GetCarDto>(getCarDto, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<CarListLessThanDto>> findByDailyPriceLessThanEqual(double dailyPrice) {

		List<Car> cars = this.carDao.getByDailyPriceLessThanEqual(dailyPrice);

		List<CarListLessThanDto> carListLessThanDto = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListLessThanDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarListLessThanDto>>(carListLessThanDto, BusinessMessages.GET_SUCCESSFUL);

	}

	@Override
	public DataResult<List<CarListSortByDailyPrice>> getCarListSortByDailyPrice(Direction sortDirection) {

		Sort sort = Sort.by(sortDirection, "dailyPrice");

		List<Car> cars = this.carDao.findAll(sort);

		List<CarListSortByDailyPrice> carListSortByDailyPrice = cars.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListSortByDailyPrice.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarListSortByDailyPrice>>(carListSortByDailyPrice,
				BusinessMessages.GET_SUCCESSFUL);
	}

	public double calculateRentPriceByCarIdAndRentDateValue(int carId, int rentDateValue) throws CarNotFoundException {

		checkIfExistByCarId(carId);

		Car car = this.carDao.getById(carId);

		double finalPrice = car.getDailyPrice() * rentDateValue;

		return finalPrice;
	}

	public void checkIfExistByCarId(int id) throws CarNotFoundException {
		if (!this.carDao.existsById(id)) {
			throw new CarNotFoundException(BusinessMessages.CAR_NOT_FOUND + id);
		}
	}

}
