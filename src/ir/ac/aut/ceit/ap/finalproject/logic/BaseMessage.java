package ir.ac.aut.ceit.ap.finalproject.logic;

public abstract class BaseMessage {

    protected byte[] mSerialized;

    /**
     * Fields are stored into serial bytes in this method.
     */
    protected abstract void serialize();

    /**
     * Fields are restored from serial bytes in this method.
     */
    protected abstract void deserialize();

    /**
     * Return message type code.
     * @return message type in bytes
     */
    public abstract byte getMessageType();

    public byte[] getSerialized() {
        return mSerialized;
    }

}