package com.jfixby.r3.api.ui.unit.input;


public interface TouchAreaSpecs {

	public String getName();

	public void setName(String name);

	void setArea(double position_x, double position_y, double width,
			double height);

	public double getPostionX();

	public double getPostionY();

	public double getWidth();

	public double getHeight();

//	public void setID(AssetID id);

//	public AssetID getID();

}
