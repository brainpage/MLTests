
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

public class Conf4 {

	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(Conf4.class);
		Job job = new Job(conf, "job4");
		FileInputFormat.addInputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/output/output2/part-00000")); 
																	
		FileOutputFormat.setOutputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/output/output4"));		

		job.setMapperClass(CooccurrenceColumnWrapperMapper.class);
		//job.setReducerClass(UserVectorToCooccurrenceReducer.class);
		

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorOrPrefWritable.class);
		
		JobClient.runJob(conf);

	}
}
