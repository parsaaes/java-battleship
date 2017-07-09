package ir.ac.aut.ceit.ap.finalproject.view;

import ir.ac.aut.ceit.ap.finalproject.logic.Block;
import ir.ac.aut.ceit.ap.finalproject.logic.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board {
    JFrame jFrame;
    JPanel boardPanel;
    JPanel buttonPanel;
    JPanel jPanel;
    private JButton[][] jButtons = new JButton[10][10];
    private ButtonHandler buttonHandler = new ButtonHandler();

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
    adding size 1
    adding size 2
    adding size 3
    adding size 4
    removing size -1
    removing size -2
    removing size -3
    removing size -4
     */
    private int addOrRemoveStatus = 0;

    public void setAddOrRemoveStatus(int addOrRemoveStatus) {
        this.addOrRemoveStatus = addOrRemoveStatus;
    }

    public boolean canReady(){
        int check = numberOfOneBlockShipToDeploy + numberOfTwoBlockShipToDeploy + numberOfThreeBlockShipToDeploy + numberOfFourBlockShipToDeploy;
        System.out.println(check);
        if(check == 0){
            return true;
        }
        else {
            return false;
        }
    }
    private ArrayList<Ship> shipsList = new ArrayList<>();

    public JButton[][] getjButtons() {
        return jButtons;
    }

    public Board() {
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
            changeNumberOfAvailableShips(ship.getSize(), false);
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
        changeNumberOfAvailableShips(ship.getSize(), true);
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

    public JPanel getButtonPanel() {
        return buttonPanel;
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

    private void changeNumberOfAvailableShips(int size, boolean isIncreasing) {
        if (isIncreasing) {
            if (size == 1) {
                numberOfOneBlockShipToDeploy += 1;
            } else if (size == 2) {
                numberOfTwoBlockShipToDeploy += 1;
            } else if (size == 3) {
                numberOfThreeBlockShipToDeploy += 1;
            } else if (size == 4) {
                numberOfFourBlockShipToDeploy += 1;
            }
        } else {
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
//        jFrame.revalidate();
//        jFrame.repaint();
    }

    public JPanel runFrame() {
//        jFrame = new JFrame();
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.setLayout(new FlowLayout());
        buttonPanel = new JPanel();
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
        buttonPanel.setLayout(new GridLayout(2, 1));
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

//        jFrame.setSize(700, 700);
        jPanel.setFocusable(true);
        jPanel.requestFocusInWindow();
        jPanel.addKeyListener(new rotateKeyboardHandler());
        boardPanel.add(jPanel);
        boardPanel.add(buttonPanel, BorderLayout.SOUTH);
        boardPanel.setPreferredSize(new Dimension(500, 500));
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new FlowLayout());
        testPanel.add(boardPanel);
        return testPanel;
//        jFrame.add(testPanel);
//        //jFrame.pack();
//
//        jFrame.setVisible(true);
    }

    private void makeBorderForShips(int xCord, int yCord, int size, boolean isHorizontal) {
        if (isHorizontal) {
            int borderStartX = xCord - 1;
            int borderStartY = yCord - 1;
            for (int y = borderStartY; y < borderStartY + 3; y++) {
                for (int x = borderStartX; x < borderStartX + size + 2; x++) {
                    if (0 <= y && y <= 9 && 0 <= x && x <= 9) {
                        if (!(xCord <= x && x <= xCord + size - 1 && yCord == y)) {
                            ((Block) (jButtons[x][y])).setBlockStatus(2);
                            ((Block) (jButtons[x][y])).setColor();
                        }
                    }
                }
            }
        } else {
            int borderStartX = xCord - 1;
            int borderStartY = yCord - 1;
            for (int x = borderStartX; x < borderStartX + 3; x++) {
                for (int y = borderStartY; y < borderStartY + size + 2; y++) {
                    if (0 <= y && y <= 9 && 0 <= x && x <= 9) {
                        if (!(yCord <= y && y <= yCord + size - 1 && xCord == x)) {
                            ((Block) (jButtons[x][y])).setBlockStatus(2);
                            ((Block) (jButtons[x][y])).setColor();
                        }
                    }
                }
            }
        }
        updateBoard();

    }

    private void removeBorderForShips(int xCord, int yCord, int size, boolean isHorizontal) {
        if (isHorizontal) {
            int borderStartX = xCord - 1;
            int borderStartY = yCord - 1;
            for (int y = borderStartY; y < borderStartY + 3; y++) {
                for (int x = borderStartX; x < borderStartX + size + 2; x++) {
                    if (0 <= y && y <= 9 && 0 <= x && x <= 9) {
                        if (!(xCord <= x && x <= xCord + size - 1 && yCord == y)) {
                            ((Block) (jButtons[x][y])).setBlockStatus(0);
                            ((Block) (jButtons[x][y])).setColor();
                        }
                    }
                }
            }
        } else {
            int borderStartX = xCord - 1;
            int borderStartY = yCord - 1;
            for (int x = borderStartX; x < borderStartX + 3; x++) {
                for (int y = borderStartY; y < borderStartY + size + 2; y++) {
                    if (0 <= y && y <= 9 && 0 <= x && x <= 9) {
                        if (!(yCord <= y && y <= yCord + size - 1 && xCord == x)) {
                            ((Block) (jButtons[x][y])).setBlockStatus(0);
                            ((Block) (jButtons[x][y])).setColor();
                        }
                    }
                }
            }
        }
        updateBoard();

    }

    private boolean checkIfForwardIsReady(int xCord, int yCord, int size, boolean isHorizontal) {
        if (isHorizontal) {
            if (xCord + size <= 10) {
                for (int x = xCord; x < xCord + size; x++) {
                    if (((Block) jButtons[x][yCord]).getBlockStatus() == 0) {
                        //do nothing
                    } else {
                        System.out.println("i returnd false");
                        return false;
                    }
                }
                return true;
            }
        } else {
            if (yCord + size <= 10) {
                for (int y = yCord; y < yCord + size; y++) {
                    if (((Block) jButtons[xCord][y]).getBlockStatus() == 0) {
                        //do nothing
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkIfForwardIsReadyForRemove(int xCord, int yCord, int size, boolean isHorizontal) {
        if (isHorizontal) {
            if (xCord + size <= 10) {
                for (int x = xCord; x < xCord + size; x++) {
                    if (((Block) jButtons[x][yCord]).getBlockStatus() == 1) {
                        //do nothing
                    } else {
                        System.out.println("i returnd false");
                        return false;
                    }
                }
                return true;
            }
        } else {
            if (yCord + size <= 10) {
                for (int y = yCord; y < yCord + size; y++) {
                    if (((Block) jButtons[xCord][y]).getBlockStatus() == 1) {
                        //do nothing
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void rotateLastShip() {
        if (!shipsList.isEmpty()) {
            Ship ship = shipsList.get(shipsList.size() - 1);
            removeShip(ship);
            if (checkIfForwardIsReady(ship.getxCord(), ship.getyCord(), ship.getSize(), !ship.isHorizontal())) {
                ship.setHorizontal(!ship.isHorizontal());
                addShip(ship);
            } else {
                addShip(ship);
            }
        }
    }

    private class ShipsAddingButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (((JButton) (e.getSource())).getText().equals("*")) {
                addOrRemoveStatus = 1;
            } else if (((JButton) (e.getSource())).getText().equals("- *")) {
                addOrRemoveStatus = -1;
            } else if (((JButton) (e.getSource())).getText().equals("**")) {
                addOrRemoveStatus = 2;
            } else if (((JButton) (e.getSource())).getText().equals("- **")) {
                addOrRemoveStatus = -2;
            } else if (((JButton) (e.getSource())).getText().equals("***")) {
                addOrRemoveStatus = 3;
            } else if (((JButton) (e.getSource())).getText().equals("- ***")) {
                addOrRemoveStatus = -3;
            } else if (((JButton) (e.getSource())).getText().equals("****")) {
                addOrRemoveStatus = 4;
            } else if (((JButton) (e.getSource())).getText().equals("- ****")) {
                addOrRemoveStatus = -4;
            }
        }
    }

    private class rotateKeyboardHandler implements KeyListener {
        boolean rDown = false;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getModifiers() == InputEvent.CTRL_MASK) {
                if (e.getKeyCode() == 82) {
                    System.out.println("CTRL + R");
                    rotateLastShip();
                }
            }
        }

    }
}
