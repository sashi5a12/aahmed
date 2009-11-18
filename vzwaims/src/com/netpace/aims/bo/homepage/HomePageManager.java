package com.netpace.aims.bo.homepage;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.homepage.AimsTopTenGames;


public class HomePageManager {
	static Logger log = Logger.getLogger(HomePageManager.class.getName());
	
	public static List getTopTenGames()throws Exception{
		log.debug("HomePageManager.getTopTenGames start:");
		List list=new ArrayList();
		Session session=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			StringBuffer buf=new StringBuffer();
			buf.append("SELECT *  FROM ")
				.append("(SELECT   * FROM aims_top_ten_games g WHERE upper(g.visible) = 'Y' ORDER BY g.sort_order)")
				.append("WHERE ROWNUM <= 10");
			session=DBHelper.getInstance().getSession();
			
			Connection con=session.connection();
			stmt=con.prepareStatement(buf.toString());
			rs=stmt.executeQuery();
			while(rs.next()){
				AimsTopTenGames game=new AimsTopTenGames();
				game.setId(new Long(rs.getLong("ID")));
				game.setName(rs.getString("NAME"));
				game.setBaseUrl(rs.getString("BASE_URL"));
				game.setUrl(rs.getString("URL"));
				game.setSortOrder(new Long(rs.getLong("SORT_ORDER")));
				game.setVisible(rs.getString("VISIBLE"));
				list.add(game);
			}			
		}
		catch (SQLException sqle){
			log.error(sqle,sqle);
			throw sqle;
		}
		catch (HibernateException e){
			log.error(e,e);
			throw e;
		}
		finally {
			if(session!=null){
				session.close();
			}
			if(rs != null){
				rs.close();
			}			
			if(stmt != null){
				stmt.close();
			}
		}
		log.debug("HomePageManager.getTopTenGames End:");
		return list;
	}
	
	public static List getConferences() throws HibernateException{
		log.debug("HomePageManager.getConferences Start:");
		List list=new ArrayList();
		Session session=null;
		try {
			session=DBHelper.getInstance().getSession();
			Query query = session.createQuery("from AimsConferences c where upper(c.visible)='Y'");
			list=query.list();
		}
		catch (HibernateException e){
			log.error(e,e);
			throw e;
		}
		finally{
			if(session != null){
				session.close();
			}
		}

		log.debug("HomePageManager.getConferences End:");
		return list;
	}
	public static void main(String args[])throws Exception{
		DBHelper dbHelper=DBHelper.getInstance();
		dbHelper.sessionFactory=new Configuration().configure().buildSessionFactory();	
		List topTenGames=HomePageManager.getTopTenGames();
		System.out.println(topTenGames);
		List conferences=HomePageManager.getConferences();
		System.out.println(conferences);
	}
}
