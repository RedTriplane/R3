
package com.jfixby.r3.activity.api.txt;

import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.names.ID;

public class TextBarSpecs {

	public float fontSize = 10;
	public Color fontColor = Colors.BLACK();
	public float borderSize;
	public ID fontID;
	public Color borderColor;

	public Raster backgroundRaster;
	public String name;
	public ID text;
	public float padding;

	@Override
	public String toString () {
		return "TextBarSpecs [fontSize=" + this.fontSize + ", fontColor=" + this.fontColor + ", borderSize=" + this.borderSize
			+ ", fontID=" + this.fontID + ", borderColor=" + this.borderColor + ", backgroundRaster=" + this.backgroundRaster
			+ ", name=" + this.name + ", text=" + this.text + ", padding=" + this.padding + "]";
	}

}
