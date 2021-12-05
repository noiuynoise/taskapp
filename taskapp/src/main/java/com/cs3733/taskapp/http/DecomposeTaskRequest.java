package com.cs3733.taskapp.http;

public class DecomposeTaskRequest {
	public String projectid;
	public String taskid;
	public String tasks[];

	public String[] getTasks() { return tasks; }
	public void setTasks(String[] tasks) { this.tasks = tasks; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }
	
	public String getTaskID() { return taskid; }
	public void setTaskID(String taskid) { this.taskid = taskid; }

	public String toString() {
		return "DecomposeTaskRequest(" + projectid + "," + taskid + ")";
	}
	
	public DecomposeTaskRequest(String projectid, String taskid, String[] tasks) {
		this.projectid = projectid;
		this.taskid  = taskid;
		this.tasks = tasks;
	}
	
	public DecomposeTaskRequest() {
	}
}


