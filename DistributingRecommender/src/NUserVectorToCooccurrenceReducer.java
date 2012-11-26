/*
 * Source code for Listing 6.4
 * 
 */

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class NUserVectorToCooccurrenceReducer extends
		Reducer<IntWritable, IntWritable, IntWritable, VectorOrPrefWritable> {

	public void reduce(IntWritable itemIndex1,
			Iterable<IntWritable> itemIndex2s, Context context)
			throws IOException, InterruptedException {
		Vector cooccurrenceRow = new RandomAccessSparseVector(
				Integer.MAX_VALUE, 100);
		for (IntWritable intWritable : itemIndex2s) {
			int itemIndex2 = intWritable.get();
			cooccurrenceRow.set(itemIndex2,
					cooccurrenceRow.get(itemIndex2) + 1.0);
		}
		System.out.println(itemIndex1.get() + ":" + cooccurrenceRow.asFormatString());
		context.write(itemIndex1, new VectorOrPrefWritable(cooccurrenceRow));
	}
}