import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;


public class ToVectorAndPrefMapper extends Mapper<IntWritable, VectorOrPrefWritable, IntWritable, VectorOrPrefWritable>{

	public void map(IntWritable key, VectorOrPrefWritable value, Context context) throws IOException, InterruptedException{
		context.write( key, value);
	}
}
