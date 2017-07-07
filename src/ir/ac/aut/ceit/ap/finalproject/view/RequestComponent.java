package ir.ac.aut.ceit.ap.finalproject.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RequestComponent extends JPanel {
    private JLabel nameLabel;
    private JButton declineButton;
    private JButton acceptButton;

    public void hideMe() {
        acceptButton.setVisible(false);
        declineButton.setVisible(false);
        nameLabel.setVisible(false);
        setVisible(false);
    }

    public RequestComponent(String name) {
        nameLabel = new JLabel(name);
        acceptButton = new JButton("Accept");
        declineButton = new JButton("Decline");
        setLayout(new FlowLayout());
        add(nameLabel);
        add(declineButton);
        add(acceptButton);
        acceptButton.addActionListener(new ButtonHandler());
        declineButton.addActionListener(new ButtonHandler());
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
    }

    private class ButtonHandler implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == acceptButton) {
                JOptionPane.showMessageDialog(null, "Accepted his request");

            } else if (e.getSource() == declineButton) {
                hideMe();
            }
        }
    }
}
