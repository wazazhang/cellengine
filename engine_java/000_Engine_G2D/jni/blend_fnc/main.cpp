


#include "com_g2d_java2d_impl_composite_BlendComposite.h"




#ifdef __cplusplus
extern "C" {
#endif



__inline jint Max(jint a, jint b)
{
	return (b > a)? b : a;
}

__inline jint Min(jint a, jint b)
{
	return (b < a)? b : a;
}


/*
* Class:     com_g2d_java2d_impl_composite_BlendComposite
* Method:    blend_cppImpl
* Signature: ([I[I[I)V
*/
JNIEXPORT void JNICALL Java_com_g2d_java2d_impl_composite_BlendComposite_blend_1cppImpl(JNIEnv *pEnv, jclass jcls, jintArray src_pixels, jintArray dst_pixels, jint width, jint height, jfloat alpha)
{
	jint *srcs = pEnv->GetIntArrayElements(src_pixels, false);
	jint *dsts = pEnv->GetIntArrayElements(dst_pixels, false);

	int size = width * height;
//
// 	for ( int i=0; i<size; ++i )
// 		dsts[i] = 0xFFFF0000;

	jint src_pixel[4];
	jint dst_pixel[4];
	jint rst_pixel[4];

	for ( int idx = 0; idx<size; ++idx ) 
	{         		
//		printf("x=%d, y=%d", x, y);
// 		int idx = y*width + x;

//		dsts[idx] = 0;

		// pixels are stored as INT_ARGB
		// our arrays are [R, G, B, A]
		jint pixel = srcs[idx];

		src_pixel[0] = (pixel >> 16) & 0xFF;
		src_pixel[1] = (pixel >>  8) & 0xFF;
		src_pixel[2] = (pixel      ) & 0xFF;
		src_pixel[3] = (pixel >> 24) & 0xFF;

		pixel = dsts[idx];

		dst_pixel[0] = (pixel >> 16) & 0xFF;
		dst_pixel[1] = (pixel >>  8) & 0xFF;
		dst_pixel[2] = (pixel      ) & 0xFF;
		dst_pixel[3] = (pixel >> 24) & 0xFF;

		rst_pixel[0] = 255 - ((255 - src_pixel[0]) * (255 - dst_pixel[0]) >> 8);
		rst_pixel[1] = 255 - ((255 - src_pixel[1]) * (255 - dst_pixel[1]) >> 8);
		rst_pixel[2] = 255 - ((255 - src_pixel[2]) * (255 - dst_pixel[2]) >> 8);
 		rst_pixel[3] = Min(255, src_pixel[3] + dst_pixel[3] - (src_pixel[3] * dst_pixel[3]) / 255);

//		blender.blend(srcPixel, dstPixel, result);

		// mixes the result with the opacity
		dsts[idx] = ((jint) (dst_pixel[3] + (rst_pixel[3] - dst_pixel[3]) * alpha) & 0xFF) << 24 |
			((jint) (dst_pixel[0] + (rst_pixel[0] - dst_pixel[0]) * alpha) & 0xFF) << 16 |
			((jint) (dst_pixel[1] + (rst_pixel[1] - dst_pixel[1]) * alpha) & 0xFF) <<  8 |
			(jint) (dst_pixel[2] + (rst_pixel[2] - dst_pixel[2]) * alpha) & 0xFF;

//		dsts[idx] = 0xFFFF0000;
	}

	pEnv->ReleaseIntArrayElements(dst_pixels, dsts, 0);
	pEnv->ReleaseIntArrayElements(src_pixels, srcs, 0);
}




#ifdef __cplusplus
}
#endif