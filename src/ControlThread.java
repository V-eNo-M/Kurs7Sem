import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 1 on 01.10.2015.
 */
public class ControlThread {
    List<MyThread> listThread = new ArrayList<MyThread>();
    private int countSleepCreateThread = 2500;

    public void GenerationThreat() {
        Random rand = new Random();
        (new Thread(new Runnable() {
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
        }) ).start();

        Thread y = (new Thread(new Runnable() {
            int number = 0;
            @Override
            public void run() {
               // while (listThread.size() > 0) {
                while(true) {
                    try {
                        number = SearchMinLength();
                        Thread m = new Thread(listThread.get(number));
                        System.out.println(listThread.get(number).getLength());
                        System.out.println("Сейчас в очереди: " + listThread.size());

                        for (int i = 0; i < listThread.size(); i++) {
                            System.out.println( "Имя потока: " + listThread.get(i).getName() + "  Длинна потока: " + listThread.get(i).getLength());
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
                    } catch (Exception e) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
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
