package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddRunnerServlet
 */
public class AddRunnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static List<Runner> data=new ArrayList<Runner>();   
   
	@Override
	public void init() throws ServletException {
		super.init();
	    
		data.add(new Runner("John","Smith","m","25:31"));
		data.add(new Runner("Frank","Jones","m","26:08"));
		data.add(new Runner("Bob","Hope","m","26:38"));
		data.add(new Runner("Ryan","Rice","m","28:24"));
		data.add(new Runner("Jacob","Zimmy","m","29:24"));
		data.add(new Runner("Mary","Brown","f","26:01"));
		data.add(new Runner("Jane","Smith","f","28:04"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String task=request.getParameter("action");
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		if (task!=null && "addRunner".equals(task)){
			String firstName=request.getParameter("txtFirstName");
			String lastName=request.getParameter("txtLastName");
			String gender=request.getParameter("ddlGender");
			String minutes=request.getParameter("txtMinutes");
			String seconds=request.getParameter("txtSeconds");
			Runner runner=new Runner(firstName,lastName, gender, minutes+":"+seconds);
			data.add(runner);
			json.put("status", "success");
			json.put("message", "Data Saved Sucessfully");
			System.out.println("Data: "+data);
		}
		else if(task!=null && "getRunners".equals(task)) {
			JSONArray dataInJsonArray=new JSONArray();
			dataInJsonArray.addAll(data);
			json.put("runners",dataInJsonArray);
		}
		System.out.println(json.toString());
		out.write(json.toString());
		out.flush();
		out.close();
	}

}
