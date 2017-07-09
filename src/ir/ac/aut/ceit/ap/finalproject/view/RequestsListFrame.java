package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class RequestsListFrame implements RequestComponent.IRequestListFrameCallback {
    private JFrame jFrame;
    private JPanel jPanel;
    private IMainFrameServerRespondCallback iMainFrameServerRespondCallback;
    private LinkedList<NetworkHandler> networkHandlerList;

    public RequestsListFrame(IMainFrameServerRespondCallback iMainFrameServerRespondCallback) {
        this.iMainFrameServerRespondCallback = iMainFrameServerRespondCallback;
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

    public void closeFrame(){
        jFrame.dispose();
    }

    public void updateList() {
        System.out.println("UPDATE LIST CALLED !!!");
        jFrame.getContentPane().removeAll();
        jPanel = new JPanel();
        jFrame.add(jPanel, BorderLayout.CENTER);
        jPanel.setLayout(new GridLayout(10, 1));

        jFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                JOptionPane.showMessageDialog(null,"exiting ...");
                // should send a message to server to close [callback]
                iMainFrameServerRespondCallback.onRequestListClosed();
            }
        });

        if (networkHandlerList != null) {
            for (NetworkHandler networkHandler : networkHandlerList) {
                if (networkHandler != null) {
                    RequestComponent requestComponent = new RequestComponent(networkHandler.getUsername(),(RequestComponent.IRequestListFrameCallback)this);
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

    @Override
    public void onServerRespondsConnection(int acceptStatus,String name) {
        iMainFrameServerRespondCallback.onServerRespondedRequest(acceptStatus,name);
    }

    public interface IMainFrameServerRespondCallback {
        void onServerRespondedRequest(int status, String name);
        void onRequestListClosed();
    }

}
