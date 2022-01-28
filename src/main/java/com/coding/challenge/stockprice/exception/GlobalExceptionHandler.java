package com.coding.challenge.stockprice.exception;

import java.time.Instant;
import java.util.Date;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleOtherException(final Exception ex, final WebRequest request) {
		final ErrorResponse errorResponse = setUpDifferentErrorResponseForResponse(ex, request);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse setUpDifferentErrorResponseForResponse(final Exception ex, final WebRequest request) {
		final ErrorResponse auditError = ErrorResponse.builder().timestamp(Date.from(Instant.now()))
				.message(ex.getMessage()).detail(request.getDescription(false)).build();

		final ErrorResponse errorResponse = auditError.clone();
		errorResponse.setMessage(ex.getClass().getName());
		return errorResponse;
	}

	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public final ResponseEntity<Object> handleIncorrectResultSizeDataAccessException(
			final IncorrectResultSizeDataAccessException ex, final WebRequest request) {
		HttpStatus responseStatus;
		if (ex.getActualSize() == 0) {
			responseStatus = HttpStatus.NOT_FOUND;
		} else {
			responseStatus = HttpStatus.BAD_REQUEST;
		}
		final ErrorResponse errorResponse = ErrorResponse.builder().timestamp(Date.from(Instant.now()))
				.message(ex.getMessage()).detail(request.getDescription(false)).build();

		return new ResponseEntity<>(errorResponse, responseStatus);
	}

}
