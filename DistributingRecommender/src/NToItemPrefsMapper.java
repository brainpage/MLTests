
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public  class NToItemPrefsMapper extends
		Mapper<LongWritable, Text, LongWritable,VectorWritable> {

	private static final Pattern NUMBERS = Pattern.compile("(\\d+)");
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		
		Matcher m = NUMBERS.matcher(line);
		m.find();
		LongWritable userID = new LongWritable(Long.parseLong(m.group()));
		IntWritable itemID = new IntWritable();		 
		Vector userVector = new RandomAccessSparseVector(Integer.MAX_VALUE, 100);
	//	List<VectorWritable> userList = new ArrayList<VectorWritable>();
		while (m.find()) {
			itemID.set(Integer.parseInt(m.group()));
			userVector.set(itemID.get(), 1.0f);
		}
		System.out.println(userVector.asFormatString());
		context.write(userID, new VectorWritable(userVector));
	}

}