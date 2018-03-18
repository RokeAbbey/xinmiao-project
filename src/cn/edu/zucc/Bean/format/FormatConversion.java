package cn.edu.zucc.Bean.format;

import java.io.FileNotFoundException;

import cn.edu.zucc.Bean.exception.InvalidParametersForFormatClass;
import cn.edu.zucc.Uitl.Constant;

public abstract class FormatConversion {
	public synchronized  Object process(Object... p) throws InvalidParametersForFormatClass, FileNotFoundException
	{
		return null;
	}
	public synchronized int getSlpOrAslp(boolean flag){
		int result = flag == Constant.SLEEP?Constant.SLEEP_INT:Constant.ASLEEP_INT;
//		System.out.println("result == "+result+",SLEEP == "+Constant.SLEEP+",ASLEEP == "+Constant.ASLEEP);
		return result;
	}
}
