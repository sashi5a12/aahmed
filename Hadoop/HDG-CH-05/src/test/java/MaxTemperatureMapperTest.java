import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.OutputLogFilter;
import org.junit.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.examples.MaxTemperatureDriver;
import com.examples.MaxTemperatureMapper;
import com.examples.MaxTemperatureReducer;

public class MaxTemperatureMapperTest {
	@Test
	public void processesValidRecord() throws IOException, InterruptedException {
		MaxTemperatureMapper mapper = new MaxTemperatureMapper();
		Text value = new Text(
				"0043011990999991950051518004+68750+023550FM-12+0382"
						+ "99999V0203201N00261220001CN9999999N9-00111+99999999999");

		@SuppressWarnings("unchecked")
		MaxTemperatureMapper.Context context = mock(MaxTemperatureMapper.Context.class);
		mapper.map(null, value, context);
		verify(context).write(new Text("1950"), new IntWritable(-11));
	}

	@Test
	public void ignoresMissingTemperatureRecord() throws IOException,
			InterruptedException {
		MaxTemperatureMapper mapper = new MaxTemperatureMapper();
		Text value = new Text(
				"0043011990999991950051518004+68750+023550FM-12+0382"
						+ "99999V0203201N00261220001CN9999999N9+99991+99999999999");
		@SuppressWarnings("unchecked")
		MaxTemperatureMapper.Context context = mock(MaxTemperatureMapper.Context.class);
		mapper.map(null, value, context);
		verify(context, never()).write(any(Text.class), any(IntWritable.class));
	}

	@Test
	public void returnsMaximumIntegerInValues() throws IOException,
			InterruptedException {
		MaxTemperatureReducer reducer = new MaxTemperatureReducer();
		Text key = new Text("1950");
		List<IntWritable> values = Arrays.asList(new IntWritable(10),
				new IntWritable(5));
		MaxTemperatureReducer.Context context = mock(MaxTemperatureReducer.Context.class);
		reducer.reduce(key, values, context);
		verify(context).write(key, new IntWritable(10));
	}

	@Test
	public void test() throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "file:///");
		conf.set("mapred.job.tracker", "local");
		Path input = new Path("1901");
		Path output = new Path("output");
		FileSystem fs = FileSystem.getLocal(conf);
		fs.delete(output, true); // delete old output
		MaxTemperatureDriver driver = new MaxTemperatureDriver();
		driver.setConf(conf);
		int exitCode = driver.run(new String[] { input.toString(),
				output.toString() });
		assertThat(exitCode,  is(0));
//		checkOutput(conf, output);
	}
	
	private void checkOutput(Configuration conf, Path output) throws IOException {
	    FileSystem fs = FileSystem.getLocal(conf);
	    Path[] outputFiles = FileUtil.stat2Paths(
	        fs.listStatus(output, new OutputLogFilter()));
	    assertThat(outputFiles.length, is(2));
	    
	    BufferedReader actual = asBufferedReader(fs.open(outputFiles[0]));
	    BufferedReader expected = asBufferedReader(
	        getClass().getResourceAsStream("/expected.txt"));
	    String expectedLine;
	    while ((expectedLine = expected.readLine()) != null) {
	      assertThat(actual.readLine(), is(expectedLine));
	    }
	    assertThat(actual.readLine(), nullValue());
	    actual.close();
	    expected.close();
	  }
	  
	  private BufferedReader asBufferedReader(InputStream in) throws IOException {
	    return new BufferedReader(new InputStreamReader(in));
	  }
}