package com.netpace.vzdn.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearchUtil;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocCollector;

import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.util.ConfigEnvProperties;
import com.netpace.vzdn.util.SearchInfo;
//import com.netpace.vzdn.utils.VzdnConstants;

public class SearchServlet extends HttpServlet {

	// Where index files save.
	private static String INDEX_DIR="";			//Where index files save. 
	private IndexReader indexReader = null;
	private Searcher indexSearcher = null;
	private final Analyzer analyzer = new StandardAnalyzer(SearchUtil.SMART_STOP_WORDS);
	private static long indexLastModified=0L;
	private static int NUMBER_OF_REC_PER_PAGE=10;
	
	@Override
	public void init() throws ServletException {
		try {
			ConfigEnvProperties props=ConfigEnvProperties.getInstance();
			INDEX_DIR=props.getProperty("dir.index");
			File indexFile=new File(INDEX_DIR);
			if (indexFile.isDirectory()){
				indexReader = IndexReader.open(INDEX_DIR);
				indexSearcher = new IndexSearcher(indexReader);
				indexLastModified =indexFile.lastModified();
			}
			else {
				System.err.println("<<<<<<<<<<<<<<Index directory doest not exist. Please create index before searching.>>>>>>>>>>>>>>>>");
			}			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		if (indexSearcher != null) {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (indexReader != null){
			try {
				indexReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		VzdnUsers loggedInUser=(VzdnUsers)session.getAttribute(VzdnConstants.LOGGED_IN_USER);
		Boolean isUserLoggedIn=loggedInUser==null?false:true;

		Query query = null;								// the Query created by the QueryParser
		ScoreDoc[] hits = null;							// the search results
		int startIndex = 0;								// the first index displayed on this page
		int endIndex = 9;								// the end index displayed on this page
		int pageNo= 0;
		String queryString = null;						// the query entered in the previous page
		String pageVal=null;
		
		queryString = request.getParameter("q");		// get the search criteria
		pageVal = request.getParameter("page");
		try {
			pageNo = Integer.parseInt(pageVal);  
		} catch (Exception e) { }
		
		startIndex=pageNo*NUMBER_OF_REC_PER_PAGE;
		endIndex=(pageNo*NUMBER_OF_REC_PER_PAGE)+(NUMBER_OF_REC_PER_PAGE-1);
		
		if (queryString == null || queryString.trim().length() == 0) {
			System.out.println("Required: Search Text");

			forwardRequest(request, response, queryString);
			return;
		}

		try {
			QueryParser qp = new QueryParser("contents", this.analyzer);
			query = qp.parse(queryString); // parse the
		} catch (ParseException e) { 
			System.out.println("Query Parsing error.");
			forwardRequest(request, response, queryString);
			return;
		}

		//In case re-index the index directory. Check the previous and current modified date and re-open the index.  
		File indexFile=new File(INDEX_DIR);
		if (indexFile.isDirectory() && (indexFile.lastModified() > indexLastModified)){			
			if (indexSearcher != null) {
					indexSearcher.close();
			}
			if (indexReader != null){
					indexReader.close();
			}			
			indexReader = IndexReader.open(INDEX_DIR);
			indexSearcher = new IndexSearcher(indexReader);
			indexLastModified =indexFile.lastModified();
		}
		if (!indexFile.isDirectory()){
			System.err.println("<<<<<<<<<<<<<<Index directory doest not exist. Please create index before searching.>>>>>>>>>>>>>>>>");
		}
		if (indexSearcher != null) {
			SearchUtil searchUtil = new SearchUtil();
			List<SearchInfo> results=new ArrayList<SearchInfo>();
			String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			
			TopDocCollector collector = new TopDocCollector(50 * NUMBER_OF_REC_PER_PAGE);
			indexSearcher.search(query, collector);
			hits = collector.topDocs().scoreDocs;
			
			if (endIndex > collector.getTotalHits()){
				endIndex=collector.getTotalHits();
			}
			
			for (int i=startIndex; i<endIndex; i++){
				Document doc = indexSearcher.doc(hits[i].doc);
				String path = doc.get("path");
				
				if (path != null) {
					if (path.indexOf("secure") != -1){
						if (isUserLoggedIn != null && isUserLoggedIn ){
							addSearchInfo(request, path, queryString, serverURL, isUserLoggedIn, searchUtil, results);		
						}
					}
					else if (path.indexOf("secure") == -1){
						addSearchInfo(request, path, queryString, serverURL, isUserLoggedIn, searchUtil, results);
					}					
				} 
				else {
					System.out.println((i + 1) + ". " + "No path for this document");
				}
			}
			
			if (results.size() > 0){
				request.setAttribute("results", results);
				request.setAttribute("links", collector.getTotalHits()/NUMBER_OF_REC_PER_PAGE);
			}			
		}
		
//		SortedSet<Term> terms = getTerms(query);
//		String stringToSearch = getStringToSearch(terms);
		forwardRequest(request, response, queryString);
	}

	private void forwardRequest(HttpServletRequest request, 
								HttpServletResponse response, 
								String queryString) throws ServletException, IOException {
		String forumQuery = getForumSearchQuery(queryString);		
		String blogQuery = getBlogSearchQuery(queryString);		
		
		request.setAttribute("forumQuery", forumQuery);
		request.setAttribute("blogQuery", blogQuery);
		request.setAttribute("queryString", queryString);
		
		RequestDispatcher rq=request.getRequestDispatcher("/pages/search/SearchResult.jsp");
		rq.forward(request, response);
	}
	
	private SortedSet<Term> getTerms(Query query){
		SortedSet<Term> terms = new TreeSet<Term>();
		query.extractTerms(terms);
		System.out.println("terms: "+terms);
		return terms;

	}
	
	private String getStringToSearch(SortedSet<Term> terms){
		StringBuffer bufVal = new StringBuffer();
		
		for(Term term : terms){
			String text = term.text();
			System.out.println("text"+text);
			bufVal.append(text + "+");
		}
		
		String stringToSearch = bufVal.substring(0, bufVal.length()-1);		
		System.out.println("String to search " + stringToSearch);
		return stringToSearch;
	}
	
	private String getForumSearchQuery(String stringToSearch){
		//Search Query for Forums
		return VzdnConstants.FORUM_PRE_SEARCH_STR  + stringToSearch + VzdnConstants.FORUM_POST_SEARCH_STR ;
	}
	
	private String getBlogSearchQuery(String stringToSearch){
		//Search Query for Blogs
		return VzdnConstants.BLOG_PRE_SEARCH_STR + stringToSearch;
	}

	private void addSearchInfo(HttpServletRequest request, 
							   String path, 
							   String queryString, 
							   String serverURL,
							   Boolean isUserLoggedIn,
							   SearchUtil searchUtil, 
							   List<SearchInfo> results) throws IOException {
		
		SearchInfo info=new SearchInfo();
//		String webPath=new StringBuilder(path).substring(path.indexOf(request.getContextPath()),path.length());
		String webPath="";
		if (path.indexOf("/webapps/ROOT") != -1){
			webPath=new StringBuilder(path).substring(path.indexOf("/webapps/ROOT")+"/webapps/ROOT".length(),path.length());
		}
		else {
			webPath=new StringBuilder(path).substring(path.indexOf("/webapps")+"/webapps".length(),path.length());
		}
		boolean addInList=true;
		info.setPath(serverURL+webPath);
		try {
			searchUtil.parse(new File(path));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String contents=searchUtil.getContents();
//		System.out.println(path);
//		System.out.println(contents);
		if (contents.contains("&lt;?xml") || contents.contains("&lt;!DOCTYPE") 
				|| contents.contains("&lt;html") || contents.contains("xml:lang") 
				|| contents.contains("&lt;title&gt;") || contents.contains("&lt;head&gt;")){
			return;
		}
		contents=SearchUtil.removeJspTags(contents);
		info.setTitle(StringUtils.replace(SearchUtil.getTitle(contents), "Â", ""));
		if (isUserLoggedIn == null || !isUserLoggedIn){
			contents=SearchUtil.removeSecureContents(contents);	
			addInList=false;
		}
		else{
			contents=SearchUtil.removeSecureTags(contents);
		}
		contents=SearchUtil.removeTitle(contents, info.getTitle());
		contents=SearchUtil.removeDirty(contents, "\"", "/>", 2);
		contents=SearchUtil.removeDirty(contents, "\"", "\">", 2);
		contents=StringUtils.remove(contents, "\" >");
		contents=SearchUtil.removeDirty(contents, "[", "]", 1);
		contents=StringUtils.replace(contents, "  ", "");	
		contents=StringUtils.replace(contents, "Â ", "");
		contents=StringUtils.replace(contents, "Â", "");		
		contents=StringUtils.replace(contents, "Back To Top", "");	
		contents=StringUtils.replace(contents, "Learn More", "");	
		contents=StringUtils.replace(contents, "View Here", "");	
		contents=StringUtils.replace(contents, "Submit App", "");		
		
		addInList=SearchUtil.allowToAddCotent(contents, queryString);
		if (contents!= null & contents.length()>0){		
			System.out.println("queryString= "+queryString);
			String displayStr=SearchUtil.createDisplayStr(contents, queryString);
			info.setSearchStr(SearchUtil.highlightSearchWord(displayStr, queryString));
		}
		if (addInList){			
			results.add(info);	
		}
		
//		System.out.println("======================================");
	}
}
