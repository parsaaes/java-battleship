package ir.ac.aut.ceit.ap.finalproject.logic;


import java.nio.ByteBuffer;

public class ServerAcceptedMessage extends BaseMessage {

    int hostAccepted;

    public ServerAcceptedMessage(int hostAccepted){
        this.hostAccepted = hostAccepted;
        serialize();
    }

    public ServerAcceptedMessage(byte[] serialized){
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.SERVER_ACCEPTED);
        byteBuffer.putInt(hostAccepted);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        hostAccepted = byteBuffer.getInt();
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.SERVER_ACCEPTED;
    }

    public int getHostAccepted(){
        return hostAccepted;
    }
}
