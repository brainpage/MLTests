import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;


public class UserVectorSplitterReducer extends Reducer<IntWritable, VectorOrPrefWritable, IntWritable, VectorOrPrefWritable>{
	public void reduce(IntWritable key, Iterable<VectorOrPrefWritable> values, Context context) throws IOException, InterruptedException {
		for (VectorOrPrefWritable sa : values){
			System.out.println(key.get() + ":" + sa.toString());
		context.write(key, sa);
		}
	}

}
