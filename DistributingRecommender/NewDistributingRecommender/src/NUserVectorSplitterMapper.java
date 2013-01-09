

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class NUserVectorSplitterMapper
		extends
		Mapper<LongWritable, VectorWritable, IntWritable, VectorOrPrefWritable> {

	public void map(LongWritable key, VectorWritable value, Context context)
			throws IOException, InterruptedException {
		long userID = key.get();
		Vector userVector = value.get();
		Iterator<Vector.Element> it = userVector.iterateNonZero();
		IntWritable itemIndexWritable = new IntWritable();
		while (it.hasNext()) {
			Vector.Element e = it.next();
			int itemIndex = e.index();
			float preferenceValue = (float) e.get();
			itemIndexWritable.set(itemIndex);
		//	System.out.println(itemIndex + ":" + (new VectorOrPrefWritable(userID, preferenceValue)).toString());
			context.write(itemIndexWritable, 
					new VectorOrPrefWritable(userID, preferenceValue));
		}
	}
}