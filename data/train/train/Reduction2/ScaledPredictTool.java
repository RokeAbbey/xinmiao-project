import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.FilenameFilter;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.concurrent.locks.*;
public class ScaledPredictTool implements FilenameFilter{
    private static String fn = null;
    private static Lock lock = new ReentrantLock();
    private static Condition con = lock.newCondition();
    public boolean accept(File dir,String name){
    
        // if(name.matches(".*[^(readme)(\\.out)]\\.dat$"))
        if(name.matches(".*(?!readme|\\.out)(?<!readme|\\.out)\\.dat"))
            return true;
        System.out.println("name = "+name +", false\n");
        return false;
    }
    public static void main(String[] args){
        //args[0] preDataFold
        //args[1] trainDataFold
        //args[2] synthesizeScaledModel.txt
        if(args.length < 3){
            System.out.println("use cammand as java ScaledPredictTool preDataFold trainDataFold synthesizeScaledModel");
            return;
        }
        File preData = new File(args[0]);
        File preDataScale = new File(args[0]+"/scale");
        File accu = new File(args[0]+"/accu.txt");
        FilenameFilter filter = new ScaledPredictTool();
        
        BufferedWriter outputToCMD = null;
        if(!preDataScale.exists())
            preDataScale.mkdir();
        Runtime r = Runtime.getRuntime();
        Process p = null;    
        
        BufferedReader inp = null;
        try{
            p = Runtime.getRuntime().exec("cmd");
            outputToCMD = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            final DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(accu,true)));
            final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));    
            inp = input;
            Thread t = new Thread(){
                        @Override
                        public void run(){
                            String line = null;
                            Pattern pa = Pattern.compile("Accuracy = \\d*\\.\\d{4}");
                            try{
                                while((line = input.readLine()) != null){
                                    lock.lock();
                                    Matcher m = pa.matcher(line);
                                    // System.out.println("cmd : "+line+" ,line 70");
                                    if(m.find()){
                                        System.out.println(m.group());
                                        output.writeUTF(m.group()+"\t"+fn+"\n");
                                        System.out.println("after writeUTF\n");
                                        output.flush();
                                        con.signal();    
                                        // lock.unlock();
                                    }
                                    lock.unlock();
                                    Thread.sleep(1000);
                                }
                                System.out.println("line 83");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
            t.start();
            int i = 0;
            for(String fn : preData.list(filter)){
                String cmd = "svm-scale -r "+args[1]+"/synscale.dat "+args[0]+"/"+fn+">>"+args[0]+"/scale/"+fn;
                System.out.println(cmd);
                outputToCMD.write(cmd);
                outputToCMD.newLine();
                outputToCMD.flush();
                Thread.sleep(500);
                // p = r.exec(cmd);
                // p = Runtime.getRuntime().exec(cmd);
                // Thread.sleep(100);
                // p.waitFor();
            }
            
            
            // Pattern pa2 = Pattern.compile("[]");
            // int i = 0;
            // for(String line = input.readLine();line != null;)
            //         System.out.println(line+"  line 63 "+(++i));
            for(String fn : preDataScale.list(filter)){
                lock.lock();
                ScaledPredictTool.fn = fn;
                String cmd = "svm-predict "+args[0]+"/scale/"+fn+" "+args[1]+"/"+args[2]+" "+args[0]+"/scale/"+fn.replace(".dat",".out.dat");
                System.out.println(cmd);
                // p = Runtime.getRuntime().exec(cmd);
                // p = r.exec(cmd);
                outputToCMD.write(cmd);
                outputToCMD.newLine();
                outputToCMD.flush();
                con.await();
                lock.unlock();
                Thread.sleep(1000);
                // p.waitFor();
            }
            // p.destroy();
            t.interrupt();
            t.join();

        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            
            try{
                if(p != null)
                    p.destroy();
                // if(output != null)
                //     output.close();
                if(inp != null)
                    inp.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            // if(r != null)
            //     r.close();
        }

    }
}