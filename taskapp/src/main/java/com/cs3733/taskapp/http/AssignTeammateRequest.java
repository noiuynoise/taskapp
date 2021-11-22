package com.cs3733.taskapp.http;

public class AssignTeammateRequest {
	String projectid;
	String taskid;
	Boolean status;

	
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String getTaskID() { return taskid; }
	public void setTaskID(String taskid) { this.taskid = taskid; }
	
	public String toString() {
		return "AssignTeammateRequest(" + projectid + "," + taskid +  "," + status + ")";
	}
	
	public AssignTeammateRequest(String projectid, String taskid, Boolean status) {
		this.projectid = projectid;
		this.taskid = taskid;
		this.status = status;
	}
	
	public AssignTeammateRequest() {
	}
}


