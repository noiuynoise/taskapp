package com.cs3733.taskapp.http;

public class Teammate {

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
	public String toString() {
		return "Teammate(" + name + ")";
	}
	
	public Teammate() {
	}

}
