
package com.jfixby.r3.activity.red.parallax;

import com.jfixby.r3.activity.api.parallax.ParallaxElementSpecs;
import com.jfixby.r3.activity.api.parallax.ParallaxSpecs;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;

public class RedParallaxSpecs implements ParallaxSpecs {
	final Set<ParallaxElementSpecs> children = Collections.newSet();
	private String name;
	private double posX;
	private double posY;
	private double h;
	private double w;

	@Override
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public void addChild (final ParallaxElementSpecs child) {
		this.children.add(child);
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public Collection<ParallaxElementSpecs> getChildren () {
		return this.children;
	}

	@Override
	public ParallaxElementSpecs newParallaxElementSpecs () {
		return new RedParallaxElementSpecs();

	}

	@Override
	public void setPositionX (final double posX) {
		this.posX = posX;
	}

	@Override
	public void setPositionY (final double posY) {
		this.posY = posY;
	}

	@Override
	public double getPositionX () {
		return this.posX;
	}

	@Override
	public double getPositionY () {
		return this.posY;
	}

	@Override
	public double getHeight () {
		return this.h;
	}

	@Override
	public double getWidth () {
		return this.w;
	}

	@Override
	public void setWidth (final double width) {
		this.w = width;
	}

	@Override
	public void setHeight (final double height) {
		this.h = height;
	}

}
