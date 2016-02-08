package com.jfixby.r3.ext.font.gdx.gwt.ft;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.r3.ext.api.font.FontParameters;

public class GwtR3FontParameters implements FontParameters {

	private int size;
	private String requiredCharacters;
	private float scaleValue;
	private float reScaleValue;
	private Color color;

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void setCharacters(String requiredCharacters) {
		this.requiredCharacters = requiredCharacters;
	}

	@Override
	public void setScaleValue(float scaleValue) {
		this.scaleValue = scaleValue;
	}

	@Override
	public void setReScaleValue(float reScaleValue) {
		this.reScaleValue = reScaleValue;
	}

	@Override
	public String getCharacters() {
		return requiredCharacters;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public float getScaleValue() {
		return scaleValue;
	}

	@Override
	public float getReScaleValue() {
		return reScaleValue;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
