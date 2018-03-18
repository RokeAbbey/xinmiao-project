package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

public class RemoveMaxAndMin extends DataProcessor{
	private int windowSize = 5;
	private int returnSize = windowSize - 2;
	@Override
	public List<Integer> process() {
		// TODO Auto-generated method stub
		int minIndex = 0;
		int maxIndex = 0;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
//		System.out.println(min + "   "+max+"   RemoveMaxAndMin line 13");
		List<Integer> list = new ArrayList<>(getDataList());
//		System.out.println(list+"  removeMaxAndMin");
		for(int i = 0;i < list.size();i++)
		{
			if(list.get(i) < min)
			{
				minIndex = i;
				min = list.get(i);
			}
			if(list.get(i) > max)
			{
				maxIndex = i;
				max = list.get(i);
			}
		}
//		System.out.println(list.size()+"   RemoveMaxAndMin line 29");
		if(list != null && list.size() > 0 )
		{
			list.remove(minIndex);
			if(minIndex < maxIndex)
				maxIndex --;
			if(list.size() > 0)
				list.remove(maxIndex);
		}
//		[2, 3, 4, 3, 4, 5, 4, 5, 6, 5, 6, 7, 6, 7, 8, 7, 8, 9]  DrawSercer line 179
//		System.out.println(list.size()+"   RemoveMaxAndMin line 37");
		for(int i = 0;i < list.size();i++){
			list.set(i, (int)(list.get(i)*getScale()));
		}
//		System.out.println(list+"  removeMaxAndMin");
		return list;
		//		return super.process();
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
