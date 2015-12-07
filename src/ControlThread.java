import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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

        json = g.fromJson(read.getKey(), JsonElement.class).getAsJsonArray();
        for (int i = 0; i < json.size(); i++) {
            JsonArray jarr = g.fromJson(json.get(i).getAsJsonObject().get("Res"),JsonElement.class).getAsJsonArray();
            ArrayList<Res> res = new ArrayList<Res>();
            for (int j = 0; j < jarr.size(); j++) {
                res.add(new Res(jarr.get(j).getAsJsonObject().get("res").getAsString()));
                res.get(j).setLenght(jarr.get(j).getAsJsonObject().get("long").getAsInt());
            }

            listThread.add(new MyThread(json.get(i).getAsJsonObject().get("name").getAsString(), json.get(i).getAsJsonObject().get("long").getAsInt(), res));
        }

        /*(new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; ; i++)
                {
                    if(listThread.size() < 10)
                    listThread.add(new MyThread(i + "/", (rand.nextInt(30)+20)));
                    try {
                        Thread.sleep(countSleepCreateThread);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) ).start();*/

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
                                System.out.print(" Имя "+ listThread.get(i).getArrayRes().get(j).getName() + " Длительность " + listThread.get(i).getArrayRes().get(j).getLenght());
                            }
                            System.out.println();
                        }

                        m.run();
                        while (m.isAlive()) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        listThread.remove(number);

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
