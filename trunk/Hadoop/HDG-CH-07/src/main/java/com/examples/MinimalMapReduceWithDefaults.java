package com.examples;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.HashPartitioner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MinimalMapReduceWithDefaults extends Configured implements Tool {

	public static Job parseInputAndOutput(Tool tool, Configuration conf,
			String[] args) throws IOException {
		/*if (args.length != 2) {
			printUsage(tool, "<input> <output>");
			return null;
		}*/
		Job job = new Job(conf);
		job.setJarByClass(tool.getClass());
		FileInputFormat.addInputPath(job, new Path("1901"));
		FileOutputFormat.setOutputPath(job, new Path("output"));
		return job;
	}

	public static void printUsage(Tool tool, String extraArgsUsage) {
		System.err.printf("Usage: %s [genericOptions] %s\n\n", tool.getClass()
				.getSimpleName(), extraArgsUsage);
		GenericOptionsParser.printGenericCommandUsage(System.err);
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job =parseInputAndOutput(this, getConf(), args);
		if (job == null) {
			return -1;
		}
		
		job.setInputFormatClass(TextInputFormat.class);
		
		job.setMapperClass(Mapper.class);
		
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
//		job.setPartitionerClass(HashPartitioner.class);
		
		job.setNumReduceTasks(1);
		job.setReducerClass(Reducer.class);
		
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new MinimalMapReduceWithDefaults(), args);
		System.exit(exitCode);
	}
}
