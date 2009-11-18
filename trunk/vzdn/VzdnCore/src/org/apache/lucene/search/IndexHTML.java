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

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;

import com.netpace.vzdn.servlets.SearchServlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Arrays;

/** Indexer for HTML files. */
public class IndexHTML {
	
	private IndexHTML() {}

	private static boolean deleting = false;		// true during deletion pass
	private static IndexReader reader;				// existing index
	private static IndexWriter writer;				// new index being built
	private static TermEnum uidIter;				// document id iterator

	// private static final String INDEX_DIR="C:/Java/Tomcat6.0.14/webapps/vzdn/WEB-INF/index"; //Where index files save.
	// private static final String APP_DIR="C:/Java/Tomcat6.0.14/webapps/vzdn"; //Dir on which index applies.

	/** Indexer for HTML files. */
	public static void main(String args[]) {
		String usage = "IndexHTML <index_directory> <root_directory>";
		if (args.length < 2) {
			System.err.println("Usage: " + usage);
			return;
		}
		try {
			String index_Dir = args[0];
			String appDir = args[1];
			
			File indexDir = new File(index_Dir);
			boolean create = false;
			File root = new File(appDir);

			Date start = new Date();
			if (!indexDir.exists()) {
				System.out.println("Index directory doesnot exists. Create new one.");
				create = true;
			}

			if (!create) { // delete stale docs
				System.out.println("Index directory exists. Reindexing the changed files.");				
				deleting = true;
				indexDocs(root, index_Dir, create);
			}

			writer = new IndexWriter(index_Dir, new StandardAnalyzer(SearchUtil.SMART_STOP_WORDS), create, IndexWriter.MaxFieldLength.LIMITED);
			indexDocs(root, index_Dir, create); // add new docs

			System.out.println("Optimizing index...");
			writer.optimize();

			Date end = new Date();

			System.out.print(end.getTime() - start.getTime());
			System.out.println(" total milliseconds");

		} catch (Exception e) {
			System.out.println(" caught a " + e.getClass() + " with message: " + e.getMessage());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

  /* Walk directory hierarchy in uid order, while keeping uid iterator from existing index in sync.  
   * Mismatches indicate one of: 
   * (a) old documents to be deleted; 
   * (b) unchanged documents, to be left alone; or 
   * (c) new documents, to be indexed.
   */

  private static void indexDocs(File file, String index, boolean create) throws Exception {
    if (!create) {									// incrementally update

      reader = IndexReader.open(index);				// open existing index
      uidIter = reader.terms(new Term("uid", ""));	// init uid iterator

      indexDocs(file);

      if (deleting) {				  // delete rest of stale docs
        while (uidIter.term() != null && uidIter.term().field() == "uid") {
          System.out.println("deleting " + HTMLDocument.uid2url(uidIter.term().text()));
          reader.deleteDocuments(uidIter.term());
          uidIter.next();
        }
        deleting = false;
      }

      uidIter.close();				  // close uid iterator
      reader.close();				  // close existing index

    } else					  // don't have exisiting
      indexDocs(file);
  }

  private static void indexDocs(File file) throws Exception {
    if (file.isDirectory()) {					// if a directory
    	if (file.getName().equalsIgnoreCase("spotlights")){}
    	else if (file.getName().equalsIgnoreCase("Side_Bars")){}
    	else if (file.getName().equalsIgnoreCase("errorPages")){}
    	else if (file.getName().equalsIgnoreCase("Backup")){}
    	else{
    		String[] files = file.list();			// list its files
    		Arrays.sort(files);						// sort the files
    		for (int i = 0; i < files.length; i++)	// recursively index them
    			indexDocs(new File(file, files[i]));
    	}
    } 
    else if (file.getPath().endsWith("header.jsp") ||    		
    		 file.getPath().endsWith("footer.jsp") ||
    		 file.getPath().endsWith("SearchResult.jsp")){}
    else if (
      file.getPath().endsWith(".html") || // index .html files
      file.getPath().endsWith(".htm")  || // index .htm files
      file.getPath().endsWith(".jsp")) {  // index .jsp files

      if (uidIter != null) {
        String uid = HTMLDocument.uid(file);	  // construct uid for doc

        while (uidIter.term() != null 
        		&& uidIter.term().field() == "uid" 
        			&& uidIter.term().text().compareTo(uid) < 0) {
          if (deleting) {			  // delete stale docs
            System.out.println("deleting " + HTMLDocument.uid2url(uidIter.term().text()));
            reader.deleteDocuments(uidIter.term());
          }
          uidIter.next();
        }
        if (uidIter.term() != null 
        		&& uidIter.term().field() == "uid" 
        			&& uidIter.term().text().compareTo(uid) == 0) {
          uidIter.next();			// keep matching docs
        } else if (!deleting) {		// add new docs
          Document doc = HTMLDocument.Document(file);
          System.out.println("adding " + doc.get("path"));
          writer.addDocument(doc);
        }
      } else {						// creating a new index
        Document doc = HTMLDocument.Document(file);
        System.out.println("adding " + doc.get("path"));
        writer.addDocument(doc);	// add docs unconditionally
      }
    }
  }
}
