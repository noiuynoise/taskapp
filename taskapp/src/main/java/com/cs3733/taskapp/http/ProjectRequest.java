package com.cs3733.taskapp.http;

public class ProjectRequest {
	
	String projectid;
	
	public String getProjectID () {return this.projectid;}
	public void setProjectID (String projectid) {this.projectid = projectid;}
	
	
	public ProjectRequest (String projectid)
	{
		this.projectid = projectid;
	}
	
	public String toString() {
		return "ProjectRequest(" + projectid + ")";
	}
	
	public ProjectRequest ()
	{
		
	}
	
	
}
