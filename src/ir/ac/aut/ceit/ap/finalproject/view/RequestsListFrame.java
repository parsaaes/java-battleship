package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class RequestsListFrame {
    private JFrame jframe;
    private JPanel jPanel;

    public RequestsListFrame() {
        jframe = new JFrame();
        jframe.setSize(400, 800);
        jPanel = new JPanel();
        jframe.setLayout(new BorderLayout());
        jframe.add(jPanel, BorderLayout.CENTER);
        jPanel.setLayout(new GridLayout(10, 1));
    }

    public void runFrame(LinkedList<NetworkHandler> networkHandlerList) {
        if (networkHandlerList != null) {
            for (NetworkHandler networkHandler : networkHandlerList) {
                if (networkHandler != null) {
                    RequestComponent requestComponent = new RequestComponent(networkHandler.getUsername());
                    jPanel.add(requestComponent);
                }
            }
        }
        jframe.setVisible(true);
    }
}
