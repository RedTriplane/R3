
package com.jfixby.r3.api.ui.unit.parallax;

import com.jfixby.scarabei.api.collections.Collection;

public interface ParallaxSpecs {

	void setName (String name);

	void addChild (ParallaxElementSpecs child);

	String getName ();

	Collection<ParallaxElementSpecs> getChildren ();

	ParallaxElementSpecs newParallaxElementSpecs ();

	void setPositionX (double posX);

	void setPositionY (double posY);

	double getPositionX ();

	double getPositionY ();

	double getHeight ();

	double getWidth ();

	void setWidth (double width);

	void setHeight (double height);

}
