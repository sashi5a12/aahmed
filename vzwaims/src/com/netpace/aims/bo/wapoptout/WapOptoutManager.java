package com.netpace.aims.bo.wapoptout;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.wapoptout.AimsWapOptout;
import com.netpace.aims.model.wapoptout.AimsWapOptoutFileLog;
import com.netpace.aims.model.wapoptout.AimsWapOptoutWhitelistUrl;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.WapoptoutUtil;

public class WapOptoutManager {
	private static final Logger log = Logger.getLogger(WapOptoutManager.class
			.getName());

	private static final String Match_Start = "start";
	private static final String Match_End = "end";
	private static final String Match_Domain = "domain";
	private static final String Match_Anywhere = "anywhere";
	private static final String Match_Anywhere_Subdomain = "anywhereInSubdomain";
	private static final String Match_Anywhere_Domain = "anywhereInDomain";
	private static final String Match_Mobi = "mobi";

	final static String starting_pattern = "^(m|wap|avantgo|wireless|wapp|mobile|iphone|pda)\\.\\*";
	final static String ending_pattern = "^\\*\\.(wml|xhtml|mobi)";
	final static String domain_pattern = "^<domain>/((.)*/)?(m/\\*|mobile/\\*|gmm/|portable)";
	final static String anywhere_subdomain_pattern = "^\\*/(m|mobile|portable)/\\*$";
	final static String anywhere_domain_pattern = "^\\*.(m|wap|avantgo|wireless|wapp|mobile|iphone|pda)\\.\\*$";
	final static String mobi_pattern = "^\\*\\.mobi(/\\*)?";

	public static void save(AimsWapOptout wapOptout) throws HibernateException,
			Exception {
		Session session = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		Transaction tx = null;
		Long submittalNumber = new Long("0");
		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
			Connection con = session.connection();
			prep = con
					.prepareStatement("SELECT SEQ_WAP_OPTOUT_SUBMITTAL_N0.nextVal as sub_No FROM Dual");
			rs = prep.executeQuery();
			while (rs.next()) {
				submittalNumber = new Long(rs.getLong("sub_No"));
			}
			wapOptout.setSubmittalNumber(submittalNumber);
			session.save(wapOptout);
			tx.commit();
		} catch (SQLException sqle) {
			tx.rollback();
			log.error(sqle, sqle);
			throw sqle;
		} catch (HibernateException e) {
			tx.rollback();
			log.error(e, e);
			throw e;
		} finally {
			if (session != null)
				session.close();
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
		}
	}

	/**
	 * Saved the list of whitelist url in the database 
	 * @author nauman
	 * 
	 * @param url
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 */
	public static void saveWhitelist(List whiteListUrls)
			throws HibernateException, Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
			session.delete("from AimsWapOptoutWhitelistUrl");

			for (Iterator iterator = whiteListUrls.iterator(); iterator
					.hasNext();) {
				AimsWapOptoutWhitelistUrl url = (AimsWapOptoutWhitelistUrl) iterator
						.next();
				session.save(url);
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			log.error(e, e);
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Checks if the provided url exists in the whitelist 
	 * @author nauman
	 * 
	 * @param url
	 * @return if contains in white list then return true else false
	 * @throws SQLException
	 * @throws HibernateException
	 */
	public static boolean doesUrlExistsInWhitelist(String url)
			throws SQLException, HibernateException {

		Session session = null;

		try {
			session = DBHelper.getInstance().getSession();
			Query query = session.createQuery("from AimsWapOptoutWhitelistUrl");

			List whitelist;
			whitelist = query.list();

			List patterns = getWhiteListPatterns(whitelist);

			for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
				String pattern = (String) iterator.next();

				if (((Pattern.compile(pattern)).matcher(url)).matches()) {
					log.info("URL  :" + url
							+ ":  EXISTS IN WHITE LIST matched by pattern :"
							+ pattern + ":");
					return true;
				}

			}
		} catch (HibernateException e) {
			log.error(e, e);
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		return false;
	}

	/**
	 * Returns the regular expression of the whitel list url existing in the table
	 * @param whitelist
	 * @return
	 */
	private static List getWhiteListPatterns(List whitelist) {
		List list = new ArrayList();

		for (Iterator iterator = whitelist.iterator(); iterator.hasNext();) {
			AimsWapOptoutWhitelistUrl url = (AimsWapOptoutWhitelistUrl) iterator
					.next();

			String string = url.getUrl().trim();
			if (!StringFuncs.isNullOrEmpty(string)) {
				list.add(getUrlPattern(string));
			}
		}
		return list;
	}

	/**
	 * Returnst the map containg domain name as key and its related patterns 
	 * @param whitelistUrls
	 * @return
	 */
	private static Map getWhitelistPatternsMap(List whitelistUrls) {
		Map map = new HashMap();

		for (Iterator iterator = whitelistUrls.iterator(); iterator.hasNext();) {
			AimsWapOptoutWhitelistUrl url = (AimsWapOptoutWhitelistUrl) iterator
					.next();

			String[] urlPatterns;
			String key;

			String whitelistPattern = url.getUrl().trim();
			if (((Pattern.compile(starting_pattern)).matcher(whitelistPattern))
					.matches()) {
				key = Match_Start;
				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, ".*", "\\.(.)*"), key);
			} else if (((Pattern.compile(ending_pattern))
					.matcher(whitelistPattern)).matches()) {
				key = Match_End;
				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, "*.", "(.)*\\."), key);
			} else if (((Pattern.compile(domain_pattern))
					.matcher(whitelistPattern)).matches()) {
				key = Match_Domain;
				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, "*", "(.)*"), key);
			} else if (((Pattern.compile(anywhere_subdomain_pattern))
					.matcher(whitelistPattern)).matches()) {
				key = Match_Anywhere_Subdomain;
				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, "*", "(.)*"), key);
			} else if (((Pattern.compile(mobi_pattern))
					.matcher(whitelistPattern)).matches()) {
				key = Match_Mobi;
				whitelistPattern = StringUtils.replace(whitelistPattern, "*.",
						"(.)*\\.");
				if (whitelistPattern.endsWith("*")) {
					whitelistPattern = StringUtils.replace(whitelistPattern,
							"*", "/(.)*");
				}

				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, "*", "(.)*"), key);
			} else if (((Pattern.compile(anywhere_domain_pattern))
					.matcher(whitelistPattern)).matches()) {
				key = Match_Anywhere_Domain;
				//urlPatterns=addValueToKey(map, StringUtils.replace(whitelistPattern, "*.", "(.)*\\."), key);

				whitelistPattern = StringUtils.replace(whitelistPattern, ".*",
						"/(.)*");

				urlPatterns = addValueToKey(map, StringUtils.replace(
						whitelistPattern, "*.", "(.)*\\."), key);
			} else {
				urlPatterns = getUrlPatterns(whitelistPattern);
				key = WapoptoutUtil.extractDomain(StringUtils.replace(
						whitelistPattern, "*.", ""));
				urlPatterns = addValueToKey(map, urlPatterns, key);
			}
			map.put(key, urlPatterns);
		}
		return map;
	}

	/**
	 * Add value to the map against provided key, if there is already 
	 * a value against that key, it appends the new key to the existing one
	 * @param map
	 * @param urlPatterns
	 * @param key
	 * @return
	 */
	private static String[] addValueToKey(Map map, String[] urlPatterns,
			String key) {
		String[] patterns;
		List list = new ArrayList();

		if (map.get(key) != null) {
			patterns = (String[]) map.get(key);
			for (int i = 0; i < patterns.length; i++)
				list.add(patterns[i]);
		}
		for (int i = 0; i < urlPatterns.length; i++)
			list.add(urlPatterns[i]);

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * Add value to the map against provided key, if there is already 
	 * a value against that key, it appends the new key to the existing one
	 * @param map
	 * @param urlPatterns
	 * @param key
	 * @return
	 */
	private static String[] addValueToKey(Map map, String urlPattern, String key) {
		String[] temp;
		List list = new ArrayList();

		if (map.get(key) != null) {
			temp = (String[]) map.get(key);

			for (int i = 0; i < temp.length; i++)
				list.add(temp[i]);
			list.add(urlPattern);
		} else {
			list.add(urlPattern);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static void main(String[] args) {
		String[] pattern = getUrlPatterns("");
		System.out.println("hloo");
		for (int i = 0; i < pattern.length; i++) {
			System.out.println(" patterns " + pattern[i]);
		}
	}

	/**
	 * Extracts patterns from the provided url
	 * @param url
	 * @return
	 */
	private static String[] getUrlPatterns(String whitelistPattern) {
		List patterns = new ArrayList();

		if (whitelistPattern.startsWith("*.")
				&& !whitelistPattern.endsWith("*")) {
			patterns.add(whitelistPattern.substring(whitelistPattern
					.indexOf("*") + 1));
			patterns.add(replaceFirst(whitelistPattern));

		} else if (!whitelistPattern.startsWith("*")
				&& whitelistPattern.endsWith("*")) {

			patterns.add(whitelistPattern.substring(0, whitelistPattern
					.lastIndexOf("*")));
			String temp = replaceEnd(whitelistPattern);
			patterns.add(temp);

		} else if (whitelistPattern.startsWith("*.")
				&& whitelistPattern.endsWith("*")) {

			String maindomain = whitelistPattern.substring(whitelistPattern
					.indexOf("*.")
					+ "*.".length(), whitelistPattern.lastIndexOf("*"));

			patterns.add(maindomain);

			String temp = whitelistPattern;
			temp = replaceEnd(temp);
			patterns.add(temp.substring(temp.indexOf("*.") + "*.".length()));
			temp = replaceFirst(temp);
			patterns.add(temp.substring(0, temp.indexOf("/(.)*")));

			patterns.add(temp);

		} else if (!whitelistPattern.startsWith("*")
				&& !whitelistPattern.endsWith("*")) {
			patterns.add(whitelistPattern);
		}
		return (String[]) patterns.toArray(new String[patterns.size()]);
	}

	/**
	 * Extracts patterns from the provided url
	 * @param url
	 * @return
	 */
	private static String getUrlPattern(String whitelistPattern) {
		whitelistPattern = StringUtils.replace(whitelistPattern, ".", "\\.");

		return StringUtils.replace(whitelistPattern, "*", "(.)*");
	}

	/**
	 * Replaces * of the provided string into regular expression wiled card
	 * in the END ONLY
	 * @param string
	 * @return replaced string
	 */
	private static String replaceEnd(String string) {
		String temp = (new StringBuffer(string)).reverse().toString();
		temp = temp.replaceFirst("\\*", (new StringBuffer("/(.)*")).reverse()
				.toString());
		return (new StringBuffer(temp).reverse().toString());
	}

	/**
	 * Replaces * of the provided string into regular expression wild card
	 * in the START ONLY
	 * @param string
	 * @return replaced string
	 */
	private static String replaceFirst(String string) {
		return string.replaceFirst("\\*\\.", "(.)*\\\\.");
	}

	
	/**
	 * Save the data from the imported whitelist into the table
	 */
	public static void saveWhitelistData(String data) throws IOException {
		Session session = null;
		Transaction tx = null;

		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			AimsWapOptoutFileLog file = new AimsWapOptoutFileLog();

			file.setCreatedDate(new Date());
			file.setWhitelistFile(Hibernate.createClob(" "));
			session.saveOrUpdate(file);

			session.flush();
			session.refresh(file, LockMode.UPGRADE);

			long bytesWrote  = LobUtils.writeToOraClob(
					(CLOB) file.getWhitelistFile(), new ByteArrayInputStream(data.getBytes())  );

			session.flush();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("Exception in logException");
			try {
				if (tx != null)
					tx.rollback();
			} catch (Exception ignore) {
			}
			ex.printStackTrace();
		}

		finally {
			try {
				session.close();
			} catch (Exception ignore) {
			}
		}

	}
}