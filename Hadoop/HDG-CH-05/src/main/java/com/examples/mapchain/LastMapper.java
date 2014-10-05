package com.examples.mapchain;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LastMapper extends Mapper<Text, IntWritable,Text, Text> {
    
    public void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
        String[] word = key.toString().split(",");
        System.out.println("Upper Case:"+word);
        context.write(new Text(word[0]), new Text(word[1]));    
    }
}