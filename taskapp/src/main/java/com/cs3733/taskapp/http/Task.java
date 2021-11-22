package com.cs3733.taskapp.http;

public class Task {
	
	String name;
	Task subtasks[];
	String id;
	String parentID;
	Boolean complete;
	int idNum;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Task[] getSubtasks() { return subtasks; }
	public void setSubtasks(Task[] subtasks) { this.subtasks = subtasks; }

	public String getID() { return id; }
	public void setID(String id) { this.id = id; }
	
	public String getParentID () {return parentID;}
	public void setParentID (String parentID) {this.parentID = parentID;}

	public Boolean getComplete() { return complete; }
	public void setComplete(Boolean complete) { this.complete = complete; }
	
	public int getIDNum () {return idNum;}
	public void setIDNum (int idNum) {this.idNum = idNum;}
	
	public String toString() {
		return "Task(" + name + ", " + id + ", " + complete + ", " + parentID +")";
	}

	
	public Task (String TUUID, String PUUID, String name, boolean complete, boolean archived, int idNum)
	{
		this.name = name;
		this.complete = complete;
		this.idNum = idNum;
		this.parentID = PUUID;
		this.id = TUUID;
		
	}
	
	public Task () { 
		
	}

}
