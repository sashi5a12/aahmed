package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = -3186426528258980678L;

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("name"));
//		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		PrintWriter out = response.getWriter();
		out.print("    <This is ");
		out.print(this.getClass());
		out.println(", using the GET method>.<br/>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println(request.getParameter("name"));		
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method.<br/>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
	}

}
