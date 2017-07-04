package ir.ac.aut.ceit.ap.finalproject.logic;


import java.net.Socket;
import java.net.SocketAddress;
import java.util.Queue;

/**
 * Created by NP on 7/4/2017.
 */
public class NetworkHandler extends Thread {
    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue;
    private Queue<byte[]> mReceivedQueue;
    private ReceivedMessageConsumer mConsumerThread;
    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback){

    }
    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback){

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
