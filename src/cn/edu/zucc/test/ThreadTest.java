package cn.edu.zucc.test;

public class ThreadTest {
	//请注意 守护线程在所有的用户线程（非守护线程）退出后会自动退出，mainThread是一个用户线程
	//在mainThread退出后 其余的用户线程可以继续运行，将下列的t.setDaemon(true)取消注释后，可以看到效果;
	static Thread t2;
	static Thread mainThread ;
	public static void main(String[] args) {
		
		Thread t = new Thread(){
			@Override
			public void run(){
//				t2 = new Thread(){
//						@Override
//						public void run(){
//							try {
//								for(int i = 0;i < 3;i++)
//								{
//									Thread.sleep(0);
//									System.out.println("i = "+i);
//									
//								}
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					};//.start();
//				t2.start();
				
				try {
					for(int i = 0;i < 5;i++){
						Thread.sleep(1000);
						System.out.println(" i = "+i+",  mainThread : "+mainThread.isAlive()+" ,t2 : "+t2.isAlive());
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
//		t.setDaemon(true);
		mainThread = Thread.currentThread();
		t.start();
		t2 = new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(3 * 1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};//.start();
		t2.start();
//		try {
//			t.join();
			System.out.println("t alive = "+t.isAlive());//+", t2 alive = "+t2.isAlive());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
