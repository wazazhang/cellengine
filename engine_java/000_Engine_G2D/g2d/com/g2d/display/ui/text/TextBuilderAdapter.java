package com.g2d.display.ui.text;

import java.awt.font.GraphicAttribute;

public interface TextBuilderAdapter 
{
	GraphicAttribute createGraphicAttribute(Instruction instruction, String key, String value);
}
