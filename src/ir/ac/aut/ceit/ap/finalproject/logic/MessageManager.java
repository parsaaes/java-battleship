package ir.ac.aut.ceit.ap.finalproject.logic;


import com.sun.corba.se.spi.activation.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback, NetworkHandler.INetworkHandlerCallback {

    private ServerSocketHandler mServerSocketHandler;

    private List<NetworkHandler> mNetworkHandlerList = new LinkedList<NetworkHandler>();


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
        } catch (IOException e) {
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

    private void consumeRequestLogin(RequestLoginMessage message) {
        System.out.println("i am consumed :D " + message.getUsername() + " - " + message.getPassword() );
        LinkedList<NetworkHandler> linkedList = (LinkedList<NetworkHandler>)mNetworkHandlerList;
        linkedList.getLast().setUsername(message.getUsername());
        for (NetworkHandler networkHandler : mNetworkHandlerList) {
            System.out.println("Device is connected name  :" + networkHandler.getUsername());
        }
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

        }
    }



    @Override
    public void onSocketClosed() {
        System.out.println("Called onsocketclosed()");
    }
}
