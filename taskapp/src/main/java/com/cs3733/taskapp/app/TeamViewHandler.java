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
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.TeamViewResponse;
import com.cs3733.taskapp.http.Teammate;

public class TeamViewHandler implements RequestHandler<String, TeamViewResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
	public static final String REAL_BUCKET = "TaskApp_Tasks/";

    public TeamViewHandler() {}

    // Test purpose only.
    TeamViewHandler(AmazonS3 s3) {
        this.s3 = s3;
    }
    
    
    @Override
    public TeamViewResponse handleRequest(String input, Context context) {
    	
        context.getLogger().log("Getting team view for Project: " + input);
        
        TasksDAO taskdao = new TasksDAO(context);
        TeammateDAO teamdao = new TeammateDAO(context);
        
    	try {
    		
    		TaskEntry projectEntry = taskdao.getProjectByName(input);
    		
    		TeamViewResponse response = new TeamViewResponse();
    		
    		List<TeammateEntry> teammateEntries = teamdao.getTeammateByTUUID(projectEntry.TUUID);
    		List<Teammate> teammates = new ArrayList<Teammate>();
    		
    		/*
    		for(TeammateEntry teammateEntry: teammateEntries) {
    			Teammate teammate = new Teammate();
    			teammate.setName(name);
    		}
    		*/
    		response.setProjectID(projectEntry.TUUID);
    		//response.setTeammates(teammates.toArray(new TeammateEntry[0]));
    		
    		return response;
    		
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage());
    	}
    }
}
