package com.cs3733.taskapp.http;

public class AddTaskRequest {
	public String projectid;
	public String tasks[];

	public String[] getTasks() { return tasks; }
	public void setTasks(String[] tasks) { this.tasks = tasks; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "AddTaskResponse(" + projectid +  ")";
	}
	
	public AddTaskRequest(String projectid, String[] tasks) {
		this.projectid = projectid;
		this.tasks = tasks;
	}
	
	public AddTaskRequest() {
	}
}

