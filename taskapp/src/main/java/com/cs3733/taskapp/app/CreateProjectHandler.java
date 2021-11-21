package com.cs3733.taskapp.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class CreateProjectHandler implements RequestHandler<String, null> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

    public CreateProjectHandler() {}

    // Test purpose only.
    CreateProjectHandler(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void handleRequest(String input, Context context) {
        context.getLogger().log("Received name: " + input);

        // Get the object from the event and show its content type
        //String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        //String key = event.getRecords().get(0).getS3().getObject().getKey();
        try {
            S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            String contentType = response.getObjectMetadata().getContentType();
            context.getLogger().log("CONTENT TYPE: " + contentType);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object %s from bucket %s. Make sure they exist and"
                + " your bucket is in the same region as this function.", key, bucket));
            throw e;
        }
    }
}