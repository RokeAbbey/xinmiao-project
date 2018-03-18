package cn.edu.zucc.test;

import java.util.Calendar;
//import java.util.
public class Test5 {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		String fileName = calendar.get(Calendar.YEAR)+"_"+(1 +calendar.get(Calendar.MONTH))+"_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"
									+calendar.get(Calendar.HOUR_OF_DAY)+"_"+calendar.get(Calendar.MINUTE)+"_"+calendar.get(Calendar.SECOND);
		System.out.println(fileName);
	}
}
