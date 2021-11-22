package com.cs3733.taskapp.http;

public class ErrorResponse {
	String errorMessage;
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage= errorMessage;
	}
	
	public String toString() {
		return errorMessage;
	}
	
	
	public String ErrorResponse() {
		return errorMessage;
	}
}