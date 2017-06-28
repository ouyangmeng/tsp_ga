import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Describe: 贪心法求TSP问题：答案为12842
 * Author: ouym
 * Created Date: 2017/4/10.
 */
public class TxTsp {

    private int cityNum; // 城市数量
    private int[][] distance; // 距离矩阵

    private int[] colable;//代表列，也表示是否走过，走过置0
    private int[] row;//代表行，选过置0

    public TxTsp(int n) {
        cityNum = n;
    }

    private void init(String filename) throws IOException {
        // 读取数据
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            // 读取一行数据，数据格式1 6734 1453
            strbuff = data.readLine();
            // 字符分割
            String[] strcol = strbuff.split(" ");
            x[i] = Integer.valueOf(strcol[1]);// x坐标
            y[i] = Integer.valueOf(strcol[2]);// y坐标
        }
        data.close();

        // 计算距离矩阵
        // ，针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // 对角线为0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                // 四舍五入，取整
                int tij = (int) Math.round(rij);
                if (tij < rij) {
                    distance[i][j] = tij + 1;
                    distance[j][i] = distance[i][j];
                } else {
                    distance[i][j] = tij;
                    distance[j][i] = distance[i][j];
                }
            }
        }

        distance[cityNum - 1][cityNum - 1] = 0;

        colable = new int[cityNum];
        colable[0] = 0;
        for (int i = 1; i < cityNum; i++) {
            colable[i] = 1;
        }

        row = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            row[i] = 1;
        }

    }

    public void solve(){

        int[] temp = new int[cityNum];
        String path="0";

        int s=0;//计算距离
        int i=0;//当前节点
        int j=0;//下一个节点
        //默认从0开始
        while(row[i]==1){
            //复制一行
            for (int k = 0; k < cityNum; k++) {
                temp[k] = distance[i][k];
                //System.out.print(temp[k]+" ");
            }
            //System.out.println();
            //选择下一个节点，要求不是已经走过，并且与i不同
            j = selectmin(temp);
            //找出下一节点
            row[i] = 0;//行置0，表示已经选过
            colable[j] = 0;//列0，表示已经走过

            path+="-->" + j;
            //System.out.println(i + "-->" + j);
            //System.out.println(distance[i][j]);
            s = s + distance[i][j];
            i = j;//当前节点指向下一节点
        }
        System.out.println("路径:" + path);
        System.out.println("总距离为:" + s);

    }

    public int selectmin(int[] p){
        int j = 0, m = p[0], k = 0;
        //寻找第一个可用节点，注意最后一次寻找，没有可用节点
        while (colable[j] == 0) {
            j++;
            //System.out.print(j+" ");
            if(j>=cityNum){
                //没有可用节点，说明已结束，最后一次为 *-->0
                m = p[0];
                break;
                //或者直接return 0;
            }
            else{
                m = p[j];
            }
        }
        //从可用节点J开始往后扫描，找出距离最小节点
        for (; j < cityNum; j++) {
            if (colable[j] == 1) {
                if (m >= p[j]) {
                    m = p[j];
                    k = j;
                }
            }
        }
        return k;
    }

    //输出节点的距离矩阵
    public void printinit() {
        System.out.println("print begin....");
        for (int i = 0; i < cityNum; i++) {
            for (int j = 0; j < cityNum; j++) {
                System.out.print(distance[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("print end....");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Start....");
        TxTsp ts = new TxTsp(10);
        ts.init("C:\\Users\\ouym\\Desktop\\tsp\\att48.txt");
        //ts.printinit();
        ts.solve();
    }
}
