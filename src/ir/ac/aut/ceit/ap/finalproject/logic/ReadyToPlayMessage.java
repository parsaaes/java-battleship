package ir.ac.aut.ceit.ap.finalproject.logic;


import java.nio.ByteBuffer;

public class ReadyToPlayMessage extends BaseMessage {

    private int enemyReadyStatus;

    public ReadyToPlayMessage(int enemyReadyStatus){
        this.enemyReadyStatus = enemyReadyStatus;
        serialize();
    }
    public ReadyToPlayMessage(byte[] serialized){
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.READYTO_PLAY);
        byteBuffer.putInt(enemyReadyStatus);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        enemyReadyStatus = byteBuffer.getInt();

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.READYTO_PLAY;
    }

    public int isEnemyReadyStatus() {
        return enemyReadyStatus;
    }
}
