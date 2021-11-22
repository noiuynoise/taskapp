package edu.wpi.cs.heineman.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.heineman.demo.http.AllConstantsResponse;
import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListConstantsTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	ListAllConstantsHandler handler = new ListAllConstantsHandler();

        AllConstantsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasE = false;
        for (Constant c : resp.list) {
        	System.out.println("found constant " + c);
        	if (c.name.equals("e")) { hasE = true; }
        }
        Assert.assertTrue("e Needs to exist in the constants table (from tutorial) for this test case to work.", hasE);
        Assert.assertEquals(200, resp.statusCode);
    }

}
