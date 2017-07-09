package ir.ac.aut.ceit.ap.finalproject.logic;

import java.nio.ByteBuffer;

public class AttackMessage extends BaseMessage {
    private int xCord;
    private int yCord;

    public AttackMessage(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
        serialize();
    }

    public AttackMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ATTACK_MESSAGE);
        byteBuffer.putInt(xCord);
        byteBuffer.putInt(yCord);
        mSerialized = byteBuffer.array();

    }

    @Override
    protected void deserialize() {

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ATTACK_MESSAGE;

    }
}
