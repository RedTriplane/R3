
package com.jfixby.r3.activity.red.parallax;

import com.jfixby.r3.activity.api.parallax.Parallax;
import com.jfixby.r3.activity.api.parallax.ParallaxFactory;
import com.jfixby.r3.activity.api.parallax.ParallaxSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;

public class RedParallaxFactory implements ParallaxFactory {

	public final RedComponentsFactory redComponentsFactory;

	public RedParallaxFactory (final RedComponentsFactory redComponentsFactory) {
		this.redComponentsFactory = redComponentsFactory;
	}

	@Override
	public ParallaxSpecs newParallaxSpecs () {
		return new RedParallaxSpecs();
	}

	@Override
	public Parallax newParallax (final ParallaxSpecs specs) {
		return new RedParallax(this, specs);
	}

}
