package ir.ac.aut.ceit.ap.finalproject.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSocketHandler extends Thread {
    private ServerSocket mServerSocket;
    private NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback;
    private IServerSocketHandlerCallback iServerSocketHandlerCallback;
    private boolean threadIsAlive;

    public ServerSocketHandler(int port, NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback, IServerSocketHandlerCallback iServerSocketHandlerCallback) {
        try {
            mServerSocket = new ServerSocket(port);
            this.iNetworkHandlerCallback = iNetworkHandlerCallback;
            this.iServerSocketHandlerCallback = iServerSocketHandlerCallback;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        threadIsAlive = true;
        while (!mServerSocket.isClosed() && threadIsAlive) {
            try {

                Socket socket = mServerSocket.accept();

                System.out.println("A Device Connected");
                if (socket.isConnected()) {
                    System.out.println("socket is conncted");
                    NetworkHandler networkHandler = new NetworkHandler(socket, iNetworkHandlerCallback);
                    networkHandler.setUsername("user1");
                    iServerSocketHandlerCallback.onNewConnectionReceived(networkHandler);
                } else {
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void stopSelf() {
        threadIsAlive = false;
        try {
            mServerSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public interface IServerSocketHandlerCallback {
        void onNewConnectionReceived(NetworkHandler networkHandler);
    }
}
