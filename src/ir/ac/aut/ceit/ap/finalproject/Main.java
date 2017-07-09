package ir.ac.aut.ceit.ap.finalproject;

import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.view.GuestWaitingFrame;
import ir.ac.aut.ceit.ap.finalproject.view.LoginFrame;
import ir.ac.aut.ceit.ap.finalproject.view.MainFrame;
import ir.ac.aut.ceit.ap.finalproject.view.SplashScreen;

public class Main {

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen("pic.png");
        splashScreen.show(5000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame();

    }
}
