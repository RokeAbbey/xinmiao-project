package cn.edu.zucc.test;
import java.util.*;
public class Test3 {
	public static void main(String[] args) {
//		ArrayList<A> l = new ArrayList<>();
//		l.add(new A());
//		List<A> l2 = (List<A>) ((List) l).clone();
//		A a2 = (A) l.get(0).clone();
//		System.out.println(l instanceof Cloneable);
		
LOOP:	for(int i = 0;i < 10;i++){
			System.out.println(i);
			if(i == 5)
				break LOOP;
		}
	}
}
class A {
	int a = 0;
	public A clone(){
		return this;
	}
}
