package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.model.InputFileReader;
import ir.ac.aut.ceit.ap.finalproject.tools.JSONTools;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChatHistoryFrame {
    private JTextArea chatHistoryArea = new JTextArea();
    private JFrame chatHistoryTextFrame = new JFrame();
    private JFrame chatHistoryListFrame = new JFrame("Chat History");
    private String json;

    public ChatHistoryFrame(){
        chatHistoryListFrame.setLayout(new GridLayout(10,1));
        //chatHistoryListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatHistoryListFrame.setSize(400,900);
        chatHistoryTextFrame.setLayout(new GridLayout(1,1));
        //chatHistoryTextFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatHistoryTextFrame.setSize(400,900);
        chatHistoryArea.setEditable(false);
        chatHistoryTextFrame.add(chatHistoryArea);

        File file = new File("chathistory");
        File[] list = file.listFiles();
        for (File file1 : list) {
            if(file1.getAbsolutePath().substring(file1.getAbsolutePath().length()-4,file1.getAbsolutePath().length()).equals("json")){
                JButton jButton = new JButton("Chat on " + file1.getName().substring(0,file1.getName().length()-5));
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,file1 + " --selected");
                        json = InputFileReader.getJSON(file1.getAbsolutePath());
                        JSONTools.parseJSON(json,chatHistoryTextFrame,chatHistoryArea);
                        chatHistoryTextFrame.setVisible(true);

                    }
                });
                chatHistoryListFrame.add(jButton);
            }
        }
    }

    public void runFrame(){
        chatHistoryListFrame.setVisible(true);
    }
}
