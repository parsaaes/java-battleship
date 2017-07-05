package ir.ac.aut.ceit.ap.finalproject.logic;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;


public class NetworkHandler extends Thread {
    private boolean threadIsAlive;
    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue = new LinkedList<>();
    private Queue<byte[]> mReceivedQueue = new LinkedList<>();
    private ReceivedMessageConsumer mConsumerThread;
    INetworkHandlerCallback iNetworkHandlerCallback;

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socketAddress, 300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socket, 300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }

    public void sendMessage(BaseMessage baseMessage) {
        mSendQueue.add(baseMessage.getSerialized());
    }

    @Override
    public void run() {
        threadIsAlive = true;
        while (mTcpChannel.isConnected() && threadIsAlive) {
            if (!mSendQueue.isEmpty()) {
                mTcpChannel.write(mSendQueue.poll());
                byte[] bytes = readChannel();
                if (bytes != null) {
                    mReceivedQueue.add(bytes);
                }
            }
        }
    }

    public void stopSelf() {
        threadIsAlive = false;
        mTcpChannel.closeChannel();
        iNetworkHandlerCallback.onSocketClosed();
    }

    private byte[] readChannel() {
        byte[] messageSizeInByte;
        byte[] resultByte;
        messageSizeInByte = mTcpChannel.read(4);
        if (messageSizeInByte == null) {
            return null;
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(messageSizeInByte);
            int size = byteBuffer.getInt();
            resultByte = new byte[size];
            resultByte[0] = messageSizeInByte[0];
            resultByte[1] = messageSizeInByte[1];
            resultByte[2] = messageSizeInByte[2];
            resultByte[3] = messageSizeInByte[3];
            for (int i = 0; i < size; i++) {
                resultByte[4 + i] = mTcpChannel.read(1)[0];
            }
        }
        return resultByte;
    }

    private class ReceivedMessageConsumer extends Thread {
        /**
         * While channel is connected and stop is not called: *
         * if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms.
         */
        @Override
        public void run() {
        }
    }

    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage);

        void onSocketClosed();
    }
}
