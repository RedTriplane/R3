package org.box2d.r3.api;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.Float2;

public interface CircleShape extends Shape {

	void setRadius(double radius);

	void setPosition(Float2 centerTmp);

	ReadOnlyFloat2 getPosition();

	double getRadius();
}
