package ir.ac.aut.ceit.ap.finalproject.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NP on 7/9/2017.
 */
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

    ------- enemy's board :
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


}

