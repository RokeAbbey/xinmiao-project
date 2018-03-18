package cn.edu.zucc.Bean;

import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
	private List<Integer> dataList = null;
	private int windowSize = 1;
	private int returnSize = 1;
	private float scale = 1;
	private int currentSignalIndex = -1;
	private int currentProcessorIndex = -1;
	private int lastShow = -1;
	private boolean[] show;
	private List<List<Integer>>[] middleDatasList = null;
	public DataProcessor() {
		// TODO Auto-generated constructor stub
	}
	public DataProcessor(List<Integer> dataList) {
		// TODO Auto-generated constructor stub
		this.dataList = dataList;
	}
	public List<Integer> process(){
		List<Integer> list = new ArrayList<>();
		list.addAll(this.dataList);
		return list;
	}
	public List<Integer> getDataList() {
		return dataList;
	}
	public void setDataList(List<Integer> dataList) {
		this.dataList = dataList;
	}
	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	public int getReturnSize() {
		return returnSize;
	}
	public void setReturnSize(int returnSize) {
		this.returnSize = returnSize;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public int getCurrentSignalIndex() {
		return currentSignalIndex;
	}
	public void setCurrentSignalIndex(int currentSignalIndex) {
//		System.out.println("currentSignalIndex = "+currentSignalIndex);
//		if(currentSignalIndex == 10)
//			new Exception().printStackTrace();
		this.currentSignalIndex = currentSignalIndex;
	}
	public int getCurrentProcessorIndex() {
		return currentProcessorIndex;
	}
	public void setCurrentProcessorIndex(int currentProcessorIndex) {
		this.currentProcessorIndex = currentProcessorIndex;
	}
	public int getLastShow() {
		return lastShow;
	}
	public void setLastShow(int lastShow) {
		this.lastShow = lastShow;
	}
	public List<List<Integer>>[] getMiddleDatasList() {
		return middleDatasList;
	}
	public void setMiddleDatasList(List<List<Integer>>[] middleDatasList) {
		this.middleDatasList = middleDatasList;
	}
	public boolean[] getShow() {
		return show;
	}
	public void setShow(boolean[] show) {
		this.show = show;
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
}
