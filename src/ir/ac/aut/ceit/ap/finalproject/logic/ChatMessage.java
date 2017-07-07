package ir.ac.aut.ceit.ap.finalproject.logic;


import java.nio.ByteBuffer;

public class ChatMessage extends BaseMessage {

    private String chatText;

    public ChatMessage(String chatText){
        this.chatText = chatText;
        serialize();
    }

    public ChatMessage(byte[] serialized){
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int chatTextLength = chatText.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + chatTextLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.SERVER_ACCEPTED);
        byteBuffer.putInt(chatTextLength);
        byteBuffer.put(chatText.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        int chatTextLength = byteBuffer.getInt();
        byte[] chatTextBytes = new byte[chatTextLength];
        byteBuffer.get(chatTextBytes);
        chatText = new String(chatTextBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.CHAT_MESSAGE;
    }

    public String getChatText() {
        return chatText;
    }
}
