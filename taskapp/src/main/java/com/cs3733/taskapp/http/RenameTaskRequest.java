package com.cs3733.taskapp.http;

public class RenameTaskRequest {

	public String projectid;
	public String taskid;
	public String name;
	
	public String getProjectID () {return this.projectid;}
	public void setProjectID (String projectID) {this.projectid = projectID;}
	
	public String getTaskID () {return this.taskid;}
	public void setTaskID (String taskID) {this.taskid = taskID;}
	
	public String getNewName () {return this.name;}
	public void setNewName (String newName) {this.name = newName;}
	
	public String toString() {
		return "RenameTaskRequest(" + projectid + "," + taskid + "," + name + ")";
	}
	
	public RenameTaskRequest (String projectID, String taskID, String newName) {
		this.taskid = taskID;
		this.projectid = projectID;
		this.name = newName;
	}
	
	public RenameTaskRequest () {
		
	}
	
	
	
}
