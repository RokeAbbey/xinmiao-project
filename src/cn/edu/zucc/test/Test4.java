package cn.edu.zucc.test;
import java.util.ArrayList;
public class Test4 {
	public static void main(String[] args) {
		final ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		final Object list2 = list;
		Thread t1 = new Thread(){
//			Object list = null;
			@Override
			public void run(){
				try {
					Thread.sleep(1000);
					synchronized (list2) {
						System.out.println("thread 1");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		Thread t2 = new Thread(){
			@Override
			public void run(){
				synchronized (list) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("thread 2");
				}
			}
		};
		
		t1.start();
		t2.start();
	}
}
