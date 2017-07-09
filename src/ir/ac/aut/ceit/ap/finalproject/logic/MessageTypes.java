package ir.ac.aut.ceit.ap.finalproject.logic;

import com.sun.javaws.jnl.ResourcesDesc;

public class MessageTypes {

    /**
     * version of protocol
     */
    public static final byte PROTOCOL_VERSION = 1;

    /* codes : */

    public static final byte REQUEST_LOGIN = 1;
    public static final byte SERVER_ACCEPTED = 2;
    public static final byte CHAT_MESSAGE = 3;
    public static final byte READYTO_PLAY = 4;
    public static final byte ATTACK_MESSAGE = 5;
    public static final byte TURN_MESSAGE = 6;
    public static final byte ATTACK_RESULT = 7;
    public static final byte ILOST_MESSAGE = 8;


}
