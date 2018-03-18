package cn.edu.zucc.Bean.format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.zucc.Bean.exception.InvalidParametersForFormatClass;
import cn.edu.zucc.Uitl.Constant;

public class ConvertToLibSVM extends FormatConversion{
	 List<Integer>[] 					lists = null;
	 boolean[]							show = null;
	 File								file = null;
	 List<Integer>						sleep = null;
	 List<Integer>						asleep = null;
	@Override
	public synchronized Object process(Object... p) throws InvalidParametersForFormatClass, FileNotFoundException{
		// TODO Auto-generated method stub
		if(p.length <= 0 )
			throw new InvalidParametersForFormatClass("必须传递参数");
		lists = (List<Integer>[]) p[0];
		show = (boolean[])p[1];
		file = (File)p[2];
		sleep = (List<Integer>)p[3];
		asleep = (List<Integer>)p[4];
		StringBuffer sb = new StringBuffer();
		if(sleep != null && sleep.size() > 0 )
			getSB(sleep,Constant.SLEEP,sb);
			//			throw new InvalidParametersForFormatClass("sleep 不能为 null ，或size 为 0");
		if(asleep != null && asleep.size() > 0 )
			getSB(asleep,Constant.ASLEEP,sb);
			//			throw new InvalidParametersForFormatClass("asleep 不能为 null ，或size 为 0");
		PrintWriter pw = new PrintWriter(file);
		pw.write(sb.toString());
		pw.close();
		{
			String str = this.file.getPath();
			str = str.replace(".dat","_readme.dat");
			System.out.println(str+"   ConvertToLibSVM");
			File file = new File(str);
			pw = new PrintWriter(file);
			
			str = "signal : ";
			for(int i = 0;i < Constant.SIG_NUM;i++)
				if(show[i])
					str += Constant.SIG_NAME[i]+"  ";
			str+="\n";
			str+="sleep = "+sleep+", asleep = "+asleep;
			pw.write(str);
			pw.close();
		}
		return null;
	}
	public void getSB(List<Integer> l,boolean flag,StringBuffer sb ){
//		StringBuffer sb = new StringBuffer();
		int startIndex = l.get(0);
		int size = l.size();
		int finishIndex = size < 2?lists[0].size() - 1:l.get(1);
//		int k = 1;
		for(int i = l.get(0);i <= finishIndex;i++){
			String s = getSlpOrAslp(flag)+"\t";
			int k = 1;
			for(int j = 0;j < Constant.SIG_NUM;j++)
			{
				if(!show[j])continue;
				s += k+":"+lists[j].get(i)+"\t";
				k++;
			}
			s += "\n";
			sb.append(s);
		}
//		return sb;
	}
	
	public static void main(String[] args) throws InvalidParametersForFormatClass, FileNotFoundException {
		 List<Integer>[] 					lists = new List[Constant.SIG_NUM];
		 boolean[]							show = new boolean[Constant.SIG_NUM];
		 File								file = new File("./test/ConvertToLibSVM.dat");
		 List<Integer>						sleep = Arrays.asList(1,4);
		 List<Integer>						asleep = Arrays.asList(6,10);
		 
		 for(int i = 0;i < Constant.SIG_NUM;i++){
			 ArrayList<Integer> l = new ArrayList(1000);
			 for(int j = 0;j < 1000;j++)
			 {
				 l.add(i*100 + j);
			 }
			 lists[i]  = l;
		 }
//		 for(List<Integer> l :lists)
//			 System.out.println(l);
		 show[0] = show[5] = true;
		 new ConvertToLibSVM().process(lists,show,file,sleep,asleep);
	}
}
