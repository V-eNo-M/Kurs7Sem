import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.swing.BakedArrayList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by 1 on 01.10.2015.
 */
public class ControlThread {
    List<MyThread> listThread = new ArrayList<MyThread>();
    private int countSleepCreateThread = 2500;

    public ControlThread() throws FileNotFoundException {
        Random rand = new Random();
        ReadFromFile read = new ReadFromFile();
        JsonArray json;
        Gson g = new Gson() ;
        ArrayList<Res> globalRes = new ArrayList<>();
        json = g.fromJson(read.getKey(), JsonElement.class).getAsJsonArray();


        //создаём список ресурсов, чтобы передавать их в локальные списки у каждого потока
            //создаём json массив
        for (int j = 0; j < json.size(); j++) {

            JsonArray jarr = g.fromJson(json.get(j).getAsJsonObject().get("Res"),JsonElement.class).getAsJsonArray();

            //пихаем в список ресурсов все ресурсы какие есть во входном файле у всех потоков
        for (int i = 0; i < jarr.size(); i++) {
            globalRes.add(new Res(jarr.get(i).getAsJsonObject().get("res").getAsString()));
        }
        }
        ArrayList<Integer> m = new ArrayList();
        ArrayList<Res> buf = new ArrayList<>();

        buf.add(globalRes.get(0));
           //ищем среди них дубликаты чтобы удалить их
        for (int i = 0; i < globalRes.size(); i++) {
                    for (int k = 0; k < buf.size(); k++) {
                        if(globalRes.get(i).getName().equals(buf.get(k).getName()))
                            break;
                        else if (k == buf.size()-1){
                            buf.add(globalRes.get(i));
                        }
                }
        }
        globalRes = buf;
        /*
            //сортируем список на удаление чтобы удалять с конца
        for (int i = 0; i < m.size(); i++) {
            for (int j = 0; j < m.size(); j++) {
                int bufer = 0;
                if (m.get(i) < m.get(j) && i < j) {
                    bufer = m.get(j);
                    m.set(j,m.get(i));
                    m.set(i,bufer);
                }
            }
        }

            //удаляем дубликаты и получаем список уникальных ресурсов
        for (int i = 0; i < m.size(); i++) {
            globalRes.remove(globalRes.get(m.get(i)));
        }*/

        // Формируем список потоков для запуска
        for (int i = 0; i < json.size(); i++) {
            JsonArray jarr = g.fromJson(json.get(i).getAsJsonObject().get("Res"),JsonElement.class).getAsJsonArray();
            ArrayList<Res> res = new ArrayList<Res>();
            ArrayList<Integer> lengthRes = new ArrayList<>();
            for (int j = 0; j < jarr.size(); j++) {
                Res x = new Res(jarr.get(j).getAsJsonObject().get("res").getAsString());
                for (Res n : globalRes) {
                    if (x.getName().equals(n.getName())) {
                        res.add(n);
                    }
                }
                lengthRes.add(jarr.get(j).getAsJsonObject().get("long").getAsInt());
                //res.get(j).setLenght(jarr.get(j).getAsJsonObject().get("long").getAsInt());
            }

            listThread.add(new MyThread(json.get(i).getAsJsonObject().get("name").getAsString(), json.get(i).getAsJsonObject().get("long").getAsInt(), res, lengthRes));
        }


        //создаём поток который будет запускать потоки(симуляция процессора)
        Thread y = (new Thread(new Runnable() {
            int number = 0;
            @Override
            public void run() {
               // while (listThread.size() > 0) {
                while(listThread.size()>0) {
                    try {
                        number = SearchMinLength();
                        Thread m = new Thread(listThread.get(number));
                        //System.out.println(listThread.get(number).getLength());
                        System.out.println("Сейчас в очереди: " + listThread.size());

                        for (int i = 0; i < listThread.size(); i++) {
                            System.out.print("Имя потока: " + listThread.get(i).getName() + "  Длинна потока: " + listThread.get(i).getLength());
                            System.out.print("  Ресурсы потока : ");
                            for (int j = 0; j < listThread.get(i).getArrayRes().size(); j++) {
                                System.out.print(" Имя " + listThread.get(i).getArrayRes().get(j).getName() + " Вероятность " + listThread.get(i).getLengthRes().get(j));
                            }
                            System.out.println();
                        }

                        m.run();
                        //пока поток работает процессор ожидает
                        while (m.isAlive()) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //когда поток завершился он удаляется из очереди потоков
                        listThread.remove(number);

                        //задержка переключения между потоками
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                }
        }));
        y.start();
    }
    //поиск минимального потока(необходимое условие в соответствии с заданием)
    public int SearchMinLength() {
        int position = 0;
        int min = 10000;
        for (int i = 0; i < listThread.size(); i++) {
            if(listThread.get(i).getLength() < min) {
                min = listThread.get(i).getLength();
                position = i;
            }
        }
        return position;
    }
}
