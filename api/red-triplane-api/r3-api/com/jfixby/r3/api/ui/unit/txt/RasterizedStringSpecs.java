
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.color.Color;

public interface RasterizedStringSpecs {

// void setFont (RasterizedFont font);
//
// RasterizedFont getFont ();

	void setFontName (AssetID font_id);

	AssetID getFontName ();

	float getFontSize ();

	void setFontSize (float size);

	Collection<Character> getRequiredCharacters ();

	void addRequiredCharacters (String chars);

	void setColor (Color font_color);

	public Color getColor ();

	void setFontScale (float font_scale);

	float getFontScale ();

	void setBorderSize (float border);

	void setBorderColor (Color border_color);

	Color getBorderColor ();

	float getBorderSize ();

}
