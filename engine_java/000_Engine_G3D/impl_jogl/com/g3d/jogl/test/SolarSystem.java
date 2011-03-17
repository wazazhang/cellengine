package com.g3d.jogl.test;


import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*; 

public class  SolarSystem 
{

  //////////////// Variables /////////////////////////

  // Solar system.
  float hourofday = 0f;
  float dayofyear = 0f;
  float dayofmonth = 10f;

  // Var for displaylists.
  int sun, planet1, planet2;
  int moon11, moon12, moon21, spaceship;


  ///////////////// Functions /////////////////////////
	public SolarSystem() {
	}


 public void draw( GL gl, GLU glu ) 
    {
       GLUquadric qobj0 = glu.gluNewQuadric();
       glu.gluQuadricDrawStyle( qobj0, GLU.GLU_LINE );
//       glu.gluQuadricNormals( qobj0, GLU.GLU_SMOOTH );

      // Take a photo:
      //if( dayofyear > 3 ) animator.stop();
      
      float angleDay = ( hourofday / 24f ) * 360f;
      float angleYear = ( dayofyear / 365f ) * 360f;
      float angleMonth = ( dayofmonth / 28f ) * 360f;
      // Clocktick (unit is 1 hour): step time.
      final float clocktick = 20f;
      hourofday = (hourofday+clocktick) % 24f;
      dayofmonth = (dayofmonth+(clocktick/24f)) % 28f;
      dayofyear = (dayofyear+(clocktick/24f)) % 365f;

//       System.out.println( "Day: " + angleDay +
// 			  "  Month: " + angleMonth +
// 			  "  Year: " + angleYear );//ddd

      gl.glPushMatrix(); {
	// Sun
	gl.glColor4f( 1f, 1f, 1f, 1f );
	glu.gluSphere( qobj0, 0.8f, 10, 10) ;      

	gl.glPushMatrix(); {
	  // Planet 1 
	  gl.glRotatef( angleYear, 0.0f, 1.0f, 0.0f ); 
	  gl.glTranslatef ( 3.0f, 0.0f, 0.0f ); 
	  gl.glColor4f( 0f, 1f, 0f, 1f );
	  glu.gluSphere( qobj0, 0.3f, 10, 10) ;      

	  // Moon 11
	  gl.glPushMatrix(); {
	    gl.glRotatef( angleMonth, 0.0f, 1.0f, 0.0f ); 
	    gl.glTranslatef (0.8f, 0.0f, 0.0f ); 
	    glu.gluSphere( qobj0, 0.1f, 10, 10 ) ;      
	  } gl.glPopMatrix(); 
	
	} gl.glPopMatrix(); // Planet 1 

      } gl.glPopMatrix(); // sun

      // glu.gluDeleteQuadric( qobj0 );
    }


}
