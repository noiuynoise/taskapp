package com.cs3733.taskapp.http;

public class ListProjectsResponse {

	Project projects[];
	
	public Project[] getProjects() { return projects; }
	public void setProjects(Project[] projects) { this.projects = projects; }
	
	public ListProjectsResponse(Project[] projects) {
		this.projects = projects;
	}
	
	public String toString() {
		return "ListProjectResponse(" + ")";
	}
	
	public ListProjectsResponse() {
	}
}
