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
import com.cs3733.taskapp.http.DeleteProjectRequest;
import com.cs3733.taskapp.http.ProjectRequest;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestCreateProjectHandler extends LambdaTest {
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
	public void testCreateProjectResponse() {
    	//create project to delete
    	CreateProjectHandler handler1 = new CreateProjectHandler(s3Client);
    	String testProjectName = "123 testCreateProjectResponse";
    	ProjectResponse response1 = handler1.handleRequest(testProjectName, createContext());
    	
    	
        Boolean work = false;
        if(response1.getProjectName().equals(testProjectName)) {
        	work = true;
        }
        
        Assert.assertTrue(work);
        
        //delete it
    	DeleteProjectHandler handler2 = new DeleteProjectHandler(s3Client);
    	DeleteProjectRequest req = new DeleteProjectRequest(response1.getProjectTUUID());
    	Boolean response2 = handler2.handleRequest(response1.getProjectTUUID(), createContext());
	}
    
    @Test
   	public void testCreateProjectRequest() {
    	//create project to delete
    	CreateProjectHandler handler1 = new CreateProjectHandler(s3Client);
    	String testProjectName = "123 testCreateProjectResponse";
    	ProjectResponse response1 = handler1.handleRequest(testProjectName, createContext());
    	
    	String puuid = response1.getProjectTUUID();
    	

    	ProjectRequest req = new ProjectRequest("PEENER");
           

        Assert.assertFalse(req.getProjectID().equals(puuid));
        
        //delete it
       	DeleteProjectHandler handler2 = new DeleteProjectHandler(s3Client);
       	DeleteProjectRequest req2 = new DeleteProjectRequest(puuid);
       	Boolean response2 = handler2.handleRequest(puuid, createContext());
   	}
	
}
