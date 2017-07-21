
package com.jfixby.r3.api.activity.parallax;

public interface ParallaxFactory {

	ParallaxSpecs newParallaxSpecs ();

	Parallax newParallax (ParallaxSpecs specs);

}
