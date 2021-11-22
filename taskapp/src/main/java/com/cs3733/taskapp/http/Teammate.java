package com.cs3733.taskapp.http;

public class Teammate {
<<<<<<< HEAD

//	  Teammate:
//		    type: object
//		    required:
//		    - name
//		    properties:
//		      name:
//		        type: string
//		      tasks:
//		        type: array
//		        items: 
//		          $ref: "#/definitions/Task"
	
	String name;
	Task tasks[];
	
	
=======
	Task tasks[];
	String name;
	
	public Task[] getTasks() { return tasks; }
	public void setTasks(Task[] tasks) { this.tasks = tasks; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }	
	
	public Teammate(Task[] tasks, String name) {
		this.tasks = tasks;
		this.name = name;
	}
	
	public Teammate() {
	}
>>>>>>> branch 'master' of https://github.com/noiuynoise/taskapp.git
}
