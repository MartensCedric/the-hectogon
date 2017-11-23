package com.cedricmartens.commons.networking.authentification;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

/**
 * Created by Cedric Martens on 2017-11-23.
 */
public class PacketCompetitorJoin extends Packet
{
    private Competitor competitor;
    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int userId = dataInputStream.readInt();
        String username = dataInputStream.readUTF();
        float x = dataInputStream.readFloat();
        float y = dataInputStream.readFloat();

        User user = new User(userId, username);
        Point position = new Point(x, y);
        competitor = new Competitor(user, position);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(competitor.getUser().getUserId());
        dataOutputStream.writeUTF(competitor.getUser().getUsername());
        dataOutputStream.writeFloat(competitor.getPosition().x);
        dataOutputStream.writeFloat(competitor.getPosition().y);
    }
}
