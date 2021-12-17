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
import com.cs3733.taskapp.http.ProjectRequest;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestDecomposeTaskHandler extends LambdaTest {
    private final String CONTENT_TYPE = "image/jpeg";
    private S3Event event;

    @Mock
    private AmazonS3 s3Client;
    @Mock
    private S3Object s3Object;

    @Captor
    private ArgumentCaptor<GetObjectRequest> getObjectRequest;

    @Before
    public void setUp() throws IOException {
        event = TestUtils.parse("/s3-event.put.json", S3Event.class);

        // TODO: customize your mock logic for s3 client
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(CONTENT_TYPE);
        when(s3Object.getObjectMetadata()).thenReturn(objectMetadata);
        when(s3Client.getObject(getObjectRequest.capture())).thenReturn(s3Object);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
	
	
    @Test
	public void testDecomposeTaskHandler() {
    	//make project
    	CreateProjectHandler handler1 = new CreateProjectHandler(s3Client);
    	String testProjectName = "teusdhgoirtslngi";
    	ProjectResponse response1 = handler1.handleRequest(testProjectName, createContext());
    	//add task to project
    	AddTaskHandler handler2 = new AddTaskHandler(s3Client);
    	String[] bop = {"boop"};
    	AddTaskRequest req = new AddTaskRequest(response1.getProjectTUUID(), bop);
        Task[] response2 = handler2.handleRequest(req, createContext());
        // test task added to project
        String holdMeStr;
        String holdMeTUUID;
        
        for(Task tsk: response2) {
            for(String str: bop) {
            	if (tsk.getName().equals(str))
            		holdMeStr = tsk.getName();
            		holdMeTUUID = tsk.getID();
            		//task added
            }
        }
        //decompose task from project
        DecomposeTaskHandler handler3 = new DecomposeTaskHandler(s3Client);
        DecomposeTaskRequest dcmReq = new DecomposeTaskRequest(response1.getProjectTUUID(), holdMeTUUID, bop);
        Task response3 = handler3.handleRequest(dcmReq, createContext()); 
       
        boolean work = true;
        for(Task tsk: response2) {
        	if (tsk.getID().equals(response3.getID()))
        		work = false;
        }
        Assert.assertTrue(work);
	}
}









