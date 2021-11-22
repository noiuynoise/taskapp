package com.cs3733.taskapp.http;

public class DecomposeTaskRequest {
	String projectid;
	String taskid;
	Task tasks[];

	public Task[] getTasks() { return tasks; }
	public void setTasks(Task[] tasks) { this.tasks = tasks; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }
	
	public String getTaskID() { return taskid; }
	public void setTaskID(String taskid) { this.taskid = taskid; }

	public String toString() {
		return "DecomposeTaskRequest(" + projectid + "," + taskid + ")";
	}
	
	public DecomposeTaskRequest(Task[] tasks, String projectid, String taskid) {
		this.tasks = tasks;
		this.projectid = projectid;
		this.taskid  = taskid;
	}
	
	public DecomposeTaskRequest() {
	}
}


