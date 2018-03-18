package cn.edu.zucc.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.edu.zucc.Bean.DataProcessor;
import cn.edu.zucc.Bean.exception.InvalidParametersForFormatClass;
import cn.edu.zucc.Bean.format.FormatConversion;
import cn.edu.zucc.Bean.rp.RawDatasProcessor;
import cn.edu.zucc.Bean.rp.Reduction;
import cn.edu.zucc.Uitl.Constant;
import cn.edu.zucc.Uitl.VerticalLayout;
import cn.edu.zucc.ui.DrawServer3.MainFrame.DrawLine;
//import jav
import cn.edu.zucc.util.io.MyBufferedReader;
import cn.edu.zucc.util.io.MyDataOutputStream;
import service.svm_predict;
import service.svm_train;
import service.svm_scale;
public class DrawServer3{
//	public static final int DEFAULT_RowWindowSize = 1;
//	public static final int DEFAULT_AttentionWindowSize = 1;
//	public static final int DEFAULT_MeditationWindowSize = 1;
//	public static final int DEFAULT_DeltaWindowSize = 1;
//	public static final int DEFAULT_ThetaWindowSize = 1;
//	public static final int DEFAULT_Low_alphaWindowSize = 1;
//	public static final int DEFAULT_High_alphaWindowSize = 1;
//	public static final int DEFAULT_Low_betaWindowSize = 1;
//	public static final int DEFAULT_High_betaWindowSize = 1;
//	public static final int DEFAULT_Low_gammaWindowSize = 1;
//	public static final int DEFAULT_Mid_gammaWindowSize = 1;
	private ServerSocket serverSocket = null;
	private Socket		 socket		  = null;
	private static List<DrawLine> drawLineList =  new ArrayList<>();  
	private List<Integer>[] rawDatasList = (ArrayList<Integer>[])new ArrayList[Constant.SIG_NUM];
	private List<Integer> datasTime = new ArrayList<>();
	private List<Integer> rawDataTime = new ArrayList<>();
//	private svm_train train = new svm_train();
//	private svm_predict predict = new svm_predict();
	//<Test>
	{
//		List<Integer> list = new ArrayList<>();
//		Random rd = new Random(1000);
//		for(int i = 1;i <= 1000;i++)
//			list.add(rd.nextInt(100));
		for(int i = 0;i < Constant.SIG_NUM;i++)
		{
			rawDatasList[i] = new ArrayList<>();
//			rawDatasList[i].addAll(list);
		}
		
	}
	
	//</Test>
	public DrawServer3() {
		// TODO Auto-generated constructor stub
		new MainFrame(rawDatasList);
		try {
			serverSocket = new ServerSocket(8000);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("thread run ! line 73");
//					Socket socket = null;
					while(true){
						try {
							socket = serverSocket.accept();
							System.out.println("connected!!");
							ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
							int[] data = null;
							System.out.println(input.available()+"  input available DrawServer line 79");
							while(true)
							{
//								data =(int[])input.readObject();
								Object o = input.readObject();
//								int size = 1;
//								synchronized (rawDatasList) {//发送的顺序是先各个频段 接下来才是raw 
									if(o instanceof int[]){
										data = (int[])o;
										synchronized (rawDatasList) {
											for(int i = Constant.ATTENTION ;i < Constant.SIG_NUM;i++)
												rawDatasList[i].add(data[i]);
										}
										for(DrawLine d:drawLineList)
											d.addItemIntoProcessedDataList();
										synchronized (datasTime) {
											datasTime.add(data[Constant.SIG_NUM]);
//											System.out.println(data[0]+"  "+data.length+"  line 85");
										}
									}
									else{
										LinkedList<Integer> list = (LinkedList<Integer>)o;
										int 				size = list.size();
										{
											int drawLineListSize = drawLineList.size();
											boolean[] flags = new boolean[drawLineListSize];
											synchronized (datasTime) {
												for(int i = 0;i < size;i++)
													rawDataTime.add(data[Constant.SIG_NUM]);
											}
											for(int i = 0;i < drawLineListSize;i++)
												flags[i] = drawLineList.get(i).show[Constant.RAW_DATA];
											for(int i = 0;i < size;i++){
												synchronized (rawDatasList) {
													rawDatasList[Constant.RAW_DATA].add(list.poll());	
												}
												for(int j = 0;j < drawLineListSize;j++)
													if(flags[j])
														drawLineList.get(j).addItemIntoProcessedDataList();
											}
										}
									}
//								}
								
								
								
//								datasTime.add(data[Constant.SIG_NUM]);
////								System.out.println(data[0]+"  "+data.length+"  line 85");
//								for(DrawLine d:drawLineList)
//									d.addItemIntoProcessedDataList();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
							try {
								if(socket != null)
									socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
			
			
			Timer timer = new Timer();
			TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					Date date = new Date();
					// TODO Auto-generated method stub
					Calendar calendar = Calendar.getInstance();
//					Calendar calendar = Calendar.getInstance();
					String fileName = calendar.get(Calendar.YEAR)+"_"+(1 +calendar.get(Calendar.MONTH))+"_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"
												+calendar.get(Calendar.HOUR_OF_DAY)+"_"+calendar.get(Calendar.MINUTE)+"_"+calendar.get(Calendar.SECOND);
					File file = new File("./data/sleep"+fileName+".dat");
					File file2 = new File("./data/time/sleep"+fileName+".dat");
					ObjectOutputStream objectOutput = null;
					ObjectOutputStream objectOutput2 = null;
					try {
						objectOutput = new ObjectOutputStream(new FileOutputStream(file,false));
						synchronized(rawDatasList){
							objectOutput.writeObject(rawDatasList);
						}
//						if(datasTime != null)
						{
							objectOutput2 = new ObjectOutputStream(new FileOutputStream(file2,false));
							synchronized (datasTime) {
								objectOutput2.writeObject(datasTime);
							}
						}
//						objectOutput.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						File log = new File("./log/log"+fileName+".dat");
						try {
							objectOutput.close();
							objectOutput = new ObjectOutputStream(new FileOutputStream(log,true));
							StackTraceElement[] stackTraceElements = e1.getStackTrace();
							for(StackTraceElement e : stackTraceElements)
								objectOutput.writeChars(e.toString());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					finally {
						try {
							if(objectOutput != null)
								objectOutput.close();
							if(objectOutput2 != null)
								objectOutput2.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			timer.schedule(tt, 10*60*1000,10*60*1000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//<Test>
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
//				try {
//					Random rd = new Random(1000);
//					int v = 0;
//					for(int i = 0;i < 1000;i++){
//						synchronized (rawDatasList) {
//							for(int j = 0;j < Constant.SIG_NUM;j++){
//								rawDatasList[j].add(v = rd.nextInt(100));
//								System.out.println(v+"  DrawServer line 75");
//							}
//						}
//						for(int j = 0;j < drawLineList.size();j++)
//							drawLineList.get(j).addItemIntoProcessedDataList();
//						Thread.sleep(1000);
//					}
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}).start();
		//</Test>
		
	}
	public List<Integer>[] getRawDatasList() {
		return rawDatasList;
	}
	public void setRawDatasList(List<Integer>[] rawDatasList) {
		this.rawDatasList = rawDatasList;
	}
	public static void main(String[] args) {
		DrawServer3 ds = new DrawServer3();
		
	}
	 class MainFrame extends JFrame{
		private JPanel mainPanel = new JPanel();
		private JButton addDrawPanelButton = new JButton("添加绘图面板"); 
		private JPanel headPanel = new JPanel();
		private JPanel centerPanel = new JPanel();
		private JScrollPane jScrollPane = null;
		

		private JFileChooser jfc = new JFileChooser("./data");
		private JMenuBar jmb = new JMenuBar();
		private JMenu jm = new JMenu("文件");
		private JMenuItem openJMItem = new JMenuItem("打开");
		private JMenuItem saveJMItem = new JMenuItem("保存");
		
		private List<Integer>[] rawDatasList = null;
		private List<DrawLine> drawLines = new ArrayList<>(); 
//		private DrawLine drawLinePanel = new DrawLine();
		private Util util = new Util();
		public MainFrame(List<Integer>[] rawDatasList){
			this.rawDatasList = rawDatasList;
			jScrollPane = new JScrollPane(centerPanel);
			add(jScrollPane);
			centerPanel.setLayout(new VerticalLayout());
			addDrawPanelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					centerPanel.add( util.createDrawPanel(centerPanel,MainFrame.this.rawDatasList,drawLines,datasTime));
					/**********************/
					centerPanel.revalidate();
					centerPanel.repaint();
					/******************************/
					
				}
			});
			setJMenuBar(jmb);
			jmb.add(jm);
			jm.add(openJMItem);
			jm.add(saveJMItem);
			openJMItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						List<Integer>[] datas =null;// new ArrayList[Constant.SIG_NUM];
						File file = jfc.getSelectedFile();
						String file2Name = "./data/time/"+file.getName();
						File file2 = new File(file2Name);
						System.out.println(file2+"  exists : "+file2.exists());
						System.out.println("abslute : file1:"+file.getAbsolutePath()+",file2:"+file2.getAbsolutePath());
						ObjectInputStream objectInputStream  = null;
						ObjectInputStream objInput = null;
						try {
							objectInputStream = new ObjectInputStream(new FileInputStream(file));
							datas = (List<Integer>[]) objectInputStream.readObject();
							System.out.println("data length : "+datas[0].size()+"   line 259 DrawServer");
							for(int i = 0;i < Constant.SIG_NUM;i++)
							{
								DrawServer3.this.rawDatasList[i].clear();
								DrawServer3.this.rawDatasList[i].addAll(datas[i]);
							}
							if(file2.exists()){
								objInput = new ObjectInputStream(new FileInputStream(file2));
								DrawServer3.this.datasTime.clear();
								DrawServer3.this.datasTime.addAll((List<Integer>)objInput.readObject());
//								{
//									System.out.println(DrawServer3.this.rawDatasList[0].size()+"  openJ raw");
//									System.out.println(DrawServer3.this.datasTime.size()+"   openJ time");
//								}
							}
							for(DrawLine dl : drawLineList)
								dl.repaintRequest();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						finally{
							try {
								if(objectInputStream != null)
									objectInputStream.close();
								if(objInput != null)
									objInput.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			saveJMItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						File file = jfc.getSelectedFile();
						ObjectOutputStream objectOutput = null;
						ObjectOutputStream objOutput = null;
						try {
							objectOutput = new ObjectOutputStream(new FileOutputStream(file,false));
							objectOutput.writeObject(DrawServer3.this.rawDatasList);
							String file2Name = "./data/time/"+file.getName();
							File file2 = new File(file2Name);
//							{
//								System.out.println(file2+"  exists : "+file2.exists());
//								System.out.println("abslute : file1:"+file.getAbsolutePath()+",file2:"+file2.getAbsolutePath());
//							}
							objOutput = new ObjectOutputStream(new FileOutputStream(file2,false));
							objOutput.writeObject(DrawServer3.this.datasTime);
						} catch (IOException e1){
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						finally{
							try {
								if(objectOutput != null)
									objectOutput.close();
								if(objOutput != null)
									objOutput.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			headPanel.add(addDrawPanelButton);
			add(headPanel,BorderLayout.NORTH);
//			add(centerPanel);
			centerPanel.setBackground(new Color(0, 100, 100));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			centerPanel.add(new JLabel("hello"));
			pack();
			setVisible(true);
		}
		 class DrawLine extends JPanel{
//			private boolean showRawLine;
//			private boolean showAttention;
//			private boolean showMeditation;
//			private boolean showDelta;
//			private boolean showTheta;
//			private boolean showLow_alpha;
//			private boolean showHigh_alpha;
//			private boolean showLow_beta;
//			private boolean showHigh_beta;
//			private boolean showLow_gamma;
//			private boolean showMid_gamma;
			public static final int DEFAULT_HEIGHT = 800;
			
			boolean[] show = new boolean[Constant.SIG_NUM];
			{
				for(int i = 0;i < show.length;i++)
					show[i] = true;
			}
//			private int rowWindowSize = DEFAULT_RowWindowSize;
//			private int attentionWindowSize = DEFAULT_AttentionWindowSize;
//			private int meditationWindowSize = DEFAULT_MeditationWindowSize;
//			private int deltaWindowSize = DEFAULT_DeltaWindowSize;
//			private int thetaWindowSize = DEFAULT_ThetaWindowSize;
//			private int low_alphaWindowSize = DEFAULT_Low_alphaWindowSize;
//			private int high_alphaWindowSize = DEFAULT_High_alphaWindowSize;
//			private int low_betaWindowSize = DEFAULT_Low_betaWindowSize;
//			private int high_betaWindowSize = DEFAULT_High_betaWindowSize;
//			private int low_gammaWindowSize = DEFAULT_Low_gammaWindowSize;
//			private int mid_gammaWindowSize = DEFAULT_Mid_gammaWindowSize;
			
//			private int[] windowsSize = new int[Constant.SIG_NUM]; 
			private JButton chooseAlgothrimButton = new JButton("选择处理算法");
//			private JDialog sortDialog = null;
			private List<Integer>[] proccessedDataList =  (ArrayList<Integer>[])new ArrayList[Constant.SIG_NUM];
//			private JScrollBar jScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 100);
			{
				for(int i = 0;i < Constant.SIG_NUM ; i++)
				{
//					rawDatasList[i] = new ArrayList<>();
					proccessedDataList[i] = new ArrayList<>();
				}
			}
			private List<Integer>[] rawDatasList = null;
			private List<Integer>[] myRawDatasList = null;
			private List<Integer> datasTime = null;//这里由于需要clone所以还是用ArrayList 不用List
			private List<DataProcessor> dataProcessorsList = new ArrayList<>();
			private List<List<Integer>>[] middleDatasList = new ArrayList[Constant.SIG_NUM];
			private List<List<Integer>> middleDatasTimeList = new ArrayList<>();
			private JLabel timeLabel = null;
			private int[] indexs = new int[0];
			private int mouseXCoordination = 0;
			private int lastIndex = -1;
			private int index = 0;
			private List<Integer> sleep = new ArrayList<>();
			private List<Integer> asleep = new ArrayList<>();
			private JRadioButton slpRBt = null;//new JRadioButton("sleep");
			private JRadioButton aslpRBt = null;//new JRadioButton("asleep");
//			private ButtonGroup buttonGroup = new ButtonGroup();
			private File outputFile = null;//存放刚才“导出数据”的文件
			private svm_predict predict = new svm_predict();
			private svm_train train = new svm_train();
			private svm_scale scale = new svm_scale();
			private boolean startPredict = false;
			private JTextArea jta = null;
		
			private void middleDatasListInitial()
			{
				int size = dataProcessorsList.size();
				for(int i = 0;i < Constant.SIG_NUM;i++)
				{
					List<List<Integer>> list = new ArrayList<>();
					middleDatasList[i] = list;
					for(int k = 0;k < size;k++)
						middleDatasList[i].add(new ArrayList<Integer>());
				}
				for(int i = 0;i < size;i++)
					middleDatasTimeList.add(new ArrayList<Integer>());
			}
			
			public JScrollBar jScrollBar = null;
			public static final int X_ALIGN = 10;
			public static final int Y_ALIGN = DEFAULT_HEIGHT >> 1;//- 10;//
			public static final int X_STEP_LENGTH = 4;
			public static final int WINDOW_LENGTH = Util.SCROLLBAR_MAXIMUM;
			public static final int AMPLIFICATION_NUM = 2;
			public static final boolean SLEEP = Constant.SLEEP;
			public static final boolean ASLEEP = Constant.ASLEEP;
			private boolean slpOrAslp = ASLEEP;
			public DrawLine(List<Integer>[] rawDatasList,JScrollBar jScrollBar,List<Integer> datasTime,JLabel timeLabel) {
				// TODO Auto-generated constructor stub
				
				this.rawDatasList = rawDatasList;
				myRawDatasList = new List[Constant.SIG_NUM];
				for(int i = 0;i < Constant.SIG_NUM;i++)
					myRawDatasList[i] = new ArrayList<>();
//				this.proccessedDataList = proccessedDataList;
				this.jScrollBar = jScrollBar;
				this.datasTime = datasTime;//(ArrayList<Integer>) ((ArrayList<Integer>) datasTime).clone();
				this.timeLabel = timeLabel;
				System.out.println(rawDatasList[0]+"   DrawServer3 line 158");
				middleDatasListInitial();
//				for(int i = 0;i < windowsSize.length;i++)
//					windowsSize[i] = 5;
				chooseAlgothrimButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dataProcessorsList = new ArrayList<>();
						new SortDialog(null,"选择处理算法",true,dataProcessorsList);
						repaintRequest();
					}
				});
				setBackground(Color.BLACK);
//				add(jScrollBar);
//				System.out.println(dataProcessorsList.size()+"   DrawServer3 line 131");
				addMouseMotionListener(new MouseMotionAdapter(){
					@Override
					public void mouseMoved(MouseEvent e){
						mouseXCoordination = e.getX() - X_ALIGN;
						index = mouseXCoordination / X_STEP_LENGTH;
						if(index != lastIndex && index >= 0 )
						{
							lastIndex = index;
							List<Integer> list = null;
//							System.out.println("middleDatasTimeList.size() = "+middleDatasTimeList.size());
							if(middleDatasTimeList.size()==0)
								list = show[Constant.RAW_DATA]?rawDataTime:DrawLine.this.datasTime;
							else
								list = middleDatasTimeList.get(middleDatasTimeList.size() - 1);
							int size = list.size();
//							System.out.println("list.size = "+size);
							if(size <= 0)
								return;
							if(index >= size)
								index = size - 1;
//							System.out.println("size : "+size+" , index : "+index+" , leftIndex : "+leftIndex+" ,rightIndex : "+rightIndex);
							DrawLine.this.timeLabel.setText(util.getTime(list.get(leftIndex + index)));
							DrawLine.this.repaint();
						}
//						System.out.println("mouse coordination  x:"+x);
					}
				});
				addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
						if(slpOrAslp == SLEEP)
							sleep.add(index + leftIndex);
						else
							asleep.add(index + leftIndex);
						System.out.println("sleep : "+sleep);
						System.out.println("asleep : "+asleep);
					}
				});
//				buttonGroup.add(slpRBt);
//				buttonGroup.add(aslpRBt);
				
				repaintRequest();
			}
			private RawDatasProcessor<int[]> rDataProcessor = new Reduction();//new Ratio();//new Summery();//
//			private 
			private synchronized void preProcessRawdatas(int index){
				int[] sigs = new int[Constant.SIG_NUM + 1];
				
//				int size = rawDatasList[Constant.LOW_ALPHA].size();
				for(int i = 0;i < Constant.SIG_NUM;i++){
					int size = myRawDatasList[i].size();
					sigs[Constant.SIG_NUM] = index == -1?size - 1:index;
					if(size > 0)
						sigs[i] = myRawDatasList[i].get(index == -1?size - 1:index);
				}
				sigs = rDataProcessor.process(sigs);
				for(int i = 0;i < Constant.SIG_NUM;i++){
					int size = myRawDatasList[i].size();
//					System.out.println("i = "+i+",size = "+size+", DrawServer 602");
					if(size > 0)
						myRawDatasList[i].set(index == -1?size - 1:index,sigs[i]);
				}
//					sigs[i] = rawDatasList[i].get(size - 1);
			}
			public void addItemIntoProcessedDataList(){
				synchronized (myRawDatasList) {
					for(int i = 0;i < Constant.SIG_NUM;i++)
					{
						int size = rawDatasList[i].size();
						if(size > 0)
							myRawDatasList[i].add(rawDatasList[i].get(size - 1));
					}
					preProcessRawdatas(-1);
				}
				int size = dataProcessorsList.size();
//				System.out.println("addItem before synchronize!");
				for(int i = 0 ;i < Constant.SIG_NUM;i++)
				{
					if(!show[i])
						continue;
					List<Integer> list = myRawDatasList[i];
//					System.out.println("myRawDatasList[i].last = "+myRawDatasList[i].get(myRawDatasList[i].size() - 1));
					synchronized (dataProcessorsList) {	
						int num = 1;
			LOOP421:	for(int j = 0;j < size;j++)
						{
							if(j == 0){
								DataProcessor dataProcessor = dataProcessorsList.get(0);
								list = myRawDatasList[i];
								if(list.size() - dataProcessor.getWindowSize() < 0){
									num = 1;
									break LOOP421;
									}
								list =myRawDatasList[i].subList(list.size() - dataProcessor.getWindowSize(),list.size());
//								System.out.println("list = "+list+"  DrawServer3 line 515");
								dataProcessor.setDataList(list);
								dataProcessor.setCurrentSignalIndex(i);
								dataProcessor.setCurrentProcessorIndex(0);
								dataProcessor.setMiddleDatasList(middleDatasList);
								list = dataProcessor.process();
	//							System.out.println(middleDatasList[i].size()+"   DrawServer3 line 236");
								
								if(middleDatasList[i].size() == 0)
									middleDatasList[i].add(new ArrayList<Integer>());
								middleDatasList[i].get(j).addAll(list);
							}
							else{
								DataProcessor dataProcessor = dataProcessorsList.get(j);
								dataProcessor.setCurrentSignalIndex(i);
								dataProcessor.setCurrentProcessorIndex(j);
								dataProcessor.setMiddleDatasList(middleDatasList);
								int n = 0;
//								for(int k = 1 + middleDatasList[i].get(j - 1).size() - num  * dataProcessorsList.get(j - 1).getReturnSize()
//										;k  <= middleDatasList[i].get(j - 1).size();k++,n++)
								List<Integer> lastList = middleDatasList[i].get(j - 1);
								int lastSize =  middleDatasList[i].get(j - 1).size();
								if(lastList.size() < dataProcessor.getWindowSize()){
									num = 1;
									break LOOP421;
									}
								int k = Math.max(lastList.size() - num  * dataProcessorsList.get(j - 1).getReturnSize()+1
													,dataProcessor.getWindowSize());
								for(
										;k  <= lastSize;k++,n++)
								{
//									if(k - dataProcessorsList.get(j - 1).getWindowSize() < 0)
//									if(k - dataProcessorsList.get(j).getWindowSize() < 0)
//										break LOOP421;
									list = middleDatasList[i].get(j - 1)
//											.subList(k - dataProcessorsList.get(j - 1).getWindowSize()
											.subList(k - dataProcessor.getWindowSize()
													, k);
									
									dataProcessor.setDataList(list);
									List<Integer> debugList = dataProcessor.process();
	//								System.out.println(debugList+"   debugList DrawServer3 line 253");
									if(middleDatasList[i].size() <= j)
										middleDatasList[i].add(new ArrayList<Integer>());
									middleDatasList[i].get(j).addAll(debugList);
								}
								num = n;
//								System.out.println("num = "+num+", DrawServer3");
							}
						}
	//					System.out.println(middleDatasList[i].size()+"   DrawServer3 line 265 addItem");
						if(middleDatasList[i].size() != 0){
							proccessedDataList[i] = middleDatasList[i].get(middleDatasList[i].size() - 1);
	//						proccessedDataList[i].addAll(middleDatasList[i].get(middleDatasList[i].size() - 1));
	//						System.out.println("DrawServer3 line 315 addItem");
						}
						else
							proccessedDataList[i] = myRawDatasList[i];
					}
					if(show[Constant.RAW_DATA])
						break;
				}
//				int num = 0;
//				ArrayList<Integer> t = new ArrayList<>();

				int num = 0;
//				System.out.println(size+"  = size");
				boolean flag = true;
OUT_LOOP:		for(int i = 0;i < size;i++)
				{
					if(middleDatasTimeList.size() <= i)
						middleDatasTimeList.add(new ArrayList());
//					System.out.println("middleTimeSize = "+middleDatasTimeList.size());
					int circulateTimes = 0;
					List<Integer> lastList = null;
					if(i == 0)
					{
						if(show[Constant.RAW_DATA])
							lastList = rawDataTime;
						else
							lastList = datasTime;
						circulateTimes = 1;
					}
					else {
						lastList = middleDatasTimeList.get(i - 1);
						circulateTimes = Math.min(num * dataProcessorsList.get(i - 1).getReturnSize()
												,lastList.size() - dataProcessorsList.get(i).getWindowSize()+1);
					}
//					System.out.println("circulateTimes : "+circulateTimes+"");
					if(lastList.size() - dataProcessorsList.get(i).getWindowSize() < 0){
						flag = false;
						break OUT_LOOP;
						}
					int k = lastList.size() - circulateTimes;
//					System.out.println("k : "+k);
					int n ;
					for(n = 0;n < circulateTimes;n++){
						for(int j = 0;j < dataProcessorsList.get(i).getReturnSize();j++)
							middleDatasTimeList.get(i).add(lastList.get(k+n));
					}
					num = n;
					flag = true;
//					t.clear();
//					t.clear();
//					int returnSize = dataProcessorsList.get(i).getReturnSize();
//					List<Integer> l = null;
//					if(i == 0)
//					{
//						l = datasTime;
//						num = l.size();
//					}
//					else
//						l = middleDatasTimeList.get(i - 1);
//					for(;;){
//						
//					}
////					int t = l.get(dataProcessorsList.get(i).getWindowSize() - 1);
////					for(int j = 0;j < returnSize;j++)
////						middleDatasTimeList.get(i).add(t);
				}
//				if(flag)
				if(false)
				{
//					List<Integer> lastList = null;
//					if(size == 0)
//						lastList = datasTime;
//					else
//						lastList = middleDatasTimeList.get(size - 1);
//					int size2 = lastList.size();
//					System.out.println("size2 "+size2);
//					System.out.println("time: " + util.getTime(lastList.get(size2 - 1)));
//				
						
					showTimes();
				}
				repaint();
				if(startPredict)
				{
					try {
//						System.out.println("predict ==null "+(predict == null));
//						if(predict != null)
//								System.out.println("input == null "+(predict.getInput() == null));
						String result = (String)Constant.ctls_ol.process(proccessedDataList,show,Constant.SLEEP);
						BufferedWriter bWriter = null;
						try {
							bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constant.beforeScaledFile)));
							bWriter.write(result +"\n");
						}catch(FileNotFoundException e){
							e.printStackTrace();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally {
							try {
								if(bWriter != null)
									bWriter.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						System.out.println("afterScaledFile.name = "+Constant.afterScaledFile.getPath());
						scale.run(new String[]{"-r",Constant.scaleModel,Constant.beforeScaledFile.getPath()});
						BufferedReader bReader = null;
						bReader = new BufferedReader(new InputStreamReader(new FileInputStream(Constant.afterScaledFile)));
						result = bReader.readLine();
						bReader.close();
						System.out.println("Drawserver result = "+result);
						if(result != null && result.length() > 0)
							predict.getInput().put(result);
//						System.out.println("predict.put ,startPredict  DrawLine 696");
					} catch (InvalidParametersForFormatClass
							| FileNotFoundException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//<Remove>
//				List<Integer> list = null;
//				int listSize = 0;
//				
//					for(int i = 0;i < Constant.SIG_NUM;i++)
//					{
//						if(!show[i])
//							continue;
//						System.out.println(Constant.SIG_NAME[i]+"   DrawServer line 215");
//						listSize = rawDatasList[i].size();
//						list = rawDatasList[i].subList(listSize - windowsSize[i], listSize);
//						for(int j = 0;j < dataProcessorsList.size();j++)
//						{
//							dataProcessorsList.get(j).setDataList(list);
//							list = dataProcessorsList.get(j).process();
//							System.out.println("addItem  DrawServer line 212");
//						}
//						proccessedDataList[i].addAll(list);
//					}
//				repaint();
//</Remove>
			}
			private void showTimes(){
				int size = dataProcessorsList.size();
				System.out.println("datasTime:");
				for(Integer i : datasTime)
					System.out.print(util.getTime(i)+" ");
				System.out.println();
				for(int i = 0;i < size;i++){
					System.out.println("middleDatasTimeList.get("+i+"):");
					for(int t : middleDatasTimeList.get(i))
						System.out.print(util.getTime(t)+" ");
					System.out.println();
				}
			}
			private int leftIndex = -1;
			private int rightIndex = -1;
//			{
//				int jScrollBarValue = jScrollBar.getValue();
//				int jScrollBarMaxValue = jScrollBar.getMaximum() - jScrollBar.getVisibleAmount();
//				int listSize = proccessedDataList[i].size();
//				boolean flag1 = listSize* X_STEP_LENGTH < WINDOW_LENGTH || jScrollBarMaxValue == 0 ;
//				leftIndex = flag1 ? 0 : jScrollBarValue * (listSize - WINDOW_LENGTH / X_STEP_LENGTH)/(jScrollBarMaxValue);
//				rightIndex = flag1 ? listSize - 1:
//										jScrollBarValue * (listSize - WINDOW_LENGTH / X_STEP_LENGTH) /jScrollBarMaxValue+ WINDOW_LENGTH/X_STEP_LENGTH - 1;
//				
//			}
			@Override
			protected synchronized void paintComponent(Graphics g){
				super.paintComponent(g);
				int width = getWidth();
				int height = getHeight();
				int jScrollBarValue = jScrollBar.getValue();
				int jScrollBarMaxValue = jScrollBar.getMaximum() - jScrollBar.getVisibleAmount();
				int i = 0;
				for(i = 0;i<Constant.SIG_NUM && !show[i];i++);
				if(i == Constant.SIG_NUM)
					i = 0;
				int listSize = proccessedDataList[i].size();
				boolean flag1 = listSize* X_STEP_LENGTH < WINDOW_LENGTH || jScrollBarMaxValue == 0 ;
//				System.out.println(( listSize* X_STEP_LENGTH < WINDOW_LENGTH )+"   "+jScrollBarMaxValue+"   "+flag1+"   line 236");
				g.drawLine(0, Y_ALIGN, width, Y_ALIGN);
				g.drawLine(X_ALIGN, 0, X_ALIGN, height);
				g.setColor(Color.DARK_GRAY);
				leftIndex = flag1 ? 0 : jScrollBarValue * (listSize - WINDOW_LENGTH / X_STEP_LENGTH)/(jScrollBarMaxValue);
				rightIndex = flag1 ? listSize - 1:
										jScrollBarValue * (listSize - WINDOW_LENGTH / X_STEP_LENGTH) /jScrollBarMaxValue+ WINDOW_LENGTH/X_STEP_LENGTH - 1;
//				System.out.println(leftIndex+" "+rightIndex +"   DrawServer3 line  243");
//				System.out.println(jScrollBarValue+"   "+jScrollBarMaxValue+"  "+listSize+"  line 244");
				boolean flag2 = rightIndex == proccessedDataList[0].size() && WINDOW_LENGTH / X_STEP_LENGTH < listSize;						
				int x = X_ALIGN + X_STEP_LENGTH;
				for( i = leftIndex+1;i <= rightIndex;i++)
				{
					synchronized(proccessedDataList){
						for(int j = 0; j < Constant.SIG_NUM ; j++){
							if(!show[j])
								continue;
							g.setColor(Constant.colorMap.get(Constant.SIG_NAME[j]));
							g.drawLine(x - X_STEP_LENGTH,Y_ALIGN - proccessedDataList[j].get(i - 1)*AMPLIFICATION_NUM 
										, x, Y_ALIGN - proccessedDataList[j].get(i) * AMPLIFICATION_NUM);
							g.setColor(Color.WHITE);
							if(show[Constant.RAW_DATA])
								break;
						}
					}
					x += X_STEP_LENGTH;
				}
				{
					int x2 = index * X_STEP_LENGTH + X_ALIGN;
					int xMax = listSize * X_STEP_LENGTH + X_ALIGN;
					if(x2 > xMax)
						return;
//					if(x2 < 0)
//						return;
					g.setColor(Constant.colorMap.get(Constant.timeLineColor));
					g.drawLine(x2,0,x2,Y_ALIGN);
				}
			}
			public void repaintRequest(){
				synchronized (myRawDatasList) {
					for(int i = 0;i < Constant.SIG_NUM;i++)
					{
						myRawDatasList[i].clear();
						for(int v : rawDatasList[i])
							myRawDatasList[i].add(v);
//						int size = myRawDatasList[i].size();
//						for(int i = 0;i < size;i++)
//							myRawDatasList[i].add()
					}
//					for(int i = 0;i < Constant.SIG_NUM;i++){
					int size = myRawDatasList[Constant.LOW_ALPHA].size();
					for(int j = 0;j < size;j++)
						preProcessRawdatas(j);
//					}
				}
				
				int dataProcessorsListSize = dataProcessorsList.size();
				int lastShow;
				for(lastShow = Constant.SIG_NUM - 1;lastShow >= 0 && !show[lastShow];lastShow --);
				
				//注意 不在for块里
				{
					int j = 0;
					for(DataProcessor d : dataProcessorsList)
					{
						d.setLastShow(lastShow);
						d.setCurrentProcessorIndex(j++);
					}
				}
				synchronized(myRawDatasList){
					for(int i = 0;i < Constant.SIG_NUM;i++)
					{
						System.out.println("show["+i+"] = " + show[i]);
						if(!show[i])
							continue;
						middleDatasList[i].clear();
						List<Integer> list = myRawDatasList[i];
						for(int j = 0;j < dataProcessorsListSize;j++){
							List<Integer> newList = new ArrayList<>();
							DataProcessor dataProcessor = dataProcessorsList.get(j);
							dataProcessor.setCurrentSignalIndex(i);
							dataProcessor.setShow(show);
//							dataProcessor.setCurrentProcessorIndex(j);
							dataProcessor.setMiddleDatasList(middleDatasList);
							int listSize = list.size();
							int windowSize = dataProcessor.getWindowSize();
							for(int k = windowSize; k <= listSize;k++)
							{
								List<Integer> subList = list.subList(k - windowSize, k);
								dataProcessor.setDataList(subList);
								newList.addAll(dataProcessor.process());
							}
							middleDatasList[i].add(newList);
							list = newList;
						}
//						if(middleDatasList[i].size() != 0){
//							proccessedDataList[i] = middleDatasList[i].get(middleDatasList[i].size() - 1);
////							proccessedDataList[i].addAll(middleDatasList[i].get(middleDatasList[i].size() - 1));
//							System.out.println("DrawServer3 line 351 repaintRequest");
//						}
//						else
//							proccessedDataList[i] = rawDatasList[i];
//						System.out.println(proccessedDataList[i]);
						if(show[Constant.RAW_DATA])
							break;
					}
					
					
					for(int i = 0;i < Constant.SIG_NUM;i++){
						if(middleDatasList[i].size() != 0){
							proccessedDataList[i] = middleDatasList[i].get(middleDatasList[i].size() - 1);
//							proccessedDataList[i].addAll(middleDatasList[i].get(middleDatasList[i].size() - 1));
							System.out.println("DrawServer3 line 351 repaintRequest");
						}
						else
							proccessedDataList[i] = myRawDatasList[i];
						if(show[Constant.RAW_DATA])
							break;
					}
//					System.out.println("DrawServer3 line 338 repaintRequest");
				}
				if(datasTime!=null)
				{
					synchronized(datasTime){
						middleDatasTimeList.clear();
						List<Integer> list = show[Constant.RAW_DATA]?rawDataTime:datasTime;
						List<Integer> newList = new ArrayList<>();
						for(int i = 0;i < dataProcessorsListSize;i++)
						{
							int size = list.size();
							int returnSize = dataProcessorsList.get(i).getReturnSize();
							for(int j = dataProcessorsList.get(i).getWindowSize() - 1;j < size;j++)
								for(int k = 0;k < returnSize;k++)
									newList.add(list.get(j));
							middleDatasTimeList.add(newList);
							list = newList;
							newList = new ArrayList<>();
						}
						showTimes();
					}
				}
				repaint();
//<Remove>				
//				int length = dataProcessorsList.size();
//				List<Integer> list = null;
//				synchronized (rawDatasList) {
//					for(int i = 0;i < Constant.SIG_NUM;i++){	
//						if(!show[i])
//							continue;
//						proccessedDataList[i] = new ArrayList<>();
//						for(int j = 0; j + windowsSize[i] <= rawDatasList[i].size();j++){
//							list = rawDatasList[i].subList(j, j + windowsSize[i]);
//							for(int k = 0; k < length;k++){
//								dataProcessorsList.get(k).setDataList(list);
//								list = dataProcessorsList.get(k).process();
//								System.out.println("repaintRequest  DrawServer line 260");
//							}
//							proccessedDataList[i].addAll(list);
//	//						System.out.println(proccessedDataList[i]+"  DrawServer line 172");
//						}
//					}
//					System.out.println(proccessedDataList[0]+"  DrawSercer line 216");
//				}
//				repaint();
//</Remove>
			}
//			int RAW_DATE  = 0;
//			int ATTENTION = 1;
//			int MEDITATION = 2;
//			int DELTA = 3;
//			int THETA = 4;
//			int LOW_ALPHA = 5;
//			int HIGHT_ALPHA = 6;
//			int LOW_BETA = 7;
//			int HIGH_BETA = 8;
//			int LOW_GAMMA = 9;
//			int MID_GAMMA = 10;
			
			public void showRaw(boolean flag){
				show[0] = flag;
			}
			public void showAttension(boolean flag){
				show[1] = flag;
			}
			public void showMeditation(boolean flag){
				show[2] = flag;
			}
			public void showDelta(boolean flag){
				show[3] = flag;
			}
			public void showTheta(boolean flag){
				show[4] = flag;
			}
			public void showLow_alpha(boolean flag){
				show[5] = flag;
			}
			public void showHigh_alpha(boolean flag){
				show[6] = flag;
			}
			public void showLow_beta(boolean flag){
				show[7] = flag;
			}
			public void showHigh_beta(boolean flag){
				show[8] = flag;
			}
			public void showLow_Gamma(boolean flag){
				show[9] = flag;
			}
			public void showMid_Gamma(boolean flag){
				show[10] = flag;
			}
			
			public List<Integer>[] getProccessedDataList() {
				return proccessedDataList;
			}
			public void setProccessedDataList(List<Integer>[] proccessedDataList) {
				this.proccessedDataList = proccessedDataList;
			}
			public List<Integer>[] getRawDatasList() {
				return rawDatasList;
			}
			public void setRawDatasList(List<Integer>[] rawDatasList) {
				this.rawDatasList = rawDatasList;
			}
			public List<DataProcessor> getDataProcessorsList() {
				return dataProcessorsList;
			}
			public void setDataProcessorsList(List<DataProcessor> dataProcessorsList) {
				this.dataProcessorsList = dataProcessorsList;
			}
			public JButton getChooseAlgothrimButton() {
				return chooseAlgothrimButton;
			}
			public void setChooseAlgothrimButton(JButton chooseAlgothrimButton) {
				this.chooseAlgothrimButton = chooseAlgothrimButton;
			}
			public boolean isSlpOrAslp() {
				return slpOrAslp;
			}
			public void setSlpOrAslp(boolean slpOrAslp) {
				this.slpOrAslp = slpOrAslp;
			}
			public List<Integer> getSleep() {
				return sleep;
			}
			public void setSleep(List<Integer> sleep) {
				this.sleep = sleep;
			}
			public List<Integer> getAsleep() {
				return asleep;
			}
			public void setAsleep(List<Integer> asleep) {
				this.asleep = asleep;
			}
			public boolean[] getShow() {
				return show;
			}
			public void setShow(boolean[] show) {
				this.show = show;
			}
			public File getOutputFile() {
				return outputFile;
			}
			public void setOutputFile(File outputFile) {
				this.outputFile = outputFile;
			}
			public svm_predict getPredict() {
				return predict;
			}
			public void setPredict(svm_predict predict) {
				this.predict = predict;
			}
			public svm_train getTrain() {
				return train;
			}
			public void setTrain(svm_train train) {
				this.train = train;
			}
			public boolean isStartPredict() {
				return startPredict;
			}
			public void setStartPredict(boolean startPredict) {
				this.startPredict = startPredict;
			}
			public JTextArea getJta() {
				return jta;
			}
			public void setJta(JTextArea jta) {
				this.jta = jta;
			}
		}
		 class Util{
				public final static int SCROLLBAR_MAXIMUM = 1600 ;//1800;
				public final static int SCROLLBAR_MINIMUM = 0;
				public final static int SCROLLBAR_EXTENT = SCROLLBAR_MAXIMUM - 1500;//SCROLLBAR_MAXIMUM - 1700;
				public DrawLine centerPanel = null;
				public JPanel parent = null;
				
				public  JPanel createDrawPanel(JPanel parent,List<Integer>[] rawDatasList,List<DrawLine> drawLines,List<Integer> datasTime){
					this.parent = parent;
					final JPanel drawp = new JPanel();
					final JPanel drawPanel = new JPanel();
					final JPanel headPanel = new JPanel();
//					DrawLine centerPanel = null;
					JPanel checkBoxPanel = new JPanel();
					JButton deleteThisPanelButton = new JButton("删除此面板");
					JCheckBox[] sigCheckBox = new JCheckBox[Constant.SIG_NUM];
					final JScrollBar jScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, SCROLLBAR_EXTENT, 0,SCROLLBAR_MAXIMUM); 
					checkBoxPanel.setLayout(new GridLayout(0, 5, 3, 3));
					JLabel lab = new JLabel("time:");
					JLabel timeLabel = new JLabel("       ");
					centerPanel =  MainFrame.this.new DrawLine(rawDatasList,jScrollBar,datasTime,timeLabel);
					for(int i = 0;i < sigCheckBox.length;i++){
						sigCheckBox[i] = new JCheckBox(Constant.SIG_NAME[i]);
						checkBoxPanel.add(sigCheckBox[i]);
						sigCheckBox[i].setSelected(true);
						sigCheckBox[i].setBackground(Constant.colorMap.get(Constant.SIG_NAME[i]));
						sigCheckBox[i].addActionListener(new ActionListener() {
							DrawLine cp = centerPanel;
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								JCheckBox cbBox = (JCheckBox)arg0.getSource();
								if(cbBox.getText().equals(Constant.SIG_NAME[0]))
									cp.showRaw(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[1]))
									cp.showAttension(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[2]))
									cp.showMeditation(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[3]))
									cp.showDelta(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[4]))
									cp.showTheta(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[5]))
									cp.showLow_alpha(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[6]))
									cp.showHigh_alpha(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[7]))
									cp.showLow_beta(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[8]))
									cp.showHigh_beta(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[9]))
									cp.showLow_Gamma(cbBox.isSelected());
								else if(cbBox.getText().equals(Constant.SIG_NAME[10]))
									cp.showMid_Gamma(cbBox.isSelected());
								cp.repaintRequest();
							}
						});
					}
					deleteThisPanelButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							Util.this.parent.remove(drawp);
							Util.this.parent.revalidate();
							Util.this.parent.repaint();
						}
					});
					jScrollBar.addAdjustmentListener(new AdjustmentListener() {
						DrawLine cp = centerPanel;
						@Override
						public void adjustmentValueChanged(AdjustmentEvent e) {
							// TODO Auto-generated method stub
							cp.repaint();
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
						}
					});
//					jScrollBar.setVisible(false);
//					headPanel.setLayout(new FlowLayout(5,5,FlowLayout.CENTER));
//					headPanel.add();
					
//					System.out.println(headPanel.getLayout()+"  headPanel layout");
//					<导出图中数据>
					JRadioButton slpRBt = new JRadioButton("sleep");
					JRadioButton aslpRBt = new JRadioButton("asleep");
					
					{
						final ButtonGroup buttonGroup = new ButtonGroup();
	//					slpRBt.addItemStateChangedListener
						slpRBt.addItemListener(new ItemListener(){
							DrawLine cp = centerPanel;
							@Override
							public void itemStateChanged(ItemEvent e) {
								// TODO Auto-generated method stub
								cp.setSlpOrAslp(e.getStateChange() != ItemEvent.SELECTED);
	//							System.out.println(centerPanel.isSlpOrAslp() == DrawLine.SLEEP);
							}
						});
						aslpRBt.setSelected(true);
						buttonGroup.add(slpRBt);
						buttonGroup.add(aslpRBt);
//						JButton clearButton = new JButton("clear");
					}
					JButton clearButton = new JButton("clear");
					clearButton.addActionListener(new ActionListener(){
						DrawLine cp = centerPanel;
						@Override
						public void actionPerformed(ActionEvent e){
							if(cp.isSlpOrAslp() == DrawLine.SLEEP)
								cp.getSleep().clear();
							else
								cp.getAsleep().clear();
						}
					});
					
					JButton outputButton = new JButton("导出数据");
					outputButton.addActionListener(new ActionListener(){
						DrawLine cp = centerPanel;
						FormatConversion conversion = Constant.ctls;
						@Override
						public void actionPerformed(ActionEvent e)
						{
							List<Integer>[] procLists = cp.getProccessedDataList();
							List<Integer>[] plists = new List[Constant.SIG_NUM];
							for(int i = 0;i < Constant.SIG_NUM;i++)
								plists[i] = (ArrayList<Integer>)((ArrayList<Integer>)procLists[i]).clone();
//							File file = new File("./test/ConvertToLibSVM2.dat");
							JFileChooser jfc = new JFileChooser("./data/train");
							try {
								if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
								{
									File file = jfc.getSelectedFile(); 
									cp.setOutputFile(file);
									conversion.process(plists,cp.getShow(),file,cp.getSleep(),cp.getAsleep());
								}
							} catch (InvalidParametersForFormatClass
									| FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
//					</导出图中数据>
//					<训练数据>
					JButton trainButton = new JButton("训练数据");
					trainButton.addActionListener(new ActionListener(){
						JFileChooser jfc = null;
						DrawLine cp = centerPanel;
						@Override
						public void actionPerformed(ActionEvent e){
							final svm_train train = cp.getTrain();
//							cp.setStartPredict(true);
							new Thread(){
								@Override
								public void run(){
									File file = null;//centerPanel.getOutputFile();
//									if(file == null)
									{
										jfc = new JFileChooser("./data/train");
										if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
											file = jfc.getSelectedFile();
	//										<睡0.5秒>
											try {
												Thread.sleep(500);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
	//										<睡0.5秒>
											jfc  = new JFileChooser("./data/model");
											if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
//												file = jfc.getSelectedFile();
												try {
													train.main(new String[]{file.getPath(),jfc.getSelectedFile().getPath()});
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									}
									
//									jfc  = new JFileChooser("./data/model");
//									if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
////										file = jfc.getSelectedFile();
//										try {
//											train.main(new String[]{file.getPath(),jfc.getSelectedFile().getPath()});
//										} catch (IOException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//									}
//									train.main(file);
								}
							}.start();
						}
					});
//					</训练数据>
//					<开始预测>
				
					JButton predictButton = new JButton("开始预测");
					predictButton.addActionListener(new ActionListener(){
						Thread thread = null;
						Thread thread2 = null;
						JFileChooser jfc = new JFileChooser("./data/model");
						DrawLine cp = centerPanel;
						ActionListener __this = this;
//						boolean
						@Override
						public void actionPerformed(ActionEvent e){
							if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
								final File file = jfc.getSelectedFile();//null;
								final svm_predict predict = cp.getPredict();
//								System.out.println("predict = "+predict);
								cp.setStartPredict(true);
//								System.out.println("ActionListener : "+this);
//								System.out.println("thread : "+thread);
								if(thread != null && thread.isAlive()){
									thread.interrupt();
								}
								else{
									if(thread != null)
										System.out.println(thread.isAlive()+"  : thread is alive");
									else
										System.out.println("thread is "+thread);
									predict.setInput(new MyBufferedReader());
									predict.setOutput(new MyDataOutputStream());
									thread = new Thread(){
										@Override
										public void run(){
											try {
												System.out.println("file.getPath : model  "+file.getPath());
												predict.main(new String[]{"./data/train2/meanless.dat",file.getPath(),"./data/out/meanless.dat"});
											} catch (IOException
													| InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									};
									thread.start();
								}
								
								
								if(thread2 == null){
									thread2 = new Thread(){
										DataOutputStream output = null;
//										DrawLine cp = centerPanel;//请注意 这里一定要用cp代替centerPanel,如果下面while中的cp改成centerPanel的话，虚拟机会去寻找centerPanel,然而centerPanel这个域已经改成新的DrawLine对象了
										@Override
										public void run(){
											try {
												System.out.println("line 1433 thread2 run");
												while(socket == null||socket.isClosed()){
													System.out.println("socket "+socket);
													if(socket != null)
														System.out.println("socket.isClosed "+socket.isClosed());
													Thread.sleep(1000);
													}
												output = new DataOutputStream(socket.getOutputStream());
												while(true){
													System.out.println(" jta.append !");
//													System.out.println("centerPanel.getPredict().getOutput() == "+cp.getPredict().getOutput());
													boolean b = cp.getPredict().getOutput().takeBoolean();
													cp.getJta().append(b+"\n");
													output.writeBoolean(b);
												}
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												thread2 = null;
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											finally{
												try {
													if(output != null)
														output.close();
//													Thread.sleep(1000);
													//请注意 如果在不改变代码结构的情况下，如果要让这个线程永久的循环下去（不会因为异常而停止运行run中的代码），可以用下面的方法来实现，但是不能用 创建新线程，然后把老线程作为新线程的构造器参数传入，这样只能起到连续两次的作用；
													Constructor[] cs = thread2.getClass().getDeclaredConstructors();
													cs[0].setAccessible(true);
													thread2 = (Thread) cs[0].newInstance(__this);
													System.out.println("line 1286");
//													thread2 = thread2.getClass().newInstance();
													thread2.start();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (InstantiationException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (InvocationTargetException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									};
									thread2.start();
								}
							}
						}
					});
//					</开始预测>
					headPanel.add(slpRBt);
					headPanel.add(aslpRBt);
					headPanel.add(clearButton);
					headPanel.add(outputButton);
					headPanel.add(trainButton);
					headPanel.add(predictButton);
					headPanel.add(lab);
					headPanel.add(timeLabel);
					headPanel.add(checkBoxPanel);
					headPanel.add(deleteThisPanelButton);
					drawPanel.setLayout(new BorderLayout());
					drawPanel.add(headPanel,BorderLayout.NORTH);
					drawPanel.add(centerPanel);
					headPanel.add(centerPanel.getChooseAlgothrimButton());
					drawLines.add(centerPanel);
					centerPanel.setPreferredSize(new Dimension(SCROLLBAR_MAXIMUM, DrawLine.DEFAULT_HEIGHT));
					drawLineList.add(centerPanel);
					drawPanel.add(jScrollBar,BorderLayout.SOUTH);
//					drawPanel.add(new JTextArea(25,20),BorderLayout.EAST);
					
					drawp.setLayout(new BorderLayout());
					drawp.add(drawPanel);
//					
					JTextArea jta = new JTextArea(25,25);
					centerPanel.setJta(jta);
					JScrollPane jsp = new JScrollPane(jta);
//					jsp.add(jta);
//					
					drawp.add(jsp,BorderLayout.EAST);
//					drawPanel = p;
					
//					System.out.println(drawPanel+"   line 86");
					return drawp;
				}
				public String getTime(int tMillis){
					String time = "";
					int s = tMillis/1000 % 60;
					int m = tMillis/(1000 * 60) % 60;
					int h = tMillis/(1000 * 3600) % 24+8;
					time = h+":"+m+":"+s;
					return time;
				}
			}
			
	}
	
}
