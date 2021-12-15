package com.cs3733.taskapp.app;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cs3733.taskapp.db.TaskEntry;
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.db.TeammateDAO;
import com.cs3733.taskapp.db.TeammateEntry;
import com.cs3733.taskapp.http.DecomposeTaskRequest;
import com.cs3733.taskapp.http.Task;

public class DecomposeTaskHandler implements RequestHandler<DecomposeTaskRequest, Task> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public DecomposeTaskHandler() {}

    // Test purpose only.
    DecomposeTaskHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Task handleRequest(DecomposeTaskRequest input, Context context) {

        context.getLogger().log("\nDecomposing Task: " + input.getTaskID() + " to: " + input.getTaskID().toString() + "\n");
    	
        TasksDAO taskdao = new TasksDAO(context);
        TeammateDAO teamdao = new TeammateDAO(context);
    	try {
    		//check if ID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input.getProjectID());
    		if(currentProjects.isEmpty()) { throw new Exception("project with PUUID does not exist");}
    		if(! currentProjects.get(0).PUUID.equals("")){ throw new Exception("project with PUUID does not exist");}
    		if(currentProjects.get(0).archived){ throw new Exception("project is archived. exist");}
    		
    		//check if task has already been decomposed
    		Task upperTask = taskdao.getTask(input.getTaskID());
    		if(upperTask.getSubtasks().length > 0) {
    			//throw new Exception("Task has already been decomposed. exists");
    		}
    		
    		//check if project is archived
    		List<TaskEntry> projectEntry = taskdao.getTaskByTUUID(input.getProjectID());
    		if(projectEntry.get(0).archived == true) {
    			throw new Exception("Project is archived. exists");
    		}
    		
    		//remove all teammates assigned to top level task
    		List<TeammateEntry> assignedTeammates = teamdao.getTeammateByTUUID(input.getTaskID());
    		for(TeammateEntry teammate:assignedTeammates) {
    			teamdao.removeTeammate(teammate.name, teammate.TUUID);
    		}
    		
    		//create new tasks
    		int idCounter = 1 + upperTask.getSubtasks().length;
    		for(String taskName:input.getTasks()) {
    			TaskEntry newTask = new TaskEntry(UUID.randomUUID().toString(), input.getTaskID(), taskName, false, false, idCounter);
    			taskdao.addTask(newTask);
    			idCounter += 1;
    		}
    		
    		//return top level task
    		return taskdao.getTask(input.getTaskID());
	    	
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}