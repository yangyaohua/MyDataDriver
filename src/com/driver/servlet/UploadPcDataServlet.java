package com.driver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.bean.Task;
import com.driver.dao.TaskDao;
import com.driver.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UploadPcDataServlet extends BaseServlet {

	@Override
	protected void dispatchTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String packageName = req.getParameter("packageName");
		String page = req.getParameter("page");
		ServletInputStream inputStream = req.getInputStream();
		PrintWriter writer = resp.getWriter();

		if (packageName == null || page == null || inputStream == null) {
			writer.write("false");
			writer.flush();
			writer.close();
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while((s = br.readLine()) != null){
			sb.append(s);
		}
		String data = sb.toString();
		System.out.println("data = " + data);
		Gson gson = new Gson();
		Map<String, Holder> holders = gson.fromJson(data, new TypeToken<Map<String, Holder>>(){}.getType());
		Map<String, Task> tasks = holders2Tasks(holders,packageName, page);
		boolean flag = TaskDao.updateTask(tasks,Constants.TABLE_PC);
		writer.write(String.valueOf(flag));
		writer.flush();
		writer.close();
	}
	
	private Map<String, Task> holders2Tasks(Map<String, Holder> holders, String packageName, String page) {
		Map<String, Task> map = new HashMap<String, Task>();
		for(String key : holders.keySet()){
			Holder holder = holders.get(key);
			map.put(key, new Task(packageName, page, holder.key, holder.value));
		}
		return map;
	}

	private class Holder{
		public String key;
		public String value;
	}
}


