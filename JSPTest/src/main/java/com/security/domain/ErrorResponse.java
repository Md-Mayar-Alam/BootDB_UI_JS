package com.security.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private final String msg;
	private final ErrorCode errorCode;
	private final HttpStatus status;
	private final Date timestamp;
	
	private ErrorResponse(String msg, ErrorCode errorCode, HttpStatus status) {
		this.msg= msg;
		this.errorCode= errorCode;
		this.status= status;
		this.timestamp= new Date();
	}
	
	public static ErrorResponse of(final String msg, final ErrorCode errorCode, final HttpStatus status) {
		return(new ErrorResponse(msg, errorCode, status));
	}

	public String getMsg() {
		return msg;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
