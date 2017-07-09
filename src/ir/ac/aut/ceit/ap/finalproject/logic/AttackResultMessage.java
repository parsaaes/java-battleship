package ir.ac.aut.ceit.ap.finalproject.logic;

import java.nio.ByteBuffer;


public class AttackResultMessage extends BaseMessage {
    private int attackResult;
    private int xCord;
    private int yCord;

    public AttackResultMessage(int attackResult , int xCord , int yCord) {
        this.attackResult = attackResult;
        this.xCord = xCord;
        this.yCord = yCord;
        serialize();
    }

    public AttackResultMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4 + 4 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ATTACK_RESULT);
        byteBuffer.putInt(attackResult);
        byteBuffer.putInt(xCord);
        byteBuffer.putInt(yCord);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        attackResult = byteBuffer.getInt();
        xCord = byteBuffer.getInt();
        yCord = byteBuffer.getInt();

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ATTACK_RESULT;
    }

    public int getAttackResult() {
        return attackResult;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
}
