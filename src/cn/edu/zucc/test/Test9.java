package cn.edu.zucc.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test9 {
	public static void main(String[] args) {
		TestInterface ti = new TestInterface(){
			Object o = new Object();
		};
		Class c = ti.getClass();
		Field[] fs = c.getDeclaredFields();
		for(Field f : fs)
		{
			int m = f.getModifiers();
			System.out.println(f.getName());
			System.out.println("isFinal : "+Modifier.isFinal(m)+", is static : "+Modifier.isStatic(m));
		}
	}
}
