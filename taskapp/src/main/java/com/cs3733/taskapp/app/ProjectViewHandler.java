package com.cs3733.taskapp.app;

import java.io.ByteArrayInputStream;
import java.util.UUID;

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
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;

//import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
//import edu.wpi.cs.heineman.demo.http.CreateConstantResponse;
//import edu.wpi.cs.heineman.demo.model.Constant;

public class ProjectViewHandler implements RequestHandler<String, ProjectResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public ProjectViewHandler() {}

    // Test purpose only.
    ProjectViewHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public ProjectResponse handleRequest(String input, Context context) {
//    	logger = context.getLogger();
//		logger.log(req.toString());

    	
        context.getLogger().log("Received name: " + input);
        
    	Task newTask = new Task(UUID.randomUUID().toString(), "", input, false, false, 0);
    	
    	TasksDAO dao = new TasksDAO(context);
    	try {
	    	if(!dao.addTask(newTask)) {
	    		throw new Exception("a project of this UUID already exists"); //need to check for duplicate names too later
	    	}
	    	return new ProjectResponse(input, new Teammate[0], new Task[0], false);
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		return new ProjectResponse(400);
    	}
    }
}