package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.Uitl.Constant;

public class ScaleLg extends DataProcessor{
	
	private int reference = Constant.LOW_ALPHA;
	private float scale = 20f;
	private List<Integer> referenceList = null;
	private int preShow = -1;
	private static final int UNKNOW = 0;
	private static final int IBI = 1;
	private static final int SBS = 2;
	private int state = UNKNOW;
	private int k = 0;
	private List<Integer> allReferenceDatas = new ArrayList<>();
	public ScaleLg(){
		setScale(scale);
	}
	public List<Integer> process(){
		List<Integer> result = null;
		List<List<Integer>>[] lists = getMiddleDatasList();
		int currentSignalIndex = getCurrentSignalIndex();
		int currentProcessorIndex = getCurrentProcessorIndex();
		if(state == IBI)
			result = processItemByItem();
		else if(currentSignalIndex < reference){
			result = processItemByItem();
		}
		else if(currentSignalIndex == reference){
			allReferenceDatas.addAll(getDataList());
			if(preShow == -1)
				result = processItemByItem();
			else{
				int processorsListSize = lists[preShow].size();
				result = new ArrayList<>();
//				if(processorsListSize <= 0){
//				List<Integer> list = getDataList();
//				for(int i : list)
//					result.add((int)getScale());
//				}
//				{
				int preShowSize = lists[preShow].get(currentProcessorIndex).size();
				int size = lists[currentSignalIndex].get(currentProcessorIndex).size();
				if(size + getReturnSize() == preShowSize){
					result = processItemByItem();
					if(state == UNKNOW)
						state = IBI;
				}
				else{
					List<Integer> list = getDataList();
					for(int i : list)
						result.add((int)getScale());
//						result = null;
					state = SBS;
				}
//					if(size == )
//				}
			}
		}
		else if(currentSignalIndex > reference){
//			int size = lists[currentSignalIndex].get()
			
		}
		preShow = currentSignalIndex;
		return result;
	}
	public List<Integer> processSigBySig(){
		List<Integer> result = null;
		return result;
	}
//	@Override
	public List<Integer> processItemByItem(){
		int currentSignalIndex = getCurrentSignalIndex();
		List<Integer> result = new ArrayList<>();
		if(currentSignalIndex >= reference)
		{
			List<Integer> list = getDataList();
			if(currentSignalIndex == reference)
			{
				referenceList = cloneList(list);
				List<List<Integer>>[] lists = getMiddleDatasList();
				int currentProcessorIndex = getCurrentProcessorIndex();
				for(int i = Constant.DELTA;i < reference;i++){
					System.out.println("i = "+i+",ScaleLg 29");
					if(lists[i].size() <= 0)continue;
					List<Integer> l = lists[i].get(currentProcessorIndex);
					int size = l.size();
//					System.out.println("size = "+size+",list.size = "+list.size()+"ScaleLg 31");
					l.set(size-1,(int)(Math.log10(l.get(size-1)*1.0/list.get(0)) * scale));
				}
				int size = list.size();
				for(int i = 0;i < size;i++)
					result.add((int)scale);
			}
			else {
//				int size = list.size();
				if(referenceList != null){
					int i = 0;
					for(int elem : list){
						result.add((int)(Math.log10(elem*1.0/referenceList.get(i++)) * scale));
					}
				}
				else{
//					throw new Exception("");
					System.err.println("low_alpha必须要显示");
					return null;
				}
			}
		}
		else if(currentSignalIndex >= Constant.DELTA){
			result = getDataList();
		}
		return result;
	}
	private List<Integer> cloneList(List<Integer> l){
		List<Integer> list = new ArrayList<>(getWindowSize());
		for(int i : l)
			list.add(i);
		return list;
	}
}
