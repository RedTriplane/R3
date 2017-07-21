
package com.jfixby.r3.activity.red.parallax;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.parallax.Parallax;
import com.jfixby.r3.activity.api.parallax.ParallaxElementSpecs;
import com.jfixby.r3.activity.api.parallax.ParallaxSpecs;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;

public class RedParallax implements Parallax, LayerBasedComponent {

	private final RedParallaxFactory redParallaxFactory;
	private final RedLayer root;
// private final RedParallaxCamera camera = new RedParallaxCamera(this);
	final List<RedParallaxComponentHandler> handlers = Collections.newList();
	private final OffsetProjection parallaxPosition;
	private final double h;
	private final double w;

	public double getHeight () {
		return this.h;
	}

	@Override
	public double getWidth () {
		return this.w;
	}

	public RedParallax (final RedParallaxFactory redParallaxFactory, final ParallaxSpecs specs) {
		this.redParallaxFactory = redParallaxFactory;
		this.root = redParallaxFactory.redComponentsFactory.newLayer();
		this.root.setName(specs.getName());
		final double x = specs.getPositionX();
		final double y = specs.getPositionY();

		this.h = specs.getHeight();
		this.w = specs.getWidth();
		this.parallaxPosition = Geometry.getProjectionFactory().newOffset();
		this.parallaxPosition.setOffsetX(x);
		this.parallaxPosition.setOffsetY(y);
		this.root.setProjection(this.parallaxPosition);

		final Collection<ParallaxElementSpecs> children = specs.getChildren();

		for (int i = 0; i < children.size(); i++) {
			final ParallaxElementSpecs child = children.getElementAt(i);
			final Component component = child.getComponent();
			final RedParallaxComponentHandler handler = new RedParallaxComponentHandler(this, component);
			this.handlers.add(handler);
			final float multiplier_x = child.getMultiplierX();
			final float multiplier_y = child.getMultiplierY();
			final float multiplier_z = child.getMultiplierZ();

			handler.setMultiplierX(multiplier_x);
			handler.setMultiplierY(multiplier_y);
			handler.setMultiplierZ(multiplier_z);

			this.root.attachComponent(handler.getRoot());
		}

// this.root.setCamera(this.camera);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void setParallaxOffset (final Float2 offset) {
// L.d("setParallaxOffset", offset);
		for (final RedParallaxComponentHandler handler : this.handlers) {
			handler.setParallaxOffset(offset);
		}
	}

	@Override
	public void setPositionX (final double x) {
		this.parallaxPosition.setOffsetX(x);
	}

	@Override
	public void setPositionY (final double y) {
		this.parallaxPosition.setOffsetY(y);
	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

}
