package chapter4;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BreakFast extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String date=new SimpleDateFormat("hh:mm:ss dd-MM-yyyy").format(new Date());
		String foodType=request.getParameter("foodType");
		String taste=request.getParameter("taste");
		PrintWriter writer=response.getWriter();
		if (foodType==null || taste==null){
//			response.sendError(419, "invalid submission");
			writer.write("<li>At <strong>"+date+"</strong>: Whoa! Be more descriptive.</li>");
		}
		else {
			writer.write("<li>At <strong>"+date+"</strong>, I ate <strong>"+taste+" "+foodType+"</strong>.</li>");
		}
		writer.flush();
		writer.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
