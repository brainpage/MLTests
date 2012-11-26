import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;


public class NToVectorAndPrefMapper extends Mapper<IntWritable, VectorOrPrefWritable, IntWritable, VectorOrPrefWritable>{

	public void map(IntWritable key, VectorOrPrefWritable value, Context context) throws IOException, InterruptedException{
		context.write( key, value);
	}
}
