import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.math.VectorWritable;

public class Conf6 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job6");
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		SequenceFileInputFormat.addInputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output5/part-r-00000"));
																	
		SequenceFileOutputFormat.setOutputPath(job, new Path(
				"/home/yf/work/hadoopcodes/output/output6"));		

		job.setMapperClass(PartialMultiplyMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(VectorWritable.class);	
		
		job.setCombinerClass(AggregateCombiner.class);
		
		job.setReducerClass(AggregateAndRecommendReducer.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(RecommendedItemsWritable.class);  
		
        job.submit();

	}
}