package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

public class Scale extends DataProcessor{
	private float scale = 20;//1f/(50f*10000f)*10f;//0.000008f;
	public Scale(){
		setScale(scale);
	}
	@Override
	public List<Integer> process(){
		List<Integer> list = new ArrayList<>();
		for(Integer i : getDataList())
			list.add((int)(scale * i * 1.8 / 4090 / 2000));
		return list;
	}
}
