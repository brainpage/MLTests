package com.brainpage.clustering;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class TimeSubstraction extends EvalFunc<Integer>{

	@Override
	public Integer exec(Tuple input) throws IOException{
		// TODO Auto-generated method stub
		String time = input.get(0).toString();
		int impression_year = Integer.parseInt(time.substring(1, 5));
		int impression_month = Integer.parseInt(time.substring(6, 8));
		int impression_day = Integer.parseInt(time.substring(9,11));
		int impression_hour = Integer.parseInt(time.substring(12,14));
		int impression_min = Integer.parseInt(time.substring(15, 17));

		int click_year = Integer.parseInt(time.substring(21, 25));
		int click_month = Integer.parseInt(time.substring(26,28));
		int click_day = Integer.parseInt(time.substring(29, 31));
		int click_hour = Integer.parseInt(time.substring(32, 34));
		int click_min = Integer.parseInt(time.substring(35, 37));
		
		int year = click_year - impression_year;
		int month = click_month - impression_month;
		int day = click_day - impression_day;
		int hour = click_hour - impression_hour;
		int min = click_min - impression_min;
		
		int interval = ((((year * 12 + month) * 30 +day) * 24 ) + hour ) * 60 + min ;
		
		return interval;		
	}
}
