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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocCollector;

public class SearchFiles {
	
	private static String queryString;
	private static final String INDEX_DIR="C:/Java/tomcat_vzdncore_1/index";			//Where index files save.
	
	private SearchFiles() {
	}
	
	public static void main(String[] args) throws Exception {
		String usage = "Usage:\tjava org.apache.lucene.demo.SearchFiles [-field f] [-raw] [-paging hitsPerPage]";
		usage += "\n\tSpecify 'false' for hitsPerPage to use streaming instead of paging search.";
		if (args.length > 0
				&& ("-h".equals(args[0]) || "-help".equals(args[0]))) {
			System.out.println(usage);
			System.exit(0);
		}

		String field = "contents";
		boolean raw = false;
		boolean paging = true;
		int hitsPerPage = 100;

		for (int i = 0; i < args.length; i++) {
			if ("-field".equals(args[i])) {
				field = args[i + 1];
				i++;
			} else if ("-raw".equals(args[i])) {
				raw = true;
			} else if ("-paging".equals(args[i])) {
				if (args[i + 1].equals("false")) {
					paging = false;
				} else {
					hitsPerPage = Integer.parseInt(args[i + 1]);
					if (hitsPerPage == 0) {
						paging = false;
					}
				}
				i++;
			}
		}

		IndexReader reader = IndexReader.open(INDEX_DIR);
		Searcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(SearchUtil.SMART_STOP_WORDS);
		QueryParser parser = new QueryParser(field, analyzer);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		while (true) {
			System.out.println("Enter query: ");

			String line = in.readLine();

			if (line == null || line.length() == -1)
				break;

			line = line.trim();
			if (line.length() == 0)
				break;
			queryString=line;
			Query query = parser.parse(line);
			System.out.println("Searching for: " + query.toString(field));

			doPagingSearch(in, searcher, query, hitsPerPage, raw);
		}
		reader.close();
	}
  
  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
   * 
   */
  public static void doPagingSearch(BufferedReader in, Searcher searcher, Query query, 
                                     int hitsPerPage, boolean raw) throws IOException {
 
	  	// Collect enough docs to show 5 pages
		TopDocCollector collector = new TopDocCollector( 5 * hitsPerPage);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		int numTotalHits = collector.getTotalHits();
		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);
		SearchUtil searchUtil = new SearchUtil();
		
		while (true) {
			if (end > hits.length) {
				System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits + " total matching documents collected.");
				System.out.println("Collect more (y/n) ?");
				String line = in.readLine();
				if (line.length() == 0 || line.charAt(0) == 'n') {
					break;
				}

				collector = new TopDocCollector(numTotalHits);
				searcher.search(query, collector);
				hits = collector.topDocs().scoreDocs;
			}

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
				if (raw) { // output raw format
					System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
					continue;
				}

				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");

				if (path != null) {
					System.out.println((i + 1) + ". " + path);

					try {
						searchUtil.parse(new File(path));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					/*String title = doc.get("title");
					if (title != null) {
						System.out.println("   Title: " + doc.get("title"));
					}*/
					String title=SearchUtil.getTitle(searchUtil.getContents());
					String contents=SearchUtil.removeTitle(searchUtil.getContents(),title);
					contents=SearchUtil.removeJspTags(contents);
					contents=SearchUtil.removeDirty(contents, "\"", "/>", 2);
					contents=SearchUtil.removeDirty(contents, "\"", "\">", 2);
					contents=StringUtils.remove(contents, "\" >");
					contents=SearchUtil.removeDirty(contents, "[", "]", 1);
					contents=StringUtils.replace(contents, "  ", "");	
					contents=StringUtils.replace(contents, " ", "");
					contents=StringUtils.replace(contents, "�", "");		
					contents=StringUtils.replace(contents, "Back To Top", "");	
					contents=StringUtils.replace(contents, "Learn More", "");	
					contents=StringUtils.replace(contents, "View Here", "");	
					contents=StringUtils.replace(contents, "Submit App", "");	
	
					String displayStr= SearchUtil.createDisplayStr(contents, queryString);
					displayStr=SearchUtil.highlightSearchWord(displayStr, queryString);
					System.out.println("   title: "+title);
					System.out.println("   contents: " + contents);
					System.out.println("   displayStr: " + displayStr);

				} else {
					System.out.println((i + 1) + ". " + "No path for this document");
				}

				System.out.println("================================================================");
			}

			if (numTotalHits >= end) {
				boolean quit = false;
				while (true) {
					System.out.print("Press ");
					if (start - hitsPerPage >= 0) {
						System.out.print("(p)revious page, ");
					}
					if (start + hitsPerPage < numTotalHits) {
						System.out.print("(n)ext page, ");
					}
					System.out.println("(q)uit or enter number to jump to a page.");

					String line = in.readLine();
					if (line.length() == 0 || line.charAt(0) == 'q') {
						quit = true;
						break;
					}
					if (line.charAt(0) == 'p') {
						start = Math.max(0, start - hitsPerPage);
						break;
					} else if (line.charAt(0) == 'n') {
						if (start + hitsPerPage < numTotalHits) {
							start += hitsPerPage;
						}
						break;
					} else {
						int page = Integer.parseInt(line);
						if ((page - 1) * hitsPerPage < numTotalHits) {
							start = (page - 1) * hitsPerPage;
							break;
						} else {
							System.out.println("No such page");
						}
					}
				}
				if (quit)break;
				end = Math.min(numTotalHits, start + hitsPerPage);
			}
		}
  	}
}