package cn.edu.zucc.Uitl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Test3 {
	public static void main(String[] args){
		File file = new File("./data/usageData/preData/陈罗克17_5_2_白天似睡非睡.dat");
		File timeFile = new File("./data/time/陈罗克17_5_2_白天似睡非睡.dat");
		ObjectInputStream objectInputStream = null;
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream timeInputStream = null;
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 8000);
			objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			timeInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(timeFile)));
			List<Integer> timeList = (List<Integer>) timeInputStream.readObject();
			List<Integer>[] lists = (List<Integer>[]) objectInputStream.readObject();
			int size = lists[Constant.LOW_ALPHA].size();
			System.out.println("size = "+size+" line 25");
			for(int j = 0;j < size;j++){
				int[] data = new int[Constant.SIG_NUM + 1];
				for(int i = 0;i < Constant.SIG_NUM;i++){
					data[i] = lists[i].get(j);
				}
				data[Constant.SIG_NUM] = timeList.get(j);
				objectOutputStream.writeObject(data);
				objectOutputStream.flush();
				Thread.sleep(100);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(objectInputStream != null)
					objectInputStream.close();
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
