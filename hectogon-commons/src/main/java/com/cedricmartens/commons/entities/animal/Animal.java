package com.cedricmartens.commons.entities.animal;

import com.cedricmartens.commons.Health;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.util.MathUtil;
import com.cedricmartens.commons.util.Vector2;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Animal extends Entity
{
    protected int id;
    protected Health health;
    protected AnimalState animalState;
    protected Entity target;
    protected float wanderSpeed;
    protected float adrenalineSpeed;
    protected float currentSpeed;
    protected Vector2 direction;


    public Animal(float x, float y) {
        super(x, y);
    }

    public void update(float delta)
    {
        switch (animalState) {
            case IDLE:
                idle(delta);
                break;
            case WANDERING:
                wander(delta);
                break;
            case FIGHTING:
                break;
            case CHASING:
                break;
            case FLEEING:
                flee(delta);
                break;
            case LOOTING:
                break;
        }
    }

    protected void wander(float delta)
    {
        move(direction.angleRad(), currentSpeed, delta);
    }

    protected void idle(float delta)
    {

    }

    protected void flee(float delta)
    {
        move((float) (direction.angleRad() + 2 * Math.PI), currentSpeed, delta);
    }

    /**
     * Checks if a target is nearby
     * @param entityList the list of possible targets
     * @return true if a target is found, otherwise false
     */
    public boolean checkForTarget(List<? extends Entity> entityList)
    {
        if(target == null)
        {
            for(Entity e : entityList)
            {
                if(!e.getClass().equals(getClass()))
                {
                    if(MathUtil.distanceToPoint(e.getPosition().x, e.getPosition().y,
                            getPosition().x, getPosition().y) < 150)
                    {
                        target = e;
                        getDirection().set(getPosition().x -  e.getPosition().x,
                                getPosition().y - e.getPosition().y);
                        getDirection().nor();
                        setCurrentSpeed(adrenalineSpeed);
                        animalState = AnimalState.FLEEING;
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public void avoidTarget()
    {
        getDirection().set(getPosition().x -  target.getPosition().x,
                getPosition().y - target.getPosition().y);
        getDirection().nor();
    }

    /**
     * Updates the animal state if it should
     * @param entityList
     * @return true if the animal was updated
     */
    public boolean updateState(List<? extends Entity> entityList)
    {
        if(checkForTarget(entityList))
            return true;
        
        switch (animalState) {
            case IDLE:
                boolean changeState = MathUtil.randomBoolean(0.005f);
                if(changeState)
                {
                    animalState = AnimalState.WANDERING;
                    getDirection().setToRandomDirection();
                    return true;
                }
                break;
            case WANDERING:
                changeState = MathUtil.randomBoolean(0.0005f);
                if(changeState)
                {
                    animalState = AnimalState.IDLE;
                    return true;
                }
                break;
            case FIGHTING:
                break;
            case CHASING:
                break;
            case FLEEING:
                if(MathUtil.distanceToPoint(target.getPosition().x, target.getPosition().y,
                        getPosition().x, getPosition().y) > 300)
                {
                    animalState = AnimalState.WANDERING;
                    target = null;
                    setCurrentSpeed(wanderSpeed);
                    return true;
                }else{
                    avoidTarget();
                }

                break;
            case LOOTING:
                break;
        }

        return false;
    }

    public void setAnimalState(AnimalState animalState) {
        this.animalState = animalState;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public AnimalState getAnimalState() {
        return animalState;
    }

    public float getWanderSpeed() {
        return wanderSpeed;
    }

    public float getAdrenalineSpeed() {
        return adrenalineSpeed;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    @Override
    public void writeTo(OutputStream outputStream) throws IOException
    {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        health.writeTo(outputStream);
        direction.writeTo(outputStream);
        boolean targetExists = target != null;
        dataOutputStream.writeBoolean(targetExists);
        if(targetExists)
        {
            dataOutputStream.writeUTF(target.getClass().getName());
            target.writeTo(outputStream);
        }
        dataOutputStream.writeFloat(currentSpeed);
        dataOutputStream.writeInt(animalState.ordinal());
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Health health = new Health();
        health.readFrom(inputStream);
        direction = new Vector2();
        direction.readFrom(inputStream);
        boolean targetExists = dataInputStream.readBoolean();
        if(targetExists)
        {
            String classEntityName = dataInputStream.readUTF();
            try {

                if(classEntityName.endsWith("Player"))
                    classEntityName = Competitor.class.getName();

                Class<? extends Entity> classEntity = (Class<? extends Entity>) Class.forName(classEntityName);
                Constructor <? extends Entity> ctor = classEntity.getConstructor();
                target = ctor.newInstance();
                target.readFrom(inputStream);
                System.out.println("Target at :" + target.getPosition().x + ", " +
                    target.getPosition().y + " -> id : " + target.getId());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        currentSpeed = dataInputStream.readFloat();

        int animalStateCode = dataInputStream.readInt();
        if(animalStateCode < 0 || animalStateCode >= AnimalState.values().length)
            throw new InvalidPacketDataException(animalStateCode + " is not a valid value for AnimalState");

        animalState = AnimalState.values()[animalStateCode];
    }
}
