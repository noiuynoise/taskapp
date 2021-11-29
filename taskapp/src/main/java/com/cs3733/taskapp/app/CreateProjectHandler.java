package com.cs3733.taskapp.app;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.cs3733.taskapp.db.TaskEntry;
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;

//import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
//import edu.wpi.cs.heineman.demo.http.CreateConstantResponse;
//import edu.wpi.cs.heineman.demo.model.Constant;

public class CreateProjectHandler implements RequestHandler<String, ProjectResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public CreateProjectHandler() {}

    // Test purpose only.
    CreateProjectHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public ProjectResponse handleRequest(String input, Context context) {

        context.getLogger().log("Creating Project: " + input);
        
        TaskEntry newEntry = new TaskEntry(UUID.randomUUID().toString(), "", input, false, false, 0);
    	
    	TasksDAO dao = new TasksDAO(context);
    	try {
    		//check for duplicates of same project name
    		List<TaskEntry> duplicateProjects = dao.getTaskByName(input);
    		for(TaskEntry project : duplicateProjects) {
    			if(project.PUUID.equals("")) {
    				throw new Exception("a project with this name already exists");
    			}
    		}
	    	if(!dao.addTask(newEntry)) {
	    		throw new Exception("a project of this TUUID already exists"); //need to check for duplicate names too later
	    	}
	    	return new ProjectResponse(input, new Teammate[0], new Task[0], false);
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}