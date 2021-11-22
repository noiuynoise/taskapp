package edu.wpi.cs.heineman.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.db.ConstantsDAO;
import edu.wpi.cs.heineman.demo.http.DeleteConstantRequest;
import edu.wpi.cs.heineman.demo.http.DeleteConstantResponse;
import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * No more JSON parsing
 */
public class DeleteConstantHandler implements RequestHandler<DeleteConstantRequest,DeleteConstantResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteConstantResponse handleRequest(DeleteConstantRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteConstantResponse response = null;
		logger.log(req.toString());

		ConstantsDAO dao = new ConstantsDAO();

		// MAKE sure that we prevent attempts to delete system constants...
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Constant constant = new Constant(req.name, 0);
		try {
			if (dao.deleteConstant(constant)) {
				response = new DeleteConstantResponse(req.name, 200);
			} else {
				response = new DeleteConstantResponse(req.name, 422, "Unable to delete constant.");
			}
		} catch (Exception e) {
			response = new DeleteConstantResponse(req.name, 403, "Unable to delete constant: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
