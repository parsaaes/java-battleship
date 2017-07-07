package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;

import javax.swing.*;
import java.util.LinkedList;

public class MainFrame implements LoginFrame.IMainFrameCallBack, MessageManager.IGUICallback, RequestsListFrame.IMainFrameServerRespondCallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame();
    RequestsListFrame requestsListFrame = new RequestsListFrame(this);
    private String username;
    public MainFrame() {
        loginFrame.runLoginFrame();

    }

    @Override
    public void onMessageMangerCreated(MessageManager messageManager, String type,String name) {
        this.messageManager = messageManager;
        this.username =name;
        this.messageManager.setiGUICallback(this);
        if (type.equals("HOST")) {
            System.out.println("gui list should be open");
            requestsListFrame.runFrame((LinkedList<NetworkHandler>) this.messageManager.getmNetworkHandlerList());
        } else {
            guestWaitingFrame.runFrame();
            this.messageManager.sendRequestLogin("server", name, "12345");
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

    @Override
    public void onServerRespondedRequest(int status, String name) {
        if (status == 1) {
            for (NetworkHandler networkHandler : this.messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    this.messageManager.setAcceptedNetworkHandler(networkHandler);
                    this.messageManager.sendServerAccepted(this.messageManager.getAcceptedNetworkHandler().getUsername(),1);
                    System.out.println("acceptednetwork set!!!");
                    break;
                }
            }
        } else if (status == -1) {
            for (NetworkHandler networkHandler : this.messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    System.out.println("Network handler was declined;;;;");
                    this.messageManager.sendServerAccepted(networkHandler.getUsername(),-1);
                    networkHandler.stopSelf();
                    break;
                }
            }
        }
    }
}
