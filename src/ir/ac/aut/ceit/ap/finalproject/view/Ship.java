package ir.ac.aut.ceit.ap.finalproject.view;

/**
 * Created by NP on 7/9/2017.
 */
public class Ship {
    private int xCord;
    private int yCord;
    private int size;
    private boolean isHorizontal;

    public Ship(int xCord, int yCord, int size, boolean isHorizontal) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.size = size;
        this.isHorizontal = isHorizontal;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

}
