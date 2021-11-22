package com.cs3733.taskapp.http;

public class ListProjectsResponse {

//	ListProjectsResponse:
//	    type: object
//	    properties:
//	      projects:
//	        type: array
//	        items:
//	          type: object
//	          properties:
//	            projectname:
//	              type: string
//	            projectid:
//	              type: string
//	            completion:
//	              type: number
	

	Project projects[];
	
	public Project[] getProjects() { return projects; }
	public void setProjects(Project[] projects) { this.projects = projects; }
	
	public ListProjectsResponse(Project[] projects) {
		this.projects = projects;
	}
	
	public ListProjectsResponse() {
	}
}
