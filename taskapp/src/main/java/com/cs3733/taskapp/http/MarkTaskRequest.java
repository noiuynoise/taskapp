package com.cs3733.taskapp.http;

public class MarkTaskRequest {
	String projectid;
	String taskid;
	Boolean status;
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String getTaskID() { return taskid; }
	public void setTaskID(String taskid) { this.taskid = taskid; }
	
	public Boolean getStatus() {return status; }
	public void setTaskID(Boolean status) { this.status = status; }

	public String toString() {
		return "MarkTaskRequest(" + projectid + "," + taskid +  "," + status + ")";
	}
	
	public MarkTaskRequest(String projectid, String taskid, Boolean status) {
		this.projectid = projectid;
		this.taskid = taskid;
		this.status = status;
	}
	
	public MarkTaskRequest() {
	}
}


