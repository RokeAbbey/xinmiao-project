package cn.edu.zucc.test;

public class Test6 {
	int series = 0;
	public static void main(String[] args) {
		Test6 t = new Test6();
		t.test();
		t.test();
		t.test();
	}
	public void test() {
		new Thread(){
			@Override
			public void run(){
				final int r = (int)(Math.random() * 100);
				try {
					for(int i = 0;i < 3;i++)
					{
						System.out.println("r = "+r+", series = "+series);
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}.start();
	}
}
