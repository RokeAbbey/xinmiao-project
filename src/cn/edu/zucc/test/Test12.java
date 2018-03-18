package cn.edu.zucc.test;

import java.util.StringTokenizer;

public class Test12 {
	public static void main(String[] args) {
		StringTokenizer st = new StringTokenizer("0	1:-9	2:155	3:9	4:-8	5:-29	6:-41	  ", " \t\n\r\f:");
		System.out.println(st.nextToken());
	}
}
