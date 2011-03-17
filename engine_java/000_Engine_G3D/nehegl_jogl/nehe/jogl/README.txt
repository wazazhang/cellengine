LICENSE:
--------
As said on top of each files this code is provided in GPL (Look in COPYING.txt for more informations).

DISCLAIMER:
-----------
This code is provided as is. 
I cannot be considered responsible for anything happening when using this code.
I does not guaranteed that this code is bug free or tested in all sorts of environment.

GENERALS:
---------
To create this OpenGL framework for JOGL, i gathered what can be found on different lesson ports on nehe.gamedev.net. 
So i thanks all the people spending time to port those lessons to JOGL.

I develloped this code with :
 - Eclipse 3.0M8 (A really nice free Java IDE)
 - Java SDK 1.4.2_04
 - JOGL 1.1b03

USAGE:
------

To use this you must first install JOGL. For that go to JOGL site (https://jogl.dev.java.net/) and download : 
 - jogl.jar (the base JOGL framework) (1)
 - jogl-natives-[your architecture].jar (2)

Copy (1) to $JAVA_HOME$/jre/lib/ext
Extract (2) to $JAVA_HOME$/jre/lib/i386/

please look in nehe.jogl.sample.SampleJOGL.java to see how to use my framework

USEFULL CLASSES:
---------------

nehe.jogl.lib.Renderer 		--> This class must only be extended to create a new OpenGL enabled frame 
								See SampleJOGL
nehe.jogl.lib.TextureLoader --> This class is usefull to load textures from .bmp or .png files
								sample from GLPrinter.java:         
										TextureLoader myText = new TextureLoader(gLDrawable,fileName);
        								int texture = myText.getTexture();
nehe.jogl.lib.GLPrinter		--> This class is usefull to print text on screen use it as : myRenderer.getPrinter().glPrint(...)

FINAL NOTE:
-----------

As i am french speaker i really apologize for my spelling i try to do my best ;)

@author konik (konik0001@msn.com)
