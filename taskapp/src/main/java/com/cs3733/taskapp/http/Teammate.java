package com.cs3733.taskapp.http;

public class Teammate {

	String tasks[];
	String name;
	
	public String[] getTasks() { return tasks; }
	public void setTasks(String[] tasks) { this.tasks = tasks; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }	
	
	public Teammate(String[] tasks, String name) {
		this.tasks = tasks;
		this.name = name;
	}
	public String toString() {
		return "Teammate(" + name + ")";
	}
	
	public Teammate() {
	}

}
