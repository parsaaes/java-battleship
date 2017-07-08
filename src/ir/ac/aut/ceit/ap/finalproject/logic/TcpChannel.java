package ir.ac.aut.ceit.ap.finalproject.logic;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public TcpChannel(Socket socket, int timeout) {
        mSocket = socket;
        try {
            mSocket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public String getRemoteIp(){
       return mSocket.getRemoteSocketAddress().toString();
    }

    public boolean isConnected() {
        return mSocket.isConnected() && !mSocket.isClosed();
    }

    public byte[] read(final int count) {
        //System.out.println("this is count -->" + count);
        byte[] bytes = new byte[count];
        boolean nothingToRead = true;
        int c;

        try {
            mInputStream = mSocket.getInputStream();
            for (int i = 0; i < bytes.length; i++) {
                //System.out.println(i);
                // System.out.println(mInputStream.available() + " is available");

                if(mInputStream.available() != 0 ) {

                    if ((c = mInputStream.read()) != -1) {
                        bytes[i] = (byte) c;
                    }
                    else {
                        break;
                    }
                    nothingToRead = false;
                }
                else {
                    break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(nothingToRead){
            return null;
        }
        return bytes;

    }

    public void write(byte[] data) {
        try {
            mOutputStream = mSocket.getOutputStream();
            mOutputStream.write(data);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeChannel() {
        try {
            mInputStream.close();
            mOutputStream.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
