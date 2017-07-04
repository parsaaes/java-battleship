package ir.ac.aut.ceit.ap.finalproject.logic;


import java.net.Socket;
import java.net.SocketAddress;
import java.util.Queue;


public class NetworkHandler extends Thread {
    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue;
    private Queue<byte[]> mReceivedQueue;
    private ReceivedMessageConsumer mConsumerThread;
    INetworkHandlerCallback iNetworkHandlerCallback;
    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback){
        mTcpChannel = new TcpChannel(socketAddress,300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }
    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback){
        mTcpChannel = new TcpChannel(socket,300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }
    private class ReceivedMessageConsumer extends Thread {
        /**
         *
         * While channel is connected and stop is not called: *
         *  if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms. */
        @Override public void run(){}
    }
    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage);
        void onSocketClosed();
    }
}
