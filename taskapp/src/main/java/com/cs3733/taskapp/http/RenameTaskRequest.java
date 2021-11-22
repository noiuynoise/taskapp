package com.cs3733.taskapp.http;

public class RenameTaskRequest {

	String projectID;
	String taskID;
	String newName;
	
	public String getProjectID () {return this.projectID;}
	public void setProjectID (String projectID) {this.projectID = projectID;}
	
	public String getTaskID () {return this.taskID;}
	public void setTaskID (String taskID) {this.taskID = taskID;}
	
	public String getNewName () {return this.newName;}
	public void setNewName (String newName) {this.newName = newName;}
	
	public String toString() {
		return "RenameTaskRequest(" + projectID + "," + taskID + "," + newName + ")";
	}
	
	public RenameTaskRequest (String projectID, String taskID, String newName) {
		this.taskID = taskID;
		this.projectID = projectID;
		this.newName = newName;
	}
	
	public String toString() {
		return "RenameTaskRequest" + taskID + ", " + projectID + ", " + newName + ")";
	}
	
	public RenameTaskRequest () {
		
	}
	
	
	
}
