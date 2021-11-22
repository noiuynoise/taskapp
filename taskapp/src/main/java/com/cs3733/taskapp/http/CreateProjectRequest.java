package com.cs3733.taskapp.http;

public class CreateProjectRequest {
	String projectid;

	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "CreateProjectRequest(" + projectid + ")";
	}
	
	public CreateProjectRequest(String projectid) {
		this.projectid = projectid;
	}
	
	public CreateProjectRequest() {
	}
}


