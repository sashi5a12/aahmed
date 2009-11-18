package org.apache.lucene.search;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.*;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.utils.HTMLParser;

public class SearchUtil {
	public static final String SMART_STOP_WORDS[] = {"taglib", "page", 
		"<%","%>", "struts",".jsp", "$TITLE_START$","$TITLE_END$",
		"$SECURE_START$","$SECURE_END$", "isELIgnored", "pageEncoding",
		"UTF-8"};
	
	public static void main(String[] argv) throws IOException, InterruptedException {
		/*if ("-dir".equals(argv[0])) {
			String[] files = new File(argv[1]).list();
			java.util.Arrays.sort(files);
			for (int i = 0; i < files.length; i++) {
				System.err.println(files[i]);
				File file = new File(argv[1], files[i]);
				new Test().parse(file);
			}
		} 
		else{
			new Test().parse(new File(argv[0]));
		}*/

		
		String contents="move your ideas";
		System.out.println("Original Contents:\t"+contents);
		contents=SearchUtil.removeJspTags(contents);
		System.out.println("Removed JSP_TAGS:\t"+contents);
		System.out.println(SearchUtil.getTitle(contents));
		contents=SearchUtil.removeSecureContents(contents);
		System.out.println("Removed Secure Text:\t"+contents);
		contents=SearchUtil.removeTitle(contents,"");
		System.out.println("Removed Title:\t"+contents);
		System.out.println("Display String:\t"+SearchUtil.createDisplayStr(contents, "Samsung"));
		
		
//		new SearchUtil().parse(new File("E:/Dev/Others/VZDN-Site/WebRoot/jsps/GotoMarket.jsp"));
	}
	private String contents;

	public static String getTitle(String contents){
		if (contents.indexOf("$TITLE_START$") != -1 && contents.indexOf("$TITLE_END$") != -1 ){
			int start=contents.indexOf("$TITLE_START$");
			int end=contents.indexOf("$TITLE_END$");
			return contents.substring(start+"$TITLE_START$".length(),end);
		}
		else {
			return "Title";
		}
	}
	public static String removeTitle(String contents, String title){
		if (contents.indexOf("$TITLE_START$") != -1 && contents.indexOf("$TITLE_END$") != -1 ){
			StringBuilder sb=new StringBuilder(contents);
			int start=sb.indexOf("$TITLE_START$");
			int end=sb.indexOf("$TITLE_END$");
			String tmp=sb.replace(start, end+"$TITLE_END$".length(),"").toString();			
			return StringUtils.removeStart(tmp, title);
		}
		else {
			return contents;
		}
	}
	public static String removeJspTags(String contents){
		StringBuilder sb=new StringBuilder(contents);
		while(true){
			if (sb.indexOf("<%") != -1 && sb.indexOf("%>") != -1){
				int start=sb.indexOf("<%");
				int end=sb.indexOf("%>");
		 		sb.replace(start, end+2, "");
			}
			else {
				break;
			}
		}
		return sb.toString();
	}
	public static String removeDirty(String contents, String startStr, String endStr, int strLen){
		StringBuilder sb=new StringBuilder(contents);
		while(true){
			if (sb.indexOf(startStr) != -1 && sb.indexOf(endStr) != -1){
				int start=sb.indexOf(startStr);
				int end=sb.indexOf(endStr);
		 		sb.replace(start, end+strLen, "");
			}
			else {
				break;
			}
		}
		return sb.toString();
	}
	public static String removeSecureContents(String contents){
		StringBuilder sb=new StringBuilder(contents);
		while(true){
			if (sb.indexOf("$SECURE_START$") != -1 && sb.indexOf("$SECURE_END$") != -1){
				int start=sb.indexOf("$SECURE_START$");
				int end=sb.indexOf("$SECURE_END$");
		 		sb.replace(start, end+"$SECURE_END$".length(), "");
			}
			else {
				break;
			}
		}
		return sb.toString();
	}
	public static String removeSecureTags(String contents){
		contents=contents.replace("$SECURE_START$", "");	
		contents=contents.replace("$SECURE_END$", "");
		return contents;
	}
	public static boolean allowToAddCotent(String contents, String searchText){
		searchText=searchText.replace("\"", "").toLowerCase();
		contents=contents.toLowerCase();
		String[] searchTextArray=searchText.split(" ");
				
		for (int i=0; i<searchTextArray.length; i++){
			if (contents.indexOf(searchTextArray[i]) != -1 ){
				return true;
			}
		}
		return false;
	}
	public static String highlightSearchWord(String displayString, String searchQuery){
		String[] searchQueryArray=null;
		String[] displayStringArray=null;
		
		
		searchQuery=StringUtils.replace(searchQuery, "  ", " ");
		
		if (searchQuery.indexOf("\"") != -1 && searchQuery.lastIndexOf("\"") != -1  ) {
			searchQuery=searchQuery.replace("\"", "");
			
			if (searchQuery.indexOf(" ") != -1){
				String displayStringLowerCase = displayString.toLowerCase();
				String searchQueryLowerCase = searchQuery.toLowerCase();
				
				int searchQueryLocation=displayStringLowerCase.indexOf(searchQueryLowerCase);
				
				StringBuffer insertBlod=new StringBuffer(displayString);
				insertBlod.insert(searchQueryLocation+searchQuery.length(), "</strong>");
				insertBlod.insert(searchQueryLocation, "<strong>");
				
				displayString=insertBlod.toString();
				return displayString;
			}
		}

		searchQueryArray=searchQuery.split(" ");
		displayStringArray=displayString.split(" ");
		for (int i=0; i<displayStringArray.length; i++){
			for (int j=0; j<searchQueryArray.length; j++){
				if (displayStringArray[i].equalsIgnoreCase(searchQueryArray[j])){
					displayStringArray[i]="<strong>"+displayStringArray[i]+"</strong>";
				}
			}
		}
		displayString=StringUtils.join(displayStringArray, " ");
		
		return displayString;
	}
	public static String createDisplayStr(String contents, String searchText){
		if (contents.indexOf("Dashboard Dev Center - Technical Resources") != -1){
			String _break="";
			System.out.println(_break);
		}
		
		if (contents != null && contents.length() <=150){
			return contents;
		}
		
		//Remove some special characters
		contents=StringUtils.remove(contents, "\"");
		contents=StringUtils.remove(contents, "\'");
		contents=StringUtils.remove(contents, "(");
		contents=StringUtils.remove(contents, ")");
		contents=StringUtils.remove(contents, "{");
		contents=StringUtils.remove(contents, "}");
		contents=StringUtils.remove(contents, "[");
		contents=StringUtils.remove(contents, "]");
		
		searchText=StringUtils.replace(searchText, "  ", " ");
		
		String originalContents=contents;
		contents=contents.toLowerCase();		//for case-insensitive searching
		String displayStr="";
		searchText=searchText.toLowerCase();
		
		boolean andCondition=false;
		
		//user searched using query: "apply your ideas"
		if (searchText.indexOf("\"") != -1 && searchText.lastIndexOf("\"") != -1  ) {
			andCondition=true;
		}
		
		searchText=searchText.replace("\"", "");
		
		String[] searchTextArray=null;
		
		if (contents != null && contents.trim().length() >0 && andCondition && contents.indexOf(searchText) != -1){
			searchTextArray=new String[]{searchText};
		}
		else {
			//user searched using query: apply your ideas
			searchTextArray=searchText.split(" ");
		}
		
				
		for (int i=0; i<searchTextArray.length; i++){
			if (contents.indexOf(searchTextArray[i]+" ") != -1 ){
				displayStr=displayStr(originalContents, contents,searchTextArray[i]+" ");
				if (displayStr.length() > 0){
					return displayStr;
				}
			}
		}
		if (displayStr.length()==0){
			if (originalContents.length()>100){
				displayStr=originalContents.substring(0,99);
			}
			else {
				displayStr=originalContents;
			}
			displayStr=displayStr.substring(0, displayStr.trim().lastIndexOf(" ")+1);
		}
		return displayStr;
	}
	private static String displayStr(String originalContents, String contents,  String searchStr) {
		
		if (originalContents.indexOf("Dashboard Dev Center - Technical Resources") != -1){
			String _break="";
			System.out.println(_break);
		}
		
		//split in two arrays based on search string
		String[] leftRightSentences=contents.split(searchStr);
		if (leftRightSentences.length > 2){
			String tmp="";
			for (int i=1; i<leftRightSentences.length; i++){
				tmp += leftRightSentences[i];
			}
			leftRightSentences[1]=tmp;
		}
		
		int leftPosition=0;
		int rightPosition=0;
		String startStr="";
		String endStr="";
		
		
		if (leftRightSentences[0].length() > 100){
			if (leftRightSentences[0].lastIndexOf(".") != -1){
				leftPosition=leftRightSentences[0].lastIndexOf(".");
			}
			else {
				leftPosition=leftRightSentences[0].lastIndexOf(" ");
			}
			startStr=originalContents.substring(leftPosition+1, leftRightSentences[0].length());
		}
		else {
			startStr=originalContents.substring(0, leftRightSentences[0].length());
		}
		
		if (leftRightSentences[1].length() > 100){
			if (leftRightSentences[1].indexOf(".") != -1){
				rightPosition=leftRightSentences[1].indexOf(".");
			}
			else {
				rightPosition=leftRightSentences[1].indexOf(" ");
			}
			endStr=originalContents.substring(leftRightSentences[0].length(), leftRightSentences[0].length()+searchStr.length()+rightPosition);		
		}
		else {
			endStr=originalContents.substring(leftRightSentences[0].length(), leftRightSentences[0].length()+leftRightSentences[1].length());				
		}
		
		String finalStr=startStr+endStr;
		int finalStrLocation=originalContents.indexOf(finalStr);
		if (originalContents.indexOf(".", finalStrLocation) != -1){
			int dotPositionAfterFinalStrLocation=originalContents.indexOf(".", finalStrLocation);
			finalStr=originalContents.substring(finalStrLocation, dotPositionAfterFinalStrLocation+1);
		}
		finalStr=finalStr.substring(0, finalStr.trim().lastIndexOf(" ")+1);
		
		return finalStr;

	}
	
	
	public String getContents() {
		return contents;
	}

	public void parse(File file) throws IOException, InterruptedException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			HTMLParser parser = new HTMLParser(fis);
			
//			this.title=Entities.encode(parser.getTitle());
			StringBuilder sb=new StringBuilder();
			LineNumberReader reader = new LineNumberReader(parser.getReader());
			for (String l = reader.readLine(); l != null; l = reader.readLine()){
//				System.out.println(l);
				sb.append(l+ " ");
			}
			this.contents=sb.toString();
//			System.out.println("Parsed Contents: "+contents);
		} 
		finally {
			if (fis != null)
				fis.close();
		}
	}
}
