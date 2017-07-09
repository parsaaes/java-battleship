package ir.ac.aut.ceit.ap.finalproject.logic;

import java.nio.ByteBuffer;


public class TurnMessage extends BaseMessage {
    private int turn;

    public TurnMessage(int turn) {
        this.turn = turn;
        serialize();
    }

    public TurnMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.TURN_MESSAGE);
        byteBuffer.putInt(turn);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        turn = byteBuffer.getInt();
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.TURN_MESSAGE;
    }

    public int getTurn() {
        return turn;
    }
}
