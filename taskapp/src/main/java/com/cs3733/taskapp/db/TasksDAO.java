package com.cs3733.taskapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.cs3733.taskapp.http.ProjectResponse;
import com.cs3733.taskapp.http.Task;


/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class TasksDAO { 

	java.sql.Connection conn;
	
	final String tblName = "TaskApp_Tasks";   // Exact capitalization
	Context context;
	
    public TasksDAO(Context context) {
    	this.context = context;
    	try  {
    		context.getLogger().log("Connecting");
    		conn = DatabaseUtil.connect();
    		context.getLogger().log("Connected");
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public List<TaskEntry> getTaskByTUUID(String TUUID) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE TUUID=?;"); //check
            ps.setString(1,  TUUID);
            ResultSet resultSet = ps.executeQuery();
            
            List<TaskEntry> output = resultsToTasks(resultSet);
            
            resultSet.close();
            ps.close();
            
            return output;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    public List<TaskEntry> getTaskByPUUID(String PUUID) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE PUUID=?;"); //check
            ps.setString(1,  PUUID);
            ResultSet resultSet = ps.executeQuery();
            
            List<TaskEntry> output = resultsToTasks(resultSet);
            
            resultSet.close();
            ps.close();
            
            return output;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    public List<TaskEntry> getTaskByName(String name) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;"); //check
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            List<TaskEntry> output = resultsToTasks(resultSet);
            
            resultSet.close();
            ps.close();
            
            return output;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    public boolean updateTask(TaskEntry entry) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET PUUID=? name=? complete=? archived=? id=?  WHERE TUUID=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
        	
        	ps.setString(1, entry.PUUID);
        	ps.setString(2, entry.name);
        	ps.setBoolean(3, entry.complete);
        	ps.setBoolean(4, entry.archived);
        	ps.setInt(5, entry.id);
        	ps.setString(6, entry.TUUID);
        	
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteTask(String TUUID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE TUUID = ?;");
            ps.setString(1, TUUID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert task: " + e.getMessage());
        }
    }


    public boolean addTask(TaskEntry entry) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE TUUID = ?;");
            ps.setString(1, entry.TUUID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            if (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (TUUID,PUUID,name,complete,archived,id) values(?,?,?,?,?,?);");
        	ps.setString(1, entry.TUUID);
        	ps.setString(2, entry.PUUID);
        	ps.setString(3, entry.name);
        	ps.setBoolean(4, entry.complete);
        	ps.setBoolean(5, entry.archived);
        	ps.setInt(6, entry.id);
        	
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert task: " + e.getMessage());
        }
    }

    public List<TaskEntry> getAllTasks() throws Exception {
        
        
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            List<TaskEntry> allTasks = resultsToTasks(resultSet);
            		
            resultSet.close();
            statement.close();
            
            return allTasks;

        } catch (Exception e) {
            throw new Exception("Failed in getting tasks: " + e.getMessage());
        }
    }
    
    public TaskEntry getProjectByName(String name) throws Exception{
		List<TaskEntry> possibleProjects = getTaskByName(name);
		for(TaskEntry entry: possibleProjects) {
			if(!entry.PUUID.contentEquals("")) {
				possibleProjects.remove(entry);
			}
		}
		if(possibleProjects.size() != 1) {
			throw new Exception("project does not exist or multiple projects with same name exist");
		}
		return possibleProjects.get(0);
    }
    
    public Task getTask(String TUUID) throws Exception {
    	Task output = new Task();
    	try {
    		List<TaskEntry> entryList = getTaskByTUUID(TUUID);
    		if(entryList.size() != 1) {
    			throw new Exception("More than one Task with TUUID exists or Task with TUUID does not exist");
    		}
    		TaskEntry entry = entryList.get(0);
    		output.setID(entry.TUUID);
    		output.setParentID(entry.PUUID);
    		output.setName(entry.name);
    		output.setComplete(entry.complete);
    		output.setIDNum(entry.id);
    		ArrayList<Task> subtasks = new ArrayList<Task>();
    		for(TaskEntry subtask: getTaskByPUUID(entry.TUUID)) {
    			subtasks.add(getTask(subtask.TUUID));
    		}
    		output.setSubtasks(subtasks.toArray(new Task[0]));
    		return output;
    	}catch (Exception e){
    		String errorText = "failed to retrieve Task from TUUID: " + TUUID + " Error: " + e.getMessage();
    		this.context.getLogger().log(errorText);
    		throw new Exception(errorText);
    	}
    	
    }
    
    private List<TaskEntry> resultsToTasks(ResultSet results) throws SQLException{
    	
    	List<TaskEntry> output = new ArrayList<TaskEntry>();
    	
    	while(results.next()) {
    		
        	String TUUID = results.getString("TUUID");
        	String PUUID = results.getString("PUUID");
        	String name  = results.getString("name");
        	boolean complete = results.getBoolean("complete");
        	boolean archived = results.getBoolean("archived");
        	int idNum = results.getInt("id");
        	
        	output.add(new TaskEntry(TUUID, PUUID, name, complete, archived, idNum));
        	
    	}
    	
    	return output;
    }
}