package cn.edu.zucc.Bean.rp;

import cn.edu.zucc.Uitl.Constant;

public class Reduction extends RawDatasProcessor<int[]>{//约分
	private int reference = Constant.THETA;
	private float scale	= 50;//1f/(50f*10000f)*10f;
	@Override
	public int[] process(Object ...ps){
		int[] sigs = new int[Constant.SIG_NUM];
		int[] pSigs = (int[])ps[0];
		for(int i = 0;i < Constant.DELTA;i++)
			sigs[i] = pSigs[i];
		for(int i = Constant.DELTA;i < Constant.SIG_NUM;i++){
			sigs[i] = (int)(Math.log10(pSigs[i] / (1.0 * pSigs[reference]) )* scale);
			System.out.println("sigs["+i+"] = "+sigs[i]+",pSigs["+i+"] = "+pSigs[i]);
		}
		return sigs;
	}
}
