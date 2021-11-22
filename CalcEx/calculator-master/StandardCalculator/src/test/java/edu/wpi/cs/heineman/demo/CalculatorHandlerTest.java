package edu.wpi.cs.heineman.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.http.*;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CalculatorHandlerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	CalculatorHandler handler = new CalculatorHandler();
    	AddRequest req = new Gson().fromJson(incoming, AddRequest.class);
        AddResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.result);
        Assert.assertEquals(200, response.statusCode);
    }
	
    void testFailInput(String incoming, String outgoing) throws IOException {
    	CalculatorHandler handler = new CalculatorHandler();
    	AddRequest req = new Gson().fromJson(incoming, AddRequest.class);

    	AddResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(400, response.statusCode);
    }
    
    @Test
    public void testCalculatorSimple() {
    	String SAMPLE_INPUT_STRING = "{\"arg1\": \"17\", \"arg2\": \"19\"}";
        String RESULT = "36.0";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    // presumes the existence of a pi constant -- which is manually copied in testconstants area
    @Test
    public void testCalculatorConstant() {
    	String SAMPLE_INPUT_STRING = "{\"arg1\": \"pi\", \"arg2\": \"19\"}";
        String RESULT = "22.1415926";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
 // presumes the existence of a e constant -- which was created during tutorial
    @Test
    public void testCalculatorEConstant() {
    	String SAMPLE_INPUT_STRING = "{\"arg1\": \"e\", \"arg2\": \"19\"}";
        String RESULT = "21.71828";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testFailInput() {
    	String SAMPLE_INPUT_STRING = "{\"arg1\": \"- GARBAGE -\", \"arg2\": \"10\"}";
        String RESULT = "";
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
