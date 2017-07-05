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
    private List<NetworkHandler> mNetworkHandlerList = new LinkedList<NetworkHandler>();;

    public MessageManager(int port) {
        mServerSocketHandler = new ServerSocketHandler(port,this,this);
        mServerSocketHandler.start();
    }

    public MessageManager(String ip , int port){
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
        NetworkHandler networkHandler = new NetworkHandler(inetSocketAddress,this);
    }

    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
    }

    @Override
    public void onMessageReceived(BaseMessage baseMessage) {

    }

    @Override
    public void onSocketClosed() {

    }
}