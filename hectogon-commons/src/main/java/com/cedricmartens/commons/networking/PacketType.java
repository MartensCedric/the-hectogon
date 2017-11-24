package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.networking.actions.PacketMovement;
import com.cedricmartens.commons.networking.authentification.*;
import com.cedricmartens.commons.networking.competitor.PacketCompetitor;
import com.cedricmartens.commons.networking.competitor.PacketCompetitorJoin;

public enum  PacketType
{
    //Add packets here
    CHAT(PacketChat.class),
    IN_LOGIN(PacketInLogin.class),
    OUT_LOGIN(PacketOutLogin.class),
    IN_REGISTER(PacketInRegister.class),
    OUT_REGISTER(PacketOutRegister.class),
    MOVEMENT(PacketMovement.class),
    JOIN(PacketCompetitorJoin.class),
    COMPETITOR(PacketCompetitor.class)
    ;
    private Class<? extends Packet> type;


    PacketType(Class<? extends Packet> type)
    {
        this.type = type;
    }

    public static Class<? extends Packet> getById(int id)
    {
        if(id <= Packet.NO_ID || id >= values().length)
            throw new IllegalArgumentException();

        return values()[id].type;
    }

    public static int idFromType(Class<? extends Packet> type)
    {
        for(PacketType pt : values())
        {
            if(pt.getType() == type)
                return pt.ordinal();
        }

        throw new PacketNotFoundException();
    }

    public Class<? extends Packet> getType() {
        return type;
    }
}
