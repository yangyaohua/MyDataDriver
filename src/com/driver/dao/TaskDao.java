package com.driver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.driver.bean.Task;
import com.driver.util.Constants;
import com.driver.util.DBUtil;
import com.driver.util.FileHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TaskDao {
	
	public static void main(String[] args) {
		
		/*String data = FileHelper.txt2String(Constants.DATA_RESTORE_FILE_PATH);
		
		System.out.println("data = " + data);
		Gson gson = new Gson();
		Map<String, Task> tasks = gson.fromJson(data, new TypeToken<Map<String, Task>>(){}.getType());
		TaskDao.updateTask(tasks);*/
		List<Task> queryTask = queryTask(Constants.TABLE_PC);
		System.out.println(queryTask);
		
		/*Map<String, Task> map = new HashMap<String, Task>();
		map.put("0", new Task("MyCtrip", "login.html", "et_user", "100000"));
		updateTask(map, Constants.TABLE_PC);*/
	}
	
	public static List<Task> queryTask(String tableName){
		List<Task> tasks = new ArrayList<Task>();
		Connection connection = DBUtil.getConnection();
		String sql = "select * from " + tableName;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String packageName = rs.getString(1);
				String index = rs.getString(2);
				String key = rs.getString(3);
				String value = rs.getString(4);
				Task task = new Task(packageName,index, key, value);
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection(rs, ps, connection);
		}
		return tasks;
	}

	public static boolean updateTask(Map<String, Task> map, String tableName){
		boolean flag = false;
		Connection connection = DBUtil.getConnection();
		String sql = "delete from " + tableName;
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			connection.setAutoCommit(false);
			sql = "insert into " + tableName + " values(?,?,?,?);";
			ps = connection.prepareStatement(sql);
			for(String key : map.keySet()){
				Task task = map.get(key);
				ps.setString(1, task.getPackageName());
				ps.setString(2, task.getIndex());
				ps.setString(3, task.getKey());
				ps.setString(4, task.getValue());
				ps.addBatch();
			}
			int[] count = ps.executeBatch();
			
			connection.commit();
			for(int i : count){
				connection.rollback();
			}
			if (count.length >= 1) {
				flag = true;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			closeConnection(null, ps, connection);
		}
		return flag;
	}
	
	private static void closeConnection(ResultSet rs, PreparedStatement ps,
			Connection connection) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				DBUtil.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
