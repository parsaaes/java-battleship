package ir.ac.aut.ceit.ap.finalproject.logic;


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

    @Override
    public boolean equals(Object obj) {
        if (xCord == ((Ship) (obj)).getxCord() && yCord == ((Ship) (obj)).getyCord() && size == ((Ship) (obj)).getSize()
                && isHorizontal == ((Ship) (obj)).isHorizontal()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equalsStartPoint(Object obj) {
        if (xCord == ((Ship) (obj)).getxCord() && yCord == ((Ship) (obj)).getyCord() && size == ((Ship) (obj)).getSize()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + xCord + "," + yCord + ")" + "[" + isHorizontal + "]" + "-" + size + "\n";
    }
}
