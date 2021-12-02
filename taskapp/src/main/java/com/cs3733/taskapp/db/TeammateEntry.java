package com.cs3733.taskapp.db;

public class TeammateEntry {
	public String name;
	public String TUUID;
	
	public TeammateEntry(String name, String TUUID) {
		this.name = name;
		this.TUUID = TUUID;
	}
	
	public String toString() {
		return "Teammate(" + this.name +  ", " + this.TUUID + ")";
	}
}
