package com.cedricmartens.hectogon.server.command;

/**
 * Created by Cedric Martens on 2017-11-09.
 */
public abstract class Command
{
    public static final String[] NO_ARGS = new String[]{};
    abstract void execute(String[] args);
}
