package com.cedricmartens.commons.networking;

/**
 * Created by Cedric Martens on 2017-11-08.
 */
public class InvalidPacketDataException extends Exception
{
    public InvalidPacketDataException() {
    }

    public InvalidPacketDataException(String message) {
        super(message);
    }

    public InvalidPacketDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPacketDataException(Throwable cause) {
        super(cause);
    }
}
