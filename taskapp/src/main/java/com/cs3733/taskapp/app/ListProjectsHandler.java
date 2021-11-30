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
import com.cs3733.taskapp.http.ListProjectsResponse;
import com.cs3733.taskapp.http.Project;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.TeamViewResponse;
import com.cs3733.taskapp.http.Teammate;

public class ListProjectsHandler implements RequestHandler<Boolean, ListProjectsResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public ListProjectsHandler() {}

    // Test purpose only.
    ListProjectsHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public ListProjectsResponse handleRequest(Boolean input, Context context) { //boolean input is ignored
    	
        context.getLogger().log("Getting Project View");
        
        TasksDAO taskdao = new TasksDAO(context);
        
    	try {
    		
    		List<TaskEntry> allTasks = taskdao.getAllTasks();
    		
    		ListProjectsResponse response = new ListProjectsResponse();
    		
    		List<Project> projects = new ArrayList<Project>();
    		
    		for(TaskEntry entry:allTasks) {
    			if(entry.PUUID.equals("")) {
    				//add all projects to list
    				Project project = new Project();
    				project.setProjectName(entry.name);
    				project.setProjectID(entry.TUUID);
    				project.setArchived(entry.archived);
    				Task projectObject = taskdao.getTask(entry.TUUID); //this makes many calls to database
    				project.setCompletion(projectObject.getCompletionPercent());
    				projects.add(project);
    			}
    		}
    		
    		response.setProjects(projects.toArray(new Project[0]));
    		
    		return response;
    		
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage());
    	}
    }
}