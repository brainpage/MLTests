import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.utils.clustering.ClusterDumper;


public class FuzzyKMeansWithTanicoForUniclick {
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
   String line;
   
   //Directory of datas processed by Pig
   FileReader fr = new FileReader("/home/yf/work/mahoutdata/clustering/fuzzykmeans/uniclickdata/groupByUserID/part-r-00000");
   BufferedReader br = new BufferedReader(fr);
   
   //Input path to clustering 
   Path input = new Path("/home/yf/work/mahoutdata/clustering/fuzzykmeans/uniclickinput1");
   Configuration conf = new Configuration();
   FileSystem fs = FileSystem.get(conf);
   SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, input, Text.class, VectorWritable.class);
   VectorWritable vecw;
   RandomAccessSparseVector rv;

   //Transform datas into RandomAccessSparseVectors, and then write them to SequenceFile.
   while((line = br.readLine()) != null){
	   rv=new RandomAccessSparseVector(Integer.MAX_VALUE,1);
	   vecw = new VectorWritable();
	   String userID = line.substring(2,18) ;
	   while (line.contains("(")){
           int i = line.indexOf("(");
           int j = line.indexOf(")");
           String s = line.substring(i+1,j);
           String[] ss = s.split(",");
          rv.set(Integer.parseInt(ss[2]), Double.parseDouble(ss[1]));                      
           line = line.substring(j+1);
          }
    vecw.set(rv);
    writer.append(new Text(userID), vecw);
   }
   fr.close();
   writer.close();
   

  
   Path output = new Path("/home/yf/work/mahoutdata/clustering/fuzzykmeans/uniclickoutput1");
   Path clustersIn = new Path("/home/yf/work/mahoutdata/clustering/fuzzykmeans/uniclickinitial1");
  
   //Do the clustering, using CanopyDriver to create initial cluster centroid, and using FuzzyKMeansDriver to do a more precise clustering.
   CanopyDriver.run(conf, input, clustersIn, new TanimotoDistanceMeasure(), 0.5, 0.8, false, 0.1, true);
   FuzzyKMeansDriver.run(conf,input, new Path(clustersIn,"clusters-0-final"), output,
   new TanimotoDistanceMeasure(), 0.1, 10, 2, true, true, 0, true);

  //Dump the result.
   System.out.println("done!");
   ClusterDumper clusterDumper = new ClusterDumper(new Path(output, "clusters-*-final"), new Path(output,
           "clusteredPoints"));
       clusterDumper.printClusters(null);  
          
}
}
