
package com.jfixby.r3.fokker.font.red.gdx;

import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.log.L;

public class GdxR3FontParameters {

	private int size;
	private String requiredCharacters;
	private float scaleValue;
	private float reScaleValue;
	private Color color;
	private int border;
	private Color border_color;

	public void setSize (final int size) {
		this.size = size;
	}

	public void setColor (final Color color) {
		this.color = color;
	}

	public void setCharacters (final String requiredCharacters) {
		this.requiredCharacters = requiredCharacters;
	}

	public void setScaleValue (final float scaleValue) {
		this.scaleValue = scaleValue;
	}

	public void setReScaleValue (final float reScaleValue) {
		this.reScaleValue = reScaleValue;
	}

	public String getCharacters () {
		return this.requiredCharacters;
	}

	public int getSize () {
		return this.size;
	}

	public float getScaleValue () {
		return this.scaleValue;
	}

	public float getReScaleValue () {
		return this.reScaleValue;
	}

	public Color getColor () {
		return this.color;
	}

	public void setBorderSize (final int border) {
		this.border = border;
	}

	public void setBorderColor (final Color border_color) {
		this.border_color = border_color;
	}

	public Color getBorderColor () {
		return this.border_color;
	}

	public int getBorderSize () {
		return this.border;
	}

	public void print () {
		L.d(this);
	}

	@Override
	public String toString () {
		return "GdxR3FontParameters [requiredCharacters=" + this.requiredCharacters + ", size=" + this.size + ", scaleValue="
			+ this.scaleValue + ", reScaleValue=" + this.reScaleValue + ", color=" + this.color + ", border=" + this.border
			+ ", border_color=" + this.border_color + "]";
	}

}
