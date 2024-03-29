package ir.ac.aut.ceit.ap.finalproject.view;


import ir.ac.aut.ceit.ap.finalproject.logic.MessageManager;
import ir.ac.aut.ceit.ap.finalproject.logic.NetworkHandler;
import ir.ac.aut.ceit.ap.finalproject.model.OutputFileWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class MainFrame implements LoginFrame.IMainFrameCallBack, MessageManager.IGUICallback, RequestsListFrame.IMainFrameServerRespondCallback, EnemyBoard.IMainFrameToEnemyBoardCallback, GuestWaitingFrame.IMainFrameGuestWaitingCallback {
    LoginFrame loginFrame = new LoginFrame(this);
    MessageManager messageManager;
    GuestWaitingFrame guestWaitingFrame = new GuestWaitingFrame(this);
    RequestsListFrame requestsListFrame = new RequestsListFrame(this);
    private String username;
    JFrame jFrame = new JFrame();
    private JPanel gamePanel = new JPanel();
    JTextArea chatArea = new JTextArea();
    String jsonList;
    private Board yourBoard = new Board();
    private EnemyBoard enemyBoard = new EnemyBoard();

    private JButton readyButton = new JButton("Ready");
    private JButton resetButton = new JButton("Reset");
    private int iAmReadyToPlay = 0;
    private int enemyReadyToPlay = 0;
    private int myTurn;
    private boolean amIHost;
    /*
    0 -> not my turn
    1 -> my turn
     */
    /*
    0 & 0 ---> cant start game
    1 & 0 ---> cant start game
    1 & 1 ---> can :D
     */


    public MainFrame() {
        loginFrame.runLoginFrame();
        //loginFrame should be closed
        enemyBoard.setiMainFrameToEnemyBoardCallback(this);


        jFrame.setSize(900, 550);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setLayout(new BorderLayout());
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "exiting ...");
                //should send i am leaving message
                messageManager.sendILeftMessage(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem saveChatMenuItem = new JMenuItem("Save Chat History");
        JMenuItem showChatHistoryMenuItem = new JMenuItem("Conversations History");
        fileMenu.add(saveChatMenuItem);
        fileMenu.add(showChatHistoryMenuItem);
        showChatHistoryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatHistoryFrame chatHistoryFrame = new ChatHistoryFrame();
                chatHistoryFrame.runFrame();
            }
        });
        saveChatMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jsonList = "";
                String chatToBeSaved = chatArea.getText();
                String[] lines = chatToBeSaved.split("\r\n|\r|\n");
                JSONObject savedChatJson = new JSONObject();
                savedChatJson.put("Information", messageManager.getAcceptedNetworkHandler().getUsername()
                        + " | " + messageManager.getAcceptedNetworkHandler().getRemoteIp() + " | " + new Date(System.currentTimeMillis()).toString().replace(":", "-"));
                JSONArray jsonArray = new JSONArray();
                for (String line : lines) {
                    JSONObject chatTextLine = new JSONObject();
                    chatTextLine.put("message", line + "\n");
                    jsonArray.put(chatTextLine);
                }
                savedChatJson.put("chat messages", jsonArray);
                jsonList = savedChatJson.toString();

                OutputFileWriter.writeJsonIntoFile(jsonList, new Date(System.currentTimeMillis()).toString().replace(":", "-"), messageManager.getAcceptedNetworkHandler().getUsername());
            }
        });
        jMenuBar.add(fileMenu);
        jMenuBar.add(helpMenu);
        jFrame.setJMenuBar(jMenuBar);

        gamePanel = new JPanel();
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JTextField chatSendArea = new JTextField();
        JButton chatSendButton = new JButton("Send");
        chatSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(chatSendArea.getText());
                chatArea.append(username + " : " + chatSendArea.getText() + "   [" + new Date(System.currentTimeMillis()).toString().replace(":", "-") + "]" + "\n");
                messageManager.sendChatMessage(chatSendArea.getText() + "   [" + new Date(System.currentTimeMillis()).toString().replace(":", "-") + "]");
                chatSendArea.setText("");
            }
        });
        chatSendButton.setPreferredSize(new Dimension(60, 20));
        JPanel chatButtonAndField = new JPanel();
        chatButtonAndField.setLayout(new BorderLayout());
        chatArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(chatArea);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chatPanel.add(jScrollPane);
        chatButtonAndField.add(chatSendArea);
        chatButtonAndField.add(chatSendButton, BorderLayout.EAST);

        chatPanel.add(chatButtonAndField, BorderLayout.SOUTH);

        JPanel readyResetPanel = new JPanel();
        readyResetPanel.setLayout(new GridLayout(2, 1));
        readyResetPanel.add(readyButton);
        gamePanel.add(readyResetPanel);
        gamePanel.setLayout(new FlowLayout());
        gamePanel.add(yourBoard.runFrame());
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton) (e.getSource())).getText().equals("Ready")) {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    gamePanel.removeAll();
//                    EnemyBoard enemyBoard = new EnemyBoard();
//                    gamePanel.add(enemyBoard.runFrame());
//                    gamePanel.revalidate();
                    if (yourBoard.canReady()) {
                        yourBoard.setAddOrRemoveStatus(0);
                        yourBoard.getButtonPanel().setVisible(false);
                        ((JButton) (e.getSource())).setText("Cancel");
                        iAmReadyToPlay = 1;
                        messageManager.sendReadyToPlayMessage(1);
                        if (enemyReadyToPlay == 1) {
                            JOptionPane.showMessageDialog(null, "Game is started [2]");
                            chooseTurn();
                        }
                    }
                } else if (((JButton) (e.getSource())).getText().equals("Cancel")) {
                    yourBoard.getButtonPanel().setVisible(true);
                    ((JButton) (e.getSource())).setText("Ready");
                    iAmReadyToPlay = 0;
                    messageManager.sendReadyToPlayMessage(0);
                }
            }
        });
        readyResetPanel.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (readyButton.getText().equals("Ready")) {
                    gamePanel.removeAll();
                    gamePanel.add(readyResetPanel);
                    yourBoard = new Board();
                    gamePanel.add(yourBoard.runFrame());
                    gamePanel.revalidate();
                }
            }
        });
        jFrame.add(gamePanel, BorderLayout.WEST);
        chatPanel.setPreferredSize(new Dimension(275, 550));
        chatPanel.setBorder(new MatteBorder(0, 1, 0, 0, Color.lightGray));
        jFrame.add(chatPanel, BorderLayout.EAST);


    }


    public void runFrame() {
        jFrame.setTitle("Playing with " + messageManager.getAcceptedNetworkHandler().getUsername());

        jFrame.setVisible(true);
    }

    public RequestsListFrame getRequestsListFrame() {
        return requestsListFrame;
    }

    public void chooseTurn() {
        if (amIHost) {
            Random randomGenerator = new Random();
            int random = randomGenerator.nextInt(2);
            if (random == 1) {
                messageManager.sendTurnMessage(0);
                myTurn = 1;
                changeBoard(false);
            } else {
                messageManager.sendTurnMessage(1);
                myTurn = 0;
            }
        }
    }

    private void changeBoard(boolean showMyBoard) {
        if (showMyBoard) {
            gamePanel.removeAll();
            gamePanel.add(yourBoard.runFrame());
            gamePanel.revalidate();
        } else {
            gamePanel.removeAll();
            gamePanel.add(enemyBoard.runFrame());
            gamePanel.revalidate();
        }
    }


    @Override
    public void onMessageMangerCreated(MessageManager messageManager, String type, String name) {
        this.messageManager = messageManager;
        this.username = name;
        this.messageManager.setiGUICallback(this);
        if (type.equals("HOST")) {
            System.out.println("gui list should be open");
            amIHost = true;
            requestsListFrame.runFrame((LinkedList<NetworkHandler>) this.messageManager.getmNetworkHandlerList());
        } else {
            amIHost = false;
            guestWaitingFrame.runFrame();
            this.messageManager.sendRequestLogin("server", name, "12345");
        }
    }

    @Override
    public void onHostAccepted(int status, String serverUserName) {
        guestWaitingFrame.closeFrame();
        if (status == 1) {
            System.out.println("game is starting");
            messageManager.getmNetworkHandlerList().get(0).setUsername(serverUserName);
            messageManager.setAcceptedNetworkHandler(messageManager.getmNetworkHandlerList().get(0));
            loginFrame.closeFrame();
            runFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Server cancelled your request! \n try again later.");
            System.exit(1);
        }
    }

    @Override
    public void onChatReceived(String chatText) {
        chatArea.append(messageManager.getAcceptedNetworkHandler().getUsername() + " : " + chatText + "\n");
    }

    @Override
    public void onReadyToPlayReceived(int status) {
        enemyReadyToPlay = status;
        JOptionPane.showMessageDialog(null, username + " 's ready status " + status);
        if (iAmReadyToPlay == 1 && enemyReadyToPlay == 1) {
            JOptionPane.showMessageDialog(null, "Game is started");
            chooseTurn();
        }
    }

    @Override
    public void onAttackReceived(int x, int y) {
        System.out.println("enemy attacked!" + x + "," + y);
        if (yourBoard.getBlock(x, y).getBlockStatus() == 1) {
            yourBoard.getBlock(x, y).setBlockStatus(4);
            yourBoard.getBlock(x, y).setColor();
            gamePanel.revalidate();
            messageManager.sendAttackResultMessage(1, x, y);
            if (yourBoard.getDestroyedBlocks() == 20) {
                //20 is number of all blocks
                messageManager.sendILostMessage(1);
                JOptionPane.showMessageDialog(null, "YOU LOST!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(1);
            }

        }
        else if(yourBoard.getBlock(x, y).getBlockStatus() == 3 || yourBoard.getBlock(x, y).getBlockStatus() == 4){
            messageManager.sendAttackResultMessage(0, x, y);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeBoard(false);
        }
        else {
            yourBoard.getBlock(x, y).setBlockStatus(3);
            yourBoard.getBlock(x, y).setColor();
            gamePanel.revalidate();
            messageManager.sendAttackResultMessage(0, x, y);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeBoard(false);
        }

    }

    @Override
    public void onTurnReceived(int turn) {
        myTurn = turn;
        System.out.println("turn received : " + turn);
        if (myTurn == 1) {
            changeBoard(false);
        }
    }

    @Override
    public void onAttackResultMessageReceived(int attackResult, int xCord, int yCord) {
        if (attackResult == 1) {
            enemyBoard.getBlock(xCord, yCord).setBlockStatus(4);
            enemyBoard.getBlock(xCord, yCord).setColor();
            gamePanel.revalidate();
            enemyBoard.disableClicking();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //changeBoard(true);
            enemyBoard.enableClicking();
        } else {
            enemyBoard.getBlock(xCord, yCord).setBlockStatus(3);
            enemyBoard.getBlock(xCord, yCord).setColor();
            gamePanel.revalidate();
            enemyBoard.disableClicking();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeBoard(true);
            enemyBoard.enableClicking();
        }

    }

    @Override
    public void onILostReceived(int iLost) {
        JOptionPane.showMessageDialog(null, "YOU WON!");
        System.exit(1);
    }

    @Override
    public void onILeftReceived(int iLeft) {
        JOptionPane.showMessageDialog(null,"Opponent Left , exiting...");
        System.exit(1);
    }

    @Override
    public void onServerRespondedRequest(int status, String name) {
        if (status == 1) {
            for (NetworkHandler networkHandler : messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    messageManager.setAcceptedNetworkHandler(networkHandler);
                    messageManager.sendServerAccepted(messageManager.getAcceptedNetworkHandler().getUsername(), 1, username);
                    System.out.println("acceptednetwork set!!!");
                    loginFrame.closeFrame();
                    requestsListFrame.closeFrame();
                    runFrame();
                    break;
                }
            }
        } else if (status == -1) {
            for (NetworkHandler networkHandler : messageManager.getmNetworkHandlerList()) {
                if (networkHandler.getUsername().equals(name)) {
                    System.out.println("Network handler was declined;;;;");
                    messageManager.sendServerAccepted(networkHandler.getUsername(), -1, username);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.messageManager.getmNetworkHandlerList().remove(networkHandler);
                    networkHandler.stopSelf();
                    break;
                }
            }
        }
    }

    @Override
    public void onRequestListClosed() {
        JOptionPane.showMessageDialog(null,"exiting ...");
        messageManager.sendILeftMessage(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    @Override
    public void onBlockAttacked(int xCord, int yCord) {
        messageManager.sendAttackMessage(xCord, yCord);
    }

    @Override
    public void onGuestWaitingClosed() {
        JOptionPane.showMessageDialog(null,"exiting ...");
        messageManager.sendILeftMessage(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
