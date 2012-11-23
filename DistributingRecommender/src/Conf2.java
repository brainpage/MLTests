import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.math.VectorWritable;

public class Conf2 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job2");
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		SequenceFileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output1/part-r-00000"));
																	
		SequenceFileOutputFormat.setOutputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output2"));		

		job.setMapperClass(UserVectorToCooccurrenceMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);		
		
		job.setReducerClass(UserVectorToCooccurrenceReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorWritable.class);  
		
        job.submit();

	}
}


