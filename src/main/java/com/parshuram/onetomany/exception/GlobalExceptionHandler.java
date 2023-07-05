package com.parshuram.onetomany.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.parshuram.onetomany.utils.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {

		String message = exception.getMessage();

		var rsp = new ApiResponse(message);

		return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgNotValidException(
			MethodArgumentNotValidException exception) {

		Map<String, String> rsp = new HashMap<>();

		exception.getAllErrors().forEach(error -> {

			String fieldName = ((FieldError) error).getField();

			String message = error.getDefaultMessage();

			rsp.put(fieldName, message);
		});

		return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);

	}
}
