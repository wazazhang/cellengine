namespace Cell.Game{


/**
 * 最底层的对图片的处理。 里面包含了一组图片的集合。 
 * 可以把一个大Image作为原图切成若干块小Image，也可以使用若干Image.
 * 
 *  
 */
    public class CImages
    {
        /*
         * 翻转参数
         */
        /** 不翻转 */
       public const int TRANS_NONE = 0;
        /** 水平翻转 */
       public const int TRANS_H = 2;
        /** 垂直翻转 */
       public const int TRANS_V = 1;
        /** 180度翻转 */
       public const int TRANS_HV = 3;
        /** 逆时针90度翻转 */
       public const int TRANS_90 = 6;
        /** 逆时针270度翻转 */
       public const int TRANS_270 = 5;
        /** 先逆时针90度翻转，然后在水平翻转 */
       public const int TRANS_H90 = 4;
        /** 先逆时针90度翻转，然后在垂直翻转 */
       public const int TRANS_V90 = 7;
        /** 180度翻转 */
       public const int TRANS_180 = 3; // 180 = HV
        //    public static Graphics g;
    }
}