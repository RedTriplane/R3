
package com.jfixby.r3.activity.red.input;

import com.jfixby.r3.activity.api.input.TouchAreaSpecs;

public class RedTouchAreaSpecs implements TouchAreaSpecs {

	private double position_x;
	private double position_y;
	private double width;
	private double height;
	private String name;

	// private AssetID id;

	@Override
	public void setArea (double position_x, double position_y, double width, double height) {
		this.position_x = position_x;
		this.position_y = position_y;
		this.width = width;
		this.height = height;
	}

	@Override
	public double getPostionX () {
		return this.position_x;
	}

	@Override
	public double getPostionY () {
		return this.position_y;
	}

	@Override
	public double getWidth () {
		return this.width;
	}

	@Override
	public double getHeight () {
		return this.height;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public void setName (String name) {
		this.name = name;
	}

	// @Override
	// public void setID(AssetID id) {
	// this.id = id;
	// }
	//
	// @Override
	// public AssetID getID() {
	// return id;
	// }
}
