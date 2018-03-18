package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.Uitl.Constant;

public class A_B_T_GSignalScaleProcessor extends DataProcessor{
	int k = 0;
	
	public A_B_T_GSignalScaleProcessor(){
		super.setWindowSize(1);
		super.setScale(100);
	}
	@Override
	public List<Integer> process(){
		List<Integer> result = getDataList();
		if(lastShowEqualCurrentSignalIndex())
		{
			int[] sigs = new int[Constant.SIG_NUM];
			boolean[] show = getShow();
			int	lastShow = getLastShow();
			int min = Integer.MAX_VALUE;
			List<List<Integer>>[] list = getMiddleDatasList();
			List<Integer> l = null;
			int currentIndex = getCurrentProcessorIndex();
			System.out.println("A_B_T_G : lastShow = "+lastShow+",currrentIndex = "+getCurrentSignalIndex());
			sigs[lastShow] = getDataList().get(0);

			int size = k++;
			if(list[lastShow].size() > 0)
				size = list[lastShow].get(currentIndex).size();
			System.out.println("size = "+size);
			System.out.print("A_B_T_G  before scale: ");
			for(int i = Constant.DELTA;i < lastShow;i++)
			{
//				if(list[i].size() <= 0)
//					continue;
				if(!show[i]) continue;
				l = list[i].get(currentIndex);
				sigs[i] = l.get(size);
				System.out.print("sigs["+i+"] = "+sigs[i]);
				if(sigs[i] < min)
					min = sigs[i];
			}
			System.out.println("sigs[lastShow] = "+sigs[lastShow]+" ");
			
			min = Math.min(min,sigs[lastShow]);
			if(min == 0)
				return result;
			System.out.println("A_B_T_G : min = "+min);
			System.out.print("A_B_T_G : ");
//			list[lastShow].get(currentIndex).add(0);
			for(int i = Constant.DELTA;i < lastShow;i++){
				if(!show[i]) continue;
				sigs[i] *= getScale();
				sigs[i] /= min;
				l = list[i].get(currentIndex);
				l.set(size,sigs[i]);
				
//				l.add(sigs[i]);
				System.out.print("l["+size+"] = "+l.get(size)+"   sigs["+i+"] = "+sigs[i]+" ");
			}
			{
				sigs[lastShow] *= getScale();
				sigs[lastShow] /= min;
			} 
			System.out.println();
			result = new ArrayList<>();
			result.add((int)(sigs[lastShow] * getScale()));
//			result.add(sigs[lastShow]);
		}
		else {
//			System.out.println("");
			k = 0;
		}
		
//OUT_78:
	System.out.println("result "+result);
		return result; 
	}
	public boolean lastShowEqualCurrentSignalIndex(){
		
		return getLastShow() == getCurrentSignalIndex();
	}
	
}
