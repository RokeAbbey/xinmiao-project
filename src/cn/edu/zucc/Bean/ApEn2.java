package cn.edu.zucc.Bean;
import java.util.List;
import java.util.ArrayList;
public class ApEn2 extends Approximate_entropy{
	public ApEn2(){
		setScale(400);
	}
	@Override
	public List<Integer> process(){
		List<Integer> list = getDataList();
		int size = list.size();
		double r = 0.2 * getS(list);
		//System.out.println("r = "+r);
//		int[][] a1 = new int[size][size];
//		int[][] a2 = new int[size][size];
//		int[][] d = new int[size][size];
		boolean[][] s = new boolean[size+1][size];
		int m = 2;
		//System.out.println("s = [");
		for(int i = 0;i < size;i++){
			for(int j = 0;j < size;j++)
			{
//				a1[i][j] = list.get(i);
//				a2[i][j] = list.get(j);
//				d[i][j] = list.get(i) - list.get(j);
				s[i][j] = Math.abs(list.get(i) - list.get(j)) <= r;
				//System.out.print((s[i][j]?1:0)+" ");
			}
			//System.out.println();
		}
		//System.out.println("]");
//		int[] temp = new int[size];
//		for(int i = 0;i < size;i++){
//			temp[i] = sum(s[i]);
//			//System.out.println("java sum_i of s = "+temp[i]);
//		}
//		System.out.println("java sum of s = "+sum(temp));
		double[] Cmr = new double[size - m + 1];
		double[] Cmr2 = new double[size - m + 1];
		for(int k = 0;k < size - m + 1;k++)
		{
			boolean[] Cmr_ij = pointMultForBoolean(s[k],s[k+1],0,size - 1,1);
			double Nm_i = sum(Cmr_ij);
//			int Nm_i = pointMultSumForBoolean(s[k],s[k+1],0,size - 1,1);
			Cmr[k] = Nm_i / (size - m + 1);
			double Nm_i2 = pointMultSumForBoolean(Cmr_ij,s[k+2],0,size - 2,2);
			Cmr2[k] = Nm_i2 / (size - m);
//			System.out.println("java Nm_i = "+Nm_i+" \njava Nm_i2 = "+Nm_i2);
		}
//		System.out.println("Cmr2[end] = "+Cmr2[size - m]);
		double l1 = 0,l2 = 0;
		for(int k = 0;k < size - m;k++)
		{
			l1 += Math.log(Cmr[k]);
			l2 += Math.log(Cmr2[k]);
		}
		l1 += Math.log(Cmr[size - m]);
		double phi1 = l1 / (size - m + 1);
		double phi2 = l2 / (size - m );
		List<Integer> resultList = new ArrayList<Integer>();
//		System.out.println("Scale : "+getScale());
//		System.out.println(getScale());
		resultList.add((int)(getScale() *(phi1 - phi2)));
		return resultList;
	}
	private static int pointMultSum(int[] a1,int[] a2,int start,int length,int offset){
		int result = 0;
		for(int i = 0;i < length;i++)
			result += a1[i] * a2[i+offset];
		return result;
	}
	private static int pointMultSumForBoolean(boolean[] a1,boolean[] a2,int start,int length,int offset){
		int result = 0;
		for(int i = 0;i < length;i++)
			result += a1[i]&&a2[i + offset] ? 1:0;
		return result;
	}
	private static boolean[] pointMultForBoolean(boolean[] a1,boolean[] a2,int start,int length,int offset){
		boolean[] result = new boolean[length];
		for(int i = 0;i < length;i++)
			result[i] = a1[i] &&a2[i+offset];
		return result;
	}
	private static int sum(boolean[] a){
		int result = 0;
		for(int i = 0;i < a.length;i++)
			result += a[i]?1:0;
		return result;
	}
	private static int sum(int[] a)
	{
		int result = 0;
		for(int i = 0;i < a.length;i++)
			result += a[i];
		return result;
	}
	public static void main(String[] args) {
		boolean[] a1 = {true,false,true,false,true,true};
		boolean[] a2 = {false,true,true,true,true,false};
		System.out.println("sum = "+sum(pointMultForBoolean(a1,a2,0,5,1)));
	}
}
