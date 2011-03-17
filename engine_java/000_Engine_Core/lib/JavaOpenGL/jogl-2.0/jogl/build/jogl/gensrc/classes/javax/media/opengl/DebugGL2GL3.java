package javax.media.opengl;

import java.io.*;
import javax.media.opengl.*;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLBase;
import javax.media.opengl.GL;
import com.sun.gluegen.runtime.*;

/** <P> Composable pipeline which wraps an underlying {@link GL} implementation,
    providing error checking after each OpenGL method call. If an error occurs,
    causes a {@link GLException} to be thrown at exactly the point of failure.
    Sample code which installs this pipeline: </P>

<PRE>
     GL gl = drawable.setGL(new DebugGL(drawable.getGL()));
</PRE>
*/
public class DebugGL2GL3 implements javax.media.opengl.GLBase, javax.media.opengl.GL, javax.media.opengl.GL2ES2, javax.media.opengl.GL2GL3
{
  public static final boolean DEBUG = com.sun.opengl.impl.Debug.debug("DebugGL2GL3");
  public DebugGL2GL3(GL2GL3 downstreamGL2GL3)
  {
    if (downstreamGL2GL3 == null) {
      throw new IllegalArgumentException("null downstreamGL2GL3");
    }
    this.downstreamGL2GL3 = downstreamGL2GL3;
    // Fetch GLContext object for better error checking (if possible)
    _context = downstreamGL2GL3.getContext();
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
    return false;
  }
  public boolean isGLES2() {
    return false;
  }
  public boolean isGL2ES1() {
    return false;
  }
  public boolean isGL2ES2() {
    return true;
  }
  public boolean isGL2GL3() {
    return true;
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
    throw new GLException("Not a GLES1 implementation");
  }
  public javax.media.opengl.GLES2 getGLES2() {
    throw new GLException("Not a GLES2 implementation");
  }
  public javax.media.opengl.GL2ES1 getGL2ES1() {
    throw new GLException("Not a GL2ES1 implementation");
  }
  public javax.media.opengl.GL2ES2 getGL2ES2() {
    return this;
  }
  public javax.media.opengl.GL2GL3 getGL2GL3() {
    return this;
  }
  public GLProfile getGLProfile() {
    return downstreamGL2GL3.getGLProfile();
  }
  public  void glBlendEquationSeparate(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBlendEquationSeparate(arg0,arg1);
    String txt = new String("glBlendEquationSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveUniform(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2GL3.glGetActiveUniform(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glGetActiveUniform(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClear(int arg0)
  {
        checkContext();
downstreamGL2GL3.glClear(arg0);
    String txt = new String("glClear(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2ui(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2ui(arg0,arg1,arg2);
    String txt = new String("glUniform2ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetUniformui64vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableClientState(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDisableClientState(arg0);
    String txt = new String("glDisableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2GL3.glTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glTexImage1D(" +
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
  public  void glUniformMatrix3x2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3x2fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix3x2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribdv(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribdv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribdv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4uiv(arg0,arg1);
    String txt = new String("glVertexAttrib4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderInfoLog(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetShaderInfoLog(arg0,arg1,arg2,arg3);
    String txt = new String("glGetShaderInfoLog(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glClearBufferuiv(arg0,arg1,arg2);
    String txt = new String("glClearBufferuiv(" +
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
downstreamGL2GL3.glBlendFuncSeparate(arg0,arg1,arg2,arg3);
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
  public  boolean glIsTexture(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsTexture(arg0);
    String txt = new String("glIsTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glCompressedTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCompressedTexSubImage1D(" +
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
  public  void glBindBuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBindBuffer(arg0,arg1);
    String txt = new String("glBindBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nbv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nbv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nbv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform2uiv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform2uiv(" +
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
  public  void glUniform4ui(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniform4ui(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniform4ui(" +
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
  public  void glTexParameterIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameterIuiv(arg0,arg1,arg2);
    String txt = new String("glTexParameterIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteTextures(arg0,arg1);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenFramebuffers(arg0,arg1);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindAttribLocation(int arg0,int arg1,java.lang.String arg2)
  {
        checkContext();
downstreamGL2GL3.glBindAttribLocation(arg0,arg1,arg2);
    String txt = new String("glBindAttribLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedTexImage3D(" +
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
  public  void glDrawRangeElements(int arg0,int arg1,int arg2,int arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2GL3.glDrawRangeElements(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glDrawRangeElements(" +
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
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawArrays(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDrawArrays(arg0,arg1,arg2);
    String txt = new String("glDrawArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedMakeBufferNonResidentNV(int arg0)
  {
        checkContext();
downstreamGL2GL3.glNamedMakeBufferNonResidentNV(arg0);
    String txt = new String("glNamedMakeBufferNonResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2GL3.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glUniform3iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform3iv(arg0,arg1,arg2);
    String txt = new String("glUniform3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetQueryObjectiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjectiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerv(arg0,arg1);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glUniform2fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2fv(arg0,arg1,arg2);
    String txt = new String("glUniform2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glPointParameterfv(arg0,arg1);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2iv(arg0,arg1,arg2);
    String txt = new String("glUniform2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameteriv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glPointParameteriv(arg0,arg1);
    String txt = new String("glPointParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisablei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glDisablei(arg0,arg1);
    String txt = new String("glDisablei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilMask(int arg0)
  {
        checkContext();
downstreamGL2GL3.glStencilMask(arg0);
    String txt = new String("glStencilMask(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniformui64vNV(arg0,arg1,arg2);
    String txt = new String("glUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsProgram(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsProgram(arg0);
    String txt = new String("glIsProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glBlendColor(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2GL3.glBlendColor(arg0,arg1,arg2,arg3);
    String txt = new String("glBlendColor(" +
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
  public  void glUniformMatrix3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetUniformfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformfv(" +
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
  public  void glGetUniformiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetUniformiv(arg0,arg1,arg2);
    String txt = new String("glGetUniformiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGetFloatv(arg0,arg1);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteVertexArrays(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteVertexArrays(arg0,arg1,arg2);
    String txt = new String("glDeleteVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterIiv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribiv(" +
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
  public  void glGetBooleani_v(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetBooleani_v(arg0,arg1,arg2,arg3);
    String txt = new String("glGetBooleani_v(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribIuiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform4uiv(arg0,arg1,arg2);
    String txt = new String("glUniform4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveUniform(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2GL3.glGetActiveUniform(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glGetActiveUniform(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCompressedTexImage1D(" +
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
  public  void glFramebufferRenderbuffer(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glFramebufferRenderbuffer(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4Nusv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nusv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4bv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4bv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2GL3.glCopyTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCopyTexImage1D(" +
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
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawElements(int arg0,java.nio.IntBuffer arg1,int arg2,java.nio.Buffer[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glMultiDrawElements(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiDrawElements(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[Ljava.nio.Buffer;>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenVertexArrays(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenVertexArrays(arg0,arg1);
    String txt = new String("glGenVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderInfoLog(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glGetShaderInfoLog(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetShaderInfoLog(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2GL3.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glVertexAttrib3fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3fv(arg0,arg1);
    String txt = new String("glVertexAttrib3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1fv(arg0,arg1);
    String txt = new String("glVertexAttrib1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribfv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform4uiv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform4uiv(" +
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
  public  void glVertexAttrib4Nbv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nbv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nbv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribIFormatNV(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribIFormatNV(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribIFormatNV(" +
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
  public  void glTexCoordFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glTexCoordFormatNV(arg0,arg1,arg2);
    String txt = new String("glTexCoordFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrontFace(int arg0)
  {
        checkContext();
downstreamGL2GL3.glFrontFace(arg0);
    String txt = new String("glFrontFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTransformFeedbackVarying(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2GL3.glGetTransformFeedbackVarying(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glGetTransformFeedbackVarying(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMakeBufferNonResidentNV(int arg0)
  {
        checkContext();
downstreamGL2GL3.glMakeBufferNonResidentNV(arg0);
    String txt = new String("glMakeBufferNonResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3ui(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3ui(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3ui(" +
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
  public  void glUniformMatrix3x2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3x2fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix3x2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawElements(int arg0,int[] arg1,int arg2,int arg3,java.nio.Buffer[] arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glMultiDrawElements(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMultiDrawElements(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[Ljava.nio.Buffer;>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenVertexArrays(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenVertexArrays(arg0,arg1,arg2);
    String txt = new String("glGenVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorMask(boolean arg0,boolean arg1,boolean arg2,boolean arg3)
  {
        checkContext();
downstreamGL2GL3.glColorMask(arg0,arg1,arg2,arg3);
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
  public  void glNamedMakeBufferResidentNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glNamedMakeBufferResidentNV(arg0,arg1);
    String txt = new String("glNamedMakeBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferData(int arg0,int arg1,java.nio.Buffer arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glBufferData(arg0,arg1,arg2,arg3);
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
  public  void glGetVertexAttribiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteVertexArrays(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteVertexArrays(arg0,arg1);
    String txt = new String("glDeleteVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterIiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexParameterIiv(" +
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
  public  void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribPointer(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glVertexAttribPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform2fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform2fv(" +
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
  public  void glPointParameterfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glPointParameterfv(arg0,arg1,arg2);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribIuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribIuiv(" +
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
  public  void glVertexAttrib4usv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4usv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetUniformiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformiv(" +
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
  public  void glUniform1ui(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glUniform1ui(arg0,arg1);
    String txt = new String("glUniform1ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2GL3.glTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glTexImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameterui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetBufferParameterui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glTexParameterIuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexParameterIuiv(" +
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
  public  void glDeleteTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteTextures(arg0,arg1,arg2);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenFramebuffers(arg0,arg1,arg2);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform2iv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform2iv(" +
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
  public  void glStencilFunc(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glStencilFunc(arg0,arg1,arg2);
    String txt = new String("glStencilFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameteriv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glPointParameteriv(arg0,arg1,arg2);
    String txt = new String("glPointParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,long arg10)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glCompressedTexSubImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleani_v(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetBooleani_v(arg0,arg1,arg2);
    String txt = new String("glGetBooleani_v(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,long arg7)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glGetIntegerui64i_vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerui64i_vNV(arg0,arg1,arg2);
    String txt = new String("glGetIntegerui64i_vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGetFloatv(arg0,arg1,arg2);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2GL3.glCopyTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glUniformMatrix3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3iv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3iv(" +
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
  public  void glGetQueryObjectiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetQueryObjectiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetQueryObjectiv(" +
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
  public  void glGetIntegerv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerv(arg0,arg1,arg2);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetUniformfv(arg0,arg1,arg2);
    String txt = new String("glGetUniformfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2fv(arg0,arg1);
    String txt = new String("glVertexAttrib2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4bv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4bv(arg0,arg1);
    String txt = new String("glVertexAttrib4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendFunc(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBlendFunc(arg0,arg1);
    String txt = new String("glBlendFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsBufferResidentNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsBufferResidentNV(arg0);
    String txt = new String("glIsBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib1fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetDoublev(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGetDoublev(arg0,arg1);
    String txt = new String("glGetDoublev(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTransformFeedbackVarying(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2GL3.glGetTransformFeedbackVarying(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glGetTransformFeedbackVarying(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribfv(" +
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
  public  void glUniform2uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2uiv(arg0,arg1,arg2);
    String txt = new String("glUniform2uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glReleaseShaderCompiler()
  {
        checkContext();
downstreamGL2GL3.glReleaseShaderCompiler();
    String txt = new String("glReleaseShaderCompiler(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointSize(float arg0)
  {
        checkContext();
downstreamGL2GL3.glPointSize(arg0);
    String txt = new String("glPointSize(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glClearBufferuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glClearBufferuiv(" +
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
  public  java.nio.ByteBuffer glMapBufferRange(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2GL3.glMapBufferRange(arg0,arg1,arg2,arg3);
    String txt = new String("glMapBufferRange(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glClearBufferiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glClearBufferiv(arg0,arg1,arg2);
    String txt = new String("glClearBufferiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVBOArrayEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsVBOArrayEnabled();
    String txt = new String("glIsVBOArrayEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniform1fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform1fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform1fv(" +
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
  public  void glClearBufferfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glClearBufferfv(arg0,arg1,arg2);
    String txt = new String("glClearBufferfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glUniform1i(arg0,arg1);
    String txt = new String("glUniform1i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform1iv(arg0,arg1,arg2);
    String txt = new String("glUniform1iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilOpSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glStencilOpSeparate(arg0,arg1,arg2,arg3);
    String txt = new String("glStencilOpSeparate(" +
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
  public  void glGetNamedBufferParameterui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetNamedBufferParameterui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetNamedBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetUniformuiv(arg0,arg1,arg2);
    String txt = new String("glGetUniformuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glCopyTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glCopyTexSubImage1D(" +
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
  public  void glTexParameterIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameterIiv(arg0,arg1,arg2);
    String txt = new String("glTexParameterIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4x3fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix4x3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetShaderiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetShaderiv(" +
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
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glUniform3fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3fv(" +
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
  public  void glPrimitiveRestartIndex(int arg0)
  {
        checkContext();
downstreamGL2GL3.glPrimitiveRestartIndex(arg0);
    String txt = new String("glPrimitiveRestartIndex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawElementsInstanced(int arg0,int arg1,int arg2,java.nio.Buffer arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glDrawElementsInstanced(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawElementsInstanced(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4x2fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix4x2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nubv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nubv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glColorFormatNV(arg0,arg1,arg2);
    String txt = new String("glColorFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilOp(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glStencilOp(arg0,arg1,arg2);
    String txt = new String("glStencilOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetQueryObjectuiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjectuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform4iv(arg0,arg1,arg2);
    String txt = new String("glUniform4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexImage(int arg0,int arg1,int arg2,int arg3,long arg4)
  {
        checkContext();
downstreamGL2GL3.glGetTexImage(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform4fv(arg0,arg1,arg2);
    String txt = new String("glUniform4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2GL3.glCopyTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glUniform(javax.media.opengl.GLUniformData arg0)
  {
        checkContext();
downstreamGL2GL3.glUniform(arg0);
    String txt = new String("glUniform(" +
    "<javax.media.opengl.GLUniformData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteFramebuffers(arg0,arg1);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerui64vNV(int arg0,com.sun.gluegen.runtime.PointerBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerui64vNV(arg0,arg1);
    String txt = new String("glGetIntegerui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3f(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3f(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetAttribLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2GL3.glGetAttribLocation(arg0,arg1);
    String txt = new String("glGetAttribLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformui64vNV(int arg0,int arg1,int arg2,long[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glProgramUniformui64vNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribPointer(arg0);
    String txt = new String("glVertexAttribPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3sv(arg0,arg1);
    String txt = new String("glVertexAttrib3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3i(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3i(" +
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
  public  void glTexParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameteri(arg0,arg1,arg2);
    String txt = new String("glTexParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsRenderbuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsRenderbuffer(arg0);
    String txt = new String("glIsRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetDoublev(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGetDoublev(arg0,arg1,arg2);
    String txt = new String("glGetDoublev(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetCompressedTexImage(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetCompressedTexImage(arg0,arg1,arg2);
    String txt = new String("glGetCompressedTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLineWidth(float arg0)
  {
        checkContext();
downstreamGL2GL3.glLineWidth(arg0);
    String txt = new String("glLineWidth(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsBuffer(arg0);
    String txt = new String("glIsBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glDeleteQueries(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteQueries(arg0,arg1);
    String txt = new String("glDeleteQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonOffset(float arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glPolygonOffset(arg0,arg1);
    String txt = new String("glPolygonOffset(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindFragDataLocation(int arg0,int arg1,java.lang.String arg2)
  {
        checkContext();
downstreamGL2GL3.glBindFragDataLocation(arg0,arg1,arg2);
    String txt = new String("glBindFragDataLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTextureFace(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTextureFace(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glFramebufferTextureFace(" +
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
  public  void glGenQueries(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenQueries(arg0,arg1);
    String txt = new String("glGenQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColorFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glSecondaryColorFormatNV(arg0,arg1,arg2);
    String txt = new String("glSecondaryColorFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1f(int arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glUniform1f(arg0,arg1);
    String txt = new String("glUniform1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  java.nio.ByteBuffer glMapBuffer(int arg0,int arg1)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2GL3.glMapBuffer(arg0,arg1);
    String txt = new String("glMapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glEndConditionalRender()
  {
        checkContext();
downstreamGL2GL3.glEndConditionalRender();
    String txt = new String("glEndConditionalRender(" +
    ")");
    checkGLGetError( txt );
  }
  public  java.nio.ByteBuffer glAllocateMemoryNV(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2GL3.glAllocateMemoryNV(arg0,arg1,arg2,arg3);
    String txt = new String("glAllocateMemoryNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  java.lang.String glGetString(int arg0)
  {
        checkContext();
    java.lang.String _res = downstreamGL2GL3.glGetString(arg0);
    String txt = new String("glGetString(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glShaderSource(int arg0,int arg1,java.lang.String[] arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glShaderSource(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glShaderSource(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[Ljava.lang.String;>" +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindRenderbuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBindRenderbuffer(arg0,arg1);
    String txt = new String("glBindRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nsv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nsv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nsv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean isFunctionAvailable(java.lang.String arg0)
  {
        return downstreamGL2GL3.isFunctionAvailable(arg0);
  }
  public  void glUniform1fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform1fv(arg0,arg1,arg2);
    String txt = new String("glUniform1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glClearBufferfv(arg0,arg1,arg2,arg3);
    String txt = new String("glClearBufferfv(" +
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
  public  void glHint(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glHint(arg0,arg1);
    String txt = new String("glHint(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteQueries(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteQueries(arg0,arg1,arg2);
    String txt = new String("glDeleteQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRenderbufferStorageMultisample(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glRenderbufferStorageMultisample(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glRenderbufferStorageMultisample(" +
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
  public  void glPixelStoref(int arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glPixelStoref(arg0,arg1);
    String txt = new String("glPixelStoref(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderSource(int arg0,int arg1,java.lang.String[] arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glShaderSource(arg0,arg1,arg2,arg3);
    String txt = new String("glShaderSource(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[Ljava.lang.String;>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1s(int arg0,short arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1s(arg0,arg1);
    String txt = new String("glVertexAttrib1s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenQueries(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenQueries(arg0,arg1,arg2);
    String txt = new String("glGenQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,long arg10)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glTexSubImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribFormatNV(int arg0,int arg1,int arg2,boolean arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribFormatNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttribFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nubv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nubv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRenderbufferStorage(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glRenderbufferStorage(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib2dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2dv(arg0,arg1);
    String txt = new String("glVertexAttrib2dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4x2fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix4x2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexBuffer(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glTexBuffer(arg0,arg1,arg2);
    String txt = new String("glTexBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform3fv(arg0,arg1,arg2);
    String txt = new String("glUniform3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3dv(arg0,arg1);
    String txt = new String("glVertexAttrib3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix4x3fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix4x3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1dv(arg0,arg1);
    String txt = new String("glVertexAttrib1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribdv(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribdv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribdv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform4fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform4fv(" +
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
  public  java.lang.String glGetStringi(int arg0,int arg1)
  {
        checkContext();
    java.lang.String _res = downstreamGL2GL3.glGetStringi(arg0,arg1);
    String txt = new String("glGetStringi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetAttachedShaders(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetAttachedShaders(arg0,arg1,arg2,arg3);
    String txt = new String("glGetAttachedShaders(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetAttachedShaders(int arg0,int arg1,int[] arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glGetAttachedShaders(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetAttachedShaders(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClampColor(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glClampColor(arg0,arg1);
    String txt = new String("glClampColor(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glCheckFramebufferStatus(int arg0)
  {
        checkContext();
    int _res = downstreamGL2GL3.glCheckFramebufferStatus(arg0);
    String txt = new String("glCheckFramebufferStatus(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glCopyTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2GL3.glCopyTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCopyTexSubImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean hasGLSL()
  {
        return downstreamGL2GL3.hasGLSL();
  }
  public  void glTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glTexSubImage1D(" +
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
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetShaderiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetShaderiv(arg0,arg1,arg2);
    String txt = new String("glGetShaderiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameterf(arg0,arg1,arg2);
    String txt = new String("glTexParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3s(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3s(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glTexParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glDeleteProgram(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDeleteProgram(arg0);
    String txt = new String("glDeleteProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetQueryObjectuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetQueryObjectuiv(" +
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
  public  void glUniform4iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform4iv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform4iv(" +
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
  public  void glDeleteFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteFramebuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsQuery(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsQuery(arg0);
    String txt = new String("glIsQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glPixelStorei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glPixelStorei(arg0,arg1);
    String txt = new String("glPixelStorei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glClearBufferiv(arg0,arg1,arg2,arg3);
    String txt = new String("glClearBufferiv(" +
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
  public  void glDrawElements(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2GL3.glDrawElements(arg0,arg1,arg2,arg3);
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
  public  void glTexParameterIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glTexParameterIiv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexParameterIiv(" +
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
  public  void glColorMaski(int arg0,boolean arg1,boolean arg2,boolean arg3,boolean arg4)
  {
        checkContext();
downstreamGL2GL3.glColorMaski(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glColorMaski(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
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
  public  void glBeginConditionalRender(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBeginConditionalRender(arg0,arg1);
    String txt = new String("glBeginConditionalRender(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform1iv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform1iv(" +
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
  public  void glUniform2i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2i(arg0,arg1,arg2);
    String txt = new String("glUniform2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform2f(arg0,arg1,arg2);
    String txt = new String("glUniform2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetUniformuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformuiv(" +
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
        return downstreamGL2GL3.getPlatformGLExtensions();
  }
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glCompressedTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glCompressedTexSubImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2x3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2x3fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix2x3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glGetTexLevelParameterfv(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexLevelParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexLevelParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenRenderbuffers(arg0,arg1);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepth(double arg0)
  {
        checkContext();
downstreamGL2GL3.glClearDepth(arg0);
    String txt = new String("glClearDepth(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2x4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2x4fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix2x4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterIuiv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nuiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nuiv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenBuffers(arg0,arg1);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTextureLayer(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTextureLayer(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glFramebufferTextureLayer(" +
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
  public  void glGetTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,long arg9)
  {
        checkContext();
downstreamGL2GL3.glTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glTexImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsEnabled(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsEnabled(arg0);
    String txt = new String("glIsEnabled(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib3sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepthf(float arg0)
  {
        checkContext();
downstreamGL2GL3.glClearDepthf(arg0);
    String txt = new String("glClearDepthf(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawArrays(int arg0,int[] arg1,int arg2,int[] arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glMultiDrawArrays(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMultiDrawArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawArrays(int arg0,java.nio.IntBuffer arg1,java.nio.IntBuffer arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glMultiDrawArrays(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiDrawArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexLevelParameteriv(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glGetTexLevelParameteriv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTexLevelParameteriv(" +
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
  public  int glCreateShader(int arg0)
  {
        checkContext();
    int _res = downstreamGL2GL3.glCreateShader(arg0);
    String txt = new String("glCreateShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMakeBufferResidentNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glMakeBufferResidentNV(arg0,arg1);
    String txt = new String("glMakeBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4ubv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4ubv(arg0,arg1);
    String txt = new String("glVertexAttrib4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthMask(boolean arg0)
  {
        checkContext();
downstreamGL2GL3.glDepthMask(arg0);
    String txt = new String("glDepthMask(" +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferfi(int arg0,int arg1,float arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glClearBufferfi(arg0,arg1,arg2,arg3);
    String txt = new String("glClearBufferfi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTransformFeedbackVaryings(int arg0,int arg1,java.lang.String[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glTransformFeedbackVaryings(arg0,arg1,arg2,arg3);
    String txt = new String("glTransformFeedbackVaryings(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[Ljava.lang.String;>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindVertexArray(int arg0)
  {
        checkContext();
downstreamGL2GL3.glBindVertexArray(arg0);
    String txt = new String("glBindVertexArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawBuffer(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDrawBuffer(arg0);
    String txt = new String("glDrawBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferSubData(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetBufferSubData(arg0,arg1,arg2,arg3);
    String txt = new String("glGetBufferSubData(" +
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
  public  void glDeleteBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteBuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlush()
  {
        checkContext();
downstreamGL2GL3.glFlush();
    String txt = new String("glFlush(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3f(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3f(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLinkProgram(int arg0)
  {
        checkContext();
downstreamGL2GL3.glLinkProgram(arg0);
    String txt = new String("glLinkProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGenTextures(arg0,arg1);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTessellationFactorAMD(float arg0)
  {
        checkContext();
downstreamGL2GL3.glTessellationFactorAMD(arg0);
    String txt = new String("glTessellationFactorAMD(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegeri_v(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetIntegeri_v(arg0,arg1,arg2);
    String txt = new String("glGetIntegeri_v(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterfv(arg0,arg1,arg2,arg3);
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
  public  void glUniform1uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform1uiv(arg0,arg1,arg2);
    String txt = new String("glUniform1uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4dv(arg0,arg1);
    String txt = new String("glVertexAttrib4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBeginTransformFeedback(int arg0)
  {
        checkContext();
downstreamGL2GL3.glBeginTransformFeedback(arg0);
    String txt = new String("glBeginTransformFeedback(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilFuncSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glStencilFuncSeparate(arg0,arg1,arg2,arg3);
    String txt = new String("glStencilFuncSeparate(" +
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
  public  void glUniformMatrix3x4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3x4fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix3x4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEndTransformFeedback()
  {
        checkContext();
downstreamGL2GL3.glEndTransformFeedback();
    String txt = new String("glEndTransformFeedback(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glProgramParameteri(arg0,arg1,arg2);
    String txt = new String("glProgramParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetProgramiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramiv(" +
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
  public  java.lang.Object getExtension(java.lang.String arg0)
  {
        return downstreamGL2GL3.getExtension(arg0);
  }
  public  void glDisableVertexAttribArray(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDisableVertexAttribArray(arg0);
    String txt = new String("glDisableVertexAttribArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1sv(arg0,arg1);
    String txt = new String("glVertexAttrib1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribPointer(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glVertexAttribPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawArraysInstanced(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glDrawArraysInstanced(arg0,arg1,arg2,arg3);
    String txt = new String("glDrawArraysInstanced(" +
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
  public  void glDisable(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDisable(arg0);
    String txt = new String("glDisable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsFramebuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsFramebuffer(arg0);
    String txt = new String("glIsFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetVertexAttribIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribIiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribIiv(" +
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
  public  void glDrawBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDrawBuffers(arg0,arg1,arg2);
    String txt = new String("glDrawBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nub(int arg0,byte arg1,byte arg2,byte arg3,byte arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nub(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4Nub(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
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
  public  void glVertexAttrib1f(int arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1f(arg0,arg1);
    String txt = new String("glVertexAttrib1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glUniform3uiv(arg0,arg1,arg2);
    String txt = new String("glUniform3uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glIndexFormatNV(arg0,arg1);
    String txt = new String("glIndexFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2x4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2x4fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix2x4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetVertexAttribIiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDrawBuffers(arg0,arg1);
    String txt = new String("glDrawBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glUnmapBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glUnmapBuffer(arg0);
    String txt = new String("glUnmapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetTexLevelParameterfv(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glGetTexLevelParameterfv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTexLevelParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean isExtensionAvailable(java.lang.String arg0)
  {
        return downstreamGL2GL3.isExtensionAvailable(arg0);
  }
  public  void glUniformMatrix2x3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix2x3fv(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix2x3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLogicOp(int arg0)
  {
        checkContext();
downstreamGL2GL3.glLogicOp(arg0);
    String txt = new String("glLogicOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform3uiv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3uiv(" +
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
  public  void glVertexAttrib3d(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib3d(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedBufferParameterui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetNamedBufferParameterui64vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameteri(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glPointParameteri(arg0,arg1);
    String txt = new String("glPointParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindTexture(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBindTexture(arg0,arg1);
    String txt = new String("glBindTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2GL3.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glGetIntegeri_v(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetIntegeri_v(arg0,arg1,arg2,arg3);
    String txt = new String("glGetIntegeri_v(" +
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
  public  void glDepthRangef(float arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glDepthRangef(arg0,arg1);
    String txt = new String("glDepthRangef(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlitFramebuffer(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9)
  {
        checkContext();
downstreamGL2GL3.glBlitFramebuffer(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glBlitFramebuffer(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4ubv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4ubv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniform1uiv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform1uiv(" +
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
  public  void glEdgeFlagFormatNV(int arg0)
  {
        checkContext();
downstreamGL2GL3.glEdgeFlagFormatNV(arg0);
    String txt = new String("glEdgeFlagFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnablei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glEnablei(arg0,arg1);
    String txt = new String("glEnablei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenTextures(arg0,arg1,arg2);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVertexArray(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsVertexArray(arg0);
    String txt = new String("glIsVertexArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glClearColor(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2GL3.glClearColor(arg0,arg1,arg2,arg3);
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
  public  void glCompressedTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCompressedTexImage1D(" +
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
  public  void glGetIntegerui64vNV(int arg0,long[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetIntegerui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4sv(arg0,arg1);
    String txt = new String("glVertexAttrib4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetProgramiv(arg0,arg1,arg2);
    String txt = new String("glGetProgramiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTessellationModeAMD(int arg0)
  {
        checkContext();
downstreamGL2GL3.glTessellationModeAMD(arg0);
    String txt = new String("glTessellationModeAMD(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompileShader(int arg0)
  {
        checkContext();
downstreamGL2GL3.glCompileShader(arg0);
    String txt = new String("glCompileShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexLevelParameteriv(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexLevelParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexLevelParameteriv(" +
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
  public  void glProgramUniformui64vNV(int arg0,int arg1,int arg2,com.sun.gluegen.runtime.PointerBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glProgramUniformui64vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCompressedTexSubImage1D(" +
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
  public  void glDepthRange(double arg0,double arg1)
  {
        checkContext();
downstreamGL2GL3.glDepthRange(arg0,arg1);
    String txt = new String("glDepthRange(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glTexParameterfv(arg0,arg1,arg2,arg3);
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
  public  void glUniform4i(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glUniform4i(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniform4i(" +
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
  public  void glUniformMatrix3x4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformMatrix3x4fv(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix3x4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,long arg7)
  {
        checkContext();
downstreamGL2GL3.glTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glTexImage1D(" +
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
  public  void glVertexAttrib4Nsv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nsv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nsv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1d(int arg0,double arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib1d(arg0,arg1);
    String txt = new String("glVertexAttrib1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2GL3.glUniform4f(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniform4f(" +
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
  public  void glDeleteBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteBuffers(arg0,arg1);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2GL3.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glFogCoordFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glFogCoordFormatNV(arg0,arg1);
    String txt = new String("glFogCoordFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameterIuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexParameterIuiv(" +
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
  public  void glVertexAttrib4Nuiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nuiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenBuffers(arg0,arg1,arg2);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetTexParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glPointParameterf(int arg0,float arg1)
  {
        checkContext();
downstreamGL2GL3.glPointParameterf(arg0,arg1);
    String txt = new String("glPointParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glDepthFunc(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDepthFunc(arg0);
    String txt = new String("glDepthFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2s(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2s(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGenRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetError()
  {
        checkContext();
    int _res = downstreamGL2GL3.glGetError();
    String txt = new String("glGetError(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glCompressedTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2GL3.glCompressedTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedTexImage3D(" +
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
  public  void glVertexAttrib2sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2sv(arg0,arg1);
    String txt = new String("glVertexAttrib2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindFramebuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBindFramebuffer(arg0,arg1);
    String txt = new String("glBindFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2f(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawRangeElements(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2GL3.glDrawRangeElements(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glDrawRangeElements(" +
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
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2d(int arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib2d(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramInfoLog(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glGetProgramInfoLog(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetProgramInfoLog(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture1D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTexture1D(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glFramebufferTexture1D(" +
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
  public  javax.media.opengl.GLContext getContext()
  {
        return downstreamGL2GL3.getContext();
  }
  public  void glVertexAttrib4fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int getSwapInterval()
  {
        return downstreamGL2GL3.getSwapInterval();
  }
  public  int glCreateProgram()
  {
        checkContext();
    int _res = downstreamGL2GL3.glCreateProgram();
    String txt = new String("glCreateProgram(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glProgramUniformui64NV(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2GL3.glProgramUniformui64NV(arg0,arg1,arg2);
    String txt = new String("glProgramUniformui64NV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glTexSubImage1D(" +
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
  public  void glCullFace(int arg0)
  {
        checkContext();
downstreamGL2GL3.glCullFace(arg0);
    String txt = new String("glCullFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4f(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4f(" +
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
  public  void glGetBufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetBufferParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glGetShaderSource(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glGetShaderSource(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetShaderSource(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4d(int arg0,double arg1,double arg2,double arg3,double arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4d(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
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
  public  void glGetActiveAttrib(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2GL3.glGetActiveAttrib(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glGetActiveAttrib(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void setSwapInterval(int arg0)
  {
    downstreamGL2GL3.setSwapInterval(arg0);
  }
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glBufferAddressRangeNV(int arg0,int arg1,long arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glBufferAddressRangeNV(arg0,arg1,arg2,arg3);
    String txt = new String("glBufferAddressRangeNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsEnabledi(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsEnabledi(arg0,arg1);
    String txt = new String("glIsEnabledi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib4Nusv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Nusv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsNamedBufferResidentNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsNamedBufferResidentNV(arg0);
    String txt = new String("glIsNamedBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glStencilMaskSeparate(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glStencilMaskSeparate(arg0,arg1);
    String txt = new String("glStencilMaskSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4s(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4s(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
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
  public  void glDrawElements(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2GL3.glDrawElements(arg0,arg1,arg2,arg3);
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
  public  boolean glIsShader(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsShader(arg0);
    String txt = new String("glIsShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glBlendEquation(int arg0)
  {
        checkContext();
downstreamGL2GL3.glBlendEquation(arg0);
    String txt = new String("glBlendEquation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonMode(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glPolygonMode(arg0,arg1);
    String txt = new String("glPolygonMode(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetQueryiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameterui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetBufferParameterui64vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Niv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Niv(arg0,arg1);
    String txt = new String("glVertexAttrib4Niv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetRenderbufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetRenderbufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderSource(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetShaderSource(arg0,arg1,arg2,arg3);
    String txt = new String("glGetShaderSource(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glAttachShader(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glAttachShader(arg0,arg1);
    String txt = new String("glAttachShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderBinary(int arg0,java.nio.IntBuffer arg1,int arg2,java.nio.Buffer arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glShaderBinary(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glShaderBinary(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2GL3.glTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glTexSubImage3D(" +
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
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveAttrib(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2GL3.glGetActiveAttrib(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glGetActiveAttrib(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableClientState(int arg0)
  {
        checkContext();
downstreamGL2GL3.glEnableClientState(arg0);
    String txt = new String("glEnableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glNormalFormatNV(arg0,arg1);
    String txt = new String("glNormalFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSampleCoverage(float arg0,boolean arg1)
  {
        checkContext();
downstreamGL2GL3.glSampleCoverage(arg0,arg1);
    String txt = new String("glSampleCoverage(" +
    "<float>" +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerui64i_vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetIntegerui64i_vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetIntegerui64i_vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleanv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glGetBooleanv(arg0,arg1);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearStencil(int arg0)
  {
        checkContext();
downstreamGL2GL3.glClearStencil(arg0);
    String txt = new String("glClearStencil(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableVertexAttribArray(int arg0)
  {
        checkContext();
downstreamGL2GL3.glEnableVertexAttribArray(arg0);
    String txt = new String("glEnableVertexAttribArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferSubData(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2GL3.glBufferSubData(arg0,arg1,arg2,arg3);
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
  public  int glGetBoundBuffer(int arg0)
  {
        checkContext();
    int _res = downstreamGL2GL3.glGetBoundBuffer(arg0);
    String txt = new String("glGetBoundBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glFlushMappedBufferRange(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glFlushMappedBufferRange(arg0,arg1,arg2);
    String txt = new String("glFlushMappedBufferRange(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetFragDataLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2GL3.glGetFragDataLocation(arg0,arg1);
    String txt = new String("glGetFragDataLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexFormatNV(arg0,arg1,arg2);
    String txt = new String("glVertexFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glDeleteRenderbuffers(arg0,arg1);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramInfoLog(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2GL3.glGetProgramInfoLog(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramInfoLog(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetUniformLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2GL3.glGetUniformLocation(arg0,arg1);
    String txt = new String("glGetUniformLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glFramebufferTexture2D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTexture2D(arg0,arg1,arg2,arg3,arg4);
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
  public  void glViewport(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glViewport(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glActiveTexture(int arg0)
  {
        checkContext();
downstreamGL2GL3.glActiveTexture(arg0);
    String txt = new String("glActiveTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBufferBase(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glBindBufferBase(arg0,arg1,arg2);
    String txt = new String("glBindBufferBase(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetUniformui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetCompressedTexImage(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2GL3.glGetCompressedTexImage(arg0,arg1,arg2);
    String txt = new String("glGetCompressedTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4fv(arg0,arg1);
    String txt = new String("glVertexAttrib4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBufferRange(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2GL3.glBindBufferRange(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glBindBufferRange(" +
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
  public  void glUseProgram(int arg0)
  {
        checkContext();
downstreamGL2GL3.glUseProgram(arg0);
    String txt = new String("glUseProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glDeleteRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenerateMipmap(int arg0)
  {
        checkContext();
downstreamGL2GL3.glGenerateMipmap(arg0);
    String txt = new String("glGenerateMipmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBeginQuery(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glBeginQuery(arg0,arg1);
    String txt = new String("glBeginQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4iv(arg0,arg1);
    String txt = new String("glVertexAttrib4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteShader(int arg0)
  {
        checkContext();
downstreamGL2GL3.glDeleteShader(arg0);
    String txt = new String("glDeleteShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnable(int arg0)
  {
        checkContext();
downstreamGL2GL3.glEnable(arg0);
    String txt = new String("glEnable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderBinary(int arg0,int[] arg1,int arg2,int arg3,java.nio.Buffer arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glShaderBinary(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glShaderBinary(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribIPointer(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2GL3.glVertexAttribIPointer(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttribIPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTexture(arg0,arg1,arg2,arg3);
    String txt = new String("glFramebufferTexture(" +
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
  public  void glVertexAttrib4Niv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4Niv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Niv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetRenderbufferParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glFinish()
  {
        checkContext();
downstreamGL2GL3.glFinish();
    String txt = new String("glFinish(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glValidateProgram(int arg0)
  {
        checkContext();
downstreamGL2GL3.glValidateProgram(arg0);
    String txt = new String("glValidateProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDetachShader(int arg0,int arg1)
  {
        checkContext();
downstreamGL2GL3.glDetachShader(arg0,arg1);
    String txt = new String("glDetachShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2GL3.glGetBufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetBufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glUniformui64vNV(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glGetQueryiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetQueryiv(" +
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
  public  boolean glIsVBOElementEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2GL3.glIsVBOElementEnabled();
    String txt = new String("glIsVBOElementEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniformui64NV(int arg0,long arg1)
  {
        checkContext();
downstreamGL2GL3.glUniformui64NV(arg0,arg1);
    String txt = new String("glUniformui64NV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleanv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glGetBooleanv(arg0,arg1,arg2);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadBuffer(int arg0)
  {
        checkContext();
downstreamGL2GL3.glReadBuffer(arg0);
    String txt = new String("glReadBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2GL3.glFramebufferTexture3D(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glFramebufferTexture3D(" +
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
  public  void glGetTexImage(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2GL3.glGetTexImage(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glScissor(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2GL3.glScissor(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4usv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4usv(arg0,arg1);
    String txt = new String("glVertexAttrib4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEndQuery(int arg0)
  {
        checkContext();
downstreamGL2GL3.glEndQuery(arg0);
    String txt = new String("glEndQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2GL3.glVertexAttrib4dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("DebugGL2GL3 [ implementing javax.media.opengl.GL2GL3,\n\t");
    sb.append(" downstream: "+downstreamGL2GL3.toString()+"\n\t]");
    return sb.toString();
  }
  private void checkGLGetError(String caller)
  {
    // Debug code to make sure the pipeline is working; leave commented out unless testing this class
    //System.err.println("Checking for GL errors after call to " + caller);

    int err = downstreamGL2GL3.glGetError();
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
        case GL_OUT_OF_MEMORY: buf.append("GL_OUT_OF_MEMORY "); break;
        case GL_NO_ERROR: throw new InternalError("Should not be treating GL_NO_ERROR as error");
        default: buf.append("Unknown glGetError() return value: ");
      }
      buf.append("( " + err + " 0x"+Integer.toHexString(err).toUpperCase() + "), ");
    } while ((--recursionDepth >= 0) && (err = downstreamGL2GL3.glGetError()) != GL_NO_ERROR);
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

  private GL2GL3 downstreamGL2GL3;
} // end class DebugGL2GL3
