package com.cs3733.taskapp.http;

public class AddTaskRequest {
	String projectid;
	Task tasks[];

	public Task[] getTasks() { return tasks; }
	public void setTasks(Task[] tasks) { this.tasks = tasks; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "TeamViewResponse(" + projectid +  ")";
	}
	
	public AddTaskRequest(Task[] tasks, String projectid) {
		this.tasks = tasks;
		this.projectid = projectid;
	}
	
	public AddTaskRequest() {
	}
}

