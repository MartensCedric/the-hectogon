package com.cedricmartens.commons.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface CustomSerializable
{
    void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException;
    void writeTo(OutputStream outputStream) throws IOException;
}
