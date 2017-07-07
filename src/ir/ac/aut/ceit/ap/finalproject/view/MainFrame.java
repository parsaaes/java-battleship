package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MainFrame implements LoginFrame.IMainFrameCallBack, MessageManager.IGUICallback, RequestsListFrame.IMainFrameServerRespondCallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame();
    RequestsListFrame requestsListFrame = new RequestsListFrame(this);
    private String username;
    JFrame jFrame = new JFrame();


    public MainFrame() {
        loginFrame.runLoginFrame();
        //loginFrame should be closed

        jFrame.setSize(900, 550);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel();
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        JTextField chatSendArea = new JTextField();
        JButton chatSendButton = new JButton("Send");
        chatSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(chatSendArea.getText());
                chatArea.append(username + " : " + chatSendArea.getText() + "\n");
                chatSendArea.setText("");
            }
        });
        chatSendButton.setPreferredSize(new Dimension(60, 20));
        JPanel chatButtonAndField = new JPanel();
        chatButtonAndField.setLayout(new BorderLayout());
        chatArea.setEditable(false);
        chatPanel.add(chatArea);
        chatButtonAndField.add(chatSendArea);
        chatButtonAndField.add(chatSendButton, BorderLayout.EAST);

        chatPanel.add(chatButtonAndField, BorderLayout.SOUTH);


        jFrame.add(gamePanel, BorderLayout.WEST);
        chatPanel.setPreferredSize(new Dimension(275, 550));
        chatPanel.setBorder(new MatteBorder(0, 1, 0, 0, Color.lightGray));
        jFrame.add(chatPanel, BorderLayout.EAST);


    }


    public void runFrame() {
        jFrame.setTitle("Playing with " + messageManager.getAcceptedNetworkHandler().getUsername());
        jFrame.setVisible(true);
    }

    public RequestsListFrame getRequestsListFrame() {
        return requestsListFrame;
    }




    @Override
    public void onMessageMangerCreated(MessageManager messageManager, String type, String name) {
        this.messageManager = messageManager;
        this.username = name;
        this.messageManager.setiGUICallback(this);
        if (type.equals("HOST")) {
            System.out.println("gui list should be open");
            requestsListFrame.runFrame((LinkedList<NetworkHandler>) this.messageManager.getmNetworkHandlerList());
        } else {
            guestWaitingFrame.runFrame();
            this.messageManager.sendRequestLogin("server", name, "12345");
        }
    }

    @Override
    public void onHostAccepted(int status,String serverUserName) {
        guestWaitingFrame.closeFrame();
        if (status == 1) {
            System.out.println("game is starting");
            messageManager.getmNetworkHandlerList().get(0).setUsername(serverUserName);
            messageManager.setAcceptedNetworkHandler(messageManager.getmNetworkHandlerList().get(0));
            loginFrame.closeFrame();
            runFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Server cancelled your request! \n try again later.");
            System.exit(1);
        }
    }

    @Override
    public void onServerRespondedRequest(int status, String name) {
        if (status == 1) {
            for (NetworkHandler networkHandler : messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    messageManager.setAcceptedNetworkHandler(networkHandler);
                    messageManager.sendServerAccepted(messageManager.getAcceptedNetworkHandler().getUsername(), 1,username);
                    System.out.println("acceptednetwork set!!!");
                    loginFrame.closeFrame();
                    requestsListFrame.closeFrame();
                    runFrame();
                    break;
                }
            }
        } else if (status == -1) {
            for (NetworkHandler networkHandler : messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    System.out.println("Network handler was declined;;;;");
                    messageManager.sendServerAccepted(networkHandler.getUsername(), -1,username);
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
