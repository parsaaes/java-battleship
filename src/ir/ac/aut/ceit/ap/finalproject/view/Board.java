package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.Block;
import ir.ac.aut.ceit.ap.finalproject.logic.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by NP on 7/9/2017.
 */
public class Board {
    JFrame jFrame;
    JPanel boardPanel;
    JPanel jPanel;
    JButton[][] jButtons = new JButton[10][10];
    private boolean isRemoving = false;
    private int numberOfOneBlockShipToDeploy = 4;
    private int numberOfTwoBlockShipToDeploy = 3;
    private int numberOfThreeBlockShipToDeploy = 2;
    private int numberOfFourBlockShipToDeploy = 1;

    private JTextField oneShipField;
    private JTextField twoShipField;
    private JTextField threeShipField;
    private JTextField fourShipField;

    private final int numberOfAllOneBlockShip = 4;
    private final int numberOfAllTwoBlockShip = 3;
    private final int numberOfAllThreeBlockShip = 2;
    private final int numberOfAllFourBlockShip = 1;
    /*
    addOrRemoveStatus :
    adding size 1
    adding size 2
    adding size 3
    adding size 4
    removing size -1
    removing size -2
    removing size -3
    removing size -4
     */
    private int addOrRemoveStatus = 1;

    private ArrayList<Ship> shipsList = new ArrayList<>();

    public Board() {
        jPanel = new JPanel();
        boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());
        jPanel.setLayout(new GridLayout(10, 10));
        jPanel.setPreferredSize(new Dimension(400,400));
        jPanel.setSize(new Dimension(400,400));
        //jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ButtonHandler buttonHandler = new ButtonHandler();

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

    public void addShip(Ship ship) {
        int xCord = ship.getxCord();
        int yCord = ship.getyCord();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();

        if (checkIfForwardIsReady(xCord, yCord, size, isHorizontal)) {
            if (isHorizontal) {
                for (int i = xCord; i < xCord + size; i++) {
                    Block block = (Block) jButtons[i][yCord];
                    block.setBlockStatus(1);
                    block.setColor();
                }
            } else {
                for (int i = yCord; i < yCord + size; i++) {
                    Block block = (Block) jButtons[xCord][i];
                    block.setBlockStatus(1);
                    block.setColor();
                }
            }
            changeNumberOfAvailableShips(ship.getSize(),false);
            makeBorderForShips(xCord, yCord, size, isHorizontal);
            shipsList.add(ship);
            System.out.println("added " + ship);
        }

        //updateBoard();
    }

    public void removeShip(Ship shipToBeRemoved) {
        Ship removingShip = null;
        for (Ship ship : shipsList) {
            if (ship.equalsStartPoint(shipToBeRemoved)) {
                removingShip = ship;
                System.out.println("equals :D");
                break;
            }
        }
        if (removingShip == null) {
            return;
        }
        Ship ship = removingShip;
        int xCord = ship.getxCord();
        int yCord = ship.getyCord();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();
        shipsList.remove(shipsList.indexOf(ship));
        changeNumberOfAvailableShips(ship.getSize(),true);
        System.out.println(shipsList);
        if (checkIfForwardIsReadyForRemove(xCord, yCord, size, isHorizontal)) {
            if (isHorizontal) {
                for (int i = xCord; i < xCord + size; i++) {
                    Block block = (Block) jButtons[i][yCord];
                    block.setBlockStatus(0);
                    block.setColor();
                }
            } else {
                for (int i = yCord; i < yCord + size; i++) {
                    Block block = (Block) jButtons[xCord][i];
                    block.setBlockStatus(0);
                    block.setColor();
                }
            }
            removeBorderForShips(xCord, yCord, size, isHorizontal);
            for (Ship aShip : shipsList) {
                makeBorderForShips(aShip.getxCord(), aShip.getyCord(), aShip.getSize(), aShip.isHorizontal());
            }
        }
        //updateBoard();
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Block block = (Block) e.getSource();
//            if (addOrRemoveStatus == 1) {
//                addShip(new Ship(block.getxCord(), block.getyCord(), 1, true));
//                System.out.println("called by " + ((Block) e.getSource()).getText());
//                System.out.println(shipsList);
//            } else if (addOrRemoveStatus == -1) {
//                //System.out.println(shipsList);
//                    removeShip(new Ship(block.getxCord(), block.getyCord(), 1, true));
//
//            }
//            else if (addOrRemoveStatus == 2) {
//                addShip(new Ship(block.getxCord(), block.getyCord(), 2, true));
//                System.out.println("called by " + ((Block) e.getSource()).getText());
//                System.out.println(shipsList);
//            } else if (addOrRemoveStatus == -2) {
//                //System.out.println(shipsList);
//                removeShip(new Ship(block.getxCord(), block.getyCord(), 2, true));
//            }
//            else if (addOrRemoveStatus == 3) {
//                addShip(new Ship(block.getxCord(), block.getyCord(), 3, true));
//                System.out.println("called by " + ((Block) e.getSource()).getText());
//                System.out.println(shipsList);
//            } else if (addOrRemoveStatus == -3) {
//                //System.out.println(shipsList);
//                removeShip(new Ship(block.getxCord(), block.getyCord(), 3, true));
//            }
//            else if (addOrRemoveStatus == 4) {
//                addShip(new Ship(block.getxCord(), block.getyCord(), 4, true));
//                System.out.println("called by " + ((Block) e.getSource()).getText());
//                System.out.println(shipsList);
//            } else if (addOrRemoveStatus == -4) {
//                //System.out.println(shipsList);
//                removeShip(new Ship(block.getxCord(), block.getyCord(), 4, true));
//            }
            if (addOrRemoveStatus > 0) {
                if (0 < getNumberOfAvailableShips(addOrRemoveStatus)) {
                    addShip(new Ship(block.getxCord(), block.getyCord(), addOrRemoveStatus, true));
                    System.out.println("called by " + ((Block) e.getSource()).getText());
                    System.out.println(shipsList);
                }
            } else if (addOrRemoveStatus < 0) {
                if (getNumberOfAllShips(-1 * addOrRemoveStatus) > getNumberOfAvailableShips(-1 * addOrRemoveStatus)) {
                    removeShip(new Ship(block.getxCord(), block.getyCord(), addOrRemoveStatus * -1, true));
                }
            }
        }
    }


    private int getNumberOfAvailableShips(int size) {
        if (size == 1) {
            return numberOfOneBlockShipToDeploy;
        } else if (size == 2) {
            return numberOfTwoBlockShipToDeploy;
        } else if (size == 3) {
            return numberOfThreeBlockShipToDeploy;
        } else if (size == 4) {
            return numberOfFourBlockShipToDeploy;
        } else {
            return -1;
        }
    }
    private int getNumberOfAllShips(int size) {
        if (size == 1) {
            return numberOfAllOneBlockShip;
        } else if (size == 2) {
            return numberOfAllTwoBlockShip;
        } else if (size == 3) {
            return numberOfAllThreeBlockShip;
        } else if (size == 4) {
            return numberOfAllFourBlockShip;
        } else {
            return -1;
        }
    }

    private void changeNumberOfAvailableShips(int size , boolean isIncreasing) {
        if(isIncreasing) {
            if (size == 1) {
                numberOfOneBlockShipToDeploy += 1;
            } else if (size == 2) {
                numberOfTwoBlockShipToDeploy += 1;
            } else if (size == 3) {
                numberOfThreeBlockShipToDeploy += 1;
            } else if (size == 4) {
                numberOfFourBlockShipToDeploy += 1;
            }
        }
        else  {
            if (size == 1) {
                numberOfOneBlockShipToDeploy -= 1;
            } else if (size == 2) {
                numberOfTwoBlockShipToDeploy -= 1;
            } else if (size == 3) {
                numberOfThreeBlockShipToDeploy -= 1;
            } else if (size == 4) {
                numberOfFourBlockShipToDeploy -= 1;
            }
        }
    }

    private void updateBoard() {
        oneShipField.setText(String.valueOf(numberOfOneBlockShipToDeploy));
        twoShipField.setText(String.valueOf(numberOfTwoBlockShipToDeploy));
        threeShipField.setText(String.valueOf(numberOfThreeBlockShipToDeploy));
        fourShipField.setText(String.valueOf(numberOfFourBlockShipToDeploy));
        jPanel.revalidate();
        jPanel.repaint();
        jPanel.setFocusable(true);
        jPanel.requestFocusInWindow();
        jFrame.revalidate();
        jFrame.repaint();
    }

    public void runFrame() {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());
        JPanel buttonPanel = new JPanel();
        oneShipField = new JTextField(2);
        twoShipField = new JTextField(2);
        threeShipField = new JTextField(2);
        fourShipField = new JTextField(2);
        JButton removeOne = new JButton("- *");
        JButton addOne = new JButton("*");
        JButton removeTwo = new JButton("- **");
        JButton addTwo = new JButton("**");
        JButton removeThree = new JButton("- ***");
        JButton addThree = new JButton("***");
        JButton removeFour = new JButton("- ****");
        JButton addFour = new JButton("****");
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.add(oneShipField);
        buttonPanel.add(addOne);
        buttonPanel.add(twoShipField);
        buttonPanel.add(addTwo);
        buttonPanel.add(threeShipField);
        buttonPanel.add(addThree);
        buttonPanel.add(fourShipField);
        buttonPanel.add(addFour);
        buttonPanel.add(removeOne);
        buttonPanel.add(removeTwo);
        buttonPanel.add(removeThree);
        buttonPanel.add(removeFour);
        ShipsAddingButton shipsAddingButton = new ShipsAddingButton();
        removeOne.addActionListener(shipsAddingButton);
        addOne.addActionListener(shipsAddingButton);
        removeTwo.addActionListener(shipsAddingButton);
        addTwo.addActionListener(shipsAddingButton);
        removeThree.addActionListener(shipsAddingButton);
        addThree.addActionListener(shipsAddingButton);
        removeFour.addActionListener(shipsAddingButton);
        addFour.addActionListener(shipsAddingButton);

        jFrame.setSize(700, 700);
        jPanel.setFocusable(true);
        jPanel.requestFocusInWindow();
        jPanel.addKeyListener(new rotateKeyboardHandler());
        boardPanel.add(jPanel);
        boardPanel.add(buttonPanel,BorderLayout.SOUTH);
        boardPanel.setPreferredSize(new Dimension(500,500));
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new FlowLayout());
        testPanel.add(boardPanel);
        jFrame.add(testPanel);
        //jFrame.pack();

        jFrame.setVisible(true);
    }
}
