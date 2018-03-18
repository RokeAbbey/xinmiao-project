package cn.edu.zucc.test;
import java.io.File;
public class Test7 {
	public static void main(String[] args) {
		File file = new File("./data/time/abcTest.dat");
		File file2 = new File(file.getPath());
		System.out.println(file.getPath());
		System.out.println(file.getName());
		System.out.println(file2.getPath()+"  "+file2.getName());
		System.out.println(file2.getParent());
	}
}
