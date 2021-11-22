package com.cs3733.taskapp.http;

public class ProjectRequest {
	
	String projectID;
	
	public String getProjectID () {return this.projectID;}
	public void setProjectID (String projectID) {this.projectID = projectID;}
	
	
	public String toString() {
		return "ProjectRequest(" + projectID + ")";
	}
	
	public ProjectRequest (String projectID)
	{
		this.projectID = projectID;
	}
	
	public ProjectRequest ()
	{
		
	}
	
	
}
