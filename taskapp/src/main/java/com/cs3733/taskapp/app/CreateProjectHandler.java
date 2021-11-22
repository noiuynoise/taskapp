package com.cs3733.taskapp.app;

import java.io.ByteArrayInputStream;

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

public class CreateProjectHandler implements RequestHandler<String, ProjectResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public CreateProjectHandler() {}

    // Test purpose only.
    CreateProjectHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createTask(Task task) throws Exception { 
		//if (logger != null) { logger.log("in createConstant"); }
		TasksDAO dao = new TasksDAO();
		
		// check if present
		Task exist = dao.getTask(task.getID());
		//Task constant = new Task (exist.getID(), exist.getParentID(), exist.getName(), exist.getComplete(), false, exist.getIDNum());
		if (exist == null) {
			return dao.addTask(task);
		} else {
			return false;
		}
	}
    


    
    
    
    @Override
    public ProjectResponse handleRequest(String input, Context context) {
//    	logger = context.getLogger();
//		logger.log(req.toString());

    	
        context.getLogger().log("Received name: " + input);

        ProjectResponse project = new ProjectResponse(input, new Teammate[0], new Task[0], false);
        try {
            //S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            //String contentType = response.getObjectMetadata().getContentType();
            //context.getLogger().log("CONTENT TYPE: " + contentType);
        	
        	Task newTask = new Task ();
        	
        	
            return project;
        } catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object from bucket. Make sure they exist and"
                + " your bucket is in the same region as this function."));
            throw e;
        }
    }
}