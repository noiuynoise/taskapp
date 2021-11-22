package edu.wpi.cs.heineman.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
import edu.wpi.cs.heineman.demo.http.AllConstantsResponse;
import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * Eliminated need to work with JSON
 */
public class ListAllConstantsHandler implements RequestHandler<Object,AllConstantsResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "constants";

	public static final String TOP_LEVEL_BUCKET = "calculator-aws-example";
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Constant> getConstants() throws Exception {
		logger.log("in getConstants");
		ConstantsDAO dao = new ConstantsDAO();
		
		return dao.getAllConstants();
	}
	
	// I am leaving in this S3 code so it can be a LAST RESORT if the constant is not in the database
	private AmazonS3 s3 = null;
	
	/**
	 * Retrieve all SYSTEM constants. This code is surprisingly dangerous since there could
	 * be an incredible number of objects in the bucket. Ignoring this for now.
	 */
	List<Constant> systemConstants() throws Exception {
		logger.log("in systemConstants");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		ArrayList<Constant> sysConstants = new ArrayList<>();
	    
		String bucket = REAL_BUCKET;
		// retrieve listing of all objects in the designated bucket
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				  .withBucketName(TOP_LEVEL_BUCKET)    // top-level bucket
				  .withPrefix(bucket);            		  // sub-folder declarations here (i.e., a/b/c)
												  
		
		// request the s3 objects in the s3 bucket 'cs3733wpi/constants' -- change based on your bucket name
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects) {
	      String name = os.getKey();
		  logger.log("S3 found:" + name);

	      // If name ends with slash it is the 'constants/' bucket itself so you skip
	      if (name.endsWith("/")) { continue; }
			
	      S3Object obj = s3.getObject(TOP_LEVEL_BUCKET, name);
	    	
	    	try (S3ObjectInputStream constantStream = obj.getObjectContent()) {
				Scanner sc = new Scanner(constantStream);
				String val = sc.nextLine();
				sc.close();
				
				// just grab name *after* the slash. Note this is a SYSTEM constant
				int postSlash = name.indexOf('/');
				sysConstants.add(new Constant(name.substring(postSlash+1), Double.valueOf(val), true));
			} catch (Exception e) {
				logger.log("Unable to parse contents of " + name);
			}
	    }
		
		return sysConstants;
	}
	
	@Override
	public AllConstantsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");

		AllConstantsResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Constant> list = getConstants();
			for (Constant c : systemConstants()) {
				if (!list.contains(c)) {
					list.add(c);
				}
			}
			response = new AllConstantsResponse(list, 200);
		} catch (Exception e) {
			response = new AllConstantsResponse(403, e.getMessage());
		}
		
		return response;
	}
}
