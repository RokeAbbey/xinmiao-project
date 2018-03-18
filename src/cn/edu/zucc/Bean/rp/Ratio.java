package cn.edu.zucc.Bean.rp;

import cn.edu.zucc.Uitl.Constant;

public class Ratio extends RawDatasProcessor<int[]>{
	private float scale = 400f;
	@Override
	public int[] process(Object ...ps){
		int[] pSigs = (int[])ps[0];
		int sum = 0;
		for(int i = Constant.THETA;i < Constant.SIG_NUM;i++)
			sum += pSigs[i];
		
		for(int i = Constant.THETA;i < Constant.SIG_NUM;i++)
			pSigs[i] = (int)(pSigs[i] * 1.0f /sum * scale);
		return pSigs;
	}
}
