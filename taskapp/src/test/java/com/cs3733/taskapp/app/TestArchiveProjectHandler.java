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
import com.cs3733.taskapp.http.DeleteProjectRequest;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;
import com.cs3733.taskapp.http.TeammateRequest;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestArchiveProjectHandler extends LambdaTest {
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

	void testSuccessInput() throws IOException {

    	
    }
	
    @Test
	public void testArchiveProjectHandle() {
    	//create project to delete
    	CreateProjectHandler handler1 = new CreateProjectHandler(s3Client);
    	String testProjectName = "teusdhgoirtslngi";
    	ProjectResponse response1 = handler1.handleRequest(testProjectName, createContext());
    	
    	String puuid = response1.getProjectTUUID();
    	
    	ArchiveProjectHandler handler2 = new ArchiveProjectHandler(s3Client);
    	Assert.assertTrue(handler2.handleRequest(puuid, createContext()));
    	
    	//delete it
    	DeleteProjectHandler handler3 = new DeleteProjectHandler(s3Client);
    	DeleteProjectRequest req = new DeleteProjectRequest(puuid);
    	Boolean response2 = handler3.handleRequest(puuid, createContext());
	}
}

