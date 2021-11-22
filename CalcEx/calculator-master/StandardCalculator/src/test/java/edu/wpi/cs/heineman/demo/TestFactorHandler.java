package edu.wpi.cs.heineman.demo;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import edu.wpi.cs.heineman.demo.http.FactorRequest;
import edu.wpi.cs.heineman.demo.http.FactorResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class TestFactorHandler {

	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

	@Test
	public void testFactor() {
		FactorRequest ft = new FactorRequest("9796083");

		FactorHandler handler = new FactorHandler();
		FactorResponse response = handler.handleRequest(ft, createContext("compute"));

		Assert.assertEquals(200, response.statusCode);
		System.out.println(response.list);
		Assert.assertEquals(5, response.list.size());
		Assert.assertTrue(response.list.contains("3"));
		Assert.assertTrue(response.list.contains("11"));
		Assert.assertTrue(response.list.contains("37"));
		Assert.assertTrue(response.list.contains("71"));
		Assert.assertTrue(response.list.contains("113"));
		
	}
	
}
