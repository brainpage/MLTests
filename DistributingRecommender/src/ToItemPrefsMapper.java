/*
 * Source code for Listing 6.1
 * 
 */
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public  class ToItemPrefsMapper extends
		Mapper<LongWritable, Text, LongWritable,LongWritable> {

	private static final Pattern NUMBERS = Pattern.compile("(\\d+)");
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		
		Matcher m = NUMBERS.matcher(line);
		m.find();
		LongWritable userID = new LongWritable(Long.parseLong(m.group()));
		LongWritable itemID = new LongWritable();
		while (m.find()) {
			itemID.set(Long.parseLong(m.group()));
			context.write(userID, itemID);
		}
	}

}