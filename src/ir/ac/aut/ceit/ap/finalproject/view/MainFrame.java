package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;

import javax.swing.*;

public class MainFrame implements LoginFrame.IMainFrameCallBack,MessageManager.IGUICallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame();

    public MainFrame(){
        loginFrame.runLoginFrame();

    }

    @Override
    public void onMessageMangerCreated(MessageManager messageManager,String type) {
        this.messageManager = messageManager;
        messageManager.setiGUICallback(this);
        if(type.equals("HOST")){
            System.out.println("gui list should be open");
            messageManager.sendServerAccepted(messageManager.getmNetworkHandlerList().get(0).getUsername(),1);
        }
        else {
            guestWaitingFrame.runFrame();
        }
    }

    @Override
    public void onHostAccepted(int status) {
    guestWaitingFrame.closeFrame();
    if(status == 1){
        System.out.println("game is starting");
    }
    else {
        JOptionPane.showMessageDialog(null,"Server cancelled your request! \n try again later.");
        System.exit(1);
    }
    }
}
