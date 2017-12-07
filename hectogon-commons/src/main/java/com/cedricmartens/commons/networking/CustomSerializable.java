package com.cedricmartens.commons.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface CustomSerializable
{
    void readFrom(InputStream stream) throws IOException;
    void writeTo(OutputStream stream) throws IOException;
}
