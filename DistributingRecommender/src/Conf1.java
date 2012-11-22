import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.mahout.math.VarLongWritable;
import org.apache.mahout.math.VectorWritable;

public class Conf1 {

	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(Conf1.class);
		//conf.setJobName("job1");
		Job job = new Job(conf, "job1");
		FileInputFormat.addInputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/input/webdata.txt")); // new
																	// Path(args[0])
		FileOutputFormat.setOutputPath(conf, new Path(
				"/home/yf/work/hadoopcodes/output/output1"));		

		job.setMapperClass(ToItemPrefsMapper.class);
		job.setReducerClass(ToUserVectorReducer.class);

		job.setOutputKeyClass(VarLongWritable.class);
		job.setOutputValueClass(VectorWritable.class);
		
		JobClient.runJob(conf);

	}
}
