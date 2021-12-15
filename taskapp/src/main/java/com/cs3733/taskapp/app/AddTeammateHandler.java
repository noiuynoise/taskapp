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
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;
import com.cs3733.taskapp.http.TeammateRequest;

public class AddTeammateHandler implements RequestHandler<TeammateRequest, Teammate> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public AddTeammateHandler() {}

    // Test purpose only.
    AddTeammateHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public Teammate handleRequest(TeammateRequest input, Context context) {

        context.getLogger().log("\nAdding Teammate: " + input.getName() + " to: " + input.getProjectID() + "\n");
        
        Teammate newTeammate = new Teammate(new String[0], input.getName());
    	
        TasksDAO taskdao = new TasksDAO(context);
    	TeammateDAO teamdao = new TeammateDAO(context);
    	try {
    		//check if ID is valid
    		List<TaskEntry> currentProjects = taskdao.getTaskByTUUID(input.getProjectID());
    		if(currentProjects.isEmpty()) { throw new Exception("project with PUUID does not exist");}
    		if(! currentProjects.get(0).PUUID.equals("")){ throw new Exception("project with PUUID does not exist");}
    		if(currentProjects.get(0).archived){ throw new Exception("project is archived. exist");}
    		
    		
    		//check if name already exists
    		List<TeammateEntry> currentTeammates = teamdao.getTeammateByTUUID(input.getProjectID());
    		
    		for(TeammateEntry teammate:currentTeammates) {
    			if(teammate.name.equals(input.getName())) { throw new Exception("A teammate of this name already exists");}
    		}
    		
    		if(teamdao.addTeammate(new TeammateEntry(input.getName(), input.getProjectID()))) { return newTeammate;}
    		throw new Exception("error adding teammate. teammate does not exist in database");
	    	
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage()); //runtime exception means 400 response
    	}
    }
}
