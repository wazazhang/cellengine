using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Runtime.Serialization.Formatters;
using System.IO;

using javax.microedition.lcdui;
using System.Runtime.InteropServices;
using System.Runtime.Remoting;
using Cell;
using CellGameEdit.PM.plugin;

namespace CellGameEdit.PM
{
    [Serializable]
    public partial class WorldForm : Form, ISerializable , IEditForm
    {
        static public Boolean IsShowDataString = false;

       

        public String id;
        public StringBuilder Data = new StringBuilder();

        Hashtable UnitList;
        Hashtable WayPointsList;
        Hashtable RegionsList;
        Hashtable EventList;

        int CellW = 16;
        int CellH = 16;

        int RuleX;
        int RuleY;

        int[][] TerrainMatrix;

        int TerrainFillSize = 1;

        private long EventIDIndex = 0;

		// picturebox1 dst level

		int last_mouse_down_x = 0;
		int last_mouse_down_y = 0;
		ListViewItem popedWayPoint;

		Point selectedOffset = new Point();



        public WorldForm(String name)
        {
            InitializeComponent();
            numericUpDown1.Value = CellW;
            numericUpDown2.Value = CellH;

            id = name; this.Text = id;

            UnitList = new Hashtable();
            WayPointsList = new Hashtable();
            RegionsList = new Hashtable();
            EventList = new Hashtable();
            //WayPoints = new ArrayList();
            //Regions = new ArrayList();

            resetTerrainSize();
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected WorldForm(SerializationInfo info, StreamingContext context)
        {
            InitializeComponent();
            numericUpDown1.Value = CellW;
            numericUpDown2.Value = CellH;

            UnitList = new Hashtable();
            WayPointsList = new Hashtable();
            RegionsList = new Hashtable();
            EventList = new Hashtable();
            try
            {
                id = (String)info.GetValue("id", typeof(String));
                this.Text = id;

                try {
                    EventIDIndex = (long)info.GetValue("EventIDIndex", typeof(long));
                }catch(Exception err){}

                // unit
                #region Units 
                ArrayList Units = (ArrayList)info.GetValue("Units", typeof(ArrayList));
                for (int i = 0; i < Units.Count; i++)
                {
                    try
                    {
                        Unit unit = ((Unit)Units[i]);
						ListViewItem item = unit.listItem;
                        listView1.Items.Add(item);
                        UnitList.Add(item, unit); 
                        item.Checked = unit.isSaveChecked;
                        //item.Selected = true;
                        //Console.WriteLine("Level: Load " + unit.id);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace);
                    }
                }
                //listView1.Refresh();
                #endregion

                // WayPoint
                #region WayPoints
                try
                {
                    ArrayList WayPoints = (ArrayList)info.GetValue("WayPoints", typeof(ArrayList));
                    for (int i = 0; i < WayPoints.Count; i++)
                    {
                        try
                        {
                            WayPoint wp = ((WayPoint)WayPoints[i]);
                            ListViewItem item = wp.listItem;
                            listView2.Items.Add(item);
                            WayPointsList.Add(item, wp);
                            //item.Selected = true;
                            //Console.WriteLine("Level: Load " + unit.id);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace);
                        }
                    }
                }
                catch (Exception err) { }
                //listView2.Refresh();
                #endregion

                // region
                #region Regions
                try
                {
                    ArrayList Regions = (ArrayList)info.GetValue("Regions", typeof(ArrayList));
                    for (int i = 0; i < Regions.Count; i++)
                    {
                        try
                        {
                            Region region = ((Region)Regions[i]);
                            ListViewItem item = region.listItem;
                            listView3.Items.Add(item);
                            RegionsList.Add(item, region);
                            //item.Selected = true;
                            //Console.WriteLine("Level: Load " + unit.id);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace);
                        }
                    }
                }
                catch (Exception err) { }
                //listView3.Refresh();
                #endregion

                #region Events
                try
                {
                    ArrayList Events = (ArrayList)info.GetValue("Events", typeof(ArrayList));
                    for (int i = 0; i < Events.Count; i++)
                    {
                        try
                        {
                            Event evt = ((Event)Events[i]);
                            listView4.Items.Add(evt.listItem);
                            EventList.Add(evt.listItem, evt);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace);
                        }
                    }
                }
                catch (Exception err) { }
                #endregion

                pictureBox1.Width = (int)info.GetValue("Width", typeof(int));
                pictureBox1.Height = (int)info.GetValue("Height", typeof(int));

                try
                {
                    CellW = (int)info.GetValue("CellW", typeof(int));
                    CellH = (int)info.GetValue("CellH", typeof(int));
                }
                catch (Exception err) { }

                numericUpDown1.Value = CellW;
                numericUpDown2.Value = CellH;
                numericUpDown3.Value = pictureBox1.Width;
                numericUpDown4.Value = pictureBox1.Height;

				try
				{
					chkLockSize.Checked = (Boolean)info.GetValue("LockSize", typeof(Boolean));
				}
				catch (Exception err) { }

                try {
                    Data.Append((String)info.GetValue("Data", typeof(String)));
                }  catch (Exception err){ }

                try {
                    TerrainMatrix = (int[][])info.GetValue("TerrainMatrix", typeof(int[][]));
                } catch (Exception err) { }

                try {
                    pictureBox1.BackColor = (Color)info.GetValue("BackColor", typeof(Color));
                } catch (Exception err) { }

            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace);
            }
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            Console.WriteLine("Serialize World : " + id);

            resetTerrainSize();

            testDelUnit();

            try
            {
                info.AddValue("id", id);

                #region Units
                ArrayList Units = new ArrayList();
                for (int i = 0; i < listView1.Items.Count; i++)
                {
                    try
                    {
                        Unit unit = (Unit)UnitList[listView1.Items[i]];
                        unit.id = listView1.Items[i].Text;
                        unit.isSaveChecked = listView1.Items[i].Checked;
                        Units.Add(unit);

                        //Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace);
                    }
                }
                info.AddValue("Units", Units);
                info.AddValue("UnitList", UnitList);
                #endregion
				//////////////////////////////////////////////
                #region WayPoints
                ArrayList WayPoints = new ArrayList();
                for (int i = 0; i < listView2.Items.Count; i++)
                {
                    try
                    {
                        WayPoint wp = (WayPoint)WayPointsList[listView2.Items[i]];
                        WayPoints.Add(wp);
                        //Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace);
                    }
                }
                info.AddValue("WayPoints", WayPoints);
                info.AddValue("WayPointsList", WayPointsList);
                #endregion
				/////////////////////////////////////////
                #region Regions
                ArrayList Regions = new ArrayList();
                for (int i = 0; i < listView3.Items.Count; i++)
                {
                    try
                    {
                        Region region = (Region)RegionsList[listView3.Items[i]];
                        Regions.Add(region);
                        //Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace);
                    }
                }
                info.AddValue("Regions", Regions);
                info.AddValue("RegionsList", RegionsList);
                #endregion
				//////////////////////////////////////
                #region Events
                ArrayList Events = new ArrayList();
                for (int i = 0; i < listView4.Items.Count; i++)
                {
                    try
                    {
                        Event evt = (Event)EventList[listView4.Items[i]];
                        Events.Add(evt);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace);
                    }
                }
                info.AddValue("Events", Events);
                info.AddValue("EventsList", EventList);
                #endregion

                ///////////////////////////////////////////////////////////////////////////
                info.AddValue("Width", pictureBox1.Width);
                info.AddValue("Height", pictureBox1.Height);

                info.AddValue("CellW", CellW);
                info.AddValue("CellH", CellH);

				info.AddValue("LockSize", chkLockSize.Checked);

                info.AddValue("Data", this.Data.ToString());

                info.AddValue("TerrainMatrix", TerrainMatrix);

                info.AddValue("BackColor", pictureBox1.BackColor);
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace);
            }
        }
        public String getID()
        {
            return id;
        }

        public Form getForm()
        {
            return this;
        }
        public void ChangeAllUnits(ArrayList maps, ArrayList sprs, ArrayList imgs)
        {
            Hashtable mapsHT = new Hashtable();
            Hashtable sprsHT = new Hashtable();
            Hashtable imgsHT = new Hashtable();

            foreach (MapForm mf in maps)
            {
                mapsHT.Add(mf.super.id + "." + mf.id, mf);
            }
            foreach (SpriteForm sf in sprs)
            {
                sprsHT.Add(sf.super.id + "." + sf.id, sf);
            }
            foreach (ImagesForm imf in imgs)
            {
                imgsHT.Add(imf.id , imf);
            }

            for (int i = 0; i < listView1.Items.Count; i++)
            {
                try
                {
                    Unit unit = (Unit)UnitList[listView1.Items[i]];
                    if (unit.type == Unit.TYPE_MAP)
                    {
                        String key = unit.SuperName + "." + unit.Name;
						if (mapsHT.ContainsKey(key))
                        {
							unit.setSuper(null, (MapForm)mapsHT[key], null);
							Console.WriteLine("World ChangeUnit : " + key);
                        }
                    }
                    if (unit.type == Unit.TYPE_SPRITE)
                    {
                        String key = unit.SuperName + "." + unit.Name;
						if (sprsHT.ContainsKey(key))
                        {
                            unit.setSuper((SpriteForm)sprsHT[key], null, null);
							Console.WriteLine("World ChangeUnit : " + key);
                        }
                    }
                    if (unit.type == Unit.TYPE_IMAGE)
                    {
                        String key = unit.Name;
                        if (imgsHT.ContainsKey(key))
                        {
                            unit.setSuper(null, null, (ImagesForm)imgsHT[key]);
                            Console.WriteLine("World ChangeUnit : " + key);
                        }
                    }
                }
                catch (Exception err)
                {
                    Console.WriteLine(this.id + " Change Units : " + err.StackTrace);
                }
            }
        }

        public void loadOver()
        {
            EventTemplatePlugin etp = ProjectForm.getInstance().getEventTemplateForm();
            if (etp != null && etp.getImageList()!=null)
            {
                this.listView4.SmallImageList = etp.getImageList();
            }
            foreach (Unit unit in UnitList.Values)
            {
                unit.loadOver();
            }
            foreach (WayPoint wp in WayPointsList.Values)
            {
                wp.loadOver();
            }
            foreach (Region rg in RegionsList.Values)
            {
                rg.loadOver();
            } 
            foreach (Event ev in EventList.Values)
            {
                ev.loadOver();
            }
            //ImageList il = ProjectForm.getInstance().getEventTemplateForm().getShareImageList();
//             this.listView1.SmallImageList = il;
//             this.listView2.SmallImageList = il;
//             this.listView3.SmallImageList = il;
            //this.listView4.SmallImageList = il;
        }

        public void OutputCustom(int index, String script, System.IO.StringWriter output)
        {
            resetTerrainSize();

            lock (this)
            {
                try
                {
                    String world = Util.getFullTrunkScript(script, "<WORLD>", "</WORLD>");

                    ArrayList maps = new ArrayList();
                    ArrayList sprs = new ArrayList();
					ArrayList imgs = new ArrayList();
                    foreach (ListViewItem item in listView1.Items)
                    {
                        try
                        {
                            Unit unit = ((Unit)UnitList[item]);
							if (unit.type == Unit.TYPE_MAP) { maps.Add(unit); }
							if (unit.type == Unit.TYPE_SPRITE) { sprs.Add(unit); }
							if (unit.type == Unit.TYPE_IMAGE) { imgs.Add(unit); }
                            unit.id = item.Text;

                        }
                        catch (Exception err) { }
                    }

                    ArrayList WayPoints = new ArrayList();
                    for (int i = 0; i < listView2.Items.Count; i++)
                    {
                        try
                        {
                            WayPoint wp = (WayPoint)WayPointsList[listView2.Items[i]];
                            WayPoints.Add(wp);
                            //Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace);
                        }
                    }

                    ArrayList Regions = new ArrayList();
                    for (int i = 0; i < listView3.Items.Count; i++)
                    {
                        try
                        {
                            Region region = (Region)RegionsList[listView3.Items[i]];
                            Regions.Add(region);
                            //Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace);
                        }
                    }

					ArrayList oEvents = new ArrayList();
					for (int i = 0; i < listView4.Items.Count; i++)
					{
						try
						{
							Event ee = (Event)EventList[listView4.Items[i]];
							oEvents.Add(ee);
							//Console.WriteLine("Level: Add " + ((Unit)UnitList[listView1.Items[i]]).id);
						}
						catch (Exception err)
						{
							Console.WriteLine(this.id + " : " + err.StackTrace);
						}
					}


                    bool fix = false;

					// maps
					#region output_map
					do
                    {
                        String[] map = new string[maps.Count];
                        for (int i = 0; i < map.Length; i++)
                        {
                            string X = ((Unit)maps[i]).getX().ToString();
							string Y = ((Unit)maps[i]).getY().ToString();
                            string ID = ((Unit)maps[i]).map().id;
                            string NAME = ((Unit)maps[i]).id;
                            string SUPER = ((Unit)maps[i]).map().super.id;
                            string PRIORITY = ((Unit)maps[i]).Priority.ToString();

                            String ignoreKey = null;
                            if (Util.testIgnore("<IGNORE_MAP>", world, ID, ref ignoreKey) == true)
                            {
                                map[i] = "";
                                continue;
                            }

                            String keepKey = null;
                            if (Util.testKeep("<KEEP_MAP>", world, ID, ref keepKey) == false)
                            {
                                map[i] = "";
                                continue;
                            }

                            string[] data = (Util.toStringMultiLine(((Unit)maps[i]).Data.ToString())) ;
                            string MAP_DATA = Util.toStringArray1D(ref data);

                            map[i] = Util.replaceKeywordsScript(world, "<UNIT_MAP>", "</UNIT_MAP>",
                                   new string[] { "<MAP_NAME>", "<IDENTIFY>", "<INDEX>", "<X>", "<Y>", "<SUPER>", "<MAP_DATA>", "<PRIORITY>" },
                                   new string[] { NAME, ID, i.ToString(), X, Y, SUPER, MAP_DATA, PRIORITY });
                        }
                        string temp = Util.replaceSubTrunksScript(world, "<UNIT_MAP>", "</UNIT_MAP>", map);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            world = temp;
                        }
                    } while (fix);
					#endregion

					// sprs
					#region output_spr
					do
                    {
                        String[] spr = new string[sprs.Count];
                        for (int i = 0; i < spr.Length; i++)
                        {
                            string X = ((Unit)sprs[i]).getX().ToString();
                            string Y = ((Unit)sprs[i]).getY().ToString();
                            string ID = ((Unit)sprs[i]).spr().id;
                            string NAME = ((Unit)sprs[i]).id;
                            string SUPER = ((Unit)sprs[i]).spr().super.id;
                            string ANIM_ID = ((Unit)sprs[i]).animID.ToString();
                            string FRAME_ID = ((Unit)sprs[i]).frameID.ToString();
                            //string SPR_DATA = ((Unit)sprs[i]).Data.ToString();
                            string PRIORITY = ((Unit)sprs[i]).Priority.ToString();
                            
                            String ignoreKey = null;
                            if (Util.testIgnore("<IGNORE_SPR>", world, ID, ref ignoreKey) == true)
                            {
                                spr[i] = "";
                                continue;
                            }

                            String keepKey = null;
                            if (Util.testKeep("<KEEP_SPR>", world, ID, ref keepKey) == false)
                            {
                                spr[i] = "";
                                continue;
                            }


                            string[] data = Util.toStringMultiLine(((Unit)sprs[i]).Data.ToString());
                            string SPR_DATA = Util.toStringArray1D(ref data);
                            spr[i] = Util.replaceKeywordsScript(world, "<UNIT_SPRITE>", "</UNIT_SPRITE>",
                                   new string[] { "<SPR_NAME>", "<IDENTIFY>", "<INDEX>", "<X>", "<Y>", "<ANIMATE_ID>", "<FRAME_ID>", "<SUPER>", "<SPR_DATA>", "<PRIORITY>" },
                                   new string[] { NAME, ID, i.ToString(), X, Y, ANIM_ID, FRAME_ID, SUPER, SPR_DATA, PRIORITY });
                        }
                        string temp = Util.replaceSubTrunksScript(world, "<UNIT_SPRITE>", "</UNIT_SPRITE>", spr);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            world = temp;
                        }
                    } while (fix);
					#endregion

					//image tile
					#region imageTile
					do
					{
						String[] imga = new string[imgs.Count];
						for (int i = 0; i < imga.Length; i++)
						{
							Unit img = (Unit)imgs[i];

							string X = img.getX().ToString();
							string Y = img.getY().ToString();
							string ID = img.images().id;
							string NAME = img.id;
							string ANCHOR = img.anchor.ToString();
							string TILE_ID = img.animID.ToString();
							string PRIORITY = img.Priority.ToString();
                            string TRANS = img.trans.ToString();
							String ignoreKey = null;
							if (Util.testIgnore("<IGNORE_IMG>", world, ID, ref ignoreKey) == true)
							{
								imga[i] = "";
								continue;
							}

							String keepKey = null;
							if (Util.testKeep("<KEEP_IMG>", world, ID, ref keepKey) == false)
							{
								imga[i] = "";
								continue;
							}

							string[] data = Util.toStringMultiLine(img.Data.ToString());
							string IMG_DATA = Util.toStringArray1D(ref data);
							imga[i] = Util.replaceKeywordsScript(world, "<UNIT_IMAGE>", "</UNIT_IMAGE>",
								   new string[] { "<IMG_NAME>",
									   "<IDENTIFY>", 
									   "<INDEX>", 
									   "<X>", "<Y>", 
									   "<TILE_ID>",
									   "<ANCHOR>", "<TRANS>",
									   "<IMG_DATA>",
									   "<PRIORITY>" },
								   new string[] { NAME,
									   ID,
									   i.ToString(), 
									   X, Y, 
									   TILE_ID, 
									   ANCHOR, TRANS,
									   IMG_DATA,
									   PRIORITY });
						}
						string temp = Util.replaceSubTrunksScript(world, 
							"<UNIT_IMAGE>", "</UNIT_IMAGE>", imga);
						if (temp == null)
						{
							fix = false;
						}
						else
						{
							fix = true;
							world = temp;
						}
					} while (fix);
					#endregion
					

					//way points
					#region output_waypoint
					do
                    {
                        String[] wp = new string[WayPoints.Count];
                        for (int i = 0; i < wp.Length; i++)
                        {
                            WayPoint p = ((WayPoint)WayPoints[i]);
							
							string X = p.getX().ToString();
                            string Y = p.getY().ToString();
                            string[] data = Util.toStringMultiLine(p.Data.ToString());
                            string PATH_DATA = Util.toStringArray1D(ref data);
                            wp[i] = Util.replaceKeywordsScript(world, "<WAYPOINT>", "</WAYPOINT>",
                                   new string[] { "<INDEX>", "<X>", "<Y>", "<PATH_DATA>" },
                                   new string[] { i.ToString(), X, Y, PATH_DATA });
                        }
                        string temp = Util.replaceSubTrunksScript(world, "<WAYPOINT>", "</WAYPOINT>", wp);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            world = temp;
                        }
                    } while (fix);

                    do
                    {
                        ArrayList link = new ArrayList();
                        foreach (WayPoint p in WayPoints)
                        {
                            try
                            {
                                if (p != null)
                                {//
                                    foreach (WayPoint l in p.link)
                                    {
                                        try
                                        {
                                            if (l != null)
                                            {//
                                                string START = WayPoints.IndexOf(p).ToString();
                                                string END = WayPoints.IndexOf(l).ToString();
                                                link.Add(
                                                    Util.replaceKeywordsScript(world, "<WAYPOINT_LINK>", "</WAYPOINT_LINK>",
                                                       new string[] { "<INDEX>", "<START>", "<END>", },
                                                       new string[] { link.Count.ToString(), START, END, })
                                                );
                                            }
                                        }
                                        catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace); }
                                    }//
                                }
                            }
                            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace); }
                        }//
                        String[] slink = new string[link.Count];
                        slink = (String[])link.ToArray(typeof(String));
                        string temp = Util.replaceSubTrunksScript(world, "<WAYPOINT_LINK>", "</WAYPOINT_LINK>", slink);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            world = temp;
                        }
                    } while (fix);
					#endregion

					// regions 
					#region output_region
					do
                    {
                        String[] region = new string[Regions.Count];
                        for (int i = 0; i < region.Length; i++)
                        {
                            Region r = ((Region)Regions[i]);
							Rectangle rect = r.getWorldBounds();
                            string X = (rect.X) + "";
                            string Y = (rect.Y) + "";
                            string W = (rect.Width) + "";
                            string H = (rect.Height) + "";
                            string[] data = Util.toStringMultiLine(r.Data.ToString());
                            string REGION_DATA = Util.toStringArray1D(ref data);
                            region[i] = Util.replaceKeywordsScript(world, "<REGION>", "</REGION>",
                                   new string[] { "<INDEX>", "<X>", "<Y>", "<W>", "<H>", "<REGION_DATA>" },
                                   new string[] { i.ToString(), X, Y, W, H, REGION_DATA });
                        }
                        string temp = Util.replaceSubTrunksScript(world, "<REGION>", "</REGION>", region);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            world = temp;
                        }
                    } while (fix);
					#endregion

					// events
					#region output_events
					do
					{
						String[] events = new string[oEvents.Count];
						for (int i = 0; i < events.Length; i++)
						{
							Event r = ((Event)oEvents[i]);
							string[] data = Util.toStringMultiLine(r.Data.ToString());
							string EVENT_DATA = Util.toStringArray1D(ref data);
							events[i] = Util.replaceKeywordsScript(world, "<EVENT>", "</EVENT>",
								   new string[] { 
									   "<INDEX>", "<ID>", 
									   "<EVENT_NAME>", "<EVENT_FILE>", 
									   "<X>", "<Y>", 
									   "<EVENT_DATA>" },
								   new string[] { 
									   i.ToString(), r.ID.ToString(),  
									   r.getEventName(), r.getEventFile(), 
									   r.getX().ToString(), r.getY().ToString(), 
									   EVENT_DATA });
						}
						string temp = Util.replaceSubTrunksScript(world, "<EVENT>", "</EVENT>", events);
						if (temp == null)
						{
							fix = false;
						}
						else
						{
							fix = true;
							world = temp;
						}
					} while (fix);
					#endregion


                    //world data
                    string[] world_data = Util.toStringMultiLine(this.Data.ToString());

                    string terrain_data = "";

                    for (int x = 0; x < TerrainMatrix.Length; x++)
                    {
                        for (int y = 0; y < TerrainMatrix[x].Length; y++)
                        {
                            terrain_data += (TerrainMatrix[x][y] + ",");
                        }
                    }


                    // world key words
                    world = Util.replaceKeywordsScript(world, "<WORLD>", "</WORLD>",
                        new string[] { 
                            "<NAME>", 
                            "<WORLD_INDEX>",
                            "<DATA>",
                            "<WIDTH>",
                            "<HEIGHT>",
                            "<GRID_W>",
                            "<GRID_H>",
                            "<GRID_X_COUNT>",
                            "<GRID_Y_COUNT>",
                            "<TERRAIN>",
                            "<UNIT_MAP_COUNT>" ,
                            "<UNIT_SPRITE_COUNT>",
							"<UNIT_IMAGE_COUNT>",
                            "<WAYPOINT_COUNT>",
                            "<REGION_COUNT>",
							"<EVENT_COUNT>"
                    },
                        new string[] { 
                            this.id, 
                            index.ToString(),
                            Util.toStringArray1D(ref world_data),
                            pictureBox1.Width.ToString(),
                            pictureBox1.Height.ToString(),
                            CellW.ToString(),
                            CellH.ToString(),
                            TerrainMatrix.Length.ToString(),
                            TerrainMatrix[0].Length.ToString(),
                            terrain_data,
                            maps.Count.ToString(),
                            sprs.Count.ToString(),
							imgs.Count.ToString(),
                            WayPoints.Count.ToString(),
                            Regions.Count.ToString(),
							oEvents.Count.ToString()
                    }
                        );

                    output.WriteLine(world);
                    //Console.WriteLine(world);
                }
                catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace); }
            }
        }


        private void WorldForm_Load(object sender, EventArgs e)
        {
            numericUpDown1.Value = CellW;
            numericUpDown2.Value = CellH;
            numericUpDown3.Value = pictureBox1.Width;
            numericUpDown4.Value = pictureBox1.Height;


            listView1.ListViewItemSorter = new ObjectsViewSorter(UnitList);
            listView1.Sort();

			pictureBox1.MouseWheel += new MouseEventHandler(pictureBox1_MouseWheel);
			textBox1.MouseWheel += new MouseEventHandler(textBox1_MouseWheel);
        }

        private void WorldForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }


        private void WorldForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

        private void WorldForm_Shown(object sender, EventArgs e)
        {
        
        }
        private void WorldForm_Activated(object sender, EventArgs e)
        {
           
        }
        private void WorldForm_VisibleChanged(object sender, EventArgs e)
        {
            if (this.Visible)
			{
				tabControl1.SelectTab(3);
                tabControl1.SelectTab(2);
                tabControl1.SelectTab(1);
                tabControl1.SelectTab(0);
            }
        }

        public void addUnitAsMap(MapForm map, int x, int y)
        {
            Unit unit = new Unit(map, "M" + (listView1.Items.Count).ToString("d3") + "_", listView1);
            ListViewItem item = unit.listItem;
            unit.listItem = item;
            unit.setPos(x, y);
            listView1.Items.Add(item);
            UnitList.Add(item, unit);
            item.Selected = true;
            item.EnsureVisible();
			Rectangle rect = unit.getWorldBounds();
			pictureBox1.Width = Math.Max(rect.X + rect.Width, pictureBox1.Width);
			pictureBox1.Height = Math.Max(rect.Y + rect.Height, pictureBox1.Height);

            CellW = map.CellW;
            CellH = map.CellH;

            if (!chkLockSize.Checked) {
                pictureBox1.Width = map.getWidth();
                pictureBox1.Height = map.getHeight();
                numericUpDown1.Value = CellW;
                numericUpDown2.Value = CellH;
                numericUpDown3.Value = pictureBox1.Width;
                numericUpDown4.Value = pictureBox1.Height;
            }
        }

        public void addUnitAsSpr(SpriteForm spr, int x, int y)
        {
            Unit unit = new Unit(spr, "S" + (listView1.Items.Count).ToString("d3") + "_", listView1);
			unit.setPos(x, y);
            ListViewItem item = unit.listItem;
            item.Checked = true;
            unit.listItem = item;
          
            listView1.Items.Add(item);
            UnitList.Add(item, unit);
            item.Selected = true;
            item.EnsureVisible();
        }

        public void addUnitAsImage(ImagesForm img, int x, int y, int tileID)
        {
            Unit unit = new Unit(img, "T" + (listView1.Items.Count).ToString("d3") + "_", listView1, tileID);
			unit.setPos(x, y);

            ListViewItem item = unit.listItem;
            item.Checked = true;
            unit.listItem = item;

            listView1.Items.Add(item);
            UnitList.Add(item, unit);
            item.Selected = true;
            item.EnsureVisible();
        }



        // list view1 map list
        private void listView1_DragDrop(object sender, DragEventArgs e)
        {
            if ((MapForm)e.Data.GetData(typeof(MapForm)) != null)
            {
                MapForm map = ((MapForm)e.Data.GetData(typeof(MapForm)));
				addUnitAsMap(map, 0, 0);
            }
            if ((SpriteForm)e.Data.GetData(typeof(SpriteForm)) != null)
            {
                SpriteForm spr = ((SpriteForm)e.Data.GetData(typeof(SpriteForm)));
                addUnitAsSpr(spr, pictureBox1.Width/2, pictureBox1.Height/2);
            }
            if ((ImagesForm)e.Data.GetData(typeof(ImagesForm)) != null)
            {
                ImagesForm img = ((ImagesForm)e.Data.GetData(typeof(ImagesForm)));
                addUnitAsImage(img, pictureBox1.Width / 2, pictureBox1.Height / 2,
					img.getAvaliableImageIndex());
            }
            listView1.Refresh();
            pictureBox1.Refresh();

            
            
            //Console.WriteLine("drag");
        }
        private void listView1_DragEnter(object sender, DragEventArgs e)
        {
            e.Effect = e.AllowedEffect;
            //Console.WriteLine("enter");
        }
        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
			Unit unit = getSelectedUnit();
			if (unit != null) {
				unit.updateListViewItem(toolStripStatusLabel1);
			}
            pictureBox1.Refresh();
        }
        private void listView1_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
        }
        private void listView1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button ==MouseButtons.Right && listView1.SelectedItems.Count > 0)
            {
                menuUnit.Show(listView1, e.Location);
            }
        }
        
        // way point list
        private void listView2_SelectedIndexChanged(object sender, EventArgs e)
        {
            popedWayPoint = null;
			WayPoint wp = getSelectedWayPoint();
			if (wp != null)
			{
				wp.updateListViewItem(toolStripStatusLabel1);
			}
			pictureBox1.Refresh();
        }
        private void listView2_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right && listView2.SelectedItems.Count > 0)
            {
                popedWayPoint = null;

                menuPath.Items[0].Visible = false;
                menuPath.Items[1].Visible = false;
                menuPath.Items[2].Visible = false;
                menuPath.Items[3].Visible = true;
                menuPath.Items[4].Visible = true;
                menuPath.Show(listView2, e.Location);     
                
            }
        }

        // region list
        private void listView3_SelectedIndexChanged(object sender, EventArgs e)
        {
			Region rg = getSelectedRegion();
			if (rg != null)
			{
				rg.updateListViewItem(toolStripStatusLabel1);
			}
			pictureBox1.Refresh();
        }
        private void listView3_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right && listView3.SelectedItems.Count > 0)
            {
                menuRegion.Show(listView3, e.Location);
            }
        }

        private void listView4_SelectedIndexChanged(object sender, EventArgs e)
        {
            Event ev = getSelectedEvent();
			if (ev != null)
			{
				ev.updateListViewItem(toolStripStatusLabel1);
			}
            pictureBox1.Refresh();
        }

        private void listView4_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right && listView4.SelectedItems.Count > 0)
            {
                menuEvent.Show(listView4, e.Location);
            }
        }


        ///////////////////////////////////////////////////////////////////////////////////////////
        // terrain

        private void fillTerrain(int x, int y, int value)
        {
            if (TerrainFillSize <= 0) { TerrainFillSize = 1; }

            int dx = x / CellW;
            int dy = y / CellH;

            if (TerrainMatrix != null)
            {
                int sx = dx - TerrainFillSize / 2;
                int sy = dy - TerrainFillSize / 2;
                int sw = TerrainFillSize;
                int sh = TerrainFillSize;

                for (int fx = Math.Max(0, sx); fx < sx + sw; fx++)
                {
                    for (int fy = Math.Max(0, sy); fy < sy + sh; fy++)
                    {
                        if (fx < TerrainMatrix.Length && fy < TerrainMatrix[fx].Length)
                        {
                            TerrainMatrix[fx][fy] = value;
                        }
                    }
                }
            }


        }

        private void resetTerrainSize()
        {
            int xdiv = pictureBox1.Width % CellW == 0 ? 0 : 1;
            int ydiv = pictureBox1.Height % CellH == 0 ? 0 : 1;

            int xcount = pictureBox1.Width / CellW + xdiv;
            int ycount = pictureBox1.Height / CellH + ydiv;

            xcount = Math.Max(xcount, 1);
            ycount = Math.Max(ycount, 1);

            if (TerrainMatrix == null || xcount != TerrainMatrix.Length || ycount != TerrainMatrix[0].Length)
            {
                int[][] matrix = new int[xcount][];

                for (int x = 0; x < xcount; x++)
                {
                    matrix[x] = new int[ycount];

                    if (TerrainMatrix != null)
                    {
                        if (x < TerrainMatrix.Length)
                        {
                            for (int y = 0; y < ycount; y++)
                            {
                                if (y < TerrainMatrix[x].Length)
                                {
                                    matrix[x][y] = TerrainMatrix[x][y];
                                }
                            }
                        }
                    }
                }

                TerrainMatrix = matrix;
            }

        }

        private void btnSceneTerrain_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        // 调整填充地形大小
        private void fixTerrainBushSize(int value) { 
            TerrainFillSize += value;
            TerrainFillSize = Math.Max(1, TerrainFillSize);
        }

        private void btnSceneTerrainColor_Click(object sender, EventArgs e)
        {
            ColorDialog colord = new ColorDialog();
            colord.Color = btnSceneTerrainColor.BackColor;
            if (colord.ShowDialog() == DialogResult.OK)
            {
                btnSceneTerrainColor.BackColor = colord.Color;
            }
        }


        private void btnSceneTerrainUp_Click(object sender, EventArgs e)
        {
            fixTerrainBushSize(1);
        }

        private void btnSceneTerrainDown_Click(object sender, EventArgs e)
        {
            fixTerrainBushSize(-1);
        }


        /// <summary>
        /// 主画板更新
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            javax.microedition.lcdui.Graphics g = new javax.microedition.lcdui.Graphics(e.Graphics);

            IsShowDataString = IsShowData.Checked;

            drawUnits(g);
            drawWayPoints(g);
            drawRegions(g);
            drawEvents(g);

            // draw terrain
            if (btnSceneTerrain.Checked || btnShowTerrain.Checked)
            {
                if (TerrainMatrix != null)
                {
                    Rectangle tbounds = getCameraBoundsTerrian();

                    for (int x = tbounds.Left; x < tbounds.Right; x++)
                    {
                        for (int y = tbounds.Top; y < tbounds.Bottom; y++)
                        {
                            if (TerrainMatrix[x][y] != 0)
                            {
                                g.setColor(0x80, TerrainMatrix[x][y]);
                                g.fillRect(x * CellW, y * CellH, CellW, CellH);
                            }
                        }
                    }
                }
            }

            // draw net grid
            if (toolStripButton12.Checked)
            {
                g.setColor(0x80, 0xff, 0xff, 0xff);

                int sx = -pictureBox1.Location.X / CellW;
                int sy = -pictureBox1.Location.Y / CellH;
                int sw = panel1.Width / CellW + 1;
                int sh = panel1.Height / CellH + 1;

                for (int bx = sx; bx < sx + sw; bx++)
                {
                    g.drawLine(
                       bx * CellW,
                       sy * CellH,
                       bx * CellW,
                       sy * CellH + sh * CellH);
                }
                for (int by = sy; by < sy + sh; by++)
                {
                    g.drawLine(
                        sx * CellW,
                        by * CellH,
                        sx * CellW + sw * CellW,
                        by * CellH);
                }

            }

            // 画尺子
            if (btnSceneRule.Checked)
            {
                toolStripStatusLabel2.Text = 
                    "Mouse:" + RuleX + "," + RuleY + " ";
                g.setColor(0x80, 0xff, 0xff, 0xff);
                g.drawLine(RuleX, 0, RuleX, pictureBox1.Height);
                g.drawLine(0, RuleY, pictureBox1.Width, RuleY);
            }

            // 画填充地形的区域
            if (btnSceneTerrain.Checked)
            {
                int dx = RuleX / CellW * CellW;
                int dy = RuleY / CellH * CellH;

                int sx = dx - TerrainFillSize / 2 * CellW;
                int sy = dy - TerrainFillSize / 2 * CellH;
                int sw = TerrainFillSize * CellW;
                int sh = TerrainFillSize * CellH;

                g.setColor(btnSceneTerrainColor.BackColor.ToArgb());
                g.drawRect(sx, sy, sw, sh);

            }

        }

		private void drawUnits(javax.microedition.lcdui.Graphics g)
		{
			try
			{
				// draw units
				if (checkShowMap.Checked || checkShowSprite.Checked)
				{
					Rectangle rect = new Rectangle(
												   -pictureBox1.Location.X,
												   -pictureBox1.Location.Y,
												   panel1.Width,
												   panel1.Height
												   );
					Rectangle drawRectMap1 = new System.Drawing.Rectangle(
											-pictureBox1.Location.X,
											-pictureBox1.Location.Y,
											panel1.Width,
											panel1.Height
									   );
					Rectangle drawRectSpr = new System.Drawing.Rectangle(
											-pictureBox1.Location.X,
											-pictureBox1.Location.Y,
											splitContainer1.Panel2.Width,
											splitContainer1.Panel2.Height
									   );

					foreach (ListViewItem item in listView1.Items)
					{
						Unit unit = ((Unit)UnitList[item]);

						if (unit.type == Unit.TYPE_MAP && checkShowMap.Checked)
						{
							// cyc map
							if (toolStripButton10.Checked)
							{
								#region cycmap
								for (int x = 0; x < pictureBox1.Width; x += unit.map().getWidth())
								{
									for (int y = 0; y < pictureBox1.Height; y += unit.map().getHeight())
									{

										if (rect.IntersectsWith(
											 new System.Drawing.Rectangle(
												x, y,
												unit.map().getWidth(),
												unit.map().getHeight())
												)
											)
										{
											unit.render(
											   g,
											   new System.Drawing.Rectangle(
													-pictureBox1.Location.X - x,
													-pictureBox1.Location.Y - y,
													panel1.Width,
													panel1.Height
											   ), x, y,
											   listView1.SelectedItems.Contains(item),
											   checkShowUnitBounds.Checked,
											   toolStripButton2.Checked,
											   toolShowLock.Checked
											);
										}
									}
								}
								#endregion
							}
							else
							{
								unit.render(
								   g,
								   drawRectMap1,
								   item.Selected,
								   checkShowUnitBounds.Checked,
								   toolStripButton2.Checked,
								   toolShowLock.Checked
								);
							}
						}
						else if (unit.type == Unit.TYPE_SPRITE && checkShowSprite.Checked)
						{
							unit.render(
								   g,
								   drawRectSpr,
								   item.Selected,
								   checkShowUnitBounds.Checked,
								   toolStripButton2.Checked,
								   toolShowLock.Checked
								);
						}
						else if (unit.type == Unit.TYPE_IMAGE && checkShowTile.Checked)
						{
							unit.render(
								   g,
								   drawRectSpr,
								   item.Selected,
								   checkShowUnitBounds.Checked,
								   toolStripButton2.Checked,
								   toolShowLock.Checked
								);
						}

					}
				}
			}
			catch (Exception err)
			{
				MessageBox.Show(err.Message);
			}
		}
        private void drawWayPoints(javax.microedition.lcdui.Graphics g)
        { // draw way points
            if (checkShowPoint.Checked)
            {
                foreach (ListViewItem item in listView2.Items)
                {
                    WayPoint p = (WayPoint)WayPointsList[item];
                   
                    if (p != null)
                    {
                        p.Render(g,item.Selected);
                    }
                }

                if (popedWayPoint != null)
                {
                    WayPoint p = ((WayPoint)WayPointsList[popedWayPoint]);
                    p.Render(g,true);
                    g.setColor(0xff, 0xff, 0xff, 0);
                    g.drawLine(
                        p.getX() ,
                        p.getY() - 5,
						p.getX(),
						p.getY() + 5
                        );
                    g.drawLine(
					   p.getX() - 5,
						p.getY(),
						p.getX() + 5,
					   p.getY()
                        );
                }
                if (listView2.SelectedItems.Count>0 && popedWayPoint != null)
                {
                    WayPoint p1 = ((WayPoint)WayPointsList[listView2.SelectedItems[0]]);
                    WayPoint p2 = ((WayPoint)WayPointsList[popedWayPoint]);

                    g.setColor(0x80, 0xff, 0, 0);
                    g.drawLine(
						 p1.getX(),
						 p1.getY(),
						 p2.getX(),
						 p2.getY()
                         );
                }
            }
        }
        private void drawRegions(javax.microedition.lcdui.Graphics g)
        {
            if (checkShowRegion.Checked)
            {
                foreach (ListViewItem item in listView3.Items)
                {
                    Region r = (Region)RegionsList[item];
                    if (r != null)
                    {
                        r.Render(g,item.Selected);
                    }
                }
            }

        }

        private void drawEvents(javax.microedition.lcdui.Graphics g)
        {
            if (checkShowEvent.Checked)
            {
                Rectangle rect = new Rectangle(
                                                  -pictureBox1.Location.X,
                                                  -pictureBox1.Location.Y,
                                                  panel1.Width,
                                                  panel1.Height
                                                  );
                foreach (ListViewItem item in listView4.Items)
                {
                    Event e = (Event)EventList[item];
                    if (e != null)
                    {
                        e.render(g, rect, item.Selected);
                        
//                         if (item.Selected)
//                         {
// 							toolStripStatusLabel1.Text = e.getToolStripText();
//                         }
                    }
                }
            }
        }

		private void unSelectAllUnit()
		{
			foreach (ListViewItem item in listView1.Items)
			{
				item.Selected = false;
			}
			popedWayPoint = null;
			foreach (ListViewItem item in listView2.Items)
			{
				item.Selected = false;
			}
			foreach (ListViewItem item in listView3.Items)
			{
				item.Selected = false;
				Region r = (Region)RegionsList[item];
				if (r != null)
				{
					r.isSub = false;
				}
			}
			foreach (ListViewItem item in listView4.Items)
			{
				item.Selected = false;
			}
		}


		private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
		{
			last_mouse_down_x = e.X;
			last_mouse_down_y = e.Y;

			try
			{
				textBox1.Focus();

				// 画地形
				if (btnSceneTerrain.Checked)
				{
					resetTerrainSize();

					if (e.Button == MouseButtons.Left)
					{
						fillTerrain(e.X, e.Y, btnSceneTerrainColor.BackColor.ToArgb());
					}
					else if (e.Button == MouseButtons.Right)
					{
						fillTerrain(e.X, e.Y, 0);
					}


				}
				else
				{
					#region pointUnit
					// 是定位指令
					if (e.Button == MouseButtons.Left && checkDirectUnit.Checked == true)
					{
						// map
						if (checkShowMap.Checked && tabControl1.SelectedTab == tabPageUnit)
						{
							if (listView1.SelectedItems.Count > 0)
							{
								Unit unit = getSelectedUnit();
								if (unit.map() != null)
								{
									if (unit.listItem.Checked)
									{
// 										unit.x = e.X;
// 										unit.y = e.Y;
										unit.setPos(e.X, e.Y);
									}
								}
							}
						}
						//select unit
						if (checkShowSprite.Checked && tabControl1.SelectedTab == tabPageUnit)
						{
							if (listView1.SelectedItems.Count > 0)
							{
								Unit unit = getSelectedUnit();
								if (unit.spr() != null)
								{
									if (unit.listItem.Checked)
									{
// 										unit.x = e.X;
// 										unit.y = e.Y; 
										unit.setPos(e.X, e.Y);
									}
								}
							}
						}
						//select image
						if (checkShowTile.Checked && tabControl1.SelectedTab == tabPageUnit)
						{
							if (listView1.SelectedItems.Count > 0)
							{
								Unit unit = getSelectedUnit();
								if (unit.images() != null)
								{
									if (unit.listItem.Checked)
									{
// 										unit.x = e.X;
// 										unit.y = e.Y;
										unit.setPos(e.X, e.Y);
									}
								}
							}
						}
						// show event
						if (checkShowEvent.Checked && tabControl1.SelectedTab == tabPageEvent)
						{
							if (listView4.SelectedItems.Count > 0)
							{
								Event ee = getSelectedEvent();
// 								ee.point.X = e.X;
// 								ee.point.Y = e.Y;
								ee.setPos(e.X, e.Y);
							}
						}
						//select way point
						if (checkShowPoint.Checked && tabControl1.SelectedTab == tabPageWP)
						{
							if (listView2.SelectedItems.Count > 0)
							{
								WayPoint wp = getSelectedWayPoint();
								wp.setPos(e.X, e.Y);
							}
						}
						//select region
						if (checkShowRegion.Checked && tabControl1.SelectedTab == tabPageRegion)
						{
							if (listView3.SelectedItems.Count > 0)
							{
								Region rg = getSelectedRegion();
								rg.setPos(e.X, e.Y);
							}
						}
					}
					#endregion

					#region selectUnit
					//不是定位指令
					if (e.Button == MouseButtons.Left && checkDirectUnit.Checked == false)
					{
						unSelectAllUnit();

						bool isChecked = false;

						//select way point
						if (!isChecked && checkShowPoint.Checked && tabControl1.SelectedTab == tabPageWP)
						{
							foreach (ListViewItem item in listView2.Items)
							{
								WayPoint p = (WayPoint)WayPointsList[item];
								if (p != null)
								{
									if (p.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										isChecked = true;
										item.Selected = true;
										p.updateListViewItem(toolStripStatusLabel1);
										break;
									}
								}
							}
						}
						// select regions
						if (!isChecked && checkShowRegion.Checked && tabControl1.SelectedTab == tabPageRegion)
						{
							foreach (ListViewItem item in listView3.Items)
							{
								Region r = (Region)RegionsList[item];
								if (r != null)
								{
									if (r.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										r.isSub = false;
										isChecked = true;
										item.Selected = true;
									}
									if (r.selectSub(e.X, e.Y))
									{
										r.isSub = true;
										isChecked = true;
										item.Selected = true;
									}
									if (isChecked)
									{
										r.updateListViewItem(toolStripStatusLabel1);
										break;
									}
								}
							}
						}
						// select event
						if (!isChecked && checkShowEvent.Checked && tabControl1.SelectedTab == tabPageEvent)
						{
							foreach (ListViewItem item in listView4.Items)
							{
								Event ee = (Event)EventList[item];
								if (ee != null)
								{
									if (ee.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										isChecked = true;
										item.Selected = true;
										ee.updateListViewItem(toolStripStatusLabel1);
										break;
									}
								}
							}
						}
						//select unit
						if (!isChecked && tabControl1.SelectedTab == tabPageUnit &&
							(checkShowMap.Checked || checkShowSprite.Checked || checkShowTile.Checked))
						{
							for (int i = listView1.Items.Count - 1; i >= 0; i--)
							{
								Unit unit = ((Unit)UnitList[listView1.Items[i]]);

								if (unit.map() != null && checkShowMap.Checked ||
									unit.spr() != null && checkShowSprite.Checked ||
									unit.images() != null && checkShowTile.Checked)
								{
									//if (unit.getGobalBounds().Contains(e.X, e.Y))
									if (unit.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										//unit.updateListViewItem();
										isChecked = true;
										listView1.Items[i].Selected = true;
										if (checkAutoSelect.Checked)
										{
											listView1.Items[i].Checked = true;
										}
										unit.updateListViewItem(toolStripStatusLabel1);
										break;
									}
								}
							}
						}


					}
					#endregion

					#region popUnit
					if (e.Button == MouseButtons.Right)
					{
						listView1.SelectedItems.Clear();

						popedWayPoint = null;
						foreach (ListViewItem item in listView3.Items)
						{
							item.Selected = false;
							Region r = (Region)RegionsList[item];
							if (r != null)
							{
								r.isSub = false;
							}
						}
						foreach (ListViewItem item in listView4.Items)
						{
							item.Selected = false;
						}

						bool isChecked = false;
						//select way point
						if (isChecked == false && checkShowPoint.Checked && tabControl1.SelectedTab == tabPageWP)
						{
							foreach (ListViewItem item in listView2.Items)
							{
								WayPoint p = (WayPoint)WayPointsList[item];
								if (p != null)
								{
									if (p.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										isChecked = true;
										popedWayPoint = item;
										break;
									}
								}
							}
						}
						// select regions
						if (isChecked == false && checkShowRegion.Checked && tabControl1.SelectedTab == tabPageRegion)
						{
							foreach (ListViewItem item in listView3.Items)
							{
								Region r = (Region)RegionsList[item];
								if (r != null)
								{
									if (r.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										isChecked = true;
										item.Selected = true;
										break;
									}
								}
							}
						}
						//select events
						if (isChecked == false && checkShowEvent.Checked && tabControl1.SelectedTab == tabPageEvent)
						{
							foreach (ListViewItem item in listView4.Items)
							{
								Event ee = (Event)EventList[item];
								if (ee != null)
								{
									if (ee.touchBounds(e.X, e.Y , ref  selectedOffset))
									{
										isChecked = true;
										item.Selected = true;
										break;
									}
								}
							}
						}
						//select unit
						if (isChecked == false && tabControl1.SelectedTab == tabPageUnit &&
							(checkShowMap.Checked || checkShowSprite.Checked || checkShowTile.Checked))
						{
							for (int i = listView1.Items.Count - 1; i >= 0; i--)
							{
								Unit unit = ((Unit)UnitList[listView1.Items[i]]);
								if (unit.map() != null && checkShowMap.Checked ||
									unit.spr() != null && checkShowSprite.Checked ||
									unit.images() != null && checkShowTile.Checked)
								{
									if (unit.touchBounds(e.X, e.Y, ref selectedOffset))
									{
										isChecked = true;
										listView1.Items[i].Selected = true;
										break;
									}
								}
							}
						}


						//弹出菜单
						if (listView1.SelectedItems.Count > 0 && checkShowSprite.Checked && tabControl1.SelectedTab == tabPageUnit)
						{
							menuUnit.Opacity = 0.8;
							menuUnit.Show(pictureBox1, e.Location);
						}
						else if (listView2.SelectedItems.Count > 0 &&
							popedWayPoint != null &&
							popedWayPoint != listView2.SelectedItems[0] &&
							checkShowPoint.Checked && tabControl1.SelectedTab == tabPageWP)
						{
							menuPath.Opacity = 0.8;
							menuPath.Items[0].Visible = true;
							menuPath.Items[1].Visible = true;
							menuPath.Items[2].Visible = true;
							menuPath.Items[3].Visible = false;
							menuPath.Items[4].Visible = false;
							menuPath.Show(pictureBox1, e.Location);
						}
						else if (listView4.SelectedItems.Count > 0 && checkShowEvent.Checked && tabControl1.SelectedTab == tabPageEvent)
						{
							menuEvent.Opacity = 0.8;
							menuEvent.Show(pictureBox1, e.Location);
						}
						else if (popedWayPoint != null &&
							listView2.SelectedItems.Count <= 0 ||
							listView2.SelectedItems.Count > 0 &&
							popedWayPoint == listView2.SelectedItems[0])
						{
							popedWayPoint.Selected = true;
							menuPath.Opacity = 0.8;
							menuPath.Items[0].Visible = false;
							menuPath.Items[1].Visible = false;
							menuPath.Items[2].Visible = false;
							menuPath.Items[3].Visible = true;
							menuPath.Items[4].Visible = true;
							menuPath.Show(pictureBox1, e.Location);
						}
						else if (listView3.SelectedItems.Count > 0 && checkShowRegion.Checked && tabControl1.SelectedTab == tabPageRegion)
						{
							menuRegion.Opacity = 0.8;
							menuRegion.Show(pictureBox1, e.Location);
						}
						else
						{
							menuWorld.Opacity = 0.8;
							menuWorld.Show(pictureBox1, e.Location);
						}
					}




					#endregion

				}
			}
			catch (Exception err) { }

			pictureBox1.Refresh();

		}
        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            
            RuleX = e.X;
            RuleY = e.Y;

            // 画地形
            if (btnSceneTerrain.Checked)
            {
                if (e.Button == MouseButtons.Left)
                {
                    fillTerrain(e.X, e.Y, btnSceneTerrainColor.BackColor.ToArgb());
                }
                else if (e.Button == MouseButtons.Right)
                {
                    fillTerrain(e.X, e.Y, 0);
                }

                pictureBox1.Refresh();

            }
            else if (e.Button == MouseButtons.Left) // 
            {
                Unit unit = getSelectedUnit();
                WayPoint p = getSelectedWayPoint();
                Region r = getSelectedRegion();
                Event ee = getSelectedEvent();

				
				if (unit != null)
				{
					#region unit
					if (unit.listItem.Checked == true)
                    {
						if (!checkStickyToCell.Checked)
						{
							unit.setPos(e.X - selectedOffset.X, e.Y - selectedOffset.Y);
						}
						else
						{
							unit.setPos(e.X - e.X % CellW, e.Y - e.Y % CellH);
						}

						unit.updateListViewItem(toolStripStatusLabel1);

						if (!chkLockSize.Checked)
						{
							Rectangle wb = unit.getWorldBounds();
							pictureBox1.Width = Math.Max(wb.X + wb.Width, pictureBox1.Width);
							pictureBox1.Height = Math.Max(wb.Y + wb.Height, pictureBox1.Height);
							numericUpDown1.Value = CellW;
							numericUpDown2.Value = CellH;
							numericUpDown3.Value = pictureBox1.Width;
							numericUpDown4.Value = pictureBox1.Height;
						}
					}
					#endregion
				}
				else  if (ee != null)
				{
					#region event
                    if (!checkStickyToCell.Checked)
                    {
						ee.setPos(e.X - selectedOffset.X, e.Y - selectedOffset.Y);
                    }
                    else
                    {
						ee.setPos(e.X - e.X % CellW, e.Y - e.Y % CellH);
                    }
					ee.updateListViewItem(toolStripStatusLabel1);
					#endregion
                }
                else if (p != null)
				{
					#region waypoint
                    
                        if (!checkStickyToCell.Checked)
                        {
							p.setPos(e.X - selectedOffset.X, e.Y - selectedOffset.Y);
                        }
                        else
						{
							p.setPos(
								e.X - e.X % CellW,
								e.Y - e.Y % CellH);
                        }
						p.updateListViewItem(toolStripStatusLabel1);

						if (!chkLockSize.Checked)
						{
							pictureBox1.Width = Math.Max(p.getX(), pictureBox1.Width);
							pictureBox1.Height = Math.Max(p.getY(), pictureBox1.Height);
							numericUpDown1.Value = CellW;
							numericUpDown2.Value = CellH;
							numericUpDown3.Value = pictureBox1.Width;
							numericUpDown4.Value = pictureBox1.Height;
						}
					#endregion
				}
				else if (r != null)
				{
					#region region
					if (!r.isSub)
                    {
                        if (!checkStickyToCell.Checked)
                        {
							r.setPos(e.X - selectedOffset.X, e.Y - selectedOffset.Y);
                        }
                        else
                        {
							r.setPos(
								e.X - e.X % CellW,
								e.Y - e.Y % CellH);
                        }
                    }
                    else
                    {
                        if (!checkStickyToCell.Checked)
                        {
							r.setSizeDst(e.X, e.Y);
                        }
                        else
                        {
							r.setSizeDst(e.X - e.X % CellW, e.Y - e.Y % CellH);
                        }
                        
                    }
					r.updateListViewItem(toolStripStatusLabel1);
					if (!chkLockSize.Checked)
					{
						Rectangle wb = r.getWorldBounds();
						pictureBox1.Width = Math.Max(wb.X + wb.Width, pictureBox1.Width);
						pictureBox1.Height = Math.Max(wb.Y + wb.Height, pictureBox1.Height);
						numericUpDown1.Value = CellW;
						numericUpDown2.Value = CellH;
						numericUpDown3.Value = pictureBox1.Width;
						numericUpDown4.Value = pictureBox1.Height;
					}
					#endregion
                }


                pictureBox1.Refresh();
            }

           
        }

        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {
            listView1.Sort();

			Unit unit = getSelectedUnit();
			WayPoint p = getSelectedWayPoint();
			Region r = getSelectedRegion();
			Event ee = getSelectedEvent();

			if (unit != null)
			{
				unit.updateListViewItem(toolStripStatusLabel1);
			}
			else if (ee != null)
			{
				ee.updateListViewItem(toolStripStatusLabel1);
			}
			else if (p != null)
			{
				p.updateListViewItem(toolStripStatusLabel1);

			}
			else if (r != null)
			{
				r.updateListViewItem(toolStripStatusLabel1);
			}	
			pictureBox1.Refresh();
        }

        private void pictureBox1_MouseWheel(object sender, MouseEventArgs e)
        {
//             Unit unit = getSelectedUnit();
//             if (unit != null) {
//                 unit.nextAnim(e.Delta);
// 				unit.updateListViewItem(toolStripStatusLabel1);
//             }
//             pictureBox1.Refresh();
        }

		private void pictureBox1_DragDrop(object sender, DragEventArgs e)
		{
		}
		private void pictureBox1_DragEnter(object sender, DragEventArgs e)
		{
		}

		private void panel1_DragDrop(object sender, DragEventArgs e)
		{
			Point loc = pictureBox1.PointToClient(new Point(e.X, e.Y));
			if ((MapForm)e.Data.GetData(typeof(MapForm)) != null)
			{
				MapForm map = ((MapForm)e.Data.GetData(typeof(MapForm)));
				addUnitAsMap(map, 0, 0);
			}
			if ((SpriteForm)e.Data.GetData(typeof(SpriteForm)) != null)
			{
				SpriteForm spr = ((SpriteForm)e.Data.GetData(typeof(SpriteForm)));
				addUnitAsSpr(spr,
					loc.X,
					loc.Y);
			}
			if ((ImagesForm)e.Data.GetData(typeof(ImagesForm)) != null)
			{
				ImagesForm img = ((ImagesForm)e.Data.GetData(typeof(ImagesForm)));
				addUnitAsImage(img,
					loc.X,
					loc.Y,
					img.getAvaliableImageIndex());
			}
			listView1.Refresh();
			pictureBox1.Refresh();
		}

		private void panel1_DragEnter(object sender, DragEventArgs e)
		{
			e.Effect = e.AllowedEffect;
		}

		private void textBox1_MouseWheel(object sender, MouseEventArgs e)
		{
			Unit unit = getSelectedUnit();
			if (unit != null)
			{
				unit.nextAnim(Util.getDirect(e.Delta));
				unit.updateListViewItem(toolStripStatusLabel1);
			}
			pictureBox1.Refresh();
		}

        // key adjust
        private void textBox1_KeyDown(object sender, KeyEventArgs e)
        {
            int eX = 0;
            int eY = 0;
            textBox1.Text = "";
            switch(e.KeyCode)
            {
                    // 微调单位位置
                case Keys.Up: eY = -1; textBox1.Text += "UP"; break;
                case Keys.Down: eY = 1; textBox1.Text += "DOWN"; break;
                case Keys.Left: eX = -1; textBox1.Text += "LEFT"; break;
                case Keys.Right: eX = 1; textBox1.Text += "RIGHT"; break;

                    // 调整填充地形大小
                case Keys.PageUp:
                    fixTerrainBushSize(1);
                    break;
                case Keys.PageDown:
                    fixTerrainBushSize(-1);
                    break;
            }

            try
            {
                #region unit
                if (listView1.SelectedItems.Count>0)
                {
                    if (listView1.SelectedItems[0].Checked == true)
                    {
                        Unit unit = ((Unit)UnitList[listView1.SelectedItems[0]]);

                        if (!checkStickyToCell.Checked)
                        {
//                             unit.x += eX;
//                             unit.y += eY;
							unit.movePos(eX, eY);
                        }
                        else
                        {
//                             unit.x += eX * CellW;
//                             unit.y += eY * CellH;
							unit.movePos(eX * CellW, eY * CellH);
                        }
						unit.updateListViewItem(toolStripStatusLabel1);
//                         if (unit.x < 0) unit.x = 0;
//                         if (unit.y < 0) unit.y = 0;
//                         listView1.SelectedItems[0].SubItems[2].Text = unit.x.ToString();
//                         listView1.SelectedItems[0].SubItems[3].Text = unit.y.ToString();
                    
                    }

                }else
                #endregion

					#region events
					if (listView4.SelectedItems.Count > 0)
					{
						Event ee = getSelectedEvent();

						if (!checkStickyToCell.Checked)
						{
// 							ee.point.X += eX;
// 							ee.point.Y += eY; 
							ee.movePos(eX, eY);
						}
						else
						{
							ee.movePos(eX * CellW, eY * CellH);
// 							ee.point.X += ;
// 							ee.point.Y += ;
						}
// 						if (ee.point.X < 0) ee.point.X = 0;
// 						if (ee.point.Y < 0) ee.point.Y = 0;
						ee.updateListViewItem(toolStripStatusLabel1);
					}
					else
					#endregion

                #region waypoint
                if (listView2.SelectedItems.Count>0)
                {
                    WayPoint p = (WayPoint)WayPointsList[listView2.SelectedItems[0]];

                    if (p != null )
                    {

                        if (!checkStickyToCell.Checked)
                        {
//                             p.point.X += eX;
//                             p.point.Y += eY;
// 
//                             if (p.point.X < 0) p.point.X = 0;
//                             if (p.point.Y < 0) p.point.Y = 0;
							p.movePos(eX, eY);
						}
						else
						{
							p.movePos(eX * CellW, eY * CellH);
//                             p.point.X += eX * CellW;
//                             p.point.Y += eY * CellH;
// 
//                             if (p.point.X < 0) p.point.X = 0;
//                             if (p.point.Y < 0) p.point.Y = 0;
                        }

//                         listView2.SelectedItems[0].SubItems[0].Text = p.getWX().ToString();
//                         listView2.SelectedItems[0].SubItems[1].Text = p.getWY().ToString();
						p.updateListViewItem(toolStripStatusLabel1);
                    }

                }else
                #endregion

                #region region
                if (listView3.SelectedItems.Count>0)
                {
                    Region r = (Region)RegionsList[listView3.SelectedItems[0]];
                    if (r != null && !r.isSub)
                    {
                        if (!checkStickyToCell.Checked)
                        {
//                             r.rect.X += eX;
//                             r.rect.Y += eY ;
// 
//                             if (r.rect.X < 0) r.rect.X = 0;
//                             if (r.rect.Y < 0) r.rect.Y = 0;
							r.movePos(eX, eY);
						}
						else
						{
							r.movePos(eX * CellW, eY * CellH);
//                             r.rect.X = eX * CellW;
//                             r.rect.Y = eY * CellH;
// 
//                             if (r.rect.X < 0) r.rect.X = 0;
//                             if (r.rect.Y < 0) r.rect.Y = 0;
                        }
                    }

                    if (r != null && r.isSub )
                    {
                        if (!checkStickyToCell.Checked)
                        {
//                             if (r.rect.Width + eX > 0) r.rect.Width += eX;
//                             else r.rect.Width = 1;
//                             if (r.rect.Height + eY > 0) r.rect.Height += eY;
//                             else r.rect.Height = 1;
							r.moveSizeDst(eX, eY);
							//r.setSizeDst(r.)
                        }
                        else
                        {
//                             if (r.rect.Width + eX*CellW > 0) r.rect.Width += eX*CellW;
//                             else r.rect.Width = 1;
//                             if (r.rect.Height + eY*CellH > 0) r.rect.Height += eY*CellH;
//                             else r.rect.Height = 1;
							r.moveSizeDst(eX * CellW, eY * CellH);
                        }
                        
                    }

//                     listView3.SelectedItems[0].SubItems[0].Text = r.rect.X.ToString();
//                     listView3.SelectedItems[0].SubItems[1].Text = r.rect.Y.ToString();
//                     listView3.SelectedItems[0].SubItems[2].Text = r.rect.Width.ToString();
//                     listView3.SelectedItems[0].SubItems[3].Text = r.rect.Height.ToString();
					r.updateListViewItem(toolStripStatusLabel1);

                }
                #endregion

                pictureBox1.Refresh();

            }catch(Exception err){}
        }
        //show select
        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        //show cd
        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

        //test del disposed
        private void testDelUnit()
        {
            foreach (ListViewItem item in listView1.Items)
            {
                try
                {
                    Unit unit = ((Unit)UnitList[item]);
                    if (unit.isKilled())
                    {
                        listView1.Items.Remove(item);
                        UnitList.Remove(item);

                        pictureBox1.Refresh();
                    }
                }
                catch (Exception err)
                {
                }
            }
        }
        //del
        private void toolStripButton6_Click(object sender, EventArgs e)
        {
           // if (MessageBox.Show(this, "是否删除所有单位", "确认", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                foreach (ListViewItem item in listView1.SelectedItems)
                {
                    if (listView1.Items.Contains(item))
                    {
                        listView1.Items.Remove(item);
                        UnitList.Remove(item);

                        pictureBox1.Refresh();
                        break;
                    }
                }
            }
        }
        //up-back
        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            if (listView1.SelectedItems.Count > 0)
            {
               
                    Unit unit = ((Unit)UnitList[listView1.SelectedItems[0]]);
                    unit.Priority++;
                    listView1.SelectedItems[0].SubItems[4].Text = unit.Priority.ToString();
                    listView1.Sort();
                    pictureBox1.Refresh();
                
            }
        }
        //down-upon
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            if (listView1.SelectedItems.Count > 0)
            {
                Unit unit = ((Unit)UnitList[listView1.SelectedItems[0]]);
                unit.Priority--;
                listView1.SelectedItems[0].SubItems[4].Text = unit.Priority.ToString();

                listView1.Sort();
                pictureBox1.Refresh();
            }
        }

        private void toolStripButton5_Click(object sender, EventArgs e)
        {

        }


//      -----------------------------------------------------------------------------------------

        /// <summary>
        /// 获得摄像机区域
        /// </summary>
        /// <returns></returns>
        public Rectangle getCameraBounds()
        {
            return new System.Drawing.Rectangle(
                                        -pictureBox1.Location.X,
                                        -pictureBox1.Location.Y,
                                        panel1.Width,
                                        panel1.Height
                                   );
        }

        /// <summary>
        /// 获得包含在可视区域内的地块区域
        /// </summary>
        /// <returns></returns>
        public Rectangle getCameraBoundsTerrian()
        {
            System.Drawing.Rectangle camera = getCameraBounds();

            Size tsize = getTerrainSize();

            int sx = Math.Min(Math.Max(0, camera.X / CellW), tsize.Width - 1);
            int sy = Math.Min(Math.Max(0, camera.Y / CellW), tsize.Height - 1);
            int sw = camera.Width / CellW + 1;
            int sh = camera.Height / CellH + 1;
            int dx = Math.Min(sx + sw, tsize.Width - 1);
            int dy = Math.Min(sy + sh, tsize.Height - 1);

            return new Rectangle(sx, sy, dx - sx + 1, dy - sy + 1);
        }

        /// <summary>
        /// 得到场景大小
        /// </summary>
        /// <returns></returns>
        public Size getWorldSize()
        {
            return new Size(pictureBox1.Width, pictureBox1.Height);
        }

        /// <summary>
        /// 得到地块长宽数量 
        /// </summary>
        /// <returns></returns>
        public Size getTerrainSize()
        {
            int xdiv = pictureBox1.Width % CellW == 0 ? 0 : 1;
            int ydiv = pictureBox1.Height % CellH == 0 ? 0 : 1;

            int xcount = pictureBox1.Width / CellW + xdiv;
            int ycount = pictureBox1.Height / CellH + ydiv;

            xcount = Math.Max(xcount, 1);
            ycount = Math.Max(ycount, 1);

            return new Size(xcount, ycount);
        }

//      -----------------------------------------------------------------------------------------
 
        public long createEventID()
        {
            return EventIDIndex++;
        }

        public List<WorldEvent> getUpdateEvents()
        {
            List<WorldEvent> Events = new List<WorldEvent>();
            for (int i = 0; i < listView4.Items.Count; i++)
            {
                Event evt = (Event)EventList[listView4.Items[i]];
                WorldEvent we = evt.asWorldEvent();
                we.mapId = id;
                Events.Add(we);
            }
            return Events;
        }

//      -----------------------------------------------------------------------------------------
 
#region menuWorld
        // add way point command
        private void addWayPoint_Click(object sender, EventArgs e)
        {
            // menuPath.SourceControl
            WayPoint p = new WayPoint(last_mouse_down_x, last_mouse_down_y, listView2);
            //WayPoints.Add(p);
            ListViewItem item = p.listItem;
            listView2.Items.Add(item);
            WayPointsList.Add(item, p);
            //Console.WriteLine("Level: Load " + unit.id);

            pictureBox1.Refresh();
        }
        // add region command
        private void addRegion_Click(object sender, EventArgs e)
        {
            // menuPath.SourceControl
            Region region = new Region(last_mouse_down_x, last_mouse_down_y, listView3);
            //Regions.Add(p);
            ListViewItem item = region.listItem;
            listView3.Items.Add(item);
            RegionsList.Add(item, region);

            pictureBox1.Refresh();
        }
        private void addEvent_Click(object sender, EventArgs e)
        {
			EventTemplatePlugin ef = ProjectForm.getInstance().getEventTemplateForm();
			if (ef != null) 
			{
				EventNode et = ef.getSelectedEvent();
				if (et != null)
				{
                    try
                    {
                        Event evt = new Event(ef, et, 
                            last_mouse_down_x, 
                            last_mouse_down_y, createEventID());
                        listView4.Items.Add(evt.listItem);
                        EventList.Add(evt.listItem, evt);
                    }
                    catch (Exception err) {
                        MessageBox.Show(err.Message);
                    }
                    pictureBox1.Refresh();
				}
			}
        }


        // open world data editor
        private void menuItemSceneData_Click(object sender, EventArgs e)
        {
            DataEdit dataEdit = new DataEdit(this.Data);
            //dataEdit.MdiParent = this.MdiParent;
            dataEdit.Text = "DataEdit(" + this.id + ")";
            dataEdit.Show();
        }

        private void menuItemSceneProperties_Click(object sender, EventArgs e)
        {
            PropertyEdit propertyEdit = new PropertyEdit(
                new WorldPorperty(
                    pictureBox1.Width,
                    pictureBox1.Height,
                    CellW,
                    CellH,
                    Data)
                );
            //propertyEdit.MdiParent = this.MdiParent;
            propertyEdit.Text = "属性(" +this.id + ")";
            propertyEdit.Show();
        }
#endregion

#region menuUnit
        private void menuItemDeleteUnit_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (ListViewItem item in listView1.SelectedItems)
                {
                    if (listView1.Items.Contains(item))
                    {
                        listView1.Items.Remove(item);
                        UnitList.Remove(item);

                        pictureBox1.Refresh();
                        break;
                    }
                }
            }
            catch (Exception err) { }
        }
        private void menuItemUnitData_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView1.SelectedItems[0] == null) return;
				Unit unit = ((Unit)UnitList[listView1.SelectedItems[0]]);
				DataEdit dataEdit = new DataEdit(unit.Data);
                //dataEdit.MdiParent = this.MdiParent;
				dataEdit.Text = "DataEdit(" + this.id + ".Unit[" + unit.id + "])";
                dataEdit.ShowDialog();
				unit.updateListViewItem(toolStripStatusLabel1);
			}
            catch (Exception err) { }
        }
        private void menuItemUnitProperties_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView1.SelectedItems[0] == null) return;
				Unit unit = ((Unit)UnitList[listView1.SelectedItems[0]]);
				PropertyEdit propertyEdit = new PropertyEdit(unit);
                //propertyEdit.MdiParent = this.MdiParent;
				propertyEdit.Text = "属性(" + unit.id + ")";
                propertyEdit.Show();
				unit.updateListViewItem(toolStripStatusLabel1);
            }
            catch (Exception err) { }
        }

#endregion

	

#region menuPath
        // del way point
        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (ListViewItem item in listView2.SelectedItems)
                {
                    WayPoint p = (WayPoint)WayPointsList[item];
                    foreach (ListViewItem link in listView2.Items)
                    {
                        WayPoint l = (WayPoint)WayPointsList[link];
                        l.DisLink(p);
                        p.DisLink(l);
                    }
                    ((WayPoint)WayPointsList[item]).DisLinkAll();
                    listView2.Items.Remove(item);
                    WayPointsList.Remove(item);
                }
            }
            catch (Exception err) { }
            pictureBox1.Refresh();
        }
        // link way point
        private void toolStripMenuItem3_Click(object sender, EventArgs e)
        {
            if (listView2.SelectedItems[0] == null) return;
            if (popedWayPoint == null) return;

            ((WayPoint)WayPointsList[listView2.SelectedItems[0]]).Link(((WayPoint)WayPointsList[popedWayPoint]));

            pictureBox1.Refresh();
        }
        private void menuItemWayPointLinkBoth_Click(object sender, EventArgs e)
        {
            if (listView2.SelectedItems[0] == null) return;
            if (popedWayPoint == null) return;

            ((WayPoint)WayPointsList[listView2.SelectedItems[0]]).Link2(((WayPoint)WayPointsList[popedWayPoint]));

            pictureBox1.Refresh();
        }
        // dislink way point
        private void toolStripMenuItem4_Click(object sender, EventArgs e)
        {
            if (listView2.SelectedItems[0] == null) return;
            if (popedWayPoint == null) return;

            ((WayPoint)WayPointsList[listView2.SelectedItems[0]]).DisLink(((WayPoint)WayPointsList[popedWayPoint]));

            pictureBox1.Refresh();
        }

        private void menuItemWayPointData_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView2.SelectedItems[0] == null) return;

                DataEdit dataEdit = new DataEdit(((WayPoint)WayPointsList[listView2.SelectedItems[0]]).Data);
                //dataEdit.MdiParent = this.MdiParent;
                dataEdit.Text = "DataEdit(" + this.id + ".WayPoint[" + listView2.Items.IndexOf(listView2.SelectedItems[0]) + "])";
                dataEdit.ShowDialog();
                listView2.SelectedItems[0].SubItems[2].Text = ((WayPoint)WayPointsList[listView2.SelectedItems[0]]).Data.ToString();
            }
            catch (Exception err) { }
        }

        private void toolStripMenuItem6_Click(object sender, EventArgs e)
        {
            if (listView2.SelectedItems[0] == null) return;

            PropertyEdit propertyEdit = new PropertyEdit(((WayPoint)WayPointsList[listView2.SelectedItems[0]]));
            //propertyEdit.MdiParent = this.MdiParent;
            propertyEdit.Text = "属性(" + this.id + ".WayPoint[" + listView2.Items.IndexOf(listView2.SelectedItems[0]) + "])";
            propertyEdit.Show();
        }
#endregion

#region menuRegion
        //del
        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (ListViewItem item in listView3.SelectedItems)
                {
                    if (listView3.Items.Contains(item))
                    {
                        listView3.Items.Remove(item);
                        RegionsList.Remove(item);

                        pictureBox1.Refresh();
                        break;
                    }
                }
            }
            catch (Exception err) { }
        }
        //data
        private void toolStripMenuItem5_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView3.SelectedItems[0] == null) return;

                DataEdit dataEdit = new DataEdit(((Region)RegionsList[listView3.SelectedItems[0]]).Data);
                //dataEdit.MdiParent = this.MdiParent;
                dataEdit.Text = "DataEdit(" + this.id + ".Region[" + listView3.Items.IndexOf(listView3.SelectedItems[0]) + "])";
                dataEdit.ShowDialog();
                listView3.SelectedItems[0].SubItems[4].Text = ((Region)RegionsList[listView3.SelectedItems[0]]).Data.ToString();
            }
            catch (Exception err) { }
        }
        private void menuItemWayPointProperties_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView3.SelectedItems[0] == null) return;

                PropertyEdit propertyEdit = new PropertyEdit(((Region)RegionsList[listView3.SelectedItems[0]]));
                //propertyEdit.MdiParent = this.MdiParent;
                propertyEdit.Text = "属性(" + this.id + ".Region[" + listView3.Items.IndexOf(listView3.SelectedItems[0]) + "])";
                propertyEdit.Show();
            }
            catch (Exception err) { }
        }
#endregion


        #region menuEvent
        private void menuItemEventData_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView4.SelectedItems[0] == null) return;
                Event evt =  ((Event)EventList[listView4.SelectedItems[0]]);
                DataEdit dataEdit = new DataEdit(
                   evt.Data);
                //dataEdit.MdiParent = this.MdiParent;
                dataEdit.Text = "DataEdit(" + this.id + ".Event[" +
                    listView4.Items.IndexOf(evt.listItem) +
                    "])";
                dataEdit.ShowDialog();
                listView4.SelectedItems[0].SubItems[4].Text =
                   evt.Data.ToString();
            }
            catch (Exception err) { }
        }

        private void menuItemEventProperties_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView4.SelectedItems[0] == null) return;
                PropertyEdit propertyEdit = new PropertyEdit(
                    ((Event)EventList[listView4.SelectedItems[0]]));
                propertyEdit.Text = "属性(" + this.id + ".Event[" + 
                    listView4.Items.IndexOf(listView4.SelectedItems[0]) + 
                    "])";
                propertyEdit.Show();
            }
            catch (Exception err) { }
        }

        private void menuItemDeleteEvent_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (ListViewItem item in listView4.SelectedItems)
                {
                    if (listView4.Items.Contains(item))
                    {
                        listView4.Items.Remove(item);
                        EventList.Remove(item);
                        pictureBox1.Refresh();
                        break;
                    }
                }
            }
            catch (Exception err) { }
        }
        #endregion

		public Unit getSelectedUnit()
		{
			if (listView1.SelectedItems.Count > 0)
			{
				return ((Unit)UnitList[listView1.SelectedItems[0]]);
			}
			return null;
		}

		public WayPoint getSelectedWayPoint()
		{
			if (listView2.SelectedItems.Count > 0)
			{
				return (WayPoint)WayPointsList[listView2.SelectedItems[0]];
			}
			return null;
		}

		public Region getSelectedRegion()
		{
			if (listView3.SelectedItems.Count > 0)
			{
				return ((Region)RegionsList[listView3.SelectedItems[0]]);
			}
			return null;
		}

        public Event getSelectedEvent()
        {
            if (listView4.SelectedItems.Count > 0)
            {
                return ((Event)EventList[listView4.SelectedItems[0]]);
            }
            return null;
        }

        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        private void toolStripButton8_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        private void toolStripButton9_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        private void toolStripButton17_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        private void checkShowTile_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

        // back color
        private void toolStripButton5_Click_1(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = false;
            MyDialog.ShowHelp = true;
            MyDialog.Color = pictureBox1.BackColor;
            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                pictureBox1.BackColor = MyDialog.Color;
            }
        }
        private void toolEvent_Click(object sender, EventArgs e)
        {

        }
        private void toolEvent_CheckedChanged(object sender, EventArgs e)
        {
			pictureBox1.Refresh();
		}
	
		private void toolAddEvent_Click_1(object sender, EventArgs e)
		{
			EventTemplatePlugin ef = ProjectForm.getInstance().getEventTemplateForm();
			if (ef != null) 
			{
				if (ef.asForm().Visible)
				{
					ef.asForm().Hide();
				}
				else
				{
                    ef.asForm().TopMost = true;
					ef.asForm().Show();
				}
			}
			
		}

        private void btnUpdateEvents_Click(object sender, EventArgs e)
        {
            EventTemplatePlugin ef = ProjectForm.getInstance().getEventTemplateForm();
            if (ef != null)
            {
                try
                {
                    ef.saveWorldEvents(getUpdateEvents());
                }
                catch (Exception err)
                {
                    MessageBox.Show(err.Message);
                }
            }
        }

		private void toolAddUnit_Click_2(object sender, EventArgs e)
		{
// 			WorldAddUnitForm addunit = ProjectForm.getInstance().getWorldAddUnitForm();
// 			if (addunit.Visible)
// 			{
// 				addunit.Hide();
// 			}
// 			else
// 			{
// 				addunit.Show();
// 			}
		}

		/*
        private void listView1_BeforeLabelEdit(object sender, LabelEditEventArgs e)
        {
            try
            {

                if (!e.CancelEdit)
                {
                    Unit unit = ((Unit)UnitList[listView1.Items[e.Item]]);
                    unit.id = e.Label;

                    //listView1.Items[e.Item].SubItems[1].
                }

            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " Unit Lable Error : " + err.StackTrace);
            }
        }

        private void listView1_AfterLabelEdit(object sender, LabelEditEventArgs e)
        {
            try
            {

                if (!e.CancelEdit)
                {
                    Unit unit = ((Unit)UnitList[listView1.Items[e.Item]]);
                    unit.id = e.Label;

                    //listView1.Items[e.Item].SubItems[1].
                }

            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " Unit Lable Error : " + err.StackTrace);
            }
        }
		*/
        private void btnSceneRule_CheckedChanged(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

        // change sprite id
        private void toolStripDropDownButton1_DropDownOpening(object sender, EventArgs e)
        {
            try
            {
                dropChangeAnimID.DropDownItems.Clear();

                foreach (ListViewItem item in listView1.SelectedItems)
                {
                    Unit unit = ((Unit)UnitList[item]);

                    if (unit.type == Unit.TYPE_SPRITE)
                    {
                        for (int i = 0; i < unit.spr().GetAnimateCount(); i++)
                        {
                            System.Drawing.Image icon = new System.Drawing.Bitmap(64, 64);
                            System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(icon);
                            unit.spr().Render(
                                new javax.microedition.lcdui.Graphics(g),
                                32, 32, false, i, 0);

                            ToolStripMenuItem pop = new ToolStripMenuItem();
                            //pop.Text = "Anim " + i.ToString();
                            pop.Text = unit.spr().getAnimateName(i);
                            pop.Image = icon;
                            pop.ImageScaling = ToolStripItemImageScaling.None;

                            dropChangeAnimID.DropDownItems.Add(pop);
                        }
                        break;
                    }
                }
            }
            catch (Exception err) { }

        }

        private void toolStripDropDownButton1_DropDownItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            try
            {
                int index = dropChangeAnimID.DropDownItems.IndexOf(e.ClickedItem);
                foreach (ListViewItem item in listView1.SelectedItems)
                {
                    Unit unit = ((Unit)UnitList[item]);

                    if (unit.type == Unit.TYPE_SPRITE)
                    {
                        item.SubItems[1].Text = index.ToString();
                        unit.animID = index;
                        break;
                    }
                }
                pictureBox1.Refresh();
            }
            catch (Exception err) { }
        }


        // center map block
        private void toolStripButton11_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        // square show
        private void toolStripButton10_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        //show block line
        private void toolStripButton12_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }
        private void panel1_Scroll(object sender, ScrollEventArgs e)
        {
            //MapRegion.Refresh();
        }

        //minimap
        private void toolStripButton13_Click(object sender, EventArgs e)
        {
            System.Drawing.Image image = new System.Drawing.Bitmap(pictureBox1.Width, pictureBox1.Height);
            System.Drawing.Graphics dg = System.Drawing.Graphics.FromImage(image);
            dg.Clear(pictureBox1.BackColor);
            javax.microedition.lcdui.Graphics g = new javax.microedition.lcdui.Graphics(dg);

            // draw units

            // draw units
            if (checkShowMap.Checked || checkShowSprite.Checked)
            {
                foreach (ListViewItem item in listView1.Items)
                {
                    try
                    {
                        Unit unit = ((Unit)UnitList[item]);

                        if (unit.type == "Map" && checkShowMap.Checked)
                        {
                            if (toolStripButton10.Checked)
                            {
                                for (int x = 0; x < pictureBox1.Width; x += unit.map().getWidth())
                                {
                                    for (int y = 0; y < pictureBox1.Height; y += unit.map().getHeight())
                                    {
                                        Rectangle rect = new Rectangle(
                                            0,
                                            0,
                                            image.Width,
                                            image.Height
                                            );
                                        if (rect.IntersectsWith(
                                             new System.Drawing.Rectangle(
                                                x, y,
                                                unit.map().getWidth(),
                                                unit.map().getHeight())
                                                )
                                            )
                                        {
                                        }
                                        {
                                           
                                            unit.render(
                                               g,
                                               new System.Drawing.Rectangle(
                                                    0,
                                                    0,
                                                    image.Width,
                                                    image.Height
											   ), x, y,
                                               listView1.SelectedItems.Contains(item),
                                               checkShowUnitBounds.Checked,
                                               toolStripButton2.Checked,
                                                toolShowLock.Checked
                                            );
                                        }
                                    }
                                }
                            }
                            else
                            {
                                unit.render(
                                   g,
                                   new System.Drawing.Rectangle(
                                        0,
                                            0,
                                            image.Width,
                                            image.Height
                                   ),
                                   listView1.SelectedItems.Contains(item),
                                   checkShowUnitBounds.Checked,
                                   toolStripButton2.Checked,
                                   toolShowLock.Checked
                                );
                            }
                        }

                        if (unit.type == "Sprite" && checkShowSprite.Checked)
                        {
                            unit.render(
                                   g,
                                    new System.Drawing.Rectangle(
                                         0,
                                            0,
                                            image.Width,
                                            image.Height
                                   ),
                                   listView1.SelectedItems.Contains(item),
                                   checkShowUnitBounds.Checked,
                                   toolStripButton2.Checked,
                                   toolShowLock.Checked
                                );
                        }

                    }
                    catch (Exception err)
                    {
                    }
                }
            }
            // draw way points
            if (checkShowPoint.Checked)
            {
                foreach (ListViewItem item in listView2.Items)
                {
                    WayPoint p = (WayPoint)WayPointsList[item];
                    if (p != null)
                    {
                        p.Render(g,item.Selected || item==popedWayPoint);
                       

                        //g.drawString(WayPoints.IndexOf(p).ToString(), p.rect.X + p.rect.Width + 1, p.rect.Y - p.rect.Height / 2, 0);
                    }
                }

            }


            MapMini mini = new MapMini(image);
            mini.Show();
        }

        // main update
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (!this.Visible) return;

            if (toolStripButton16.Checked)
            {
                pictureBox1.Refresh();
            }
        }

        // fix cell and world size
        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            CellW = (int)numericUpDown1.Value;
            resetTerrainSize();
            pictureBox1.Refresh();
        }
        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            CellH = (int)numericUpDown2.Value;
            resetTerrainSize();
            pictureBox1.Refresh();
        }
        private void numericUpDown3_ValueChanged(object sender, EventArgs e)
        {
            pictureBox1.Width = (int)numericUpDown3.Value;
            resetTerrainSize();
            pictureBox1.Refresh();
        }
        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            pictureBox1.Height = (int)numericUpDown4.Value;
            resetTerrainSize();
            pictureBox1.Refresh();
        }

        // tab changed
        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //tabControl1.SelectedTab.Focus();
            pictureBox1.Refresh();
        }

        private void toolStripButton16_Click(object sender, EventArgs e)
        {
            if (toolStripButton16.Checked)
            {
                timer1.Start();
            }
            else
            {
                timer1.Stop();
            }
        }

        private void btnShowTerrain_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

        private void toolStripButton1_Click_1(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

	

    

        // delete all waypoints
        private void btnWaypointsDeleteAll_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show(this, "是否删除所有路点", "确认", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                try
                {
                    foreach (ListViewItem item in listView2.Items)
                    {
                        WayPoint p = (WayPoint)WayPointsList[item];

                        ((WayPoint)WayPointsList[item]).DisLinkAll();
                        listView2.Items.Remove(item);
                        WayPointsList.Remove(item);
                    }
                }
                catch (Exception err) { }
                pictureBox1.Refresh();

            }
           
        }

        // delete all regions
        private void btnRegionsDeleteAll_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show(this, "是否删除所有区域", "确认", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                try
                {
                    foreach (ListViewItem item in listView3.Items)
                    {
                        listView3.Items.Remove(item);
                        RegionsList.Remove(item);
                    }

                }
                catch (Exception err) { }
            }
            pictureBox1.Refresh();
        }

        // sort objects
        private void listView1_ColumnClick(object sender, ColumnClickEventArgs e)
        {
            try
            {
                listView1.Sort();

            }
            catch (Exception err) { }
            pictureBox1.Refresh();
        }

	

        class ObjectsViewSorter : IComparer
        {
            Hashtable UnitMap;

            public ObjectsViewSorter(Hashtable map)
            {
                UnitMap = map;
            }

            public int Compare(Object a, Object b)
            {
                try
                {
                    Unit u1 = ((Unit)UnitMap[a]);
                    Unit u2 = ((Unit)UnitMap[b]);
                    if (u1.m_Priority != u2.m_Priority)
                    {
                        return u1.Priority - u2.Priority;
                    }
                    return u1.getY() - u2.getY();

                }catch(Exception err){}
                return 0;
            }
        }

	

	

	

	
       

      

      


       

   



     








  







    }


   


    public class WorldPorperty
    {
        StringBuilder _Data;
        int _Width;
        int _Height;
        int _CellW;
        int _CellH;

        [Description("数据"), Category("属性")]
        public StringBuilder Data
        {
            get { return _Data; }
        }

        [Description("宽"), Category("属性")]
        public int Width
        {
            get { return _Width; }
            //set { _Width = value; }
        }

        [Description("高"), Category("属性")]
        public int Height
        {
            get { return _Height; }
            //set { _Height = value; }
        }

        [Description("格子宽"), Category("属性")]
        public int CellW
        {
            get { return _CellW; }
            //set { _CellW = value; }
        }

        [Description("格子高"), Category("属性")]
        public int CellH
        {
            get { return _CellH; }
            //set { _CellH = value; }
        }

        public WorldPorperty(int w,int h,int cw,int ch,StringBuilder data)
        {
            _Width = w;
            _Height = h;
            _CellW = cw;
            _CellH = ch;
            _Data = data;
        }
    }
	
	/// <summary>
	/// /////////////////////////////////////////////////////////////////////////////////////////////////////
	/// </summary>

	public abstract class WorldListViewObject
	{
		public ListViewItem listItem;

		abstract public void updateListViewItem(ToolStripStatusLabel lb);

        abstract public void loadOver();

		abstract public bool setPos(int x, int y);

		abstract public bool touchBounds(int x, int y, ref Point offset);

		abstract public int getX();

		abstract public int getY();

		abstract public Rectangle getWorldBounds();
		
		public void movePos(int dx, int dy)
		{
			this.setPos(getX() + dx, getY() + dy);
		}
	}

	//----------------------------------------------------------------------------------------------------------
    [Serializable]
	public class Region : WorldListViewObject, ISerializable
    {
        // serial
        public bool isSub = false;

        private System.Drawing.Rectangle rect;
     

        public StringBuilder Data = new StringBuilder();
        [Description("数据"), Category("属性")]
        public StringBuilder m_Data
        {
            get { return Data; } 
        }


        public Color Color;
        [Description("颜色"), Category("属性")]
        public Color m_Color
        {
            get { return Color; }
            set { Color = value; } 
        }


        public const int SHAPE_RECT = 0;
        public const int SHAPE_ROUND = 1;

        public int Shape;
        [Description("形状"), Category("属性")]
        public int m_Shape
        {
            get { return Shape; }
            set { Shape = value; }
        }


        public Region(int x, int y, ListView p)
        {
            rect = new Rectangle(x - 8, y - 8, 16, 16);

            Color = Color.FromArgb(0xff, 0, 0xff, 0);
            Shape = 0;

			//Regions.Add(p);
			listItem = new ListViewItem(new String[] { 
                                this.rect.X.ToString(), 
                                this.rect.Y.ToString(),
                                this.rect.Width.ToString(),
                                this.rect.Height.ToString(),
                                this.Data.ToString(),
                            });
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected Region(SerializationInfo info, StreamingContext context)
        {
            try
            {
                rect = new Rectangle(
                    (int)info.GetValue("X", typeof(int)),
                    (int)info.GetValue("Y", typeof(int)),
                    (int)info.GetValue("W", typeof(int)),
                    (int)info.GetValue("H", typeof(int))
                    );
                Data.Append((String)info.GetValue("Data", typeof(String)));

                try {
                    Color = (Color)info.GetValue("Color", typeof(Color));
                    Shape = (int)info.GetValue("Shape", typeof(int));
                }
                catch (Exception e) {
                    Color = Color.FromArgb(0xff, 0, 0xff, 0);
                    Shape = 0;
                }
            }
            catch (Exception err)
            {
                Console.WriteLine("Region : " + err.StackTrace);
			}
			listItem = new ListViewItem(new String[] { 
                                this.rect.X.ToString(), 
                                this.rect.Y.ToString(),
                                this.rect.Width.ToString(),
                                this.rect.Height.ToString(),
                                this.Data.ToString(),
                            });
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            try
            {
                info.AddValue("X", rect.X);
                info.AddValue("Y", rect.Y);
                info.AddValue("W", rect.Width);
                info.AddValue("H", rect.Height);
                info.AddValue("Data", Data.ToString());

                info.AddValue("Color", Color);
                info.AddValue("Shape", Shape);
            }
            catch (Exception err)
            {
                Console.WriteLine("Region : " + err.StackTrace);
            }
        }

		override public bool touchBounds(int x, int y, ref Point offset)
        {
            if (rect.Contains(x, y))
            {
                offset.X = x - getX();
				offset.Y = y - getY();
				return true;
            }
            return false;
        }

		public void setSizeDst(int ex, int ey)
		{
			if (ex - rect.X > 0) rect.Width = ex - rect.X;
			else rect.Width = 1;
			if (ey - rect.Y > 0) rect.Height = ey - rect.Y;
			else rect.Height = 1;
		}

		public void moveSizeDst(int eX, int eY)
		{
			
				if (rect.Width + eX > 0) rect.Width += eX;
				else rect.Width = 1;
				if (rect.Height + eY > 0) rect.Height += eY;
				else rect.Height = 1;

			
		}

		override public bool setPos(int x, int y)
		{
			rect.X = x;
			rect.Y = y;
			return false;
		}
		override public int getX()
		{
			return rect.X;
		}

		override public int getY()
		{
			return rect.Y;
		}
		override public Rectangle getWorldBounds()
		{
			return new Rectangle(rect.X, rect.Y, rect.Width, rect.Height);
		}

        public bool selectSub(int x, int y)
        {
            System.Drawing.Rectangle s_rect = new System.Drawing.Rectangle(rect.Right - 4, rect.Bottom - 4, 4, 4);
            if (s_rect.Contains(x, y))
            {
                return true;
            }
            return false;
        }

        public void Render(javax.microedition.lcdui.Graphics g,bool isCheck)
        {
            try
            {
                if (isCheck)
                {
                    g.setColor(0xff, 0xff, 0xff, 0xff);
                    g.drawRect(rect.X, rect.Y, rect.Width, rect.Height);
                    
                    g.setDColor(0x80, Color);
                   
                    switch (Shape)
                    {
                        case SHAPE_RECT:
                            g.fillRect(rect.X, rect.Y, rect.Width, rect.Height);
                            break;
                        case SHAPE_ROUND:
                            g.fillArc(rect.X, rect.Y, rect.Width, rect.Height, 0, 360);
                            break;
                    }
                }
                else
                {
                    g.setDColor(0xff, Color);

                    switch (Shape) 
                    { 
                        case SHAPE_RECT:
                            g.drawRect(rect.X, rect.Y, rect.Width, rect.Height);
                            break;
                        case SHAPE_ROUND:
                            g.drawArc(rect.X, rect.Y, rect.Width, rect.Height, 0, 360);
                            break;
                    }
                   
                    
                }

                //System.Drawing.Rectangle[] s_rect = new Rectangle[4];
                //s_rect[0] = new System.Drawing.Rectangle(rect.Left - 2, rect.Top - 2, 5, 5);
                //s_rect[1] = new System.Drawing.Rectangle(rect.Left - 2, rect.Bottom - 2, 5, 5);
                //s_rect[2] = new System.Drawing.Rectangle(rect.Right - 2, rect.Top - 2, 5, 5);
                //s_rect[3] = new System.Drawing.Rectangle(rect.Right - 2, rect.Bottom - 2, 5, 5);


                // resize
                {
                    if (isCheck)
                    {
                        System.Drawing.Rectangle s_rect = new System.Drawing.Rectangle(rect.Right - 4, rect.Bottom - 4, 4, 4);
                        g.setColor(0x80, 0xff, 0xff, 0xff);
                        g.drawRect(s_rect.X, s_rect.Y, s_rect.Width, s_rect.Height);
                    }
                    if (isSub)
                    {
                        System.Drawing.Rectangle s_rect = new System.Drawing.Rectangle(rect.Right - 4, rect.Bottom - 4, 4, 4);
                        g.setColor(0xff, 0xff, 0xff, 0xff);
                        g.drawRect(s_rect.X, s_rect.Y, s_rect.Width, s_rect.Height);
                    }
                }
                if (WorldForm.IsShowDataString)
                {
                    g.setColor(0xff, 0xff, 0xff, 0xff);
                    g.dg.DrawString(Data.ToString(), javax.microedition.lcdui.Graphics.font, g.brush, rect);

                }
				g.setColor(0xff, 0, 0, 0);
				g.dg.DrawString(
					"r" + listItem.Index,
					g.getFont(),
					System.Drawing.Brushes.Black,
					rect.X,
					rect.Y - g.getFont().Height - 1);
            }
            catch (Exception err)
            {
                Console.WriteLine("Region : " + err.StackTrace);
            }

        }
        override public void loadOver()
        {

        }
		override public void updateListViewItem(ToolStripStatusLabel lb)
		{
			listItem.SubItems[0].Text = this.rect.X.ToString();
			listItem.SubItems[1].Text = this.rect.Y.ToString();
			listItem.SubItems[2].Text = this.rect.Width.ToString();
			listItem.SubItems[3].Text = this.rect.Height.ToString();
			listItem.SubItems[4].Text = this.Data.ToString();
			lb.Text = "Region[" + listItem.Index + "]:" +
			"X=" + (rect.X) + "," +
			"Y=" + (rect.Y) + "," +
			"W=" + (rect.Width) + "," +
			"H=" + (rect.Height) + " | "
		;
		}

    }


    [Serializable]
	public class WayPoint : WorldListViewObject, ISerializable
    {
        // serial
        private Point point;
        

        public StringBuilder Data = new StringBuilder();
        [Description("数据"), Category("属性")]
        public StringBuilder m_Data
        {
            get { return Data; }
        }

        public ArrayList link = new ArrayList();




		public WayPoint(int x, int y, ListView p)
        {
            point = new Point(x, y);
			//WayPoints.Add(p);
			listItem = new ListViewItem(new String[] { 
                                getX().ToString(),
                                getY().ToString(),
                                Data.ToString(),
                            });
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected WayPoint(SerializationInfo info, StreamingContext context)
        {
            try
            {
                point = new Point(
                    (int)info.GetValue("X", typeof(int)),
                    (int)info.GetValue("Y", typeof(int))
                    );
                link = (ArrayList)info.GetValue("link", typeof(ArrayList));

                try
                {
                    Data.Append((String)info.GetValue("Data", typeof(String)));
                }
                catch (Exception err) { }
            }
            catch (Exception err)
            {
                Console.WriteLine("Path : " + err.StackTrace);
			} 
			listItem = new ListViewItem(new String[] { 
                                getX().ToString(),
                                getY().ToString(),
                                Data.ToString(),
                            });
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            try
            {
                info.AddValue("X", point.X);
                info.AddValue("Y", point.Y);
                info.AddValue("link", link);
                info.AddValue("Data", Data.ToString());
            }
            catch (Exception err)
            {
                Console.WriteLine("Path : " + err.StackTrace);
            }
        }
		override public bool setPos(int x, int y)
		{
			point.X = x;
			point.Y = y;
			return false;
		}
		override public int getX()
        {
            return point.X ;
        }
		override public int getY()
        {
            return point.Y;
        }
		override public bool touchBounds(int x, int y, ref Point offset)
		{
			Rectangle rect = new Rectangle(
				point.X - 4,
				point.Y - 4,
				8, 8);
			if (rect.Contains(x, y))
			{
				offset.X = x - getX();
				offset.Y = y - getY();
				return true;
			}
			return false;
		}
		override public Rectangle getWorldBounds()
		{
			return  new Rectangle(
				point.X - 4,
				point.Y - 4,
				8, 8);
		}


        public bool Link2(WayPoint point)
        {
            if (point == null) return false;
            if (point == this) return false;

            if (!this.link.Contains(point))
            {
                this.link.Add(point);
            }
            if (!point.link.Contains(this))
            {
                point.link.Add(this);
            }

            return true;
        }

        public bool Link(WayPoint point)
        {
            if (point == null) return false;
            if (point == this) return false;

            if (!this.link.Contains(point))
            {
                this.link.Add(point);
            }

            return true;
        }

        public bool DisLink(WayPoint point)
        {
            if (point == null) return false;

            if (this.link.Contains(point))
            {
                this.link.Remove(point);
            }

            return true;
        }

        public bool DisLinkAll()
        {
            if (link.Count == 0) return false;

            foreach (WayPoint p in link)
            {
                if (p != null)
                {
                    if (p.link.Contains(this))
                    {
                        p.link.Remove(this);
                    }
                }
            }

            this.link.Clear();

            return true;
        }

        public void Render(javax.microedition.lcdui.Graphics g, bool isCheck)
        {
            try
            {
				Rectangle rect = getWorldBounds();
                int sx = point.X;
                int sy = point.Y;

                if (isCheck)
                {
                    g.setColor(0xff, 0xff, 0xff, 0xff);
					g.drawRect(rect.X, rect.Y, rect.Width, rect.Height);

                    if (WorldForm.IsShowDataString)
                    {
                        g.setColor(0xff, 0xff, 0xff, 0xff);
                        g.dg.DrawString(Data.ToString(),
							javax.microedition.lcdui.Graphics.font, 
							g.brush, 
							new System.Drawing.Rectangle(rect.X, rect.Y, 400, 400));

                    }

                }
                else
                {
                    g.setColor(0xff, 0, 0, 0xff);
					g.drawRect(rect.X, rect.Y, rect.Width, rect.Height);
                }

                foreach (WayPoint p in link)
                {
                    if (p != null)
                    {
                        int dx = p.point.X;
                        int dy = p.point.Y;

                        float[] px = new float[3];
                        float[] py = new float[3];

                        float angle = (float)Math.Atan2(dy - sy, dx - sx);

                        px[0] = dx;
                        py[0] = dy;

                        px[1] = dx - (float)Math.Cos(angle - Math.PI / 12) * 10;
                        py[1] = dy - (float)Math.Sin(angle - Math.PI / 12) * 10;

                        px[2] = dx - (float)Math.Cos(angle + Math.PI / 12) * 10;
                        py[2] = dy - (float)Math.Sin(angle + Math.PI / 12) * 10;



                        if (isCheck)
                        {
                            g.setColor(0xff, 0xff, 0xff, 0xff);
                            g.drawLine(sx, sy, dx, dy);
                            g.dg.FillPolygon(g.brush,
                                new PointF[] { 
                                new PointF(px[0],py[0]),
                                new PointF(px[1],py[1]),
                                new PointF(px[2],py[2]),
                            }
                            );
                        }
                        else
                        {
                            g.setColor(0x80, 0x00, 0x00, 0xff);
                            g.drawLine(sx, sy, dx, dy);
                            g.dg.FillPolygon(g.brush,
                                new PointF[] { 
                                new PointF(px[0],py[0]),
                                new PointF(px[1],py[1]),
                                new PointF(px[2],py[2]),
                            }
                            );
                        }

                        //float degree = Math.Atan();

                    }
                }



				g.setColor(0xff, 0, 0, 0);
				g.dg.DrawString(
					"p" + listItem.Index.ToString(),
					g.getFont(),
					System.Drawing.Brushes.Black,
					getX(),
					getY() - g.getFont().Height - 1);

            }
            catch (Exception err)
            {
                Console.WriteLine("Path : " + err.StackTrace);
            }

        }
        override public void loadOver()
        {

        }
		override public void updateListViewItem(ToolStripStatusLabel lb)
		{
			listItem.SubItems[0].Text = getX().ToString();
			listItem.SubItems[1].Text = getY().ToString();
			listItem.SubItems[2].Text = Data.ToString();
			lb.Text = "Point[" + listItem.Index + "]:" +
			"X=" + getX() + "," +
			"Y=" + getY() + " | ";
		}

    }

    [Serializable]
    public enum ImageAnchor
    {
        L_T, C_T, R_T,
        L_C, C_C, R_C,
        L_B, C_B, R_B,
    };

    [Serializable]
	public class Unit : WorldListViewObject, ISerializable
    {
        public const String TYPE_SPRITE  = "Sprite";
        public const String TYPE_MAP     = "Map";
        public const String TYPE_IMAGE   = "Image";

        public static javax.microedition.lcdui.Image imgLock =
            new javax.microedition.lcdui.Image(Resource1.lock_16);

        #region 属性
        public int frameID = 0;

        public ImageAnchor anchor = ImageAnchor.L_T;
        [Description("图片锚点"), Category("属性")]
        public ImageAnchor m_ImageAnchor
        {
            get { return anchor; }
            set { anchor = value; resetImageBounds(); }
        }

        public ImageTrans trans = ImageTrans.NONE;
        [Description("图片翻转"), Category("属性")]
        public ImageTrans m_ImageTrans
        {
            get { return trans; }
            set { trans = value; resetImageBounds(); }
        }

        public int animID = 0;
     
        public StringBuilder Data = new StringBuilder();
     
        public String id = "null";
        [Description("数据"), Category("属性")]
        public String m_ID
        {
			get { return id; }
			set { this.id = value; }
        }

        public String type = "null";

        [Description("图片组"), Category("属性")]
        public String m_Tiles
        {
            get { return getImagesName(); }
        }

        [Description("资源"), Category("属性")]
        public String m_Resource
        {
            get { return getName(); }
        }

        private int x = 0;
		private int y = 0;
        


        [Description("位置"), Category("属性")]
        public Point m_Position
        {
            get { return new Point(x, y); }
            set { x = value.X; y = value.Y; }
        }

        public int m_Priority=0;
        [Description("优先级"), Category("属性")]
        public int Priority
        {
            get { return this.m_Priority; }
            set { this.m_Priority = value; }
        }

		public String Name
		{
			get
			{
				if (_map != null && _map.id != null)
				{
					return _map.id;
				}
				if (_spr != null && _spr.id != null)
				{
					return _spr.id;
                }
                if (_images != null && _images.id != null)
                {
                    return _images.id;
                }
				return this._saved_obj_id;
			}
		}
		public String SuperName
		{
			get
			{
				if (_map != null && _map.super != null)
				{
					return _map.super.id;
				}
				if (_spr != null && _spr.super != null)
				{
					return _spr.super.id;
				}
                if (_images != null)
                {
                    return _images.id;
                }
				return this._saved_images_id;
			}
		}

        #endregion

#region 事件

        public event EventHandler OnUpdate;
        //[EditorAttribute(typeof(System.ComponentModel.Design.ObjectSelectorEditor), typeof(System.Drawing.Design.UITypeEditor))]
        [Description("每周期的更新事件"), Category("事件")]
        public EventHandler Update { get { return OnUpdate; } set { OnUpdate = value; } }

#endregion

        private Rectangle Bounds;

		private SpriteForm  _spr;
        private MapForm     _map;
        private ImagesForm  _images;

		private String _saved_obj_id;
		private String _saved_images_id;

        public Boolean isSaveChecked;
      

		public Unit(SpriteForm spr, String no, ListView p)
        {
            this.id = no + spr.id;
            this.animID = 0;
            this.listItem = new ListViewItem(new String[] {
                        this.id, 
                        this.animID.ToString() ,
                        this.x.ToString(),
                        this.y.ToString(),
                        this.Priority.ToString(),
                        this.Data.ToString(),
                    });
            setSuper(spr, null, null);
        }

		public Unit(MapForm map, String no, ListView p)
        {
            this.id = no + map.id;
            this.animID = 0;
            this.listItem = new ListViewItem(new String[] {
                        this.id, 
                        this.animID.ToString() ,
                        this.x.ToString(),
                        this.y.ToString(),
                        this.Priority.ToString(),
                        this.Data.ToString(),
                    });
            setSuper(null, map, null);
        }

        public Unit(ImagesForm images, String no, ListView p, int tileID)
        {
            this.id = no + images.id;
            this.animID = tileID;
            this.listItem = new ListViewItem(new String[] {
                        this.id, 
                        this.animID.ToString() ,
                        this.x.ToString(),
                        this.y.ToString(),
                        this.Priority.ToString(),
                        this.Data.ToString(),
                    });
            setSuper(null, null, images);

        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected Unit(SerializationInfo info, StreamingContext context)
        {
            try
            {
                id = (String)info.GetValue("id", typeof(String));
                x = (int)info.GetValue("x", typeof(int));
                y = (int)info.GetValue("y", typeof(int));
                anchor = (ImageAnchor)Util.GetValue(info, "anchor", typeof(ImageAnchor), ImageAnchor.L_T);
                trans = (ImageTrans)Util.GetValue(info, "trans", typeof(ImageTrans), ImageTrans.NONE);
                m_Priority = (int)Util.GetValue(info, "priority", typeof(int), 0);
                animID = (int)Util.GetValue(info, "animID",   typeof(int), 0);
                isSaveChecked = (Boolean)Util.GetValue(info, "isSaveChecked", typeof(Boolean), false);

				try
				{
					type = (String)info.GetValue("Type", typeof(String));
					_saved_obj_id = (String)info.GetValue("Name", typeof(String));
					_saved_images_id = (String)info.GetValue("SuperName", typeof(String));
				}
				catch (Exception err) { }

				this.Bounds = new Rectangle(-10, -10, 20, 20);

				if (!ProjectForm.IsCopy)
				{
					try
					{
						_map = (MapForm)info.GetValue("map", typeof(MapForm));
						if (_map != null)
						{
							type = TYPE_MAP;
						}
						_spr = (SpriteForm)info.GetValue("spr", typeof(SpriteForm));
						if (_spr != null)
						{
							type = TYPE_SPRITE;
						}
                        _images = (ImagesForm)info.GetValue("images", typeof(ImagesForm));
                        if (_images != null)
                        {
                            type = TYPE_IMAGE;
                        }
					}
					catch (Exception err) { }
				}

                try
                {
                    Data.Append((String)info.GetValue("Data", typeof(String)));
                }
                catch (Exception err) { }
            }
            catch (Exception err)
            {
                Console.WriteLine("Unit:" + err.StackTrace);
            }

			this.listItem = new ListViewItem(new String[] {
                        this.id, 
                        this.animID.ToString() ,
                        this.x.ToString(),
                        this.y.ToString(),
                        this.Priority.ToString(),
                        this.Data.ToString(),
                    });
            setSuper(_spr, _map, _images);
        }



		public void setSuper(SpriteForm sprf, MapForm mapf, ImagesForm imagesf)
		{
           if (mapf != null)
			{
                this._map = mapf;
                this.type = TYPE_MAP;
                this.Bounds = new Rectangle(0, 0, mapf.CellW, mapf.CellH);
            }
			else if (sprf != null)
            {
                this._spr = sprf;
                this.type = TYPE_SPRITE;
                this.Bounds = sprf.getVisibleBounds(animID, 0);
            }
           else if (imagesf != null)
            {
                this._images = imagesf;
                this.type = TYPE_IMAGE;
                this.Bounds = resetImageBounds();
            }
		}

        private Rectangle resetImageBounds()
		{
			if (_spr != null)
			{
				this.Bounds = _spr.getVisibleBounds(animID, 0);
			}
            else if (_images != null)
            {
                javax.microedition.lcdui.Image img = _images.getDstImage(animID);
                this.Bounds = new Rectangle(
                    0, 0,
                    img.getWidth(),
                    img.getHeight());

                switch (trans)
                {
                    case ImageTrans.R_90:
                    case ImageTrans.R_270:
                    case ImageTrans.MR_90:
                    case ImageTrans.MR_270:
                        this.Bounds.Width = img.getHeight();
                        this.Bounds.Height = img.getWidth();
                        break;
                }

                switch (anchor)
                {
                    case ImageAnchor.L_T:
                        break;
                    case ImageAnchor.C_T:
                        this.Bounds.X -= Bounds.Width / 2;
                        break;
                    case ImageAnchor.R_T:
                        this.Bounds.X -= Bounds.Width;
                        break;
                    case ImageAnchor.L_C:
                        this.Bounds.Y -= Bounds.Height / 2;
                        break;
                    case ImageAnchor.C_C:
                        this.Bounds.X -= Bounds.Width / 2;
                        this.Bounds.Y -= Bounds.Height / 2;
                        break;
                    case ImageAnchor.R_C:
                        this.Bounds.X -= Bounds.Width;
                        this.Bounds.Y -= Bounds.Height / 2;
                        break;
                    case ImageAnchor.L_B:
                        this.Bounds.Y -= Bounds.Height;
                        break;
                    case ImageAnchor.C_B:
                        this.Bounds.X -= Bounds.Width / 2;
                        this.Bounds.Y -= Bounds.Height;
                        break;
                    case ImageAnchor.R_B:
                        this.Bounds.X -= Bounds.Width;
                        this.Bounds.Y -= Bounds.Height;
                        break;
                }
			} 
            return Bounds;
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            try
            {
                info.AddValue("id", id);
                info.AddValue("x", x);
                info.AddValue("y", y);
                info.AddValue("animID", animID);
                info.AddValue("priority", m_Priority);
                info.AddValue("isSaveChecked", isSaveChecked);
                info.AddValue("anchor", anchor);
                info.AddValue("trans", trans);
				/*
                if (!ProjectForm.IsCopy)
                {
                    try
                    {
                        info.AddValue("map", map);
                    }
                    catch (Exception err) { }
                    try
                    {
                        info.AddValue("spr", spr);
                    }
                    catch (Exception err) { }
                }
                */
                {
                    info.AddValue("Type",type);
                    info.AddValue("Name", getName());
                    info.AddValue("SuperName", getImagesName());
                }
                info.AddValue("Data", Data.ToString());
            }
            catch (Exception err)
            {
                Console.WriteLine("Unit:" + err.StackTrace);
            }
        }
        override public void loadOver()
        {

        }

        public SpriteForm spr()
        {
            return _spr;
        }
        public MapForm map()
        {
            return _map;
        }
        public ImagesForm images()
        {
            return _images;
        }
		override public void updateListViewItem(ToolStripStatusLabel lb)
		{
			listItem.SubItems[0].Text = id.ToString();
			listItem.SubItems[1].Text = animID.ToString();
			listItem.SubItems[2].Text = x.ToString();
			listItem.SubItems[3].Text = y.ToString();
			listItem.SubItems[4].Text = Priority.ToString();
			listItem.SubItems[5].Text = Data.ToString();
			  //listView1.SelectedItems[0].SubItems[2].Text = unit.x.ToString();
                            //listView1.SelectedItems[0].SubItems[3].Text = unit.y.ToString();
			lb.Text = "Unit[" + getName() + "]:" +
			"X=" + getX() + "," +
			"Y=" + getY() + "," +
			"Prio=" + Priority + " | "
		;
		}
		
		override public int getX()
		{
			return x;
		}
		override public int getY()
		{
			return y;
		}
		override public bool setPos(int x, int y)
		{
			this.x = x;
			this.y = y;
			return true;
		}
		override public bool touchBounds(int x, int y, ref Point offset)
		{
			if (Bounds.Contains(x - this.x, y - this.y))
			{
				offset.X = x - getX();
				offset.Y = y - getY();
				return true;
			}
			return false;
		}
		override public Rectangle getWorldBounds()
		{
			return new Rectangle(
				x + Bounds.X,
				y + Bounds.Y,
				Bounds.Width, 
				Bounds.Height);
		}

        public void nextAnim(int delta)
        {
            if (_spr != null) {
                animID = Util.cycNum(animID, delta, _spr.GetAnimateCount()); 
            }
            else if (_images != null) {
                animID = Util.cycNum(animID, delta, _images.getDstImageCount()); 
            }

			this.Bounds = resetImageBounds();
        }

        public bool isKilled()
        {
            if (spr() == null && map() == null && images() == null)
            {
                return true;
            }
            if ((spr() != null && spr().IsDisposed) ||
                (map() != null && map().IsDisposed) ||
                (images() != null && images().IsDisposed))
            {
                return true;
            }
            return false;
        }
		/*
        public Rectangle getGobalBounds()
        {
            Rectangle rect = new Rectangle(
                x + Bounds.X, 
                y + Bounds.Y, 
                Bounds.Width,
                Bounds.Height);
            return rect;
        }
		*/
        public string getName()
        {
            return Name;
        }

        public string getImagesName()
        {
            return SuperName;
        }

        public void render(
            javax.microedition.lcdui.Graphics g,
            System.Drawing.Rectangle scope, 
            Boolean selected,
            Boolean select,
            Boolean debug,
            Boolean showLock)
        {
			render(g, scope, x, y, selected, select, debug, showLock);
        }


		public void render(
			javax.microedition.lcdui.Graphics g,
			System.Drawing.Rectangle scope, 
			int x, int y,
			Boolean selected,
			Boolean select,
			Boolean debug,
			Boolean showLock)
		{

			if (_spr != null)
			{
				//this.Bounds = _spr.getVisibleBounds(animID, 0);
				_spr.Render(g, x, y, debug, animID, frameID);
			}
			else if (_map != null)
			{
				_map.Render(g, x, y, scope, false, debug, true, frameID);
				frameID++;
			}
			else if (_images != null)
			{
				javax.microedition.lcdui.Image img = _images.getDstImage(animID);
				g.drawImageTrans(img,
						  x + Bounds.X,
						  y + Bounds.Y,
						  trans);
			}

			if (select)
			{
				g.setColor(0xff, 0xff, 0xff, 0xff);
				g.drawRect(x + Bounds.X, y + Bounds.Y, Bounds.Width, Bounds.Height);
			}

			if (selected)
			{
				g.setColor(0x40, 0xff, 0xff, 0xff);
				g.fillRect(x + Bounds.X, y + Bounds.Y, Bounds.Width, Bounds.Height);

				if (WorldForm.IsShowDataString)
				{
					g.setColor(0xff, 0xff, 0xff, 0xff);
					g.dg.DrawString(Data.ToString(),
						javax.microedition.lcdui.Graphics.font, 
						g.brush,
						new System.Drawing.Rectangle(x, y, 400, 400));
				}
				g.setColor(0xff, 0xffffff);
				g.drawLine(x - 10, y, x + 10, y);
				g.drawLine(x, y - 10, x, y + 10);
			}

			if (showLock && !listItem.Checked)
			{
				g.drawImage(imgLock, x + Bounds.X, y + Bounds.Y);
			}
		}



    }


    [Serializable]
    public class Event : WorldListViewObject, ISerializable
    {
        public static javax.microedition.lcdui.Image quest =
           new javax.microedition.lcdui.Image(Resource1.Question);

		private EventTemplatePlugin et;
		private EventNode node;
		private javax.microedition.lcdui.Image icon;
        private String et_file;
        private String et_name;



        public long ID;
        [Description("EVENT_ID"), Category("属性")]
        public long m_ID
        {
            get { return ID; }
        }

        private Point point;
        [Description("位置"), Category("属性")]
        public Point m_Point
        {
            get { return point; }
            set { point = value; }
        }

        public StringBuilder Data = new StringBuilder();
        [Description("数据"), Category("属性")]
        public StringBuilder m_Data
        {
            get { return Data; }
        }

        private Rectangle rect = new Rectangle(
            -quest.getWidth()/2,
            -quest.getHeight()/2,
            quest.getWidth(),
            quest.getHeight());

		public Event(EventTemplatePlugin et, EventNode nd, int x, int y, long id)
        {
            this.et = et;
			this.node = nd;

            et_file = et.getClassName();
            et_name = nd.name;

            ID = id;
            point = new Point(x, y);

            listItem = new ListViewItem(new String[] { 
               et_name,
               getX().ToString(),
               getY().ToString(),
               Data.ToString(),
              });
			listItem.ImageKey = node.name;

            loadOver();
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected Event(SerializationInfo info, StreamingContext context)
        {
            try
            {
                et_name = (String)info.GetValue("et_name", typeof(String));
                et_file = (String)info.GetValue("et_file", typeof(String));
                ID = (long)info.GetValue("ID", typeof(long));
                point = new Point(
                      (int)info.GetValue("X", typeof(int)),
                      (int)info.GetValue("Y", typeof(int))
                      );
                Data.Append((String)info.GetValue("Data", typeof(String)));
            }
            catch (Exception err)
            {
                Console.WriteLine("Event:" + err.StackTrace);
            }

            listItem = new ListViewItem(new String[] { 
                   et_name,
                   getX().ToString(),
                   getY().ToString(),
                   Data.ToString(),
                  });
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            try
            {
                info.AddValue("et_name", et_name);
                info.AddValue("et_file", et_file);
                info.AddValue("ID", ID);
                info.AddValue("X", point.X);
                info.AddValue("Y", point.Y);
                info.AddValue("Data", Data.ToString());
            }
            catch (Exception err)
            {
                Console.WriteLine("Event:" + err.StackTrace);
            }
        }

        public WorldEvent asWorldEvent()
        {
            WorldEvent we = new WorldEvent();
            we.source = node;
            we.x = getX();
            we.y = getY();
            we.appendData = Data.ToString();
            return we;
        }

        override public void loadOver()
        {
            et = ProjectForm.getInstance().getEventTemplateForm(et_file);
			
            if (et != null)
			{
				node = et.getEvent(et_name);
				
				if (node != null)
                {
                    listItem.ImageKey = node.iconKey;
                    try {
                        System.Drawing.Image picon = et.getImageList().Images[node.iconKey];
                        if (picon != null)
                        {
                            this.icon = new javax.microedition.lcdui.Image(picon);
                            rect = new Rectangle(
                                -icon.getWidth() / 2,
                                -icon.getHeight() / 2,
                                icon.getWidth(),
                                icon.getHeight());
                        }
                    } catch (Exception err) {}
                }
            }
        }

		override public void updateListViewItem(ToolStripStatusLabel lb)
        {
            listItem.SubItems[0].Text = et_name;
            listItem.SubItems[1].Text = getX().ToString();
            listItem.SubItems[2].Text = getY().ToString();
            listItem.SubItems[3].Text = Data.ToString();
			lb.Text = "Event[" + listItem.Index + "]:" +
								"X=" + (getX()) + "," +
								"Y=" + (getY()) + ","; 
        }
		
		public String getEventFile()
		{
			return et_file;
		}
		public String getEventName()
		{
			return et_name;
		}
		override public Rectangle getWorldBounds()
		{
			return new Rectangle(
				this.point.X + rect.X,
				this.point.Y + rect.Y,
				rect.Width,
				rect.Height);
		}
		override public bool setPos(int x, int y)
		{
			this.point.X = x;
			this.point.Y = y;
			return true;
		}
		override public int getX()
        {
            return point.X;
        }
		override public int getY()
        {
            return point.Y;
        }
		override public bool touchBounds(int x, int y, ref Point offset)
		{
// 			Rectangle rect = new Rectangle(
// 				point.X - 4,
// 				point.Y - 4,
// 				8, 8);
			if (rect.Contains(x - point.X, y - point.Y))
			{
				offset.X = x - getX();
				offset.Y = y - getY();
				return true;
			}
			return false;
		}
//         public int getW()
//         {
//             return rect.Width;
//         }
//         public int getH()
//         {
//             return rect.Height;
//         }
//         public Rectangle getRect()
//         {
//             return rect;
//         }

        public void render(javax.microedition.lcdui.Graphics g, 
            System.Drawing.Rectangle scope,
            Boolean selected)
        {
            if (icon!= null) 
            {
				g.drawImage(icon, point.X + rect.X, point.Y + rect.Y);
            }
            else
            {
                g.drawImage(quest, point.X + rect.X, point.Y + rect.Y);
            }
            if (selected)
            {
                g.setColor(0xff, 0xff, 0xff, 0xff);
                g.drawRect(
                   point.X + rect.X,
                   point.Y + rect.Y,
                   rect.Width,
                   rect.Height);
            }
        }
    }

}