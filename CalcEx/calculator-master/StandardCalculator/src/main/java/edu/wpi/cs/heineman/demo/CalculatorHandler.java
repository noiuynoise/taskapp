package edu.wpi.cs.heineman.demo;

import java.util.Scanner;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
import edu.wpi.cs.heineman.demo.http.AddRequest;
import edu.wpi.cs.heineman.demo.http.AddResponse;
import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * Final version of calculator.
 * 
 * If using just double values as strings, then returns the result.
 * If any of the strings do not parse as a number, they are searched for as a constant.
 * First we search the RDS database.
 * Second, we attempt to load up from S3 bucket.
 * 
 * Note: I have stopped using com.fasterxml.jackson.databind.JsonNode and instead use two different
 * JSon packages. SimpleJson is just that -- Simple!. GSon is a google package that is quite useful
 * 
 * @author heineman
 */
public class CalculatorHandler implements RequestHandler<AddRequest,AddResponse> {

	// I am leaving in this S3 code so it can be a LAST RESORT if the constant is not in the database
	private AmazonS3 s3 = null;

	LambdaLogger logger;
	
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "constants";
	public static final String TEST_BUCKET = "testconstants";

	// This is how you would retrieve a constant, but not something we will do anymore
	double getDoubleFromBucket(String constantName) throws Exception {
		if (s3 == null) {
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		}

		String bucket = REAL_BUCKET;
		boolean useTestDB = System.getenv("TESTING") != null;
		if (useTestDB) {
			bucket = TEST_BUCKET;
		}
		S3Object obj = s3.getObject("calculator-aws-example/" + bucket, constantName);

		try (S3ObjectInputStream constantStream = obj.getObjectContent()) {
			Scanner sc = new Scanner(constantStream);
			String val = sc.nextLine();
			sc.close();
			return Double.parseDouble(val);
		}
	}

	/**
	 * Try to get from RDS first. Then get from bucket.
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public double loadConstant(String arg) throws Exception {
		double val = 0;
		try {
			val = loadValueFromRDS(arg);
			return val;
		} catch (Exception e) {
			return getDoubleFromBucket(arg);
		}
	}

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	double loadValueFromRDS(String arg) throws Exception {
		if (logger != null) { logger.log("in loadValue"); }
		ConstantsDAO dao = new ConstantsDAO();
		if (logger != null) { logger.log("retrieved DAO"); }
		Constant constant = dao.getConstant(arg);
		if (logger != null) { logger.log("retrieved Constant"); }
		return constant.value;
	}

	@Override
	public AddResponse handleRequest(AddRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		boolean fail = false;
		String failMessage = "";
		double val1 = 0.0;
		try {
			val1 = Double.parseDouble(req.getArg1());
		} catch (NumberFormatException e) {
			try {
				val1 = loadConstant(req.getArg1());
			} catch (Exception ex) {
				failMessage = req.getArg1() + " is an invalid constant.";
				fail = true;
			}
		}

		double val2 = 0.0;
		try {
			val2 = Double.parseDouble(req.getArg2());
		} catch (NumberFormatException e) {
			try {
				val2 = loadConstant(req.getArg2());
			} catch (Exception ex) {
				failMessage = req.getArg2() + " is an invalid constant.";
				fail = true;
			}
		}

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		AddResponse response;
		if (fail) {
			response = new AddResponse(400, failMessage);
		} else {
			response = new AddResponse(val1 + val2, 200);  // success
		}

		return response; 
	}
}

