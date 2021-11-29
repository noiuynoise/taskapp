package com.cs3733.taskapp.db;

public class TaskEntry {
	public String name;
	public String TUUID;
	public String PUUID;
	public Boolean complete;
	public Boolean archived;
	public int id;
	
	public TaskEntry(String TUUID, String PUUID, String name, Boolean complete, Boolean archived, int id) {
		this.name = name;
		this.TUUID = TUUID;
		this.PUUID = PUUID;
		this.complete = complete;
		this.archived = archived;
		this.id = id;
	}
}
