package edu.wpi.cs.heineman.demo.http;

/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class FactorRequest {
	String arg1;

	public String getArg1() { return arg1; }
	public void setArg1(String arg1) { this.arg1 = arg1; }
	
	public String toString() {
		return "Factor(" + arg1 + ")";
	}
	
	public FactorRequest (String arg1) {
		this.arg1 = arg1;
	}
	
	public FactorRequest() {
	}
}
