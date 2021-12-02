package com.cs3733.taskapp.app;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cs3733.taskapp.db.TaskEntry;
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.db.TeammateDAO;
import com.cs3733.taskapp.db.TeammateEntry;
import com.cs3733.taskapp.http.Teammate;
import com.cs3733.taskapp.http.TeammateRequest;

public class MarkTaskHandler implements RequestHandler<String, Boolean> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public MarkTaskHandler() {}

    // Test purpose only.
    MarkTaskHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Boolean handleRequest(String input, Context context) {

        context.getLogger().log("\nMarking Task: " + input + "\n");
    	
        TasksDAO taskdao = new TasksDAO(context);
    	try {
    		//check if ID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input);
    		if(currentProjects.isEmpty()) { throw new Exception("TUUID does not exist");}
    		//if(currentProjects.get(0).PUUID.equals("")) { throw new Exception("TUUID points to task not project. does not exist");}
    		
    		//edit project
    		TaskEntry task = currentProjects.get(0);
    		
    		task.complete = !task.complete;
    		
    		//archive project
    		return taskdao.updateTask(task);
	    	
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}