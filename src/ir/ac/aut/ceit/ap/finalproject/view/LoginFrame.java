package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginFrame {

    private JFrame jFrame = new JFrame();
    private JLabel nameLabel = new JLabel("Name : ");
    private JTextField nameTextField = new JTextField();
    private ButtonGroup radioGroup = new ButtonGroup();
    private JRadioButton hostModeButton = new JRadioButton("Host");
    private JRadioButton guestModeButton = new JRadioButton("Guest");
    private JLabel hostPortLabel = new JLabel("Port : ");
    private JTextField hostPortTextField = new JTextField();
    private JLabel guestIpLabel = new JLabel("ip : ");
    private JTextField guestIpTextField = new JTextField();
    private JLabel guestPortLabel = new JLabel("Port : ");
    private JTextField guestPortTextField = new JTextField();
    private JButton startButton = new JButton("Start");
    private JButton exitButton = new JButton("Exit");
    private Boolean isHostMode;

    public LoginFrame(){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(350,400);
        jFrame.setLayout(new GridLayout(7,1));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(nameLabel);
        nameTextField.setColumns(15);
        namePanel.add(nameTextField);
        jFrame.add(namePanel);

        hostModeButton.setSelected(true);
        hostModeButton.addItemListener(new RadioHandler());

        radioGroup.add(hostModeButton);
        radioGroup.add(guestModeButton);

        JPanel hostInfoPanel = new JPanel();
        hostInfoPanel.setLayout(new FlowLayout());
        hostInfoPanel.add(hostPortLabel);
        hostPortTextField.setColumns(15);
        hostInfoPanel.add(hostPortTextField);

        JPanel guestInfoPanel = new JPanel();
        guestInfoPanel.setLayout(new FlowLayout());
        guestInfoPanel.add(guestIpLabel);
        guestIpTextField.setColumns(10);
        guestInfoPanel.add(guestIpTextField);
        guestInfoPanel.add(guestPortLabel);
        guestPortTextField.setColumns(10);
        guestInfoPanel.add(guestPortTextField);

        jFrame.add(hostModeButton);
        jFrame.add(hostInfoPanel);
        jFrame.add(guestModeButton);
        jFrame.add(guestInfoPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(exitButton);
        buttonsPanel.add(startButton);

        jFrame.add(buttonsPanel);


    }

    public void runLoginFrame(){
        jFrame.setVisible(true);
    }

    private class RadioHandler implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(hostModeButton.isSelected()){
                isHostMode = true;
            }
            else {
                isHostMode = false;
            }
            System.out.println("is host mode ? "+isHostMode);
        }

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startButton) {
                if(isHostMode){
                    MessageManager messageManager = new MessageManager(Integer.parseInt(hostPortTextField.getText()));
                }
                else {
                    MessageManager messageManager = new MessageManager(guestIpTextField.getText(),Integer.parseInt(guestPortTextField.getText()));
                }
            }
            else if(e.getSource() == exitButton) {

            }
        }
    }



}
