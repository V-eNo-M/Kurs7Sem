import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lida on 01.10.2015.
 */
public class MyThread implements Runnable{
    private String name;
    
    public ArrayList<Integer> getLengthRes() {
        return lengthRes;
    }

    private ArrayList<Integer> lengthRes = new ArrayList<Integer>();

    private int length;

    public ArrayList<Res> getArrayRes() {
        return arrayRes;
    }

    private ArrayList<Res>  arrayRes = new ArrayList<Res>();

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public MyThread(String name,int length , ArrayList<Res> res ,ArrayList<Integer> lengthRes){
        this.name = name;
        this.length = length;
        this.arrayRes = res;
        this.lengthRes = lengthRes;
    }

    @Override
    public void run() {
        System.out.println("Start thread " + name);
        try {
            System.out.println("Длинна потока = " + this.length);
            Random rand = new Random();

            //заполняем вероятности использования ресурса
            for (int i = 0; i < arrayRes.size(); i++) {
                arrayRes.get(i).setLenght(lengthRes.get(i));
            }
            //цикл выывода на экран времени выполнения потока и его ресурсов
            for (int i = 0; i < length; i++) {
                Thread.sleep(100);
                System.out.print("Время потока " + name + " - " + (length - i));
                for (int j = 0; j < arrayRes.size(); j++) {
                    if((rand.nextInt(100)+1)<=arrayRes.get(j).getLenght()) {
                        System.out.print(" Занятый ресурс " + arrayRes.get(j).getName() + " - " + (arrayRes.get(j).getLenght()));
                        arrayRes.get(j).setIsUsed(true);
                    }
                    else arrayRes.get(j).setIsUsed(false);
                }
                System.out.println();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Close thread " + name);
    }

}
