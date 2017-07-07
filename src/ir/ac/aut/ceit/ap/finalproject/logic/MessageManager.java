package ir.ac.aut.ceit.ap.finalproject.logic;


import ir.ac.aut.ceit.ap.finalproject.view.RequestsListFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback, NetworkHandler.INetworkHandlerCallback {

    private ServerSocketHandler mServerSocketHandler;

    private List<NetworkHandler> mNetworkHandlerList = new LinkedList<NetworkHandler>();

    private IGUICallback iGUICallback;
    private NetworkHandler acceptedNetworkHandler;
    public void setiGUICallback(IGUICallback iGUICallback) {
        this.iGUICallback = iGUICallback;
    }

    public NetworkHandler getAcceptedNetworkHandler() {
        return acceptedNetworkHandler;
    }

    public void setAcceptedNetworkHandler(NetworkHandler acceptedNetworkHandler) {
        this.acceptedNetworkHandler = acceptedNetworkHandler;
    }

    public void setUser(String user) {
        mNetworkHandlerList.get(0).setUsername(user);
    }

    public List<NetworkHandler> getmNetworkHandlerList() {
        return mNetworkHandlerList;
    }

    public MessageManager(int port) {
        mServerSocketHandler = new ServerSocketHandler(port, this, this);
        mServerSocketHandler.start();
        System.out.println("serversocket Started");

    }

    public MessageManager(String ip, int port) {
//        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
        try {
            Socket socket;
            socket = new Socket(ip, port);
            NetworkHandler networkHandler = new NetworkHandler(socket, this);
            mNetworkHandlerList.add(networkHandler);
            networkHandler.start();
        }
        catch (ConnectException e){
            JOptionPane.showMessageDialog(null,"No Host Found !");
            System.exit(1);
        }
        catch (UnknownHostException e){
            JOptionPane.showMessageDialog(null,"No Host Found !");
            System.exit(1);
        }
        catch (NoRouteToHostException e){
            JOptionPane.showMessageDialog(null,"No Host Found !");
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void sendRequestLogin(String to, String username, String password){
        RequestLoginMessage requestLoginMessage = new RequestLoginMessage(username,password);
        // s
        for (NetworkHandler networkHandler : mNetworkHandlerList) {
            System.out.println(networkHandler.getUsername());

            System.out.println("its equals! so it should be send now");
            networkHandler.sendMessage(requestLoginMessage);

        }
    }

    public void sendServerAccepted(String to , int hostAccepted , String serverUserName){
        ServerAcceptedMessage serverAcceptedMessage = new ServerAcceptedMessage(hostAccepted,serverUserName);
        for (NetworkHandler networkHandler : mNetworkHandlerList) {
            System.out.println("before if");
            if(networkHandler.getUsername().equals(to)){
                System.out.println("it is equal");
                networkHandler.sendMessage(serverAcceptedMessage);
                break;
            }
        }
    }

    public void sendChatMessage(String chatText){
        ChatMessage chatMessage = new ChatMessage(chatText);
        acceptedNetworkHandler.sendMessage(chatMessage);
    }

    private void consumeRequestLogin(RequestLoginMessage message) {
        System.out.println("i am consumed :D " + message.getUsername() + " - " + message.getPassword() );
        LinkedList<NetworkHandler> linkedList = (LinkedList<NetworkHandler>)mNetworkHandlerList;
        linkedList.getLast().setUsername(message.getUsername());
        for (NetworkHandler networkHandler : mNetworkHandlerList) {
            System.out.println("[LIST] Device is connected name  : " + networkHandler.getUsername());
        }
        iGUICallback.getRequestsListFrame().updateList();
        // test
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sendServerAccepted(getmNetworkHandlerList().get(0).getUsername(),1);

    }

    private void consumeServerAccepted(ServerAcceptedMessage serverAcceptedMessage){
        System.out.println("server accepted message received ");
        iGUICallback.onHostAccepted(serverAcceptedMessage.getHostAccepted(),serverAcceptedMessage.getServerUserName());
    }

    private void consumeChatMessage(ChatMessage chatMessage){
        System.out.println("chat received");
        iGUICallback.onChatReceived(chatMessage.getChatText());
    }

    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
        //networkHandler.setUsername("user1");
        mNetworkHandlerList.add(networkHandler);
        networkHandler.start();
    }


    @Override
    public void onMessageReceived(BaseMessage baseMessage) {
        switch (baseMessage.getMessageType()) {
            case MessageTypes.REQUEST_LOGIN:
                consumeRequestLogin((RequestLoginMessage) baseMessage);
                break;
            case MessageTypes.SERVER_ACCEPTED:
                consumeServerAccepted((ServerAcceptedMessage) baseMessage);
                break;
            case MessageTypes.CHAT_MESSAGE:
                consumeChatMessage((ChatMessage) baseMessage);
                break;

        }
    }



    @Override
    public void onSocketClosed() {
        System.out.println("Called onsocketclosed()");
        JOptionPane.showMessageDialog(null,"Disconnected");
    }

    public interface IGUICallback {
        void onHostAccepted(int status,String serverUserName);
        void onChatReceived(String chatText);
        RequestsListFrame getRequestsListFrame();
    }
}
