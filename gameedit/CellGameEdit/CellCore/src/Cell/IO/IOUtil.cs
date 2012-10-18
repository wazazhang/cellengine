using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace Cell.IO
{
    public class IOUtil
    {
        public static MemoryStream copy(Stream src, long srcindex, int count)
        {
            long oldp = src.Position;
            try 
            {
                src.Position = srcindex;
                byte[] data = new byte[count];
                src.Read(data, 0, count);
                MemoryStream ms = new MemoryStream(data);
                return ms;
            }
            finally
            {
                src.Position = oldp;
            }
        }


    }

    public class LittleEdian
    {
        public static uint readU32(Stream s)
        {
            uint ret = 0;
            byte[] data = new byte[4];
            s.Read(data, 0, 4);
            ret |= (uint)(data[0] << 24);
            ret |= (uint)(data[1] << 16);
            ret |= (uint)(data[2] << 8);
            ret |= (uint)(data[3]);
            return ret;
        }
    }
}
