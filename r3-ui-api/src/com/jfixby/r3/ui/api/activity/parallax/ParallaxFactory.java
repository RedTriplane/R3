
package com.jfixby.r3.ui.api.activity.parallax;

public interface ParallaxFactory {

	ParallaxSpecs newParallaxSpecs ();

	Parallax newParallax (ParallaxSpecs specs);

}
