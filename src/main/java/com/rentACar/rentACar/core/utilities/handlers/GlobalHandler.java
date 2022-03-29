package com.rentACar.rentACar.core.utilities.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.ErrorDataResult;

@RestControllerAdvice
public class GlobalHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
		Map<String,String> validationErrors = new HashMap<String,String>();
		
		for(FieldError fieldError :methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return new ErrorDataResult<Object>(validationErrors,"Validation.Error");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorDataResult<Object> handleBusinessExceptions(BusinessException businessException){
		
		ErrorDataResult<Object> error = new ErrorDataResult<Object>(businessException.getMessage(),"Business.Error");
		
		return error;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorDataResult<Object> handleIllegalArgumentForPageable(IllegalArgumentException illegalArgumentException){
		
		ErrorDataResult<Object> error = new ErrorDataResult<Object>(illegalArgumentException.getMessage(),"Illegal Argument");
		
		return error;
	}
}
