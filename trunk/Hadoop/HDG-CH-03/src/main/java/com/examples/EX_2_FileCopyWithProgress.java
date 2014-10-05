package com.examples;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class EX_2_FileCopyWithProgress {

	public static void main(String[] args) throws IOException {
		String localFile="/Users/aahmed/hadoop/1902";
		String dist="hdfs://master:9000/user/aahmed/1902";
		
		InputStream in = new BufferedInputStream(new FileInputStream(localFile));
		
		Configuration conf= new Configuration();
        conf.set("fs.defaultFS","hdfs://master:9000/");

		FileSystem fs =FileSystem.get(URI.create(dist), conf);
		OutputStream out = fs.create(new Path(dist), new Progressable() {
			
			@Override
			public void progress() {
				System.out.print(".");
			}
		});
		
		IOUtils.copyBytes(in, out, 4096, true);
	}

}
