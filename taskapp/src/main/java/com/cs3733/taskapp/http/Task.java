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
	String ID;
	boolean complete;
	
	public String getName () { return this.name; }
	public void setName (String name) { this.name = name; }
	
	public Task[] getSubtasks () {return this.subtasks;}
	public void setSubtasks (Task[] subtasks) {this.subtasks = subtasks;}	
	
	public String getID() { return this.ID; }
	public void setID(String ID) { this.ID = ID; }
	
	public boolean getComplete () {return this.complete;}
	public void setComplete (boolean complete) {this.complete = complete;}
	
	
	public Task (String name, Task[] subtasks, String ID, boolean complete)
	{
		this.name = name;
		this.subtasks = subtasks;
		this.ID = ID;
		this.complete = complete;
	}
	
	public Task ()
	{
		
	}
	
	
	
	
	
	
}
