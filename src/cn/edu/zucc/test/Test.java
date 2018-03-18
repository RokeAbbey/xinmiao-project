package cn.edu.zucc.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import HelloWorld.Hello;

//import HelloWorld.Hello;

public class Test {
	public static void main(String[] args) {
//		try {
//			Hello hello = new Hello();
//			hello.HelloWorld(null);
//		} catch (MWException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(MWClassID.DOUBLE.getClass());
//		System.out.println(MWComplexity.);
		int[] dims = {3};
//		int[] arrays = {1,2,3};
		List<Integer> arrays = Arrays.asList(1,2,3);//new ArrayList<Integer>();
		
		MWNumericArray x = MWNumericArray.newInstance(dims,arrays,MWClassID.DOUBLE);
		System.out.println(x.toString());
	}
}
