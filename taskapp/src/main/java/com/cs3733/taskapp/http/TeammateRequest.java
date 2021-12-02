package com.cs3733.taskapp.http;

public class TeammateRequest {
	public String name;
	public String projectid;

	public String getName() { return name; }
	public void setTeammates(String name) { this.name = name; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "Add(" + projectid + "," + name + ")";
	}
	
	public TeammateRequest(String projectid, String name) {
		this.name = name;
		this.projectid = projectid;
	}
	
	public TeammateRequest() {
	}
}
