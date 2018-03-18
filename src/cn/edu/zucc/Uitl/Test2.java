package cn.edu.zucc.Uitl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFileChooser;

//import com.sun.java.util.jar.pack.Package.File;

public class Test2 {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException
	{
		Date date = new Date();
		System.out.println(((date.getYear()- 100 + 2000)+"_"+(date.getMonth()+1)+"_"+date.getDate()+"_"+date.getHours()+"_"+date.getMinutes()+"_"+date.getSeconds()));
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
//			System.out.println(jfc.getSelectedFile().getAbsolutePath());
			File file = jfc.getSelectedFile();
			file.createNewFile();
			
		}
	}
}
