package com.jsp.ums.exceptionhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> structure(HttpStatus status, String message, Object rootCause) {
		return new ResponseEntity<Object>(Map.of(
				"status", status.value(), 
				"message", message, 
				"rootCause", rootCause),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<ObjectError> allErrors = ex.getAllErrors();
		
		Map<String, String> errors=new HashMap<>();
		
		allErrors.forEach(error ->{
			FieldError fieldError =(FieldError)error;
			
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		});

		return structure(HttpStatus.BAD_REQUEST, "Failed To Save The Data", errors);
	}

	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handleUserNotFoundById(UserNotFoundByIdException e) {
		return structure(HttpStatus.NOT_FOUND, e.getMessage(), "User not present for given id");
	}

	@ExceptionHandler(UsersDataNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundB(UsersDataNotFoundException e) {
		return structure(HttpStatus.NOT_FOUND, e.getMessage(), "User data not present");
	}

//	@ExceptionHandler(UserNotFoundByIdException.class)
//	public ResponseEntity<Object> handleUserDataNotFound(UsersDataNotFoundException e)
//	{
//		Map<String, Object> strucute= Map.of(
//				"status",HttpStatus.NOT_FOUND.value(),
//				"message",e.getMessage(),
//				"rootCause","User Data Not Present"
//				);
//		
//		return new ResponseEntity<Object>(strucute,HttpStatus.NOT_FOUND);		
//	}

}
