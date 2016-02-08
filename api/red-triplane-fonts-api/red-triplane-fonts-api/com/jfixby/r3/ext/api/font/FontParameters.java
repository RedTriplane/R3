package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.color.Color;

public interface FontParameters {

	void setSize(int round);

	void setColor(Color color);

	void setCharacters(String requiredCharacters);

	void setScaleValue(float scaleValue);

	void setReScaleValue(float reScaleValue);

	String getCharacters();

	int getSize();

	float getScaleValue();

	float getReScaleValue();

	Color getColor();
}
