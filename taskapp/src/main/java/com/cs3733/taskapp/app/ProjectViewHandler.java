package com.cs3733.taskapp.app;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import java.util.ArrayList;
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
import com.cs3733.taskapp.db.TeammateDAO;
import com.cs3733.taskapp.db.TeammateEntry;
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
    	
        context.getLogger().log("Getting project view for Project: " + input);
        
        TasksDAO taskdao = new TasksDAO(context);
        TeammateDAO teamdao = new TeammateDAO(context);
        
    	try {
    		List<TaskEntry> possibleProjects = taskdao.getTaskByName(input);
    		for(TaskEntry entry: possibleProjects) {
    			if(!entry.PUUID.contentEquals("")) {
    				possibleProjects.remove(entry);
    			}
    		}
    		if(possibleProjects.size() != 1) {
    			throw new Exception("project does not exist or multiple projects with same name exist");
    		}
    		TaskEntry projectEntry = possibleProjects.get(0);
    		
    		ProjectResponse response = new ProjectResponse();
    		response.setArchived(projectEntry.archived);
    		response.setProjectTUUID(projectEntry.TUUID);
    		response.setProjectName(projectEntry.name);
    		
    		Task projectTask = taskdao.getTask(projectEntry.TUUID);
    		
    		response.setTasks(projectTask.getSubtasks());
    		
    		List<String> allTUUID = projectTask.getAllTUUID();
    		
    		//context.getLogger().log(allTUUID.toString());
    		
    		TeammateEntry teamList[] = teamdao.getTeammateByTUUID(projectEntry.TUUID).toArray(new TeammateEntry[0]);
    		
    		//context.getLogger().log(teamList.toString());
    		
    		List<Teammate> teammateList = new ArrayList<Teammate>();
    		
    		for(TeammateEntry teammate:teamList) {
    			Teammate newTeammate = new Teammate();
    			newTeammate.setName(teammate.name);
    			
    			List<String> assignedTasks = new ArrayList<String>();
    			List<TeammateEntry> teamTasks = teamdao.getTeammateByName(teammate.name);
    			
    			for(TeammateEntry task:teamTasks) {
    				if(allTUUID.contains(task.TUUID)) {
    					assignedTasks.add(task.TUUID);
    				}
    			}
    			
    			newTeammate.setTasks(assignedTasks.toArray(new String[0]));
    			
    			teammateList.add(newTeammate);
    		}
    		
    		response.setTeammates(teammateList.toArray(new Teammate[0]));
    		
    		return response;
    		
    	}catch(Exception e) {
    		context.getLogger().log("Error: "+e.getMessage());
    		throw new RuntimeException(e.getMessage());
    	}
    }
}