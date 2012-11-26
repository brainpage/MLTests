
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.math.VectorWritable;

public class NConf1 {

	//public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	public static Job SetJob() throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job1");
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/input/webdata.txt"));
																	
		SequenceFileOutputFormat.setOutputPath(job, new Path(
				"/home/yf/work/hadoopcodes/Noutput/output1"));		

		job.setMapperClass(NToItemPrefsMapper.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(VectorWritable.class);		
		 		
		return job;
        //job.submit();

	}
}

