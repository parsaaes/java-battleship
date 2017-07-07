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
    private ReceivedMessageConsumer mConsumerThread = new ReceivedMessageConsumer();
    INetworkHandlerCallback iNetworkHandlerCallback;
    private String username;

    public Queue<byte[]> getmReceivedQueue() {
        return mReceivedQueue;
    }

    public Queue<byte[]> getmSendQueue() {
        return mSendQueue;
    }

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socketAddress, 3000);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socket, 3000);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void sendMessage(BaseMessage baseMessage) {
        mSendQueue.add(baseMessage.getSerialized());
        System.out.println("added to sendQui");
    }

    @Override
    public void run() {
        threadIsAlive = true;
        mConsumerThread.start();
        while (mTcpChannel.isConnected() && threadIsAlive) {
            if (!mSendQueue.isEmpty()) {
                mTcpChannel.write(mSendQueue.poll());
            }
            byte[] bytes = readChannel();
            if (bytes != null) {
                mReceivedQueue.add(bytes);
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
        }
        else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(messageSizeInByte);
            int size = byteBuffer.getInt();
            System.out.println("size is " + size);
            resultByte = new byte[size];
            resultByte[0] = messageSizeInByte[0];
            resultByte[1] = messageSizeInByte[1];
            resultByte[2] = messageSizeInByte[2];
            resultByte[3] = messageSizeInByte[3];
            for (int i = 0; i < size - 4; i++) {
                resultByte[4 + i] = mTcpChannel.read(1)[0];
            }
        }
        return resultByte;
    }

    private class ReceivedMessageConsumer extends Thread {
        public byte getType(byte[] bytes) {
            switch (bytes[5]) {
                case MessageTypes.REQUEST_LOGIN:
                case MessageTypes.SERVER_ACCEPTED:
                case MessageTypes.MOVE_MESSAGE:
                    return bytes[5];
                default:
                    System.out.println("Unknown type!!!");

            }
            return -1;
        }

        /**
         * While channel is connected and stop is not called: *
         * if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms.
         */
        @Override
        public void run() {
            while (mTcpChannel.isConnected() && threadIsAlive) {
                if (!mReceivedQueue.isEmpty()) {
                    byte[] messageBytes = mReceivedQueue.poll();
                    switch (getType(messageBytes)) {
                        case MessageTypes.REQUEST_LOGIN:
                            RequestLoginMessage requestLoginMessage = new RequestLoginMessage(messageBytes);
                            username = requestLoginMessage.getUsername();
                            iNetworkHandlerCallback.onMessageReceived(requestLoginMessage);
                            System.out.println("Request");
                            break;
                        case MessageTypes.SERVER_ACCEPTED:
                            ServerAcceptedMessage serverAcceptedMessage = new ServerAcceptedMessage(messageBytes);
                            if(serverAcceptedMessage.hostAccepted == 0){
                                stopSelf();
                            }
                            iNetworkHandlerCallback.onMessageReceived(serverAcceptedMessage);
                            System.out.println("Server accepted");
                            break;
                        case MessageTypes.MOVE_MESSAGE:
                            //
                            break;
                        default:
                            System.out.println("Unknown type!!!");
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage);

        void onSocketClosed();
    }
}
