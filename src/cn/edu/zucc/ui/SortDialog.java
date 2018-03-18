package cn.edu.zucc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.edu.zucc.Bean.A_B_T_GSignalScaleProcessor;
import cn.edu.zucc.Bean.ApEn;
import cn.edu.zucc.Bean.ApEn2;
import cn.edu.zucc.Bean.AverageProcessor;
import cn.edu.zucc.Bean.Complexity;
import cn.edu.zucc.Bean.DataProcessor;
import cn.edu.zucc.Bean.RemoveMaxAndMin;
import cn.edu.zucc.Bean.Scale;
import cn.edu.zucc.Bean.ScaleLg;

//import com.sun.javafx.can.DataProcessor;

public class SortDialog extends JDialog{
	private static final Map<String, DataProcessor> processorMap = new HashMap();
	static{
		processorMap.put("average", new AverageProcessor());
		processorMap.put("removeMinAndMax", new RemoveMaxAndMin());
		processorMap.put("AE", new ApEn2());
		processorMap.put("Complexity", new Complexity());
		processorMap.put("MatlabApEn",new ApEn());
		processorMap.put("A_B_T_G",new A_B_T_GSignalScaleProcessor());
		processorMap.put("scale",new Scale());
		processorMap.put("scaleLg",new ScaleLg());
//		processorMap.put("pro3", new DataProcessor(new ArrayList<>()));
//		processorMap.put("pro4", new DataProcessor(new ArrayList<>()));
	}
	private List<DataProcessor> dataProcessorsList = null;//  new ArrayList<>();
	private List<JCheckBox> checkBoxsList = new ArrayList<>();
	private JPanel sortPanel = null;
	public static void main(String[] args) {
		List<DataProcessor> dataProcessorsList = new ArrayList<>();
//		new SortDialog(dataProcessorsList);
	}
	public SortDialog(JFrame arg0,String arg1,Boolean arg2,List<DataProcessor> dataProcessorsList) {
		// TODO Auto-generated constructor stub
		super(arg0,arg1,arg2);
		setLayout(new BorderLayout());
		this.dataProcessorsList = dataProcessorsList;
		Set<Map.Entry<String, DataProcessor>> iteratorSet =	processorMap.entrySet();
		JPanel p1 = new JPanel();
		for(Map.Entry<String, DataProcessor> entry : iteratorSet)
		{
			JCheckBox b = new JCheckBox(entry.getKey());
			checkBoxsList.add(b);
			b.addActionListener(new CheckBoxListener(b.getText()));
			p1.add(b);
		}
		sortPanel = new SortPanel();
		add(sortPanel, BorderLayout.SOUTH);
		sortPanel.setPreferredSize(new Dimension(100, 200));
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		p3.add(p1);
		JButton clearButton = new JButton("清除");
		p4.add(clearButton);
		p3.add(p4);
		add(p3);
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(JCheckBox jc : checkBoxsList)
					jc.setSelected(false);
				SortDialog.this.dataProcessorsList.clear();
			}
		});
		pack();
		setVisible(true);
//		System.out.println(p1.getY()+"   "+p1.getHeight()+"   SortDialog line 55");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
	private class CheckBoxListener implements ActionListener{

		private String keyStr;
		public CheckBoxListener(String keyStr) {
			this.keyStr = keyStr;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JCheckBox b = (JCheckBox) e.getSource();
			if(b.getText().equals(keyStr))
			{
				if(b.isSelected())
					dataProcessorsList.add(processorMap.get(keyStr));
				else
					dataProcessorsList.remove(processorMap.get(keyStr));
				sortPanel.repaint();
				System.out.println(dataProcessorsList.size()+"   SortDialog line 77");
			}
		}
	}
	private class SortPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4631042862261477960L;
		private static final int HEIGHT = 100;
		private static final int WIDTH = 80;
		private static final int DEFAULT_YALIGN = 10;
		private int xAlign = 0;
		private int yAlign = DEFAULT_YALIGN;
		private Map<String, Integer> motionX = new HashMap<>();
		private Map<String, Integer> motionY = new HashMap<>();
		{
			Set<Map.Entry<String, DataProcessor>> entrySet = processorMap.entrySet();
			for(Map.Entry<String, DataProcessor> entry : entrySet){
//				System.out.println(entry.getKey()+"   SortDialog line 85");
				motionX.put(entry.getKey(), 0);
				motionY.put(entry.getKey(), 0);
			}
		}
		{
			setBackground(Color.YELLOW);
		}
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			xAlign = getWidth()>>1;
			yAlign = getY();
			int x = 0;
			FontMetrics fs = getFontMetrics(getFont());
			g.setColor(Color.BLACK);
			
			for(int i = 0;i < dataProcessorsList.size();i++,x+=x){
				String str = checkBoxsList.get(i).getText();
//				System.out.println(str +"   SortDialog line 97");
//				System.out.println(xAlign + x + motionX.get(str));
//				System.out.println( yAlign + motionY.get(str)+"  "+SortDialog.this.getHeight());
				g.fillRect(xAlign + x + motionX.get(str), yAlign + motionY.get(str), WIDTH, HEIGHT);
				g.drawString(str, xAlign + x + motionX.get(str) +(WIDTH  - fs.stringWidth(str) >> 1)
						, yAlign + motionY.get(str) +(HEIGHT - fs.getAscent() >> 1));
			}
		}
	}
}

