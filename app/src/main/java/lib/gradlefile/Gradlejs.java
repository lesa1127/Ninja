package lib.gradlefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LESA on 2018/1/14.
 */

public class Gradlejs {
    public static void built(String rootparth,String host,String port,String token){
        File olefile = new File(rootparth+"/configuration.js");
        File part1f =new File(rootparth+"/part1.txt");
        File part2f =new File(rootparth+"/part2.txt");
        File part3f =new File(rootparth+"/part3.txt");
        File part4f =new File(rootparth+"/part4.txt");

        FileOutputStream out=null;
        FileInputStream part1 = null;
        FileInputStream part2 = null;
        FileInputStream part3 = null;
        FileInputStream part4 = null;
        try {
            out  = new FileOutputStream(olefile);
            part1=new FileInputStream(part1f);
            part2=new FileInputStream(part2f);
            part3=new FileInputStream(part3f);
            part4=new FileInputStream(part4f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (out!=null&&part1!=null&&part2!=null&&part3!=null&&part4!=null){
            byte[] buffer = null;


            try {
                buffer = new byte[(int) part1f.length()];
                part1.read(buffer);
                out.write(buffer);
                out.write(host.getBytes("utf-8"));

                buffer = new byte[(int)part2f.length()];
                part2.read(buffer);
                out.write(buffer);
                out.write(port.getBytes("utf-8"));

                buffer = new byte[(int)part3f.length()];
                part3.read(buffer);
                out.write(buffer);
                out.write(token.getBytes("utf-8"));

                buffer = new byte[(int)part4f.length()];
                part4.read(buffer);
                out.write(buffer);

                out.flush();
                out.close();

                part1.close();
                part2.close();
                part3.close();
                part4.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
