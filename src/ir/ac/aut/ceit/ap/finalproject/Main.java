package ir.ac.aut.ceit.ap.finalproject;

import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.view.GuestWaitingFrame;
import ir.ac.aut.ceit.ap.finalproject.view.LoginFrame;
import ir.ac.aut.ceit.ap.finalproject.view.MainFrame;
import ir.ac.aut.ceit.ap.finalproject.view.SplashScreen;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        Thread musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Player player = new Player(new FileInputStream("game.mp3"));
                    player.play();
                } catch (JavaLayerException e) {
                    System.out.println("no music");
                } catch (FileNotFoundException e) {
                    System.out.println("no music");
                }
            }
        });
        musicThread.start();
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
