package ir.ac.aut.ceit.ap.finalproject;

import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;
import ir.ac.aut.ceit.ap.finalproject.logic.RequestLoginMessage;

public class Main {

    public static void main(String[] args) {
        MessageManager messageManager = new MessageManager("127.0.0.1",12345);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3rd main");
        for (NetworkHandler networkHandler : messageManager.getmNetworkHandlerList()) {
            System.out.println(networkHandler);
        }
        messageManager.sendRequestLogin("user1","iamUserName","iamPassword");

    }
}
