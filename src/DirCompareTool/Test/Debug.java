/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DirCompareTool.Test;

/**
 *
 * @author Marshall
 */
public class Debug
{
    private static final boolean DEBUG_ENABLED = true;
    
    public static void debugMessage(String s)
    {
        if (DEBUG_ENABLED) System.out.println(s);
    }
}
