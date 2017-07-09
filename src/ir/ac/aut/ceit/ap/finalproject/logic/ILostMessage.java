package ir.ac.aut.ceit.ap.finalproject.logic;


import java.nio.ByteBuffer;

public class ILostMessage extends BaseMessage {
    private int iLost;

    public ILostMessage(int iLost) {
        this.iLost = iLost;
        serialize();
    }

    public ILostMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ILOST_MESSAGE);
        byteBuffer.putInt(iLost);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        iLost = byteBuffer.getInt();

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ILOST_MESSAGE;
    }

    public int getiLost() {
        return iLost;
    }
}
