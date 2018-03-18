package cn.edu.zucc.util.io;
import java.io.*;
import java.util.concurrent.*;
public class MyBufferedReader{
	LinkedBlockingQueue<String> strQueue = new LinkedBlockingQueue<>();
//	Reader in = null;
	
	public MyBufferedReader() {
//		super(in);
//		this.in = in;
		// TODO Auto-generated constructor stub
	}
//	@Override
	public String readLine() throws InterruptedException{
//		try {
		return strQueue.take();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
	}
	public void put(String str) throws InterruptedException{
		strQueue.put(str);
	}
	public void close() throws IOException{
//		in.close();
	}
	public static void main(String[] args) {
//		BufferedInputStream
		try {
			BufferedReader br = new BufferedReader(new FileReader("./trainfile/train1.txt"));
			final MyBufferedReader mbr = new MyBufferedReader();
			{
				new Thread(){
					@Override
					public void run(){
						while(true)
							try {
								System.out.println(mbr.readLine());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}.start();
			}
			String str = null;
			Thread.sleep(1000);
			while((str = br.readLine()) != null){
				mbr.put(str);
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
