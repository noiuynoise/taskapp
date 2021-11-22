package edu.wpi.cs.heineman.demo;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
import edu.wpi.cs.heineman.demo.http.CreateConstantRequest;
import edu.wpi.cs.heineman.demo.http.CreateConstantResponse;
import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * Create a new constant and store in S3 bucket.

 * @author heineman
 */
public class CreateConstantHandler implements RequestHandler<CreateConstantRequest,CreateConstantResponse> {

	LambdaLogger logger;
	
	// To access S3 storage
	private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "constants/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createConstant(String name, double value) throws Exception { 
		if (logger != null) { logger.log("in createConstant"); }
		ConstantsDAO dao = new ConstantsDAO();
		
		// check if present
		Constant exist = dao.getConstant(name);
		Constant constant = new Constant (name, value);
		if (exist == null) {
			return dao.addConstant(constant);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean createSystemConstant(String name, double value) throws Exception {
		if (logger != null) { logger.log("in createSystemConstant"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = REAL_BUCKET;
		
		byte[] contents = ("" + value).getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest("calculator-aws-example", bucket + name, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
	@Override 
	public CreateConstantResponse handleRequest(CreateConstantRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateConstantResponse response;
		try {
			if (req.system) {
				if (createSystemConstant(req.name, req.value)) {
					response = new CreateConstantResponse(req.name);
				} else {
					response = new CreateConstantResponse(req.name, 422);
				}
			} else {
				if (createConstant(req.name, req.value)) {
					response = new CreateConstantResponse(req.name);
				} else {
					response = new CreateConstantResponse(req.name, 422);
				}
			}
		} catch (Exception e) {
			response = new CreateConstantResponse("Unable to create constant: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
