package cn.edu.zucc.Uitl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

public class VerticalLayout extends FlowLayout implements LayoutManager{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601308501751639891L;
	private int xGap ,yGap;
	private Object out;
	public VerticalLayout() {
		// TODO Auto-generated constructor stub
	}
	public VerticalLayout(Object out){
		this.out = out;
	}
	
	@Override
	public void layoutContainer(Container parent) {
		// TODO Auto-generated method stub
		Insets  insets = parent.getInsets();
		int x = insets.left, y = insets.right;
//		System.out.println("x=" +x+"   ,y="+y+"    DrawServer3 line 138");
		int length = parent.getComponentCount();
		Component[] children = parent.getComponents();
		for(int i = 0;i < length ;i++)
		{
			if(children[i].isVisible()){
				Dimension dimension =  children[i].getPreferredSize();
				children[i].setBounds(x, y, dimension.width, dimension.height);
				y += yGap+dimension.height;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return preferredLayoutSize(parent);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		Dimension d =  new Dimension(0, 0);
		int memberNum = parent.getComponentCount();
		Component[] components = parent.getComponents();
		for(int i = 0;i < memberNum ;i++)
		{
			if(components[i].isVisible()){
				Dimension d2 = components[i].getPreferredSize();
				d.width = Math.max(d2.width,d.width);
				d.height += d2.height;
			}
			Insets insets = parent.getInsets();
			d.height += insets.top + insets.bottom;
			d.width += insets.left + insets.right; 
		}
		return d;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
//		super.removeLayoutComponent(comp);
	}
	
}