package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.networking.authentification.PacketInLogin;
import com.cedricmartens.commons.networking.authentification.PacketInRegister;
import com.cedricmartens.commons.networking.authentification.PacketOutLogin;
import com.cedricmartens.commons.networking.authentification.PacketOutRegister;

public enum  PacketType
{
    //Add packets here
    IN_CHAT(PacketChat.class),
    IN_LOGIN(PacketInLogin.class),
    OUT_LOGIN(PacketOutLogin.class),
    IN_REGISTER(PacketInRegister.class),
    OUT_REGISTER(PacketOutRegister.class)
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

        //TODO custom ex
        throw new IllegalStateException();
    }

    public Class<? extends Packet> getType() {
        return type;
    }
}
