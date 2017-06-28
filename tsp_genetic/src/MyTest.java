import java.io.*;
import java.util.Random;

/**
 * Describe: description of this class
 * Author: ouym
 * Created Date: 2017/4/10.
 */
public class MyTest {

    public static void main(String[] args) {
        MyTest mt = new MyTest();
        mt.getCityPosition();
    }

    /**
     * 随机生成48个城市的坐标
     * x轴范围[0,6000)
     * y轴范围[0,9000)
     */
    public void getCityPosition(){
        int i = 0;
        File file = new File("C:\\Users\\ouym\\Desktop\\知识工程\\myData.txt");
        while(i++<48){
            int x = (int) (Math.random()*6000);
            int y = (int) (Math.random()*9000);
            //System.out.println(i+" "+x+" "+y);
            try {
                OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file,true));
                BufferedWriter bw = new BufferedWriter(ow);
                bw.write(i+" "+x+" "+y+"\r\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
