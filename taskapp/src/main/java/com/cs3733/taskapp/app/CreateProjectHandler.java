package com.cs3733.taskapp.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;
import com.cs3733.taskapp.http.Teammate;

public class CreateProjectHandler implements RequestHandler<String, ProjectResponse> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

    public CreateProjectHandler() {}

    // Test purpose only.
    CreateProjectHandler(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public ProjectResponse handleRequest(String input, Context context) {
        context.getLogger().log("Received name: " + input);

        ProjectResponse project = new ProjectResponse(input, new Teammate[0], new Task[0], false);
        try {
            //S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            //String contentType = response.getObjectMetadata().getContentType();
            //context.getLogger().log("CONTENT TYPE: " + contentType);
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