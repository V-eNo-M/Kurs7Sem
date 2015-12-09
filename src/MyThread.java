import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 01.10.2015.
 */
public class MyThread implements Runnable{
    @SerializedName("name")
    private String name;


    private int Glj ;


    public ArrayList<Integer> getLengthRes() {
        return lengthRes;
    }

    private ArrayList<Integer> lengthRes = new ArrayList<Integer>();

    @SerializedName("long")
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

            //проверяем всели ресурсы нужные нам свободны
                for (int j = 0; j < arrayRes.size(); j++) {
                    //если ресурс доступен, то блокируем его
                    if(!arrayRes.get(j).getIsUsed()){
                   // arrayRes.get(j).setIsUsed(true);
                    }
                    else {
                        //если не все свободны, то запускаем их выполнение и ждём освобождение
                        Glj = j;
                        Thread m1= (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < arrayRes.size() ; i++) {
                                    if (arrayRes.get(i).getIsUsed()) {
                                        while(arrayRes.get(i).getLenght() >=0) {
                                            for (int j = 0; j < arrayRes.size(); j++) {
                                                System.out.print("Время ожидания ресурса " + arrayRes.get(i).getName() + " - " + arrayRes.get(i).getLenght());
                                                System.out.println();
                                                arrayRes.get(i).setLenght(arrayRes.get(i).getLenght() - 1);
                                            try {
                                                Thread.sleep(100);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }}
                                    //когда ресурсы освобождаются меняем параметр
                                    else arrayRes.get(i).setIsUsed(false);
                                }
                            }
                        }));
                        m1.start();

                        //ожидаем освобождение ресурсов
                        while(m1.isAlive())
                            Thread.sleep(100);
                    }
                }

            //заполняем время использования ресурса
            for (int i = 0; i < arrayRes.size(); i++) {
                arrayRes.get(i).setLenght(lengthRes.get(i));
                arrayRes.get(i).setIsUsed(true);
            }
            //цикл выывода на экран времени выполнения потока и его ресурсов
            for (int i = 0; i < length; i++) {
                Thread.sleep(100);
                System.out.print("Время потока " + name + " - " + (length - i));
                for (int j = 0; j < arrayRes.size(); j++) {
                    System.out.print(" Время выполнения ресурса " + arrayRes.get(j).getName() + " - " + (arrayRes.get(j).getLenght()));
                    if(arrayRes.get(j).getLenght() > 0)
                    arrayRes.get(j).setLenght(arrayRes.get(j).getLenght() - 1);
                }
                System.out.println();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // освобождаем есурсы которые больше не нужны
        for (int j = 0; j < arrayRes.size(); j++) {
            if(arrayRes.get(j).getLenght() < 1)
            arrayRes.get(j).setIsUsed(false);
        }
        System.out.println("Close thread " + name);
    }

}
