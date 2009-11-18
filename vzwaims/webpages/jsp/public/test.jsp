<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="net.sf.hibernate.Session"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.netpace.aims.model.DBHelper"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="net.sf.hibernate.HibernateException"%>

<%!
	public static String getSysDate() throws Exception {
		Session session = null;
		Statement stmt = null;
		ResultSet rs = null;
		String date = "";
		Connection conn=null;
		try {
			session = DBHelper.getInstance().getSession();
			conn = session.connection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select sysdate from dual");

			if (rs.next()) {
				date = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		return date;
	}
%>

<%
	String date = getSysDate();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Test Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>

	<body>
		<H1><%=date%></H1>
	</body>
</html>
