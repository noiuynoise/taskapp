package com.cs3733.taskapp.http;

public class Teammate {

//	  Teammate:
//		    type: object
//		    required:
//		    - name
//		    properties:
//		      name:
//		        type: string
//		      tasks:
//		        type: array
//		        items: 
//		          $ref: "#/definitions/Task"
	
	String name;
	Task tasks[];
	
	
}
