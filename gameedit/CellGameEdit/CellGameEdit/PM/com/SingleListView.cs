using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace CellGameEdit.PM.com
{
    public class SingleListView : ListView
    {
        private const int WM_LBUTTONDBLCLK = 0x0203;
        public SingleListView()
            : base()
        {
        }
        protected override void WndProc(ref Message m)
        {
//             if (m.Msg == 0x201 || m.Msg == 0x203)
//             {  // Trap WM_LBUTTONDOWN + double click
//                 var pos = new Point(m.LParam.ToInt32() & 0xffff, m.LParam.ToInt32() >> 16);
//                 var loc = this.HitTest(pos);
//                 switch (loc.Location)
//                 {
//                     case ListViewHitTestLocations.None:
//                     case ListViewHitTestLocations.AboveClientArea:
//                     case ListViewHitTestLocations.BelowClientArea:
//                     case ListViewHitTestLocations.LeftOfClientArea:
//                     case ListViewHitTestLocations.RightOfClientArea:
//                         return;  // Don't let the native control see it
//                 }
//             }
             if (m.Msg == WM_LBUTTONDBLCLK)
             {
                 //Point p = PointToClient(new Point(Cursor.Position.X, Cursor.Position.Y));
                 //ListViewItem lvi = GetItemAt(p.X, p.Y);
                 OnDoubleClick(new EventArgs());
             }
            else
            {
                base.WndProc(ref m);
            }
        }
    }
}
