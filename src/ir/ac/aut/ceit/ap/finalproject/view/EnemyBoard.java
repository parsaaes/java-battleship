package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.Block;
import ir.ac.aut.ceit.ap.finalproject.logic.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EnemyBoard {
    JFrame jFrame;
    JPanel boardPanel;
    JPanel buttonPanel;
    JPanel jPanel;
    private JButton[][] jButtons = new JButton[10][10];
    private ButtonHandler buttonHandler = new ButtonHandler();



    public JButton[][] getjButtons() {
        return jButtons;
    }

    public EnemyBoard() {
        jPanel = new JPanel();
        boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());
        jPanel.setLayout(new GridLayout(10, 10));
        jPanel.setPreferredSize(new Dimension(400, 400));
        jPanel.setSize(new Dimension(400, 400));
        //jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                jButtons[x][y] = new Block(x + "," + y, x, y, 0);
                //jButtons[x][y].
                jButtons[x][y].setOpaque(true);
                jButtons[x][y].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.lightGray));
                jButtons[x][y].addActionListener(buttonHandler);
                jPanel.add(jButtons[x][y]);
            }
        }
        jPanel.setPreferredSize(new Dimension(500, 500));
    }





    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Block block = (Block) e.getSource();
            /*
            here ---> mainframe [callback]
            mainframe ---> messageManager
            enemy should check attacked block and set that black and send status message
            messageManager ---> mainFrame [callback]
            mainFrame ---> here should update attacked block status (for 3 second)
             */

        }
    }





    private void updateBoard() {
        jPanel.revalidate();
        jPanel.repaint();
    }

    public JPanel runFrame() {

        boardPanel.add(jPanel);
        //boardPanel.add(buttonPanel, BorderLayout.SOUTH);
        boardPanel.setPreferredSize(new Dimension(500, 500));
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new FlowLayout());
        testPanel.add(boardPanel);
        return testPanel;

    }



    }



