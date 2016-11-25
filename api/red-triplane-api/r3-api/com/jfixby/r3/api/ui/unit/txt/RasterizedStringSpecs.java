
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.color.Color;

public interface RasterizedStringSpecs {

	float getFontSize ();

	void setFontSize (float size);

	Collection<Character> getRequiredCharacters ();

	void addRequiredCharacters (String chars);

	void addRequiredCharacters (Iterable<Character> chars);

	void setFontColor (Color font_color);

	public Color getFontColor ();

	void setFontScale (float font_scale);

	float getFontScale ();

	void setBorderSize (float border);

	void setBorderColor (Color border_color);

	Color getBorderColor ();

	float getBorderSize ();

	void setFontName (ID newAssetID);

	ID getFontName ();

}
