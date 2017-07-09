package ir.ac.aut.ceit.ap.finalproject.view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SplashScreen {
    private JFrame jFrame;

    public SplashScreen(String path) {
        jFrame = new JFrame();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("No screen added");
        }

        JPanel jPanel = new JPanel();
        if (bufferedImage != null) {
            jPanel.add(new JLabel(new ImageIcon(bufferedImage)));
            jFrame.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
        } else {
            jPanel.add(new JLabel("Battle Ship"));

            jFrame.setSize(400, 400);
        }
        jFrame.setUndecorated(true);
        jFrame.add(jPanel);

        jFrame.setLocation(400, 100);
    }

    public void show(int time) {
        jFrame.setVisible(true);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jFrame.setVisible(false);
    }
}
