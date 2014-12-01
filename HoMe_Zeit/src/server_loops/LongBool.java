package server_loops;

import java.io.*;
/**
 *
 * @author Nevanor
 */
public class LongBool implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; // have added
	public long time;
    public boolean truefalse;
    public LongBool (long time, boolean truefalse)
    {
        this.time = time;
        this.truefalse = truefalse;
    }
}