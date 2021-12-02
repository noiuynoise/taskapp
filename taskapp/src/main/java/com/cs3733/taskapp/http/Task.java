package com.cs3733.taskapp.http;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> getAllTUUID(){
		List<String> output = new ArrayList<String>();
		output.add(this.id);
		for(Task subtask:this.subtasks) {
			output.addAll(subtask.getAllTUUID());
		}
		return output;
	}
	
	public double getCompletionPercent() {
		if(subtasks.length == 0) {
			if(complete) {
				return 100.0;
			}else {
				return 0.0;
			}
		}else{
			double total = 0.0;
			for(Task t:subtasks) {
				total += t.getCompletionPercent();
			}
			return total / subtasks.length;
		}
	}
	
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
