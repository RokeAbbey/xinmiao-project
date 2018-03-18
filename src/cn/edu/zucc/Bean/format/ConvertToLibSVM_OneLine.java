package cn.edu.zucc.Bean.format;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



//import 
import cn.edu.zucc.Bean.exception.InvalidParametersForFormatClass;
import cn.edu.zucc.Uitl.Constant;

public class ConvertToLibSVM_OneLine extends FormatConversion{
	private List<Integer> 					list;
	private List<Integer>[]					lists;
	private boolean[]						show;
	private boolean							flag;
	@Override
	public synchronized Object process(Object... p){
		/*if(p.length <= 0 )
			throw new InvalidParametersForFormatClass("必须传递参数");
		list = (List<Integer>) p[0];
		
		for(int i = 0;i < Constant.SIG_NUM;i++){
			lists[i] = new ArrayList<>(1);
			lists[i].add(list.get(i));
		}
		*/
		lists = (List<Integer>[]) p[0];
		show = (boolean[])p[1];
		flag = (boolean)p[2];
		String result = getSlpOrAslp(flag)+"\t";
//		int lastIndex = lists[0].size() - 1;'; 
		boolean f = true;
		int k = 0;
		for(int i = 0;i < Constant.SIG_NUM;i++){
			if(!show[i])continue;
			int lastIndex = lists[i].size() - 1;
			if(lastIndex < 0)
				continue;
			f = false;
			result += (++k)+":"+lists[i].get(lastIndex)+"\t";
		}
		System.out.println("f = "+f+"  ConvertToLine_OneLine");
		if(f)
			return null;
		result += "\n";
		return result;
	}
	public static void main(String[] args) throws InvalidParametersForFormatClass, FileNotFoundException {
		 List<Integer>[] 					lists = new List[Constant.SIG_NUM];
		 boolean[]							show = new boolean[Constant.SIG_NUM];
//		 File								file = new File("./test/ConvertToLibSVM.dat");
//		 List<Integer>						sleep = Arrays.asList(1,4);
//		 List<Integer>						asleep = Arrays.asList(6,10);
		 
		 for(int i = 0;i < Constant.SIG_NUM;i++){
			 ArrayList<Integer> l = new ArrayList(1000);
			 for(int j = 0;j < 1000;j++)
			 {
				 l.add(i*100 + j);
			 }
			 lists[i]  = l;
		 }
		 show[0] = show[5] = true;
		 ConvertToLibSVM_OneLine c = new ConvertToLibSVM_OneLine();//.process(lists,show,Constant.SLEEP);
		 for(int k = 0;k < 10;k++){
			 for(int i = 0;i < Constant.SIG_NUM;i++)
				 lists[i].add(k);
			 System.out.println(c.process(lists,show,Constant.SLEEP));
		 }
//		 for(List<Integer> l :lists)
//			 System.out.println(l);
		
	}
}
