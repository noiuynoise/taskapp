package edu.wpi.cs.heineman.demo;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.heineman.demo.http.CreateConstantRequest;
import edu.wpi.cs.heineman.demo.http.CreateConstantResponse;
import edu.wpi.cs.heineman.demo.http.DeleteConstantRequest;
import edu.wpi.cs.heineman.demo.http.DeleteConstantResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteConstantHandlerTest extends LambdaTest {

    @Test
    public void testCreateAndDeleteConstant() {
    	// create constant
        int rnd = (int) (Math.random() * 1000000);
        CreateConstantRequest ccr = new CreateConstantRequest("x" + rnd, 9.123);
        
        CreateConstantResponse resp = new CreateConstantHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeleteConstantRequest dcr = new DeleteConstantRequest("x" + rnd);
        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);
        
        // try again and this should fail
        d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
   
}
