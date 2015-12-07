/**
 * Created by 1 on 07.12.2015.
 */
public class Res {
    private String name;
    private int lenght = 0;
    private boolean isUsed = false;

    public boolean getIsUsed() {
        return isUsed;
    }

    public String getName() {
        return name;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Res(String name) {
        this.name = name;
    }

}
