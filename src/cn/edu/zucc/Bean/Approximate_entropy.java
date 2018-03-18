package cn.edu.zucc.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

//import com.sun.org.apache.xpath.internal.operations.String;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Approximate_entropy extends DataProcessor{
	private int windowSize = 1000;
	private int returnSize = 1;
	public Approximate_entropy() {
		// TODO Auto-generated constructor stub
		super();
		setScale(200);
	}
	@Override
	public List<Integer> process(){
		int m = 2;
		int n = windowSize;
		double r = 0.2 * getS(getDataList());
//		System.out.println(r+"   AE line 19");
		List<Integer> x = getDataList();
		List<Integer> result = new ArrayList<>();
		boolean[][] d = new boolean[n][n];
		System.out.println(n+"  "+x.size()+"   ,line 35");
		for(int i = 0;i < n;i++)
			for(int j = 0;j < n;j++)
				d[i][j] = Math.abs(x.get(i) - x.get(j)) < r;
//		for(int i = 0;i < n;i++)
//		{
//			for(int j = 0;j < n;j++)
//				System.out.print((d[i][j]?1:0)+"  ");
//			System.out.println();
//		}
//		System.out.println("   AE line 34");
		double phy1 = 0,phy2 = 0;
//		54,60,66,63,99,67,5,78,44,130
		for(int i = 0;i < n - m + 1;i++)
		{
			double c1 = 0;
			for(int j = 0;j + m - 1 < n;j++)
			{
				if(j == i)
					continue;
				boolean flag = true;
				for(int k = 0;k < m;k++)
					flag =flag && d[i+k][j+k];//boolean 也可以 &= ? 
//				c1 += d[i][j] && d[i + 1][j + 1]?1:0;
				c1 += flag ?1:0;
			}
//			System.out.println(c1+"   AE line 42");
			c1 /= n - m;
			phy1 += Math.log(c1);
		}
//		System.out.println("phy1 = "+phy1+" ,n - m + 1 = "+(n - m + 1)+"   AE line 45");
		phy1 /= n - m + 1;
		m++;
		for(int i = 0;i < n - m + 1;i++)
		{
			double c2 = 0;
			for(int j = 0;j + m - 1 < n;j++)
			{
				if(j == i)
					continue;
				boolean flag = true;
				for(int k = 0;k < m ;k++)
					flag = flag && d[i + k][j + k];
				c2 += flag?1:0;
			}
			c2 /= n - m;
			phy2 += Math.log(c2);
		}
		phy2 /= n - m + 1;
//		double phy1 = process(m, r);
//		double phy2 = process(m + 1, r);
//		result.add((int)(phy1 - phy2));
//		System.out.println("phy1 = "+phy1+",phy2 = "+phy2+",  phy1 - phy2 = "+(phy1 - phy2)+"  AE line 66");
		result.add((int)((phy1 - phy2) * getScale()));
//		System.out.println(result.get(0)+"  AE line 78");
		return result;
	}

	private static double getX(List<Integer> list)
	{
		double sum = 0;
		for(int i : list)
			sum += i;
		return sum / list.size();
	}
	protected static double getS(List<Integer> list){
		double sum = 0;
		double x = getX(list);
//		System.out.println("X = "+x+"  AE line 107");
		for(int i : list)
			sum +=(i - x)*(i - x);
		return Math.sqrt(sum/(list.size() - 1));
	}
	
	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	public int getReturnSize() {
		return returnSize;
	}
	public void setReturnSize(int returnSize) {
		this.returnSize = returnSize;
	}
	public static void main(String[] args) {
		Approximate_entropy ahep = new Approximate_entropy();
		File file = new File("./data/Test.dat");
		boolean flag = !file.exists();
		PrintWriter pWriter = null;
		try {
			if(flag)
				pWriter = new PrintWriter(file);
			Random r = new Random(1000);
			ArrayList<Integer> list = new ArrayList<>();
			for(int i = 0,num = 0;i < 2000;i++)
			{
				list.add(num = r.nextInt(1000));
				if(flag)
					pWriter.println(num);
			}
			for(int i = ahep.getWindowSize();i < list.size();i++)
			{
				ahep.setDataList(list.subList(i - ahep.getWindowSize(), i));
				System.out.println(ahep.process()+"  "+(i - ahep.getWindowSize()+1));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(flag)
				pWriter.close();
		}
		
		
		
	}
	class Node implements Comparable<Node>{
		private int value;
		private int owner;
		public Node() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Node(int value, int owner) {
			super();
			this.value = value;
			this.owner = owner;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getOwner() {
			return owner;
		}
		public void setOwner(int owner) {
			this.owner = owner;
		}
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			if(this.value < o.value )
				return -1;
			if(this.value > o.value )
				return 1;
			return 0;
		}
		@Override
		public String toString(){
			return "value="+value+",owner="+owner;
		}
	}
	
}
