/*
 * Source code for Listing 6.2
 * 
 */

import java.io.IOException;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.math.Vector;

public class ToUserVectorReducer extends
		Reducer<LongWritable, LongWritable, LongWritable, VectorWritable> {

	public void reduce(LongWritable userID,
			Iterable<LongWritable> itemPrefs, Context context)
			throws IOException, InterruptedException {
		Vector userVector = new RandomAccessSparseVector(Integer.MAX_VALUE, 100);
		for (LongWritable itemPref : itemPrefs) {
			userVector.set((int) itemPref.get(), 1.0f);
		}
		System.out.println(userVector.asFormatString());
		context.write(userID, new VectorWritable(userVector));
	}
}