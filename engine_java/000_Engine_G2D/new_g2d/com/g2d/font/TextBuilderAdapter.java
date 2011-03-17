package com.g2d.font;

import com.g2d.text.Instruction;


/**
 * 将 TextAttribute REPLACEMENT 转译渲染
 * @author WAZA
 *
 */
public interface TextBuilderAdapter 
{
	abstract public GraphicAttribute	createGraphicAttribute(Instruction instruction, String key, String value);

}
