package cn.edu.zucc.test;

import java.io.DataOutputStream;

public class Test10 {
	static Thread t = null;
	public static void main(String[] args) {
		 t = new Thread(){
			DataOutputStream o = null;
			@Override
			public void run(){
				System.out.println("line 8");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try {
						t = t.getClass().newInstance();
						t.start();
					} catch (InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
//		try {
//			t.join();
//			t = t.getClass().newInstance();//			t.start();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}
}
