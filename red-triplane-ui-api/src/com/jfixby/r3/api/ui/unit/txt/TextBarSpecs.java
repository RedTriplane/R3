
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.strings.api.Text;

public interface TextBarSpecs {

	void setFont (ID font_id);

	ID getFont ();

	void setFontSize (float font_size);

	float getFontSize ();

	public void setPadding (float padding);

	public float getPadding ();

	Raster getBackgroundRaster ();

	void setBackgroundRaster (Raster bg_asset_id);

	void setFontScale (float font_scale);

	float getFontScale ();

	void setLocaleName (String locale_name);

	public String getLocaleName ();

	void setFontColor (Color color);

	Color getFontColor ();

	void setRawText (String text_value_raw);

	String getRawText ();

	Text getText ();

	void setText (Text text);

}
