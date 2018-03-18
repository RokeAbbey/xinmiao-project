package cn.edu.zucc.test;
import java.util.Date;
import java.util.Calendar;
public class Test2 {
	public static void main(String[] args) {
		int t = (int)(System.currentTimeMillis()%(24 * 3600 * 1000));
//		Date d = new Date(t);
//		System.out.println(d.getHours()+"点"+d.getMinutes()+"分"+d.getSeconds()+"秒");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		System.out.println(h+":"+m+":"+s+"    ");
	}
}
