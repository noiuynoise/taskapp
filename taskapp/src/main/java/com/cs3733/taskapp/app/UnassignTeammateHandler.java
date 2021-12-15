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
import com.cs3733.taskapp.http.TeammateRequest;

public class UnassignTeammateHandler implements RequestHandler<AssignTeammateRequest, Boolean> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public UnassignTeammateHandler() {}

    // Test purpose only.
    UnassignTeammateHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Boolean handleRequest(AssignTeammateRequest input, Context context) {
	    context.getLogger().log("\nUnassigning Teammate: " + input.getTeammate() + " from: " + input.getTaskID() + "\n");
	
		
	    TasksDAO taskdao = new TasksDAO(context);
		TeammateDAO teamdao = new TeammateDAO(context);
		try {
			
    		//check if TUUID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input.getTaskID());
    		if(currentProjects.isEmpty()) { throw new Exception("TUUID does not exist");}
    		Task currTask = taskdao.getTask(input.getTaskID());
    		if(currTask.getSubtasks().length > 0){ throw new Exception("Subtasks exist on this task. Task is not terminal");}
    		if(currentProjects.get(0).archived){ throw new Exception("project is archived. exist");}
    		
    		//find top level PUUID
    		String nextPUUID = currTask.getParentID();
    		String projectTUUID = currTask.getParentID();
    		while(! nextPUUID.equals("")) {
    			projectTUUID = nextPUUID;
    			TaskEntry upperTask = taskdao.getTaskByTUUID(nextPUUID).get(0);
    			nextPUUID = upperTask.PUUID;
    		}
    		
    		List<TeammateEntry> teammatesOnProject = teamdao.getTeammateByTUUID(projectTUUID);
    		
    		//check if ID is valid
    		List<TaskEntry> project = taskdao.getTaskByTUUID(projectTUUID);
    		if(project.get(0).archived){ throw new Exception("project is archived. exist");}
    		
    		List<String> teammateNames = new ArrayList<String>();
    		
    		for(TeammateEntry teammate:teammatesOnProject) {
    			teammateNames.add(teammate.name);
    		}
    		
    		if(! teammateNames.contains(input.getTeammate())) { throw new Exception("Teammate does not exist in project"); }
			
			boolean status = teamdao.removeTeammate(input.getTeammate(), input.getTaskID());

			if (status == false)
			{
				throw new Exception("Task does not exist");
			}
			
			return status;
			
			
	
    	}catch(Exception e) {
			context.getLogger().log("Error: "+e.getMessage());
			throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
		}
    }
}
