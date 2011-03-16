            
            
			////djz 1.0文件结构

            ////*文件开始*//
            //u32	FileSize	;	//文件大小
            //u32	BPM		    ;	//歌曲速度 (Beat Per Min 拍/分钟)
            //u32	NoteCount	;	//音符数量
            //Node*	Notes   	;	//音符数据
            ////*文件结束*//

            ////文件字节流按照 高位存高字节 低位存低字节的顺序

            ////音符结构体 (就是游戏中落下的块)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 该音符在什么时间激发
            //u8	Line	;	//游戏中的轨道 ：将由编辑器编辑
            //u8	Data	;	//发音 (就是MIDI事件的发音)
            //}Node;
            

            
            ////djz 2.0文件结构

            ////*文件开始*//
            //u32	FileSize	;	//文件大小
            //u32   EndTime     ;   //结束时间
            //u32	NoteCount	;	//音符数量
            //Note*	Notes   	;	//音符数据
            //u32	MetaCount	;	//控制数量
            //Meta*	Controls	;	//控制数据
            ////*文件结束*//

            ////文件字节流按照 高位存高字节 低位存低字节的顺序

            ////音符结构体 (就是游戏中落下的块)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 该音符在什么时间激发
            //u8	Line	;	//游戏中的轨道：将由编辑器编辑
            //u8	Data	;	//发音 (就是MIDI事件的发音)
            //}Note;

            ////控制结构体(比如控制BPM)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 在什么时间激发
            //u8	Type	;	//类型
            //u32   Data    ;   //数据
            //}Meta;