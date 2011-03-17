
/*
 * WAZA
 * Created on 2006-7-8
 *
 */
package com.cell.musicgame;

/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * <summary> MGPlayer
 */
public interface MGPlayerListener{

    public void beatBPM(int beatCount);

    public void lostNote(int line);
 
    public void hitNote(int line,int acc);

    public void showNote(int[][] pos);
}
