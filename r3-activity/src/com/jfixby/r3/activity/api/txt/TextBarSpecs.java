
package com.jfixby.r3.activity.api.txt;

import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;

public class TextBarSpecs {

	public float fontSize = 10;
	public Color fontColor = Colors.BLACK();
	public float borderSize;
	public ID fontID;
	public Color borderColor;

	public Raster backgroundRaster;
	public String name;
	public String text;
	public float padding;

}
