package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class RequestsListFrame {
    private JFrame jFrame;
    private JPanel jPanel;
    private LinkedList<NetworkHandler> networkHandlerList;
    public RequestsListFrame() {
        jFrame = new JFrame();
        jFrame.setSize(400, 800);
        jPanel = new JPanel();
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jPanel, BorderLayout.CENTER);
        jPanel.setLayout(new GridLayout(10, 1));
    }

    public void runFrame(LinkedList<NetworkHandler> networkHandlerList) {
        this.networkHandlerList = networkHandlerList;
        jFrame.setVisible(true);
    }
    public void updateList() {
        System.out.println("UPDATE LIST CALLED !!!");
        jFrame.getContentPane().removeAll();
        jPanel = new JPanel();
        jFrame.add(jPanel, BorderLayout.CENTER);
        jPanel.setLayout(new GridLayout(10, 1));
        if(networkHandlerList != null) {
            for (NetworkHandler networkHandler : networkHandlerList) {
                if(networkHandler != null) {
                    RequestComponent requestComponent = new RequestComponent(networkHandler.getUsername());
                    System.out.println(networkHandler.getUsername() + "IT SHOULD BE ADDED TO LIST");
                    jPanel.add(requestComponent);
                }
            }
        }
        jPanel.repaint();
        jPanel.revalidate();
        jFrame.repaint();
        jFrame.revalidate();
    }
}
