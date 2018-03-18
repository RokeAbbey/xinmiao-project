package cn.edu.zucc.Uitl;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zucc.Bean.format.ConvertToLibSVM;
import cn.edu.zucc.Bean.format.ConvertToLibSVM_OneLine;
import cn.edu.zucc.Bean.format.FormatConversion;

//import com.sun.javafx.collections.MappingChange.Map;

public abstract class Constant {
	//代表各个信号
	public static int RAW_DATA  = 0;
	public static int ATTENTION = 1;
	public static int MEDITATION = 2;
	public static int DELTA = 3;
	public static int THETA = 4;
	public static int LOW_ALPHA = 5;
	public static int HIGHT_ALPHA = 6;
	public static int LOW_BETA = 7;
	public static int HIGH_BETA = 8;
	public static int LOW_GAMMA = 9;
	public static int MID_GAMMA = 10;
	
	public static int SIG_NUM = 11;//信号种数
	
	public static String[] SIG_NAME = {"raw","attention","meditation","delta","theta","low-alpha","high-alpha"
							,"low-beta","high-beta","low-gamma","mid-gamma"};//信号名称;
	public static Map<String, Color> colorMap = new HashMap<>();
	static {
		colorMap.put(SIG_NAME[0], Color.BLUE);
		colorMap.put(SIG_NAME[1], Color.CYAN);
		colorMap.put(SIG_NAME[2], Color.PINK);
		colorMap.put(SIG_NAME[3], new Color(0x795543));//Color.GRAY);
		colorMap.put(SIG_NAME[4], Color.green);
		colorMap.put(SIG_NAME[5], Color.LIGHT_GRAY);
		colorMap.put(SIG_NAME[6], new Color(0xea80fc));//Color.MAGENTA);
		colorMap.put(SIG_NAME[7], Color.ORANGE);
		colorMap.put(SIG_NAME[8], Color.RED);
		colorMap.put(SIG_NAME[9], Color.WHITE);
		colorMap.put(SIG_NAME[10], Color.YELLOW);
//		colorMap.put("timeLineColor",);
	}
	public static final Color timeLineColor = new Color(123,234,123);
	public static final boolean SLEEP = false;
	public static final boolean ASLEEP = true;
	public static final float	SLEEP_FLOAT =  SLEEP?1.0f:0.0f;
	public static final float 	ASLEEP_FLOAT = 1.0f - SLEEP_FLOAT;
	public static final int		SLEEP_INT = (int)SLEEP_FLOAT;
	public static final int 	ASLEEP_INT = (int)ASLEEP_FLOAT;
	public static final FormatConversion ctls = new ConvertToLibSVM();
	public static final FormatConversion ctls_ol = new ConvertToLibSVM_OneLine();
	
	public static final File	beforeScaledFile = new File("./data/scale/beforeScaledFile.dat");
	public static final File	afterScaledFile = new File("./data/scale/afterScaledFile.dat");
	public static final String	scaleModel = "./data/train/train/Reduction2/trainData/synscale.dat";
	public static final String	trainModel = "./data/train/train/Reduction2/trainData/synthesizeScaledModel2.txt";
}
