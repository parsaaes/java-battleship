package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;

import javax.swing.*;
import java.util.LinkedList;

public class MainFrame implements LoginFrame.IMainFrameCallBack, MessageManager.IGUICallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame();
    RequestsListFrame requestsListFrame = new RequestsListFrame();

    public MainFrame() {
        loginFrame.runLoginFrame();

    }

    @Override
    public void onMessageMangerCreated(MessageManager messageManager, String type) {
        this.messageManager = messageManager;
        this.messageManager.setiGUICallback(this);
        if (type.equals("HOST")) {
            System.out.println("gui list should be open");
            requestsListFrame.runFrame((LinkedList<NetworkHandler>) this.messageManager.getmNetworkHandlerList());
        } else {
            guestWaitingFrame.runFrame();
            this.messageManager.sendRequestLogin("server", "user!!!", "12345");
        }
    }

    public RequestsListFrame getRequestsListFrame() {
        return requestsListFrame;
    }

    @Override
    public void onHostAccepted(int status) {
        guestWaitingFrame.closeFrame();
        if (status == 1) {
            System.out.println("game is starting");
        } else {
            JOptionPane.showMessageDialog(null, "Server cancelled your request! \n try again later.");
            System.exit(1);
        }
    }
}
