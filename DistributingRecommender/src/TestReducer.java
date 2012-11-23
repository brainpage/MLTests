import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.VectorWritable;


public class TestReducer extends Reducer<LongWritable, VectorWritable, LongWritable, VectorWritable>{
	public void reduce(LongWritable key, Iterable<VectorWritable> values, Context context) throws IOException, InterruptedException {
		for (VectorWritable sa : values){
			System.out.println(key.get() + ":" + sa.toString());
		context.write(key, sa);
		}
	}

}
