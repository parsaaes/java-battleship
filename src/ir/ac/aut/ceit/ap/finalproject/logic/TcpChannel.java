package ir.ac.aut.ceit.ap.finalproject.logic;

import sun.rmi.transport.tcp.TCPChannel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class TcpChannel {
    private Socket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

public TcpChannel(SocketAddress socketAddress, int timeout) {
    mSocket = new Socket();
    try {
        mSocket.setSoTimeout(timeout);
    } catch (SocketException e) {
        e.printStackTrace();
    }
    try {
        mSocket.connect(socketAddress);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public TcpChannel(Socket socket,int timeout)  {
    mSocket = socket;
    try {
        mSocket.setSoTimeout(timeout);
    } catch (SocketException e) {
        e.printStackTrace();
    }
}
//    public TcpChannel(Socket socket, int timeout)
//    /** * Try to read specific count from input stream. */
//    public byte[] read(final int count)
//    /** * Write bytes on output stream. */
//    public void write(byte[] data)
//    /** * Check socket’s connectivity. */
//    public boolean isConnected()
//    /** * Try to close socket and input-output streams. */
//    public void closeChannel()
}
