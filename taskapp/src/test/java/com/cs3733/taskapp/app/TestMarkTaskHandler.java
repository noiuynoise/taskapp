package com.cs3733.taskapp.app;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.cs3733.taskapp.http.AddTaskRequest;
import com.cs3733.taskapp.http.CreateProjectRequest;
import com.cs3733.taskapp.http.DecomposeTaskRequest;
import com.cs3733.taskapp.http.DeleteProjectRequest;
import com.cs3733.taskapp.http.MarkTaskRequest;
import com.cs3733.taskapp.http.ProjectRequest;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestMarkTaskHandler extends LambdaTest {
    private final String CONTENT_TYPE = "image/jpeg";
    private S3Event event;

    @Mock
    private AmazonS3 s3Client;
    @Mock
    private S3Object s3Object;

    @Captor
    private ArgumentCaptor<GetObjectRequest> getObjectRequest;

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
	
	
    @Test
	public void testMarkTaskHandler() {
    	//make project
    	CreateProjectHandler handler1 = new CreateProjectHandler(s3Client);
    	String testProjectName = "teusdhgoirtslngi";
    	ProjectResponse response1 = handler1.handleRequest(testProjectName, createContext());
    	String puuid = response1.getProjectTUUID();
    	//add task to project
    	AddTaskHandler handler2 = new AddTaskHandler(s3Client);
    	String[] bop = {"boop"};
    	AddTaskRequest req = new AddTaskRequest(puuid, bop);
        Task[] response2 = handler2.handleRequest(req, createContext());
        
        // get task UUID
        String holdMeTUUID = "";
        
        for(Task tsk: response2) {
            for(String str: bop) {
            	if (tsk.getName().equals(str))
            		holdMeTUUID = tsk.getID();
            		//task added
            }
        }
        //decompose task from project
        MarkTaskHandler handler3 = new MarkTaskHandler(s3Client);
        MarkTaskRequest mrkReq = new MarkTaskRequest(puuid,holdMeTUUID, true);
        Boolean response3 = handler3.handleRequest(holdMeTUUID, createContext()); 
       
        Assert.assertTrue(response3);
        
        //delete it
    	DeleteProjectHandler handler4 = new DeleteProjectHandler(s3Client);
    	DeleteProjectRequest req4 = new DeleteProjectRequest(puuid);
    	Boolean response4 = handler4.handleRequest(puuid, createContext());
	}
}









