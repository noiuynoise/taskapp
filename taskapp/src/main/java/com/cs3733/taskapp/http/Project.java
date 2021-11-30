package com.cs3733.taskapp.http;

public class Project {
	String projectname;
	String projectid;
	double completion;
	Boolean archived;
	
	public String getProjectName() { return projectname; }
	public void setProjectName(String projectname) { this.projectname = projectname; }

	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public double getCompletion() { return completion; }
	public void setCompletion(double completion) { this.completion = completion; }
	
	public Boolean getArchived() { return archived; }
	public void setArchived(Boolean archived) { this.archived = archived; }
	
	
	public String toString() {
		return "Project(" + projectname + ", " + projectid + ", " + completion + ")";
	}
	
	
	public Project( String projectid, String projectname, Double completion) {
		this.projectname = projectname;
		this.projectid = projectid;
		this.completion = completion;
	}
	
	public Project() {
	}

}
