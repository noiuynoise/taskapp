package com.cs3733.taskapp.http;

public class AssignTeammateRequest {
	public String taskid;
	public String teammate;

	public String getTaskID() { return taskid; }
	public void setTaskID(String taskid) { this.taskid = taskid; }
	
	public String getTeammate() {return teammate; }
	public void setTeammate(String teammate) { this.teammate = teammate; }

	
	public String toString() {
		return "AssignTeammateRequest(" + taskid +  "," + teammate + ")";
	}
	
	public AssignTeammateRequest(String taskid, String teammate) {
		this.taskid = taskid;
		this.teammate = teammate;
	}
	
	public AssignTeammateRequest() {
	}
}


