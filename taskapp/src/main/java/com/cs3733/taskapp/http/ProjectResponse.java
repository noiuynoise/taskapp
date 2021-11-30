package com.cs3733.taskapp.http;
/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class ProjectResponse {

	
	Teammate teammates[];
	Task tasks[];
	Boolean archived;
	String TUUID;
	String name;

	public Teammate[] getTeammates() { return teammates; }
	public void setTeammates(Teammate[] teammates) { this.teammates = teammates; }
	
	public Task[] getTasks() { return tasks; }
	public void setTasks(Task[] tasks) { this.tasks = tasks; }
	
	public Boolean getArchived() { return archived; }
	public void setArchived(Boolean archived) { this.archived = archived; }
	
	public String getProjectTUUID() { return TUUID; }
	public void setProjectTUUID(String TUUID) { this.TUUID = TUUID; }
	
	public String getProjectName() { return name; }
	public void setProjectName(String name) { this.name = name; }

	public String toString() {
		return "Add(" + archived + "," + TUUID + ")";
	}
	
	public ProjectResponse(String name, String TUUID, Teammate[] teammates, Task[] tasks, Boolean archived) {
		this.teammates = teammates;
		this.tasks = tasks;
		this.archived = archived;
		this.TUUID = TUUID;
		this.name = name;
	}
	
	public ProjectResponse() {
	}
}
