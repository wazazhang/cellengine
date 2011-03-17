/*
 * DropTargetAdapter.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package javazoom.jlgui.player.amp.skin;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DropTargetAdapter implements DropTargetListener
{
    private static Log log = LogFactory.getLog(DropTargetAdapter.class);

    public DropTargetAdapter()
    {
        super();
    }

    public void dragEnter(DropTargetDragEvent e)
    {
        if (isDragOk(e) == false)
        {
            e.rejectDrag();
        }
    }

    public void dragOver(DropTargetDragEvent e)
    {
        if (isDragOk(e) == false)
        {
            e.rejectDrag();
        }
    }

    public void dropActionChanged(DropTargetDragEvent e)
    {
        if (isDragOk(e) == false)
        {
            e.rejectDrag();
        }
    }

    public void dragExit(DropTargetEvent dte)
    {
    }

    protected boolean isDragOk(DropTargetDragEvent e)
    {
        // Check DataFlavor
        DataFlavor[] dfs = e.getCurrentDataFlavors();
        DataFlavor tdf = null;
        for (int i = 0; i < dfs.length; i++)
        {
            if (DataFlavor.javaFileListFlavor.equals(dfs[i]))
            {
                tdf = dfs[i];
                break;
            }
            else if (DataFlavor.stringFlavor.equals(dfs[i]))
            {
                tdf = dfs[i];
                break;
            }
        }
        // Only file list allowed.
        if (tdf != null)
        {
            // Only DnD COPY allowed.
            if ((e.getSourceActions() & DnDConstants.ACTION_COPY) != 0)
            {
                return true;
            }
            else return false;
        }
        else return false;
    }

    public void drop(DropTargetDropEvent e)
    {
        // Check DataFlavor
        DataFlavor[] dfs = e.getCurrentDataFlavors();
        DataFlavor tdf = null;
        for (int i = 0; i < dfs.length; i++)
        {
            if (DataFlavor.javaFileListFlavor.equals(dfs[i]))
            {
                tdf = dfs[i];
                break;
            }
            else if (DataFlavor.stringFlavor.equals(dfs[i]))
            {
                tdf = dfs[i];
                break;
            }
        }
        // Data Flavor available ?
        if (tdf != null)
        {
            // Accept COPY DnD only.
            if ((e.getSourceActions() & DnDConstants.ACTION_COPY) != 0)
            {
                e.acceptDrop(DnDConstants.ACTION_COPY);
            }
            else return;
            try
            {
                Transferable t = e.getTransferable();
                Object data = t.getTransferData(tdf);
                processDrop(data);
            }
            catch (IOException ioe)
            {
                log.info("Drop error", ioe);
                e.dropComplete(false);
                return;
            }
            catch (UnsupportedFlavorException ufe)
            {
                log.info("Drop error", ufe);
                e.dropComplete(false);
                return;
            }
            catch (Exception ex)
            {
                log.info("Drop error", ex);
                e.dropComplete(false);
                return;
            }
            e.dropComplete(true);
        }
    }

    public void processDrop(Object data)
    {
    }
}
