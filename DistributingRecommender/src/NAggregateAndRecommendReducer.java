  
  
import java.io.IOException;  
import java.net.URI;  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Iterator;  
import java.util.List;  
import java.util.PriorityQueue;  
import java.util.Queue;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;  
import org.apache.hadoop.io.Writable;  
import org.apache.hadoop.mapreduce.Reducer;  
import org.apache.hadoop.util.ReflectionUtils;  
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;  
import org.apache.mahout.cf.taste.impl.common.FastMap;  
import org.apache.mahout.cf.taste.impl.recommender.ByValueRecommendedItemComparator;  
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;  
import org.apache.mahout.cf.taste.recommender.RecommendedItem;   
import org.apache.mahout.math.Vector;  
import org.apache.mahout.math.VectorWritable;  
  
public class NAggregateAndRecommendReducer extends Reducer<LongWritable,VectorWritable,LongWritable,RecommendedItemsWritable> {  
      
    private int recommendationsPerUser=5;  
    private String path="/home/yf/work/hadoopcodes/output/output1/part-r-00000";  
      
    private static FastMap<Integer,String> map=new FastMap<Integer,String>();  
    public void setup(Context context) throws IOException{  
        Configuration conf=new Configuration();  
        FileSystem fs=FileSystem.get(URI.create(path), conf);  
        Path tempPath=new Path(path);  
        SequenceFile.Reader reader=null;  
        try {  
            reader=new SequenceFile.Reader(fs, tempPath, conf);  
            Writable key=(Writable)ReflectionUtils.newInstance(reader.getKeyClass(),conf);  
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);   
        //  long position = reader.getPosition();    
            while (reader.next(key, value)) {    
                map.put(Integer.parseInt(key.toString()), value.toString());  
        //      System.out.println(key.toString()+","+value.toString());  
            //    position = reader.getPosition(); // beginning of next record    
            }  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }    
    }  
      
    public void reduce(LongWritable key, Iterable<VectorWritable> values,Context context) throws IOException, InterruptedException{  
          
            int userID=(int)key.get();  
            Vector rev=null;  
            for(VectorWritable vec:values){  
                rev=rev==null? vec.get():rev.plus(vec.get());  
            }  
            Queue<RecommendedItem>topItems=new PriorityQueue<RecommendedItem>(  
                    recommendationsPerUser+1,  
                    Collections.reverseOrder(ByValueRecommendedItemComparator.getInstance())  
                    );  
            Iterator<Vector.Element>recommendationVectorIterator=  
                    rev.iterateNonZero();  
            while(recommendationVectorIterator.hasNext()){  
                Vector.Element e=recommendationVectorIterator.next();  
                int index=e.index();  
              //  System.out.println("Vecotr.element.indxe:"+index);  //  test here  find the index is item id or not  ** test result : index is item  
                if(!hasItem(userID,String.valueOf(index))){  
                    float value=(float) e.get();  
                    if(topItems.size()<recommendationsPerUser){  
                        //  here only set index  
                        topItems.add(new GenericRecommendedItem(index,value));  
                    }else if(value>topItems.peek().getValue()){  
                        topItems.add(new GenericRecommendedItem(index,value));  
                        topItems.poll();  
                    }  
                }  
            }  
            List<RecommendedItem>recom=new ArrayList<RecommendedItem>(topItems.size());  
            recom.addAll(topItems);  
            Collections.sort(recom,ByValueRecommendedItemComparator.getInstance());
            System.out.println(key.toString() + "//" + recom.toString());
            context.write(key, new RecommendedItemsWritable(recom));          
        }  
      
    public static boolean hasItem(int user,String item){  // to check whether the user has rate the item  
        boolean flag=false;  
        String items=map.get(user);  
        if(items.contains(item)){  
            flag=true;  
        }  
        return flag;  
    }  
} 
