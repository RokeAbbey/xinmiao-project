package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import com.mathworks.toolbox.javabuilder.*;
public class ApEn extends DataProcessor{
	int times = 0;
	public ApEn(){
		setWindowSize(1000);
		setScale(200);
	}
	public ApEn(List<Integer> list){
		this();
		setDataList(list);
	}
	public List<Integer> process(){
//		List<Integer> list = new ArrayList<>();
//		list.addAll(this.getDataList());
		List<Integer> list = null;
		Object[] result = null;
		int[] dims = {getWindowSize()};
		list = getDataList();
		int[] arrays = new int[dims[0]];
		for(int i = 0;i < dims[0];i++)
			arrays[i] = list.get(i);
		MWNumericArray x = MWNumericArray.newInstance(dims,arrays,MWClassID.DOUBLE);
		try {
			fast_ApEn.ApEn ae = new fast_ApEn.ApEn();
			result = ae.fast_ApEn(3,x,0.2);

//			List<Integer> resultList = new ArrayList()
			list = new ArrayList<Integer>();// = new ArrayList();
//			int index = 0;
//			for(Object i : result)
//				System.out.println(i.toString()+" index:"+(++index));
//				list.add((int)i);
//			System.out.println(result.length+"   line 41");
//			System.out.println(result[0].toString());
			Object rs= ((MWNumericArray)result[0]).toDoubleArray();
//			System.out.println(rs.getClass()+"   "+rs.toString());
			double[][] drs = (double[][])rs;
			list.add((int)((double)drs[0][0]*getScale()));
//			list.add((int)(((MWNumericArray)result[0]).toDoubleArray()[0]*getScale()));
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 MWArray.disposeArray(x);  
			 MWArray.disposeArray(result);
			 times++;
			 System.out.println(times+"  line 54");
		}
		return list;
	}
	public static void main(String[] args) {
		ApEn ae = new ApEn();
		Random r = new Random(16870);
		ae.setWindowSize(1000);
		List<Integer> list = new ArrayList<Integer>(ae.getWindowSize());
//		for(int i = 0;i < 1000;i++)
//			list.add(i);
		for(int i = 0;i < 1000;i++)
			list.add(r.nextInt(1000));
		ae.setDataList(list);
		long start = System.currentTimeMillis();
		List<Integer> resultList = ae.process();
		long fin = System.currentTimeMillis();
		System.out.println(resultList+"   \n  time gap:"+(fin - start));
//		Approximate_entropy ae2 = new Approximate_entropy();
		ApEn2 ae2 = new ApEn2();
		ae2.setDataList(list);
		ae2.setWindowSize(1000);
		start = System.currentTimeMillis();
		resultList = ae2.process();
		fin = System.currentTimeMillis();
		System.out.println(resultList+"   \n  time gap:"+(fin - start));
		
	}
}
