package javax.media.opengl;

import java.io.*;
import javax.media.opengl.*;
import javax.media.opengl.GLBase;
import javax.media.opengl.GLES1;
import javax.media.opengl.GL;
import javax.media.opengl.GL2ES1;
import com.sun.gluegen.runtime.*;

/** <P> Composable pipeline which wraps an underlying {@link GL} implementation,
    providing error checking after each OpenGL method call. If an error occurs,
    causes a {@link GLException} to be thrown at exactly the point of failure.
    Sample code which installs this pipeline: </P>

<PRE>
     GL gl = drawable.setGL(new DebugGL(drawable.getGL()));
</PRE>
*/
public class DebugGLES1 implements javax.media.opengl.GLBase, javax.media.opengl.GL, javax.media.opengl.GL2ES1, javax.media.opengl.GLES1
{
  public static final boolean DEBUG = com.sun.opengl.impl.Debug.debug("DebugGLES1");
  public DebugGLES1(GLES1 downstreamGLES1)
  {
    if (downstreamGLES1 == null) {
      throw new IllegalArgumentException("null downstreamGLES1");
    }
    this.downstreamGLES1 = downstreamGLES1;
    // Fetch GLContext object for better error checking (if possible)
    _context = downstreamGLES1.getContext();
  }

  public boolean isGL() {
    return true;
  }
  public boolean isGL3bc() {
    return false;
  }
  public boolean isGL3() {
    return false;
  }
  public boolean isGL2() {
    return false;
  }
  public boolean isGLES1() {
    return true;
  }
  public boolean isGLES2() {
    return false;
  }
  public boolean isGL2ES1() {
    return true;
  }
  public boolean isGL2ES2() {
    return false;
  }
  public boolean isGL2GL3() {
    return false;
  }
  public boolean isGLES() {
    return isGLES2() || isGLES1();
  }
  public javax.media.opengl.GL getGL() {
    return this;
  }
  public javax.media.opengl.GL3bc getGL3bc() {
    throw new GLException("Not a GL3bc implementation");
  }
  public javax.media.opengl.GL3 getGL3() {
    throw new GLException("Not a GL3 implementation");
  }
  public javax.media.opengl.GL2 getGL2() {
    throw new GLException("Not a GL2 implementation");
  }
  public javax.media.opengl.GLES1 getGLES1() {
    return this;
  }
  public javax.media.opengl.GLES2 getGLES2() {
    throw new GLException("Not a GLES2 implementation");
  }
  public javax.media.opengl.GL2ES1 getGL2ES1() {
    return this;
  }
  public javax.media.opengl.GL2ES2 getGL2ES2() {
    throw new GLException("Not a GL2ES2 implementation");
  }
  public javax.media.opengl.GL2GL3 getGL2GL3() {
    throw new GLException("Not a GL2GL3 implementation");
  }
  public GLProfile getGLProfile() {
    return downstreamGLES1.getGLProfile();
  }
  public  void glLoadPaletteFromModelViewMatrixOES()
  {
        checkContext();
downstreamGLES1.glLoadPaletteFromModelViewMatrixOES();
    String txt = new String("glLoadPaletteFromModelViewMatrixOES(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGLES1.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glTexImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGLES1.glLoadMatrixf(arg0);
    String txt = new String("glLoadMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetFloatv(arg0,arg1,arg2);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glTexCoordPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoordPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferSubData(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glBufferSubData(arg0,arg1,arg2,arg3);
    String txt = new String("glBufferSubData(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glTexEnvf(arg0,arg1,arg2);
    String txt = new String("glTexEnvf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetMaterialfv(arg0,arg1,arg2);
    String txt = new String("glGetMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlush()
  {
        checkContext();
downstreamGLES1.glFlush();
    String txt = new String("glFlush(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGLES1.glColorPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glColorPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvi(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexEnvi(arg0,arg1,arg2);
    String txt = new String("glTexEnvi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glLightfv(arg0,arg1,arg2,arg3);
    String txt = new String("glLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullFace(int arg0)
  {
        checkContext();
downstreamGLES1.glCullFace(arg0);
    String txt = new String("glCullFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetLightfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glLightfv(arg0,arg1,arg2);
    String txt = new String("glLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsFramebuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsFramebuffer(arg0);
    String txt = new String("glIsFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glBlendEquationSeparate(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBlendEquationSeparate(arg0,arg1);
    String txt = new String("glBlendEquationSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetLightfv(arg0,arg1,arg2);
    String txt = new String("glGetLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLineWidth(float arg0)
  {
        checkContext();
downstreamGLES1.glLineWidth(arg0);
    String txt = new String("glLineWidth(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean isExtensionAvailable(java.lang.String arg0)
  {
        return downstreamGLES1.isExtensionAvailable(arg0);
  }
  public  void glEGLImageTargetTexture2DOES(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGLES1.glEGLImageTargetTexture2DOES(arg0,arg1);
    String txt = new String("glEGLImageTargetTexture2DOES(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int getSwapInterval()
  {
        return downstreamGLES1.getSwapInterval();
  }
  public  void glDrawTexxOES(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGLES1.glDrawTexxOES(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawTexxOES(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelx(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glLightModelx(arg0,arg1);
    String txt = new String("glLightModelx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilOp(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glStencilOp(arg0,arg1,arg2);
    String txt = new String("glStencilOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenx(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexGenx(arg0,arg1,arg2);
    String txt = new String("glTexGenx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetMaterialfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glDeleteFramebuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void setSwapInterval(int arg0)
  {
    downstreamGLES1.setSwapInterval(arg0);
  }
  public  void glGetTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexEnvxv(arg0,arg1,arg2);
    String txt = new String("glTexEnvxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendFuncSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glBlendFuncSeparate(arg0,arg1,arg2,arg3);
    String txt = new String("glBlendFuncSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBindBuffer(arg0,arg1);
    String txt = new String("glBindBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetClipPlanex(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetClipPlanex(arg0,arg1,arg2);
    String txt = new String("glGetClipPlanex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glLoadMatrixf(arg0,arg1);
    String txt = new String("glLoadMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendEquation(int arg0)
  {
        checkContext();
downstreamGLES1.glBlendEquation(arg0);
    String txt = new String("glBlendEquation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGLES1.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetFramebufferAttachmentParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glMaterialf(arg0,arg1,arg2);
    String txt = new String("glMaterialf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetFloatv(arg0,arg1);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGLES1.glNormalPointer(arg0,arg1,arg2);
    String txt = new String("glNormalPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glLightModelfv(arg0,arg1);
    String txt = new String("glLightModelfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGLES1.glVertexPointer(arg0);
    String txt = new String("glVertexPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGLES1.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glReadPixels(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialx(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glMaterialx(arg0,arg1,arg2);
    String txt = new String("glMaterialx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferRenderbuffer(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glFramebufferRenderbuffer(arg0,arg1,arg2,arg3);
    String txt = new String("glFramebufferRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glMultMatrixf(arg0,arg1);
    String txt = new String("glMultMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableClientState(int arg0)
  {
        checkContext();
downstreamGLES1.glEnableClientState(arg0);
    String txt = new String("glEnableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexParameterxv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGeniv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexGeniv(arg0,arg1,arg2);
    String txt = new String("glGetTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glFogfv(arg0,arg1,arg2);
    String txt = new String("glFogfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexParameteri(arg0,arg1,arg2);
    String txt = new String("glTexParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointSizex(int arg0)
  {
        checkContext();
downstreamGLES1.glPointSizex(arg0);
    String txt = new String("glPointSizex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexfvOES(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGLES1.glDrawTexfvOES(arg0);
    String txt = new String("glDrawTexfvOES(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearStencil(int arg0)
  {
        checkContext();
downstreamGLES1.glClearStencil(arg0);
    String txt = new String("glClearStencil(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,long arg7)
  {
        checkContext();
downstreamGLES1.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedTexImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glTexGenf(arg0,arg1,arg2);
    String txt = new String("glTexGenf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glPointParameterfv(arg0,arg1);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexGenfv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glOrthox(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGLES1.glOrthox(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glOrthox(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsEnabled(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsEnabled(arg0);
    String txt = new String("glIsEnabled(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexGeni(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexGeni(arg0,arg1,arg2);
    String txt = new String("glTexGeni(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexxvOES(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGLES1.glDrawTexxvOES(arg0);
    String txt = new String("glDrawTexxvOES(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGLES1.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glTexSubImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterx(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexParameterx(arg0,arg1,arg2);
    String txt = new String("glTexParameterx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsTexture(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsTexture(arg0);
    String txt = new String("glIsTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  boolean glIsVBOArrayEnabled()
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsVBOArrayEnabled();
    String txt = new String("glIsVBOArrayEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  java.nio.ByteBuffer glMapBuffer(int arg0,int arg1)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGLES1.glMapBuffer(arg0,arg1);
    String txt = new String("glMapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glPointParameterfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glPointParameterfv(arg0,arg1,arg2);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture2D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGLES1.glFramebufferTexture2D(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glFramebufferTexture2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnviv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexEnviv(arg0,arg1,arg2);
    String txt = new String("glTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexGenfv(arg0,arg1,arg2);
    String txt = new String("glTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawElements(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glDrawElements(arg0,arg1,arg2,arg3);
    String txt = new String("glDrawElements(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGLES1.glNormalPointer(arg0);
    String txt = new String("glNormalPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvx(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTexEnvx(arg0,arg1,arg2);
    String txt = new String("glTexEnvx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexfvOES(float[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glDrawTexfvOES(arg0,arg1);
    String txt = new String("glDrawTexfvOES(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glFogfv(arg0,arg1);
    String txt = new String("glFogfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glAlphaFuncx(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glAlphaFuncx(arg0,arg1);
    String txt = new String("glAlphaFuncx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glActiveTexture(int arg0)
  {
        checkContext();
downstreamGLES1.glActiveTexture(arg0);
    String txt = new String("glActiveTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glDeleteRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGLES1.glMultMatrixf(arg0);
    String txt = new String("glMultMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadIdentity()
  {
        checkContext();
downstreamGLES1.glLoadIdentity();
    String txt = new String("glLoadIdentity(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glLightModelfv(arg0,arg1,arg2);
    String txt = new String("glLightModelfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexiOES(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGLES1.glDrawTexiOES(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawTexiOES(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthRangef(float arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glDepthRangef(arg0,arg1);
    String txt = new String("glDepthRangef(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClipPlanef(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glClipPlanef(arg0,arg1);
    String txt = new String("glClipPlanef(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexfOES(float arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGLES1.glDrawTexfOES(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawTexfOES(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLineWidthx(int arg0)
  {
        checkContext();
downstreamGLES1.glLineWidthx(arg0);
    String txt = new String("glLineWidthx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexParameterxv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glOrtho(double arg0,double arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGLES1.glOrtho(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glOrtho(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGeniv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexGeniv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glAlphaFunc(int arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glAlphaFunc(arg0,arg1);
    String txt = new String("glAlphaFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMode(int arg0)
  {
        checkContext();
downstreamGLES1.glMatrixMode(arg0);
    String txt = new String("glMatrixMode(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearColor(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGLES1.glClearColor(arg0,arg1,arg2,arg3);
    String txt = new String("glClearColor(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTranslatex(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glTranslatex(arg0,arg1,arg2);
    String txt = new String("glTranslatex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenerateMipmap(int arg0)
  {
        checkContext();
downstreamGLES1.glGenerateMipmap(arg0);
    String txt = new String("glGenerateMipmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexxvOES(int[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glDrawTexxvOES(arg0,arg1);
    String txt = new String("glDrawTexxvOES(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGLES1.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedTexSubImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glWeightPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glWeightPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnable(int arg0)
  {
        checkContext();
downstreamGLES1.glEnable(arg0);
    String txt = new String("glEnable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthRangex(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glDepthRangex(arg0,arg1);
    String txt = new String("glDepthRangex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnviv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexEnviv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGLES1.glVertexPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean hasGLSL()
  {
        return downstreamGLES1.hasGLSL();
  }
  public  void glPointParameterx(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glPointParameterx(arg0,arg1);
    String txt = new String("glPointParameterx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4f(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGLES1.glColor4f(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glDeleteRenderbuffers(arg0,arg1);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTranslatef(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glTranslatef(arg0,arg1,arg2);
    String txt = new String("glTranslatef(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glTexParameterf(arg0,arg1,arg2);
    String txt = new String("glTexParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterf(int arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glPointParameterf(arg0,arg1);
    String txt = new String("glPointParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVBOElementEnabled()
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsVBOElementEnabled();
    String txt = new String("glIsVBOElementEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glClipPlanef(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glClipPlanef(arg0,arg1,arg2);
    String txt = new String("glClipPlanef(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindTexture(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBindTexture(arg0,arg1);
    String txt = new String("glBindTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorMask(boolean arg0,boolean arg1,boolean arg2,boolean arg3)
  {
        checkContext();
downstreamGLES1.glColorMask(arg0,arg1,arg2,arg3);
    String txt = new String("glColorMask(" +
    "<boolean>" +
    ", " +
    "<boolean>" +
    ", " +
    "<boolean>" +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendFunc(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBlendFunc(arg0,arg1);
    String txt = new String("glBlendFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexsvOES(short[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glDrawTexsvOES(arg0,arg1);
    String txt = new String("glDrawTexsvOES(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glUnmapBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glUnmapBuffer(arg0);
    String txt = new String("glUnmapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glBindFramebuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBindFramebuffer(arg0,arg1);
    String txt = new String("glBindFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4x(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glColor4x(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4x(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glDeleteFramebuffers(arg0,arg1);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexsOES(short arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGLES1.glDrawTexsOES(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawTexsOES(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClientActiveTexture(int arg0)
  {
        checkContext();
downstreamGLES1.glClientActiveTexture(arg0);
    String txt = new String("glClientActiveTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthRange(double arg0,double arg1)
  {
        checkContext();
downstreamGLES1.glDepthRange(arg0,arg1);
    String txt = new String("glDepthRange(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCurrentPaletteMatrix(int arg0)
  {
        checkContext();
downstreamGLES1.glCurrentPaletteMatrix(arg0);
    String txt = new String("glCurrentPaletteMatrix(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGLES1.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetFramebufferAttachmentParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetClipPlanex(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetClipPlanex(arg0,arg1);
    String txt = new String("glGetClipPlanex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGLES1.glColorPointer(arg0);
    String txt = new String("glColorPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexEnvxv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexEnvxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGLES1.glCopyTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCopyTexImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsRenderbuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsRenderbuffer(arg0);
    String txt = new String("glIsRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGLES1.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glTexImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glMaterialxv(arg0,arg1,arg2,arg3);
    String txt = new String("glMaterialxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetClipPlanef(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetClipPlanef(arg0,arg1,arg2);
    String txt = new String("glGetClipPlanef(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGLES1.glTexCoordPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoordPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableClientState(int arg0)
  {
        checkContext();
downstreamGLES1.glDisableClientState(arg0);
    String txt = new String("glDisableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexGenxv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexGenxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glDeleteBuffers(arg0,arg1);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClear(int arg0)
  {
        checkContext();
downstreamGLES1.glClear(arg0);
    String txt = new String("glClear(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnvxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexEnvxv(arg0,arg1,arg2);
    String txt = new String("glGetTexEnvxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonOffsetx(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glPolygonOffsetx(arg0,arg1);
    String txt = new String("glPolygonOffsetx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexParameterxv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glColorPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glColorPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGLES1.glIsBuffer(arg0);
    String txt = new String("glIsBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glCopyTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGLES1.glCopyTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCopyTexSubImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGenBuffers(arg0,arg1,arg2);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGeniv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexGeniv(arg0,arg1,arg2);
    String txt = new String("glTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGenRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEGLImageTargetRenderbufferStorageOES(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGLES1.glEGLImageTargetRenderbufferStorageOES(arg0,arg1);
    String txt = new String("glEGLImageTargetRenderbufferStorageOES(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixx(int[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glLoadMatrixx(arg0,arg1);
    String txt = new String("glLoadMatrixx(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3x(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glNormal3x(arg0,arg1,arg2);
    String txt = new String("glNormal3x(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetIntegerv(arg0,arg1,arg2);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean isFunctionAvailable(java.lang.String arg0)
  {
        return downstreamGLES1.isFunctionAvailable(arg0);
  }
  public  void glDrawArrays(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glDrawArrays(arg0,arg1,arg2);
    String txt = new String("glDrawArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  java.lang.Object getExtension(java.lang.String arg0)
  {
        return downstreamGLES1.getExtension(arg0);
  }
  public  void glClipPlanex(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glClipPlanex(arg0,arg1);
    String txt = new String("glClipPlanex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilMask(int arg0)
  {
        checkContext();
downstreamGLES1.glStencilMask(arg0);
    String txt = new String("glStencilMask(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGenFramebuffers(arg0,arg1,arg2);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogxv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glFogxv(arg0,arg1,arg2);
    String txt = new String("glFogxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetBufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetBufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGLES1.glNormalPointer(arg0,arg1,arg2);
    String txt = new String("glNormalPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetClipPlanef(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetClipPlanef(arg0,arg1);
    String txt = new String("glGetClipPlanef(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glDeleteTextures(arg0,arg1,arg2);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGenxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexGenxv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexGenxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSampleCoverage(float arg0,boolean arg1)
  {
        checkContext();
downstreamGLES1.glSampleCoverage(arg0,arg1);
    String txt = new String("glSampleCoverage(" +
    "<float>" +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPushMatrix()
  {
        checkContext();
downstreamGLES1.glPushMatrix();
    String txt = new String("glPushMatrix(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGLES1.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glReadPixels(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRotatef(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGLES1.glRotatef(arg0,arg1,arg2,arg3);
    String txt = new String("glRotatef(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonOffset(float arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glPolygonOffset(arg0,arg1);
    String txt = new String("glPolygonOffset(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelxv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glLightModelxv(arg0,arg1,arg2);
    String txt = new String("glLightModelxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glNormal3f(arg0,arg1,arg2);
    String txt = new String("glNormal3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleanv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetBooleanv(arg0,arg1);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetMaterialxv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMaterialxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  java.lang.Object getPlatformGLExtensions()
  {
        return downstreamGLES1.getPlatformGLExtensions();
  }
  public  void glPointParameterxv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glPointParameterxv(arg0,arg1,arg2);
    String txt = new String("glPointParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGLES1.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedTexImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glViewport(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glViewport(arg0,arg1,arg2,arg3);
    String txt = new String("glViewport(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFixedv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetFixedv(arg0,arg1);
    String txt = new String("glGetFixedv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGenTextures(arg0,arg1,arg2);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixx(int[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glMultMatrixx(arg0,arg1);
    String txt = new String("glMultMatrixx(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRotatex(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glRotatex(arg0,arg1,arg2,arg3);
    String txt = new String("glRotatex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetBoundBuffer(int arg0)
  {
        checkContext();
    int _res = downstreamGLES1.glGetBoundBuffer(arg0);
    String txt = new String("glGetBoundBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glBindRenderbuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glBindRenderbuffer(arg0,arg1);
    String txt = new String("glBindRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGLES1.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glTexSubImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexsvOES(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGLES1.glDrawTexsvOES(arg0);
    String txt = new String("glDrawTexsvOES(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetLightxv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLightxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthMask(boolean arg0)
  {
        checkContext();
downstreamGLES1.glDepthMask(arg0);
    String txt = new String("glDepthMask(" +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepthf(float arg0)
  {
        checkContext();
downstreamGLES1.glClearDepthf(arg0);
    String txt = new String("glClearDepthf(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawElements(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGLES1.glDrawElements(arg0,arg1,arg2,arg3);
    String txt = new String("glDrawElements(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSampleCoveragex(int arg0,boolean arg1)
  {
        checkContext();
downstreamGLES1.glSampleCoveragex(arg0,arg1);
    String txt = new String("glSampleCoveragex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisable(int arg0)
  {
        checkContext();
downstreamGLES1.glDisable(arg0);
    String txt = new String("glDisable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnviv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexEnviv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glLightxv(arg0,arg1,arg2,arg3);
    String txt = new String("glLightxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4ub(byte arg0,byte arg1,byte arg2,byte arg3)
  {
        checkContext();
downstreamGLES1.glColor4ub(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4ub(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepthx(int arg0)
  {
        checkContext();
downstreamGLES1.glClearDepthx(arg0);
    String txt = new String("glClearDepthx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexivOES(int[] arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glDrawTexivOES(arg0,arg1);
    String txt = new String("glDrawTexivOES(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPopMatrix()
  {
        checkContext();
downstreamGLES1.glPopMatrix();
    String txt = new String("glPopMatrix(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetRenderbufferParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetRenderbufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glMatrixIndexPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glMatrixIndexPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointSizePointerOES(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGLES1.glPointSizePointerOES(arg0,arg1,arg2);
    String txt = new String("glPointSizePointerOES(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelxv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glLightModelxv(arg0,arg1);
    String txt = new String("glLightModelxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightx(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glLightx(arg0,arg1,arg2);
    String txt = new String("glLightx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRenderbufferStorage(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glRenderbufferStorage(arg0,arg1,arg2,arg3);
    String txt = new String("glRenderbufferStorage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointSize(float arg0)
  {
        checkContext();
downstreamGLES1.glPointSize(arg0);
    String txt = new String("glPointSize(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGenxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexGenxv(arg0,arg1,arg2);
    String txt = new String("glGetTexGenxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLogicOp(int arg0)
  {
        checkContext();
downstreamGLES1.glLogicOp(arg0);
    String txt = new String("glLogicOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glMaterialfv(arg0,arg1,arg2,arg3);
    String txt = new String("glMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4x(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGLES1.glMultiTexCoord4x(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4x(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearColorx(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glClearColorx(arg0,arg1,arg2,arg3);
    String txt = new String("glClearColorx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGenTextures(arg0,arg1);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixx(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGLES1.glMultMatrixx(arg0);
    String txt = new String("glMultMatrixx(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepth(double arg0)
  {
        checkContext();
downstreamGLES1.glClearDepth(arg0);
    String txt = new String("glClearDepth(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrontFace(int arg0)
  {
        checkContext();
downstreamGLES1.glFrontFace(arg0);
    String txt = new String("glFrontFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFinish()
  {
        checkContext();
downstreamGLES1.glFinish();
    String txt = new String("glFinish(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterxv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glPointParameterxv(arg0,arg1);
    String txt = new String("glPointParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetMaterialxv(arg0,arg1,arg2);
    String txt = new String("glGetMaterialxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGLES1.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedTexSubImage2D(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  java.lang.String glGetString(int arg0)
  {
        checkContext();
    java.lang.String _res = downstreamGLES1.glGetString(arg0);
    String txt = new String("glGetString(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetFixedv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetFixedv(arg0,arg1,arg2);
    String txt = new String("glGetFixedv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glHint(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glHint(arg0,arg1);
    String txt = new String("glHint(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGLES1.glVertexPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetLightxv(arg0,arg1,arg2);
    String txt = new String("glGetLightxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glShadeModel(int arg0)
  {
        checkContext();
downstreamGLES1.glShadeModel(arg0);
    String txt = new String("glShadeModel(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetRenderbufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetRenderbufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glOrthof(float arg0,float arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGLES1.glOrthof(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glOrthof(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glQueryMatrixxOES(int[] arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
    int _res = downstreamGLES1.glQueryMatrixxOES(arg0,arg1,arg2,arg3);
    String txt = new String("glQueryMatrixxOES(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  int glQueryMatrixxOES(java.nio.IntBuffer arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
    int _res = downstreamGLES1.glQueryMatrixxOES(arg0,arg1);
    String txt = new String("glQueryMatrixxOES(" +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMaterialfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glMaterialfv(arg0,arg1,arg2);
    String txt = new String("glMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glLightxv(arg0,arg1,arg2);
    String txt = new String("glLightxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrustum(double arg0,double arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGLES1.glFrustum(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glFrustum(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnviv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexEnviv(arg0,arg1,arg2);
    String txt = new String("glGetTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawTexivOES(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGLES1.glDrawTexivOES(arg0);
    String txt = new String("glDrawTexivOES(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glLightf(arg0,arg1,arg2);
    String txt = new String("glLightf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glCheckFramebufferStatus(int arg0)
  {
        checkContext();
    int _res = downstreamGLES1.glCheckFramebufferStatus(arg0);
    String txt = new String("glCheckFramebufferStatus(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexGenxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexGenxv(arg0,arg1,arg2);
    String txt = new String("glTexGenxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGenfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexGenfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnvfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexEnvfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glScissor(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glScissor(arg0,arg1,arg2,arg3);
    String txt = new String("glScissor(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrustumx(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGLES1.glFrustumx(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glFrustumx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glDeleteBuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glMaterialxv(arg0,arg1,arg2);
    String txt = new String("glMaterialxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelStorei(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glPixelStorei(arg0,arg1);
    String txt = new String("glPixelStorei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexEnvfv(arg0,arg1,arg2);
    String txt = new String("glTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelf(int arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glLightModelf(arg0,arg1);
    String txt = new String("glLightModelf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGeniv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexGeniv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGenRenderbuffers(arg0,arg1);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrustumf(float arg0,float arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGLES1.glFrustumf(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glFrustumf(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetError()
  {
        checkContext();
    int _res = downstreamGLES1.glGetError();
    String txt = new String("glGetError(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glStencilFunc(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glStencilFunc(arg0,arg1,arg2);
    String txt = new String("glStencilFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnvxv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetTexEnvxv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexEnvxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterxv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGLES1.glTexParameterxv(arg0,arg1,arg2);
    String txt = new String("glTexParameterxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGenBuffers(arg0,arg1);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  javax.media.opengl.GLContext getContext()
  {
        return downstreamGLES1.getContext();
  }
  public  void glGetBooleanv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glGetBooleanv(arg0,arg1,arg2);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGetIntegerv(arg0,arg1);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthFunc(int arg0)
  {
        checkContext();
downstreamGLES1.glDepthFunc(arg0);
    String txt = new String("glDepthFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glTexEnvfv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixx(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGLES1.glLoadMatrixx(arg0);
    String txt = new String("glLoadMatrixx(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGLES1.glTexCoordPointer(arg0);
    String txt = new String("glTexCoordPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glScalex(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glScalex(arg0,arg1,arg2);
    String txt = new String("glScalex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogx(int arg0,int arg1)
  {
        checkContext();
downstreamGLES1.glFogx(arg0,arg1);
    String txt = new String("glFogx(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogf(int arg0,float arg1)
  {
        checkContext();
downstreamGLES1.glFogf(arg0,arg1);
    String txt = new String("glFogf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glScalef(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGLES1.glScalef(arg0,arg1,arg2);
    String txt = new String("glScalef(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogxv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glFogxv(arg0,arg1);
    String txt = new String("glFogxv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glGetBufferParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetBufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glGenFramebuffers(arg0,arg1);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferData(int arg0,int arg1,java.nio.Buffer arg2,int arg3)
  {
        checkContext();
downstreamGLES1.glBufferData(arg0,arg1,arg2,arg3);
    String txt = new String("glBufferData(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGLES1.glDeleteTextures(arg0,arg1);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClipPlanex(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGLES1.glClipPlanex(arg0,arg1,arg2);
    String txt = new String("glClipPlanex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGLES1.glMultiTexCoord4f(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGenfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexGenfv(arg0,arg1,arg2);
    String txt = new String("glGetTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnvfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGLES1.glGetTexEnvfv(arg0,arg1,arg2);
    String txt = new String("glGetTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("DebugGLES1 [ implementing javax.media.opengl.GLES1,\n\t");
    sb.append(" downstream: "+downstreamGLES1.toString()+"\n\t]");
    return sb.toString();
  }
  private void checkGLGetError(String caller)
  {
    // Debug code to make sure the pipeline is working; leave commented out unless testing this class
    //System.err.println("Checking for GL errors after call to " + caller);

    int err = downstreamGLES1.glGetError();
    if (err == GL_NO_ERROR) { return; }

    StringBuffer buf = new StringBuffer(Thread.currentThread()+
      " glGetError() returned the following error codes after a call to " + caller + ": ");

    // Loop repeatedly to allow for distributed GL implementations,
    // as detailed in the glGetError() specification
    int recursionDepth = 10;
    do {
      switch (err) {
        case GL_INVALID_ENUM: buf.append("GL_INVALID_ENUM "); break;
        case GL_INVALID_VALUE: buf.append("GL_INVALID_VALUE "); break;
        case GL_INVALID_OPERATION: buf.append("GL_INVALID_OPERATION "); break;
        case GL_STACK_OVERFLOW: buf.append("GL_STACK_OVERFLOW "); break;
        case GL_STACK_UNDERFLOW: buf.append("GL_STACK_UNDERFLOW "); break;
        case GL_OUT_OF_MEMORY: buf.append("GL_OUT_OF_MEMORY "); break;
        case GL_NO_ERROR: throw new InternalError("Should not be treating GL_NO_ERROR as error");
        default: buf.append("Unknown glGetError() return value: ");
      }
      buf.append("( " + err + " 0x"+Integer.toHexString(err).toUpperCase() + "), ");
    } while ((--recursionDepth >= 0) && (err = downstreamGLES1.glGetError()) != GL_NO_ERROR);
    throw new GLException(buf.toString());
  }
  private void checkContext() {
    GLContext currentContext = GLContext.getCurrent();
    if (currentContext == null) {
      throw new GLException("No OpenGL context is current on this thread");
    }
    if ((_context != null) && (_context != currentContext)) {
      throw new GLException("This GL object is being incorrectly used with a different GLContext than that which created it");
    }
  }
  private GLContext _context;

  private GLES1 downstreamGLES1;
} // end class DebugGLES1
