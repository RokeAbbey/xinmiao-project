package cn.edu.zucc.Uitl;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame{
	DrawPanel dp = new DrawPanel();
	public Test() {
		// TODO Auto-generated constructor stub
		add(dp);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			while(true){
				Thread.sleep(1000);
				dp.repaint();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		try {
			TimerTask tt = new TimerTask() {
//				Socket socket = new Socket("192.168.1.105", 8000);
				Socket socket = new Socket("localhost", 8000);
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				Random r = new Random(1000);
				int number = 0;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int[] data = new int[Constant.SIG_NUM+1];
//					double[] num = {0.001,0.01,0.1,1,10,100,1000,10000,100000,0.0001};
					int[] num = {1,10,100,1000,10*1000,100*1000,50*1000,5000,500,50,5};
					number++;
					for(int i = 0;i < Constant.SIG_NUM;i++)
						data[i] = i*100;//data[i] = num[(i+number)%Constant.SIG_NUM];//(int)Math.pow(number, ((i - Constant.LOW_ALPHA)));//r.nextInt(500);
//					data[Constant.LOW_ALPHA] = 10 * 1000;
					data[Constant.SIG_NUM ] =(int)(System.currentTimeMillis()%(24 * 3600 * 1000));
					LinkedList<Integer> list = new LinkedList<>();
					for(int i = 0;i < 512;i++)
						list.addLast(i + number * 10);
					
					try {
						output.writeObject(data);
						output.writeObject(list);
//						System.out.println("output Test line 50");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
//						throw e;
					}
				}
			};
			timer.schedule(tt, 0, 3000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class DrawPanel extends JPanel{
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawString(System.currentTimeMillis()+"", getWidth()>>1, getHeight()>>1);
		}
	}
}
