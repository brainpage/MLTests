import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;


public class MakeARecommender {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		
		System.out.println("Start to run a job.");
		NConf1 nConf1 = new NConf1();
        Job job1 = nConf1.SetJob();
        job1.submit();
        job1.waitForCompletion(true);
        System.out.println("job1 completed.");
        
		NConf2 nConf2 = new NConf2();
        Job job2 = nConf2.SetJob();
        job2.submit();
        boolean order2 = job2.waitForCompletion(true);
        System.out.println("job2 completed.");
        
		NConf3 nConf3 = new NConf3();
        Job job3 = nConf3.SetJob();
        job3.submit();
       job3.waitForCompletion(true);
        System.out.println("job3 completed.");
        
		NConf4 nConf4 = new NConf4();
        Job job4 = nConf4.SetJob();
        job4.submit();
         job4.waitForCompletion(true);
        System.out.println("job4 completed.");
        
		NConf5 nConf5 = new NConf5();
        Job job5 = nConf5.SetJob();
        job5.submit();
        System.out.println("job5 completed.");
        
        
     
	}

}
