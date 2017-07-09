package ir.ac.aut.ceit.ap.finalproject.logic;

import java.nio.ByteBuffer;


public class ILeftTheGameMessage extends BaseMessage {

    private int iLeft;

    public ILeftTheGameMessage(int iLeft) {
        this.iLeft = iLeft;
        serialize();
    }

    public ILeftTheGameMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ILEFT_MESSAGE);
        byteBuffer.putInt(iLeft);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        iLeft = byteBuffer.getInt();

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ILEFT_MESSAGE;
    }

    public int getiLeft() {
        return iLeft;
    }
}
