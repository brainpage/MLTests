import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

public class Conf5 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job5");
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		SequenceFileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output3/part-r-00000"));
		SequenceFileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output4/part-r-00000"));
																	
		SequenceFileOutputFormat.setOutputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output5"));		

		job.setMapperClass(ToVectorAndPrefMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(VectorOrPrefWritable.class);		
		
		job.setReducerClass(ToVectorAndPrefReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorAndPrefsWritable.class);  
		
        job.submit();

	}
}
