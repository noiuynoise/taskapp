package com.cs3733.taskapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public TasksDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Task getTask(String taskID) throws Exception {
        
        try {
            Task task = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE TUUID=?;"); //check
            ps.setString(1,  taskID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                task = generateTask(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return task;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting task: " + e.getMessage());
        }
    }
    
    public boolean updateTask(Task task) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE TUUID=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
//            ps.setDouble(1, task.value);
//            ps.setString(2, constant.name);
        	ps.setString(1, task.getID());
        	ps.setString(2, task.getParentID());
        	ps.setString(3, task.getName());
        	ps.setBoolean(4, task.getComplete());
        	ps.setBoolean(5, false);
        	ps.setInt(6, task.getIDNum());
        	
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteTask(Task task) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE TUUID = ?;");
            ps.setString(1, task.getID());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert task: " + e.getMessage());
        }
    }


    public boolean addTask(Task task) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE TUUID = ?;");
            ps.setString(1, task.getID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Task c = generateTask(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (TUUID,PUUID,name,complete,archived,id) values(?,?,?,?,?,?);");
        	ps.setString(1, task.getID());
        	ps.setString(2, task.getParentID());
        	ps.setString(3, task.getName());
        	ps.setBoolean(4, task.getComplete());
        	ps.setBoolean(5, false);
        	ps.setInt(6, task.getIDNum());
        	
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert task: " + e.getMessage());
        }
    }

    public List<Task> getAllTasks() throws Exception {
        
        List<Task> allTasks = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Task c = generateTask(resultSet);
                allTasks.add(c);
            }
            resultSet.close();
            statement.close();
            return allTasks;

        } catch (Exception e) {
            throw new Exception("Failed in getting tasks: " + e.getMessage());
        }
    }
    
    private Task generateTask(ResultSet resultSet) throws Exception {
    	String TUUID = resultSet.getString("TUUID");
    	String PUUID = resultSet.getString("PUUID");
    	String name  = resultSet.getString("name");
    	boolean complete = resultSet.getBoolean("complete");
    	boolean archived = resultSet.getBoolean("archived");
    	int idNum = resultSet.getInt("id");
    	
        
        //Double value = resultSet.getDouble("value");
        
        
        return new Task (TUUID, PUUID, name, complete, archived, idNum);
    }

}