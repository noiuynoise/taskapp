package edu.wpi.cs.heineman.demo.http;

public class DeleteConstantRequest {
	public String name;
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	
	public DeleteConstantRequest (String n) {
		this.name = n;
	}

	public DeleteConstantRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}
}
