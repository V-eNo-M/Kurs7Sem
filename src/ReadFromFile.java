import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Lida on 15.11.2015.
 */
public class ReadFromFile {
    private String key = "";
    private final String fileNameKey = new String("process.txt");

    public ReadFromFile() throws FileNotFoundException {
        Read();
    }
    //читаем из файла
    private void Read() throws FileNotFoundException {
        Scanner in = null;
        try {
            in = new Scanner(new File(fileNameKey));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()){
        key += in.nextLine();
        }
        in.close();
    }

    public String getKey() {
        return key;
    }
}
