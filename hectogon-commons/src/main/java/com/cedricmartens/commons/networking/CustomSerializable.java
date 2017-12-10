package com.cedricmartens.commons.networking;


import java.io.IOException;
import java.io.OutputStream;
public interface CustomSerializable
{
    void writeTo(OutputStream outputStream) throws IOException;
}
