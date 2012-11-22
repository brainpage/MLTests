
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
// import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
//import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
// import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

public class Conf5 {

	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(Conf5.class);
		Job job = new Job(conf, "job5");
		
	/*	SequenceFileInputFormat.addInputPath(job, new Path("/home/yf/work/hadoopcodes/output/output3/part-00000"));
		SequenceFileInputFormat.addInputPath(job, new Path("/home/yf/work/hadoopcodes/output/output4/part-00000")); */
		
		MultipleInputs.addInputPath(job, new Path("/home/yf/work/hadoopcodes/output/output3/part-00000"),
				FileInputFormat.class);
		MultipleInputs.addInputPath(job, new Path("/home/yf/work/hadoopcodes/output/output4/part-00000"),
				FileInputFormat.class);
																	
		FileOutputFormat.setOutputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/output/output5"));		

		job.setMapperClass(ToVectorAndPrefMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(VectorOrPrefWritable.class);
		
		job.setReducerClass(ToVectorAndPrefReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorAndPrefsWritable.class);
		
		JobClient.runJob(conf);

	}
}
