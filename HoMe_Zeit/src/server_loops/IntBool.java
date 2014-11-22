package server_loops;

import java.io.*;
/**
 *
 * @author Nevanor
 */
public class IntBool implements Serializable
{
    int number;
    boolean truefalse;
    public IntBool (int number, boolean truefalse)
    {
        this.number = number;
        this.truefalse = truefalse;
    }
}