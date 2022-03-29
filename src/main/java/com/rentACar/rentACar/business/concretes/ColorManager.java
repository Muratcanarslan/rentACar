package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.ColorService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.colorDtos.ColorListDto;
import com.rentACar.rentACar.business.dtos.colorDtos.GetColorDto;
import com.rentACar.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.rentACar.rentACar.business.requests.colorRequests.DeleteColorRequest;
import com.rentACar.rentACar.business.requests.colorRequests.UpdateColorRequest;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.colorExceptions.ColorNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.ColorDao;
import com.rentACar.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Color> colors = this.colorDao.findAll(pageable).getContent();

		List<ColorListDto> result = colors.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ColorListDto>>(result, BusinessMessages.GET_SUCCESSFUL);

	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws ColorAlreadyExistsException {

		checkIfExistsByColorName(createColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public DataResult<GetColorDto> getById(int id) throws ColorNotFoundException {

		checkIfExistsByColorId(id);

		Color color = this.colorDao.getById(id);

		GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);

		return new SuccessDataResult<GetColorDto>(getColorDto, BusinessMessages.GET_SUCCESSFUL);

	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest)
			throws ColorNotFoundException, ColorAlreadyExistsException {

		checkIfExistsByColorId(updateColorRequest.getColorId());
		checkIfExistsByColorName(updateColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);

	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws ColorNotFoundException {
		
		checkIfExistsByColorId(deleteColorRequest.getColorId());
		
		this.colorDao.deleteById(deleteColorRequest.getColorId());
		
		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	public void checkIfExistsByColorName(String name) throws ColorAlreadyExistsException {
		if (this.colorDao.existsByColorName(name)) {
			throw new ColorAlreadyExistsException(BusinessMessages.COLOR_ALREADY_EXISTS);
		}
	}

	public void checkIfExistsByColorId(int id) throws ColorNotFoundException {
		if (!this.colorDao.existsById(id)) {
			throw new ColorNotFoundException(BusinessMessages.COLOR_NOT_FOUND + id);
		}
	}

}
