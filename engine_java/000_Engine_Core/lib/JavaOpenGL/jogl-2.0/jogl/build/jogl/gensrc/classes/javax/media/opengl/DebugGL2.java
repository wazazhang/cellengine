package javax.media.opengl;

import java.io.*;
import javax.media.opengl.*;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLBase;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import com.sun.gluegen.runtime.*;

/** <P> Composable pipeline which wraps an underlying {@link GL} implementation,
    providing error checking after each OpenGL method call. If an error occurs,
    causes a {@link GLException} to be thrown at exactly the point of failure.
    Sample code which installs this pipeline: </P>

<PRE>
     GL gl = drawable.setGL(new DebugGL(drawable.getGL()));
</PRE>
*/
public class DebugGL2 implements javax.media.opengl.GLBase, javax.media.opengl.GL, javax.media.opengl.GL2ES1, javax.media.opengl.GL2ES2, javax.media.opengl.GL2GL3, javax.media.opengl.GL2
{
  public static final boolean DEBUG = com.sun.opengl.impl.Debug.debug("DebugGL2");
  public DebugGL2(GL2 downstreamGL2)
  {
    if (downstreamGL2 == null) {
      throw new IllegalArgumentException("null downstreamGL2");
    }
    this.downstreamGL2 = downstreamGL2;
    // Fetch GLContext object for better error checking (if possible)
    _context = downstreamGL2.getContext();
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
    return true;
  }
  public boolean isGLES1() {
    return false;
  }
  public boolean isGLES2() {
    return false;
  }
  public boolean isGL2ES1() {
    return true;
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
    return this;
  }
  public javax.media.opengl.GLES1 getGLES1() {
    throw new GLException("Not a GLES1 implementation");
  }
  public javax.media.opengl.GLES2 getGLES2() {
    throw new GLException("Not a GLES2 implementation");
  }
  public javax.media.opengl.GL2ES1 getGL2ES1() {
    return this;
  }
  public javax.media.opengl.GL2ES2 getGL2ES2() {
    return this;
  }
  public javax.media.opengl.GL2GL3 getGL2GL3() {
    return this;
  }
  public GLProfile getGLProfile() {
    return downstreamGL2.getGLProfile();
  }
  public  void glColor3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glColor3s(arg0,arg1,arg2);
    String txt = new String("glColor3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteBufferRegion(int arg0)
  {
        checkContext();
downstreamGL2.glDeleteBufferRegion(arg0);
    String txt = new String("glDeleteBufferRegion(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1sv(arg0,arg1);
    String txt = new String("glMultiTexCoord1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3h(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glColor3h(arg0,arg1,arg2);
    String txt = new String("glColor3h(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsProgram(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsProgram(arg0);
    String txt = new String("glIsProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetMultiTexEnvfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexEnvfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexEnvfvEXT(" +
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
  public  void glTexCoord2iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2iv(arg0,arg1);
    String txt = new String("glTexCoord2iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3i(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexCoord3i(" +
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
  public  void glVertexAttrib2fvARB(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2fvARB(arg0,arg1);
    String txt = new String("glVertexAttrib2fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnd()
  {
        checkContext();
downstreamGL2.glEnd();
    insideBeginEndPair = false;
    String txt = new String("glEnd(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix3x2fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3x2fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix3x2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glTexCoord3d(arg0,arg1,arg2);
    String txt = new String("glTexCoord3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glNormalPointer(arg0,arg1,arg2);
    String txt = new String("glNormalPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3dv(arg0);
    String txt = new String("glNormal3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3iv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoadfEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixLoadfEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixLoadfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDrawBuffers(arg0,arg1,arg2);
    String txt = new String("glDrawBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix4x2fv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexEnvfEXT(int arg0,int arg1,int arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMultiTexEnvfEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexEnvfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlushMappedBufferRange(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glFlushMappedBufferRange(arg0,arg1,arg2);
    String txt = new String("glFlushMappedBufferRange(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFencesAPPLE(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenFencesAPPLE(arg0,arg1,arg2);
    String txt = new String("glGenFencesAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexParameterIuiv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dARB(int arg0,double arg1,double arg2,double arg3,double arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4dARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4dARB(" +
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
  public  void glTextureParameterIivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glTextureParameterIivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glTextureParameterIivEXT(" +
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
  public  void glGetShaderInfoLog(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetShaderInfoLog(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttribI2ui(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI2ui(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI2ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawElementsInstanced(int arg0,int arg1,int arg2,java.nio.Buffer arg3,int arg4)
  {
        checkContext();
downstreamGL2.glDrawElementsInstanced(arg0,arg1,arg2,arg3,arg4);
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
  public  void glProgramBufferParametersIuivNV(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersIuivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramBufferParametersIuivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nubv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nubv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Niv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Niv(arg0,arg1);
    String txt = new String("glVertexAttrib4Niv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex3fv(arg0);
    String txt = new String("glVertex3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendEquation(int arg0)
  {
        checkContext();
downstreamGL2.glBlendEquation(arg0);
    String txt = new String("glBlendEquation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteProgramsARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteProgramsARB(arg0,arg1,arg2);
    String txt = new String("glDeleteProgramsARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTextureParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureParameterfvEXT(" +
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
  public  void glMateriali(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMateriali(arg0,arg1,arg2);
    String txt = new String("glMateriali(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenOcclusionQueriesNV(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenOcclusionQueriesNV(arg0,arg1);
    String txt = new String("glGenOcclusionQueriesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexArrayRangeNV(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGL2.glVertexArrayRangeNV(arg0,arg1);
    String txt = new String("glVertexArrayRangeNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightivARB(arg0,arg1);
    String txt = new String("glWeightivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void setSwapInterval(int arg0)
  {
    downstreamGL2.setSwapInterval(arg0);
  }
  public  void glMultiTexCoord3dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3dv(arg0,arg1);
    String txt = new String("glMultiTexCoord3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos4dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos4dv(arg0,arg1);
    String txt = new String("glRasterPos4dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFinishFenceNV(int arg0)
  {
        checkContext();
downstreamGL2.glFinishFenceNV(arg0);
    String txt = new String("glFinishFenceNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2ivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform2ivARB(arg0,arg1,arg2);
    String txt = new String("glUniform2ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1d(double arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1d(arg0);
    String txt = new String("glTexCoord1d(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegeri_v(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetIntegeri_v(arg0,arg1,arg2);
    String txt = new String("glGetIntegeri_v(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3iv(arg0,arg1);
    String txt = new String("glNormal3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glNormal3f(arg0,arg1,arg2);
    String txt = new String("glNormal3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex3dv(arg0,arg1);
    String txt = new String("glVertex3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVBOArrayEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2.glIsVBOArrayEnabled();
    String txt = new String("glIsVBOArrayEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTextureParameterfEXT(int arg0,int arg1,int arg2,float arg3)
  {
        checkContext();
downstreamGL2.glTextureParameterfEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameterfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord4hv(arg0,arg1);
    String txt = new String("glTexCoord4hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameters4fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramEnvParameters4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramEnvParameters4fvEXT(" +
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
  public  void glGetTexLevelParameteriv(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTexLevelParameteriv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glNamedProgramStringEXT(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramStringEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramStringEXT(" +
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
  public  void glRasterPos4fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos4fv(arg0);
    String txt = new String("glRasterPos4fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameterui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetBufferParameterui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMakeBufferResidentNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMakeBufferResidentNV(arg0,arg1);
    String txt = new String("glMakeBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEndOcclusionQueryNV()
  {
        checkContext();
downstreamGL2.glEndOcclusionQueryNV();
    String txt = new String("glEndOcclusionQueryNV(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform3fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform3fvEXT(" +
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
  public  void glIndexfv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexfv(arg0,arg1);
    String txt = new String("glIndexfv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGenfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexGenfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexGenfvEXT(" +
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
  public  void glTexCoord2dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord2dv(arg0);
    String txt = new String("glTexCoord2dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetOcclusionQueryuivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetOcclusionQueryuivNV(arg0,arg1,arg2);
    String txt = new String("glGetOcclusionQueryuivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTranslated(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glTranslated(arg0,arg1,arg2);
    String txt = new String("glTranslated(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform3fvARB(arg0,arg1,arg2);
    String txt = new String("glUniform3fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetUniformLocationARB(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetUniformLocationARB(arg0,arg1);
    String txt = new String("glGetUniformLocationARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetTextureLevelParameterivEXT(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glGetTextureLevelParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureLevelParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex4hv(arg0);
    String txt = new String("glVertex4hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCurrentPaletteMatrix(int arg0)
  {
        checkContext();
downstreamGL2.glCurrentPaletteMatrix(arg0);
    String txt = new String("glCurrentPaletteMatrix(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glPixelMapfv(arg0,arg1,arg2);
    String txt = new String("glPixelMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glRenderMode(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glRenderMode(arg0);
    String txt = new String("glRenderMode(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  int glCheckNamedFramebufferStatusEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glCheckNamedFramebufferStatusEXT(arg0,arg1);
    String txt = new String("glCheckNamedFramebufferStatusEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetVertexAttribdvARB(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribdvARB(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribdvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterIuivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterIuivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexParameterIuivEXT(" +
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
  public  void glCompressedTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2.glCompressedTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glReadBuffer(int arg0)
  {
        checkContext();
downstreamGL2.glReadBuffer(arg0);
    String txt = new String("glReadBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsBufferResidentNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsBufferResidentNV(arg0);
    String txt = new String("glIsBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glAccum(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glAccum(arg0,arg1);
    String txt = new String("glAccum(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBeginVertexShaderEXT()
  {
        checkContext();
downstreamGL2.glBeginVertexShaderEXT();
    String txt = new String("glBeginVertexShaderEXT(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nuiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nuiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindFramebuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBindFramebuffer(arg0,arg1);
    String txt = new String("glBindFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2iv(arg0,arg1);
    String txt = new String("glWindowPos2iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribIiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean hasGLSL()
  {
        return downstreamGL2.hasGLSL();
  }
  public  void glMultiTexCoord1iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1iv(arg0,arg1);
    String txt = new String("glMultiTexCoord1iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureLevelParameterfvEXT(int arg0,int arg1,int arg2,int arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glGetTextureLevelParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureLevelParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexEnvfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexEnvfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexEnvfvEXT(" +
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
  public  void glGetTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexParameterfv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttribI3iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI3iv(arg0,arg1);
    String txt = new String("glVertexAttribI3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetDoublev(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetDoublev(arg0,arg1,arg2);
    String txt = new String("glGetDoublev(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformBufferEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glUniformBufferEXT(arg0,arg1,arg2);
    String txt = new String("glUniformBufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glVertexPointer(arg0,arg1,arg2,arg3);
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
  public  void glCopyConvolutionFilter2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glCopyConvolutionFilter2D(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glCopyConvolutionFilter2D(" +
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
  public  void glVariantsvEXT(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantsvEXT(arg0,arg1);
    String txt = new String("glVariantsvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3hv(arg0,arg1);
    String txt = new String("glVertexAttrib3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameteri(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPointParameteri(arg0,arg1);
    String txt = new String("glPointParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWindowPos3i(arg0,arg1,arg2);
    String txt = new String("glWindowPos3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindTexture(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBindTexture(arg0,arg1);
    String txt = new String("glBindTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramEnvParameterdvARB(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterdvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramEnvParameterdvARB(" +
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
  public  void glIndexFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexFormatNV(arg0,arg1);
    String txt = new String("glIndexFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLineStipple(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glLineStipple(arg0,arg1);
    String txt = new String("glLineStipple(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4hv(arg0);
    String txt = new String("glColor4hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParametersI4ivNV(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParametersI4ivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParametersI4ivNV(" +
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
  public  void glWindowPos2dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos2dv(arg0);
    String txt = new String("glWindowPos2dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glConvolutionParameteri(arg0,arg1,arg2);
    String txt = new String("glConvolutionParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1i(arg0,arg1);
    String txt = new String("glMultiTexCoord1i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteLists(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDeleteLists(arg0,arg1);
    String txt = new String("glDeleteLists(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2.glCompressedTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glIndexiv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glIndexiv(arg0);
    String txt = new String("glIndexiv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedBufferParameterivEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetNamedBufferParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedBufferParameterivEXT(" +
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
  public  void glProgramUniformMatrix2x4fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2x4fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix2x4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnable(int arg0)
  {
        checkContext();
downstreamGL2.glEnable(arg0);
    String txt = new String("glEnable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteShader(int arg0)
  {
        checkContext();
downstreamGL2.glDeleteShader(arg0);
    String txt = new String("glDeleteShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3sv(arg0);
    String txt = new String("glNormal3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapuiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetPixelMapuiv(arg0,arg1,arg2);
    String txt = new String("glGetPixelMapuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glOrtho(double arg0,double arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGL2.glOrtho(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glPixelMapuiv(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glPixelMapuiv(arg0,arg1,arg2);
    String txt = new String("glPixelMapuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetDoubleIndexedvEXT(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetDoubleIndexedvEXT(arg0,arg1,arg2);
    String txt = new String("glGetDoubleIndexedvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnablei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEnablei(arg0,arg1);
    String txt = new String("glEnablei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableVariantClientStateEXT(int arg0)
  {
        checkContext();
downstreamGL2.glDisableVariantClientStateEXT(arg0);
    String txt = new String("glDisableVariantClientStateEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI1iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI1iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI1iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4ub(byte arg0,byte arg1,byte arg2,byte arg3)
  {
        checkContext();
downstreamGL2.glColor4ub(arg0,arg1,arg2,arg3);
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
  public  void glPixelTransformParameterfEXT(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glPixelTransformParameterfEXT(arg0,arg1,arg2);
    String txt = new String("glPixelTransformParameterfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4ui(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glColor4ui(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4ui(" +
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
  public  void glGenBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenBuffers(arg0,arg1);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexParameterfvEXT(" +
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
  public  boolean glIsRenderbuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsRenderbuffer(arg0);
    String txt = new String("glIsRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniformMatrix2x3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix2x3fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glColor4us(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glColor4us(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4us(" +
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
  public  void glMatrixMultTransposefEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixMultTransposefEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixMultTransposefEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTextureParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTextureParameterivEXT(" +
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
  public  void glColor3uiv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3uiv(arg0);
    String txt = new String("glColor3uiv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glTextureImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glTextureImage2DEXT(" +
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
  public  void glFogCoordhv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glFogCoordhv(arg0);
    String txt = new String("glFogCoordhv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glMultiTexSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glMultiTexSubImage2DEXT(" +
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
  public  void glVertexArrayRangeAPPLE(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGL2.glVertexArrayRangeAPPLE(arg0,arg1);
    String txt = new String("glVertexArrayRangeAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1hv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramiv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord1fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1fv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalMesh2(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glEvalMesh2(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glEvalMesh2(" +
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
  public  void glProgramLocalParameterI4ivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4ivNV(arg0,arg1,arg2);
    String txt = new String("glProgramLocalParameterI4ivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedMultiTexImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCompressedMultiTexImage3DEXT(" +
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
  public  void glEvalMesh1(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glEvalMesh1(arg0,arg1,arg2);
    String txt = new String("glEvalMesh1(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glTexEnvf(arg0,arg1,arg2);
    String txt = new String("glTexEnvf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameter4dvARB(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4dvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParameter4dvARB(" +
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
  public  void glGetConvolutionParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetConvolutionParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetConvolutionParameteriv(" +
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
  public  void glColor4usv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4usv(arg0);
    String txt = new String("glColor4usv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramEnvParameterIuivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterIuivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramEnvParameterIuivNV(" +
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
  public  void glColor3bv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3bv(arg0);
    String txt = new String("glColor3bv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NsvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NsvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NsvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawBuffer(int arg0)
  {
        checkContext();
downstreamGL2.glDrawBuffer(arg0);
    String txt = new String("glDrawBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord2dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord2dv(arg0);
    String txt = new String("glEvalCoord2dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetUniformLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetUniformLocation(arg0,arg1);
    String txt = new String("glGetUniformLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMatrixLoaddEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixLoaddEXT(arg0,arg1);
    String txt = new String("glMatrixLoaddEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightdvARB(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightdvARB(arg0,arg1);
    String txt = new String("glWeightdvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultfEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixMultfEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixMultfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetPixelMapfv(arg0,arg1,arg2);
    String txt = new String("glGetPixelMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGeniEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGeniEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGeniEXT(" +
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
  public  void glColor3ubv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3ubv(arg0);
    String txt = new String("glColor3ubv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameterI4uiNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4uiNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramEnvParameterI4uiNV(" +
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
  public  void glProgramBufferParametersIivNV(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersIivNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramBufferParametersIivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glHistogram(int arg0,int arg1,int arg2,boolean arg3)
  {
        checkContext();
downstreamGL2.glHistogram(arg0,arg1,arg2,arg3);
    String txt = new String("glHistogram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexGenfv(arg0,arg1,arg2,arg3);
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
  public  int glGetUniformBufferSizeEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetUniformBufferSizeEXT(arg0,arg1);
    String txt = new String("glGetUniformBufferSizeEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glSecondaryColor3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3sv(arg0);
    String txt = new String("glSecondaryColor3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullParameterfvEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glCullParameterfvEXT(arg0,arg1,arg2);
    String txt = new String("glCullParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonMode(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPolygonMode(arg0,arg1);
    String txt = new String("glPolygonMode(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetRenderbufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetRenderbufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightfvARB(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightfvARB(arg0,arg1);
    String txt = new String("glWeightfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  java.lang.Object getExtension(java.lang.String arg0)
  {
        return downstreamGL2.getExtension(arg0);
  }
  public  void glShaderOp1EXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glShaderOp1EXT(arg0,arg1,arg2);
    String txt = new String("glShaderOp1EXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGenf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glTexGenf(arg0,arg1,arg2);
    String txt = new String("glTexGenf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2svARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2svARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapParameterfvNV(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMapParameterfvNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapParameterfvNV(" +
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
  public  void glGetUniformivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformivARB(arg0,arg1,arg2);
    String txt = new String("glGetUniformivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4sv(arg0);
    String txt = new String("glColor4sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameterivEXT(" +
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
  public  void glDeleteOcclusionQueriesNV(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteOcclusionQueriesNV(arg0,arg1);
    String txt = new String("glDeleteOcclusionQueriesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord4sv(arg0,arg1);
    String txt = new String("glTexCoord4sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexPointer(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glIndexPointer(arg0,arg1,arg2);
    String txt = new String("glIndexPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord4iv(arg0);
    String txt = new String("glTexCoord4iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3sv(arg0,arg1);
    String txt = new String("glVertexAttrib3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexParameterivEXT(" +
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
  public  void glUniform3ivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3ivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3ivARB(" +
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
  public  void glLightModeli(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLightModeli(arg0,arg1);
    String txt = new String("glLightModeli(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInfoLogARB(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetInfoLogARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetInfoLogARB(" +
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
  public  int glCheckFramebufferStatus(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glCheckFramebufferStatus(arg0);
    String txt = new String("glCheckFramebufferStatus(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexGeniv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexGeniv(arg0,arg1,arg2);
    String txt = new String("glTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix2fv(arg0,arg1,arg2,arg3);
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
  public  void glGetFloatIndexedvEXT(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetFloatIndexedvEXT(arg0,arg1,arg2);
    String txt = new String("glGetFloatIndexedvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3b(byte arg0,byte arg1,byte arg2)
  {
        checkContext();
downstreamGL2.glColor3b(arg0,arg1,arg2);
    String txt = new String("glColor3b(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3x4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix3x4fv(arg0,arg1,arg2,arg3,arg4);
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
  public  boolean isExtensionAvailable(java.lang.String arg0)
  {
        return downstreamGL2.isExtensionAvailable(arg0);
  }
  public  void glUniform3fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform3fv(arg0,arg1,arg2);
    String txt = new String("glUniform3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightusvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightusvARB(arg0,arg1);
    String txt = new String("glWeightusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameterI4ivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4ivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParameterI4ivNV(" +
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
  public  void glPixelDataRangeNV(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glPixelDataRangeNV(arg0,arg1,arg2);
    String txt = new String("glPixelDataRangeNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexEnviEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMultiTexEnviEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexEnviEXT(" +
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
  public  void glGetProgramEnvParameterfvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterfvARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramEnvParameterfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1fvARB(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1fvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetBufferParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetBufferParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord4fv(arg0,arg1);
    String txt = new String("glTexCoord4fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib1dAPPLE(int arg0,int arg1,double arg2,double arg3,int arg4,int arg5,java.nio.DoubleBuffer arg6)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib1dAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMapVertexAttrib1dAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4bv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4bv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerIndexedv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetIntegerIndexedv(arg0,arg1,arg2);
    String txt = new String("glGetIntegerIndexedv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultdEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixMultdEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixMultdEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform1ivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform1ivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform1ivEXT(" +
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
  public  void glMatrixOrthoEXT(int arg0,double arg1,double arg2,double arg3,double arg4,double arg5,double arg6)
  {
        checkContext();
downstreamGL2.glMatrixOrthoEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMatrixOrthoEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
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
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nbv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nbv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nbv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NivARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribfv(arg0,arg1,arg2,arg3);
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
  public  void glUniform1f(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glUniform1f(arg0,arg1);
    String txt = new String("glUniform1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NubvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NubvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferSubData(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetBufferSubData(arg0,arg1,arg2,arg3);
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
  public  void glGetObjectParameterivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetObjectParameterivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetObjectParameterivARB(" +
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
  public  void glMapGrid1d(int arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glMapGrid1d(arg0,arg1,arg2);
    String txt = new String("glMapGrid1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glRasterPos3s(arg0,arg1,arg2);
    String txt = new String("glRasterPos3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4s(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glTexCoord4s(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoord4s(" +
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
  public  void glVertex4sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex4sv(arg0);
    String txt = new String("glVertex4sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapGrid1f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glMapGrid1f(arg0,arg1,arg2);
    String txt = new String("glMapGrid1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glTexCoord3hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord3hv(arg0);
    String txt = new String("glTexCoord3hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1fARB(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glUniform1fARB(arg0,arg1);
    String txt = new String("glUniform1fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColorFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColorFormatNV(arg0,arg1,arg2);
    String txt = new String("glSecondaryColorFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameters4fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParameters4fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParameters4fvEXT(" +
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
  public  void glFramebufferTextureFace(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glFramebufferTextureFace(arg0,arg1,arg2,arg3,arg4);
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
  public  void glTexCoord4i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexCoord4i(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoord4i(" +
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
  public  void glDeleteVertexArrays(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteVertexArrays(arg0,arg1,arg2);
    String txt = new String("glDeleteVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordPointer(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glFogCoordPointer(arg0,arg1,arg2);
    String txt = new String("glFogCoordPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4d(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glTexCoord4d(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoord4d(" +
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
  public  void glProgramEnvParametersI4ivNV(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParametersI4ivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParametersI4ivNV(" +
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
  public  void glFogCoordh(short arg0)
  {
        checkContext();
downstreamGL2.glFogCoordh(arg0);
    String txt = new String("glFogCoordh(" +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4h(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glTexCoord4h(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoord4h(" +
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
  public  void glGetMinmax(int arg0,boolean arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glGetMinmax(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMinmax(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4f(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glTexCoord4f(arg0,arg1,arg2,arg3);
    String txt = new String("glTexCoord4f(" +
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
  public  void glColor3usv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3usv(arg0,arg1);
    String txt = new String("glColor3usv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMinmaxParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMinmaxParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetMinmaxParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos4sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos4sv(arg0,arg1);
    String txt = new String("glRasterPos4sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribfvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribfvARB(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glTexCoordPointer(arg0,arg1,arg2,arg3);
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
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glCopyColorTable(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glCopyColorTable(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glCopyColorTable(" +
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
  public  void glVertexAttrib2hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI2uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI2uiv(arg0,arg1);
    String txt = new String("glVertexAttribI2uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glColor3hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3hv(arg0,arg1);
    String txt = new String("glColor3hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterIuivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterIuivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedProgramLocalParameterIuivEXT(" +
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
  public  void glGetBooleanv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetBooleanv(arg0,arg1);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTextureSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glCompressedTextureSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCompressedTextureSubImage2DEXT(" +
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
  public  void glMapVertexAttrib2fAPPLE(int arg0,int arg1,float arg2,float arg3,int arg4,int arg5,float arg6,float arg7,int arg8,int arg9,float[] arg10,int arg11)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib2fAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glMapVertexAttrib2fAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg11).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3f(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glUniform3f(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord2hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2hv(arg0,arg1);
    String txt = new String("glMultiTexCoord2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSetInvariantEXT(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glSetInvariantEXT(arg0,arg1,arg2);
    String txt = new String("glSetInvariantEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultdEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixMultdEXT(arg0,arg1);
    String txt = new String("glMatrixMultdEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBindBuffer(arg0,arg1);
    String txt = new String("glBindBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glInitNames()
  {
        checkContext();
downstreamGL2.glInitNames();
    String txt = new String("glInitNames(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3h(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3h(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3h(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3s(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendEquationSeparate(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBlendEquationSeparate(arg0,arg1);
    String txt = new String("glBlendEquationSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib1dAPPLE(int arg0,int arg1,double arg2,double arg3,int arg4,int arg5,double[] arg6,int arg7)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib1dAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glMapVertexAttrib1dAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glTextureImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glTextureImage1DEXT(" +
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
  public  void glStencilOp(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glStencilOp(arg0,arg1,arg2);
    String txt = new String("glStencilOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2.glTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glGetLocalConstantFloatvEXT(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetLocalConstantFloatvEXT(arg0,arg1,arg2);
    String txt = new String("glGetLocalConstantFloatvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedRenderbufferParameterivEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetNamedRenderbufferParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedRenderbufferParameterivEXT(" +
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
  public  void glWindowPos3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos3sv(arg0,arg1);
    String txt = new String("glWindowPos3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteQueries(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteQueries(arg0,arg1);
    String txt = new String("glDeleteQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix2fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGend(int arg0,int arg1,double arg2)
  {
        checkContext();
downstreamGL2.glTexGend(arg0,arg1,arg2);
    String txt = new String("glTexGend(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glColorPointer(arg0,arg1,arg2,arg3);
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
  public  void glProgramLocalParameterI4uiNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4uiNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramLocalParameterI4uiNV(" +
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
  public  void glVertex3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex3iv(arg0,arg1);
    String txt = new String("glVertex3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformiv(arg0,arg1,arg2);
    String txt = new String("glGetUniformiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glUniform2i(arg0,arg1,arg2);
    String txt = new String("glUniform2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameterIuivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glTextureParameterIuivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glTextureParameterIuivEXT(" +
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
  public  void glDeleteTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteTextures(arg0,arg1,arg2);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMaterialfv(arg0,arg1,arg2,arg3);
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
  public  void glUniformui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniformui64vNV(arg0,arg1,arg2,arg3);
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
  public  void glUniform4fARB(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glUniform4fARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniform4fARB(" +
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
  public  void glUniform2f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glUniform2f(arg0,arg1,arg2);
    String txt = new String("glUniform2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGenfEXT(int arg0,int arg1,int arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGenfEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGenfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glLightiv(arg0,arg1,arg2);
    String txt = new String("glLightiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveUniformARB(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2.glGetActiveUniformARB(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glGetActiveUniformARB(" +
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
  public  void glGetIntegerv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetIntegerv(arg0,arg1,arg2);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexs(short arg0)
  {
        checkContext();
downstreamGL2.glIndexs(arg0);
    String txt = new String("glIndexs(" +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderSourceARB(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetShaderSourceARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetShaderSourceARB(" +
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
  public  void glFramebufferReadBufferEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFramebufferReadBufferEXT(arg0,arg1);
    String txt = new String("glFramebufferReadBufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadName(int arg0)
  {
        checkContext();
downstreamGL2.glLoadName(arg0);
    String txt = new String("glLoadName(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3uivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3uivEXT(" +
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
  public  void glVertexAttribFormatNV(int arg0,int arg1,int arg2,boolean arg3,int arg4)
  {
        checkContext();
downstreamGL2.glVertexAttribFormatNV(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetUniformfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformfv(arg0,arg1,arg2,arg3);
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
  public  void glPointSize(float arg0)
  {
        checkContext();
downstreamGL2.glPointSize(arg0);
    String txt = new String("glPointSize(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetConvolutionParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetConvolutionParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetConvolutionParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexLevelParameterfvEXT(int arg0,int arg1,int arg2,int arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetMultiTexLevelParameterfvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetMultiTexLevelParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVBOElementEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2.glIsVBOElementEnabled();
    String txt = new String("glIsVBOElementEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glEdgeFlagv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEdgeFlagv(arg0,arg1);
    String txt = new String("glEdgeFlagv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribIFormatNV(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribIFormatNV(arg0,arg1,arg2,arg3);
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
  public  void glProgramUniformMatrix4fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glUnmapBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glUnmapBuffer(arg0);
    String txt = new String("glUnmapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib4NuivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NuivARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean isFunctionAvailable(java.lang.String arg0)
  {
        return downstreamGL2.isFunctionAvailable(arg0);
  }
  public  void glVertexAttrib4fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4fv(arg0,arg1);
    String txt = new String("glVertexAttrib4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord2fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord2fv(arg0);
    String txt = new String("glEvalCoord2fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glArrayElement(int arg0)
  {
        checkContext();
downstreamGL2.glArrayElement(arg0);
    String txt = new String("glArrayElement(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixRotatedEXT(int arg0,double arg1,double arg2,double arg3,double arg4)
  {
        checkContext();
downstreamGL2.glMatrixRotatedEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMatrixRotatedEXT(" +
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
  public  void glGenVertexArrays(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenVertexArrays(arg0,arg1,arg2);
    String txt = new String("glGenVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectui64vEXT(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetQueryObjectui64vEXT(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjectui64vEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4ivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform4ivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform4ivARB(" +
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
  public  void glDeleteVertexShaderEXT(int arg0)
  {
        checkContext();
downstreamGL2.glDeleteVertexShaderEXT(arg0);
    String txt = new String("glDeleteVertexShaderEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjecti64vEXT(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetQueryObjecti64vEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetQueryObjecti64vEXT(" +
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
  public  void glUniform2uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform2uiv(arg0,arg1,arg2);
    String txt = new String("glUniform2uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fvARB(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix2fvARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix2fvARB(" +
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
  public  void glSetLocalConstantEXT(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glSetLocalConstantEXT(arg0,arg1,arg2);
    String txt = new String("glSetLocalConstantEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameterI4iNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4iNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramEnvParameterI4iNV(" +
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
  public  void glWeightdvARB(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightdvARB(arg0,arg1,arg2);
    String txt = new String("glWeightdvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2fARB(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2fARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoaddEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixLoaddEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixLoaddEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapParameterivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMapParameterivNV(arg0,arg1,arg2);
    String txt = new String("glGetMapParameterivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform2fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform2fvARB(" +
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
  public  void glProgramLocalParameterI4uivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4uivNV(arg0,arg1,arg2);
    String txt = new String("glProgramLocalParameterI4uivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord2dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord2dv(arg0,arg1);
    String txt = new String("glEvalCoord2dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform(javax.media.opengl.GLUniformData arg0)
  {
        checkContext();
downstreamGL2.glUniform(arg0);
    String txt = new String("glUniform(" +
    "<javax.media.opengl.GLUniformData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3hv(arg0);
    String txt = new String("glSecondaryColor3hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBegin(int arg0)
  {
        checkContext();
downstreamGL2.glBegin(arg0);
    insideBeginEndPair = true;
    // NOTE: can't check glGetError(); it's not allowed inside glBegin/glEnd pair
  }
  public  void glProgramEnvParameter4dvARB(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4dvARB(arg0,arg1,arg2);
    String txt = new String("glProgramEnvParameter4dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderBinary(int arg0,java.nio.IntBuffer arg1,int arg2,java.nio.Buffer arg3,int arg4)
  {
        checkContext();
downstreamGL2.glShaderBinary(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetCompressedTexImage(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glGetCompressedTexImage(arg0,arg1,arg2);
    String txt = new String("glGetCompressedTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPopMatrix()
  {
        checkContext();
downstreamGL2.glPopMatrix();
    String txt = new String("glPopMatrix(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glTexCoord3f(arg0,arg1,arg2);
    String txt = new String("glTexCoord3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyPixels(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glCopyPixels(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glCopyPixels(" +
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
  public  void glVertex3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertex3i(arg0,arg1,arg2);
    String txt = new String("glVertex3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glMaterialiv(arg0,arg1,arg2);
    String txt = new String("glMaterialiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedMultiTexSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedMultiTexSubImage1DEXT(" +
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
  public  void glProgramUniform4ivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform4ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform4ivEXT(" +
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
  public  void glStencilClearTagEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glStencilClearTagEXT(arg0,arg1);
    String txt = new String("glStencilClearTagEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4usv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4usv(arg0,arg1);
    String txt = new String("glVertexAttrib4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NusvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NusvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexParameterfvEXT(" +
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
  public  void glConvolutionFilter1D(int arg0,int arg1,int arg2,int arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glConvolutionFilter1D(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glConvolutionFilter1D(" +
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
  public  void glGetVertexAttribdvARB(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribdvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribdvARB(" +
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
  public  void glVertexAttrib4bvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4bvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4bvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexubvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixIndexubvARB(arg0,arg1);
    String txt = new String("glMatrixIndexubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos2iv(arg0);
    String txt = new String("glRasterPos2iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glClearBufferiv(arg0,arg1,arg2);
    String txt = new String("glClearBufferiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableClientStateIndexedEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEnableClientStateIndexedEXT(arg0,arg1);
    String txt = new String("glEnableClientStateIndexedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonOffset(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPolygonOffset(arg0,arg1);
    String txt = new String("glPolygonOffset(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableIndexed(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEnableIndexed(arg0,arg1);
    String txt = new String("glEnableIndexed(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform1iv(arg0,arg1,arg2);
    String txt = new String("glUniform1iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTextureImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2.glCopyTextureImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCopyTextureImage2DEXT(" +
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
  public  void glDeleteFencesAPPLE(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteFencesAPPLE(arg0,arg1);
    String txt = new String("glDeleteFencesAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glCopyTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetDoubleIndexedvEXT(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetDoubleIndexedvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetDoubleIndexedvEXT(" +
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
  public  void glFinishTextureSUNX()
  {
        checkContext();
downstreamGL2.glFinishTextureSUNX();
    String txt = new String("glFinishTextureSUNX(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3fvARB(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix3fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix3fvARB(" +
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
  public  void glFogfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glFogfv(arg0,arg1,arg2);
    String txt = new String("glFogfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexLevelParameterfv(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTexLevelParameterfv(arg0,arg1,arg2,arg3);
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
  public  void glProgramEnvParameter4fARB(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4fARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramEnvParameter4fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
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
  public  void glUniform1ivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform1ivARB(arg0,arg1,arg2);
    String txt = new String("glUniform1ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1f(float arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1f(arg0);
    String txt = new String("glTexCoord1f(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawArrays(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDrawArrays(arg0,arg1,arg2);
    String txt = new String("glDrawArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4fvARB(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix4fvARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix4fvARB(" +
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
  public  void glCompileShaderARB(int arg0)
  {
        checkContext();
downstreamGL2.glCompileShaderARB(arg0);
    String txt = new String("glCompileShaderARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetBoundBuffer(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glGetBoundBuffer(arg0);
    String txt = new String("glGetBoundBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glRasterPos2fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2fv(arg0,arg1);
    String txt = new String("glRasterPos2fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameteriEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTextureParameteriEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameteriEXT(" +
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
  public  void glWindowPos2dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2dv(arg0,arg1);
    String txt = new String("glWindowPos2dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2.glCopyTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glVertexWeighthv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexWeighthv(arg0,arg1);
    String txt = new String("glVertexWeighthv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedMultiTexImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedMultiTexImage2DEXT(" +
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
  public  void glTextureSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glTextureSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glTextureSubImage1DEXT(" +
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
  public  void glGetUniformfvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformfvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformfvARB(" +
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
  public  void glNamedProgramLocalParameterI4uiEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4uiEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glNamedProgramLocalParameterI4uiEXT(" +
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
  public  java.nio.ByteBuffer glMapBuffer(int arg0,int arg1)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2.glMapBuffer(arg0,arg1);
    String txt = new String("glMapBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniform2ui(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glUniform2ui(arg0,arg1,arg2);
    String txt = new String("glUniform2ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramEnvParameterdvARB(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterdvARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramEnvParameterdvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs4hv(int arg0,int arg1,java.nio.ShortBuffer arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribs4hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribs4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord3sv(arg0);
    String txt = new String("glTexCoord3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearBufferfv(arg0,arg1,arg2,arg3);
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
  public  void glColorTable(int arg0,int arg1,int arg2,int arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glColorTable(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glColorTable(" +
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
  public  void glSecondaryColor3ubv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3ubv(arg0,arg1);
    String txt = new String("glSecondaryColor3ubv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glLightModelfv(arg0,arg1,arg2);
    String txt = new String("glLightModelfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI1i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI1i(arg0,arg1);
    String txt = new String("glVertexAttribI1i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapParameterivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glMapParameterivNV(arg0,arg1,arg2);
    String txt = new String("glMapParameterivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexGenfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexGenfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexGenfvEXT(" +
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
  public  void glGetDoublev(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetDoublev(arg0,arg1);
    String txt = new String("glGetDoublev(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glNormal3d(arg0,arg1,arg2);
    String txt = new String("glNormal3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform4uivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform4uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform4uivEXT(" +
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
  public  void glRasterPos4iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos4iv(arg0,arg1);
    String txt = new String("glRasterPos4iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlushRenderAPPLE()
  {
        checkContext();
downstreamGL2.glFlushRenderAPPLE();
    String txt = new String("glFlushRenderAPPLE(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glMatrixFrustumEXT(int arg0,double arg1,double arg2,double arg3,double arg4,double arg5,double arg6)
  {
        checkContext();
downstreamGL2.glMatrixFrustumEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMatrixFrustumEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
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
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3fv(arg0);
    String txt = new String("glNormal3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glVertexAttribPointer(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glSecondaryColor3b(byte arg0,byte arg1,byte arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3b(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3b(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameterI4uivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4uivNV(arg0,arg1,arg2);
    String txt = new String("glProgramEnvParameterI4uivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantivEXT(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantivEXT(arg0,arg1);
    String txt = new String("glVariantivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexRenderbufferEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexRenderbufferEXT(arg0,arg1,arg2);
    String txt = new String("glMultiTexRenderbufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearBufferuiv(arg0,arg1,arg2,arg3);
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
  public  void glGetNamedProgramLocalParameterfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramLocalParameterfvEXT(" +
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
  public  void glTranslatef(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glTranslatef(arg0,arg1,arg2);
    String txt = new String("glTranslatef(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEdgeFlagPointer(int arg0,long arg1)
  {
        checkContext();
downstreamGL2.glEdgeFlagPointer(arg0,arg1);
    String txt = new String("glEdgeFlagPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSwizzleEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glSwizzleEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glSwizzleEXT(" +
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
  public  void glNormal3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3dv(arg0,arg1);
    String txt = new String("glNormal3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInvariantBooleanvEXT(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetInvariantBooleanvEXT(arg0,arg1,arg2);
    String txt = new String("glGetInvariantBooleanvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3fv(arg0,arg1);
    String txt = new String("glColor3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureBufferEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTextureBufferEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureBufferEXT(" +
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
  public  void glLightModeliv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glLightModeliv(arg0,arg1);
    String txt = new String("glLightModeliv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4ubvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ubvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4ubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glAreTexturesResident(int arg0,int[] arg1,int arg2,byte[] arg3,int arg4)
  {
        checkContext();
    boolean _res = downstreamGL2.glAreTexturesResident(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glAreTexturesResident(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetProgramEnvParameterIivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterIivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramEnvParameterIivNV(" +
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
  public  void glVertexAttribI3i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribI3i(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribI3i(" +
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
  public  void glVertexAttrib2fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeighth(short arg0)
  {
        checkContext();
downstreamGL2.glVertexWeighth(arg0);
    String txt = new String("glVertexWeighth(" +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3iv(arg0,arg1,arg2,arg3);
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
  public  void glWeightsvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightsvARB(arg0,arg1);
    String txt = new String("glWeightsvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelStorei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPixelStorei(arg0,arg1);
    String txt = new String("glPixelStorei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix4fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glFlushVertexArrayRangeNV()
  {
        checkContext();
downstreamGL2.glFlushVertexArrayRangeNV();
    String txt = new String("glFlushVertexArrayRangeNV(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2d(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2d(arg0,arg1);
    String txt = new String("glTexCoord2d(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2dv(arg0,arg1);
    String txt = new String("glTexCoord2dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2f(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2f(arg0,arg1);
    String txt = new String("glTexCoord2f(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3fv(arg0,arg1);
    String txt = new String("glMultiTexCoord3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2h(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2h(arg0,arg1);
    String txt = new String("glTexCoord2h(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos2fv(arg0);
    String txt = new String("glWindowPos2fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2i(arg0,arg1);
    String txt = new String("glTexCoord2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2s(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2s(arg0,arg1);
    String txt = new String("glTexCoord2s(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMinmaxParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMinmaxParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMinmaxParameterfv(" +
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
  public  void glGetNamedBufferParameterui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetNamedBufferParameterui64vNV(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4ivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ivARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glReadBufferRegion(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glReadBufferRegion(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glReadBufferRegion(" +
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
  public  void glVertex3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex3dv(arg0);
    String txt = new String("glVertex3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetError()
  {
        checkContext();
    int _res = downstreamGL2.glGetError();
    String txt = new String("glGetError(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glFogiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glFogiv(arg0,arg1);
    String txt = new String("glFogiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterIivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTextureParameterIivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureParameterIivEXT(" +
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
  public  void glIndexsv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexsv(arg0,arg1);
    String txt = new String("glIndexsv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderSourceARB(int arg0,int arg1,java.lang.String[] arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glShaderSourceARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glShaderSourceARB(" +
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
  public  void glRasterPos4dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos4dv(arg0);
    String txt = new String("glRasterPos4dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3dv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClampColor(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glClampColor(arg0,arg1);
    String txt = new String("glClampColor(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fvARB(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3fvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3iv(arg0);
    String txt = new String("glColor3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3sv(arg0,arg1);
    String txt = new String("glColor3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapusv(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glPixelMapusv(arg0,arg1,arg2);
    String txt = new String("glPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParametersI4uivNV(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramEnvParametersI4uivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramEnvParametersI4uivNV(" +
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
  public  void glTexCoord2fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord2fv(arg0);
    String txt = new String("glTexCoord2fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform1fv(arg0,arg1,arg2,arg3);
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
  public  void glProgramUniform1fEXT(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glProgramUniform1fEXT(arg0,arg1,arg2);
    String txt = new String("glProgramUniform1fEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3uiEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3uiEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3uiEXT(" +
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
  public  void glMatrixLoadTransposefEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixLoadTransposefEXT(arg0,arg1);
    String txt = new String("glMatrixLoadTransposefEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPushName(int arg0)
  {
        checkContext();
downstreamGL2.glPushName(arg0);
    String txt = new String("glPushName(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4iv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4fv(arg0,arg1);
    String txt = new String("glColor4fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture2D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glFramebufferTexture2D(arg0,arg1,arg2,arg3,arg4);
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
  public  void glSecondaryColor3hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3hv(arg0,arg1);
    String txt = new String("glSecondaryColor3hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexFormatNV(arg0,arg1,arg2);
    String txt = new String("glVertexFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetAttachedObjectsARB(int arg0,int arg1,int[] arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetAttachedObjectsARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetAttachedObjectsARB(" +
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
  public  void glGetAttachedObjectsARB(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetAttachedObjectsARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetAttachedObjectsARB(" +
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
  public  void glTessellationFactorAMD(float arg0)
  {
        checkContext();
downstreamGL2.glTessellationFactorAMD(arg0);
    String txt = new String("glTessellationFactorAMD(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCallLists(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glCallLists(arg0,arg1,arg2);
    String txt = new String("glCallLists(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLocalConstantIntegervEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetLocalConstantIntegervEXT(arg0,arg1,arg2);
    String txt = new String("glGetLocalConstantIntegervEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos3iv(arg0,arg1);
    String txt = new String("glWindowPos3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearStencil(int arg0)
  {
        checkContext();
downstreamGL2.glClearStencil(arg0);
    String txt = new String("glClearStencil(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindMultiTextureEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glBindMultiTextureEXT(arg0,arg1,arg2);
    String txt = new String("glBindMultiTextureEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterdvEXT(int arg0,int arg1,int arg2,double[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterdvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedProgramLocalParameterdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableClientState(int arg0)
  {
        checkContext();
downstreamGL2.glEnableClientState(arg0);
    String txt = new String("glEnableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI2iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI2iv(arg0,arg1);
    String txt = new String("glVertexAttribI2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapusv(int arg0,long arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapusv(arg0,arg1);
    String txt = new String("glGetPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleani_v(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetBooleani_v(arg0,arg1,arg2,arg3);
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
  public  void glTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glDrawElements(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glDrawElements(arg0,arg1,arg2,arg3);
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
  public  void glClearIndex(float arg0)
  {
        checkContext();
downstreamGL2.glClearIndex(arg0);
    String txt = new String("glClearIndex(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetHistogramParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetHistogramParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetHistogramParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawArrays(int arg0,int[] arg1,int arg2,int[] arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glMultiDrawArrays(arg0,arg1,arg2,arg3,arg4,arg5);
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
downstreamGL2.glMultiDrawArrays(arg0,arg1,arg2,arg3);
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
  public  void glVertex2fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex2fv(arg0);
    String txt = new String("glVertex2fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,long arg10)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glProgramBufferParametersfvNV(int arg0,int arg1,int arg2,int arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersfvNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramBufferParametersfvNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4dv(arg0,arg1);
    String txt = new String("glMultiTexCoord4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantubvEXT(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantubvEXT(arg0,arg1);
    String txt = new String("glVariantubvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramLocalParameterfvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterfvARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramLocalParameterfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform2fv(arg0,arg1,arg2);
    String txt = new String("glUniform2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteRenderbuffers(arg0,arg1);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform1uiv(arg0,arg1,arg2);
    String txt = new String("glUniform1uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlush()
  {
        checkContext();
downstreamGL2.glFlush();
    String txt = new String("glFlush(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapControlPointsNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,boolean arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glMapControlPointsNV(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glMapControlPointsNV(" +
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
    "<boolean>" +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferParameterivEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetFramebufferParameterivEXT(arg0,arg1,arg2);
    String txt = new String("glGetFramebufferParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedFramebufferTexture3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTexture3DEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glNamedFramebufferTexture3DEXT(" +
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
  public  void glGetMapfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMapfv(arg0,arg1,arg2);
    String txt = new String("glGetMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterIuivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexParameterIuivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexParameterIuivEXT(" +
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
  public  void glFramebufferTexture1D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glFramebufferTexture1D(arg0,arg1,arg2,arg3,arg4);
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
  public  void glRasterPos3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos3fv(arg0);
    String txt = new String("glRasterPos3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4sARB(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4sARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4sARB(" +
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
  public  void glProgramUniformMatrix3fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEndQuery(int arg0)
  {
        checkContext();
downstreamGL2.glEndQuery(arg0);
    String txt = new String("glEndQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI1ui(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI1ui(arg0,arg1);
    String txt = new String("glVertexAttribI1ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetLightiv(arg0,arg1,arg2);
    String txt = new String("glGetLightiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glMatrixMultTransposedEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixMultTransposedEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixMultTransposedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectui64vEXT(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetQueryObjectui64vEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetQueryObjectui64vEXT(" +
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
  public  void glUniformMatrix4x3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix4x3fv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord2hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2hv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjecti64vEXT(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetQueryObjecti64vEXT(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjecti64vEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexubv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexubv(arg0,arg1);
    String txt = new String("glIndexubv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInfoLogARB(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetInfoLogARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetInfoLogARB(" +
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
  public  void glMultiTexCoord2iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2iv(arg0,arg1);
    String txt = new String("glMultiTexCoord2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos3sv(arg0);
    String txt = new String("glWindowPos3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glGetMapdv(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMapdv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapdv(" +
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
  public  void glVertexAttrib2hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2hv(arg0,arg1);
    String txt = new String("glVertexAttrib2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendFuncSeparateINGR(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glBlendFuncSeparateINGR(arg0,arg1,arg2,arg3);
    String txt = new String("glBlendFuncSeparateINGR(" +
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
  public  void glColorMaterial(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColorMaterial(arg0,arg1);
    String txt = new String("glColorMaterial(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetHistogram(int arg0,boolean arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glGetHistogram(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetHistogram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFenceivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetFenceivNV(arg0,arg1,arg2);
    String txt = new String("glGetFenceivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedProgramivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedProgramivEXT(" +
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
  public  void glColor3hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3hv(arg0);
    String txt = new String("glColor3hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos3dv(arg0,arg1);
    String txt = new String("glRasterPos3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4fvARB(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4fvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2dv(arg0,arg1);
    String txt = new String("glVertex2dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glExtractComponentEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glExtractComponentEXT(arg0,arg1,arg2);
    String txt = new String("glExtractComponentEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3bv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3bv(arg0,arg1);
    String txt = new String("glNormal3bv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex4sv(arg0,arg1);
    String txt = new String("glVertex4sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameterI4uivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4uivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedProgramLocalParameterI4uivEXT(" +
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
  public  void glEdgeFlag(boolean arg0)
  {
        checkContext();
downstreamGL2.glEdgeFlag(arg0);
    String txt = new String("glEdgeFlag(" +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord3hv(arg0,arg1);
    String txt = new String("glTexCoord3hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  java.lang.Object getPlatformGLExtensions()
  {
        return downstreamGL2.getPlatformGLExtensions();
  }
  public  void glMatrixMode(int arg0)
  {
        checkContext();
downstreamGL2.glMatrixMode(arg0);
    String txt = new String("glMatrixMode(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthRangef(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glDepthRangef(arg0,arg1);
    String txt = new String("glDepthRangef(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniformui64vNV(arg0,arg1,arg2);
    String txt = new String("glUniformui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
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
  public  void glDeleteFencesNV(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteFencesNV(arg0,arg1,arg2);
    String txt = new String("glDeleteFencesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelTransformParameterivEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glPixelTransformParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glPixelTransformParameterivEXT(" +
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
  public  void glVertexAttribI4uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1dv(arg0);
    String txt = new String("glTexCoord1dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3usv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3usv(arg0);
    String txt = new String("glColor3usv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix4x3fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4x3fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix4x3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos4sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos4sv(arg0);
    String txt = new String("glRasterPos4sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightuivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightuivARB(arg0,arg1);
    String txt = new String("glWeightuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantuivEXT(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantuivEXT(arg0,arg1);
    String txt = new String("glVariantuivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1s(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1s(arg0,arg1);
    String txt = new String("glVertexAttrib1s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelTransformParameterfvEXT(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glPixelTransformParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glPixelTransformParameterfvEXT(" +
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
  public  void glIndexsv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glIndexsv(arg0);
    String txt = new String("glIndexsv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenRenderbuffers(arg0,arg1);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonStipple(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glPolygonStipple(arg0);
    String txt = new String("glPolygonStipple(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glLightfv(arg0,arg1,arg2,arg3);
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
  public  void glGetQueryiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetQueryiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3sv(arg0);
    String txt = new String("glColor3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetClipPlane(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetClipPlane(arg0,arg1,arg2);
    String txt = new String("glGetClipPlane(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetLightfv(arg0,arg1,arg2,arg3);
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
  public  boolean glIsBuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsBuffer(arg0);
    String txt = new String("glIsBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniformMatrix2x4fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix2x4fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glSecondaryColor3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3iv(arg0);
    String txt = new String("glSecondaryColor3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1h(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1h(arg0,arg1);
    String txt = new String("glVertexAttrib1h(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexEnvivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexEnvivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexEnvivEXT(" +
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
  public  void glVertexAttrib1fARB(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1fARB(arg0,arg1);
    String txt = new String("glVertexAttrib1fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glViewport(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glViewport(arg0,arg1,arg2,arg3);
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
  public  void glProgramParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glProgramParameteri(arg0,arg1,arg2);
    String txt = new String("glProgramParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderInfoLog(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetShaderInfoLog(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetInvariantIntegervEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetInvariantIntegervEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetInvariantIntegervEXT(" +
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
  public  void glTexCoord3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord3iv(arg0);
    String txt = new String("glTexCoord3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNewList(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNewList(arg0,arg1);
    String txt = new String("glNewList(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorSubTable(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glColorSubTable(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glColorSubTable(" +
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
  public  void glShaderSource(int arg0,int arg1,java.lang.String[] arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glShaderSource(arg0,arg1,arg2,arg3,arg4);
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
  public  void glProgramUniform2ivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2ivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2ivEXT(" +
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
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glRotated(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glRotated(arg0,arg1,arg2,arg3);
    String txt = new String("glRotated(" +
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
  public  void glClipPlane(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glClipPlane(arg0,arg1,arg2);
    String txt = new String("glClipPlane(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1fv(arg0,arg1);
    String txt = new String("glVertexAttrib1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRotatef(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glRotatef(arg0,arg1,arg2,arg3);
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
  public  void glMapVertexAttrib2dAPPLE(int arg0,int arg1,double arg2,double arg3,int arg4,int arg5,double arg6,double arg7,int arg8,int arg9,java.nio.DoubleBuffer arg10)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib2dAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glMapVertexAttrib2dAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4ubv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4ubv(arg0);
    String txt = new String("glColor4ubv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGeniv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexGeniv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib3sARB(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3sARB(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3sARB(" +
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
  public  void glGetMapAttribParameterivNV(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMapAttribParameterivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapAttribParameterivNV(" +
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
  public  void glGetMultiTexLevelParameterivEXT(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetMultiTexLevelParameterivEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetMultiTexLevelParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformui64vNV(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib3s(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3s(arg0,arg1,arg2,arg3);
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
  public  void glColor3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glColor3i(arg0,arg1,arg2);
    String txt = new String("glColor3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenTextures(arg0,arg1);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixScalefEXT(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMatrixScalefEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMatrixScalefEXT(" +
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
  public  void glBindVertexArray(int arg0)
  {
        checkContext();
downstreamGL2.glBindVertexArray(arg0);
    String txt = new String("glBindVertexArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGendv(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexGendv(arg0,arg1,arg2);
    String txt = new String("glGetTexGendv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2fv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos3dv(arg0);
    String txt = new String("glWindowPos3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform4fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform4fvEXT(" +
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
  public  void glGetVariantFloatvEXT(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVariantFloatvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVariantFloatvEXT(" +
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
  public  void glVariantfvEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantfvEXT(arg0,arg1);
    String txt = new String("glVariantfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glMultMatrixf(arg0);
    String txt = new String("glMultMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3h(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3h(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexCoord3h(" +
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
  public  void glMultTransposeMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMultTransposeMatrixf(arg0,arg1);
    String txt = new String("glMultTransposeMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform1fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform1fvARB(" +
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
  public  void glVertexAttrib3h(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3h(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3h(" +
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
  public  void glWeightsvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightsvARB(arg0,arg1,arg2);
    String txt = new String("glWeightsvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameter4fARB(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4fARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramLocalParameter4fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
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
  public  void glSecondaryColor3bv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3bv(arg0);
    String txt = new String("glSecondaryColor3bv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3s(int arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3s(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexCoord3s(" +
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
  public  void glGetPixelMapuiv(int arg0,long arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapuiv(arg0,arg1);
    String txt = new String("glGetPixelMapuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexEnviv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexEnviv(arg0,arg1,arg2);
    String txt = new String("glGetTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyMultiTexSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2.glCopyMultiTexSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCopyMultiTexSubImage2DEXT(" +
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
  public  void glTextureParameterfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glTextureParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameterfvEXT(" +
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
  public  void glDrawBuffersATI(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDrawBuffersATI(arg0,arg1);
    String txt = new String("glDrawBuffersATI(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix3x4fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3x4fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix3x4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glPixelMapuiv(arg0,arg1,arg2,arg3);
    String txt = new String("glPixelMapuiv(" +
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
  public  void glShaderOp3EXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glShaderOp3EXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glShaderOp3EXT(" +
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
  public  void glApplyTextureEXT(int arg0)
  {
        checkContext();
downstreamGL2.glApplyTextureEXT(arg0);
    String txt = new String("glApplyTextureEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord1iv(arg0,arg1);
    String txt = new String("glTexCoord1iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3fv(arg0,arg1);
    String txt = new String("glSecondaryColor3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeighthv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertexWeighthv(arg0);
    String txt = new String("glVertexWeighthv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameter4dARB(int arg0,int arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4dARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramEnvParameter4dARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
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
  public  void glGetVertexAttribivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribivARB(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionFilter2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glConvolutionFilter2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glConvolutionFilter2D(" +
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
  public  void glVertexAttrib4bv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4bv(arg0,arg1);
    String txt = new String("glVertexAttrib4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glWindowPos3s(arg0,arg1,arg2);
    String txt = new String("glWindowPos3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs4hv(int arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribs4hv(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribs4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord3sv(arg0,arg1);
    String txt = new String("glTexCoord3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthFunc(int arg0)
  {
        checkContext();
downstreamGL2.glDepthFunc(arg0);
    String txt = new String("glDepthFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParametersI4uivNV(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParametersI4uivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParametersI4uivNV(" +
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
  public  void glMultiTexCoord1h(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1h(arg0,arg1);
    String txt = new String("glMultiTexCoord1h(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3x2fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix3x2fv(arg0,arg1,arg2,arg3);
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
  public  boolean glIsQuery(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsQuery(arg0);
    String txt = new String("glIsQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glProgramLocalParameter4dvARB(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4dvARB(arg0,arg1,arg2);
    String txt = new String("glProgramLocalParameter4dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI3uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI3uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI3uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2.glVertexAttribPointer(arg0);
    String txt = new String("glVertexAttribPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixd(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMultMatrixd(arg0,arg1);
    String txt = new String("glMultMatrixd(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform4fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform4fvARB(" +
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
  public  void glGetNamedBufferParameterui64vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetNamedBufferParameterui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetNamedBufferParameterui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1s(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1s(arg0,arg1);
    String txt = new String("glMultiTexCoord1s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterIuivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTextureParameterIuivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureParameterIuivEXT(" +
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
  public  void glAlphaFunc(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glAlphaFunc(arg0,arg1);
    String txt = new String("glAlphaFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexImage(int arg0,int arg1,int arg2,int arg3,long arg4)
  {
        checkContext();
downstreamGL2.glGetTexImage(arg0,arg1,arg2,arg3,arg4);
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
  public  void glScissor(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glScissor(arg0,arg1,arg2,arg3);
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
  public  void glGetVariantIntegervEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVariantIntegervEXT(arg0,arg1,arg2);
    String txt = new String("glGetVariantIntegervEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetConvolutionFilter(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetConvolutionFilter(arg0,arg1,arg2,arg3);
    String txt = new String("glGetConvolutionFilter(" +
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
  public  void glLoadTransposeMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLoadTransposeMatrixf(arg0,arg1);
    String txt = new String("glLoadTransposeMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompileShader(int arg0)
  {
        checkContext();
downstreamGL2.glCompileShader(arg0);
    String txt = new String("glCompileShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2iARB(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glUniform2iARB(arg0,arg1,arg2);
    String txt = new String("glUniform2iARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenProgramsARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenProgramsARB(arg0,arg1,arg2);
    String txt = new String("glGenProgramsARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexGenivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexGenivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexGenivEXT(" +
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
  public  void glFrustum(double arg0,double arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGL2.glFrustum(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetMapAttribParameterfvNV(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMapAttribParameterfvNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapAttribParameterfvNV(" +
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
  public  void glGetNamedProgramLocalParameterIivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterIivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedProgramLocalParameterIivEXT(" +
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
  public  void glColor4bv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4bv(arg0);
    String txt = new String("glColor4bv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRenderbufferStorageMultisampleCoverageNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glRenderbufferStorageMultisampleCoverageNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glRenderbufferStorageMultisampleCoverageNV(" +
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
  public  void glGetColorTableParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetColorTableParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetColorTableParameteriv(" +
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
  public  void glPixelTransformParameteriEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glPixelTransformParameteriEXT(arg0,arg1,arg2);
    String txt = new String("glPixelTransformParameteriEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord1dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord1dv(arg0);
    String txt = new String("glEvalCoord1dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetSeparableFilter(int arg0,int arg1,int arg2,long arg3,long arg4,long arg5)
  {
        checkContext();
downstreamGL2.glGetSeparableFilter(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetSeparableFilter(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<long>" +
    ", " +
    "<long>" +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteBuffers(arg0,arg1);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterIivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameterIivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameterIivEXT(" +
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
  public  void glGetMultiTexEnvivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexEnvivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexEnvivEXT(" +
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
  public  void glSetFenceNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSetFenceNV(arg0,arg1);
    String txt = new String("glSetFenceNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord3fv(arg0,arg1);
    String txt = new String("glTexCoord3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureRenderbufferEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTextureRenderbufferEXT(arg0,arg1,arg2);
    String txt = new String("glTextureRenderbufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NbvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NbvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NbvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectsv(short[] arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRectsv(arg0,arg1,arg2,arg3);
    String txt = new String("glRectsv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectsv(java.nio.ShortBuffer arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glRectsv(arg0,arg1);
    String txt = new String("glRectsv(" +
    "<java.nio.ShortBuffer>" +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3uiv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4uivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4uivARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4uivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFlushPixelDataRangeNV(int arg0)
  {
        checkContext();
downstreamGL2.glFlushPixelDataRangeNV(arg0);
    String txt = new String("glFlushPixelDataRangeNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4usv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4usv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NusvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NusvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLogicOp(int arg0)
  {
        checkContext();
downstreamGL2.glLogicOp(arg0);
    String txt = new String("glLogicOp(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glFramebufferTexture(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib1dvARB(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1dvARB(arg0,arg1);
    String txt = new String("glVertexAttrib1dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glWeightPointer(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib2sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2sv(arg0,arg1);
    String txt = new String("glVertexAttrib2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureMaterialEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTextureMaterialEXT(arg0,arg1);
    String txt = new String("glTextureMaterialEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightubvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightubvARB(arg0,arg1);
    String txt = new String("glWeightubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramivARB(" +
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
  public  void glMatrixPopEXT(int arg0)
  {
        checkContext();
downstreamGL2.glMatrixPopEXT(arg0);
    String txt = new String("glMatrixPopEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBufferParameterui64vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetBufferParameterui64vNV(arg0,arg1,arg2,arg3);
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
  public  void glNormal3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3sv(arg0,arg1);
    String txt = new String("glNormal3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteObjectARB(int arg0)
  {
        checkContext();
downstreamGL2.glDeleteObjectARB(arg0);
    String txt = new String("glDeleteObjectARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexParameterIuiv(arg0,arg1,arg2);
    String txt = new String("glTexParameterIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeightfEXT(float arg0)
  {
        checkContext();
downstreamGL2.glVertexWeightfEXT(arg0);
    String txt = new String("glVertexWeightfEXT(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform1iEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glProgramUniform1iEXT(arg0,arg1,arg2);
    String txt = new String("glProgramUniform1iEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLocalConstantBooleanvEXT(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetLocalConstantBooleanvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLocalConstantBooleanvEXT(" +
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
  public  void glRasterPos3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos3iv(arg0,arg1);
    String txt = new String("glRasterPos3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorTableParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glColorTableParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glColorTableParameterfv(" +
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
  public  void glCompressedTextureSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,int arg10,java.nio.Buffer arg11)
  {
        checkContext();
downstreamGL2.glCompressedTextureSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glCompressedTextureSubImage3DEXT(" +
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
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameterI4ivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParameterI4ivEXT(" +
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
  public  void glProgramUniform2fEXT(int arg0,int arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2fEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2fEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixd(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glMultMatrixd(arg0);
    String txt = new String("glMultMatrixd(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameter4dvARB(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4dvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParameter4dvARB(" +
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
  public  void glMapParameterfvNV(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMapParameterfvNV(arg0,arg1,arg2,arg3);
    String txt = new String("glMapParameterfvNV(" +
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
  public  void glVariantsvEXT(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantsvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantsvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameter4fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4fvARB(arg0,arg1,arg2);
    String txt = new String("glProgramEnvParameter4fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4iARB(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniform4iARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniform4iARB(" +
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
  public  void glGetPolygonStipple(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glGetPolygonStipple(arg0);
    String txt = new String("glGetPolygonStipple(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsFramebuffer(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsFramebuffer(arg0);
    String txt = new String("glIsFramebuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTexParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexParameteri(arg0,arg1,arg2);
    String txt = new String("glTexParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glFeedbackBuffer(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glFeedbackBuffer(arg0,arg1,arg2);
    String txt = new String("glFeedbackBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferRenderbuffer(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glFramebufferRenderbuffer(arg0,arg1,arg2,arg3);
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
  public  void glColor4hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4hv(arg0,arg1);
    String txt = new String("glColor4hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1dv(arg0,arg1);
    String txt = new String("glVertexAttrib1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSeparableFilter2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glSeparableFilter2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glSeparableFilter2D(" +
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
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSeparableFilter2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6,long arg7)
  {
        checkContext();
downstreamGL2.glSeparableFilter2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glSeparableFilter2D(" +
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
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glVertex3s(arg0,arg1,arg2);
    String txt = new String("glVertex3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform2fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform2fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform2fvEXT(" +
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
  public  boolean glIsEnabledi(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsEnabledi(arg0,arg1);
    String txt = new String("glIsEnabledi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetShaderiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetShaderiv(arg0,arg1,arg2,arg3);
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
  public  void glBlendFuncSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glBlendFuncSeparate(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib1dvARB(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1dvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib1fAPPLE(int arg0,int arg1,float arg2,float arg3,int arg4,int arg5,java.nio.FloatBuffer arg6)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib1fAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMapVertexAttrib1fAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointerARB(int arg0,int arg1,int arg2,boolean arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glVertexAttribPointerARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glVertexAttribPointerARB(" +
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
  public  void glVertex3h(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glVertex3h(arg0,arg1,arg2);
    String txt = new String("glVertex3h(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramLocalParameterIuivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterIuivNV(arg0,arg1,arg2);
    String txt = new String("glGetProgramLocalParameterIuivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenerateMultiTexMipmapEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glGenerateMultiTexMipmapEXT(arg0,arg1);
    String txt = new String("glGenerateMultiTexMipmapEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramVertexLimitNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glProgramVertexLimitNV(arg0,arg1);
    String txt = new String("glProgramVertexLimitNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordfv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFogCoordfv(arg0,arg1);
    String txt = new String("glFogCoordfv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParametersI4uivEXT(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParametersI4uivEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glNamedProgramLocalParametersI4uivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glColorFormatNV(arg0,arg1,arg2);
    String txt = new String("glColorFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramLocalParameterIivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterIivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramLocalParameterIivNV(" +
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
  public  void glDisableVertexAttribArrayARB(int arg0)
  {
        checkContext();
downstreamGL2.glDisableVertexAttribArrayARB(arg0);
    String txt = new String("glDisableVertexAttribArrayARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform2iEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2iEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2iEXT(" +
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
  public  void glMultiTexGenivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGenivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGenivEXT(" +
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
  public  void glEvalCoord1dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord1dv(arg0,arg1);
    String txt = new String("glEvalCoord1dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableVariantClientStateEXT(int arg0)
  {
        checkContext();
downstreamGL2.glEnableVariantClientStateEXT(arg0);
    String txt = new String("glEnableVariantClientStateEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisablei(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDisablei(arg0,arg1);
    String txt = new String("glDisablei(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyMultiTexSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9)
  {
        checkContext();
downstreamGL2.glCopyMultiTexSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCopyMultiTexSubImage3DEXT(" +
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
  public  void glVertex2iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2iv(arg0,arg1);
    String txt = new String("glVertex2iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex4hv(arg0,arg1);
    String txt = new String("glVertex4hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexParameterIiv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformui64NV(int arg0,long arg1)
  {
        checkContext();
downstreamGL2.glUniformui64NV(arg0,arg1);
    String txt = new String("glUniformui64NV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribIuiv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribIuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearColorIi(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearColorIi(arg0,arg1,arg2,arg3);
    String txt = new String("glClearColorIi(" +
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
  public  java.lang.String glGetString(int arg0)
  {
        checkContext();
    java.lang.String _res = downstreamGL2.glGetString(arg0);
    String txt = new String("glGetString(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTransformFeedbackVaryings(int arg0,int arg1,java.lang.String[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTransformFeedbackVaryings(arg0,arg1,arg2,arg3);
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
  public  void glGetInvariantFloatvEXT(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetInvariantFloatvEXT(arg0,arg1,arg2);
    String txt = new String("glGetInvariantFloatvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glMultiTexSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glMultiTexSubImage1DEXT(" +
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
  public  void glEndVertexShaderEXT()
  {
        checkContext();
downstreamGL2.glEndVertexShaderEXT();
    String txt = new String("glEndVertexShaderEXT(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameterI4iEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4iEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glNamedProgramLocalParameterI4iEXT(" +
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
  public  void glEvalPoint2(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalPoint2(arg0,arg1);
    String txt = new String("glEvalPoint2(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord4hv(arg0);
    String txt = new String("glTexCoord4hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelTransferf(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPixelTransferf(arg0,arg1);
    String txt = new String("glPixelTransferf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib2dAPPLE(int arg0,int arg1,double arg2,double arg3,int arg4,int arg5,double arg6,double arg7,int arg8,int arg9,double[] arg10,int arg11)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib2dAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glMapVertexAttrib2dAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg11).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClipPlane(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glClipPlane(arg0,arg1);
    String txt = new String("glClipPlane(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorTableParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glColorTableParameteriv(arg0,arg1,arg2);
    String txt = new String("glColorTableParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapfv(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glPixelMapfv(arg0,arg1,arg2);
    String txt = new String("glPixelMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glCreateShaderObjectARB(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glCreateShaderObjectARB(arg0);
    String txt = new String("glCreateShaderObjectARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetClipPlane(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetClipPlane(arg0,arg1);
    String txt = new String("glGetClipPlane(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTextureSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glCopyTextureSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCopyTextureSubImage1DEXT(" +
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
  public  void glGetColorTableParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetColorTableParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetColorTableParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnviv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexEnviv(arg0,arg1,arg2);
    String txt = new String("glTexEnviv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glAreTexturesResident(int arg0,java.nio.IntBuffer arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
    boolean _res = downstreamGL2.glAreTexturesResident(arg0,arg1,arg2);
    String txt = new String("glAreTexturesResident(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glProgramLocalParameter4fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4fvARB(arg0,arg1,arg2);
    String txt = new String("glProgramLocalParameter4fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetQueryObjectuiv(arg0,arg1,arg2,arg3);
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
  public  void glGetCompressedMultiTexImageEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetCompressedMultiTexImageEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetCompressedMultiTexImageEXT(" +
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
  public  void glEvalCoord1fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord1fv(arg0);
    String txt = new String("glEvalCoord1fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glConvolutionParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glConvolutionParameterfv(" +
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
downstreamGL2.glGetTexEnvfv(arg0,arg1,arg2,arg3);
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
  public  void glDrawRangeElements(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glDrawRangeElements(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetTexGenfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexGenfv(arg0,arg1,arg2);
    String txt = new String("glGetTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantPointerEXT(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glVariantPointerEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glVariantPointerEXT(" +
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
  public  void glNamedBufferDataEXT(int arg0,int arg1,java.nio.Buffer arg2,int arg3)
  {
        checkContext();
downstreamGL2.glNamedBufferDataEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedBufferDataEXT(" +
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
  public  void glGetVariantBooleanvEXT(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVariantBooleanvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVariantBooleanvEXT(" +
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
  public  void glTexBuffer(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexBuffer(arg0,arg1,arg2);
    String txt = new String("glTexBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexEnvfv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord1sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1sv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1ui(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glUniform1ui(arg0,arg1);
    String txt = new String("glUniform1ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGendv(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexGendv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTexGendv(" +
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
  public  void glWindowPos3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos3dv(arg0,arg1);
    String txt = new String("glWindowPos3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2.glTexCoordPointer(arg0);
    String txt = new String("glTexCoordPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glConvolutionParameteriv(arg0,arg1,arg2);
    String txt = new String("glConvolutionParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameters4fvEXT(int arg0,int arg1,int arg2,int arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameters4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParameters4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetFloatv(arg0,arg1,arg2);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3i(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGendEXT(int arg0,int arg1,int arg2,double arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGendEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGendEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameterivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glTextureParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameterivEXT(" +
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
  public  void glVertexAttrib4uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4uiv(arg0,arg1);
    String txt = new String("glVertexAttrib4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexParameteriv(arg0,arg1,arg2);
    String txt = new String("glTexParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3dARB(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3dARB(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3dARB(" +
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
  public  void glColor4uiv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4uiv(arg0);
    String txt = new String("glColor4uiv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glCopyTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glUniform4iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform4iv(arg0,arg1,arg2);
    String txt = new String("glUniform4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultTransposedEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixMultTransposedEXT(arg0,arg1);
    String txt = new String("glMatrixMultTransposedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetBooleanIndexedv(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetBooleanIndexedv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetBooleanIndexedv(" +
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
  public  void glGetMaterialfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMaterialfv(arg0,arg1,arg2,arg3);
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
  public  void glProgramUniform1uiEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glProgramUniform1uiEXT(arg0,arg1,arg2);
    String txt = new String("glProgramUniform1uiEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenFramebuffers(arg0,arg1);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLoadMatrixf(arg0,arg1);
    String txt = new String("glLoadMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterIiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexParameterIiv(arg0,arg1,arg2);
    String txt = new String("glTexParameterIiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightusvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightusvARB(arg0,arg1,arg2);
    String txt = new String("glWeightusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGenVertexShadersEXT(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glGenVertexShadersEXT(arg0);
    String txt = new String("glGenVertexShadersEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetNamedFramebufferAttachmentParameterivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedFramebufferAttachmentParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedFramebufferAttachmentParameterivEXT(" +
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
  public  void glMultiDrawElements(int arg0,int[] arg1,int arg2,int arg3,java.nio.Buffer[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glMultiDrawElements(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glPointParameteriv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glPointParameteriv(arg0,arg1);
    String txt = new String("glPointParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformuiv(arg0,arg1,arg2);
    String txt = new String("glGetUniformuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix3fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glVertexAttrib4ubv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ubv(arg0,arg1);
    String txt = new String("glVertexAttrib4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedFramebufferTextureEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTextureEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedFramebufferTextureEXT(" +
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
  public  void glTexCoord1dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord1dv(arg0,arg1);
    String txt = new String("glTexCoord1dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilMask(int arg0)
  {
        checkContext();
downstreamGL2.glStencilMask(arg0);
    String txt = new String("glStencilMask(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRecti(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRecti(arg0,arg1,arg2,arg3);
    String txt = new String("glRecti(" +
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
  public  void glSecondaryColorPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glSecondaryColorPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glSecondaryColorPointer(" +
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
  public  void glUniform4fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform4fv(arg0,arg1,arg2,arg3);
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
  public  void glGetMapiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMapiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapiv(" +
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
  public  void glMultiTexCoord4fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4fv(arg0,arg1);
    String txt = new String("glMultiTexCoord4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsEnabledIndexed(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsEnabledIndexed(arg0,arg1);
    String txt = new String("glIsEnabledIndexed(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glRects(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glRects(arg0,arg1,arg2,arg3);
    String txt = new String("glRects(" +
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
  public  void glProgramUniformMatrix2x3fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2x3fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix2x3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI1uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI1uiv(arg0,arg1);
    String txt = new String("glVertexAttribI1uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMap2f(int arg0,float arg1,float arg2,int arg3,int arg4,float arg5,float arg6,int arg7,int arg8,float[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glMap2f(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glMap2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCallList(int arg0)
  {
        checkContext();
downstreamGL2.glCallList(arg0);
    String txt = new String("glCallList(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawPixels(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glDrawPixels(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawPixels(" +
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
  public  void glRasterPos3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glRasterPos3i(arg0,arg1,arg2);
    String txt = new String("glRasterPos3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex2dv(arg0);
    String txt = new String("glVertex2dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantbvEXT(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantbvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantbvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParametersI4ivEXT(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParametersI4ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParametersI4ivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform1uivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform1uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform1uivEXT(" +
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
  public  void glVertexAttrib2svARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2svARB(arg0,arg1);
    String txt = new String("glVertexAttrib2svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapControlPointsNV(int arg0,int arg1,int arg2,int arg3,int arg4,boolean arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glGetMapControlPointsNV(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glGetMapControlPointsNV(" +
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
    "<boolean>" +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetObjectParameterfvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetObjectParameterfvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetObjectParameterfvARB(" +
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
  public  void glProgramUniform4uiEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniform4uiEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniform4uiEXT(" +
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
  public  void glRasterPos3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos3dv(arg0);
    String txt = new String("glRasterPos3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex4fv(arg0,arg1);
    String txt = new String("glVertex4fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterfv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glPointParameterfv(arg0,arg1,arg2);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMap1f(int arg0,float arg1,float arg2,int arg3,int arg4,float[] arg5,int arg6)
  {
        checkContext();
downstreamGL2.glMap1f(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMap1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeightPointerEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glVertexWeightPointerEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexWeightPointerEXT(" +
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
  public  void glClear(int arg0)
  {
        checkContext();
downstreamGL2.glClear(arg0);
    String txt = new String("glClear(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4sv(arg0,arg1);
    String txt = new String("glColor4sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1iARB(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glUniform1iARB(arg0,arg1);
    String txt = new String("glUniform1iARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectf(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glRectf(arg0,arg1,arg2,arg3);
    String txt = new String("glRectf(" +
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
  public  void glBitmap(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2.glBitmap(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glBitmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapdv(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMapdv(arg0,arg1,arg2);
    String txt = new String("glGetMapdv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectd(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glRectd(arg0,arg1,arg2,arg3);
    String txt = new String("glRectd(" +
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
  public  void glTexCoord4sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord4sv(arg0);
    String txt = new String("glTexCoord4sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveUniformARB(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glGetActiveUniformARB(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glGetActiveUniformARB(" +
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
  public  void glSecondaryColor3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3sv(arg0,arg1);
    String txt = new String("glSecondaryColor3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterdvEXT(int arg0,int arg1,int arg2,java.nio.DoubleBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterdvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramLocalParameterdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogi(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFogi(arg0,arg1);
    String txt = new String("glFogi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightbvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glWeightbvARB(arg0,arg1);
    String txt = new String("glWeightbvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameteriEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameteriEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameteriEXT(" +
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
  public  void glDeleteFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteFramebuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameter4fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedProgramLocalParameter4fvEXT(" +
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
  public  void glProgramUniform1fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform1fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform1fvEXT(" +
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
  public  void glProgramUniform3ivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3ivEXT(" +
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
  public  void glGetShaderSourceARB(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetShaderSourceARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetShaderSourceARB(" +
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
  public  void glFramebufferDrawBuffersEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glFramebufferDrawBuffersEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glFramebufferDrawBuffersEXT(" +
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
  public  void glGetHistogramParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetHistogramParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetHistogramParameteriv(" +
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
  public  void glBeginConditionalRender(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBeginConditionalRender(arg0,arg1);
    String txt = new String("glBeginConditionalRender(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4usv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4usv(arg0,arg1);
    String txt = new String("glColor4usv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NsvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NsvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NsvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilFunc(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glStencilFunc(arg0,arg1,arg2);
    String txt = new String("glStencilFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform2iv(arg0,arg1,arg2,arg3);
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
  public  int glBindParameterEXT(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glBindParameterEXT(arg0);
    String txt = new String("glBindParameterEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetQueryObjectiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetQueryObjectiv(arg0,arg1,arg2,arg3);
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
  public  void glProgramUniformMatrix4x2fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4x2fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix4x2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDetachObjectARB(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDetachObjectARB(arg0,arg1);
    String txt = new String("glDetachObjectARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLighti(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glLighti(arg0,arg1,arg2);
    String txt = new String("glLighti(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordhv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFogCoordhv(arg0,arg1);
    String txt = new String("glFogCoordhv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos3fv(arg0);
    String txt = new String("glWindowPos3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenFencesNV(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenFencesNV(arg0,arg1);
    String txt = new String("glGenFencesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4ubv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4ubv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterIivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterIivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexParameterIivEXT(" +
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
  public  void glMultiTexCoord1hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1hv(arg0,arg1);
    String txt = new String("glMultiTexCoord1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetColorTable(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glGetColorTable(arg0,arg1,arg2,arg3);
    String txt = new String("glGetColorTable(" +
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
  public  void glGenQueries(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenQueries(arg0,arg1,arg2);
    String txt = new String("glGenQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform2uivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform2uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform2uivEXT(" +
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
  public  void glStringMarkerGREMEDY(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGL2.glStringMarkerGREMEDY(arg0,arg1);
    String txt = new String("glStringMarkerGREMEDY(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMakeBufferNonResidentNV(int arg0)
  {
        checkContext();
downstreamGL2.glMakeBufferNonResidentNV(arg0);
    String txt = new String("glMakeBufferNonResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexi(int arg0)
  {
        checkContext();
downstreamGL2.glIndexi(arg0);
    String txt = new String("glIndexi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearDepth(double arg0)
  {
        checkContext();
downstreamGL2.glClearDepth(arg0);
    String txt = new String("glClearDepth(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4dv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexuivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixIndexuivARB(arg0,arg1);
    String txt = new String("glMatrixIndexuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glInterleavedArrays(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glInterleavedArrays(arg0,arg1,arg2);
    String txt = new String("glInterleavedArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform4uiv(arg0,arg1,arg2,arg3);
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
  public  void glVertex4iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex4iv(arg0);
    String txt = new String("glVertex4iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetOcclusionQueryivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetOcclusionQueryivNV(arg0,arg1,arg2);
    String txt = new String("glGetOcclusionQueryivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMaterialiv(arg0,arg1,arg2);
    String txt = new String("glGetMaterialiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3uiv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3uiv(arg0,arg1);
    String txt = new String("glSecondaryColor3uiv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1fv(arg0);
    String txt = new String("glTexCoord1fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeightfvEXT(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertexWeightfvEXT(arg0);
    String txt = new String("glVertexWeightfvEXT(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glClientAttribDefaultEXT(int arg0)
  {
        checkContext();
downstreamGL2.glClientAttribDefaultEXT(arg0);
    String txt = new String("glClientAttribDefaultEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4iv(arg0);
    String txt = new String("glColor4iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3hv(arg0,arg1);
    String txt = new String("glNormal3hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexubv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glIndexubv(arg0);
    String txt = new String("glIndexubv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColorPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glSecondaryColorPointer(arg0,arg1,arg2,arg3);
    String txt = new String("glSecondaryColorPointer(" +
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
  public  void glStencilFuncSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glStencilFuncSeparate(arg0,arg1,arg2,arg3);
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
  public  void glTexCoord4iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord4iv(arg0,arg1);
    String txt = new String("glTexCoord4iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameters4fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramLocalParameters4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramLocalParameters4fvEXT(" +
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
  public  void glActiveTexture(int arg0)
  {
        checkContext();
downstreamGL2.glActiveTexture(arg0);
    String txt = new String("glActiveTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3ivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform3ivARB(arg0,arg1,arg2);
    String txt = new String("glUniform3ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawPixels(int arg0,int arg1,int arg2,int arg3,long arg4)
  {
        checkContext();
downstreamGL2.glDrawPixels(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glDrawPixels(" +
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
  public  void glMultiTexCoord3sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3sv(arg0,arg1);
    String txt = new String("glMultiTexCoord3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexParameterivEXT(" +
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
  public  void glProgramUniform4iEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniform4iEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniform4iEXT(" +
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
  public  void glGetVertexAttribfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribfv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGeni(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexGeni(arg0,arg1,arg2);
    String txt = new String("glTexGeni(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib2fAPPLE(int arg0,int arg1,float arg2,float arg3,int arg4,int arg5,float arg6,float arg7,int arg8,int arg9,java.nio.FloatBuffer arg10)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib2fAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glMapVertexAttrib2fAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg9).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexParameterfv(arg0,arg1,arg2,arg3);
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
  public  void glColor3ub(byte arg0,byte arg1,byte arg2)
  {
        checkContext();
downstreamGL2.glColor3ub(arg0,arg1,arg2);
    String txt = new String("glColor3ub(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedRenderbufferStorageEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glNamedRenderbufferStorageEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedRenderbufferStorageEXT(" +
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
  public  void glTexGendv(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexGendv(arg0,arg1,arg2);
    String txt = new String("glTexGendv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetUniformivARB(" +
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
  public  void glTextureLightEXT(int arg0)
  {
        checkContext();
downstreamGL2.glTextureLightEXT(arg0);
    String txt = new String("glTextureLightEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexParameterivEXT(" +
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
  public  void glDrawArraysInstanced(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glDrawArraysInstanced(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord3hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3hv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteOcclusionQueriesNV(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteOcclusionQueriesNV(arg0,arg1,arg2);
    String txt = new String("glDeleteOcclusionQueriesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeightPointerEXT(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glVertexWeightPointerEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexWeightPointerEXT(" +
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
  public  void glGetVertexAttribfvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribfvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribfvARB(" +
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
  public  void glGetLocalConstantFloatvEXT(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetLocalConstantFloatvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLocalConstantFloatvEXT(" +
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
  public  void glGetBufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetBufferParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glNormal3bv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3bv(arg0);
    String txt = new String("glNormal3bv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1dv(arg0,arg1);
    String txt = new String("glMultiTexCoord1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerIndexedv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetIntegerIndexedv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetIntegerIndexedv(" +
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
  public  void glRasterPos2dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2dv(arg0,arg1);
    String txt = new String("glRasterPos2dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPolygonStipple(long arg0)
  {
        checkContext();
downstreamGL2.glPolygonStipple(arg0);
    String txt = new String("glPolygonStipple(" +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4s(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4s(arg0,arg1,arg2,arg3,arg4);
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
  public  void glIndexub(byte arg0)
  {
        checkContext();
downstreamGL2.glIndexub(arg0);
    String txt = new String("glIndexub(" +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsTexture(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsTexture(arg0);
    String txt = new String("glIsTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib4dvARB(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4dvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVariantEnabledEXT(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsVariantEnabledEXT(arg0,arg1);
    String txt = new String("glIsVariantEnabledEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glNamedProgramLocalParameter4dvEXT(int arg0,int arg1,int arg2,double[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4dvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParameter4dvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4f(arg0,arg1,arg2,arg3,arg4);
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
  public  void glTexCoord1sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1sv(arg0);
    String txt = new String("glTexCoord1sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4h(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4h(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4h(" +
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
  public  void glVertexAttrib4NivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NivARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4d(int arg0,double arg1,double arg2,double arg3,double arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4d(arg0,arg1,arg2,arg3,arg4);
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
  public  void glFlushVertexArrayRangeAPPLE(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGL2.glFlushVertexArrayRangeAPPLE(arg0,arg1);
    String txt = new String("glFlushVertexArrayRangeAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramLocalParameterdvARB(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterdvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramLocalParameterdvARB(" +
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
  public  void glCompressedTextureSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glCompressedTextureSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedTextureSubImage1DEXT(" +
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
  public  void glProgramUniform1ivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform1ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform1ivEXT(" +
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
  public  void glVariantdvEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantdvEXT(arg0,arg1);
    String txt = new String("glVariantdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnvi(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexEnvi(arg0,arg1,arg2);
    String txt = new String("glTexEnvi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexGeniv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexGeniv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib1svARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1svARB(arg0,arg1);
    String txt = new String("glVertexAttrib1svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSampleCoverage(float arg0,boolean arg1)
  {
        checkContext();
downstreamGL2.glSampleCoverage(arg0,arg1);
    String txt = new String("glSampleCoverage(" +
    "<float>" +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs1hv(int arg0,int arg1,java.nio.ShortBuffer arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribs1hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribs1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameterI4ivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4ivNV(arg0,arg1,arg2);
    String txt = new String("glProgramEnvParameterI4ivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2hv(arg0,arg1);
    String txt = new String("glTexCoord2hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  java.nio.ByteBuffer glMapNamedBufferEXT(int arg0,int arg1)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2.glMapNamedBufferEXT(arg0,arg1);
    String txt = new String("glMapNamedBufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glProgramUniformMatrix2fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveUniform(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glGetActiveUniform(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glCopyTextureImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2.glCopyTextureImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCopyTextureImage1DEXT(" +
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
  public  void glUnlockArraysEXT()
  {
        checkContext();
downstreamGL2.glUnlockArraysEXT();
    String txt = new String("glUnlockArraysEXT(" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsPBOPackEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2.glIsPBOPackEnabled();
    String txt = new String("glIsPBOPackEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetBooleani_v(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetBooleani_v(arg0,arg1,arg2);
    String txt = new String("glGetBooleani_v(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferData(int arg0,int arg1,java.nio.Buffer arg2,int arg3)
  {
        checkContext();
downstreamGL2.glBufferData(arg0,arg1,arg2,arg3);
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
  public  void glMaterialfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glMaterialfv(arg0,arg1,arg2);
    String txt = new String("glMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4iv(arg0,arg1);
    String txt = new String("glVertexAttrib4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetColorTable(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetColorTable(arg0,arg1,arg2,arg3);
    String txt = new String("glGetColorTable(" +
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
  public  void glGetProgramiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramiv(arg0,arg1,arg2);
    String txt = new String("glGetProgramiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameterI4ivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4ivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParameterI4ivNV(" +
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
  public  void glProgramUniform3iEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3iEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3iEXT(" +
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
  public  void glVertexAttribI1iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI1iv(arg0,arg1);
    String txt = new String("glVertexAttribI1iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribdv(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribdv(arg0,arg1,arg2,arg3);
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
  public  void glGetMultiTexLevelParameterfvEXT(int arg0,int arg1,int arg2,int arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexLevelParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexLevelParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3ui(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3ui(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenBuffers(arg0,arg1,arg2);
    String txt = new String("glGenBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetConvolutionParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetConvolutionParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetConvolutionParameterfv(" +
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
  public  void glInterleavedArrays(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glInterleavedArrays(arg0,arg1,arg2);
    String txt = new String("glInterleavedArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFogCoordFormatNV(arg0,arg1);
    String txt = new String("glFogCoordFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2dARB(int arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2dARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2dARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixRotatefEXT(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glMatrixRotatefEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMatrixRotatefEXT(" +
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
  public  void glVertex2hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex2hv(arg0);
    String txt = new String("glVertex2hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTextureParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetTextureParameterivEXT(" +
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
  public  void glColor3uiv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3uiv(arg0,arg1);
    String txt = new String("glColor3uiv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformfv(arg0,arg1,arg2);
    String txt = new String("glGetUniformfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedFramebufferTexture2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTexture2DEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedFramebufferTexture2DEXT(" +
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
  public  void glGetRenderbufferParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetRenderbufferParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glTextureImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2.glTextureImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glTextureImage3DEXT(" +
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
  public  void glValidateProgram(int arg0)
  {
        checkContext();
downstreamGL2.glValidateProgram(arg0);
    String txt = new String("glValidateProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord2fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord2fv(arg0,arg1);
    String txt = new String("glEvalCoord2fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPNTrianglesfATI(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPNTrianglesfATI(arg0,arg1);
    String txt = new String("glPNTrianglesfATI(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix4fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapusv(int arg0,int arg1,java.nio.ShortBuffer arg2)
  {
        checkContext();
downstreamGL2.glPixelMapusv(arg0,arg1,arg2);
    String txt = new String("glPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetConvolutionParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetConvolutionParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetConvolutionParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSwapAPPLE()
  {
        checkContext();
downstreamGL2.glSwapAPPLE();
    String txt = new String("glSwapAPPLE(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetCompressedTextureImageEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetCompressedTextureImageEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetCompressedTextureImageEXT(" +
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
  public  void glDepthBoundsEXT(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glDepthBoundsEXT(arg0,arg1);
    String txt = new String("glDepthBoundsEXT(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramEnvParameterIuivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterIuivNV(arg0,arg1,arg2);
    String txt = new String("glGetProgramEnvParameterIuivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTransformFeedbackVarying(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glGetTransformFeedbackVarying(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glUseProgram(int arg0)
  {
        checkContext();
downstreamGL2.glUseProgram(arg0);
    String txt = new String("glUseProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenerateMipmap(int arg0)
  {
        checkContext();
downstreamGL2.glGenerateMipmap(arg0);
    String txt = new String("glGenerateMipmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsList(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsList(arg0);
    String txt = new String("glIsList(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniform2fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform2fvARB(arg0,arg1,arg2);
    String txt = new String("glUniform2fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantubvEXT(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantubvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantubvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelf(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glLightModelf(arg0,arg1);
    String txt = new String("glLightModelf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramBufferParametersIivNV(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersIivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramBufferParametersIivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fvARB(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix2fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix2fvARB(" +
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
  public  void glMatrixLoadIdentityEXT(int arg0)
  {
        checkContext();
downstreamGL2.glMatrixLoadIdentityEXT(arg0);
    String txt = new String("glMatrixLoadIdentityEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos2sv(arg0);
    String txt = new String("glWindowPos2sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribPointerARB(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glVertexAttribPointerARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glVertexAttribPointerARB(" +
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
  public  void glMultiTexParameterfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameterfvEXT(" +
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
  public  void glEvalCoord1f(float arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord1f(arg0);
    String txt = new String("glEvalCoord1f(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos2iv(arg0);
    String txt = new String("glWindowPos2iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1f(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1f(arg0,arg1);
    String txt = new String("glVertexAttrib1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4ui(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glVertexAttribI4ui(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttribI4ui(" +
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
  public  void glGetVertexAttribIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribIiv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4Nuiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nuiv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs3hv(int arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribs3hv(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribs3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3dv(arg0);
    String txt = new String("glColor3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMaterialf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glMaterialf(arg0,arg1,arg2);
    String txt = new String("glMaterialf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1iv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord1iv(" +
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
downstreamGL2.glVertexAttrib4Nub(arg0,arg1,arg2,arg3,arg4);
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
  public  void glDepthMask(boolean arg0)
  {
        checkContext();
downstreamGL2.glDepthMask(arg0);
    String txt = new String("glDepthMask(" +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI3iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI3iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexBlendARB(int arg0)
  {
        checkContext();
downstreamGL2.glVertexBlendARB(arg0);
    String txt = new String("glVertexBlendARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glAttachShader(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glAttachShader(arg0,arg1);
    String txt = new String("glAttachShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindFragDataLocation(int arg0,int arg1,java.lang.String arg2)
  {
        checkContext();
downstreamGL2.glBindFragDataLocation(arg0,arg1,arg2);
    String txt = new String("glBindFragDataLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4bv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4bv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetOcclusionQueryuivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetOcclusionQueryuivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetOcclusionQueryuivNV(" +
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
  public  void glSelectBuffer(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glSelectBuffer(arg0,arg1);
    String txt = new String("glSelectBuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableVertexAttribArray(int arg0)
  {
        checkContext();
downstreamGL2.glDisableVertexAttribArray(arg0);
    String txt = new String("glDisableVertexAttribArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4usvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4usvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4usvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nusv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nusv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3usv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3usv(arg0,arg1);
    String txt = new String("glSecondaryColor3usv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureLevelParameterivEXT(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetTextureLevelParameterivEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetTextureLevelParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableClientStateIndexedEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDisableClientStateIndexedEXT(arg0,arg1);
    String txt = new String("glDisableClientStateIndexedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLinkProgram(int arg0)
  {
        checkContext();
downstreamGL2.glLinkProgram(arg0);
    String txt = new String("glLinkProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindVertexShaderEXT(int arg0)
  {
        checkContext();
downstreamGL2.glBindVertexShaderEXT(arg0);
    String txt = new String("glBindVertexShaderEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterIuivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterIuivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexParameterIuivEXT(" +
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
  public  void glUniformMatrix4fvARB(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix4fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniformMatrix4fvARB(" +
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
  public  void glMultiTexCoord3f(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3f(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexCoord3f(" +
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
  public  void glVertexAttrib4NbvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NbvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4NbvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3f(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3f(arg0,arg1,arg2,arg3);
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
  public  void glVertex3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glVertex3d(arg0,arg1,arg2);
    String txt = new String("glVertex3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2h(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2h(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2h(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glNormal3i(arg0,arg1,arg2);
    String txt = new String("glNormal3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2i(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexiv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexiv(arg0,arg1);
    String txt = new String("glIndexiv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedBufferParameterivEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetNamedBufferParameterivEXT(arg0,arg1,arg2);
    String txt = new String("glGetNamedBufferParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetFragDataLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetFragDataLocation(arg0,arg1);
    String txt = new String("glGetFragDataLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMultiTexCoord2f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2f(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapuiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapuiv(arg0,arg1);
    String txt = new String("glGetPixelMapuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2d(int arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2d(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableVertexAttribAPPLE(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEnableVertexAttribAPPLE(arg0,arg1);
    String txt = new String("glEnableVertexAttribAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexArrayParameteriAPPLE(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexArrayParameteriAPPLE(arg0,arg1);
    String txt = new String("glVertexArrayParameteriAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexLevelParameterfv(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetTexLevelParameterfv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glMinmax(int arg0,int arg1,boolean arg2)
  {
        checkContext();
downstreamGL2.glMinmax(arg0,arg1,arg2);
    String txt = new String("glMinmax(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<boolean>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2d(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2d(arg0,arg1);
    String txt = new String("glWindowPos2d(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2f(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2f(arg0,arg1);
    String txt = new String("glWindowPos2f(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glFogfv(arg0,arg1);
    String txt = new String("glFogfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex3sv(arg0,arg1);
    String txt = new String("glVertex3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2i(arg0,arg1);
    String txt = new String("glWindowPos2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetHandleARB(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glGetHandleARB(arg0);
    String txt = new String("glGetHandleARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniformMatrix3fvARB(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix3fvARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glUniformMatrix3fvARB(" +
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
  public  void glCopyConvolutionFilter1D(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glCopyConvolutionFilter1D(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glCopyConvolutionFilter1D(" +
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
  public  void glVertexAttrib4hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4bv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4bv(arg0,arg1);
    String txt = new String("glColor4bv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2s(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2s(arg0,arg1);
    String txt = new String("glWindowPos2s(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightModelfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glLightModelfv(arg0,arg1);
    String txt = new String("glLightModelfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos3sv(arg0,arg1);
    String txt = new String("glRasterPos3sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glNewBufferRegion(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glNewBufferRegion(arg0);
    String txt = new String("glNewBufferRegion(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetMultiTexGenfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexGenfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexGenfvEXT(" +
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
  public  void glClearBufferfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glClearBufferfv(arg0,arg1,arg2);
    String txt = new String("glClearBufferfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyMultiTexImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2.glCopyMultiTexImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCopyMultiTexImage2DEXT(" +
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
  public  void glIndexdv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glIndexdv(arg0);
    String txt = new String("glIndexdv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformfvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetUniformfvARB(arg0,arg1,arg2);
    String txt = new String("glGetUniformfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nsv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nsv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nsv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glValidateProgramARB(int arg0)
  {
        checkContext();
downstreamGL2.glValidateProgramARB(arg0);
    String txt = new String("glValidateProgramARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterfEXT(int arg0,int arg1,int arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameterfEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameterfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,long arg7)
  {
        checkContext();
downstreamGL2.glCompressedTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glProgramLocalParametersI4ivNV(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramLocalParametersI4ivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramLocalParametersI4ivNV(" +
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
  public  void glVertexAttribI4sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4sv(arg0,arg1);
    String txt = new String("glVertexAttribI4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos2fv(arg0);
    String txt = new String("glRasterPos2fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord4d(int arg0,double arg1,double arg2,double arg3,double arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4d(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4d(" +
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
  public  void glGetTexParameterIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexParameterIuiv(arg0,arg1,arg2,arg3);
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
  public  void glGenFencesAPPLE(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenFencesAPPLE(arg0,arg1);
    String txt = new String("glGenFencesAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4i(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4i(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4i(" +
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
  public  void glDrawRangeElements(int arg0,int arg1,int arg2,int arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glDrawRangeElements(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glDrawBuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDrawBuffers(arg0,arg1);
    String txt = new String("glDrawBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2f(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2f(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4h(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4h(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4h(" +
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
  public  void glEvalCoord2f(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord2f(arg0,arg1);
    String txt = new String("glEvalCoord2f(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetNamedProgramLocalParameterfvEXT(" +
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
  public  void glVertexAttrib2d(int arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2d(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionParameterf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glConvolutionParameterf(arg0,arg1,arg2);
    String txt = new String("glConvolutionParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4f(arg0,arg1,arg2,arg3,arg4);
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
  public  void glEvalCoord2d(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord2d(arg0,arg1);
    String txt = new String("glEvalCoord2d(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameterIivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glTextureParameterIivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameterIivEXT(" +
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
  public  void glVertexAttrib2h(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2h(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2h(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1f(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1f(arg0,arg1);
    String txt = new String("glMultiTexCoord1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPointParameterf(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPointParameterf(arg0,arg1);
    String txt = new String("glPointParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormal3fv(arg0,arg1);
    String txt = new String("glNormal3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glWindowPos3f(arg0,arg1,arg2);
    String txt = new String("glWindowPos3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2s(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2s(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4ubv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4ubv(arg0,arg1);
    String txt = new String("glColor4ubv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantPointerEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glVariantPointerEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glVariantPointerEXT(" +
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
  public  void glPolygonStipple(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPolygonStipple(arg0,arg1);
    String txt = new String("glPolygonStipple(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2fv(arg0,arg1);
    String txt = new String("glVertexAttrib2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2s(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2s(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2s(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPNTrianglesiATI(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPNTrianglesiATI(arg0,arg1);
    String txt = new String("glPNTrianglesiATI(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord2iv(arg0);
    String txt = new String("glTexCoord2iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3iv(arg0,arg1);
    String txt = new String("glMultiTexCoord3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform4fEXT(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glProgramUniform4fEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniform4fEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
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
  public  void glColor3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor3fv(arg0);
    String txt = new String("glColor3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1hv(arg0,arg1);
    String txt = new String("glVertexAttrib1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTessellationModeAMD(int arg0)
  {
        checkContext();
downstreamGL2.glTessellationModeAMD(arg0);
    String txt = new String("glTessellationModeAMD(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegeri_v(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetIntegeri_v(arg0,arg1,arg2,arg3);
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
  public  void glNormal3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3iv(arg0);
    String txt = new String("glNormal3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1dARB(int arg0,double arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1dARB(arg0,arg1);
    String txt = new String("glVertexAttrib1dARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexLevelParameteriv(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTexLevelParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,int arg10,java.nio.Buffer arg11)
  {
        checkContext();
downstreamGL2.glMultiTexSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glMultiTexSubImage3DEXT(" +
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
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixTranslatedEXT(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glMatrixTranslatedEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMatrixTranslatedEXT(" +
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
  public  void glGetMinmaxParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMinmaxParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetMinmaxParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3fv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glShadeModel(int arg0)
  {
        checkContext();
downstreamGL2.glShadeModel(arg0);
    String txt = new String("glShadeModel(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2fv(arg0,arg1);
    String txt = new String("glWindowPos2fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix4fv(arg0,arg1,arg2,arg3);
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
  public  void glMatrixIndexusvARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixIndexusvARB(arg0,arg1);
    String txt = new String("glMatrixIndexusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2dvARB(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2dvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Niv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Niv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Niv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2fv(arg0,arg1);
    String txt = new String("glTexCoord2fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform1fv(arg0,arg1,arg2);
    String txt = new String("glUniform1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteProgramsARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteProgramsARB(arg0,arg1);
    String txt = new String("glDeleteProgramsARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFinish()
  {
        checkContext();
downstreamGL2.glFinish();
    String txt = new String("glFinish(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramBufferParametersIuivNV(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersIuivNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramBufferParametersIuivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fvARB(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3fvARB(arg0,arg1);
    String txt = new String("glVertexAttrib3fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDetachShader(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDetachShader(arg0,arg1);
    String txt = new String("glDetachShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenOcclusionQueriesNV(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenOcclusionQueriesNV(arg0,arg1,arg2);
    String txt = new String("glGenOcclusionQueriesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2.glColorPointer(arg0);
    String txt = new String("glColorPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalMapsNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalMapsNV(arg0,arg1);
    String txt = new String("glEvalMapsNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3us(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glColor3us(arg0,arg1,arg2);
    String txt = new String("glColor3us(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindProgramARB(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBindProgramARB(arg0,arg1);
    String txt = new String("glBindProgramARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3bv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3bv(arg0,arg1);
    String txt = new String("glSecondaryColor3bv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapusv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetPixelMapusv(arg0,arg1,arg2);
    String txt = new String("glGetPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2ivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform2ivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform2ivARB(" +
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
  public  void glMultiTexCoord4s(int arg0,short arg1,short arg2,short arg3,short arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4s(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoord4s(" +
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
  public  void glWeightivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightivARB(arg0,arg1,arg2);
    String txt = new String("glWeightivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glScaled(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glScaled(arg0,arg1,arg2);
    String txt = new String("glScaled(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix3x2fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3x2fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix3x2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoadfEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixLoadfEXT(arg0,arg1);
    String txt = new String("glMatrixLoadfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexEnvfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexEnvfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexEnvfvEXT(" +
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
  public  void glLightModeliv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glLightModeliv(arg0,arg1,arg2);
    String txt = new String("glLightModeliv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2fvARB(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2fvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1i(int arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1i(arg0);
    String txt = new String("glTexCoord1i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glEvalCoord1d(double arg0)
  {
        checkContext();
downstreamGL2.glEvalCoord1d(arg0);
    String txt = new String("glEvalCoord1d(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramEnvParameterIivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterIivNV(arg0,arg1,arg2);
    String txt = new String("glGetProgramEnvParameterIivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1d(int arg0,double arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1d(arg0,arg1);
    String txt = new String("glVertexAttrib1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerui64i_vNV(int arg0,int arg1,long[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetIntegerui64i_vNV(arg0,arg1,arg2,arg3);
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
  public  void glUniform3iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform3iv(arg0,arg1,arg2);
    String txt = new String("glUniform3iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferAttachmentParameteriv(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetFramebufferAttachmentParameteriv(arg0,arg1,arg2,arg3);
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
  public  int getSwapInterval()
  {
        return downstreamGL2.getSwapInterval();
  }
  public  void glRasterPos4iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos4iv(arg0);
    String txt = new String("glRasterPos4iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableVertexAttribAPPLE(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDisableVertexAttribAPPLE(arg0,arg1);
    String txt = new String("glDisableVertexAttribAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI2i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI2i(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib2dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2dv(arg0,arg1);
    String txt = new String("glVertexAttrib2dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameterI4iNV(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4iNV(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramLocalParameterI4iNV(" +
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
  public  void glClearBufferuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glClearBufferuiv(arg0,arg1,arg2);
    String txt = new String("glClearBufferuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBitmap(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5,long arg6)
  {
        checkContext();
downstreamGL2.glBitmap(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glBitmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glCreateProgram()
  {
        checkContext();
    int _res = downstreamGL2.glCreateProgram();
    String txt = new String("glCreateProgram(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniformMatrix4x2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix4x2fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glProgramEnvParameterI4uivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParameterI4uivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParameterI4uivNV(" +
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
  public  void glVariantivEXT(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantivEXT(arg0,arg1,arg2);
    String txt = new String("glVariantivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedBufferSubDataEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glNamedBufferSubDataEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedBufferSubDataEXT(" +
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
  public  void glGetTextureParameterIivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTextureParameterIivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTextureParameterIivEXT(" +
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
  public  java.nio.ByteBuffer glAllocateMemoryNV(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2.glAllocateMemoryNV(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glMultiTexImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glMultiTexImage1DEXT(" +
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
  public  void glVertexAttrib3svARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3svARB(arg0,arg1);
    String txt = new String("glVertexAttrib3svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderSourceARB(int arg0,int arg1,java.lang.String[] arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glShaderSourceARB(arg0,arg1,arg2,arg3);
    String txt = new String("glShaderSourceARB(" +
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
  public  void glGetTextureParameterfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTextureParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTextureParameterfvEXT(" +
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
  public  void glColor3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3iv(arg0,arg1);
    String txt = new String("glColor3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramInfoLog(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetProgramInfoLog(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glTexCoord3i(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexCoord3i(arg0,arg1,arg2);
    String txt = new String("glTexCoord3i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsEnabled(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsEnabled(arg0);
    String txt = new String("glIsEnabled(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMultiTexCoord3d(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3d(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexCoord3d(" +
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
  public  void glCompressedMultiTexSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,int arg10,java.nio.Buffer arg11)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glCompressedMultiTexSubImage3DEXT(" +
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
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glVertex3f(arg0,arg1,arg2);
    String txt = new String("glVertex3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParametersI4uivNV(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParametersI4uivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParametersI4uivNV(" +
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
  public  void glVertex3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex3fv(arg0,arg1);
    String txt = new String("glVertex3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3d(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3d(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib2dvARB(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib2dvARB(arg0,arg1);
    String txt = new String("glVertexAttrib2dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGenfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGenfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGenfvEXT(" +
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
  public  void glColorSubTable(int arg0,int arg1,int arg2,int arg3,int arg4,long arg5)
  {
        checkContext();
downstreamGL2.glColorSubTable(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glColorSubTable(" +
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
  public  void glProgramUniform3fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3fvEXT(" +
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
  public  void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2.glReadPixels(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glIndexfv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glIndexfv(arg0);
    String txt = new String("glIndexfv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4i(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glVertexAttribI4i(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttribI4i(" +
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
  public  void glRasterPos4fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos4fv(arg0,arg1);
    String txt = new String("glRasterPos4fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramEnvParameters4fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParameters4fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParameters4fvEXT(" +
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
  public  void glGenerateTextureMipmapEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glGenerateTextureMipmapEXT(arg0,arg1);
    String txt = new String("glGenerateTextureMipmapEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4ivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ivARB(arg0,arg1);
    String txt = new String("glVertexAttrib4ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSetFenceAPPLE(int arg0)
  {
        checkContext();
downstreamGL2.glSetFenceAPPLE(arg0);
    String txt = new String("glSetFenceAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVariantBooleanvEXT(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVariantBooleanvEXT(arg0,arg1,arg2);
    String txt = new String("glGetVariantBooleanvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glFogiv(arg0,arg1,arg2);
    String txt = new String("glFogiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs2hv(int arg0,int arg1,java.nio.ShortBuffer arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribs2hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribs2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPolygonStipple(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glGetPolygonStipple(arg0,arg1);
    String txt = new String("glGetPolygonStipple(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantusvEXT(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantusvEXT(arg0,arg1);
    String txt = new String("glVariantusvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glWindowPos3d(arg0,arg1,arg2);
    String txt = new String("glWindowPos3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glPixelMapfv(arg0,arg1,arg2,arg3);
    String txt = new String("glPixelMapfv(" +
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
  public  void glRasterPos2iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2iv(arg0,arg1);
    String txt = new String("glRasterPos2iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearBufferiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearBufferiv(arg0,arg1,arg2,arg3);
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
  public  void glColorMaski(int arg0,boolean arg1,boolean arg2,boolean arg3,boolean arg4)
  {
        checkContext();
downstreamGL2.glColorMaski(arg0,arg1,arg2,arg3,arg4);
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
  public  void glMultiTexCoord1d(int arg0,double arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1d(arg0,arg1);
    String txt = new String("glMultiTexCoord1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetSeparableFilter(int arg0,int arg1,int arg2,java.nio.Buffer arg3,java.nio.Buffer arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glGetSeparableFilter(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetSeparableFilter(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<java.nio.Buffer>" +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedBufferSubDataEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedBufferSubDataEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedBufferSubDataEXT(" +
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
  public  void glTextureRangeAPPLE(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glTextureRangeAPPLE(arg0,arg1,arg2);
    String txt = new String("glTextureRangeAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2d(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glVertex2d(arg0,arg1);
    String txt = new String("glVertex2d(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1sv(arg0,arg1);
    String txt = new String("glVertexAttrib1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3fvARB(" +
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
  public  void glVertex2f(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glVertex2f(arg0,arg1);
    String txt = new String("glVertex2f(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform1iv(arg0,arg1,arg2,arg3);
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
  public  void glDrawBufferRegion(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glDrawBufferRegion(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glDrawBufferRegion(" +
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
  public  void glVertex2h(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glVertex2h(arg0,arg1);
    String txt = new String("glVertex2h(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2i(arg0,arg1);
    String txt = new String("glVertex2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexParameterfv(arg0,arg1,arg2);
    String txt = new String("glGetTexParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTextureSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2.glCopyTextureSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCopyTextureSubImage2DEXT(" +
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
  public  void glShaderBinary(int arg0,int[] arg1,int arg2,int arg3,java.nio.Buffer arg4,int arg5)
  {
        checkContext();
downstreamGL2.glShaderBinary(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glMaterialiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMaterialiv(arg0,arg1,arg2,arg3);
    String txt = new String("glMaterialiv(" +
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
  public  void glVertex2s(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glVertex2s(arg0,arg1);
    String txt = new String("glVertex2s(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform4ivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform4ivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform4ivEXT(" +
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
  public  void glMultiTexCoord4hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4hv(arg0,arg1);
    String txt = new String("glMultiTexCoord4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightubvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightubvARB(arg0,arg1,arg2);
    String txt = new String("glWeightubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexEnvfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexEnvfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexEnvfvEXT(" +
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
  public  void glGetTextureLevelParameterfvEXT(int arg0,int arg1,int arg2,int arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetTextureLevelParameterfvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetTextureLevelParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3dv(arg0,arg1);
    String txt = new String("glColor3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendFunc(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBlendFunc(arg0,arg1);
    String txt = new String("glBlendFunc(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord2sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord2sv(arg0,arg1);
    String txt = new String("glTexCoord2sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3iARB(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3iARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3iARB(" +
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
  public  void glCopyTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8)
  {
        checkContext();
downstreamGL2.glCopyTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glGetLocalConstantBooleanvEXT(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetLocalConstantBooleanvEXT(arg0,arg1,arg2);
    String txt = new String("glGetLocalConstantBooleanvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribDivisor(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribDivisor(arg0,arg1);
    String txt = new String("glVertexAttribDivisor(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPopAttrib()
  {
        checkContext();
downstreamGL2.glPopAttrib();
    String txt = new String("glPopAttrib(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPushAttrib(int arg0)
  {
        checkContext();
downstreamGL2.glPushAttrib(arg0);
    String txt = new String("glPushAttrib(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,int arg10,java.nio.Buffer arg11)
  {
        checkContext();
downstreamGL2.glTextureSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
    String txt = new String("glTextureSubImage3DEXT(" +
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
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionFilter2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2.glConvolutionFilter2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glConvolutionFilter2D(" +
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
  public  void glRectiv(java.nio.IntBuffer arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glRectiv(arg0,arg1);
    String txt = new String("glRectiv(" +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectiv(int[] arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRectiv(arg0,arg1,arg2,arg3);
    String txt = new String("glRectiv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexdv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexdv(arg0,arg1);
    String txt = new String("glIndexdv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedMakeBufferResidentNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNamedMakeBufferResidentNV(arg0,arg1);
    String txt = new String("glNamedMakeBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerui64vNV(int arg0,long[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetIntegerui64vNV(arg0,arg1,arg2);
    String txt = new String("glGetIntegerui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[J>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapParameterivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMapParameterivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glMapParameterivNV(" +
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
  public  void glVertex4h(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glVertex4h(arg0,arg1,arg2,arg3);
    String txt = new String("glVertex4h(" +
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
  public  void glVertex4i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertex4i(arg0,arg1,arg2,arg3);
    String txt = new String("glVertex4i(" +
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
  public  void glProgramUniform4uivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform4uivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform4uivEXT(" +
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
  public  void glVertex4d(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glVertex4d(arg0,arg1,arg2,arg3);
    String txt = new String("glVertex4d(" +
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
  public  void glVertexAttrib4fARB(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4fARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4fARB(" +
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
  public  void glScalef(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glScalef(arg0,arg1,arg2);
    String txt = new String("glScalef(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4svARB(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4svARB(arg0,arg1);
    String txt = new String("glVertexAttrib4svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4f(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glVertex4f(arg0,arg1,arg2,arg3);
    String txt = new String("glVertex4f(" +
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
  public  boolean glIsPBOUnpackEnabled()
  {
        checkContext();
    boolean _res = downstreamGL2.glIsPBOUnpackEnabled();
    String txt = new String("glIsPBOUnpackEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTextureNormalEXT(int arg0)
  {
        checkContext();
downstreamGL2.glTextureNormalEXT(arg0);
    String txt = new String("glTextureNormalEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFencesAPPLE(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteFencesAPPLE(arg0,arg1,arg2);
    String txt = new String("glDeleteFencesAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glTestFenceNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glTestFenceNV(arg0);
    String txt = new String("glTestFenceNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glUniform1ivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform1ivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform1ivARB(" +
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
  public  void glPixelStoref(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPixelStoref(arg0,arg1);
    String txt = new String("glPixelStoref(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVertexArray(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsVertexArray(arg0);
    String txt = new String("glIsVertexArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  int glGenLists(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glGenLists(arg0);
    String txt = new String("glGenLists(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertex4s(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glVertex4s(arg0,arg1,arg2,arg3);
    String txt = new String("glVertex4s(" +
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
  public  void glRenderbufferStorageMultisample(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glRenderbufferStorageMultisample(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetConvolutionFilter(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glGetConvolutionFilter(arg0,arg1,arg2,arg3);
    String txt = new String("glGetConvolutionFilter(" +
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
  public  void glProgramUniformMatrix2x4fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2x4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix2x4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexImage(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glGetTexImage(arg0,arg1,arg2,arg3,arg4);
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
  public  void glMultiTexCoord2sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2sv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1hv(arg0);
    String txt = new String("glTexCoord1hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultTransposefEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixMultTransposefEXT(arg0,arg1);
    String txt = new String("glMatrixMultTransposefEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos2sv(arg0);
    String txt = new String("glRasterPos2sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDrawElements(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glDrawElements(arg0,arg1,arg2,arg3);
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
  public  void glUniformMatrix2x3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix2x3fv(arg0,arg1,arg2,arg3);
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
  public  int glGetAttribLocation(int arg0,java.lang.String arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetAttribLocation(arg0,arg1);
    String txt = new String("glGetAttribLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetVertexAttribdv(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVertexAttribdv(arg0,arg1,arg2);
    String txt = new String("glGetVertexAttribdv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterfvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterfvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexParameterfvEXT(" +
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
  public  void glCompressedTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glUniform3i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3i(arg0,arg1,arg2,arg3);
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
  public  void glMultiTexCoord1fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1fv(arg0,arg1);
    String txt = new String("glMultiTexCoord1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameterIuivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glTextureParameterIuivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glTextureParameterIuivEXT(" +
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
  public  void glDeleteTextures(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteTextures(arg0,arg1);
    String txt = new String("glDeleteTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEndConditionalRender()
  {
        checkContext();
downstreamGL2.glEndConditionalRender();
    String txt = new String("glEndConditionalRender(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixScaledEXT(int arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glMatrixScaledEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMatrixScaledEXT(" +
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
  public  void glLightiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glLightiv(arg0,arg1,arg2,arg3);
    String txt = new String("glLightiv(" +
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
  public  void glBindRenderbuffer(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBindRenderbuffer(arg0,arg1);
    String txt = new String("glBindRenderbuffer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexMaterialEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glIndexMaterialEXT(arg0,arg1);
    String txt = new String("glIndexMaterialEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex2sv(arg0);
    String txt = new String("glVertex2sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveAttrib(int arg0,int arg1,int arg2,int[] arg3,int arg4,int[] arg5,int arg6,int[] arg7,int arg8,byte[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glGetActiveAttrib(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glGetIntegerv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetIntegerv(arg0,arg1);
    String txt = new String("glGetIntegerv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectdv(double[] arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRectdv(arg0,arg1,arg2,arg3);
    String txt = new String("glRectdv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectdv(java.nio.DoubleBuffer arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glRectdv(arg0,arg1);
    String txt = new String("glRectdv(" +
    "<java.nio.DoubleBuffer>" +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3uivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform3uivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform3uivEXT(" +
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
  public  void glWeightbvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightbvARB(arg0,arg1,arg2);
    String txt = new String("glWeightbvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribiv(arg0,arg1,arg2,arg3);
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
  public  void glTexGenfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexGenfv(arg0,arg1,arg2);
    String txt = new String("glTexGenfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapParameterivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMapParameterivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapParameterivNV(" +
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
  public  void glProgramLocalParameterI4uivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParameterI4uivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParameterI4uivNV(" +
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
  public  void glEndList()
  {
        checkContext();
downstreamGL2.glEndList();
    String txt = new String("glEndList(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixMultfEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixMultfEXT(arg0,arg1);
    String txt = new String("glMatrixMultfEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapfv(arg0,arg1);
    String txt = new String("glGetPixelMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedMakeBufferNonResidentNV(int arg0)
  {
        checkContext();
downstreamGL2.glNamedMakeBufferNonResidentNV(arg0);
    String txt = new String("glNamedMakeBufferNonResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformui64vNV(int arg0,int arg1,int arg2,com.sun.gluegen.runtime.PointerBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniformui64vNV(arg0,arg1,arg2,arg3);
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
  public  void glVertexPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2.glVertexPointer(arg0);
    String txt = new String("glVertexPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glUniform1i(arg0,arg1);
    String txt = new String("glUniform1i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NuivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NuivARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightfvARB(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightfvARB(arg0,arg1,arg2);
    String txt = new String("glWeightfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4ubv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4ubv(arg0,arg1);
    String txt = new String("glVertexAttribI4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4ui(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniform4ui(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetPolygonStipple(long arg0)
  {
        checkContext();
downstreamGL2.glGetPolygonStipple(arg0);
    String txt = new String("glGetPolygonStipple(" +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferSubData(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glBufferSubData(arg0,arg1,arg2,arg3);
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
  public  void glUniform4ivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform4ivARB(arg0,arg1,arg2);
    String txt = new String("glUniform4ivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenVertexArrays(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenVertexArrays(arg0,arg1);
    String txt = new String("glGenVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableClientState(int arg0)
  {
        checkContext();
downstreamGL2.glDisableClientState(arg0);
    String txt = new String("glDisableClientState(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4usv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4usv(arg0,arg1);
    String txt = new String("glVertexAttribI4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullParameterfvEXT(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glCullParameterfvEXT(arg0,arg1);
    String txt = new String("glCullParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,long arg7)
  {
        checkContext();
downstreamGL2.glTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glGetShaderSource(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetShaderSource(arg0,arg1,arg2,arg3);
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
  public  void glUniform2uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform2uiv(arg0,arg1,arg2,arg3);
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
  public  void glRectfv(float[] arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRectfv(arg0,arg1,arg2,arg3);
    String txt = new String("glRectfv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRectfv(java.nio.FloatBuffer arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glRectfv(arg0,arg1);
    String txt = new String("glRectfv(" +
    "<java.nio.FloatBuffer>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetHistogram(int arg0,boolean arg1,int arg2,int arg3,long arg4)
  {
        checkContext();
downstreamGL2.glGetHistogram(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetHistogram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glUnmapNamedBufferEXT(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glUnmapNamedBufferEXT(arg0);
    String txt = new String("glUnmapNamedBufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetBooleanIndexedv(int arg0,int arg1,java.nio.ByteBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetBooleanIndexedv(arg0,arg1,arg2);
    String txt = new String("glGetBooleanIndexedv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI2uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI2uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI2uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterIuivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterIuivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramLocalParameterIuivEXT(" +
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
  public  void glTexGendv(int arg0,int arg1,double[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexGendv(arg0,arg1,arg2,arg3);
    String txt = new String("glTexGendv(" +
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
  public  void glProgramUniformui64NV(int arg0,int arg1,long arg2)
  {
        checkContext();
downstreamGL2.glProgramUniformui64NV(arg0,arg1,arg2);
    String txt = new String("glProgramUniformui64NV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMapParameterfvNV(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMapParameterfvNV(arg0,arg1,arg2);
    String txt = new String("glGetMapParameterfvNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetObjectParameterivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetObjectParameterivARB(arg0,arg1,arg2);
    String txt = new String("glGetObjectParameterivARB(" +
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
downstreamGL2.glDeleteVertexArrays(arg0,arg1);
    String txt = new String("glDeleteVertexArrays(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexImageEXT(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glGetMultiTexImageEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetMultiTexImageEXT(" +
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
  public  void glGetMinmaxParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMinmaxParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMinmaxParameteriv(" +
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
  public  void glTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,long arg9)
  {
        checkContext();
downstreamGL2.glTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
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
  public  void glProgramEnvParametersI4ivNV(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramEnvParametersI4ivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramEnvParametersI4ivNV(" +
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
  public  boolean glIsOcclusionQueryNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsOcclusionQueryNV(arg0);
    String txt = new String("glIsOcclusionQueryNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetProgramEnvParameterfvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramEnvParameterfvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramEnvParameterfvARB(" +
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
  public  void glVertexAttrib1fvARB(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1fvARB(arg0,arg1);
    String txt = new String("glVertexAttrib1fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteQueries(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteQueries(arg0,arg1,arg2);
    String txt = new String("glDeleteQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapGrid2d(int arg0,double arg1,double arg2,int arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGL2.glMapGrid2d(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMapGrid2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex3hv(arg0,arg1);
    String txt = new String("glVertex3hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3x4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix3x4fv(arg0,arg1,arg2,arg3);
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
  public  void glCompressedTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glMapGrid2f(int arg0,float arg1,float arg2,int arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glMapGrid2f(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMapGrid2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBitmap(int arg0,int arg1,float arg2,float arg3,float arg4,float arg5,byte[] arg6,int arg7)
  {
        checkContext();
downstreamGL2.glBitmap(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glBitmap(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGenSymbolsEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
    int _res = downstreamGL2.glGenSymbolsEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGenSymbolsEXT(" +
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
  public  void glUniform3fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3fv(arg0,arg1,arg2,arg3);
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
  public  boolean glIsFenceNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsFenceNV(arg0);
    String txt = new String("glIsFenceNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertex3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex3iv(arg0);
    String txt = new String("glVertex3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformiv(arg0,arg1,arg2,arg3);
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
  public  void glVariantdvEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantdvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix2fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetFloatIndexedvEXT(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetFloatIndexedvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetFloatIndexedvEXT(" +
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
  public  void glGetProgramLocalParameterdvARB(int arg0,int arg1,java.nio.DoubleBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterdvARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramLocalParameterdvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3fEXT(int arg0,int arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform3fEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform3fEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUseProgramObjectARB(int arg0)
  {
        checkContext();
downstreamGL2.glUseProgramObjectARB(arg0);
    String txt = new String("glUseProgramObjectARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameter4fEXT(int arg0,int arg1,int arg2,float arg3,float arg4,float arg5,float arg6)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4fEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glNamedProgramLocalParameter4fEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
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
  public  void glNamedProgramLocalParameter4dvEXT(int arg0,int arg1,int arg2,java.nio.DoubleBuffer arg3)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4dvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedProgramLocalParameter4dvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramStringEXT(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramStringEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramStringEXT(" +
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
  public  void glTexCoordFormatNV(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glTexCoordFormatNV(arg0,arg1,arg2);
    String txt = new String("glTexCoordFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dvARB(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4dvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrontFace(int arg0)
  {
        checkContext();
downstreamGL2.glFrontFace(arg0);
    String txt = new String("glFrontFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4ubv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ubv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4ubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetAttachedShaders(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetAttachedShaders(arg0,arg1,arg2,arg3);
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
downstreamGL2.glGetAttachedShaders(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glMultiTexCoord4sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4sv(arg0,arg1);
    String txt = new String("glMultiTexCoord4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantbvEXT(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVariantbvEXT(arg0,arg1);
    String txt = new String("glVariantbvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos2dv(arg0);
    String txt = new String("glRasterPos2dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord1dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord1dv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord1dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glCompressedTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glTexCoord4fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord4fv(arg0);
    String txt = new String("glTexCoord4fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedRenderbufferParameterivEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetNamedRenderbufferParameterivEXT(arg0,arg1,arg2);
    String txt = new String("glGetNamedRenderbufferParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormal3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glNormal3s(arg0,arg1,arg2);
    String txt = new String("glNormal3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBufferBase(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glBindBufferBase(arg0,arg1,arg2);
    String txt = new String("glBindBufferBase(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorTableParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glColorTableParameterfv(arg0,arg1,arg2);
    String txt = new String("glColorTableParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedFramebufferTextureLayerEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTextureLayerEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedFramebufferTextureLayerEXT(" +
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
  public  void glEndTransformFeedback()
  {
        checkContext();
downstreamGL2.glEndTransformFeedback();
    String txt = new String("glEndTransformFeedback(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureParameterIuivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetTextureParameterIuivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetTextureParameterIuivEXT(" +
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
  public  void glNormal3h(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glNormal3h(arg0,arg1,arg2);
    String txt = new String("glNormal3h(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVariantIntegervEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVariantIntegervEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVariantIntegervEXT(" +
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
  public  void glLockArraysEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLockArraysEXT(arg0,arg1);
    String txt = new String("glLockArraysEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glColor3f(arg0,arg1,arg2);
    String txt = new String("glColor3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2sv(arg0,arg1);
    String txt = new String("glMultiTexCoord2sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glMatrixIndexPointer(arg0,arg1,arg2,arg3);
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
  public  void glMultTransposeMatrixd(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glMultTransposeMatrixd(arg0);
    String txt = new String("glMultTransposeMatrixd(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1iv(arg0);
    String txt = new String("glTexCoord1iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3ub(byte arg0,byte arg1,byte arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3ub(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3ub(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableVertexAttribArray(int arg0)
  {
        checkContext();
downstreamGL2.glEnableVertexAttribArray(arg0);
    String txt = new String("glEnableVertexAttribArray(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribivARB(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribivARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetVertexAttribivARB(" +
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
  public  void glProgramEnvParameter4fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramEnvParameter4fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramEnvParameter4fvARB(" +
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
  public  void glProgramLocalParametersI4uivNV(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramLocalParametersI4uivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramLocalParametersI4uivNV(" +
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
  public  void glVertexAttribI3uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI3uiv(arg0,arg1);
    String txt = new String("glVertexAttribI3uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapParameterfvNV(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glMapParameterfvNV(arg0,arg1,arg2);
    String txt = new String("glMapParameterfvNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorTable(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glColorTable(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glColorTable(" +
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
  public  void glVertexAttrib4svARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4svARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexEnvivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexEnvivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexEnvivEXT(" +
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
  public  void glClearDepthf(float arg0)
  {
        checkContext();
downstreamGL2.glClearDepthf(arg0);
    String txt = new String("glClearDepthf(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2dv(arg0,arg1);
    String txt = new String("glMultiTexCoord2dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4hv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetCompressedTexImage(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glGetCompressedTexImage(arg0,arg1,arg2);
    String txt = new String("glGetCompressedTexImage(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordfv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glFogCoordfv(arg0);
    String txt = new String("glFogCoordfv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMapVertexAttrib1fAPPLE(int arg0,int arg1,float arg2,float arg3,int arg4,int arg5,float[] arg6,int arg7)
  {
        checkContext();
downstreamGL2.glMapVertexAttrib1fAPPLE(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glMapVertexAttrib1fAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionFilter1D(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glConvolutionFilter1D(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glConvolutionFilter1D(" +
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
  public  void glTexCoord2sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord2sv(arg0);
    String txt = new String("glTexCoord2sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4uivARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4uivARB(arg0,arg1);
    String txt = new String("glVertexAttrib4uivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform3uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform3uiv(arg0,arg1,arg2);
    String txt = new String("glUniform3uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedMultiTexSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCompressedMultiTexSubImage2DEXT(" +
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
  public  void glProgramUniform2fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2fvEXT(" +
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
  public  void glBindAttribLocation(int arg0,int arg1,java.lang.String arg2)
  {
        checkContext();
downstreamGL2.glBindAttribLocation(arg0,arg1,arg2);
    String txt = new String("glBindAttribLocation(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexMask(int arg0)
  {
        checkContext();
downstreamGL2.glIndexMask(arg0);
    String txt = new String("glIndexMask(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullFace(int arg0)
  {
        checkContext();
downstreamGL2.glCullFace(arg0);
    String txt = new String("glCullFace(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantusvEXT(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantusvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantusvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenProgramsARB(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenProgramsARB(arg0,arg1);
    String txt = new String("glGenProgramsARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,java.nio.Buffer arg10)
  {
        checkContext();
downstreamGL2.glMultiTexImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glMultiTexImage3DEXT(" +
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
  public  void glGetMultiTexGenivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexGenivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexGenivEXT(" +
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
  public  void glGetIntegerui64i_vNV(int arg0,int arg1,com.sun.gluegen.runtime.PointerBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetIntegerui64i_vNV(arg0,arg1,arg2);
    String txt = new String("glGetIntegerui64i_vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoorddv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFogCoorddv(arg0,arg1);
    String txt = new String("glFogCoorddv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedProgramLocalParameterIivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramLocalParameterIivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramLocalParameterIivEXT(" +
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
  public  void glNamedFramebufferTextureFaceEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTextureFaceEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedFramebufferTextureFaceEXT(" +
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
  public  void glGetColorTableParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetColorTableParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetColorTableParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterIivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexParameterIivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexParameterIivEXT(" +
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
  public  void glDeleteBuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteBuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteBuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInvariantIntegervEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetInvariantIntegervEXT(arg0,arg1,arg2);
    String txt = new String("glGetInvariantIntegervEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPrimitiveRestart()
  {
        checkContext();
downstreamGL2.glPrimitiveRestart();
    String txt = new String("glPrimitiveRestart(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPopName()
  {
        checkContext();
downstreamGL2.glPopName();
    String txt = new String("glPopName(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glShaderSource(int arg0,int arg1,java.lang.String[] arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glShaderSource(arg0,arg1,arg2,arg3);
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
  public  void glProgramUniform2ivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform2ivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform2ivEXT(" +
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
  public  void glResetHistogram(int arg0)
  {
        checkContext();
downstreamGL2.glResetHistogram(arg0);
    String txt = new String("glResetHistogram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord3iv(arg0,arg1);
    String txt = new String("glTexCoord3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexGeniv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetTexGeniv(arg0,arg1,arg2);
    String txt = new String("glGetTexGeniv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedTextureImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glCompressedTextureImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedTextureImage1DEXT(" +
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
  public  void glClientActiveTexture(int arg0)
  {
        checkContext();
downstreamGL2.glClientActiveTexture(arg0);
    String txt = new String("glClientActiveTexture(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glGetUniformOffsetEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glGetUniformOffsetEXT(arg0,arg1);
    String txt = new String("glGetUniformOffsetEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetProgramStringARB(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramStringARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramStringARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInvariantFloatvEXT(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetInvariantFloatvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetInvariantFloatvEXT(" +
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
  public  void glGetMapAttribParameterivNV(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMapAttribParameterivNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMapAttribParameterivNV(" +
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
  public  void glGetMultiTexLevelParameterivEXT(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexLevelParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexLevelParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribs2hv(int arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribs2hv(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribs2hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glDepthRange(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glDepthRange(arg0,arg1);
    String txt = new String("glDepthRange(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEnableVertexAttribArrayARB(int arg0)
  {
        checkContext();
downstreamGL2.glEnableVertexAttribArrayARB(arg0);
    String txt = new String("glEnableVertexAttribArrayARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetIntegerui64vNV(int arg0,com.sun.gluegen.runtime.PointerBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetIntegerui64vNV(arg0,arg1);
    String txt = new String("glGetIntegerui64vNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<com.sun.gluegen.runtime.PointerBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glGenRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord4dv(arg0,arg1);
    String txt = new String("glTexCoord4dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWeightuivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glWeightuivARB(arg0,arg1,arg2);
    String txt = new String("glWeightuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramLocalParameter4fvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4fvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramLocalParameter4fvARB(" +
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
  public  void glVariantuivEXT(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantuivEXT(arg0,arg1,arg2);
    String txt = new String("glVariantuivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3svARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3svARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetColorTableParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetColorTableParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetColorTableParameterfv(" +
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
  public  void glGetQueryiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetQueryiv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib1sARB(int arg0,short arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib1sARB(arg0,arg1);
    String txt = new String("glVertexAttrib1sARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLinkProgramARB(int arg0)
  {
        checkContext();
downstreamGL2.glLinkProgramARB(arg0);
    String txt = new String("glLinkProgramARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3iv(arg0,arg1);
    String txt = new String("glSecondaryColor3iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexEnvivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexEnvivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexEnvivEXT(" +
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
  public  void glCompressedTextureImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glCompressedTextureImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCompressedTextureImage3DEXT(" +
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
  public  void glColor3ui(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glColor3ui(arg0,arg1,arg2);
    String txt = new String("glColor3ui(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4d(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glColor4d(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4d(" +
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
  public  void glColor4b(byte arg0,byte arg1,byte arg2,byte arg3)
  {
        checkContext();
downstreamGL2.glColor4b(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4b(" +
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
  public  void glGetTexEnviv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexEnviv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glVertexAttribPointer(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetTexGenfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexGenfv(arg0,arg1,arg2,arg3);
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
  public  void glCompressedTextureImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glCompressedTextureImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
    String txt = new String("glCompressedTextureImage2DEXT(" +
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
  public  void glConvolutionParameterfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glConvolutionParameterfv(arg0,arg1,arg2);
    String txt = new String("glConvolutionParameterfv(" +
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
downstreamGL2.glGetTexEnvfv(arg0,arg1,arg2);
    String txt = new String("glGetTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4s(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glColor4s(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4s(" +
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
  public  void glDrawBuffersATI(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDrawBuffersATI(arg0,arg1,arg2);
    String txt = new String("glDrawBuffersATI(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glPixelMapuiv(arg0,arg1,arg2);
    String txt = new String("glPixelMapuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEdgeFlagPointer(int arg0,java.nio.Buffer arg1)
  {
        checkContext();
downstreamGL2.glEdgeFlagPointer(arg0,arg1);
    String txt = new String("glEdgeFlagPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4h(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glColor4h(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4h(" +
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
  public  void glColor4i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glColor4i(arg0,arg1,arg2,arg3);
    String txt = new String("glColor4i(" +
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
  public  void glColor4f(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glColor4f(arg0,arg1,arg2,arg3);
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
  public  void glEvalCoord1fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glEvalCoord1fv(arg0,arg1);
    String txt = new String("glEvalCoord1fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord3dv(arg0);
    String txt = new String("glTexCoord3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexFuncEXT(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glIndexFuncEXT(arg0,arg1);
    String txt = new String("glIndexFuncEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoadTransposedEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixLoadTransposedEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixLoadTransposedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenTextures(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenTextures(arg0,arg1,arg2);
    String txt = new String("glGenTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nubv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nubv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nubv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadTransposeMatrixd(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glLoadTransposeMatrixd(arg0);
    String txt = new String("glLoadTransposeMatrixd(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlitFramebuffer(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9)
  {
        checkContext();
downstreamGL2.glBlitFramebuffer(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
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
  public  void glTexEnvfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glTexEnvfv(arg0,arg1,arg2);
    String txt = new String("glTexEnvfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBindBufferRange(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glBindBufferRange(arg0,arg1,arg2,arg3,arg4);
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
  public  void glClearColor(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glClearColor(arg0,arg1,arg2,arg3);
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
  public  void glNormalFormatNV(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glNormalFormatNV(arg0,arg1);
    String txt = new String("glNormalFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4iv(arg0,arg1);
    String txt = new String("glVertexAttribI4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGendvEXT(int arg0,int arg1,int arg2,double[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexGendvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexGendvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3dv(arg0);
    String txt = new String("glSecondaryColor3dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4sv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glRasterPos3d(arg0,arg1,arg2);
    String txt = new String("glRasterPos3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4dv(arg0);
    String txt = new String("glColor4dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisable(int arg0)
  {
        checkContext();
downstreamGL2.glDisable(arg0);
    String txt = new String("glDisable(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordd(double arg0)
  {
        checkContext();
downstreamGL2.glFogCoordd(arg0);
    String txt = new String("glFogCoordd(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameters4fvEXT(int arg0,int arg1,int arg2,int arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameters4fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glNamedProgramLocalParameters4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2iv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFloatv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetFloatv(arg0,arg1);
    String txt = new String("glGetFloatv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glResetMinmax(int arg0)
  {
        checkContext();
downstreamGL2.glResetMinmax(arg0);
    String txt = new String("glResetMinmax(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexSubImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9,long arg10)
  {
        checkContext();
downstreamGL2.glTexSubImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
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
  public  void glNamedFramebufferTexture1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedFramebufferTexture1DEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedFramebufferTexture1DEXT(" +
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
  public  void glUniform3fARB(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glUniform3fARB(arg0,arg1,arg2,arg3);
    String txt = new String("glUniform3fARB(" +
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
  public  void glProgramUniformui64vNV(int arg0,int arg1,int arg2,long[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformui64vNV(arg0,arg1,arg2,arg3,arg4);
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
  public  void glTextureSubImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glTextureSubImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glTextureSubImage2DEXT(" +
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
  public  void glBeginTransformFeedback(int arg0)
  {
        checkContext();
downstreamGL2.glBeginTransformFeedback(arg0);
    String txt = new String("glBeginTransformFeedback(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glLoadMatrixf(arg0);
    String txt = new String("glLoadMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glBufferRegionEnabled()
  {
        checkContext();
    int _res = downstreamGL2.glBufferRegionEnabled();
    String txt = new String("glBufferRegionEnabled(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertex3hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex3hv(arg0);
    String txt = new String("glVertex3hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3fv(arg0,arg1);
    String txt = new String("glVertexAttrib3fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMaterialfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMaterialfv(arg0,arg1,arg2);
    String txt = new String("glGetMaterialfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetLightiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetLightiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLightiv(" +
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
  public  void glColorPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glColorPointer(arg0,arg1,arg2,arg3);
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
  public  void glTexSubImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,long arg6)
  {
        checkContext();
downstreamGL2.glTexSubImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glVertexAttribI4bv(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4bv(arg0,arg1);
    String txt = new String("glVertexAttribI4bv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixTranslatefEXT(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glMatrixTranslatefEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMatrixTranslatefEXT(" +
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
  public  void glClearBufferfi(int arg0,int arg1,float arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearBufferfi(arg0,arg1,arg2,arg3);
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
  public  void glGetPixelMapfv(int arg0,long arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapfv(arg0,arg1);
    String txt = new String("glGetPixelMapfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3d(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameterI4uivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParameterI4uivEXT(" +
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
  public  void glMap2f(int arg0,float arg1,float arg2,int arg3,int arg4,float arg5,float arg6,int arg7,int arg8,java.nio.FloatBuffer arg9)
  {
        checkContext();
downstreamGL2.glMap2f(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glMap2f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4fv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform4fv(arg0,arg1,arg2);
    String txt = new String("glUniform4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFencesNV(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteFencesNV(arg0,arg1);
    String txt = new String("glDeleteFencesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nbv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nbv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nbv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glBindLightParameterEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glBindLightParameterEXT(arg0,arg1);
    String txt = new String("glBindLightParameterEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glFogCoordPointer(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glFogCoordPointer(arg0,arg1,arg2);
    String txt = new String("glFogCoordPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelTransformParameterivEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glPixelTransformParameterivEXT(arg0,arg1,arg2);
    String txt = new String("glPixelTransformParameterivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMinmax(int arg0,boolean arg1,int arg2,int arg3,long arg4)
  {
        checkContext();
downstreamGL2.glGetMinmax(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMinmax(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<long>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix2x3fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix2x3fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix2x3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord4fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4fv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord4fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix3fv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttribI4uiv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttribI4uiv(arg0,arg1);
    String txt = new String("glVertexAttribI4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilMaskSeparate(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glStencilMaskSeparate(arg0,arg1);
    String txt = new String("glStencilMaskSeparate(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoordPointer(int arg0,int arg1,int arg2,java.nio.Buffer arg3)
  {
        checkContext();
downstreamGL2.glTexCoordPointer(arg0,arg1,arg2,arg3);
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
  public  void glMap1f(int arg0,float arg1,float arg2,int arg3,int arg4,java.nio.FloatBuffer arg5)
  {
        checkContext();
downstreamGL2.glMap1f(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMap1f(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glVertex4fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex4fv(arg0);
    String txt = new String("glVertex4fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  javax.media.opengl.GLContext getContext()
  {
        return downstreamGL2.getContext();
  }
  public  void glWriteMaskEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glWriteMaskEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glWriteMaskEXT(" +
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
  public  void glGetFenceivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetFenceivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetFenceivNV(" +
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
  public  void glGetActiveUniform(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2.glGetActiveUniform(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glNamedFramebufferRenderbufferEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glNamedFramebufferRenderbufferEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedFramebufferRenderbufferEXT(" +
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
  public  void glPointParameterfv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glPointParameterfv(arg0,arg1);
    String txt = new String("glPointParameterfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(javax.media.opengl.GLArrayData arg0)
  {
        checkContext();
downstreamGL2.glNormalPointer(arg0);
    String txt = new String("glNormalPointer(" +
    "<javax.media.opengl.GLArrayData>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTextureLayer(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glFramebufferTextureLayer(arg0,arg1,arg2,arg3,arg4);
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
  public  void glGetNamedProgramivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedProgramivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedProgramivEXT(" +
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
  public  void glTexSubImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,long arg8)
  {
        checkContext();
downstreamGL2.glTexSubImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glNamedProgramLocalParameter4dEXT(int arg0,int arg1,int arg2,double arg3,double arg4,double arg5,double arg6)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4dEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glNamedProgramLocalParameter4dEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
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
  public  void glGetObjectParameterfvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetObjectParameterfvARB(arg0,arg1,arg2);
    String txt = new String("glGetObjectParameterfvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMap1d(int arg0,double arg1,double arg2,int arg3,int arg4,double[] arg5,int arg6)
  {
        checkContext();
downstreamGL2.glMap1d(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glMap1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg6).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexImage2DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,java.nio.Buffer arg9)
  {
        checkContext();
downstreamGL2.glMultiTexImage2DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glMultiTexImage2DEXT(" +
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
  public  boolean glIsFenceAPPLE(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsFenceAPPLE(arg0);
    String txt = new String("glIsFenceAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetLocalConstantIntegervEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetLocalConstantIntegervEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetLocalConstantIntegervEXT(" +
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
  public  void glBindBufferOffset(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glBindBufferOffset(arg0,arg1,arg2,arg3);
    String txt = new String("glBindBufferOffset(" +
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
  public  void glVertexAttribI3ui(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribI3ui(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribI3ui(" +
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
  public  void glCopyColorSubTable(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glCopyColorSubTable(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glCopyColorSubTable(" +
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
  public  void glNamedProgramLocalParameter4fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameter4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParameter4fvEXT(" +
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
  public  void glWindowPos3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glWindowPos3iv(arg0);
    String txt = new String("glWindowPos3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferTexture3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glFramebufferTexture3D(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glProgramUniform1fvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glProgramUniform1fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniform1fvEXT(" +
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
  public  void glGetMultiTexGendvEXT(int arg0,int arg1,int arg2,java.nio.DoubleBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetMultiTexGendvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMultiTexGendvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4usv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4usv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4usv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3fARB(int arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glVertexAttrib3fARB(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttrib3fARB(" +
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
  public  void glVertexAttrib3dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI2iv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI2iv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4i(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniform4i(arg0,arg1,arg2,arg3,arg4);
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
  public  void glProgramUniformMatrix4x2fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4x2fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix4x2fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4f(int arg0,float arg1,float arg2,float arg3,float arg4)
  {
        checkContext();
downstreamGL2.glUniform4f(arg0,arg1,arg2,arg3,arg4);
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
  public  void glNormal3b(byte arg0,byte arg1,byte arg2)
  {
        checkContext();
downstreamGL2.glNormal3b(arg0,arg1,arg2);
    String txt = new String("glNormal3b(" +
    "<byte>" +
    ", " +
    "<byte>" +
    ", " +
    "<byte>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMap2d(int arg0,double arg1,double arg2,int arg3,int arg4,double arg5,double arg6,int arg7,int arg8,double[] arg9,int arg10)
  {
        checkContext();
downstreamGL2.glMap2d(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
    String txt = new String("glMap2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg10).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glIndexd(double arg0)
  {
        checkContext();
downstreamGL2.glIndexd(arg0);
    String txt = new String("glIndexd(" +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyMultiTexSubImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6)
  {
        checkContext();
downstreamGL2.glCopyMultiTexSubImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
    String txt = new String("glCopyMultiTexSubImage1DEXT(" +
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
  public  void glMultiTexCoord4iv(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord4iv(arg0,arg1);
    String txt = new String("glMultiTexCoord4iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullParameterdvEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glCullParameterdvEXT(arg0,arg1);
    String txt = new String("glCullParameterdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixd(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLoadMatrixd(arg0,arg1);
    String txt = new String("glLoadMatrixd(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1uiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform1uiv(arg0,arg1,arg2,arg3);
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
  public  void glPrioritizeTextures(int arg0,int[] arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glPrioritizeTextures(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glPrioritizeTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCompressedMultiTexImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,java.nio.Buffer arg7)
  {
        checkContext();
downstreamGL2.glCompressedMultiTexImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCompressedMultiTexImage1DEXT(" +
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
  public  void glVertexAttrib3dvARB(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3dvARB(arg0,arg1);
    String txt = new String("glVertexAttrib3dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTextureImageEXT(int arg0,int arg1,int arg2,int arg3,int arg4,java.nio.Buffer arg5)
  {
        checkContext();
downstreamGL2.glGetTextureImageEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glGetTextureImageEXT(" +
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
  public  void glDeleteRenderbuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glDeleteRenderbuffers(arg0,arg1,arg2);
    String txt = new String("glDeleteRenderbuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBeginQuery(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glBeginQuery(arg0,arg1);
    String txt = new String("glBeginQuery(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetFramebufferParameterivEXT(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetFramebufferParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetFramebufferParameterivEXT(" +
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
  public  void glProgramStringARB(int arg0,int arg1,int arg2,java.lang.String arg3)
  {
        checkContext();
downstreamGL2.glProgramStringARB(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramStringARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.lang.String>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2sv(arg0,arg1);
    String txt = new String("glVertex2sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexParameterIuivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexParameterIuivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexParameterIuivEXT(" +
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
  public  void glVertex4dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex4dv(arg0,arg1);
    String txt = new String("glVertex4dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos3fv(arg0,arg1);
    String txt = new String("glWindowPos3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4dv(arg0,arg1);
    String txt = new String("glVertexAttrib4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glEdgeFlagFormatNV(int arg0)
  {
        checkContext();
downstreamGL2.glEdgeFlagFormatNV(arg0);
    String txt = new String("glEdgeFlagFormatNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord1hv(arg0,arg1);
    String txt = new String("glTexCoord1hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTransformFeedbackVarying(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2.glGetTransformFeedbackVarying(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glColor3bv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3bv(arg0,arg1);
    String txt = new String("glColor3bv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2sv(arg0,arg1);
    String txt = new String("glRasterPos2sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPushClientAttrib(int arg0)
  {
        checkContext();
downstreamGL2.glPushClientAttrib(arg0);
    String txt = new String("glPushClientAttrib(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord1fv(arg0,arg1);
    String txt = new String("glTexCoord1fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexWeightfvEXT(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertexWeightfvEXT(arg0,arg1);
    String txt = new String("glVertexWeightfvEXT(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3us(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3us(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3us(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor3ubv(byte[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor3ubv(arg0,arg1);
    String txt = new String("glColor3ubv(" +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glClearColorIui(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glClearColorIui(arg0,arg1,arg2,arg3);
    String txt = new String("glClearColorIui(" +
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
  public  void glGetHistogramParameteriv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetHistogramParameteriv(arg0,arg1,arg2);
    String txt = new String("glGetHistogramParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCullParameterdvEXT(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glCullParameterdvEXT(arg0,arg1,arg2);
    String txt = new String("glCullParameterdvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glFramebufferDrawBuffersEXT(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glFramebufferDrawBuffersEXT(arg0,arg1,arg2);
    String txt = new String("glFramebufferDrawBuffersEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glRasterPos3f(arg0,arg1,arg2);
    String txt = new String("glRasterPos3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadIdentity()
  {
        checkContext();
downstreamGL2.glLoadIdentity();
    String txt = new String("glLoadIdentity(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFogCoordf(float arg0)
  {
        checkContext();
downstreamGL2.glFogCoordf(arg0);
    String txt = new String("glFogCoordf(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glColor4fv(arg0);
    String txt = new String("glColor4fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform2iv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform2iv(arg0,arg1,arg2);
    String txt = new String("glUniform2iv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsVertexAttribEnabledAPPLE(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsVertexAttribEnabledAPPLE(arg0,arg1);
    String txt = new String("glIsVertexAttribEnabledAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glEdgeFlagv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glEdgeFlagv(arg0);
    String txt = new String("glEdgeFlagv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBlendColor(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glBlendColor(arg0,arg1,arg2,arg3);
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
  public  void glMap2d(int arg0,double arg1,double arg2,int arg3,int arg4,double arg5,double arg6,int arg7,int arg8,java.nio.DoubleBuffer arg9)
  {
        checkContext();
downstreamGL2.glMap2d(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glMap2d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg7).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg8).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetQueryObjectiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjectiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoadTransposefEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixLoadTransposefEXT(arg0,arg1,arg2);
    String txt = new String("glMatrixLoadTransposefEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3dv(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib3dv(arg0,arg1);
    String txt = new String("glVertexAttrib3dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glCreateShader(int arg0)
  {
        checkContext();
    int _res = downstreamGL2.glCreateShader(arg0);
    String txt = new String("glCreateShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glClearAccum(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glClearAccum(arg0,arg1,arg2,arg3);
    String txt = new String("glClearAccum(" +
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
  public  void glProgramLocalParameter4dARB(int arg0,int arg1,double arg2,double arg3,double arg4,double arg5)
  {
        checkContext();
downstreamGL2.glProgramLocalParameter4dARB(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramLocalParameter4dARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
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
  public  void glGetMultiTexGendvEXT(int arg0,int arg1,int arg2,double[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexGendvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexGendvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPassThrough(float arg0)
  {
        checkContext();
downstreamGL2.glPassThrough(arg0);
    String txt = new String("glPassThrough(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDeleteFramebuffers(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glDeleteFramebuffers(arg0,arg1);
    String txt = new String("glDeleteFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform3ivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform3ivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform3ivEXT(" +
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
  public  void glMap1d(int arg0,double arg1,double arg2,int arg3,int arg4,java.nio.DoubleBuffer arg5)
  {
        checkContext();
downstreamGL2.glMap1d(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glMap1d(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg4).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFinishFenceAPPLE(int arg0)
  {
        checkContext();
downstreamGL2.glFinishFenceAPPLE(arg0);
    String txt = new String("glFinishFenceAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelMapusv(int arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glPixelMapusv(arg0,arg1,arg2,arg3);
    String txt = new String("glPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetActiveAttrib(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3,java.nio.IntBuffer arg4,java.nio.IntBuffer arg5,java.nio.ByteBuffer arg6)
  {
        checkContext();
downstreamGL2.glGetActiveAttrib(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glListBase(int arg0)
  {
        checkContext();
downstreamGL2.glListBase(arg0);
    String txt = new String("glListBase(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexuivARB(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixIndexuivARB(arg0,arg1,arg2);
    String txt = new String("glMatrixIndexuivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramLocalParameterfvARB(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterfvARB(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramLocalParameterfvARB(" +
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
  public  void glUniform2fv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform2fv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4sv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4sv(arg0,arg1);
    String txt = new String("glVertexAttrib4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4uiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform4uiv(arg0,arg1,arg2);
    String txt = new String("glUniform4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramBufferParametersfvNV(int arg0,int arg1,int arg2,int arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramBufferParametersfvNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramBufferParametersfvNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetHistogramParameterfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetHistogramParameterfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetHistogramParameterfv(" +
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
  public  void glGetMaterialiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMaterialiv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMaterialiv(" +
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
  public  void glVertex2fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2fv(arg0,arg1);
    String txt = new String("glVertex2fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3uiv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3uiv(arg0);
    String txt = new String("glSecondaryColor3uiv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3f(float arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glSecondaryColor3f(arg0,arg1,arg2);
    String txt = new String("glSecondaryColor3f(" +
    "<float>" +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2hv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex2hv(arg0,arg1);
    String txt = new String("glVertex2hv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glVertex4iv(arg0,arg1);
    String txt = new String("glVertex4iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetOcclusionQueryivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetOcclusionQueryivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetOcclusionQueryivNV(" +
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
  public  void glFinishRenderAPPLE()
  {
        checkContext();
downstreamGL2.glFinishRenderAPPLE();
    String txt = new String("glFinishRenderAPPLE(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPrimitiveRestartIndex(int arg0)
  {
        checkContext();
downstreamGL2.glPrimitiveRestartIndex(arg0);
    String txt = new String("glPrimitiveRestartIndex(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glActiveStencilFaceEXT(int arg0)
  {
        checkContext();
downstreamGL2.glActiveStencilFaceEXT(arg0);
    String txt = new String("glActiveStencilFaceEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColor4iv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4iv(arg0,arg1);
    String txt = new String("glColor4iv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4dv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4dv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glInsertComponentEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glInsertComponentEXT(arg0,arg1,arg2);
    String txt = new String("glInsertComponentEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3fv(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos3fv(arg0,arg1);
    String txt = new String("glRasterPos3fv(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex4dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex4dv(arg0);
    String txt = new String("glVertex4dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetMultiTexParameterIivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMultiTexParameterIivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMultiTexParameterIivEXT(" +
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
  public  void glGenFencesNV(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenFencesNV(arg0,arg1,arg2);
    String txt = new String("glGenFencesNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGenQueries(int arg0,java.nio.IntBuffer arg1)
  {
        checkContext();
downstreamGL2.glGenQueries(arg0,arg1);
    String txt = new String("glGenQueries(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform2uivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2uivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2uivEXT(" +
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
  public  void glGetShaderSource(int arg0,int arg1,int[] arg2,int arg3,byte[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glGetShaderSource(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glGetMapfv(int arg0,int arg1,float[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetMapfv(arg0,arg1,arg2,arg3);
    String txt = new String("glGetMapfv(" +
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
  public  void glPopClientAttrib()
  {
        checkContext();
downstreamGL2.glPopClientAttrib();
    String txt = new String("glPopClientAttrib(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib3dvARB(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib3dvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib3dvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glPrioritizeTextures(int arg0,java.nio.IntBuffer arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glPrioritizeTextures(arg0,arg1,arg2);
    String txt = new String("glPrioritizeTextures(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadMatrixd(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glLoadMatrixd(arg0);
    String txt = new String("glLoadMatrixd(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferAddressRangeNV(int arg0,int arg1,long arg2,int arg3)
  {
        checkContext();
downstreamGL2.glBufferAddressRangeNV(arg0,arg1,arg2,arg3);
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
  public  void glGenFramebuffers(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGenFramebuffers(arg0,arg1,arg2);
    String txt = new String("glGenFramebuffers(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2i(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2i(arg0,arg1);
    String txt = new String("glRasterPos2i(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2f(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2f(arg0,arg1);
    String txt = new String("glRasterPos2f(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos2d(double arg0,double arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2d(arg0,arg1);
    String txt = new String("glRasterPos2d(" +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1svARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1svARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1svARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBeginOcclusionQueryNV(int arg0)
  {
        checkContext();
downstreamGL2.glBeginOcclusionQueryNV(arg0);
    String txt = new String("glBeginOcclusionQueryNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NubARB(int arg0,byte arg1,byte arg2,byte arg3,byte arg4)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NubARB(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glVertexAttrib4NubARB(" +
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
  public  void glCopyTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2.glCopyTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
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
  public  void glVertexAttribs1hv(int arg0,int arg1,short[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glVertexAttribs1hv(arg0,arg1,arg2,arg3);
    String txt = new String("glVertexAttribs1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetNamedFramebufferAttachmentParameterivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetNamedFramebufferAttachmentParameterivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetNamedFramebufferAttachmentParameterivEXT(" +
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
  public  void glTexCoord2hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord2hv(arg0);
    String txt = new String("glTexCoord2hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glLightf(arg0,arg1,arg2);
    String txt = new String("glLightf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexParameterIiv(arg0,arg1,arg2,arg3);
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
  public  void glGetBooleanv(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glGetBooleanv(arg0,arg1,arg2);
    String txt = new String("glGetBooleanv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoordPointerEXT(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glMultiTexCoordPointerEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexCoordPointerEXT(" +
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
  public  void glPointParameteriv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glPointParameteriv(arg0,arg1,arg2);
    String txt = new String("glPointParameteriv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetUniformuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetUniformuiv(arg0,arg1,arg2,arg3);
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
  public  void glRasterPos2s(short arg0,short arg1)
  {
        checkContext();
downstreamGL2.glRasterPos2s(arg0,arg1);
    String txt = new String("glRasterPos2s(" +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiDrawElements(int arg0,java.nio.IntBuffer arg1,int arg2,java.nio.Buffer[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiDrawElements(arg0,arg1,arg2,arg3,arg4);
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
  public  void glFramebufferDrawBufferEXT(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFramebufferDrawBufferEXT(arg0,arg1);
    String txt = new String("glFramebufferDrawBufferEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix3fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glBindMaterialParameterEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glBindMaterialParameterEXT(arg0,arg1);
    String txt = new String("glBindMaterialParameterEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glDeleteProgram(int arg0)
  {
        checkContext();
downstreamGL2.glDeleteProgram(arg0);
    String txt = new String("glDeleteProgram(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glConvolutionParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glConvolutionParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glConvolutionParameteriv(" +
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
  public  void glReleaseShaderCompiler()
  {
        checkContext();
downstreamGL2.glReleaseShaderCompiler();
    String txt = new String("glReleaseShaderCompiler(" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTextureParameterivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glTextureParameterivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glTextureParameterivEXT(" +
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
  public  void glColor4dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4dv(arg0,arg1);
    String txt = new String("glColor4dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4NubvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4NubvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4NubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixPushEXT(int arg0)
  {
        checkContext();
downstreamGL2.glMatrixPushEXT(arg0);
    String txt = new String("glMatrixPushEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexParameteriv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttribIPointer(int arg0,int arg1,int arg2,int arg3,java.nio.Buffer arg4)
  {
        checkContext();
downstreamGL2.glVertexAttribIPointer(arg0,arg1,arg2,arg3,arg4);
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
  public  void glColor4uiv(int[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glColor4uiv(arg0,arg1);
    String txt = new String("glColor4uiv(" +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glSecondaryColor3dv(arg0,arg1);
    String txt = new String("glSecondaryColor3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGendvEXT(int arg0,int arg1,int arg2,java.nio.DoubleBuffer arg3)
  {
        checkContext();
downstreamGL2.glMultiTexGendvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexGendvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord1sv(arg0,arg1);
    String txt = new String("glTexCoord1sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform4iv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform4iv(arg0,arg1,arg2,arg3);
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
  public  void glIndexf(float arg0)
  {
        checkContext();
downstreamGL2.glIndexf(arg0);
    String txt = new String("glIndexf(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix4x3fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix4x3fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glVertexAttrib4fvARB(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4fvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glBufferParameteri(int arg0,int arg1,int arg2)
  {
        checkContext();
downstreamGL2.glBufferParameteri(arg0,arg1,arg2);
    String txt = new String("glBufferParameteri(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform1uivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform1uivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform1uivEXT(" +
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
  public  void glRasterPos4d(double arg0,double arg1,double arg2,double arg3)
  {
        checkContext();
downstreamGL2.glRasterPos4d(arg0,arg1,arg2,arg3);
    String txt = new String("glRasterPos4d(" +
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
  public  void glUniform3ui(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glUniform3ui(arg0,arg1,arg2,arg3);
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
  public  void glRasterPos4i(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRasterPos4i(arg0,arg1,arg2,arg3);
    String txt = new String("glRasterPos4i(" +
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
  public  void glRasterPos4f(float arg0,float arg1,float arg2,float arg3)
  {
        checkContext();
downstreamGL2.glRasterPos4f(arg0,arg1,arg2,arg3);
    String txt = new String("glRasterPos4f(" +
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
  public  void glColorMask(boolean arg0,boolean arg1,boolean arg2,boolean arg3)
  {
        checkContext();
downstreamGL2.glColorMask(arg0,arg1,arg2,arg3);
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
  public  void glFogf(int arg0,float arg1)
  {
        checkContext();
downstreamGL2.glFogf(arg0,arg1);
    String txt = new String("glFogf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3hv(arg0,arg1);
    String txt = new String("glMultiTexCoord3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos4s(short arg0,short arg1,short arg2,short arg3)
  {
        checkContext();
downstreamGL2.glRasterPos4s(arg0,arg1,arg2,arg3);
    String txt = new String("glRasterPos4s(" +
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
  public  void glNormal3hv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glNormal3hv(arg0);
    String txt = new String("glNormal3hv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix4x3fvEXT(int arg0,int arg1,int arg2,boolean arg3,float[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix4x3fvEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glProgramUniformMatrix4x3fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  java.nio.ByteBuffer glMapBufferRange(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
    java.nio.ByteBuffer _res = downstreamGL2.glMapBufferRange(arg0,arg1,arg2,arg3);
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
  public  void glGetMapiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetMapiv(arg0,arg1,arg2);
    String txt = new String("glGetMapiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI1uiv(int arg0,int[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI1uiv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI1uiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord3sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord3sv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord3sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsProgramARB(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsProgramARB(arg0);
    String txt = new String("glIsProgramARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glNamedProgramLocalParametersI4ivEXT(int arg0,int arg1,int arg2,int arg3,int[] arg4,int arg5)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParametersI4ivEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glNamedProgramLocalParametersI4ivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<[I>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg5).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform2uiEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform2uiEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform2uiEXT(" +
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
  public  void glFinishObjectAPPLE(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glFinishObjectAPPLE(arg0,arg1);
    String txt = new String("glFinishObjectAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix2x4fv(int arg0,int arg1,boolean arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glUniformMatrix2x4fv(arg0,arg1,arg2,arg3);
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
  public  void glVertexAttrib4ubvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4ubvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4ubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3s(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glTexCoord3s(arg0,arg1,arg2);
    String txt = new String("glTexCoord3s(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLineWidth(float arg0)
  {
        checkContext();
downstreamGL2.glLineWidth(arg0);
    String txt = new String("glLineWidth(" +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetInvariantBooleanvEXT(int arg0,int arg1,byte[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetInvariantBooleanvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glGetInvariantBooleanvEXT(" +
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
  public  void glGetLightfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetLightfv(arg0,arg1,arg2);
    String txt = new String("glGetLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLightfv(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glLightfv(arg0,arg1,arg2);
    String txt = new String("glLightfv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetPixelMapusv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glGetPixelMapusv(arg0,arg1);
    String txt = new String("glGetPixelMapusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexEnviv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexEnviv(arg0,arg1,arg2,arg3);
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
  public  void glColor3d(double arg0,double arg1,double arg2)
  {
        checkContext();
downstreamGL2.glColor3d(arg0,arg1,arg2);
    String txt = new String("glColor3d(" +
    "<double>" +
    ", " +
    "<double>" +
    ", " +
    "<double>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetQueryObjectuiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetQueryObjectuiv(arg0,arg1,arg2);
    String txt = new String("glGetQueryObjectuiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord4dv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord4dv(arg0);
    String txt = new String("glTexCoord4dv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3h(short arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glTexCoord3h(arg0,arg1,arg2);
    String txt = new String("glTexCoord3h(" +
    "<short>" +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glPixelTransformParameterfvEXT(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glPixelTransformParameterfvEXT(arg0,arg1,arg2);
    String txt = new String("glPixelTransformParameterfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  int glBindTextureUnitParameterEXT(int arg0,int arg1)
  {
        checkContext();
    int _res = downstreamGL2.glBindTextureUnitParameterEXT(arg0,arg1);
    String txt = new String("glBindTextureUnitParameterEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib1hv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib1fv(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib1fv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib1fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorTableParameteriv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glColorTableParameteriv(arg0,arg1,arg2,arg3);
    String txt = new String("glColorTableParameteriv(" +
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
  public  void glPushMatrix()
  {
        checkContext();
downstreamGL2.glPushMatrix();
    String txt = new String("glPushMatrix(" +
    ")");
    checkGLGetError( txt );
  }
  public  int glBindTexGenParameterEXT(int arg0,int arg1,int arg2)
  {
        checkContext();
    int _res = downstreamGL2.glBindTexGenParameterEXT(arg0,arg1,arg2);
    String txt = new String("glBindTexGenParameterEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glNamedRenderbufferStorageMultisampleEXT(int arg0,int arg1,int arg2,int arg3,int arg4)
  {
        checkContext();
downstreamGL2.glNamedRenderbufferStorageMultisampleEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedRenderbufferStorageMultisampleEXT(" +
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
  public  void glEvalPoint1(int arg0)
  {
        checkContext();
downstreamGL2.glEvalPoint1(arg0);
    String txt = new String("glEvalPoint1(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixIndexusvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixIndexusvARB(arg0,arg1,arg2);
    String txt = new String("glMatrixIndexusvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniform1fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform1fvARB(arg0,arg1,arg2);
    String txt = new String("glUniform1fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord1s(short arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1s(arg0);
    String txt = new String("glTexCoord1s(" +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glStencilOpSeparate(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glStencilOpSeparate(arg0,arg1,arg2,arg3);
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
  public  void glGetVariantFloatvEXT(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetVariantFloatvEXT(arg0,arg1,arg2);
    String txt = new String("glGetVariantFloatvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniform4fvEXT(int arg0,int arg1,int arg2,java.nio.FloatBuffer arg3)
  {
        checkContext();
downstreamGL2.glProgramUniform4fvEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glProgramUniform4fvEXT(" +
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
  public  void glMultTransposeMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glMultTransposeMatrixf(arg0);
    String txt = new String("glMultTransposeMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glLoadTransposeMatrixd(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glLoadTransposeMatrixd(arg0,arg1);
    String txt = new String("glLoadTransposeMatrixd(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVariantfvEXT(int arg0,float[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVariantfvEXT(arg0,arg1,arg2);
    String txt = new String("glVariantfvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultMatrixf(float[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMultMatrixf(arg0,arg1);
    String txt = new String("glMultMatrixf(" +
    "<[F>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glNormalPointer(int arg0,int arg1,java.nio.Buffer arg2)
  {
        checkContext();
downstreamGL2.glNormalPointer(arg0,arg1,arg2);
    String txt = new String("glNormalPointer(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.Buffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2fv(int arg0,java.nio.FloatBuffer arg1)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2fv(arg0,arg1);
    String txt = new String("glMultiTexCoord2fv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramInfoLog(int arg0,int arg1,java.nio.IntBuffer arg2,java.nio.ByteBuffer arg3)
  {
        checkContext();
downstreamGL2.glGetProgramInfoLog(arg0,arg1,arg2,arg3);
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
  public  void glTexCoord1h(short arg0)
  {
        checkContext();
downstreamGL2.glTexCoord1h(arg0);
    String txt = new String("glTexCoord1h(" +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMatrixLoadTransposedEXT(int arg0,java.nio.DoubleBuffer arg1)
  {
        checkContext();
downstreamGL2.glMatrixLoadTransposedEXT(arg0,arg1);
    String txt = new String("glMatrixLoadTransposedEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3dv(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glTexCoord3dv(arg0,arg1);
    String txt = new String("glTexCoord3dv(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glAttachObjectARB(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glAttachObjectARB(arg0,arg1);
    String txt = new String("glAttachObjectARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glProgramUniformMatrix3x4fvEXT(int arg0,int arg1,int arg2,boolean arg3,java.nio.FloatBuffer arg4)
  {
        checkContext();
downstreamGL2.glProgramUniformMatrix3x4fvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glProgramUniformMatrix3x4fvEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<boolean>" +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsNamedBufferResidentNV(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsNamedBufferResidentNV(arg0);
    String txt = new String("glIsNamedBufferResidentNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glTextureParameterfvEXT(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glTextureParameterfvEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glTextureParameterfvEXT(" +
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
  public  void glMultiTexBufferEXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glMultiTexBufferEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glMultiTexBufferEXT(" +
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
  public  boolean glTestFenceAPPLE(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glTestFenceAPPLE(arg0);
    String txt = new String("glTestFenceAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glVertexAttrib2sARB(int arg0,short arg1,short arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib2sARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib2sARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<short>" +
    ", " +
    "<short>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glIsShader(int arg0)
  {
        checkContext();
    boolean _res = downstreamGL2.glIsShader(arg0);
    String txt = new String("glIsShader(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glPushClientAttribDefaultEXT(int arg0)
  {
        checkContext();
downstreamGL2.glPushClientAttribDefaultEXT(arg0);
    String txt = new String("glPushClientAttribDefaultEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  int glCreateProgramObjectARB()
  {
        checkContext();
    int _res = downstreamGL2.glCreateProgramObjectARB();
    String txt = new String("glCreateProgramObjectARB(" +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glMatrixIndexubvARB(int arg0,byte[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMatrixIndexubvARB(arg0,arg1,arg2);
    String txt = new String("glMatrixIndexubvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[B>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos3sv(arg0);
    String txt = new String("glRasterPos3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4bvARB(int arg0,java.nio.ByteBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4bvARB(arg0,arg1);
    String txt = new String("glVertexAttrib4bvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glUniformMatrix3x2fv(int arg0,int arg1,boolean arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glUniformMatrix3x2fv(arg0,arg1,arg2,arg3,arg4);
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
  public  void glVertexPointer(int arg0,int arg1,int arg2,long arg3)
  {
        checkContext();
downstreamGL2.glVertexPointer(arg0,arg1,arg2,arg3);
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
  public  java.lang.String glGetStringi(int arg0,int arg1)
  {
        checkContext();
    java.lang.String _res = downstreamGL2.glGetStringi(arg0,arg1);
    String txt = new String("glGetStringi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glNamedRenderbufferStorageMultisampleCoverageEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5)
  {
        checkContext();
downstreamGL2.glNamedRenderbufferStorageMultisampleCoverageEXT(arg0,arg1,arg2,arg3,arg4,arg5);
    String txt = new String("glNamedRenderbufferStorageMultisampleCoverageEXT(" +
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
  public  void glVertexAttrib4Nsv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nsv(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4Nsv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultTransposeMatrixd(double[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glMultTransposeMatrixd(arg0,arg1);
    String txt = new String("glMultTransposeMatrixd(" +
    "<[D>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3fv(arg0);
    String txt = new String("glSecondaryColor3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexParameterf(int arg0,int arg1,float arg2)
  {
        checkContext();
downstreamGL2.glTexParameterf(arg0,arg1,arg2);
    String txt = new String("glTexParameterf(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttribI4sv(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribI4sv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribI4sv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetProgramivARB(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramivARB(arg0,arg1,arg2);
    String txt = new String("glGetProgramivARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyMultiTexImage1DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7)
  {
        checkContext();
downstreamGL2.glCopyMultiTexImage1DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7);
    String txt = new String("glCopyMultiTexImage1DEXT(" +
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
  public  void glLoadTransposeMatrixf(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glLoadTransposeMatrixf(arg0);
    String txt = new String("glLoadTransposeMatrixf(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glFrustumf(float arg0,float arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glFrustumf(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glTexParameterIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glTexParameterIuiv(arg0,arg1,arg2,arg3);
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
  public  void glCompressedTexImage1D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,java.nio.Buffer arg6)
  {
        checkContext();
downstreamGL2.glCompressedTexImage1D(arg0,arg1,arg2,arg3,arg4,arg5,arg6);
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
  public  void glPixelZoom(float arg0,float arg1)
  {
        checkContext();
downstreamGL2.glPixelZoom(arg0,arg1);
    String txt = new String("glPixelZoom(" +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRasterPos3iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glRasterPos3iv(arg0);
    String txt = new String("glRasterPos3iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex3sv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex3sv(arg0);
    String txt = new String("glVertex3sv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4hv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4hv(arg0,arg1);
    String txt = new String("glVertexAttrib4hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glNamedProgramLocalParameterI4ivEXT(int arg0,int arg1,int arg2,java.nio.IntBuffer arg3)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParameterI4ivEXT(arg0,arg1,arg2,arg3);
    String txt = new String("glNamedProgramLocalParameterI4ivEXT(" +
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
  public  void glUniform4fvARB(int arg0,int arg1,java.nio.FloatBuffer arg2)
  {
        checkContext();
downstreamGL2.glUniform4fvARB(arg0,arg1,arg2);
    String txt = new String("glUniform4fvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexGenivEXT(int arg0,int arg1,int arg2,int[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glMultiTexGenivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glMultiTexGenivEXT(" +
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
  public  void glOrthof(float arg0,float arg1,float arg2,float arg3,float arg4,float arg5)
  {
        checkContext();
downstreamGL2.glOrthof(arg0,arg1,arg2,arg3,arg4,arg5);
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
  public  void glVertexAttrib4usvARB(int arg0,short[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glVertexAttrib4usvARB(arg0,arg1,arg2);
    String txt = new String("glVertexAttrib4usvARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertexAttrib4Nusv(int arg0,java.nio.ShortBuffer arg1)
  {
        checkContext();
downstreamGL2.glVertexAttrib4Nusv(arg0,arg1);
    String txt = new String("glVertexAttrib4Nusv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3usv(java.nio.ShortBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3usv(arg0);
    String txt = new String("glSecondaryColor3usv(" +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetVertexAttribIuiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetVertexAttribIuiv(arg0,arg1,arg2,arg3);
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
  public  void glGetMapAttribParameterfvNV(int arg0,int arg1,int arg2,float[] arg3,int arg4)
  {
        checkContext();
downstreamGL2.glGetMapAttribParameterfvNV(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glGetMapAttribParameterfvNV(" +
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
  public  void glPixelTransferi(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glPixelTransferi(arg0,arg1);
    String txt = new String("glPixelTransferi(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glVertex2iv(java.nio.IntBuffer arg0)
  {
        checkContext();
downstreamGL2.glVertex2iv(arg0);
    String txt = new String("glVertex2iv(" +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glHint(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glHint(arg0,arg1);
    String txt = new String("glHint(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetTexParameterIiv(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetTexParameterIiv(arg0,arg1,arg2,arg3);
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
  public  void glFogCoorddv(java.nio.DoubleBuffer arg0)
  {
        checkContext();
downstreamGL2.glFogCoorddv(arg0);
    String txt = new String("glFogCoorddv(" +
    "<java.nio.DoubleBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glSecondaryColor3ubv(java.nio.ByteBuffer arg0)
  {
        checkContext();
downstreamGL2.glSecondaryColor3ubv(arg0);
    String txt = new String("glSecondaryColor3ubv(" +
    "<java.nio.ByteBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glDisableIndexed(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glDisableIndexed(arg0,arg1);
    String txt = new String("glDisableIndexed(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glWindowPos2sv(short[] arg0,int arg1)
  {
        checkContext();
downstreamGL2.glWindowPos2sv(arg0,arg1);
    String txt = new String("glWindowPos2sv(" +
    "<[S>" +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glGetShaderiv(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetShaderiv(arg0,arg1,arg2);
    String txt = new String("glGetShaderiv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glHintPGI(int arg0,int arg1)
  {
        checkContext();
downstreamGL2.glHintPGI(arg0,arg1);
    String txt = new String("glHintPGI(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
  }
  public  void glCopyTextureSubImage3DEXT(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,int arg8,int arg9)
  {
        checkContext();
downstreamGL2.glCopyTextureSubImage3DEXT(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
    String txt = new String("glCopyTextureSubImage3DEXT(" +
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
  public  void glShaderOp2EXT(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glShaderOp2EXT(arg0,arg1,arg2,arg3);
    String txt = new String("glShaderOp2EXT(" +
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
  public  void glCompressedTexImage3D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,java.nio.Buffer arg8)
  {
        checkContext();
downstreamGL2.glCompressedTexImage3D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
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
  public  void glVertexAttribs3hv(int arg0,int arg1,java.nio.ShortBuffer arg2)
  {
        checkContext();
downstreamGL2.glVertexAttribs3hv(arg0,arg1,arg2);
    String txt = new String("glVertexAttribs3hv(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.ShortBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glColorMaskIndexed(int arg0,boolean arg1,boolean arg2,boolean arg3,boolean arg4)
  {
        checkContext();
downstreamGL2.glColorMaskIndexed(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glColorMaskIndexed(" +
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
  public  void glNamedProgramLocalParametersI4uivEXT(int arg0,int arg1,int arg2,int arg3,java.nio.IntBuffer arg4)
  {
        checkContext();
downstreamGL2.glNamedProgramLocalParametersI4uivEXT(arg0,arg1,arg2,arg3,arg4);
    String txt = new String("glNamedProgramLocalParametersI4uivEXT(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg2).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg3).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glTexCoord3fv(java.nio.FloatBuffer arg0)
  {
        checkContext();
downstreamGL2.glTexCoord3fv(arg0);
    String txt = new String("glTexCoord3fv(" +
    "<java.nio.FloatBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glRenderbufferStorage(int arg0,int arg1,int arg2,int arg3)
  {
        checkContext();
downstreamGL2.glRenderbufferStorage(arg0,arg1,arg2,arg3);
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
  public  void glGetProgramLocalParameterIivNV(int arg0,int arg1,java.nio.IntBuffer arg2)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterIivNV(arg0,arg1,arg2);
    String txt = new String("glGetProgramLocalParameterIivNV(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ", " +
    "<java.nio.IntBuffer>" +
    ")");
    checkGLGetError( txt );
  }
  public  boolean glTestObjectAPPLE(int arg0,int arg1)
  {
        checkContext();
    boolean _res = downstreamGL2.glTestObjectAPPLE(arg0,arg1);
    String txt = new String("glTestObjectAPPLE(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<int> 0x"+Integer.toHexString(arg1).toUpperCase() +
    ")");
    checkGLGetError( txt );
    return _res;
  }
  public  void glGetProgramLocalParameterIuivNV(int arg0,int arg1,int[] arg2,int arg3)
  {
        checkContext();
downstreamGL2.glGetProgramLocalParameterIuivNV(arg0,arg1,arg2,arg3);
    String txt = new String("glGetProgramLocalParameterIuivNV(" +
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
  public  void glUniform2fARB(int arg0,float arg1,float arg2)
  {
        checkContext();
downstreamGL2.glUniform2fARB(arg0,arg1,arg2);
    String txt = new String("glUniform2fARB(" +
    "<int> 0x"+Integer.toHexString(arg0).toUpperCase() +
    ", " +
    "<float>" +
    ", " +
    "<float>" +
    ")");
    checkGLGetError( txt );
  }
  public  void glMultiTexCoord2dv(int arg0,double[] arg1,int arg2)
  {
        checkContext();
downstreamGL2.glMultiTexCoord2dv(arg0,arg1,arg2);
    String txt = new String("glMultiTexCoord2dv(" +
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
    sb.append("DebugGL2 [ implementing javax.media.opengl.GL2,\n\t");
    sb.append(" downstream: "+downstreamGL2.toString()+"\n\t]");
    return sb.toString();
  }
  private void checkGLGetError(String caller)
  {
    if (insideBeginEndPair) {
      return;
    }

    // Debug code to make sure the pipeline is working; leave commented out unless testing this class
    //System.err.println("Checking for GL errors after call to " + caller);

    int err = downstreamGL2.glGetError();
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
    } while ((--recursionDepth >= 0) && (err = downstreamGL2.glGetError()) != GL_NO_ERROR);
    throw new GLException(buf.toString());
  }
  /** True if the pipeline is inside a glBegin/glEnd pair.*/
  private boolean insideBeginEndPair = false;

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

  private GL2 downstreamGL2;
} // end class DebugGL2
