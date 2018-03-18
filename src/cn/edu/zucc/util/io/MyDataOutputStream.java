package cn.edu.zucc.util.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;

import cn.edu.zucc.Uitl.Constant;

public class MyDataOutputStream {
	private LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<String>();
//	OutputStream out = null;
	public MyDataOutputStream() {
//		super(out);
		// TODO Auto-generated constructor stub
//		this.out = out;
	}
	public void writeBytes(String s) {
		try {
			lbq.put(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public float takeFloat() throws NumberFormatException, InterruptedException{
		return Float.parseFloat(take());
	}
	public boolean takeBoolean() throws NumberFormatException, InterruptedException{
		return Float.parseFloat(take()) == Constant.SLEEP_FLOAT?Constant.SLEEP:Constant.ASLEEP;
	}
	public String take() throws InterruptedException{
//		try {
		return lbq.take();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
	}
	public int takeInt() throws NumberFormatException, InterruptedException{
		return Integer.parseInt(String.valueOf(take().charAt(0)));
	}
	public void close(){}
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		BufferedReader b = null;
		MyDataOutputStream mb = null;
		try {
			final BufferedReader br = new BufferedReader(new FileReader("./trainfile/train1.txt"));
			final MyDataOutputStream mbr = new MyDataOutputStream();
			b = br;
			mb = mbr;
			{
				new Thread(){
					@Override
					public void run(){
						String str = null;
						try{
							while((str = br.readLine()) != null){
								Thread.sleep(1000);
								mbr.writeBytes(str);
							}
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
			}
//			String str = null;
//			Thread.sleep(1000);
//			while((str = br.readLine()) != null){
//				mbr.put(str);
//			}
			int i = 0;
			while(true){
				System.out.println(mbr.takeInt()+"  "+(++i));
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
			try {
				if(b != null)
					b.close();
				if(mb != null)
					mb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
