package server_loops;

import java.io.*;
/**
 *
 * @author Nevanor
 */
public class LongBool implements Serializable
{
    long time;
    boolean truefalse;
    public LongBool (long time, boolean truefalse)
    {
        this.time = time;
        this.truefalse = truefalse;
    }
}