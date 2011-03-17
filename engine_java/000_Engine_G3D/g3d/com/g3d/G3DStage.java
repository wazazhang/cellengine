package com.g3d;

public abstract class G3DStage 
{
	abstract public void init		(G3DCanvas canvas);
	
	abstract public void update		(G3DCanvas canvas, G3DGraphics g, int interval);
	
	abstract public void removed	(G3DCanvas canvas);
}
