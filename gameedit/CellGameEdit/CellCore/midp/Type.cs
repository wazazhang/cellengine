using System;
using System.Collections.Generic;
using System.Text;

namespace Cell
{

    [Serializable]
    public enum ImageTrans
    {
        NONE,
        R_90, R_180, R_270,
        MIRROR,
        MR_90, MR_180, MR_270,
    };

}
