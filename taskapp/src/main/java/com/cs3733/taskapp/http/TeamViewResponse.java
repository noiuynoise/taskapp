package com.cs3733.taskapp.http;
/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class TeamViewResponse {
	Teammate teammates[];
	String projectid;

	public Teammate[] getTeammates() { return teammates; }
	public void setTeammates(Teammate[] teammates) { this.teammates = teammates; }
	
	public String getProjectID() { return projectid; }
	public void setProjectID(String projectid) { this.projectid = projectid; }

	public String toString() {
		return "TeamViewResponse(" + projectid + ")";
	}
	
	public TeamViewResponse(Teammate[] teammates, String projectid) {
		this.teammates = teammates;
		this.projectid = projectid;
	}
	
	public TeamViewResponse() {
	}
}
