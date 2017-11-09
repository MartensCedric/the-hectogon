package com.cedricmartens.hectogon.server.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Created by Cedric Martens on 2017-11-09.
 */
public class CommandCenter implements Runnable
{
    private boolean running;
    public CommandCenter() {
        running = true;
    }

    @Override
    public void run() {
        Scanner reader = new Scanner(System.in);
        while (running)
        {
            String command = reader.next();
            Class<?> commandClass = null;
            try {
                commandClass = Class.forName(getFullCommandName(command));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Constructor<?> ctor = null;
            try {
                ctor = commandClass.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                Object object = ctor.newInstance();
                Command comm = (Command) object;
                comm.execute(Command.NO_ARGS);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        reader.close();
    }

    public String getFullCommandName(String commandAlias)
    {
        if(commandAlias == null || commandAlias.length() == 0)
        {
            throw new IllegalArgumentException();
        }

        String lowerCases = commandAlias.toLowerCase().substring(1);

        commandAlias = commandAlias.substring(0, 1).toUpperCase();

        String finalCommand = commandAlias.charAt(0) + lowerCases;


        return getClass().getPackage() + "." + finalCommand + "Command";
    }
}
