/*
 * Source code for Listing 6.8
 * 
 */


import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class NAggregateCombiner
		extends
		Reducer<LongWritable, VectorWritable, LongWritable, VectorWritable> {

	public void reduce(LongWritable key, Iterable<VectorWritable> values,
			Context context) throws IOException, InterruptedException {
		Vector partial = null;
		for (VectorWritable vectorWritable : values) {
			partial = partial == null ? vectorWritable.get() : partial
					.plus(vectorWritable.get());
		}
	//	System.out.println(key.toString() + "," + partial.toString());
		context.write(key, new VectorWritable(partial));
	}
}