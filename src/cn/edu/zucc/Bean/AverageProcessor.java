package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

public class AverageProcessor extends DataProcessor{
	private int windowSize = 5;
	private int returnSize = 1;
	
	@Override
	public List<Integer> process() {
		// TODO Auto-generated method stub
//		if()
		List<Integer>list = getDataList();
//		System.out.println(list +"   Average line 12");
		if(list == null || list.size() <= 0)
			return list;
		int sum = 0;
		for(Integer i : list)
			sum += i;
		sum /= list.size();
		list = new ArrayList<>();
		list.add((int)(sum * getScale()));
//		System.out.println(list+"  average ");
		return list; 
	}
	@Override
	public int getWindowSize() {
		return windowSize;
	}
	@Override
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	@Override
	public int getReturnSize() {
		return returnSize;
	}
	@Override
	public void setReturnSize(int returnSize) {
		this.returnSize = returnSize;
	}
	
	
}
