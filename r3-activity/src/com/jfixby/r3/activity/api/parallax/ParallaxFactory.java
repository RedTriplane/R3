
package com.jfixby.r3.activity.api.parallax;

public interface ParallaxFactory {

	ParallaxSpecs newParallaxSpecs ();

	Parallax newParallax (ParallaxSpecs specs);

}
