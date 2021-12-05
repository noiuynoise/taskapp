package com.cs3733.taskapp.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cs3733.taskapp.db.TasksDAO;
import com.cs3733.taskapp.db.TeammateDAO;
import com.cs3733.taskapp.http.TeammateRequest;

public class UnassignTeammateHandler implements RequestHandler<TeammateRequest, Boolean> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public UnassignTeammateHandler() {}

    // Test purpose only.
    UnassignTeammateHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Boolean handleRequest(TeammateRequest input, Context context) {
	    context.getLogger().log("\nUnassigning Teammate: " + input.getName() + " from: " + input.getProjectID() + "\n");
	
		
	    TasksDAO taskdao = new TasksDAO(context);
		TeammateDAO teamdao = new TeammateDAO(context);
		try {
		
			boolean status = teamdao.removeTeammate(input.getName(), input.getProjectID());
			
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
