package ir.ac.aut.ceit.ap.finalproject.view;


import javax.swing.*;
import java.awt.*;

public class GuestWaitingFrame {
    private JFrame jFrame = new JFrame("Waiting");

    public GuestWaitingFrame(){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300,70);
        jFrame.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("Waiting for server to accept...");
        jFrame.add(jLabel);
    }

    public void closeFrame(){
        jFrame.dispose();
    }

    public void runFrame(){
        jFrame.setVisible(true);
    }

}
