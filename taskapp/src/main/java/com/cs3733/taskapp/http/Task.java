package com.cs3733.taskapp.http;

public class Task {

//	Task:
//	    type: object
//	    required:
//	    - name
//	    properties:
//	      name:
//	        type: string
//	      subtasks:
//	        type: array
//	        items: 
//	          $ref: "#/definitions/Task"
//	      id:
//	        type: string
//	      complete:
//	        type: boolean
	
	
	String name;
	Task subtasks[];
	String id;
	Boolean complete;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Task[] getSubtasks() { return subtasks; }
	public void setSubtasks(Task[] subtasks) { this.subtasks = subtasks; }

	public String getID() { return id; }
	public void setID(String id) { this.id = id; }

	public Boolean getComplete() { return complete; }
	public void setComplete(Boolean complete) { this.complete = complete; }


}
