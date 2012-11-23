import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class ConfTest {

	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(Conf1.class);
		conf.setJobName("job1");
		FileInputFormat.addInputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/test/input/webdata.txt")); 
																	
		FileOutputFormat.setOutputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/test/output/output1"));		

		conf.setMapperClass(Test1Mapper.class);
		
		conf.setReducerClass(Test1Reducer.class);
		conf.setOutputKeyClass(LongWritable.class);
		conf.setOutputValueClass(LongWritable.class);
		
		JobClient.runJob(conf);

	}
}
