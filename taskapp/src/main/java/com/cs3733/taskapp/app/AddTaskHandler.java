package com.cs3733.taskapp.app;

import java.util.ArrayList;
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
import com.cs3733.taskapp.http.AddTaskRequest;
import com.cs3733.taskapp.http.Project;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;
import com.cs3733.taskapp.http.TeammateRequest;

public class AddTaskHandler implements RequestHandler<AddTaskRequest, Task[]> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public AddTaskHandler() {}

    // Test purpose only.
    AddTaskHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Task[] handleRequest(AddTaskRequest input, Context context) {

        context.getLogger().log("\nAdding Task(s): " + input.getTasks().toString() + " to: " + input.getProjectID() + "\n");
        
        TasksDAO taskdao = new TasksDAO(context);
    	try {
    		//check if ID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input.getProjectID());
    		if(currentProjects.isEmpty()) { throw new Exception("project with PUUID does not exist");}
    		if(! currentProjects.get(0).PUUID.equals("")){ throw new Exception("project with PUUID does not exist");}
    		
    		List<TaskEntry> currentTasks = taskdao.getTaskByPUUID(input.getProjectID());
    		
    		List<String> currentTaskNames = new ArrayList<String>();
    		
    		for(TaskEntry task:currentTasks) {
    			currentTaskNames.add(task.name);
    		}
    		
    		int currentIndex = currentTaskNames.size() + 1;
    		
    		for(String taskName:input.tasks) {
    			//check if name already exists in project
        		if(currentTaskNames.contains(taskName)) {
        			continue; //won't add duplicate names but will not error out so the rest are added
        		}
        		TaskEntry newTask = new TaskEntry(UUID.randomUUID().toString(), input.getProjectID(), taskName, false, false, currentIndex);
        		
        		taskdao.addTask(newTask);
        		
        		currentIndex += 1;
    		}
    		
    		Task project = taskdao.getTask(input.getProjectID());
    		
    		return project.getSubtasks();
	    	
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}