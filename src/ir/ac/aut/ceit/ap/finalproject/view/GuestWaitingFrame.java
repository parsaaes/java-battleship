package ir.ac.aut.ceit.ap.finalproject.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuestWaitingFrame {
    private JFrame jFrame = new JFrame("Waiting");


    public GuestWaitingFrame(){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300,70);
        jFrame.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("Waiting for server to accept...");
        jFrame.add(jLabel);
        jFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                // should send a message to server to close [callback]
            }
        });
    }

    public void closeFrame(){
        jFrame.dispose();
    }

    public void runFrame(){
        jFrame.setVisible(true);
    }

}
