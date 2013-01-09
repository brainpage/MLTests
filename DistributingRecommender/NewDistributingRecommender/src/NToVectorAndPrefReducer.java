import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.math.Vector;

public class NToVectorAndPrefReducer extends Reducer<IntWritable,VectorOrPrefWritable,IntWritable,VectorAndPrefsWritable>{

	public void reduce(IntWritable key, Iterable<VectorOrPrefWritable> values, Context context) throws IOException, InterruptedException{
		List<Long> userfs = new ArrayList<Long>();
		List<Float> prefs = new ArrayList<Float>();
		Vector v = null;
		for(VectorOrPrefWritable value:values){
			if(value.getVector()!=null){
				v =value.getVector();
			}else{
				userfs.add(value.getUserID());
				prefs.add(value.getValue());
			}
		}
		VectorAndPrefsWritable forprint = new VectorAndPrefsWritable(v,userfs,prefs);
	//	System.out.println(key.get() + ":" + forprint.toString() );
		context.write(key, new VectorAndPrefsWritable(v,userfs,prefs));
	}
}
