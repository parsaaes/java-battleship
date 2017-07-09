package ir.ac.aut.ceit.ap.finalproject.logic;

import java.nio.ByteBuffer;


public class AttackResultMessage extends BaseMessage {
    private int attackResult;

    public AttackResultMessage(int attackResult) {
        this.attackResult = attackResult;
        serialize();
    }

    public AttackResultMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ATTACK_RESULT);
        byteBuffer.putInt(attackResult);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        attackResult = byteBuffer.getInt();

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ATTACK_RESULT;
    }

    public int getAttackResult() {
        return attackResult;
    }
}
