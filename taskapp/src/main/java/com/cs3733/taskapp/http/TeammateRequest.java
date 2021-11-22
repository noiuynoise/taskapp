package com.cs3733.taskapp.http;

public class TeammateRequest {
	String name;
	String projectid;


	public String getName() { return name; }
	public void setTeammates(String name) { this.name = name; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "Add(" + name + "," + projectid + ")";
	}
	
	public TeammateRequest(String name, String projectid) {
		this.name = name;
		this.projectid = projectid;
	}
	
	public TeammateRequest() {
	}
}
