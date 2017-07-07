package ir.ac.aut.ceit.ap.finalproject;

import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.view.GuestWaitingFrame;
import ir.ac.aut.ceit.ap.finalproject.view.LoginFrame;

public class Main {

    public static void main(String[] args) {
//        LoginFrame loginFrame = new LoginFrame();
//        loginFrame.runLoginFrame();
     MessageManager messageManager = new MessageManager("127.0.0.1",12345);
     messageManager.sendRequestLogin("ss","ss","ss");

    }
}
