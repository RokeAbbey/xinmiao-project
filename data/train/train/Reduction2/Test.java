public class Test{
    public static void main(String... args){
        String str = "abc_readme.dat";
        boolean f = str.matches("[(readme)]\\.dat");
        System.out.println(f);
    }
}