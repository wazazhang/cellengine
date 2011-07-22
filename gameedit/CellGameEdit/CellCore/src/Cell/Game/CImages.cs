namespace Cell.Game{


/**
 * 最底层的对图片的处理。 里面包含了一组图片的集合。 
 * 可以把一个大Image作为原图切成若干块小Image，也可以使用若干Image.
 * 
 *  
 */
    public class CImages
    {
        public const int TRANS_NONE = 0;
        public const int TRANS_90 = 1;
        public const int TRANS_180 = 2;
        public const int TRANS_270 = 3;
        public const int TRANS_H = 4;
        public const int TRANS_H90 = 5;
        public const int TRANS_H180 = 6;
        public const int TRANS_H270 = 7;
    }
}