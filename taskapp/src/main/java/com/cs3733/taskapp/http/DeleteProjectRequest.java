package com.cs3733.taskapp.http;

public class DeleteProjectRequest {
	String projectid;

	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "DeleteProjectRequest(" + projectid + ")";
	}
	
	public DeleteProjectRequest(String projectid) {
		this.projectid = projectid;
	}
	
	public DeleteProjectRequest() {
	}
	
}
