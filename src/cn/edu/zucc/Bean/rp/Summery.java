package cn.edu.zucc.Bean.rp;
import cn.edu.zucc.Uitl.*;
public class Summery extends Ratio{
	private float scale = 400f;
	int num = 0;
	@Override
	public int[] process(Object ...ps){
		int[] sigs = (int[])ps[0];//new int[Constant.SIG_NUM];
		if(sigs[Constant.SIG_NUM] <= 1)
			num = 0;
		for(int i : sigs)
			System.out.print(i+" ");
		System.out.println();
		System.out.println("theta : "+sigs[Constant.THETA]+",delta : "+sigs[Constant.DELTA]);
		int sumSleep = sigs[Constant.THETA] + sigs[Constant.DELTA];
		int sumAsleep = sigs[Constant.LOW_ALPHA] + sigs[Constant.HIGHT_ALPHA] + sigs[Constant.LOW_BETA] + sigs[Constant.HIGH_BETA]
							+sigs[Constant.LOW_GAMMA] + sigs[Constant.MID_GAMMA];
		for(int i = Constant.DELTA ;i < Constant.SIG_NUM;i++)
			sigs[i] = 0;
		sigs[Constant.LOW_ALPHA] = (int)(sumSleep * 1.0f /(sumSleep  + sumAsleep) * scale);
		sigs[Constant.HIGH_BETA] = (int)(sumAsleep * 1.0f / (sumSleep + sumAsleep) * scale);
		if(sigs[Constant.THETA] > sigs[Constant.HIGH_BETA])
			num++;
		System.out.println("sigs1 = "+sigs[Constant.THETA]+",sigs2 = "+sigs[Constant.HIGH_BETA]+",s > as :num = "+num);
		return sigs;
	}
}
