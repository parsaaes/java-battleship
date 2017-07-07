package ir.ac.aut.ceit.ap.finalproject.logic;


import java.nio.ByteBuffer;

public class ServerAcceptedMessage extends BaseMessage {

    private int hostAccepted;
    private String serverUserName;

    public ServerAcceptedMessage(int hostAccepted , String serverUserName){
        this.hostAccepted = hostAccepted;
        this.serverUserName = serverUserName;
        serialize();
    }

    public ServerAcceptedMessage(byte[] serialized){
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int serverUserNameLength = serverUserName.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + serverUserNameLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.SERVER_ACCEPTED);
        byteBuffer.putInt(hostAccepted);
        byteBuffer.put(serverUserName.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        hostAccepted = byteBuffer.getInt();
        int usernameLength = byteBuffer.getInt();
        byte[] usernameBytes = new byte[usernameLength];
        byteBuffer.get(usernameBytes);
        serverUserName = new String(usernameBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.SERVER_ACCEPTED;
    }

    public int getHostAccepted(){
        return hostAccepted;
    }

    public String getServerUserName() {
        return serverUserName;
    }
}
