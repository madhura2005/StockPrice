package com.coding.challenge.stockprice.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@Data
@ToString
public class ErrorResponse implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private final Date timestamp;

	private String message;

	private final String detail;

	@Override
	public ErrorResponse clone() {
		return ErrorResponse.builder().timestamp(timestamp).message(message).detail(detail).build();
	}

}
