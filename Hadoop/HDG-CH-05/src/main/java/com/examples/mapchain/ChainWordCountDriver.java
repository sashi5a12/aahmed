package com.examples.mapchain;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ChainWordCountDriver extends Configured implements Tool{
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf=getConf();
		Job job = Job.getInstance(conf, "Chaining word count");
		
		Path outputPath = new Path("output");
        FileSystem  fs = FileSystem.get(new URI(outputPath.toString()), conf);
        
        //It will delete the output directory if it already exists. don't need to delete it  manually  
        fs.delete(outputPath, true);
        
        //Setting the input and output path 
        FileInputFormat.addInputPaths(job, "customers.txt");
        FileOutputFormat.setOutputPath(job, outputPath);
        
        //Considering the input and output as text file set the input & output format to TextInputFormat 
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        Configuration tokenizerMapperConfig = new Configuration(false);
        ChainMapper.addMapper(job, TokenizerMapper.class,
                LongWritable.class, Text.class,
                Text.class, IntWritable.class,
                tokenizerMapperConfig);

        Configuration uppercaseMapperConfig = new Configuration(false);
        ChainMapper.addMapper(job, UpperCaserMapper.class,
                Text.class, IntWritable.class,
                Text.class, IntWritable.class,
                uppercaseMapperConfig);

        Configuration wordCountReducerConfig = new Configuration(false);
        ChainReducer.setReducer(job, WordCountReducer.class, 
        		Text.class, IntWritable.class, 
        		Text.class, IntWritable.class, 
        		wordCountReducerConfig);
        
        Configuration lastMapperConfig = new Configuration(false);
        ChainReducer.addMapper(job, LastMapper.class, 
        		Text.class, IntWritable.class, 
        		Text.class, IntWritable.class, 
        		lastMapperConfig);
		
        return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new ChainWordCountDriver(), args));
    }
}
