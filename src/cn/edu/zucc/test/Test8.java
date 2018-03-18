package cn.edu.zucc.test;

import java.util.ArrayList;

public class Test8 {
	public static void main(String[] args) {
		ArrayList<Object> l = new ArrayList<>();
		for(int i = 3;i > 0;i--)
			l.add(g());
		ArrayList<Object> l2 = new ArrayList<>();
		for(int i = 3;i > 0;i--)
			l2.add(g2());
		System.out.println(l+"  \n"+l2);
	}
	static Object g(){
		final Object j = new Object();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j;
	}
	static Object g2(){
		Object j = new Object();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j;
	}
}
