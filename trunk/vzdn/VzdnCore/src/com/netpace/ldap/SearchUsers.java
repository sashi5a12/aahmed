package com.netpace.ldap;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.ldap.bean.LdapUser;


public class SearchUsers {

	/**
	 * @param args
	 */
	
	private Hashtable<String, String> env = null ;
	private SimpleDateFormat sdf = null ;
	private String searchAttributes[] = null ;
	private ResourceBundle bundle = null ;
	private String ldapProtocol = null ;
	private String ldapHost = null ;
	private int ldapPort ;
	private String ldapUserName = null ;
	private String ldapPassword = null ;
	private String ldapSearchBase = null ;
	
	private String inputDateFormat = null ;
	private String ldapDateFormat = null ;
	private String ldapInputDateFormat = null ;
	private String securityAuthentication = null ;
	private String defaultFromTime = null;
	private String defaultToTime = null;
	private String csvDateFormat = null;
	private String filter = null;
	private String commaReplacement ;
	private static final String COMMA = "," ;
	private String ignoreEmails[] = null ;
	private String conditionPrefix = null;
	private String conditionPostfix = null;
	
	private static Logger log = Logger.getLogger(SearchUsers.class);
	
	public SearchUsers()
	{
		log.debug( "Start searching users in ldap." );
		sdf = new SimpleDateFormat();
		bundle = ResourceBundle.getBundle( "ldapSearchUser" );
		
		searchAttributes = bundle.getString( "attributes.searchUser.ldap" ).split( "," ) ;
		ldapProtocol = bundle.getString( "property.searchUser.ldap.protocol" );
		ldapHost = bundle.getString( "property.searchUser.ldap.host" );
		ldapPort = Integer.parseInt( bundle.getString( "property.searchUser.ldap.port" ) );
		ldapUserName = bundle.getString( "property.searchUser.ldap.username" );
		ldapPassword = bundle.getString( "property.searchUser.ldap.password" );
		ldapSearchBase = bundle.getString( "property.searchUser.ldap.searchBase" );
		
		inputDateFormat = bundle.getString( "input.searchUser.date.format" );
		ldapDateFormat = bundle.getString( "ldap.searchUser.date.format" );
		ldapInputDateFormat = bundle.getString( "ldap.input.searchUser.date.format" );		
		securityAuthentication = bundle.getString( "default.value.searchUser.security.authentication" );
		defaultFromTime = bundle.getString( "default.value.searchUser.from.time" );
		defaultToTime = bundle.getString( "default.value.searchUser.to.time" );
		csvDateFormat = bundle.getString( "csv.searchUser.date.format" );
		commaReplacement = bundle.getString( "result.comma.replace" );
		
		filter = bundle.getString( "ldap.searchUser.query" );
		conditionPrefix = bundle.getString( "ldap.searchUser.query.condition.prefix" );
		conditionPostfix = bundle.getString( "ldap.searchUser.query.condition.postfix" );
		
		bundle = ResourceBundle.getBundle( "ApplicationResources" );
		ignoreEmails = bundle.getString( "hidden.users" ).split( "," );
	}
	
	public ArrayList<LdapUser> searchUsers( String from, String to , String sort) throws ParseException, NamingException, IOException
	{
		return searchUsers( ldapHost , ldapPort, ldapUserName, ldapPassword, ldapSearchBase , from , to , sort) ;
		
	}
	
	public ArrayList<LdapUser> searchUsers( String from, String to ) throws ParseException, NamingException, IOException
	{
		return searchUsers( from , to  , "") ;
		
	}
	
	public ArrayList<LdapUser> searchUsers( String host, int port, String userName, String password, String searchBase, String from, String to , String sortCol) throws ParseException, NamingException, IOException
	{
		ArrayList<LdapUser> arr = null;
		LdapContext con = null ;
		NamingEnumeration<SearchResult> result = null ;
		try {
			init(host, port, userName, password);
			
			arr = new ArrayList<LdapUser>();
			
			Date fromDate = getDate( inputDateFormat , from ) ;
			Date toDate = getDate( inputDateFormat , to ) ;
			

			con = new InitialLdapContext(env, null);
			String filter = getFilter( fromDate , toDate );
			boolean critical = Control.CRITICAL ;
			SortControl sortCtrl = new SortControl(new String[]{sortCol} , critical) ;
			SearchControls searchCtrl = new SearchControls( );
			searchCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			//Timestamp, Email Address, First Name, Last Name, Title, DOB, Country, Terms & Condition agreement
			searchCtrl.setReturningAttributes( searchAttributes );

			Control[] ctrls = new Control[]{sortCtrl};

			log.info("searchBase = "+searchBase) ;
			log.info("Search Filter is as follows:\n"+filter) ;
			
			con.setRequestControls(ctrls) ;
			result = con.search( searchBase , filter, searchCtrl);

			while ( result != null && result.hasMoreElements() )
			{
				SearchResult sr = result.nextElement() ;

				if ( sr.getAttributes() != null )
				{
					LdapUser user = new LdapUser();
					if ( sr.getAttributes().get( searchAttributes[0] ) != null )
						user.setCreatedTimeStamp( ( (String) sr.getAttributes().get( searchAttributes[0] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[1] ) != null )
						user.setEmail( ( (String) sr.getAttributes().get( searchAttributes[1] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[2] ) != null )
						user.setTitle( ( (String) sr.getAttributes().get( searchAttributes[2] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[3] ) != null )
						user.setFirstName( ( (String) sr.getAttributes().get( searchAttributes[3] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[4] ) != null )
						user.setLastName( ( (String) sr.getAttributes().get( searchAttributes[4] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[5] ) != null )
						user.setDob( ( (String) sr.getAttributes().get( searchAttributes[5] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[6] ) != null )
						user.setCountry( ( (String) sr.getAttributes().get( searchAttributes[6] ).get() ).replaceAll(COMMA, commaReplacement)  );
					if ( sr.getAttributes().get( searchAttributes[7] ) != null )
						user.setAgreement( ( (String) sr.getAttributes().get( searchAttributes[7] ).get() ).replaceAll(COMMA, commaReplacement)  );

					if(user.getCreatedTimeStamp()!=null && StringUtils.isNotBlank(user.getCreatedTimeStamp())){
						user.setCreatedTimeStamp( user.getCreatedTimeStamp().substring( 0 , user.getCreatedTimeStamp().length() - 1 ) );
						Date date = getDate( ldapDateFormat , user.getCreatedTimeStamp() ) ;
						user.setCreatedTimeStamp( getDate( csvDateFormat , date) ) ;
					}
					

					arr.add( user );
				}
			}
			log.debug( "Developers list size = " + arr.size() );
		}catch (ParseException e) {
			log.error("Error in searching users from LDAP " , e) ;
			throw e ;
		} catch (NamingException e) {
			log.error("Error in searching users from LDAP " , e) ;
			throw e ;
		} catch (IOException e) {
			log.error("Error in searching users from LDAP " , e) ;
			throw e ;
		}catch (Throwable e) {
			log.error("Error in searching users from LDAP " , e) ;
		}
		finally {
			if(result != null){
				result.close();
			}
			if(con != null){
				con.close();
			}
		}
        
		return arr;
	}
	
	private void init(String host, int port, String userName, String password) throws NamingException
	{
		String providerUrl = ldapProtocol + "://" + host + ":" + port ;
		
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        env.put(Context.PROVIDER_URL, providerUrl );
        env.put(Context.SECURITY_AUTHENTICATION, securityAuthentication );
        env.put(Context.SECURITY_PRINCIPAL, userName );
        env.put(Context.SECURITY_CREDENTIALS, password );
        
        log.info("Context.INITIAL_CONTEXT_FACTORY = "+env.get(Context.INITIAL_CONTEXT_FACTORY)) ;
        log.info("Context.PROVIDER_URL = "+env.get(Context.PROVIDER_URL)) ;
        log.info("Context.SECURITY_AUTHENTICATION = "+env.get(Context.SECURITY_AUTHENTICATION)) ;
        log.info("Context.SECURITY_PRINCIPAL = "+env.get(Context.SECURITY_PRINCIPAL)) ;
	}
	
	private Date getDate(String pattern, String date) throws ParseException
	{
		sdf.applyLocalizedPattern(pattern);
		return sdf.parse( date );
	}
	
	private String getDate(String pattern, Date date)
	{
		sdf.applyLocalizedPattern(pattern);
		return sdf.format( date );
	}
	
	 private String getFilter(Date fromDate, Date toDate )
	 {
		 
		 StringBuffer sb = new StringBuffer();
		 for ( String mail : ignoreEmails )
		 {
			 sb.append( conditionPrefix ).append( mail ).append( conditionPostfix );
		 }
		 
		 String from = getDate( ldapInputDateFormat , fromDate ) + defaultFromTime ;
		 String to = getDate( ldapInputDateFormat , toDate ) + defaultToTime ;
		 
		 Object arguments[] = new Object[] { from , to , sb.toString() } ;
		 return MessageFormat.format( filter, arguments );
	 }

}
