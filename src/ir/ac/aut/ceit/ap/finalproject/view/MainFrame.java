package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.LinkedList;

public class MainFrame implements LoginFrame.IMainFrameCallBack, MessageManager.IGUICallback, RequestsListFrame.IMainFrameServerRespondCallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame();
    RequestsListFrame requestsListFrame = new RequestsListFrame(this);
    private String username;
    JFrame jFrame = new JFrame();


    public MainFrame() {
       // loginFrame.runLoginFrame();
        //loginFrame should be closed

        jFrame.setSize(900,550);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel();
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        JTextField chatSendArea = new JTextField();
        JButton chatSendButton = new JButton("Send");
        chatSendButton.setPreferredSize(new Dimension(60,20));
        JPanel chatButtonAndField = new JPanel();
        chatButtonAndField.setLayout(new BorderLayout());
        chatArea.setEnabled(false);
        chatPanel.add(chatArea);
        chatButtonAndField.add(chatSendArea);
        chatButtonAndField.add(chatSendButton,BorderLayout.EAST);

        chatPanel.add(chatButtonAndField,BorderLayout.SOUTH);


        jFrame.add(gamePanel,BorderLayout.WEST);
        chatPanel.setPreferredSize(new Dimension(275,550));
        chatPanel.setBorder(new MatteBorder(0,1,0,0,Color.lightGray));
        jFrame.add(chatPanel,BorderLayout.EAST);



    }


    public void runFrame(){
        jFrame.setVisible(true);
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
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.messageManager.getmNetworkHandlerList().remove(networkHandler);
                    networkHandler.stopSelf();
                    break;
                }
            }
        }
    }
}
