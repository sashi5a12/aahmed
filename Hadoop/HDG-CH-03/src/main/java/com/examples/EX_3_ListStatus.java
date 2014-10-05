package com.examples;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class EX_3_ListStatus {
    public static void main(String args[]) throws IOException{
        
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://master:9000/");
        
        FileSystem fs = FileSystem.get(URI.create("hdfs://master:9000/user/aahmed"), conf);
        
        Path[] paths=new Path[2];
        paths[0] = new Path("hdfs://master:9000/user/aahmed");
        paths[1] = new Path("hdfs://master:9000/user/aahmed/1901");
        
        FileStatus[] status = fs.listStatus(paths);
        System.out.println(status[0]);
        
        Path[] listedPaths = FileUtil.stat2Paths(status); 
        for (Path p : listedPaths) {
            System.out.println(p); 
        }
    }
}
