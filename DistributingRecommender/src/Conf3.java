import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

public class Conf3 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job3");
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		SequenceFileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output1/part-r-00000"));
																	
		SequenceFileOutputFormat.setOutputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output3"));		

		job.setMapperClass(UserVectorSplitterMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(VectorOrPrefWritable.class);		
		
		job.setReducerClass(UserVectorSplitterReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorOrPrefWritable.class);  
		
        job.submit();

	}
}
