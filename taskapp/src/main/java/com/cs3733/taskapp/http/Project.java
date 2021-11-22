package com.cs3733.taskapp.http;

public class Project {
	String projectname;
	String projectid;
	Double completion;
	
	public String getProjectName() { return projectname; }
	public void setProjectName(String projectname) { this.projectname = projectname; }

	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public Double getCompletion() { return completion; }
	public void setCompletion(Double completion) { this.completion = completion; }
	
	
	public String toString() {
		return "Project(" + projectname + ", " + projectid + ", " + completion + ")";
	}
	
	
	public Project(String projectname, String projectid, Double completion) {
		this.projectname = projectname;
		this.projectid = projectid;
		this.completion = completion;
	}
	
	public Project() {
	}

}
