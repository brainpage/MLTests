
package com.brainpage.clustering;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class test extends EvalFunc<Integer>{

        @Override
        public Integer exec(Tuple time) throws IOException{
                // TODO Auto-generated method stub
                String impression_time = time.get(0).toString();
                String click_time = time.get(0).toString();
                
                return impression_time.length();
        }
}
