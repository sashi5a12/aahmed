package com.examples;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class EX_1_FileSystemCat {

	public static void main(String[] args) throws IOException, URISyntaxException {
		Configuration conf = new Configuration();
		String uri="hdfs://master:9000/user/aahmed/NAMES.TXT";
        conf.set("fs.defaultFS","hdfs://master:9000/");

		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		
//		InputStream in = null;
		FSDataInputStream in=null;
		try {
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096, false);
		}finally{
			IOUtils.closeStream(in);
		}
	}

}
