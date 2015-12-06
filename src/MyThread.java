/**
 * Created by 1 on 01.10.2015.
 */
public class MyThread implements Runnable{
    private String name;
    private int length;

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }



    public MyThread(String name,int length){
        this.name = name;
        this.length = length;
    }

    @Override
    public void run() {
        System.out.println("Start thread " + name);
        try {
            System.out.println("My length = " + this.length);
            for (int i = 0; i < length; i++) {
                Thread.sleep(100);
                System.out.println("Time thread " + name + " - " + (length - i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Close thread " + name);
    }

}
