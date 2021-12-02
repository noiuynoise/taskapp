package com.cs3733.taskapp.app;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cs3733.taskapp.db.TaskEntry;
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.db.TeammateDAO;
import com.cs3733.taskapp.db.TeammateEntry;
import com.cs3733.taskapp.http.AssignTeammateRequest;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;
import com.cs3733.taskapp.http.TeammateRequest;

public class AssignTeammateHandler implements RequestHandler<AssignTeammateRequest, Task> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public AssignTeammateHandler() {}

    // Test purpose only.
    AssignTeammateHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Task handleRequest(AssignTeammateRequest input, Context context) {

        context.getLogger().log("\nAssigning Teammate: " + input.getTeammate() + " to: " + input.getTaskID() + "\n");
    	
        TasksDAO taskdao = new TasksDAO(context);
    	TeammateDAO teamdao = new TeammateDAO(context);
    	try {
    		//check if TUUID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input.getTaskID());
    		if(currentProjects.isEmpty()) { throw new Exception("TUUID does not exist");}
    		Task currTask = taskdao.getTask(input.getTaskID());
    		if(currTask.getSubtasks().length > 0){ throw new Exception("Subtasks exist on this task. Task is not terminal");}
    		
    		//find top level PUUID
    		String nextPUUID = currTask.getParentID();
    		String projectTUUID = currTask.getParentID();
    		while(! nextPUUID.equals("")) {
    			projectTUUID = nextPUUID;
    			TaskEntry upperTask = taskdao.getTaskByTUUID(nextPUUID).get(0);
    			nextPUUID = upperTask.PUUID;
    		}
    		
    		List<TeammateEntry> teammatesOnProject = teamdao.getTeammateByTUUID(projectTUUID);
    		
    		List<String> teammateNames = new ArrayList<String>();
    		
    		for(TeammateEntry teammate:teammatesOnProject) {
    			teammateNames.add(teammate.name);
    		}
    		
    		if(! teammateNames.contains(input.getTeammate())) { throw new Exception("Teammate does not exist in project"); }
    		
    		//assign teammate
    		TeammateEntry newEntry = new TeammateEntry(input.getTeammate(), input.getTaskID());
    		teamdao.addTeammate(newEntry);
    		
    		//return task
    		return currTask;
	    	
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}
