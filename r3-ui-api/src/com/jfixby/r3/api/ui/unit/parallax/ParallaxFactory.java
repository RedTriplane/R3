
package com.jfixby.r3.api.ui.unit.parallax;

public interface ParallaxFactory {

	ParallaxSpecs newParallaxSpecs ();

	Parallax newParallax (ParallaxSpecs specs);

}
