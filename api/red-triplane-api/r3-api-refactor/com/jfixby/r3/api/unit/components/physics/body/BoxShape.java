package com.jfixby.r3.api.unit.components.physics.body;

public interface BoxShape extends Shape{

	void setSize(double width, double height);

	void setWidth(double width);

	void setHeight(double height);

	double getWidth();

	double getHeight();

}
