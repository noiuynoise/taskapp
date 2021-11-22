package edu.wpi.cs.heineman.demo;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.http.FactorRequest;
import edu.wpi.cs.heineman.demo.http.FactorResponse;

public class FactorHandler implements RequestHandler<FactorRequest,FactorResponse> {

	LambdaLogger logger;
	
	@Override
	public FactorResponse handleRequest(FactorRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of FactorHandle");
		logger.log(req.toString());

		boolean fail = false;
		String failMessage = "";
		int val1 = 0;
		try {
			val1 = Integer.parseInt(req.getArg1());
		} catch (NumberFormatException e) {
			failMessage = req.getArg1() + " is not an integer.";
			fail = true;
		}

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		FactorResponse response;
		if (fail) {
			response = new FactorResponse(400, failMessage);
		} else {
			ArrayList<String> factors = new ArrayList<>();
			while (val1 > 1) {
				if (val1 % 2 == 0) {
					factors.add("2");
					val1 /= 2;
				} else {
					break;
				}
			}
			
			double target = Math.sqrt(val1);
			for (int p = 3; p <= target; p += 2) {
				while (val1 % p == 0) {
					factors.add("" + p);
					val1 /= p;
				}
			}
			
			if (val1 != 1) {
				factors.add("" +val1);
			}
			
			response = new FactorResponse(factors, 200);  // success
		}

		return response; 
	}
}