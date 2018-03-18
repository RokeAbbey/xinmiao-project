package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Complexity extends DataProcessor{
	private int windowSize = 360;
	private int returnSize = 1;
	public Complexity() {
		// TODO Auto-generated constructor stub
		super();
		setScale(200);
	}
	@Override
	public List<Integer> process(){
		List<Integer> result = new ArrayList<>();
		List<Integer> list = getDataList();
		double x = getX(list);
		char[] cs = new char[list.size()];
		for(int i = 0;i < cs.length;i++)
			cs[i] = list.get(i) > x ?'1':'0';
		String ss = new String(cs);
//		System.out.println(ss+"   S Complexity line 18");
		if(ss.length() < 2)
			return result;
		int c = 1;
		String s = ss.substring(0,1);
		String qs = ss.substring(1,ss.length());
		String q = qs.substring(0,1);
		while(true){
			if(s.contains(q)){
				if(q.length() < qs.length())
					q = qs.substring(0,q.length() + 1);
				else{
					c++;
					break;
					}
			}
			else
			{
				s += q;
				c++;
				qs = qs.substring(q.length(),qs.length());
				if(qs.length() <= 0 )
					break;
				q = qs.substring(0,1);
			}
		}
//		System.out.println("c is "+c+"   Complexity line 44");
//		System.out.println("result is "+c * Math.log(windowSize) /windowSize+"  Complexity line 46");
		
		result.add((int)(getScale() * c * Math.log(windowSize) /windowSize));
		return result;
	}
	private double getX(List<Integer> list)
	{
		double sum = 0;
		for(int i : list)
			sum += i;
		return sum / list.size();
	}
	public static void main(String[] args) {
		Complexity c = new Complexity();
		c.setDataList(Arrays.asList(1,0,1,0,1,0,1,0));
		c.process();
	}
	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	public int getReturnSize() {
		return returnSize;
	}
	public void setReturnSize(int returnSize) {
		this.returnSize = returnSize;
	}
	
}
