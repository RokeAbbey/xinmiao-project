package cn.edu.zucc.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
//解决实例方法内无法用匿名类的newInstance问题;
public class Test11 {
	Thread t = null;
	public Test11(){
		
//		try {
//			Class c = Class.forName("cn.edu.zucc.test.Test");
//			Test t = (Test) c.newInstance();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	
//		t = new Thread(){
//			@Override
//			public void run(){
//				System.out.println("line 14");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				finally{
//					try {
//						t = (Thread) Class.forName(t.getClass().getName()).newInstance();
//						t.start();
//					} catch (InstantiationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//			}
//		};
//		t.start();
		
	}
	public void test2(){
		try {
//			new Thread(){}.getClass().newInstance();
			Constructor[] cs = new Thread(){
				Object oa = null;
			}.getClass().getDeclaredConstructors();
			cs[0].setAccessible(true);
			Field[] fs = cs[0].newInstance(this).getClass().getDeclaredFields();
			for(Field f : fs)
			{
				f.setAccessible(true);
				System.out.println(f.getName());
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		test();
		new Test11().test2();
	}
	public static void test(){
		Thread t = new Thread(){
			@Override
			public void run(){}
		};
		System.out.println(t.getClass().getName()+" line 44");
	}
}
