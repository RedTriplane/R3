package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.text.StringValue;

public interface RasterizedStringSpecs {

	void setString(StringValue value);

	StringValue getString();

	void setFont(RasterizedFont font);

	RasterizedFont getFont();

}
