package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.networking.actions.PacketCompetitorMovement;
import com.cedricmartens.commons.networking.actions.PacketMovement;
import com.cedricmartens.commons.networking.actions.PacketPositionCorrection;
import com.cedricmartens.commons.networking.animal.PacketAnimalDeath;
import com.cedricmartens.commons.networking.animal.PacketAnimalUpdate;
import com.cedricmartens.commons.networking.authentication.PacketInLogin;
import com.cedricmartens.commons.networking.authentication.PacketInRegister;
import com.cedricmartens.commons.networking.authentication.PacketOutLogin;
import com.cedricmartens.commons.networking.authentication.PacketOutRegister;
import com.cedricmartens.commons.networking.combat.PacketProjectile;
import com.cedricmartens.commons.networking.competitor.PacketCompetitor;
import com.cedricmartens.commons.networking.competitor.PacketCompetitorJoin;
import com.cedricmartens.commons.networking.competitor.PacketDeath;
import com.cedricmartens.commons.networking.inventory.PacketDropItem;
import com.cedricmartens.commons.networking.inventory.PacketInventory;
import com.cedricmartens.commons.networking.inventory.PacketLoot;
import com.cedricmartens.commons.networking.inventory.PacketLootUpdate;

public enum  PacketType
{
    CHAT(PacketChat.class),
    IN_LOGIN(PacketInLogin.class),
    OUT_LOGIN(PacketOutLogin.class),
    IN_REGISTER(PacketInRegister.class),
    OUT_REGISTER(PacketOutRegister.class),
    MOVEMENT(PacketMovement.class),
    COMP_MOVEMENT(PacketCompetitorMovement.class),
    JOIN(PacketCompetitorJoin.class),
    COMPETITOR(PacketCompetitor.class),
    LOOT(PacketLoot.class),
    LOOT_UPDATE(PacketLootUpdate.class),
    INVENTORY(PacketInventory.class),
    DROP(PacketDropItem.class),
    CORRECTION(PacketPositionCorrection.class),
    DEATH(PacketDeath.class),
    ANIMAL_DEATH(PacketAnimalDeath.class),
    ANIMAL_UPDATE(PacketAnimalUpdate.class),
    PROJECTILE(PacketProjectile.class)
    ;

    private Class<? extends Packet> type;


    PacketType(Class<? extends Packet> type)
    {
        this.type = type;
    }

    public static Class<? extends Packet> getById(int id)
    {
        if(id <= Packet.NO_ID || id >= values().length)
            throw new IllegalArgumentException("Packet with ID " + id + " not found!");

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
