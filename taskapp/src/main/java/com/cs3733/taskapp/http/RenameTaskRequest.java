package com.cs3733.taskapp.http;

public class RenameTaskRequest {

	
	String taskID;
	String projectID;
	String newName;
	
	public String getTaskID () {return this.taskID;}
	public void setTaskID (String taskID) {this.taskID = taskID;}
	
	public String getProjectID () {return this.projectID;}
	public void setProjectID (String projectID) {this.projectID = projectID;}
	
	public String getNewName () {return this.newName;}
	public void setNewName (String newName) {this.newName = newName;}
	
	public RenameTaskRequest (String taskID, String projectID, String newName) {
		this.taskID = taskID;
		this.projectID = projectID;
		this.newName = newName;
	}
	
	public RenameTaskRequest () {
		
	}
	
	
	
}
