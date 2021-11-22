package edu.wpi.cs.heineman.demo.http;

import java.util.ArrayList;
import java.util.List;

public class FactorResponse {
	public final List<String> list;
	public final int statusCode;
	public final String error;
	
	public FactorResponse (List<String> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public FactorResponse (int code, String errorMessage) {
		this.list = new ArrayList<String>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "Empty Response"; }
		return "FactorResponse(" + list.size() + ")";
	}
}
