package com.cs3733.taskapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;

public class TeammateDAO {

	java.sql.Connection conn;
	
	final String tblName = "TaskApp_Teammates";   // Exact capitalization
	Context context; //get context for logging functionality

    public TeammateDAO(Context context) {
    	this.context = context;
    	try  {
    		context.getLogger().log("Connecting");
    		conn = DatabaseUtil.connect();
    		context.getLogger().log("Connected");
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public boolean addTeammate(TeammateEntry entry) throws Exception{
    	PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE Teammate = ? AND TUUID = ?;");
        ps.setString(1, entry.name);
        ps.setString(2, entry.TUUID);
        ResultSet resultSet = ps.executeQuery();
    	
        if (resultSet.next()) {
            return false;
        }

        ps = conn.prepareStatement("INSERT INTO " + tblName + " (TUUID,Teammate) values(?,?);");
    	ps.setString(1, entry.TUUID);
    	ps.setString(2, entry.name);
    	
        ps.execute();
        return true;
    }
    
    
    public boolean removeTeammate(String name, String TUUID) throws Exception{
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE Teammate = ? AND TUUID = ?;");
            ps.setString(1, name);
            ps.setString(2, TUUID);
            int numAffected = ps.executeUpdate();
            ps.close();
            if (numAffected == 0)
            {
            	throw new Exception("Cannot delete teammate that does not exist");
            }
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete task: " + e.getMessage());
        }
    	
    }
    
    public List<TeammateEntry> getTeammateByTUUID(String TUUID) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE TUUID=?;"); //check
            ps.setString(1,  TUUID);
            ResultSet resultSet = ps.executeQuery();
            
            List<TeammateEntry> output = resultsToTeammates(resultSet);
            
            resultSet.close();
            ps.close();
            
            return output;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    public List<TeammateEntry> getTeammateByName(String name) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE Teammate=?;"); //check
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            List<TeammateEntry> output = resultsToTeammates(resultSet);
            
            resultSet.close();
            ps.close();
            
            return output;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    private List<TeammateEntry> resultsToTeammates(ResultSet results) throws SQLException{
    	List<TeammateEntry> output = new ArrayList<TeammateEntry>();
    	
    	while(results.next()) {
    		String name = results.getString("Teammate");
    		String TUUID = results.getString("TUUID");
    		output.add(new TeammateEntry(name, TUUID));
    	}
    	
    	return output;
    }
}
