package com.cs3733.taskapp.http;
/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class ProjectResponse {
	
//	ProjectResponse:
//	    type: object
//	    properties:
//	      teammates:
//	        type: array
//	        items: 
//	          $ref: "#/definitions/Teammate"
//	      tasks:
//	        type: array
//	        items: 
//	          $ref: "#/definitions/Task"  
//	      archived:
//	        type: boolean
//	      projectid:
//	        type: string
	
	
	
	Teammate teammates[];
	Task tasks[];
	Boolean archived;
	String projectid;

	public Teammate[] getTeammates() { return teammates; }
	public void setTeammates(Teammate[] teammates) { this.teammates = teammates; }
	
	public Task[] getTasks() { return tasks; }
	public void setTasks(Task[] tasks) { this.tasks = tasks; }
	
	public Boolean getArchived() { return archived; }
	public void setArchived(Boolean archived) { this.archived = archived; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "Add(" + archived + "," + projectid + ")";
	}
	
	public ProjectResponse(Teammate[] teammates, Task[] tasks, Boolean archived, String projectid) {
		this.teammates = teammates;
		this.tasks = tasks;
		this.archived = archived;
		this.projectid = projectid;
	}
	
	public ProjectResponse() {
	}
}
