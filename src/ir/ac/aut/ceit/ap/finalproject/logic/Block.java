package ir.ac.aut.ceit.ap.finalproject.logic;

import javax.swing.*;
import java.awt.*;


public class Block extends JButton {
    private int blockStatus;
    private int xCord;
    private int yCord;

    /*
    blockStatus =>
    ------- sunk your board :
    0 -> free [cyan]
    1 -> a ship is here [black]
    2 -> cant be sunk (around a ship) [white]

    ------- enemy's board & (you board enemy's attack result)
    3 -> not approved attack
    4 -> approved attack
     */

    public Block(String text, int xCord, int yCord, int blockStatus) {
        super(text);
        super.setBackground(Color.CYAN);
        this.xCord = xCord;
        this.yCord = yCord;
        this.blockStatus = blockStatus;
        super.setPreferredSize(new Dimension(32, 32));
        super.setSize(new Dimension(32, 32));
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public int getBlockStatus() {
        return blockStatus;
    }


    public void setBlockStatus(int blockStatus) {
        this.blockStatus = blockStatus;
    }

    public void setColor() {
        if (blockStatus == 0) {
            super.setBackground(Color.CYAN);
        } else if (blockStatus == 1) {
            super.setBackground(Color.GRAY);
        } else if (blockStatus == 2) {
            super.setBackground(Color.WHITE);
        } else if (blockStatus == 3) {
            super.setBackground(Color.ORANGE);
        } else if (blockStatus == 4) {
            super.setBackground(Color.RED);
        }
    }

}

