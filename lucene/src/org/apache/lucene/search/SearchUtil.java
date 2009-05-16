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

import org.apache.lucene.search.utils.Entities;
import org.apache.lucene.search.utils.HTMLParser;

public class SearchUtil {
	public static final String SMART_STOP_WORDS[] = { "a", "a's", "able",
		"about", "above", "according", "accordingly", "across", "actually",
		"after", "afterwards", "again", "against", "ain't", "all", "allow",
		"allows", "almost", "alone", "along", "already", "also",
		"although", "always", "am", "among", "amongst", "an", "and",
		"another", "any", "anybody", "anyhow", "anyone", "anything",
		"anyway", "anyways", "anywhere", "apart", "appear", "appreciate",
		"appropriate", "are", "aren't", "around", "as", "aside", "ask",
		"asking", "associated", "at", "available", "away", "awfully", "b",
		"be", "became", "because", "become", "becomes", "becoming", "been",
		"before", "beforehand", "behind", "being", "believe", "below",
		"beside", "besides", "best", "better", "between", "beyond", "both",
		"brief", "but", "by", "c", "c'mon", "c's", "came", "can", "can't",
		"cannot", "cant", "cause", "causes", "certain", "certainly",
		"changes", "clearly", "co", "com", "come", "comes", "concerning",
		"consequently", "consider", "considering", "contain", "containing",
		"contains", "corresponding", "could", "couldn't", "course",
		"currently", "d", "definitely", "described", "despite", "did",
		"didn't", "different", "do", "does", "doesn't", "doing", "don't",
		"done", "down", "downwards", "during", "e", "each", "edu", "eg",
		"eight", "either", "else", "elsewhere", "enough", "entirely",
		"especially", "et", "etc", "even", "ever", "every", "everybody",
		"everyone", "everything", "everywhere", "ex", "exactly", "example",
		"except", "f", "far", "few", "fifth", "first", "five", "followed",
		"following", "follows", "for", "former", "formerly", "forth",
		"four", "from", "further", "furthermore", "g", "get", "gets",
		"getting", "given", "gives", "go", "goes", "going", "gone", "got",
		"gotten", "greetings", "h", "had", "hadn't", "happens", "hardly",
		"has", "hasn't", "have", "haven't", "having", "he", "he's",
		"hello", "help", "hence", "her", "here", "here's", "hereafter",
		"hereby", "herein", "hereupon", "hers", "herself", "hi", "him",
		"himself", "his", "hither", "hopefully", "how", "howbeit",
		"however", "i", "i'd", "i'll", "i'm", "i've", "ie", "if",
		"ignored", "immediate", "in", "inasmuch", "inc", "indeed",
		"indicate", "indicated", "indicates", "inner", "insofar",
		"instead", "into", "inward", "is", "isn't", "it", "it'd", "it'll",
		"it's", "its", "itself", "j", "just", "k", "keep", "keeps", "kept",
		"know", "knows", "known", "l", "last", "lately", "later", "latter",
		"latterly", "least", "less", "lest", "let", "let's", "like",
		"liked", "likely", "little", "look", "looking", "looks", "ltd",
		"m", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile",
		"merely", "might", "more", "moreover", "most", "mostly", "much",
		"must", "my", "myself", "n", "name", "namely", "nd", "near",
		"nearly", "necessary", "need", "needs", "neither", "never",
		"nevertheless", "new", "next", "nine", "no", "nobody", "non",
		"none", "noone", "nor", "normally", "not", "nothing", "novel",
		"now", "nowhere", "o", "obviously", "of", "off", "often", "oh",
		"ok", "okay", "old", "on", "once", "one", "ones", "only", "onto",
		"or", "other", "others", "otherwise", "ought", "our", "ours",
		"ourselves", "out", "outside", "over", "overall", "own", "p",
		"particular", "particularly", "per", "perhaps", "placed", "please",
		"plus", "possible", "presumably", "probably", "provides", "q",
		"que", "quite", "qv", "r", "rather", "rd", "re", "really",
		"reasonably", "regarding", "regardless", "regards", "relatively",
		"respectively", "right", "s", "said", "same", "saw", "say",
		"saying", "says", "second", "secondly", "see", "seeing", "seem",
		"seemed", "seeming", "seems", "seen", "self", "selves", "sensible",
		"sent", "serious", "seriously", "seven", "several", "shall", "she",
		"should", "shouldn't", "since", "six", "so", "some", "somebody",
		"somehow", "someone", "something", "sometime", "sometimes",
		"somewhat", "somewhere", "soon", "sorry", "specified", "specify",
		"specifying", "still", "sub", "such", "sup", "sure", "t", "t's",
		"take", "taken", "tell", "tends", "th", "than", "thank", "thanks",
		"thanx", "that", "that's", "thats", "the", "their", "theirs",
		"them", "themselves", "then", "thence", "there", "there's",
		"thereafter", "thereby", "therefore", "therein", "theres",
		"thereupon", "these", "they", "they'd", "they'll", "they're",
		"they've", "think", "third", "this", "thorough", "thoroughly",
		"those", "though", "three", "through", "throughout", "thru",
		"thus", "to", "together", "too", "took", "toward", "towards",
		"tried", "tries", "truly", "try", "trying", "twice", "two", "u",
		"un", "under", "unfortunately", "unless", "unlikely", "until",
		"unto", "up", "upon", "us", "use", "used", "useful", "uses",
		"using", "usually", "uucp", "v", "value", "various", "very", "via",
		"viz", "vs", "w", "want", "wants", "was", "wasn't", "way", "we",
		"we'd", "we'll", "we're", "we've", "welcome", "well", "went",
		"were", "weren't", "what", "what's", "whatever", "when", "whence",
		"whenever", "where", "where's", "whereafter", "whereas", "whereby",
		"wherein", "whereupon", "wherever", "whether", "which", "while",
		"whither", "who", "who's", "whoever", "whole", "whom", "whose",
		"why", "will", "willing", "wish", "with", "within", "without",
		"won't", "wonder", "would", "would", "wouldn't", "x", "y", "yes",
		"yet", "you", "you'd", "you'll", "you're", "you've", "your",
		"yours", "yourself", "yourselves", "z", "zero","taglibs", "<%","%>"};
	
	private String title;
	private String contents;
	
	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

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
	}

	public String removeJspTags(String contents){
		StringBuilder sb=new StringBuilder(contents);
		while(true){
			if (sb.indexOf("<%") != -1 && sb.indexOf("%>") != -1){
				int start=contents.indexOf("<%");
				int end=contents.indexOf("%>");
		 		sb.replace(start, end+2, "");
			}
			else {
				break;
			}
		}
		int start=contents.indexOf("<%");
		int end=contents.indexOf("%>");
 		sb.replace(start, end+2, ""); 		
		return sb.toString();
	}
	
	public void createDisplayStr(String contents, String searchText){
		String[] searchByStr=new String[]{searchText};
		if (searchText.indexOf("\"") == -1 && searchText.lastIndexOf("\"") == -1){
			searchByStr=searchText.split(" ");
		}
				
		int displayTextLen=100;		
		if (searchByStr.length > 1){
			displayTextLen=(int)Math.rint(displayTextLen/searchByStr.length);
		}
		
		for (int i=0; i<searchByStr.length; i++){
			displayStr(contents,displayTextLen,searchByStr[i]);
		}		
	}
	private static String displayStr(String contents, int displayLength, String searchStr) {
		
		String[] strArray=new String[2];
		strArray[0]=contents.substring(0, contents.indexOf(searchStr));
		strArray[1]=contents.substring(contents.indexOf(searchStr)+searchStr.length(), contents.length());
			
		String startStr="";
		String endStr="";		
		if (strArray[0].length() > displayLength){
			startStr=strArray[0].substring(strArray[0].length()-displayLength, strArray[0].length());
		}
		else {
			startStr=strArray[0].substring(0, strArray[0].length());
		}

		if (strArray[1].length() > displayLength){
			endStr=strArray[1].substring(0, displayLength);
		}
		else {
			endStr=strArray[1].substring(0, strArray[1].length());
		}
				
		String finalStr=startStr.trim()+" "+searchStr+" "+endStr.trim();
		int startIndex=finalStr.indexOf(" ");
		int lastIndex=finalStr.lastIndexOf(" ");
		finalStr=finalStr.substring(startIndex, lastIndex);
		
		return finalStr.trim();
	}

	public void parse(File file) throws IOException, InterruptedException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			HTMLParser parser = new HTMLParser(fis);
			
			this.title=Entities.encode(parser.getTitle());
			StringBuilder sb=new StringBuilder();
			LineNumberReader reader = new LineNumberReader(parser.getReader());
			for (String l = reader.readLine(); l != null; l = reader.readLine()){
				System.out.println(l);
				sb.append(l);
			}
			this.contents=sb.toString();
		} 
		finally {
			if (fis != null)
				fis.close();
		}
	}
}
